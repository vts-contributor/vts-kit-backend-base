package vn.com.viettel.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class TestController {

    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.status(HttpStatus.OK).body("Hello world!");
    }

}
