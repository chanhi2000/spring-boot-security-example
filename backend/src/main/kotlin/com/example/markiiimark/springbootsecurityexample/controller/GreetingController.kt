package com.example.markiiimark.springbootsecurityexample.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/greetings")
class GreetingController {

    @GetMapping
    fun sayHello(): ResponseEntity<String> =
        ResponseEntity.ok("Hello from our API")

    @GetMapping("/say-goodbye")
    fun sayGoodbye(): ResponseEntity<String> =
        ResponseEntity.ok("Good bye and see you later")
}