package vn.com.viettel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.com.viettel.core.log.AppLog;

import javax.annotation.PostConstruct;

@Component
public class StaticContextInitializer {

    @Value("${app.code:N/A}")
    private String appCode;

    @Value("${app.service.code:N/A}")
    private String serviceCode;

    @PostConstruct
    public void init() {
        AppLog.setAppLogInfo(appCode, serviceCode);
    }
}
