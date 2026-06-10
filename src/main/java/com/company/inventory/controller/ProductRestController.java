package com.company.inventory.controller;

import com.company.inventory.model.Product;
import com.company.inventory.response.ProductResponseRest;
import com.company.inventory.services.IProductService;
import com.company.inventory.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/v1")
public class ProductRestController {

    @Autowired
    private IProductService productService;


    /**
     * save product
     * @param picture
     * @param name
     * @param price
     * @param quantity
     * @param categoryId
     * @return
     * @throws IOException
     */
    @PostMapping("/products")
    public ResponseEntity<ProductResponseRest> save(@RequestParam("picture")MultipartFile picture,
                                                    @RequestParam("name")String name,
                                                    @RequestParam("price") int price,
                                                    @RequestParam("quantity") int quantity,
                                                    @RequestParam("categoryId") Long categoryId
    )throws IOException{
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setPicture(Util.compressZLib(picture.getBytes()));

        ResponseEntity<ProductResponseRest> response = this.productService.save(product,categoryId);

        return response;
    }

    /**
     * search by Id
     * @param id
     * @return
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseRest> searchById(@PathVariable Long id){
        ResponseEntity<ProductResponseRest> response = this.productService.searchById(id);
        return response;
    }

    /**
     * search by Name
     * @param name
     * @return
     */
    @GetMapping("/products/filter/{name}")
    public ResponseEntity<ProductResponseRest> searchByName(@PathVariable String name){
        ResponseEntity<ProductResponseRest> response = this.productService.serarchByName(name);
        return response;
    }

    /**
     * delete product by id
     * @param id
     * @return
     */
    @DeleteMapping("/products/{id}")
    public ResponseEntity<ProductResponseRest> deleteById(@PathVariable Long id){
        ResponseEntity<ProductResponseRest> response = this.productService.deleteById(id);
        return response;
    }

    /**
     * get all products
     * @return
     */
    @GetMapping("/products")
    public ResponseEntity<ProductResponseRest> getProducts(){
        ResponseEntity<ProductResponseRest> response = this.productService.search();
        return response;
    }

    /**
     * update product
     * @param picture
     * @param name
     * @param price
     * @param quantity
     * @param categoryId
     * @param id
     * @return
     * @throws IOException
     */
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseRest> update(@RequestParam("picture")MultipartFile picture,
                                                    @RequestParam("name")String name,
                                                    @RequestParam("price") int price,
                                                    @RequestParam("quantity") int quantity,
                                                    @RequestParam("categoryId") Long categoryId,
                                                    @PathVariable Long id
    )throws IOException{
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setPicture(Util.compressZLib(picture.getBytes()));

        ResponseEntity<ProductResponseRest> response = productService.update(product,categoryId,id);
        return response;
    }

}
