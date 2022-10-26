package vn.com.viettel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vn.com.viettel.core.rest.IApplication;

@SpringBootApplication
public class BackEndTemplateApplication implements IApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackEndTemplateApplication.class, args);
    }

}
