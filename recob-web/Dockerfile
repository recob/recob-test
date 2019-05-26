FROM recob/base as build
WORKDIR /workspace/web

COPY pom.xml .
COPY src src

RUN mvn install -Dmaven.test.skip=true
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:8-jdk-alpine
ARG DEPENDENCY=/workspace/web/target/dependency
VOLUME /tmp
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.recob.Application"]