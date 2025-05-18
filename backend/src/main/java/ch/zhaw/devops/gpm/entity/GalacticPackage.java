package ch.zhaw.devops.gpm.entity;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class GalacticPackage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    private String description;
    private String author;
    private String version;
    private String category;
    private int downloads;
    private String compatibility; // z.B. "Republic", "Empire", "Neutral"
    private Date releaseDate;
    
    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public GalacticPackage() {
    }

    public GalacticPackage(String name, String description, String author, String version, String category, int downloads, String compatibility, Date releaseDate) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.version = version;
        this.category = category;
        this.downloads = downloads;
        this.compatibility = compatibility;
        this.releaseDate = releaseDate;
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

    public String getAuthor() {
        return author;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public String getCompatibility() {
        return compatibility;
    }

    public void setCompatibility(String compatibility) {
        this.compatibility = compatibility;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

}