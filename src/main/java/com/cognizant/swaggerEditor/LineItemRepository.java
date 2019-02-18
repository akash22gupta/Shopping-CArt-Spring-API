package com.cognizant.swaggerEditor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineItemRepository extends CrudRepository<LineItem, Integer> {
}
