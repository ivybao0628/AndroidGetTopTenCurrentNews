package com.singwai.currenttoptennews.modal;

/**
 * Created by Singwai Chan on 2/1/15.
 */
public class NewsItem {

    String title;
    String description;
    String link;
    String pubDate;
    String pictureLink;


    public NewsItem(String title, String description, String link, String pubDate, String pictureLink){
        this.setTitle(title);
        this.setDescription(description);
        this.setLink(link);
        this.setPubDate(pubDate);
        this.setPictureLink(pictureLink);
    }

    //Sometime link is not available.
    //We will use a placeholder from drawable.

    public NewsItem(String title, String description, String link, String pubDate){
        this.setTitle(title);
        this.setDescription(description);
        this.setLink(link);
        this.setPubDate(pubDate);
    }

    //List of getter and setter. Add validation as needed.
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    //todo check and make sure input is valid.
    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

}
