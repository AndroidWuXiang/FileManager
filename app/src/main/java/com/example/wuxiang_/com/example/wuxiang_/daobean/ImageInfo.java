package com.example.wuxiang_.com.example.wuxiang_.daobean;

import android.graphics.Bitmap;

/**
 * Created by wuxiang_ on 2016/10/6.
 */

public class ImageInfo {
    private String thumbPath;
    private String path;
    private String Title;
    private String displayName;
    private String mimeType;
    private Bitmap image;

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getPath() {
        return path;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getTitle() {
        return Title;
    }

    public String getMimeType() {
        return mimeType;
    }
}
