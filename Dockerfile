FROM openjdk:11
VOLUME /tmp
ADD build/libs/controle-financeiro-0.0.1-SNAPSHOT.jar controle-financeiro.jar
EXPOSE 80 443
ENTRYPOINT ["java","-jar","/controle-financeiro.jar"]