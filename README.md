# Tarea Lite Thinking RETO 2.
# Documentación de la API

## Master en microservicios
## Tareas: Diseño de la API - Entidades del Negocio
### ¿Cómo manejarías la generación y validación de tokens JWT?

#### Utilizo las siguientes clases para manejar el Token:

- JwtAuthenticationFilter
- AuthenticationService
- JwtService
- UserService

#### Y para la configuración y validación de permisos:

- SecurityConfiguration
- ApplicationConfiguration

### Define las entidades principales relacionadas con tu negocio simulado. ¿Cómo modelarías las relaciones entre las entidades?
Estoy utilizando las entidades 

- Tutoriales
- Tags

Entre relación seria de mucho a mucho

### Operaciones CRUD: Define las operaciones CRUD necesarias para gestionar las entidades. ¿Qué endpoints y métodos HTTP usarías para cada operación?


| Tutorial                | Tags                              |
|-------------------------|-----------------------------------|
| Creacion                | Creación                          |
| Editar                  | Editar                            |
| Eliminar                | Eliminar                          |
| Consultar [Todos o Uno] | Consultar [Todos]                 |
|                         | Consultar [ Tags de un Tutorial ] |
|                         |                                   |                                        


### Seguridad con JWT: ¿En qué rutas de tu API implementarías la seguridad con JWT?

Las rutas permitidas sin validación sera"/auth/**"
Todas las demas rutas necesitan una validación de token.

``` shell
 /**
     * 
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        (authorizationManagerRequestMatcherRegistry)
                                -> authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/auth/**")
                                .permitAll().anyRequest().authenticated())
                .sessionManagement(httpSecuritySessionManagementConfigurer
                        -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

```

## Desarrollo de Endpoints - Implementación de Endpoints.
### Implementa los endpoints necesarios para realizar las operaciones CRUD. Utiliza métodos como GET, POST, PUT, PATCH, DELETE según sea necesario.

- Entidad - Tutorial
```python
@GetMapping("/tutorial")
public ResponseEntity<Object> getAllTutorials(@RequestParam(required = false) String title)

@GetMapping("/tutorial/{id}")
public ResponseEntity<Object> getTutorialById(@PathVariable("id") long id)

@PostMapping("/tutorial")
public ResponseEntity<Object> createTutorial(@RequestBody Tutorial tutorial)

@PutMapping("/tutorial/{id}")
public ResponseEntity<Object> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial)

@DeleteMapping("/tutorial/{id}")
public ResponseEntity<Object> deleteTutorial(@PathVariable("id") long id)

@DeleteMapping("/tutorial")
public ResponseEntity<Object> deleteAllTutorials()
    
@GetMapping("/tutorial/published")
public ResponseEntity<Object> findByPublished()    
```

- Entidad - Tags
```python
@GetMapping("/tags")
public ResponseEntity<Object> getAllTags()

@GetMapping("/tutorials/{tutorialId}/tags")
public ResponseEntity<Object> getAllTagsByTutorialId(@PathVariable(value = "tutorialId") Long tutorialId)

@GetMapping("/tags/{id}")
public ResponseEntity<Object> getTagsById(@PathVariable(value = "id") Long id)

@GetMapping("/tags/{tagId}/tutorials")
public ResponseEntity<Object> getAllTutorialsByTagId(@PathVariable(value = "tagId") Long tagId)

@PostMapping("/tutorials/{tutorialId}/tags")
public ResponseEntity<Object> addTag(@PathVariable(value = "tutorialId") Long tutorialId, @RequestBody Tag tagRequest)

@PutMapping("/tags/{id}")
public ResponseEntity<Object> updateTag(@PathVariable("id") long id, @RequestBody Tag tagRequest)

@DeleteMapping("/tutorials/{tutorialId}/tags/{tagId}")
public ResponseEntity<Object> deleteTagFromTutorial(@PathVariable(value = "tutorialId") Long tutorialId, @PathVariable(value = "tagId") Long tagId)

@DeleteMapping("/tags/{id}")
public ResponseEntity<Object> deleteTag(@PathVariable("id") long id)

```

Desarrollo de Endpoints - Implementación de Endpoints
Implementa los endpoints necesarios para realizar las operaciones CRUD. Utiliza métodos como GET, POST, PUT, PATCH, DELETE según sea necesario.

Seguridad con JWT (Implementación) Incorpora la generación y validación de tokens JWT en las rutas seleccionadas. ¿Cómo manejarías la autorización basada en roles usando JWT?

Base de Datos: ¿Cómo implementarías la persistencia de datos?

¿Considerarías el uso de contenedores para la base de datos?
Si cierta forma si, incluso dejo un ejemplo para subir uno

```sh
docker run -d -e MYSQL_ROOT_PASSWORD=secret -e MYSQL_DATABASE=taskdb --name mysqldb -p 3307:3306 mysql:8.0
```
Postman: Proporciona una colección de Postman que permita probar los diferentes endpoints de la API.

```sh
// Crear usuario
POST: http://localhost:8005/auth/signup
{
  "email":"yonai@hotmail.com",
  "password":"7777",
  "fullName":"Yonaides tavares"
}

// Logear del usuario
POAST: http://localhost:8005/auth/login
{
  "email":"yonai@hotmail.com",
  "password":"7777"
}

// Crear Tutorial
POST: http://localhost:8005/api/tutorial
{
  "title":"titulo del tutorial 1",
  "description":"Tremendo tutorial exp",
 "published": false
}

//Crear Tags
POST: http://localhost:8005/api/tutorial/1/tags
{
  "name":"nuevo tag"
}

// Editar Tutorial
PUT: http://localhost:8005/api/tutorial/1
{
  "title":"titulo del tutorial 1",
  "description":"Tremendo tutorial",
  "published": false
}

//Listar todos los tutoriales
GET: http://localhost:8005/api/tutorial
{
  "data": [
    {
      "id": 1,
      "title": "titulo del tutorial 1",
      "description": "Tremendo tutorial exp",
      "published": false,
      "tags": [
        {
          "id": 1,
          "name": "Java EE 9"
        },
        {
          "id": 2,
          "name": "Spring Boot"
        }
      ],
      "createdDate": "2024-01-16T11:48:50.478+00:00",
      "modifiedDate": "2024-01-16T11:48:50.478+00:00"
    }
  ],
  "message": "listado de tutoriales",
  "status": 200
}

// Listar Taga
GET: http://localhost:8005/api/tags

{
  "data": [
    {
      "id": 1,
      "name": "Java EE 9"
    },
    {
      "id": 2,
      "name": "Spring Boot"
    },
    {
      "id": 3,
      "name": "Super nuevo"
    },
    {
      "id": 4,
      "name": "Java Context"
    },
    {
      "id": 5,
      "name": "Java Context"
    },
    {
      "id": 6,
      "name": "Java mix "
    }    
  ],
  "message": "listado de tags ",
  "status": 200
}

// Borrar tutoriales
DELETE: http://localhost:8005/api/tutorial/1

//Borrar Tags
DELETE: http://localhost:8005/api/tags/1

```

## ¿Cómo manejarías errores y excepciones en tu API?
```python
// Utilizando una clase global
@ControllerAdvice
public class GlobalExceptionHandler  {

    /**
     * 
     * @param exception
     * @return
     */
    @ExceptionHandler({TutorialNotFoundException.class})
    public ResponseEntity<Object> handleTutorialNotFoundException(TutorialNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    /**
     * 
     * @param exception
     * @return
     */
    @ExceptionHandler({RuntimeExceptionError.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    /**
     * 
     * @param exception
     * @return
     */
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(TutorialNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

}

```
### ¿Cómo estructurarías tu código para que sea mantenible y escalable?
Modulando el codigo y creando paquetes para separar las funciones.  

### ¿Qué otras buenas prácticas considerarías al desarrollar la API?
Utilizando librerias como Lombok, modelmapper [para el mapeo de entitades] y spring-boot-starter-validation [para validar las entidades] 