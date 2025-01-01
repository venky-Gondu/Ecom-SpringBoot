package com.example.Ecom.service;

import com.example.Ecom.Repositry.ProductRepo;
import com.example.Ecom.model.Products;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepo repo;

    public List<Products> getAllProducts() {
        return repo.findAll();
    }

    public Products getProduct(int id) {
        // Updated to return null if product is not found
        Products prod=repo.findById(id).orElse(null);
        System.out.println(prod.isProductAvailable());
        return prod;

    }

    public Products addProduct(Products product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return repo.save(product);
    }


    public Products updateProduct(int id, Products updatedProduct, MultipartFile imgFile) throws IOException {
        Products existingProduct = repo.findById(id).orElse(null);
        if (existingProduct != null) {
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setBrand(updatedProduct.getBrand());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setReleaseDate(updatedProduct.getReleaseDate());
            existingProduct.setProductAvailable(updatedProduct.isProductAvailable());
            existingProduct.setQuantity(updatedProduct.getQuantity());

            if (imgFile != null && !imgFile.isEmpty()) {
                existingProduct.setImageName(imgFile.getOriginalFilename());
                existingProduct.setImageType(imgFile.getContentType());
                existingProduct.setImageData(imgFile.getBytes());
            }
            return repo.save(existingProduct);
        }
        return null;
    }

    public void deleteProduct(int id) {
        Products product = repo.findById(id).orElse(null);
        if (product != null) {
            repo.deleteById(id); // Ensure this line is executed
        } else {
            throw new IllegalArgumentException("Product not found for deletion.");
        }
    }


    public List<Products> searchProducts(String keyword) {
        return repo.search(keyword);
    }
}
