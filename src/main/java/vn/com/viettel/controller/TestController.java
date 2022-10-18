package vn.com.viettel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.viettel.core.dto.request.Jwt;
import vn.com.viettel.dto.JwtUser;
import vn.com.viettel.dto.ShowcaseDTO;
import vn.com.viettel.service.ShowcaseService;
import vn.com.viettel.utils.Utils;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class TestController {

    @Autowired
    private ShowcaseService showcaseService;

    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.status(HttpStatus.OK).body("Hello world!");
    }

    @GetMapping("/info")
    public ResponseEntity<String> getInfo() {
        return ResponseEntity.status(HttpStatus.OK).body("You have just logged in through secure URL!!");
    }

    @GetMapping("/showcase/info")
    public ResponseEntity<JwtUser> getAllShowcase(@AuthenticationPrincipal JwtUser jwtUser) {
        return ResponseEntity.status(HttpStatus.OK).body(jwtUser);
    }

}
