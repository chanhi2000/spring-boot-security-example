# `spring-boot-security-example`

---
## 🚀Quickstart

### What's included?

![shield-springboot][shield-springboot]
![shield-java][shield-java]
![shield-kotlin][shield-kotlin]
![shield-gradle][shield-gradle]

### 🧰Prerequisite(s)
- Intellij Idea CE
- Postman

---
## Sequence Diagram

### Basic Auth.

```mermaid
sequenceDiagram
    participant Client
    participant Backend
    Client->>+Backend: HTTP Request (GET)
    Backend->>Backend: Checks authorization
    Backend-->>-Client: Returns 401 
    Note over Backend,Client: UNAUTHORIZED
    Client->>+Backend: HTTP Request (GET) => Basic64 username:password
    Backend->>Backend: Checks username:password
    Backend-->>-Client: Responds: 200
    Note over Backend,Client: OK
```

### JWT Auth.

```mermaid
sequenceDiagram
    participant Client
    participant JwtAuthFilter
    participant UserDetailsService
    participant UserDao
    participant SecurityContextHolder
    participant GreetingController
    par Backend / API
        Client->>JwtAuthFilter: GET /api/v1/greetings
        JwtAuthFilter->>+JwtAuthFilter: Check if the JWT exists
        JwtAuthFilter->>-UserDetailsService: Ask for user information
        UserDetailsService->>UserDao: Fetch user information
        UserDao->>UserDetailsService: Sends back user details
        UserDetailsService->>JwtAuthFilter: Receives user details
        JwtAuthFilter->>+JwtAuthFilter: Compare / validate JWT data w/ the current user
        JwtAuthFilter->>-SecurityContextHolder: Update the SecurityContextHolder and call the filterChain
        SecurityContextHolder->>+SecurityContextHolder: Set the user as authenticated
        SecurityContextHolder->>-SecurityContextHolder: Forward the request to DispatchServlet
        SecurityContextHolder->>GreetingController: sayHello()
        GreetingController->>Client: Returns: "Hello from our API"
    end
```
---
## `curl` 호출 테스트

### Basic Auth.

#### ⚠️`401` Unauthorized

_로그인 인증 없이_ 호출 했을 시 `401 Unauthorized` 응답결과 나오는지 확인

```sh
curl http://localhost:8080/api/v1/greetings
```

#### ✅ `200` Ok

로그인 인증결과로 Basic Auth처리 후 호출 했을 시 `200 Ok` 응답결과가 나오는지 확인

(예를들어) 인증결과는 스프링 부트 후 로그에 아래와 같이 기록된다.
```
...
Using generated security password: 7dd436e6-1c3d-4dde-b09a-cb648b1fa400
...
```

```sh
curl -u "user:7dd436e6-1c3d-4dde-b09a-cb648b1fa400" \
    http://localhost:8080/api/v1/greetings
```

### JWT Auth.

#### JWT인증토큰 발행

인증정보가 유효한 사용자 정보를 인자로 넣어 요청

```sh
curl -s -X POST -H 'Accept: application/json' \
    -H "Content-Type: application/json" \
    --data '{"email": "bouali.social@gmail.com", "password": "password"}' \
    http://localhost:8080/api/v1/auth/authenticate
```

(예를 들면) 결과는 아래와 같이 암호화 된 문자열로 나온다.
```
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJib3VhbGkuc29jaWFsQGdtYWlsLmNvbSIsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn1dLCJpYXQiOjE2NzA0MDY2NjgsImV4cCI6MTY3MDQ5MzA2OH0.4Hpq0xWPMoodJeu9wc7zOIPlp_Xw8xD3S79d8gOdjNs
```

#### JWT토큰을 활용하여 API 요청

```sh
curl -H "Accept: application/json" \
    -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJib3VhbGkuc29jaWFsQGdtYWlsLmNvbSIsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn1dLCJpYXQiOjE2NzA0MDY2NjgsImV4cCI6MTY3MDQ5MzA2OH0.4Hpq0xWPMoodJeu9wc7zOIPlp_Xw8xD3S79d8gOdjNs" \
    http://localhost:8080/api/v1/greetings

curl -H "Accept: application/json" \
    -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJib3VhbGkuc29jaWFsQGdtYWlsLmNvbSIsImF1dGhvcml0aWVzIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn1dLCJpYXQiOjE2NzA0MDY2NjgsImV4cCI6MTY3MDQ5MzA2OH0.4Hpq0xWPMoodJeu9wc7zOIPlp_Xw8xD3S79d8gOdjNs" \
    http://localhost:8080/api/v1/greetings/say-goodbye    
```

---
## ✅Task(s) Completed

- [x] ~~initialize project~~
- [x] ~~Bootstrap app~~
- [x] ~~Create API~~
- [x] ~~Install Spring Security~~
- [x] ~~Implement basic authentication~~
- [x] ~~Implement JWT authentication filter~~
- [x] ~~Implement JWT utils class and finalize the filter~~
- [x] ~~Tell Spring to use the JWT filter~~
- [x] ~~Implement `userDetailsService`~~
- [x] ~~Add the `authenticationProvider` bean~~
- [x] ~~Add session management creation policy~~
- [x] ~~Implement Authentication controller~~
- [x] ~~Improve the code~~
- [x] ~~Testing the application~~

---
## 📚References

- [Spring Security Tutorial - [NEW] [2022] | Amigoscode][youtube-tut]
- [jwt Official Site](https://jwt.io)


[shield-springboot]: https://img.shields.io/badge/springboot-2.7.6-6DB33F?logo=springboot&logoColor=6DB33F&style=flat-square
[shield-java]: https://img.shields.io/badge/Java-17-f3812a?logo=java&logoColor=f3812a&style=flat-square
[shield-kotlin]: https://img.shields.io/badge/Kotlin-1.6.21-0095D5?logo=kotlin&logoColor=0095D5&style=flat-square
[shield-gradle]: https://img.shields.io/badge/Gradle-7.5.1-abd759?logo=gradle&logoColor=abd759&style=flat-square
[youtube-tut]: https://www.youtube.com/watch?v=b9O9NI-RJ3o
