package com.cognizant.swaggerEditor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="\"shoppingCart\"")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String name = null;

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    @OneToMany
    private List<LineItem> lineItems;

    public ShoppingCart(){

    }

    public ShoppingCart(String name){
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getId() {
        return this.id;
    }

    public String getName(){
        return this.name;
    }


}
