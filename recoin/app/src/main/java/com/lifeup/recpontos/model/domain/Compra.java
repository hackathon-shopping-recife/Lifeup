package com.lifeup.recpontos.model.domain;

import java.io.Serializable;

public class Compra implements Serializable{

    private String _id = new String();
    private int totalValue = 0;
    private String name = new String();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(int totalValue) {
        this.totalValue = totalValue;
    }
}
