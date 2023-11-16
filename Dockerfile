# Use a base image with Java 17
FROM adoptopenjdk:17-jdk-hotspot as build

CMD ["mkdir", "app"]
# Set the working directory inside the container
WORKDIR /app

# Create the /app directory
#RUN mkdir /app

# Copy the packaged JAR file into the container at /app
COPY target/demo-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port that your Spring Boot application will run on
EXPOSE 8080

# Specify the command to run on container start
CMD ["java", "-jar", "app.jar"]

# Use the official PostgreSQL image
FROM postgres:latest

# Set the environment variables
ENV POSTGRES_DB near
ENV POSTGRES_USER near_user
ENV POSTGRES_PASSWORD near_by_me

# Expose the PostgreSQL port
EXPOSE 5432
