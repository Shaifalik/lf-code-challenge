package com.labforward.api.hello.controller;

import com.labforward.api.hello.domain.Greeting;
import com.labforward.api.hello.domain.GreetingDTO;
import com.labforward.api.hello.service.HelloWorldService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins="http://localhost:4200",allowedHeaders = "*")
@RestController
public class HelloController {

    @Autowired
    private ModelMapper modelMapper;
    private HelloWorldService helloWorldService;

    public HelloController(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    @GetMapping("/hello")
    public ResponseEntity<GreetingDTO> getDefaultGreeting() {
        return ResponseEntity.ok(modelMapper.map(helloWorldService.getDefaultGreeting(), GreetingDTO.class));
    }

    @GetMapping(value = "/hello/{id}")
    public ResponseEntity<GreetingDTO> getGreetingById(@PathVariable String id) {
        Greeting greeting = helloWorldService.getGreeting(id);
        return ResponseEntity.ok(modelMapper.map(greeting, GreetingDTO.class));
    }

    @PostMapping(value = "/hello/create")
    public ResponseEntity<GreetingDTO> newGreeting(@RequestBody GreetingDTO request) {
        Greeting greeting = modelMapper.map(request, Greeting.class);
        return new ResponseEntity<>(modelMapper.map(helloWorldService.createGreeting(greeting), GreetingDTO.class), HttpStatus.CREATED);
    }

    @PutMapping(value = "/hello/update/{id}")
    public ResponseEntity<GreetingDTO> replaceGreeting(@PathVariable String id, @RequestBody GreetingDTO request) {
        Greeting greeting = modelMapper.map(request, Greeting.class);
        return ResponseEntity.ok(modelMapper.map(helloWorldService.updateGreeting(id, greeting), GreetingDTO.class));
    }
}
