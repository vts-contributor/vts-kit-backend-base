package vn.com.viettel.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Utils {
    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);


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
