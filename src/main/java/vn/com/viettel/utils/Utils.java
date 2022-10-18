package vn.com.viettel.utils;

import org.keycloak.KeycloakPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Utils extends vn.com.viettel.core.utils.Utils {
    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    /**
     * Lay thong tin user
     *
     * @param authentication
     * @return
     */
    public static String getUserLogin(Authentication authentication) {
        try {
            KeycloakPrincipal principal = (KeycloakPrincipal) authentication.getPrincipal();
            String userName = principal.getKeycloakSecurityContext().getToken().getPreferredUsername().toUpperCase();
            return userName;
        } catch (Exception e) {
            LOGGER.error("Loi! getUserLogin: ", e);
            return null;
        }
    }

    /**
     * Lay thong tin user id
     *
     * @param authentication
     * @return
     */
    public static String getUserLoginId(Authentication authentication) {
        try {
            KeycloakPrincipal principal = (KeycloakPrincipal) authentication.getPrincipal();
            String userId = principal.getKeycloakSecurityContext().getToken().getId();
            return userId;
        } catch (Exception e) {
            LOGGER.error("Loi! getUserLoginId: ", e);
            return null;
        }
    }

    /**
     * Get string token
     *
     * @param authentication
     * @return
     */
    public static String getStringToken(Authentication authentication) {
        try {
            KeycloakPrincipal principal = (KeycloakPrincipal) authentication.getPrincipal();
            String strToken = principal.getKeycloakSecurityContext().getTokenString();
            return strToken;
        } catch (Exception e) {
            LOGGER.error("Loi! getUserLogin: ", e);
            return null;
        }
    }

    /**
     * Lay is user login tu token
     *
     * @param authentication
     * @return
     */
    public static String getIdUserLogin(Authentication authentication) {
        try {
            KeycloakPrincipal principal = (KeycloakPrincipal) authentication.getPrincipal();
            String userId = principal.getKeycloakSecurityContext().getToken().getId();
            return userId;
        } catch (Exception e) {
            LOGGER.error("Loi! getIdUserLogin: ", e);
            return null;
        }
    }

    /**
     * Lay role
     * @param authentication
     * @return
     */
    public static Set<String> getRoleId(Authentication authentication) {
        try {
            KeycloakPrincipal principal = (KeycloakPrincipal) authentication.getPrincipal();
            Set<String> roleId = principal.getKeycloakSecurityContext().getToken().getResourceAccess().get(getClientId(authentication)).getRoles();
            return roleId;
        } catch (Exception e) {
            LOGGER.error("Loi! getUserLogin: ", e);
            return new HashSet<>();
        }
    }

    /**
     * Lay client id
     */
    public static String getClientId(Authentication authentication) {
        try {
            KeycloakPrincipal principal = (KeycloakPrincipal) authentication.getPrincipal();
            String clientId = principal.getKeycloakSecurityContext().getToken().getIssuedFor();
            return clientId;
        } catch (Exception e) {
            LOGGER.error("Loi! getUserLogin: ", e);
            return null;
        }
    }

    /**
     * Kiem tra object co null hay khong
     *
     * @param objects
     * @return
     */
    public static boolean isNullOrEmpty(final Object[] objects) {
        return objects == null || objects.length == 0;
    }

    /**
     * Kiem tra chuoi String null or rong
     *
     * @param toTest
     * @return
     */
    public static boolean isNullOrEmpty(String toTest) {
        return toTest == null || toTest.isEmpty();
    }

    /**
     * Kiem tra object co null hay khong
     *
     * @param obj
     * @return
     */
    public static boolean isNullObject(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            return isNullOrEmpty(obj.toString());
        }
        return false;
    }


    /**
     * Kiem tra list null or empty
     *
     * @param collection
     * @return
     */
    public static boolean isNullOrEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Check valid file
     * @param fileName
     * @param file
     * @param maxFileSizeMb
     * @return
     */
    public static boolean checkFileValid(String fileName, byte[] file, Integer maxFileSizeMb) {
        if (Objects.isNull(maxFileSizeMb)) {
            maxFileSizeMb = 15;
        }
        Objects.requireNonNull(file);
        long fileSizeMb = file.length / (1024 * 1024);
        return checkFileExtensionValid(fileName, ".JPG", ".PNG", ".TIFF", ".BMP", ".PDF", ".JPEG", ".WEBP", ".HEIC", ".DOCX", ".DOC", ".XLS", ".XLSX") && fileSizeMb <= maxFileSizeMb;
    }

    /**
     * Check valid extension file
     * @param fileName
     * @param fileExtensions
     * @return
     */
    public static boolean checkFileExtensionValid(String fileName, String... fileExtensions) {
        Objects.requireNonNull(fileName);
        for (String fileExtension : fileExtensions) {
            if (fileName.toLowerCase().endsWith(fileExtension.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
