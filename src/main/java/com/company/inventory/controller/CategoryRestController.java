package com.company.inventory.controller;

import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.services.ICategorySevice;
import com.company.inventory.util.CategoryExcelExporter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@CrossOrigin(origins = {"*"})
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

    /**
     * export to excel file
     * @param response
     * @throws IOException
     */
    @GetMapping("/categories/export/excel")
    public void exportToExcel(HttpServletResponse response)throws IOException{
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=result_category.xlsx";
        response.setHeader(headerKey, headerValue);

        ResponseEntity<CategoryResponseRest> categoryResponse = service.search();
        CategoryExcelExporter excelExporter = new CategoryExcelExporter(categoryResponse.getBody().getCategoryResponse().getCategory());

        excelExporter.export(response);
    }

}
