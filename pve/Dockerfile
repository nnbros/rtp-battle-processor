FROM openjdk:17-alpine
ARG BOT_TOKEN
ENV RTP_BOT_TOKEN=${BOT_TOKEN}
COPY ./pve/target/*.jar /app/rtp-pve.jar
COPY ./battle-processor-core/target/scripts /app/scripts
RUN chmod 777 /app/rtp-pve.jar
ENTRYPOINT ["java", "-jar", "/app/rtp-pve.jar"]
