/**
 * 
 */
package org.push.onlyfeed.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * @author push
 *
 */
@Entity
@Table(name = "feed_sources")
public class FeedSourceEntity {
    public static final String DEFAULT_LOGO_NAME = "no_logo.gif";
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "name")
    @Size(min = 2, max = 100, message = "{validation.lengthRange}")
    @NotNull
    private String name;
    
    @Column(name = "url")
    @Size(min = 1, max = 512, message = "{validation.lengthRange}")
    @URL(message = "{validation.url}")
    @NotNull
    private String url;
    
    @Column(name = "logo_url")
    @Size(min = 1, max = 512, message = "{validation.lengthRange}")
    @URL(message = "{validation.url}")
    private String logoUrl;
    
    @Column(name = "description")
    @Size(min = 0, max = 1000, message = "{validation.lengthRange}")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserEntity user;
    
    @OneToMany(mappedBy = "feedSource", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<FeedItemEntity> feedItemList = new ArrayList<>();
    
    
    
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
    
    
    public UserEntity getUser() {
        return user;
    }
    
    public void setUser(UserEntity user) {
        this.user = user;
    }
    
    
    public List<FeedItemEntity> getFeedItemList() {
        return feedItemList;
    }
    
    public void setFeedItemList(List<FeedItemEntity> feedItemList) {
        this.feedItemList = feedItemList;
    }



    @Override
    public String toString() {
        return "FeedSourceEntity [id=" + id + ", name=" + name + ", url=" + url + ", logoUrl=" + logoUrl 
                + ", description=" + ((description == null) ? "none" : description) + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FeedSourceEntity other = (FeedSourceEntity) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    
}
