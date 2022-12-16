package vn.com.viettel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import vn.com.viettel.core.config.EnableJasypt;

@SpringBootApplication(scanBasePackages = {"vn.com.viettel"})
@EnableJasypt(key = "VTS!@#GPDN@qaz2022")
@EnableFeignClients(basePackages = {"vn.com.viettel"})
public class BackEndTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackEndTemplateApplication.class, args);
    }

}
