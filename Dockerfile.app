# Dockerfile.app
FROM openjdk:18
WORKDIR /app

# Copy your application’s jar file into the container
COPY target/SPE_Social_Project-1.0-SNAPSHOT.jar app.jar
COPY mysql-connector-j-8.0.33.jar mysql-connector-j-8.0.33.jar

# Run the application
ENTRYPOINT ["sh", "-c", "echo 'Waiting for 30 seconds...'; sleep 30; java -cp app.jar:mysql-connector-j-8.0.33.jar JDBCTest"]
EXPOSE 8081