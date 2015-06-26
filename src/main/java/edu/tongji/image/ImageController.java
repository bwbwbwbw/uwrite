package edu.tongji.image;

import edu.tongji.error.ResourceNotFoundException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.WebContentGenerator;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * Created by Breezewish on 6/13/15.
 */
@Controller
@Secured("ROLE_USER")
public class ImageController extends WebContentGenerator {

    @Autowired
    private ImageResolver imageResolver;

    @RequestMapping(value = "image/{name}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImage(@PathVariable String name) {
        try {
            String fp = imageResolver.resolveRealPath(name);
            InputStream in = new FileInputStream(fp);

            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(ImageUtil.getMimeType(name));

            return new ResponseEntity<>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
        } catch (IOException ex) {
            throw new ResourceNotFoundException();
        } catch (IllegalArgumentException ex) {
            throw new ResourceNotFoundException();
        }
    }

    public static String SHAsum(byte[] convertme) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        return byteArray2Hex(md.digest(convertme));
    }

    private static String byteArray2Hex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String showFileUpload()
    {
        return "upload";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ImageResponse handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            if (!ImageUtil.isImageFile(file.getOriginalFilename())) {
                return new ImageResponse();
            }
            try {
                byte[] bytes = file.getBytes();
                String filename = SHAsum(bytes) + "." + ImageUtil.getFileExtension(file.getOriginalFilename());
                String targetFile = imageResolver.resolveRealPath(filename);

                File f = new File(targetFile);
                if (!f.exists()) {
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(f));
                    stream.write(bytes);
                    stream.close();
                }
                return new ImageResponse("/image/" + filename);
            } catch (Exception e) {
                e.printStackTrace();;
                return new ImageResponse();
            }
        } else {
            return new ImageResponse();
        }
    }
}
