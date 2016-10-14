# EvaluationOntologies
Download or clone the project

Create a configuration.properties file following the jasypt instructions

http://www.jasypt.org/cli.html + http://www.jasypt.org/encrypting-configuration.html 


Edit the EncryptConnection.java file with the path of your configuration.properties file.

Do "mvn clean install" at the main folder

Deploy target/EvaluationOntologies.war into an Apache Tomcat

Create a database on MySQL with the name you put into the url property at the configuration.properties file.

Run your server ;)
