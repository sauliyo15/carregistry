# API de Gestión de Vehículos y Marcas

Este proyecto es una API RESTful desarrollada con **Spring Boot** que simula un sistema de gestión para un concesionario. Permite administrar vehículos y marcas, con características avanzadas como autenticación JWT, subida de imágenes, exportación/importación de datos en formato CSV, y documentación con Swagger.


## Características Principales

La API de Gestión de Vehículos y Marcas permite:

- **Gestión de Vehículos**: crear, actualizar, leer y eliminar información sobre vehículos.
- **Gestión de Marcas**: crear, actualizar, leer y eliminar información sobre marcas de vehículos.
- **Asociación**: vincular vehículos con sus respectivas marcas.
- **Registro de usuarios**: registro de usuarios en el la base de datos
- **Seguridad**: login y autenticación de usuarios mediante JWT y autorización de operaciones basada en roles usando Spring Security.
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


    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>

    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <scope>provided</scope>
    </dependency>

    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.2.0</version>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>spring-security-test</artifactId>
        <scope>test</scope>
    </dependency>

    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.11.5</version>
    </dependency>

    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.11.5</version>
    </dependency>

    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.11.5</version>
    </dependency>

    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-csv</artifactId>
        <version>1.8</version>
    </dependency>


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

    token.secret.key=MTc1MDQxOWEwZjUxNTAxNjBkYWRhM2QxZmU4NmIzY2QwM2M3N2FiNjgyMTI5NjE0N2I0NDdiZmY2ZjQ0NWNkZDU1ZmY5NzNjMTRlYTc4NGJkZDE5YTMxMDcxMTU4NWFiZjc2Mzk2NmZmYTc4NGYyOGM2YTc2MGQxMjk5NjY1ZmE=
    token.expirationms=3600000

### 4. Levantar la Aplicación

Inicia la aplicación con:

    mvn spring-boot:run


## Documentación de la API

La API está documentada utilizando **Swagger** y **OpenAPI**. Swagger proporciona una interfaz interactiva que permite a los desarrolladores explorar y probar los endpoints de la API de manera fácil y rápida.

Accede a la documentación de la API en:
http://localhost:8080/swagger-ui/index.html

### Características de la Documentación

- **Visualización Interactiva**: puedes ver todos los endpoints disponibles y probarlos directamente desde la interfaz.
- **Ejemplos de Solicitud y Respuesta**: cada endpoint incluye ejemplos de datos para ayudar a entender cómo interactuar con la API.
- **Descripción Detallada**: la documentación incluye descripciones completas de cada endpoint, parámetros, respuestas y errores posibles.`


## Testing

El proyecto incluye pruebas de **Servicios**, **Controladores** y **Mappers/Converters** usando **Mockito** y **JUnit**

Ejecuta las pruebas con el siguiente comando:

    mvn test


## Funcionalidades Adicionales

### Manejo de Archivos CSV

- **Carga**: subida de datos masivos desde un archivo CSV.
- **Descarga**: generación de un archivo CSV con los datos almacenados.

### Gestión de Imágenes

- **Subida**: almacena imágenes en la base de datos en formato Base64.
- **Descarga**: recupera y devuelve imágenes asociadas a un usuario.

### Procesos Asíncronos

El proyecto utiliza métodos anotados con ``@Async`` para operaciones intensivas, como inserción masiva de datos, asegurando así un rendimiento óptimo y no bloqueante.


## Contribuir

Si quieres contribuir al proyecto, sigue estos pasos:

1. Crea un nuevo branch para tu funcionalidad:

        git checkout -b nueva-funcionalidad

2. Realiza tus cambios y haz commit:

        git commit -m "Descripción de los cambios"

3. Envía los cambios a tu repositorio remoto:

        git push origin nueva-funcionalidad

4. Crea un Pull Request y explica tus cambios.


## Licencia

**MIT License**

**Copyright (c) [2024] [Saúl García Calvo]**

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.