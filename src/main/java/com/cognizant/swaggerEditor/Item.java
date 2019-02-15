package com.cognizant.swaggerEditor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="\"item\"")
public class Item {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String name;
    private Long price;
    private boolean onSale;

    public Item(String name) {
        this.name = name;
    }

    public Item() {

    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setOnSale(boolean onSale) {
        this.onSale = onSale;
    }

    public Long getPrice() {
        return price;
    }

    public boolean getOnSale() {
        return onSale;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id &&
                Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public String getName() {
        return name;
    }

    @SpringBootApplication

    public static class ShoppingCartApiApplication {
        public static void main(String[] args) {
            SpringApplication.run(ShoppingCartApiApplication.class, args);
        }

    }
}