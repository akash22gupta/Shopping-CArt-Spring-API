package com.cognizant.swaggerEditor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private LineItemRepository lineItemRepository;

    @GetMapping("{id}")
    public Optional<ShoppingCart> getShoppingCartById(@PathVariable(name = "id") int id){
        return shoppingCartRepository.findById(id);
    }

    @PostMapping
    public ShoppingCart saveShoppingCart(@RequestBody ShoppingCart newShoppingCart) {
        return shoppingCartRepository.save(newShoppingCart);
    }

    @GetMapping("{id}/lineitem")
    public List<LineItem> getLineItemsByCartId(@PathVariable(name = "id") Integer id) {
//        return ((List<LineItem>)lineItemRepository.findAll())
//                .stream()
//                .filter((lineItem) -> lineItem.getShoppingCartId().equals(id))
//                .collect(Collectors.toList());

//        return shoppingCartRepository.
    }
}
