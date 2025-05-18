# Simple application related to transaction management within a banking system


## Project Architecture
```plainText
transaction-manager/
├── src/
│   ├── main/
│   │   ├── java/com/homework/transactionmanager/
│   │   │   ├── config/
│   │   │   │   ├── CacheConfig.java
│   │   │   │   ├── TransactionMetrics.java
│   │   │   ├── controller/
│   │   │   │   ├── TransactionController.java
│   │   │   ├── dto/
│   │   │   │   ├── ErrorResponse.java
│   │   │   ├── entity/
│   │   │   │   ├── Transaction.java
│   │   │   ├── enums/
│   │   │   │   ├── TransactionType.java
│   │   │   ├── exception/
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   ├── DuplicateTransactionException.java
│   │   │   │   ├── TransactionNotFoundException.java
│   │   │   ├── repository/
│   │   │   │   ├── TransactionRepository.java
│   │   │   ├── service/
│   │   │   │   ├── TransactionService.java
│   │   │   │   ├── impl/
│   │   │   │   │   └──TransactionServiceImpl.java
│   │   │   ├── TransactionsApplication.java
│   │   ├── resources/
│   │   │   ├── templates/
│   │   │   │   ├── transactions.html
│   │   │   │   ├── add-transaction.html
│   │   │   │   ├── edit-transaction.html
│   │   │   ├── application.properties
│   │   │   ├── data.sql
│   │   │   └── schema.sql
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── prometheus.yml
└── README.md
```

## Projects Introduce
The project will be presented in the following aspects sequentially
1. Technical Solution
   + Stability Strategy
3. Implementation effects
4. Areas for Future Optimization

## Technical Solution
### Technologies Used
+ Java21
+ Spring Boot 3.1.5
+ Lombok
+ Maven
+ spring-boot-starter-web
+ spring-boot-starter-thymeleaf
+ spring-boot-starter-data-jpa
+ h2 (in memory database)
+ micrometer-registry-prometheus (monitor collect)
+ grafana (Chosen for visualization due to its)
+ caffeine (cache)
+ spring-boot-starter-cache (cache)
+ resilience4j-spring-boot2(rate limiting)
### Applications Layered Architecture

| Layer | Responsibilities |
| ---- | ----|
| Controller |  Handle HTTP requests/responses |
 | Service | Business logic processing |
| Repository | Data persistence operations |

### Memory Database Selection
Certainly, you could implement an in-memory database from scratch, but there are many open-source in-memory databases available on the market. These solutions not only handle SQL parsing and querying but also integrate seamlessly with Spring Boot.

| DataBase Type | Features                                                                                                                                                                                                                                                   | Applicable Scenarios                                                    | 
| ---- |------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------|
| H2 Database | 1. Supports in-memory mode (jdbc:h2:mem:) where data persists only during application runtime.<br/>2. Offers full SQL compliance with partial MySQL/PostgreSQL syntax compatibility.<br/> 3. Operates in either embedded mode or server mode (switchable). | Unit testing、Rapid prototyping、Lightweight applications                 |
| Apache Ignite | 1.Supports in-memory data storage with ANSI SQL compliance and ACID transactions.<br/>2. Distributed architecture with horizontal scaling capability.<br/> 3. Provides extended functionalities including machine learning and stream processing.          | Real-time computation, distributed caching, and large-scale data processing. |
| VoltDB | 1. Distributed In-Memory Architecture <br/>2. Full SQL & ACID Compliance<br/> 3.  High Performance Characteristics       | Real-time Analytics、Financial Trading Systems   |
| .... | ....                                                                                                                                                                                                                                                       | ....                                                                    |

While VoltDB would theoretically be better suited for this project's architecture, I have selected H2 due to the following practical considerations:

1. Time Constraints
   + H2 offers significantly faster integration (embedded deployment, zero configuration)
   + Eliminates distributed system coordination overhead
2. Adequate Feature Coverage
   + Meets all documented transaction requirements (ACID-compliant)
   + Provides sufficient throughput for current load projections
3. Resource Efficiency
   + Single-JVM deployment reduces operational complexity
   + Lower memory footprint aligns with prototype-stage budgets

### Cache
Using Caffeine cache
Advantages of Using Caffeine Cache
1. Extreme Performance
   + High-Concurrency Throughput

     + Segment-based locking and lock-free operations reduce thread contention by over 80%
     + Optimistic locking via Java 8’s StampedLock significantly boosts concurrent throughput
     + Asynchronous loading prevents thread blocking, achieving 5–10x higher throughput than Guava Cache
   + Intelligent Eviction Algorithms

     + W-TinyLFU balances hit rate and memory usage, improving hit rates by 20%–40%
     + Supports LRU/LFU and custom weight-based eviction rules
2. Engineering Advantages
   + Memory Management
     + Automatic eviction controls memory usage (size/time/reference-based policies)
     + Three-tier cache architecture optimizes hot-data access speed
   + Developer-Friendly

     + Guava Cache API-compatible, minimizing migration effort
     + Built-in metrics for hit rates, load times, and more

### Stability Strategy

#### Monitor and Warn
For ensuring system reliability, this project implements a comprehensive monitoring stack:

1. Core Components

   + Prometheus: Selected as the metrics collection backbone for its:
     + Multi-dimensional data model
     + Powerful query language (PromQL)
     + Reliable time-series database

   + Grafana: Chosen for visualization due to its:
      + Rich dashboard ecosystem
      + Alert management integration
      + Cross-platform compatibility

2. Key Monitoring Capabilities

   + Real-time system health tracking
   + Automated anomaly detection
   + Multi-level alert escalation (Email/SMS/Webhook)
3. Technical Rationale

   + Open-source standard with proven enterprise adoption
   + Kubernetes-native instrumentation support
   + <5% performance overhead on monitored systems

#### Unit Test And Stress Testing
##### Unit Test 
see /test/java/*/controller and page function
##### Stress Testing
Using wrk 
````plaintext
brew install wrk
sudo apt-get install wrk 
wrk -t4 -c100 -d30s http://localhost:8080/transactions
````
#### Rate Limiting
Using resilience4j to rate limiting

## Implementation effects
### Building and Running
#### Docker
````plainText
docker-compose up -d 
````
#### k8s
````plainText
kubectl apply -f k8s/app/deployment.yaml
kubectl apply -f k8s/app/service.yaml
````
#### URL
##### Base Url
http://localhost:8080/api/transactions
##### h2 console url
http://localhost:8080/h2-console
##### prometheus url
http://localhost:9090
##### grafana url
http://localhost:3000

## Areas for Future Optimization
+ Using VoltDB or Some more suitable memory cache
+ Kubernetes Deployment Monitoring
+ More 3H(High Availability,High Scalability,High Performance)


   