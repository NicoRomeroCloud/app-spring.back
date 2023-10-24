# app-spring-back

Este es el repositorio de la aplicación Spring Boot "app-spring-back" realizada para cumplir con la pruba dada por Azurian. 
La aplicación se conecta a una base de datos PostgreSQL.

A continuación, se proporcionan las instrucciones para crear y configurar la conexión a la base de datos.

## Configuración de la Base de Datos

La aplicación utiliza Spring Data JPA para conectarse a una base de datos PostgreSQL. 

Luego, sigue estos pasos para configurar la conexión a la base de datos:

1. Abre el archivo de configuración de la base de datos application.properties.

2. Agrega la siguiente configuración al archivo:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/prueba_azurian
spring.datasource.username=postgres
spring.datasource.password=nico1234
