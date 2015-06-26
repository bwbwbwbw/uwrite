package edu.tongji.image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Breezewish on 6/26/15.
 */
public class ImageResponse implements java.io.Serializable {

    private List<ImageItem> files;

    protected ImageResponse()
    {

    }

    public ImageResponse(String path)
    {
        files = new ArrayList<>();
        files.add(new ImageItem(path));
    }

    public List<ImageItem> getFiles() {
        return files;
    }

    public void setFiles(List<ImageItem> files) {
        this.files = files;
    }

}
