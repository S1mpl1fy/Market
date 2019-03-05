package com.example.market1.Model;

public class PublishForm {
    private String title, description, image;
    private double price;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSellMethod() {
        return sellMethod;
    }

    public void setSellMethod(int sellMethod) {
        this.sellMethod = sellMethod;
    }

    public int getDealMethod() {
        return dealMethod;
    }

    public void setDealMethod(int dealMethod) {
        this.dealMethod = dealMethod;
    }

    private int sellMethod;
    private int dealMethod;

}
