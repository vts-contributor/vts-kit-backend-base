package vn.com.viettel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class JwtUser {
    String sub;
    String name;
    String azp;
}
