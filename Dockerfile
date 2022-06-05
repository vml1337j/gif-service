FROM adoptopenjdk/openjdk11:alpine AS builder
WORKDIR application

COPY build/libs/*.jar app.jar
RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../../app.jar)

RUN jdeps \
--ignore-missing-deps \
-q \
--multi-release 11 \
--print-module-deps \
--class-path "build/dependency/BOOT-INF/lib/*" \
app.jar > deps.info

FROM alpine:3.10.3 as packager
RUN apk --no-cache add openjdk11-jdk openjdk11-jmods
ENV JAVA_MINIMAL="/opt/java-minimal"
COPY --from=builder application/deps.info deps.info
RUN /usr/lib/jvm/java-11-openjdk/bin/jlink \
    --verbose \
    --add-modules  $(cat deps.info) \
    --compress 2 --strip-debug --no-header-files --no-man-pages \
    --release-info="add:IMPLEMENTOR=radistao:IMPLEMENTOR_VERSION=radistao_JRE" \
    --output "$JAVA_MINIMAL"

FROM alpine:3.10.3
ENV JAVA_HOME=/opt/java-minimal
ENV PATH="$PATH:$JAVA_HOME/bin"
COPY --from=packager "$JAVA_HOME" "$JAVA_HOME"
COPY --from=builder application/*.jar app.jar
ENV EXCHANGE_API_KEY=""
ENV EXCHANGE_API_BASE="USD"
ENV GIPHY_API_KEY=""
ENTRYPOINT ["java", "-Dexchange-rate-api.api_key=${EXCHANGE_API_KEY}", "-Dexchange-rate-api.base_currency=${EXCHANGE_API_BASE}", "-Dgiphy-api.api_key=${GIPHY_API_KEY}", "-jar","/app.jar"]
