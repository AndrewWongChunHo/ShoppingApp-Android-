package com.example.project_text.models;

import java.io.Serializable;

public class item implements Serializable {

    String name;
    String brand;
    String image;
    int price;
    String description;

    public item() {
    }

    public item(String name, String brand, String image, int price, String description) {
        this.name = name;
        this.brand = brand;
        this.image = image;
        this.price = price;
        this.description = description;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

