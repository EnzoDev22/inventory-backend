package com.company.inventory.services;

import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;
import org.springframework.http.ResponseEntity;

public interface ICategorySevice {

    public ResponseEntity<CategoryResponseRest> search();

    public ResponseEntity<CategoryResponseRest> searchById(Long id);

    public ResponseEntity<CategoryResponseRest> save(Category category);

    public ResponseEntity<CategoryResponseRest> update(Category category, Long id);
}
