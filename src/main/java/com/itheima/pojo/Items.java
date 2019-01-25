package com.itheima.pojo;

import org.apache.solr.client.solrj.beans.Field;

public class Items {
    @Field
   private long id ;
    @Field
   private String title ;
    @Field
   private long price ;

    public Items() {

    }

    public Items(long id, String title, long price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Items{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}