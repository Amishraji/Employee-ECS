FROM openjdk:17
EXPOSE 5000
ADD target/Crud-Employee.jar Crud-Employee.jar
ENTRYPOINT [ "java","-jar","/Crud-Employee.jar"]