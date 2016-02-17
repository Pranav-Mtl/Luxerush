package com.shoping.BE;

/**
 * Created by appslure on 16-02-2016.
 */
public class SubscriptionItemDetailBE {

    private String productName,subscriptionPrice,size,color,tag,brand,description,productType,productCondition;
    private String jsonProductImage;

    private String productID;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductCondition() {
        return productCondition;
    }

    public void setProductCondition(String productCondition) {
        this.productCondition = productCondition;
    }

    public String getJsonProductImage() {
        return jsonProductImage;
    }

    public void setJsonProductImage(String jsonProductImage) {
        this.jsonProductImage = jsonProductImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSubscriptionPrice() {
        return subscriptionPrice;
    }

    public void setSubscriptionPrice(String subscriptionPrice) {
        this.subscriptionPrice = subscriptionPrice;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
