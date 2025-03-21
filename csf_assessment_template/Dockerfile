From node:23 AS angular-builder


WORKDIR /src

COPY client/*.json .
COPY client/public public
COPY client/src src

RUN npm ci
RUN npm i -g @angular/cli

Run ng build

FROM eclipse-temurin:23-jdk AS sb-builder

WORKDIR /src

COPY server/mvnw .
COPY server/pom.xml .
COPY server/src src
COPY server/.mvn .mvn

COPY --from=angular-builder /src/dist/client/browser/* /src/main/resources/static


RUN chmod a+x mvnw

Run ./mvnw package -Dtest.skip=true


FROM eclipse-temurin:23-jre 
WORKDIR /app


COPY --from=SB-builder /src/target/server-0.0.1-SNAPSHOT.jar app.jar


ENV SPRING_MONGODB_URI=mongodb://localhost:27017
ENV SPRING_MONGODB_DATABASE=restaurant
ENV SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/abc
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=Bobododo123!
ENV PORT=3000
ENV PAYEE=name

EXPOSE ${PORT}
ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar