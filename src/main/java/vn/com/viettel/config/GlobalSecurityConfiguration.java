package vn.com.viettel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class GlobalSecurityConfiguration {

    @Value("${common.ip.restricted.url}")
    String ipRestrictedUrl;

    @Value("${common.permission.ignore.url}")
    String permissionIgnoreUrl;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors().and()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(
                        HttpMethod.GET,
                        "/v2/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui.html**",
                        "/webjars/**",
                        "/swagger-ui/**",
                        "/favicon.ico"
                ).permitAll();

        // Cau hinh restricted IP via URL
        String strUrlIpRestricted = ipRestrictedUrl;
        if (strUrlIpRestricted.trim().length() > 0) {
            String[] arrUrlIpRestricted = strUrlIpRestricted.split(";");
            if (arrUrlIpRestricted.length > 0) {
                for (String string : arrUrlIpRestricted) {
                    String[] strConf = string.trim().split("\\|");
                    if (strConf.length > 1) {
                        String strUrl = strConf[0].trim();
                        String strIp = strConf[1].trim();
                        if (strUrl.length() > 0 && strIp.length() > 0) {
                            String[] arrIP = strIp.split(",");
                            StringBuilder hasIpAddress = new StringBuilder();
                            for (String ip : arrIP) {
                                hasIpAddress.append("hasIpAddress('").append(ip).append("')").append(" or ");
                            }
                            http.authorizeRequests().antMatchers(strUrl).access(hasIpAddress.toString());
                        }
                    }
                }
            }
        }

        // Cau hinh ignore URL bypass authentication
        String strPermission = permissionIgnoreUrl;
        if (strPermission.trim().length() > 0) {
            String[] arrLinkIgnore = strPermission.split(";");
            if (arrLinkIgnore.length > 0) {
                for (String string : arrLinkIgnore) {
                    String strLinkIg = string.trim();
                    String[] strSleep = strLinkIg.split(":");
                    if (strSleep.length > 1) {
                        String strMethod = strSleep[0].trim();
                        String strUrl = strSleep[1].trim();
                        if (strMethod.length() > 0 && strUrl.length() > 0) {
                            switch (strMethod.toLowerCase()) {
                                case "get":
                                    http.authorizeRequests().antMatchers(HttpMethod.GET, strUrl).permitAll();
                                    break;
                                case "head":
                                    http.authorizeRequests().antMatchers(HttpMethod.HEAD, strUrl).permitAll();
                                    break;
                                case "post":
                                    http.authorizeRequests().antMatchers(HttpMethod.POST, strUrl).permitAll();
                                    break;
                                case "put":
                                    http.authorizeRequests().antMatchers(HttpMethod.PUT, strUrl).permitAll();
                                    break;
                                case "patch":
                                    http.authorizeRequests().antMatchers(HttpMethod.PATCH, strUrl).permitAll();
                                    break;
                                case "delete":
                                    http.authorizeRequests().antMatchers(HttpMethod.DELETE, strUrl).permitAll();
                                    break;
                                case "options":
                                    http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, strUrl).permitAll();
                                    break;
                                case "trace":
                                    http.authorizeRequests().antMatchers(HttpMethod.TRACE, strUrl).permitAll();
                                    break;
                                default:
                                    http.authorizeRequests().antMatchers(strUrl).permitAll();
                                    break;
                            }
                        } else {
                            http.authorizeRequests().antMatchers(strLinkIg).permitAll();
                        }
                    } else {
                        http.authorizeRequests().antMatchers(strLinkIg).permitAll();
                    }
                }
            }
        }
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

}
