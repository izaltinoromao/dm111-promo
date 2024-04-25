package br.inatel.dm111promo.persistence.promobyuser;

import br.inatel.dm111promo.persistence.promo.Product;

import java.util.Date;
import java.util.List;

public class PromoByUser {

    private String id;
    private String userId;
    private String name;
    private Date starting;
    private Date expiration;
    private List<Product> productsForYou;
    private List<Product> products;

    public PromoByUser() {
    }

    public PromoByUser(String id, String userId, String name, Date starting, Date expiration, List<Product> productsForYou, List<Product> products) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.starting = starting;
        this.expiration = expiration;
        this.productsForYou = productsForYou;
        this.products = products;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStarting() {
        return starting;
    }

    public void setStarting(Date starting) {
        this.starting = starting;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public List<Product> getProductsForYou() {
        return productsForYou;
    }

    public void setProductsForYou(List<Product> productsForYou) {
        this.productsForYou = productsForYou;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
