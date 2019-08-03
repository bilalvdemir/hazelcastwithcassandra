FROM openjdk:8
ADD build/libs/*.jar /
ADD build.gradle /build.gradle
EXPOSE 9090
ENTRYPOINT [ "java", "-jar", "hazelcastwithcassandra-0.0.1-SNAPSHOT.jar"]
