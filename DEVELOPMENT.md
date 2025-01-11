# CODING SHUTTLE - UBER APP DEVELOPMENT SUMMARY


## Development Env requirement
- IDE : [Intellij Idea](https://www.jetbrains.com/idea/download/)
- [Spring initializer](https://start.spring.io/)

## Import project on Intellij
- Run Project

## application.properties
- debug=true
- server.port=9000
- [Document](https://docs.spring.io/spring-boot/appendix/application-properties/index.html)

### Beans (Every thing is a bean in springboot application) [link](https://app.codingshuttle.com/classroom/spring-boot-0-to-1---fundamentals_4/w/introduction-to-spring--spring-boot_23/lecture/beans_756)
- Car obj = new Car(); // Java POJO
- This is instantiated, assembled and managed bu Spring IoC Container.
- Traditionally, Spring allows a developer to manage bean dependencies by using XML-based configuration.
  - Now we use Spring Annotations (Every annotation extends from Component)
    - Using Stereotype Annotations
      - @Component
      - @Service
      - @Repository
      - @Controller
    - Explicit Bean Declaration in configuration Class
      - @Configuration
          ```java
              @configuration
              public class AppConfig{
                    
                    @Bean
                    Apple getApple(){
                       return new Apple();
                    }
                    
              }
          ```
      - Bean Lifecycle
        - Bean Creation : .The bean instance is created by invoking a static factory method or an instance factory method(for annotation-based configuration)
        - Dependency Injection : Spring sets the bean's properties and dependencies either through setter injection, constructor injection, or field injection.
        - Bean Initialized : If the bean implements the InitializingBean interface or defines a custom initialization method annotated with @PostConstruct, Spring invokes the initialization method after the bean has configured.
        - Bean is Used : The bean is now fully initialized and ready to be used by the application.
        - Bean Destroyed : Spring invokes the destruction method when the bean is no longer needed or when the application context is being shut down.