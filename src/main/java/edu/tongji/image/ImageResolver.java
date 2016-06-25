package edu.tongji.image;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
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
