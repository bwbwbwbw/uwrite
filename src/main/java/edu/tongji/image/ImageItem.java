package edu.tongji.image;

/**
 * Created by Breezewish on 6/26/15.
 */
public class ImageItem implements java.io.Serializable {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    protected ImageItem() {

    }

    public ImageItem(String url) {
        this.setUrl(url);
    }
}