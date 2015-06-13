package edu.tongji.image;

import org.springframework.beans.factory.annotation.Value;

import java.io.File;

/**
 * Created by Breezewish on 6/13/15.
 */
public class ImageResolver {

    @Value("${uwrite.image.path}")
    private String storagePath;

    public String resolveRealPath(String givenPath) {
        if (givenPath.indexOf('/') > -1 || givenPath.indexOf('\\') > -1) {
            throw new IllegalArgumentException();
        }
        if (givenPath.indexOf('.') == -1) {
            throw new IllegalArgumentException();
        }
        if (givenPath.length() < 5) {
            throw new IllegalArgumentException();
        }
        return this.storagePath + File.separator + givenPath.charAt(0) + File.separator +
                givenPath.charAt(1) + File.separator + givenPath.substring(2);
    }

}
