package com.company.inventory.response;

import com.company.inventory.model.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {

    private List<Product> products;

}
