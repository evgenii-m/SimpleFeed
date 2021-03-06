/**
 * 
 */
package org.push.onlyfeed.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;


/**
 * @author push
 *
 */
@Entity
@Table(name = "feed_items")
public class FeedItemEntity {
    public final static int URL_LENGTH_MAX = 512;
    public final static int TITLE_LENGTH_MAX = 500;
    public final static int DESCRIPTION_LENGTH_MAX = 10000;
    public final static int AUTHOR_LENGTH_MAX = 100;
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "title")
    @Size(min = 0, max = 500, message = "{validation.lengthRange}")
    @NotNull
    private String title;
    
    @Column(name = "description")
    @Size(min = 0, max = 10000, message = "{validation.lengthRange}")
    @NotNull
    private String description;
    
    @Column(name = "link")
    @Size(min = 0, max = 512, message = "{validation.lengthRange}")
    @URL(message = "{validation.url}")
    @NotNull
    private String link;
    
    @Column(name = "published_date")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    @NotNull
    private Date publishedDate;
    
    @Column(name = "author")
    @Size(min = 0, max = 100, message = "{validation.lengthRange}")
    private String author;

    @Column(name = "viewed")
    private boolean viewed;
    
    @Column(name = "image_url")
    @Size(min = 0, max = 512, message = "{validation.lengthRange}")
    @URL(message = "{validation.url}")
    private String imageUrl;
    
    @ManyToOne
    @JoinColumn(name = "feed_source_id")
    private FeedSourceEntity feedSource;
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title.length() > TITLE_LENGTH_MAX) {
            this.title = title.substring(0, TITLE_LENGTH_MAX);
        } else {
            this.title = title;
        }
    }


    public String getDescription() {
        return description;
    }
    
    public String getSummary() {
        // TODO: modify for block only HTML tags (strings as "< str >" musn't block)
        return description.replaceAll("<.*?(/>|>)", "");
    }

    public void setDescription(String description) {
        if (description.length() > DESCRIPTION_LENGTH_MAX) {
            this.description = description.substring(0, DESCRIPTION_LENGTH_MAX-1);
        } else {
            this.description = description;
        }
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        if (link.length() > URL_LENGTH_MAX) {
            this.link = link.substring(0, URL_LENGTH_MAX);
        } else {
            this.link = link;
        }
    }


    public Date getPublishedDate() {
        return publishedDate;
    }
    
    public String getPublishedDateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm");
        return dateFormat.format(publishedDate);
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (author.length() > AUTHOR_LENGTH_MAX) {
            this.author = author.substring(0, AUTHOR_LENGTH_MAX);
        } else {
            this.author = author;
        }
    }
    
    
    public boolean getViewed() {
        return viewed;
    }
    
    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    
    public String getImageUrl() {
        if (imageUrl == null) {
            imageUrl = feedSource.getLogoUrl();
        }
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        if (imageUrl.length() > URL_LENGTH_MAX) {
            this.imageUrl = imageUrl.substring(0, URL_LENGTH_MAX);
        } else {
            this.imageUrl = imageUrl;
        }
    }
    

    public FeedSourceEntity getFeedSource() {
        return feedSource;
    }

    public void setFeedSource(FeedSourceEntity feedSource) {
        this.feedSource = feedSource;
    }

    


    @Override
    public String toString() {
        return "FeedItemEntity [id=" + id + ", title=" + title
                + ", description=" + description + ", link=" + link
                + ", publishedDate=" + publishedDate + ", author=" + author
                + ", viewed=" + viewed + ", imageUrl=" + imageUrl
                + ", feedSource.id=" + feedSource.getId() + "]";
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
        FeedItemEntity other = (FeedItemEntity) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
