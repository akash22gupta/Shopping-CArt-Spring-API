package com.cognizant.swaggerEditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
    @RequestMapping("/api/item")
    public class ItemController {
        @Autowired
        private ItemRepository repository;

        @GetMapping
        public List<Item> getAllItems() {
            return (List<Item>) repository.findAll();
        }

        @PostMapping
        public Item saveUser(@RequestBody Item newItem) {
            return repository.save(newItem);
        }

        @GetMapping("{id}")
        public Optional<Item> getUserById(@PathVariable(name = "id") int id){
            return repository.findById(id);
        }

        @PutMapping("{id}")
        public Item updateItemById(@RequestBody Item itemToUPdate, @PathVariable(name="id") int id){
            return repository.findById(id)
                    .map(item->{
                        item.setName(itemToUPdate.getName());
                    return repository.save(item);
                    })
                    .orElseGet(()->repository.save(itemToUPdate));
        }

    }
