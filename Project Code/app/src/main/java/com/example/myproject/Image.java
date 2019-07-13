package com.example.myproject;


import com.google.firebase.database.Exclude;

public class Image {
    private String imageId;
    private String userId;
    private String url;
    private String tags;


    public Image() {
    }

    public Image(String imageId, String userId, String url, String tags) {
        this.imageId = imageId;
        this.userId = userId;
        this.url = url;
        this.tags = tags;
    }

    @Exclude
    public String getImageId() {
        return imageId;
    }

    @Exclude
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}

