package com.cognizant.swaggerEditor;


import javax.persistence.*;
import java.util.Objects;

@Entity
public class LineItem {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    public Integer getShoppingCartId() {
        return shoppingCartId;
    }

    private Integer shoppingCartId;

    private Integer quantity;

    public LineItem() {
    }

    public Integer getQuantity() {
        return quantity;
    }

    public LineItem(Integer shoppingCartId, Integer quantity) {
        this.shoppingCartId = shoppingCartId;
        this.quantity = quantity;
    }
}
