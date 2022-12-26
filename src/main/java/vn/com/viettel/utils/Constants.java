package vn.com.viettel.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final String REQUEST_MAPPING_PREFIX = "/api";
    public static final String VERSION_API_V1 = "/v1";
    public static final String LOCALE_VN = "vi_VN";
    public static final String TIMEZONE_VN = "Asia/Ho_Chi_Minh";

    public static final Map<String, String> PROPERTY_MAPPING = initMap();
    private static Map<String, String> initMap() {
        Map<String, String> map = new HashMap<>();
        map.put("username", "Tên đăng nhập");
        map.put("matkhau", "Mật khẩu");
        return Collections.unmodifiableMap(map);
    }
}
