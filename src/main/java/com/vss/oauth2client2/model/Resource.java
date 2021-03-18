package com.vss.oauth2client2;

public class Resource {

    private Integer id;
    private String name;

    public Resource(Integer id, String name){
        this.id=id;
        this.name = name;
    }
    public Resource(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
