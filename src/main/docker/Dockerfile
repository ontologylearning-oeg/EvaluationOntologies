FROM tomcat:8-jre8
#MAINTAINER David

WORKDIR /usr/local/tomcat

ADD DrOntoEval.war /usr/local/tomcat/webapps/eval.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
