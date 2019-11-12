package com.vlocity.dto;

import java.io.Serializable;

public class RoleDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7283983165883252651L;

    private Long id;

    private String name;

    private String description;

    private boolean deleted;

    
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
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
