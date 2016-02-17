package com.shoping.BE;

import java.io.Serializable;

/**
 * Created by appslure on 08-02-2016.
 */
public class ItemDetailBE implements Serializable {

    private String productName,buyingPrice,rentPrice,subscriptionPrice,size,color,tag,brand,description,productType,productCondition,buyingDiscountPrice,rentDiscountPrice;
    private String jsonProductDetail,jsonProductImage,jsonRelatedProduct;

    private String productCategory,productID;


    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBuyingDiscountPrice() {
        return buyingDiscountPrice;
    }

    public void setBuyingDiscountPrice(String buyingDiscountPrice) {
        this.buyingDiscountPrice = buyingDiscountPrice;
    }

    public String getRentDiscountPrice() {
        return rentDiscountPrice;
    }

    public void setRentDiscountPrice(String rentDiscountPrice) {
        this.rentDiscountPrice = rentDiscountPrice;
    }

    public String getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(String buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public String getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(String rentPrice) {
        this.rentPrice = rentPrice;
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

    public String getJsonProductDetail() {
        return jsonProductDetail;
    }

    public void setJsonProductDetail(String jsonProductDetail) {
        this.jsonProductDetail = jsonProductDetail;
    }

    public String getJsonProductImage() {
        return jsonProductImage;
    }

    public void setJsonProductImage(String jsonProductImage) {
        this.jsonProductImage = jsonProductImage;
    }

    public String getJsonRelatedProduct() {
        return jsonRelatedProduct;
    }

    public void setJsonRelatedProduct(String jsonRelatedProduct) {
        this.jsonRelatedProduct = jsonRelatedProduct;
    }
}
