# API de Gestión de Vehículos y Marcas

Este proyecto es una API RESTful desarrollada en Spring Boot para gestionar información de vehículos y sus marcas. La aplicación permite realizar operaciones CRUD sobre vehículos y marcas, facilitando así la administración de estos elementos de forma eficiente y escalable.


## Descripción

La API de Gestión de Vehículos y Marcas permite:

- **Gestión de Vehículos**: crear, actualizar, leer y eliminar información sobre vehículos.
- **Gestión de Marcas**: crear, actualizar, leer y eliminar información sobre marcas de vehículos.
- **Asociación**: vincular vehículos con sus respectivas marcas.
- **Consulta**: obtener información detallada sobre vehículos y marcas disponibles.


## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3**
- **MySQL** en contenedor Docker
- **Spring Data JPA** para la gestión de entidades
- **Lombok** para simplificación de código
- **Docker** para el contenedor de base de datos


## Dependencias Principales

- **Spring Boot Starter Web**: para construir la API REST.
- **Spring Boot Starter Data JPA**: para la integración con JPA y la persistencia de datos.
- **SpringDoc OpenAPI**: para la documentación de la API con Swagger UI.
- **Lombok**: para reducir el boilerplate y facilitar la creación de entidades.
- **MySQL Connector**: driver para conectarse a la base de datos MySQL.


## Configuración Inicial

### 1. Clonar el Repositorio

        git clone https://github.com/sauliyo15/carregistry.git

### 2. Configuración de la Base de Datos en Docker

Inicia un contenedor de MySQL con Docker, incluyendo una base de datos inicial:

        docker run --name vehiculos_mysql 
            -e MYSQL_ROOT_PASSWORD=password 
            -e MYSQL_DATABASE=vehiculos_db
            -e MYSQL_USER=user
            -e MYSQL_PASSWORD=password
            -p 3306:3306 -d mysql:latest

### 3. Configurar el archivo ``application.properties``

En el archivo ``src/main/resources/application.properties``, define las propiedades de conexión a la base de datos:

        spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
        spring.datasource.url=jdbc:mysql://localhost:3306/vehiculos_db
        spring.datasource.username=user
        spring.datasource.password=password
        spring.jpa.show-sql=true
        spring.jpa.hibernate.ddl-auto=update
        spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect


