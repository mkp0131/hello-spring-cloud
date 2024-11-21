# Spring Cloud

## Eureka

### Eureka Server 설정

1. 유레카 서버로 설치하여 실행

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

2. `application.yml` 파일에 유레카 서버 설정 추가

```yaml
spring:
  application:
    name: discovery-service

server:
  port: 8761

eureka:
  client:
    register-with-eureka: false # 서버 자신을 Eureka 서버로 등록하지 않음
    fetch-registry: false # 서버 자신을 Eureka 서버로 등록하지 않음
```

3. main 클래스에 `@EnableEurekaServer` 어노테이션 추가

```java
@SpringBootApplication
@EnableEurekaServer
public class EcommerceApplication {
	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}
}
```

> 자동으로 actuator 가 설치된다!

### Eureka client 설정

1. 클라이언트 프로젝트 main 클래스에 `@EnableDiscoveryClient` 어노테이션 추가

```java
@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
```

2. `application.yml` 파일에 유레카 클라이언트 설정 추가

```yaml
spring:
  application:
    name: user-service

server:
  port: 0 # 랜덤 포트 설정

eureka:
  instance:
    instance-id: ${spring.application.name}:${spring.application.instance_id:${random.value}}
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://127.0.0.1:8761/eureka
```

## Spring Cloud Config

## [Spring cloud] Spring Cloud Gateway

### 의존성 설치

```
Spring Boot DevTools
Spring Cloud Routing Gateway
Eureka Discovery Client
```

> spring-cloud-starter-gateway-mvc 가 아닌 spring-cloud-starter-gateway 를 설치하도록 한다!

### 설정

설정방식에는 2가지가있다 `application.yml` 파일에 설정하는 방식과 `config/FilterConfig.java` 파일에 설정하는 방식이 있다.

- application.yml에서는 직관적으로 필요한 Filter를 선언할 수 있는 반면에 Java 프로그래밍을 통해서는 프로그리밍 처리에 따른 다양 작업을 함께 할 수 있다는 장점, Java 프로그래밍으로 처리한 후에는 컴파일을 거쳐 빌드해야 반영될 수 있다는 점

#### 1. Filter 설정

- filter 설정 `config/FilterConfig.java` 파일 생성
- filter 설정이 더 좋은 것 같다!
- 라우트 별로 filter 설정말고도 글로벌로도 설정이 가능하다. (Global Filter)

```java
@Configuration
public class FilterConfig {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/first-service/**")
                        .filters(f -> f.addRequestHeader("first-request", "first-request-header")
                                .addRequestHeader("first-response", "first-response-header"))
                        .uri("http://localhost:8081"))
                .route(r -> r.path("/second-service/**")
                        .filters(f -> f.addRequestHeader("second-request", "second-request-header")
                                .addRequestHeader("second-response", "second-response-header"))
                        .uri("http://localhost:8082"))
                .build();
    }
}
```


#### 2. application.yml 파일에 설정

```yaml
spring:
  cloud:
    gateway:
      mvc:
        routes:
        - id: user-service
          uri: http://localhost:8081
          predicates:
          - Path=/user/**
          filters:
          - AddRequestHeader=first-request, first-request-header
          - AddResponseHeader=first-response, first-response-header
        - id: product-service
          uri: http://localhost:8082
          predicates:
          - Path=/product/**
          filters:
          - AddRequestHeader=second-request, second-request-header
          - AddResponseHeader=second-response, second-response-header
```

### Eureka 와 연동

- `application.yml` 파일에 설정 추가
- `lb://{서비스 이름}` 으로 서비스 이름을 사용할 수 있다.
- 주의할점!!: Path=/user/ 로 요청시 해당 서비스에서도 /user/ 로 요청되어야 한다.

> 같은 서비스이름으로 다중으로 등록시, 라운드로빈으로 LB 처리된다.

```yaml
spring:
  application:
    name: gateway-service
  cloud:
    gateway:
      routes:
      - id: user-service
        uri: lb://USER-SERVICE
        predicates:
          - Path=/user/**

eureka:
  client:
    register-with-eureka: true # 기본값 true  
    fetch-registry: true # 기본값 true
    service-url:
      defaultZone: http://127.0.0.1:8761/eureka
```

#### register-with-eureka: false: 게이트웨이가 자신을 Eureka 서버에 등록하지  않음

##### true 장점

- 다른 서비스에서 게이트웨이를 찾을 수 있음
- 서비스 간 내부 통신에서 게이트웨이를 통한 라우팅 가능
- 로드밸런싱이나 서킷브레이커 등의 기능 활용 가능

#### false 장점

- 단순한 구조 유지
- 불필요한 서비스 등록 방지
- 외부 요청 처리에만 집중


## [spring] spring cloud 스프링 클라우드 actuator 적용

스프링 클라우드의 클라이언트들에는 actuator 가 기본적으로 포함되어있지 않다. 설치를 따로 해주자!

- 의존성 추가

```xml
<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-actuator -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
  <version>3.3.5</version>
</dependency>
```

- `application.yml` 파일에 설정 추가

```yaml
eureka:
  instance:
    instance-id: ${spring.application.name}:${spring.application.instance_id:${random.value}}
    health-check-url-path: /actuator/health # Actuator의 health 정보를 유레카에 전달
    status-page-url-path: /actuator/info # Actuator의 info 정보를 유레카에 전달
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://127.0.0.1:8761/eureka
    healthcheck:
      enabled: true    # Actuator의 health 정보를 유레카에 전달
```

