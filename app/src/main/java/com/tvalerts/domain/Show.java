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

    public void setName(String name) {
        this.name = name;
    }
}
