package me.zhyd.hunter.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class VirtualArticle {

    private String title;
    private String content;
    private String author;
    private Date releaseDate;
    private String source;
    private String description;
    private String keywords;
    private List<String> tags;

    private Set<ImageLink> imageLinks;

    public VirtualArticle setTitle(String title) {
        this.title = title;
        return this;
    }

    public VirtualArticle setContent(String content) {
        this.content = content;
        return this;
    }

    public VirtualArticle setAuthor(String author) {
        this.author = author;
        return this;
    }

    public VirtualArticle setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public VirtualArticle setSource(String source) {
        this.source = source;
        return this;
    }

    public VirtualArticle setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public VirtualArticle setDescription(String description) {
        this.description = description;
        return this;
    }

    public VirtualArticle setKeywords(String keywords) {
        this.keywords = keywords;
        return this;
    }

    public VirtualArticle setImageLinks(Set<ImageLink> imageLinks) {
        this.imageLinks = imageLinks;
        return this;
    }
}
