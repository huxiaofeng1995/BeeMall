package com.beemall.pojo;

import java.io.Serializable;

public class TbContentCategory implements Serializable{
    private Long id;

    private String name;

//    private String contentGroup;
//
//    private String contentKey;
//
//    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

}