FROM frolvlad/alpine-oraclejdk8:slim

ENV SPRING_PROFILES_ACTIVE ${SPRING_PROFILES_ACTIVE:-docker}
ENV JAVA_OPTS ${JAVA_OPTS:-'-Xmx512m'}
ENV DEBUG_OPTS ${DEBUG_OPTS}
ENV PORT ${PORT:-8761}

RUN adduser -D -g '' java

ADD *.jar /app.jar

VOLUME /tmp

RUN sh -c 'touch /app.jar'

USER java

EXPOSE ${PORT}

CMD java ${JAVA_OPTS} ${DEBUG_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.jar