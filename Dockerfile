FROM java:8

ENV JAR_FILE lite-customer-service-1.1.jar
ENV CONFIG_FILE /conf/customer-service-config.yaml

WORKDIR /opt/lite-customer-service

COPY build/libs/$JAR_FILE /opt/lite-customer-service

CMD java "-jar" $JAR_FILE "server" $CONFIG_FILE
