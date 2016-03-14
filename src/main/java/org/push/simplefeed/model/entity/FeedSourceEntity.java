/**
 * 
 */
package org.push.simplefeed.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;


/**
 * @author push
 *
 */
@Entity
@Table(name = "feed_sources")
public class FeedSourceEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "name")
    @NotEmpty(message = "{validation.name.NotEmpty.message}")
    @Size(min = 1, max = 100, message = "{validation.name.Size.message}")
    private String name;
    
    @Column(name = "url")
    @NotEmpty(message = "{validation.url.NotEmpty.message}")
    @URL(message = "{validation.url.URL.message}")
    @Size(max = 255, message = "{validation.url.Size.message}")
    private String url;
    
    @Column(name = "logo_url")
    private String logoUrl;
    
    @Column(name = "description")
    private String description;
    
    
    
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
    
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }


    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
    @Override
    public String toString() {
        return "FeedSourceEntity [id=" + id + ", name=" + name + ", url=" + url
                + ", logoUrl=" + logoUrl + ", description=" + description
                + "]";
    }
    
}
