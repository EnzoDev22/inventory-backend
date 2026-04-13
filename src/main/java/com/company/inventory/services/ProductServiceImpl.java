package com.company.inventory.services;

import com.company.inventory.dao.ICategoryDao;
import com.company.inventory.dao.IProductDao;
import com.company.inventory.model.Category;
import com.company.inventory.model.Product;
import com.company.inventory.response.ProductResponseRest;
import com.company.inventory.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService{

    @Autowired
    private ICategoryDao categoryDao;

    @Autowired
    private IProductDao productDao;

    @Override
    public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId) {

        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();

        try{

            //search category to set in the product object
            Optional<Category> categoryOptional = this.categoryDao.findById(categoryId);

            if(categoryOptional.isPresent()){
                product.setCategory(categoryOptional.get());
            }else{
                response.setMetadata("respuesta no ok", "-1", "Categoria no encontrada.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            //save product
            Product productSave = this.productDao.save(product);

            if(productSave != null){
                list.add(productSave);
                response.setMetadata("respuesta ok", "00", "Producto guardado.");
                response.getProductResponse().setProducts(list);
            }else{
                response.setMetadata("respuesta no ok", "-1", "Producto no guardado.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

        }catch(Exception e){
            e.getStackTrace();
            response.setMetadata("respuesta no ok", "-1", "Error al guardar producto.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> searchById(Long id) {

        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();

        try{
            //search product by id
            Optional<Product> productOptional = this.productDao.findById(id);

            if(productOptional.isPresent()){
                byte[] imageDescompressed = Util.decompressZLib(productOptional.get().getPicture());
                productOptional.get().setPicture(imageDescompressed);
                list.add(productOptional.get());
                response.getProductResponse().setProducts(list);
                response.setMetadata("respuesta ok", "00", "Producto encontrado.");
            }else{
                response.setMetadata("respuesta no ok", "-1", "Producto no encontrado.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        }catch(Exception e){
            e.getStackTrace();
            response.setMetadata("respuesta no ok", "-1", "Error al buscar producto.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponseRest> serarchByName(String name) {

        ProductResponseRest response = new ProductResponseRest();
        List<Product> list = new ArrayList<>();
        List<Product> listAux = new ArrayList<>();

        try{
            //search product by name
            listAux = this.productDao.findByNameContainingIgnoreCase(name);

            if(listAux.size() > 0){

                listAux.stream().forEach((p) -> {
                    byte[] imageDescompressed = Util.decompressZLib(p.getPicture());
                    p.setPicture(imageDescompressed);
                    list.add(p);
                });

                response.getProductResponse().setProducts(list);
                response.setMetadata("respuesta ok", "00", "Productos encontrados.");
            }else{
                response.setMetadata("respuesta no ok", "-1", "Productos no encontrados.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        }catch(Exception e){
            e.getStackTrace();
            response.setMetadata("respuesta no ok", "-1", "Error al buscar producto por nombre.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<ProductResponseRest> deleteById(Long id) {

        ProductResponseRest response = new ProductResponseRest();

        try{
            //search product by id
            Optional<Product>optionalProduct = this.productDao.findById(id);

            if (optionalProduct.isEmpty()){
                //not exist the product with the id
                response.setMetadata("respuesta no ok", "-2", "Producto no encontrado.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }else{
                //delete product by id
                this.productDao.deleteById(id);
                response.setMetadata("respuesta ok", "00", "Producto eliminado.");
            }
        }catch(Exception e){
            e.getStackTrace();
            response.setMetadata("respuesta no ok", "-1", "Error al eliminar producto.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
    }
}
