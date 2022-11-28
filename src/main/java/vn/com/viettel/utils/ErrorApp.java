package vn.com.viettel.utils;

import vn.com.viettel.core.config.I18n;

public enum ErrorApp {
    SUCCESS(200, I18n.getMessage("msg.success")),
    BAD_REQUEST(400, I18n.getMessage("msg.bad.request")),
    BAD_REQUEST_PATH(400, I18n.getMessage("msg.bad.request.path")),
    UNAUTHORIZED(401, I18n.getMessage("msg.unauthorized")),
    FORBIDDEN(403, I18n.getMessage("msg.access.denied")),
    INTERNAL_SERVER(500, I18n.getMessage("msg.internal.server"));

    private final int code;
    private final String description;

    ErrorApp(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }
}
