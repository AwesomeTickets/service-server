FROM maven:3.5.0

WORKDIR /service-server

RUN echo '\
<settings>\n \
    <localRepository>/service-server/maven-repository</localRepository>\n \
</settings>' \
> /usr/share/maven/conf/settings.xml

# Cache dependencies
COPY ./pom.xml .
RUN mvn clean test tomcat7:shutdown -DskipTests=true

COPY . .

EXPOSE 8000

CMD ["mvn", "clean", "tomcat7:run"]
