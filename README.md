# Hazelcast with CassandraDB
Hazelcast used to cache the data of CassandraDB

## Getting Started
This project includes:
 - CREATE, Retrieve, Update, Delete Operations
 - MVC Pattern
 - Web Service
 - Logger
 - Validators and custom validator annotation
 - Exceptions
 - Regex Pattern Matchers
 - Global exception handler
 - Message formatter
 - Custom model mapper 
 - Lombok
 - CassandraDB
 - Hazelcast
 - Application yml profiles
 - Builder pattern
 - Hazelcast kubernetes discovery and sync with other scaled services
 
 
# Used Annotations

 - @Service
 - @RestController
 - @RequestMapping
 - @ResponseBody
 - @GetMapping
 - @PostMapping
 - @PutMapping
 - @DeleteMapping
 - @ControllerAdvice
 - @ExceptionHandler
 - @Id
 - @NotNull
 - @Email
 - @Pattern
 - @Documented
 - @Constraint
 - @Target
 - @Retention
 - @Configuration
 - @EnableHazelcastRepositories
 - @Value
 - @Bean
 - @Profile
 - @Data
 - @AllArgsConstructor
 - @NoArgsConstructor
 - @KeySpace
 - @PostConstruct
 - Custom Validator Annotation

# Start Application With Docker
 - Build Jar with Gradle
   
   Linux:
   ```
   gradle build
   ```
   Windows:
   ```
   ./gradlew build
   ```
 - Learn Docker version
   ```
   docker -v
   ```
   if docker is not installed: 
   * [Docker For Windows](https://docs.docker.com/docker-for-windows/install/) - Download Setup
   
 - Build docker in project root directory
   ```
   docker build -f Dockerfile -t hazelcast-with-cassandradb .
   ```
 - Show docker builded images
   ```
   docker images
   ```
   - Run dockerized cassandradb with exposed port 
   ```
   docker run --name my-cassandra -d -P -p 9042:9042 cassandra:2.2.11
   ```
 - Run docker image with exposed port 
   ```
   docker run -d --name hazelcast-with-cassandradb -p 9090:9090 hazelcast-with-cassandradb
   ```

## Contributing

Please read [CHANGELOG.md](https://github.com/bilalvdemir/hazelcastwithcassandra/blob/master/CHANGELOG.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Authors

* **Bilal Demir** - *Initial work* - [bilalvdemir](https://github.com/bilalvdemir)
