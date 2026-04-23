# Smart Campus Sensor & Room Management API

A RESTful API built with JAX-RS (Jersey) and Apache Tomcat for managing rooms and sensors across a university campus.

---

## API Overview

The Smart Campus API provides a versioned REST interface for campus facilities managers to manage physical rooms and the IoT sensors deployed within them. It supports full CRUD operations on rooms and sensors, historical sensor reading logs, and a robust error-handling strategy.

**Base URL:** `http://localhost:8080/SmartCampus/api/v1`

### Resource Hierarchy

```
/api/v1
├── /rooms
│   ├── GET    /          — List all rooms
│   ├── POST   /          — Create a new room
│   ├── GET    /{roomId}  — Get a specific room
│   └── DELETE /{roomId}  — Delete a room (blocked if sensors exist)
├── /sensors
│   ├── GET    /          — List all sensors (supports ?type= filter)
│   ├── POST   /          — Register a new sensor (validates roomId)
│   └── /{sensorId}/readings
│       ├── GET  /        — Get reading history for a sensor
│       └── POST /        — Append a new reading (updates sensor currentValue)
```

---

## Tech Stack

- **Language:** Java 8 or higher
- **Framework:** JAX-RS via Jersey 2.32
- **Server:** Apache Tomcat 9
- **Build Tool:** Maven
- **Data Store:** In-memory (`ArrayList` via `MockDatabase`)

---

## How to Build and Run

### Prerequisites
- [NetBeans IDE](https://netbeans.apache.org/) (version 18 or later recommended)
- [Apache Tomcat 9.x](https://tomcat.apache.org/download-90.cgi) — download and extract the ZIP
- [Java JDK 8](https://www.oracle.com/java/technologies/downloads/) or higher
- [Postman](https://www.postman.com/downloads/) for testing

---

### Step 1 — Set Up Apache Tomcat in NetBeans

1. In NetBeans, go to the **Services** tab
2. Right-click **Servers** → **Add Server**
3. Select **Apache Tomcat or TomEE** → click **Next**
4. Click **Browse** and locate your extracted Tomcat folder (e.g. `apache-tomcat-9.0.100`)
5. Enter a username and password (any value, e.g. `admin` / `admin`)
6. Click **Finish** — you should see a green triangle next to the server in the Services tab

> **Mac users only:** Before starting, open Terminal and run:
> ```bash
> cd /path/to/apache-tomcat-9.0.100/bin
> chmod +x catalina.sh startup.sh shutdown.sh
> ```

---

### Step 2 — Clone and Open the Project

```bash
git clone https://github.com/your-username/SmartCampus.git
```

Then in NetBeans:
1. **File** → **Open Project**
2. Navigate to the cloned `SmartCampus` folder and open it
3. NetBeans will detect it as a Maven project automatically

---

### Step 3 — Clean and Build

1. Right-click the project in the **Projects** tab
2. Select **Clean and Build**
3. Wait for Maven to download all dependencies — check the Output tab for `BUILD SUCCESS`

---

### Step 4 — Run the Project

1. Right-click the project
2. Select **Run**
3. NetBeans will deploy the WAR to Tomcat and open a browser automatically

---

### Step 5 — Verify

Open your browser or Postman and navigate to:
```
http://localhost:8080/SmartCampus/api/v1
```
You should receive a JSON response with API metadata.

---

## Sample curl Commands

### 1. GET — Discovery Endpoint
```bash
curl -X GET http://localhost:8080/SmartCampus/api/v1 \
  -H "Accept: application/json"
```

### 2. POST — Create a Room
```bash
curl -X POST http://localhost:8080/SmartCampus/api/v1/rooms \
  -H "Content-Type: application/json" \
  -d '{"id":"R5","name":"Innovation Lab","capacity":40}'
```

### 3. GET — Get All Sensors Filtered by Type
```bash
curl -X GET "http://localhost:8080/SmartCampus/api/v1/sensors?type=Temperature" \
  -H "Accept: application/json"
```

### 4. POST — Register a Sensor (with roomId validation)
```bash
curl -X POST http://localhost:8080/SmartCampus/api/v1/sensors \
  -H "Content-Type: application/json" \
  -d '{"id":"S6","type":"CO2","status":"active","currentValue":400.0,"roomId":"R1"}'
```

### 5. POST — Add a Sensor Reading
```bash
curl -X POST http://localhost:8080/SmartCampus/api/v1/sensors/S1/readings \
  -H "Content-Type: application/json" \
  -d '{"id":"RD10","timestamp":1700000010,"value":25.3}'
```

### 6. DELETE — Delete a Room with no sensors (success)
```bash
curl -X DELETE http://localhost:8080/SmartCampus/api/v1/rooms/R4 \
  -H "Accept: application/json"
```

### 7. DELETE — Delete a Room with sensors (409 Conflict)
```bash
curl -X DELETE http://localhost:8080/SmartCampus/api/v1/rooms/R1 \
  -H "Accept: application/json"
```

---

## Conceptual Report — Question Answers

---


**Question:** Explain the default lifecycle of a JAX-RS resource class. Is a new instance created per request, or is it a singleton? How does this impact in-memory data management?

**Answer:**

By default, JAX-RS creates a new instance of every resource class for each incoming HTTP request. When a client sends a request to an endpoint, for example `api/v1/rooms`, it will instantiate a fresh `SensorRoom` object to handle it, and that object is discarded once the response is sent.

This has a significant effect on in-memory data management. Because each resource instance is freshly created, it will not hold state between requests as instance variables. This is why data must live externally — in this case, as a separate `MockDatabase` file with static data.

However, this introduces a thread safety concern. Multiple concurrent requests can modify the same `ArrayList` simultaneously, causing data corruption. In a production system, this would be addressed using thread-safe data structures or a proper database with transaction support. For this coursework, the in-memory lists are accessed sequentially, so there will not be thread-related issues introduced.

---


**Question:** Why is the provision of hypermedia (HATEOAS) considered a hallmark of advanced RESTful design? How does it benefit client developers compared to static documentation?

**Answer:**

HATEOAS means Hypermedia As the Engine Of Application State. It is the principle that API responses should include links to related actions or resources, allowing the client to navigate the API dynamically rather than relying on hardcoded URLs.

The core benefit is discoverability. When a client calls `GET /api/v1` and receives `"rooms": "/api/v1/rooms"` in the response, it does not need prior knowledge of the URL structure. The API documents itself at runtime. This contrasts with static documentation, which can become outdated, is not machine-readable, and requires developers to update client code when URLs change manually.

For client developers, HATEOAS means that if the server-side URL structure changes, clients that follow links from the discovery endpoint will adapt automatically.

---


**Question:** When returning a list of rooms, what are the implications of returning only IDs versus returning the full room objects?

**Answer:**

Returning only IDs minimises the response payload, improving response time for large lists. However, it forces the client to make a second request for every room they want to inspect.

Returning full objects means all data is available in a single response, eliminating additional round-trip requests. The trade-off is a larger payload, which requires more memory and may include fields the client does not need.

The best approach in production is a middle ground — returning full objects by default, but supporting query parameters to allow clients to specify the fields they need.

---


**Question:** Is the DELETE operation idempotent in your implementation? What happens if a client sends the same DELETE request multiple times?

**Answer:**

The HTTP specification defines an idempotent operation as one where making the same request multiple times produces the same server state as making it once. By this definition, DELETE should be idempotent, but whether it is in practice depends on the implementation.

In this API, DELETE is not fully idempotent in terms of HTTP response codes. The first DELETE will succeed and return 200, assuming that the element exists. The second identical request finds no room with that ID, triggers a `LinkedResourceNotFoundException`, and returns a 400 status code. The server state after both calls is identical, but the responses differ.

A fully idempotent implementation would return `204 No Content` on the first successful delete, and also `204 No Content` on subsequent calls for the same ID.

---


**Question:** What are the technical consequences if a client sends data in a format other than `application/json` to an endpoint annotated with `@Consumes(MediaType.APPLICATION_JSON)`?

**Answer:**

When a client sends a request with a `Content-Type` header that does not match the media type declared in `@Consumes`, JAX-RS automatically rejects the request before it even reaches the resource method. The runtime returns an **HTTP 415 Unsupported Media Type** response immediately.

This behaviour is beneficial because it enforces a strict contract at the API boundary. Resource methods are only invoked with data in the expected format, which prevents malformed data from reaching business logic. It also provides clear, standardised feedback to consumers, who can immediately understand from the 415 status that they must correct their `Content-Type` header.

---


**Question:** Why is `@QueryParam` considered superior to embedding the filter value in the URL path for filtering and searching collections?

**Answer:**

There is a fundamental semantic distinction between path parameters and query parameters in REST design. Path parameters identify a specific, unique resource — for example, `/sensors/S1` identifies one exact sensor. Query parameters modify how a collection is returned, without changing what resource is addressed.

Using `GET /api/v1/sensors?type=CO2` correctly communicates that we are still addressing the `/sensors` collection but applying a filter to narrow results. If the type were embedded in the path as `/api/v1/sensors/type/CO2`, this implies that `type/CO2` is itself a distinct addressable resource, which is semantically incorrect. A filter is not a resource but a query.

Query parameters are also more flexible. Multiple filters combine easily without changing the URL structure. Additionally, query parameters are optional by nature, whereas optional path segments create ambiguous routing that is difficult to handle cleanly in JAX-RS.

---


**Question:** Discuss the architectural benefits of the Sub-Resource Locator pattern. How does delegating logic to separate classes help manage complexity compared to a single massive controller?

**Answer:**

The Sub-Resource Locator pattern is a JAX-RS feature where a resource method carries `@Path` but no HTTP verb annotation. Instead of handling the request itself, it instantiates and returns another resource class, and JAX-RS continues routing within that object. In this project, `SensorResource` contains a locator at `@Path("{sensorId}/readings")` that returns a `SensorReadingResource`, which then handles the actual GET and POST operations.

The primary benefit is the separation of concerns. `SensorResource` manages sensor-level operations, while `SensorReadingResource` solely manages reading history. Each class has a single well-defined purpose, making the codebase significantly easier to maintain, extend, and test compared to a single massive controller where all logic is intertwined.

---


**Question:** Why is HTTP 422 Unprocessable Entity often more semantically accurate than 404 Not Found when the issue is a missing reference inside a valid JSON payload?

**Answer:**

HTTP 404 Not Found is semantically meant to indicate that the requested URL does not correspond to any known resource on the server. When a client sends `POST /api/v1/sensors` with a `roomId` that does not exist, the URL `/api/v1/sensors` is perfectly valid. The problem is not the address — it is the content of the request body.

HTTP 422 Unprocessable Entity is specifically designed for situations where the request is syntactically correct but semantically invalid — a field within it references a non-existent entity. Returning 422 communicates precisely that the request was received and understood, but the data inside it has a logical problem. A developer receiving 404 from a POST would first check whether they have the correct URL, wasting time debugging the wrong thing, whereas 422 immediately directs them to the payload.

---


**Question:** From a cybersecurity standpoint, explain the risks associated with exposing internal Java stack traces to external API consumers.

**Answer:**

Exposing raw Java stack traces to external consumers is a significant security vulnerability known as **information disclosure**. A stack trace reveals multiple categories of sensitive internal information that an attacker can exploit.

Java stack traces expose internal package and class names, library names and version numbers, file system paths, and line numbers — all of which can be perfect resources for attackers. Library versions in particular can be cross-referenced against public CVE databases to find known exploits for those specific versions.

The `GenericExceptionMapper` in this project addresses this by catching all unexpected exceptions and returning only a generic `"An unexpected error occurred"` message with HTTP 500, while the actual exception details are logged server-side. This ensures the API never leaks internal implementation details to external consumers.

---


**Question:** Why is it advantageous to use JAX-RS filters for cross-cutting concerns like logging, rather than manually inserting `Logger.info()` statements inside every resource method?

**Answer:**

Cross-cutting concerns are behaviours that affect multiple parts of an application but do not belong to any single business operation — logging, authentication, and rate limiting are classic examples. Implementing these inside individual resource methods violates the Don't Repeat Yourself (DRY) principle and creates several practical problems.

If logging were added manually to every resource method, the same boilerplate code would be duplicated across dozens of methods. Any change to the log format would require modifying every method individually, introducing a high risk of inconsistency.

JAX-RS filters solve this by intercepting every request and response automatically from one central location, with zero changes required to resource classes. The `LoginFilter` in this project logs the HTTP method, URI, and response status for every API call from a single class. This keeps resource classes focused purely on business logic, making the codebase easier to read, test, and maintain.
