package com.lifeup.recpontos.model.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Promo implements Serializable{

    private String _id = new String();
    private String title = new String();
    private String description = new String();
    //private ArrayList<String> categories = new ArrayList<String>();
    private String logo = new String();
    private String price = new String();

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

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

    /*public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }*/

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
