FROM ascdc/jdk8
EXPOSE 8080
RUN mkdir -p .opt/studio-backend
WORKDIR /opt/studio-backend
COPY . /opt/studio-backend
ENTRYPOINT  ["java","-jar","HCI-0.0.1-SNAPSHOT.jar"]