package com.cognizant.swaggerEditor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart,Integer> {
    @Override
    default Iterable<ShoppingCart> findAllById(Iterable<Integer> integers) {
        return null;
    }
}
