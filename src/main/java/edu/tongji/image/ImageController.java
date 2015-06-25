package edu.tongji.image;

import edu.tongji.error.ResourceNotFoundException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.WebContentGenerator;

import java.io.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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


    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name,
       @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                new BufferedOutputStream(new FileOutputStream(new File(name)));
                stream.write(bytes);
                stream.close();
                return "You successfully uploaded " + name + "!";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }
}
