# API de Gestión de Vehículos y Marcas

Este proyecto es una API RESTful desarrollada con **Spring Boot** que simula un sistema de gestión para un concesionario. Permite administrar vehículos y marcas, con características avanzadas como autenticación JWT, subida de imágenes, exportación/importación de datos en formato CSV, y documentación con Swagger.


## Características Principales

La API de Gestión de Vehículos y Marcas permite:

- **Gestión de Vehículos**: crear, actualizar, leer y eliminar información sobre vehículos.
- **Gestión de Marcas**: crear, actualizar, leer y eliminar información sobre marcas de vehículos.
- **Asociación**: vincular vehículos con sus respectivas marcas.
- **Seguridad**: autenticación de usuarios mediante JWT y autorización de operaciones basada en roles usando Spring Security.
- **Operaciones Asíncronas**: operaciones masivas para la carga y gestión eficiente de datos.
- **Gestión de Archivos**: subida de imágenes asociadas a usuarios y exportación e importación de datos en formato CSV.
- **Testing**: amplia cobertura de pruebas con Mockito y JUnit.


## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3**
- **MySQL** (contenedor Docker)
- **Spring Data JPA**
- **Spring Security**
- **Lombok**
- **Docker**
- **Swagger (SpringDoc OpenAPI)**
- **Commons CSV**


## Estructura del proyecto

La aplicación está organizada en paquetes siguiendo las mejores prácticas de arquitectura en capas:

- **config**: configuración global de la aplicación.
- **controller**: controladores REST.
- **dtos**: objetos de transferencia de datos.
- **mappers/converters**: transformación entre entidades y DTOs.
- **entitys**: entidades JPA para la base de datos.
- **exception**: excepciones personalizadas.
- **filter**: filtros de seguridad, como interceptores JWT.
- **model**: modelos de negocio.
- **repositorys**: interfaces de repositorios JPA.
- **services**: lógica de negocio.
- **testing**: paquetes paralelos con pruebas unitarias e integración.


## Dependencias Principales

Las principales dependencias utilizadas en este proyecto incluyen:

- **Spring Boot Starter Web**: construcción de la API REST.
- **Spring Boot Starter Data JPA**: gestión de la persistencia de datos.
- **Spring Boot Starter Security**: implementación de seguridad.
- **JJWT**: manejo de autenticación JWT.
- **SpringDoc OpenAPI**: documentación de la API con Swagger UI.
- **Lombok**: reducción de código repetitivo.
- **MySQL Connector**: driver para la base de datos.
- **Apache Commons CSV**: procesamiento de archivos CSV.


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


