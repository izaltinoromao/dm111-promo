package br.inatel.dm111promo.persistence.promo;
/*
{
    "productId": "",
    "discount": 15
}
*/
public class Product {

    private String productId;
    private int discount;

    public Product(String productId, int discount) {
        this.productId = productId;
        this.discount = discount;
    }

    public Product() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
