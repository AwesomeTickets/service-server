FROM maven:3.5.0

WORKDIR /service-server

RUN echo ' \
<settings>\n \
    <mirrors>\n \
        <mirror>\n \
            <id>alimaven</id>\n \
            <name>aliyun maven</name>\n \
            <url>http://maven.aliyun.com/nexus/content/groups/public/</url>\n \
            <mirrorOf>central</mirrorOf>\n \
        </mirror>\n \
    </mirrors>\n \
    <localRepository>/service-server/maven-repository</localRepository>\n \
</settings>' \
> /usr/share/maven/conf/settings.xml

# Cache dependencies
COPY ./pom.xml .
RUN mvn clean test tomcat7:shutdown -DskipTests=true

COPY . .

EXPOSE 8000

CMD ["mvn", "clean", "tomcat7:run"]
