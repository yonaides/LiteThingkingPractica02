# Tarea Lite Thinking RETO 2.

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

Repositorio en GitHub: Organiza tu repositorio en GitHub de manera clara y comprensible.

Documentación de la API: Proporciona documentación clara sobre cómo utilizar tu API, incluyendo ejemplos de solicitudes y respuestas.

Postman: Proporciona una colección de Postman que permita probar los diferentes endpoints de la API.

Sugerencias y Buenas Prácticas
Utiliza principios RESTful en el diseño de tu API. ¿Cómo manejarías errores y excepciones en tu API?

¿Cómo estructurarías tu código para que sea mantenible y escalable?

¿Qué otras buenas prácticas considerarías al desarrollar la API?

