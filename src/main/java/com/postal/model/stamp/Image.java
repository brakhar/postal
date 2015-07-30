package com.postal.model.stamp;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by brakhar on 20.03.15.
 */

@Entity
@Table(name = "image")
public class Image implements Serializable{

    private static final long serialVersionUID = -2626997191126244559L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "content")
    private byte[] content;
    private int height;
    private int width;
    private String alt;

    public Image() {}

    public Image(byte[] content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }
}
