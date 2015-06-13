package edu.tongji.image;

import org.springframework.http.MediaType;

/**
 * Created by Breezewish on 6/13/15.
 */
public class ImageUtil {

    private static String getFileExtension(String filePath) {
        if (filePath.lastIndexOf(".") != -1 && filePath.lastIndexOf(".") != 0)
            return filePath.substring(filePath.lastIndexOf(".") + 1);
        else return "";
    }

    public static MediaType getMimeType(String filePath) {
        if (filePath.indexOf('.') == -1) {
            throw new IllegalArgumentException();
        }

        switch (getFileExtension(filePath.toLowerCase())) {
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpeg":
            case "jpg":
                return MediaType.IMAGE_JPEG;
            case "gif":
                return MediaType.IMAGE_GIF;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
