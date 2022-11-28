package vn.com.viettel.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Utils {
    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

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
     * Mapping message validate
     * @param field
     * @return
     */
    public static String getErrorField(String field) {
        String error = Constants.PROPERTY_MAPPING.get(field);
        if (error == null) {
            return field;
        } else {
            return error;
        }
    }
}
