# ── Stage 1: Build the WAR ────────────────────────────────────────────────────
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# ── Stage 2: Run on Tomcat 11 ─────────────────────────────────────────────────
FROM tomcat:11.0-jdk21-temurin

# Remove default Tomcat webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy built WAR as ROOT so the app is served at /
COPY --from=builder /app/target/Hostel_Finder-1.0-SNAPSHOT.war \
     /usr/local/tomcat/webapps/ROOT.war

# Script to replace Tomcat's port with Railway's $PORT at startup
RUN echo '#!/bin/bash\n\
PORT=${PORT:-8080}\n\
sed -i "s/port=\"8080\"/port=\"$PORT\"/" /usr/local/tomcat/conf/server.xml\n\
exec catalina.sh run' > /usr/local/bin/start.sh && chmod +x /usr/local/bin/start.sh

EXPOSE 8080

CMD ["/usr/local/bin/start.sh"]