package com.shorturlgenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "urls")
public class UrlModel implements Serializable {
    public UrlModel() {
    }

    public UrlModel(String shortUrl, String longUrl) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "shortUrl", unique = true)
    private String shortUrl;
    @Column(name = "longUrl", unique = true)
    private String longUrl;
    @Column(name = "timeOfBirth",unique = true)
    private long timer = System.currentTimeMillis();

    public long getTimer() {
        return timer;
    }

    public int getId() {
        return id;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }


}
