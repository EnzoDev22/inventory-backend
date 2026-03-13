package com.company.inventory.services;

import com.company.inventory.response.CategoryResponseRest;
import org.springframework.http.ResponseEntity;

public interface ICategorySevice {

    public ResponseEntity<CategoryResponseRest> search();
}
