package br.inatel.dm111promo.persistence.promo;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/*
{
    "id": "",
    "name": "",
    "starting": "",
    "expiration": "",
    "products": []
}

 */
public class Promo {

    private String id;
    private String name;
    private Date starting;
    private Date expiration;

    private List<Product> products;

    public Promo() {
    }

    public Promo(String id, String name, Date starting, Date expiration, List<Product> products) {
        this.id = id;
        this.name = name;
        this.starting = starting;
        this.expiration = expiration;
        this.products = products;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
