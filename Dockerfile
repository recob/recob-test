FROM maven:3.5-jdk-8-alpine as build
WORKDIR /workspace/app

COPY pom.xml .
COPY recob-domain recob-domain
COPY recob-repository recob-repository
COPY recob-service recob-service
COPY recob-starter recob-starter

RUN mvn install -Dmaven.test.skip=true && rm -r *
