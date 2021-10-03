package com.jlundho.kry.models.api;

public class UpdateServiceRequest {

    private String name;
    private String url;

    public UpdateServiceRequest() {

    }

    public UpdateServiceRequest(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
