package com.tvalerts.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Class to hold the Tv Show information
 * Created by anita on 6/05/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Show {

    private long id;
    private String url;
    private String name;
    private String type;
    private String language;
    private String status;
    private String premiered;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPremiered() {
        return premiered;
    }

    public void setPremiered(String premiered) {
        this.premiered = premiered;
    }

    public void setName(String name) {
        this.name = name;
    }


}
