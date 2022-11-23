package vn.com.viettel.controller;

import org.apache.log4j.Logger;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class TestController {

    private static final Logger LOGGER = Logger.getLogger(TestController.class);

    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.status(HttpStatus.OK).body("Hello world!");
    }

    @GetMapping("/info")
    public ResponseEntity<String> getInfo() {
        return ResponseEntity.status(HttpStatus.OK).body("You have just logged in through secure URL!!");
    }

}
