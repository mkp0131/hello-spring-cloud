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

## Spring Cloud Gateway