package com.company.inventory.controller;

import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.services.ICategorySevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class CategoryRestController {

    @Autowired
    private ICategorySevice service;

    /**
     * get all categories
     * @return
     */
    @GetMapping("/categories")
    public ResponseEntity<CategoryResponseRest> searchCategories(){

        ResponseEntity<CategoryResponseRest> response = service.search();

        return response;
    }

    /**
     * get categories by id
     * @param id
     * @return all categories
     */
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseRest> searchCategoriesById(@PathVariable Long id){

        ResponseEntity<CategoryResponseRest> response = service.searchById(id);

        return response;
    }

    /**
     * save category
     * @param category
     * @return the category created
     */
    @PostMapping("/categories")
    public ResponseEntity<CategoryResponseRest> save(@RequestBody Category category){

        ResponseEntity<CategoryResponseRest> response = service.save(category);

        return response;
    }

    /**
     * update category
     * @param category
     * @param id
     * @return the objet updated
     */
    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseRest> update(@RequestBody Category category, @PathVariable Long id){

        ResponseEntity<CategoryResponseRest> response = service.update(category, id);

        return response;
    }

    /**
     * delete category by id
     * @param id
     * @return category deleted
     */
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseRest> delete(@PathVariable Long id){

        ResponseEntity<CategoryResponseRest> response = service.deleteById(id);

        return response;
    }

}
