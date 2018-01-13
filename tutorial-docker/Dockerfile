FROM openjdk:8-jdk

RUN apt-get update && apt-get install -y curl

# Vertx cache and cluster config
ENV VERTX_OPTS -Dvertx.cacheDirBase=/tmp/

# Set the location of the verticles
ENV VERTICLE_HOME /usr/verticle

COPY ./target/build $VERTICLE_HOME
WORKDIR $VERTICLE_HOME

ENTRYPOINT ["sh","-c"]
CMD ["java \
        -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.Log4j2LogDelegateFactory \
        -Dglue.layers=./config/api-framework-1.0-SNAPSHOT-common-config.zip,./config/tutorial-simple-post-api-1.0-SNAPSHOT-common-config.zip,./config/tutorial-docker-1.0-SNAPSHOT-common-config.zip \
        -jar tutorial-docker-1.0-SNAPSHOT.jar"]

# Reference Config:
# 1. Debug enable
# 2. Vertx and Hazelcast JMX enable
# 3. G1 garbage collector
# 4. Set Vertx Logging to use Log4J
#
#===============================================================================
# NOTE:
# $RMI_PORT : RMI Port on which you want to access JMX. Map this port to Host 
#             Port also.
# $HOST : Host IP Address. IP Address of your machine, not, docker
#===============================================================================
#
#CMD ["java \
#        -Xmx1g \
#        -Xms1g \
#        -XX:+UseG1GC \
#        -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.Log4j2LogDelegateFactory \
#        -Dhazelcast.jmx=true \
#        -Dcom.sun.management.jmxremote \
#        -Dcom.sun.management.jmxremote.port=$RMI_PORT \
#        -Dcom.sun.management.jmxremote.rmi.port=$RMI_PORT \
#        -Djava.rmi.server.hostname=$HOST \
#        -Dcom.sun.management.jmxremote.ssl=false \
#        -Dcom.sun.management.jmxremote.authenticate=false \
#        -Dglue.layers=./config/api-framework-1.0-SNAPSHOT-common-config.zip,./config/tutorial-docker-1.0-SNAPSHOT-common-config.zip \
#        -agentlib:jdwp=transport=dt_socket,address=$DEBUG_PORT,server=y,suspend=n \
#        -jar tutorial-docker-1.0-SNAPSHOT.jar"]
