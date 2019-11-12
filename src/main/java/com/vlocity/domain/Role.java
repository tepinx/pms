package com.vlocity.domain;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "Role")
public class Role implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5540467017193074520L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "deleted", columnDefinition = "boolean default false")
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
