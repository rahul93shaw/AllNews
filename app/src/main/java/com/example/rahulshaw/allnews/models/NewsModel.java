package com.example.rahulshaw.allnews.models;

/**
 * Created by Rahul Shaw on 22-06-2017.
 */

public class NewsModel {

    private String authorName;
    private String articleName;
    private String description;
    private String image;
    private String urlToNews;
    private String newsPartnerLogo;
    private String newsPartnerName;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrlToNews() {
        return urlToNews;
    }

    public void setUrlToNews(String urlToNews) {
        this.urlToNews = urlToNews;
    }

    public String getNewsPartnerLogo() {
        return newsPartnerLogo;
    }

    public void setNewsPartnerLogo(String newsPartnerLogo) {
        this.newsPartnerLogo = newsPartnerLogo;
    }
    public String getNewsPartnerName() {
        return newsPartnerName;
    }

    public void setNewsPartnerName(String newsPartnerName) {
        this.newsPartnerName = newsPartnerName;
    }
}
