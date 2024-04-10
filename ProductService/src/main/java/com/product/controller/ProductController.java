package com.product.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.product.dto.ProductDTO;
import com.product.entity.Product;
import com.product.exception.ProductNotFoundException;
import com.product.serviceimpl.ProductServiceImpl;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*")    // Front-end Connection
public class ProductController {

    @Autowired
    private ProductServiceImpl productServiceImpl;

    //  to add a new product
    @PostMapping("/addProduct")
    public ProductDTO addNewProduct(@RequestBody Product product) {
        // Calls the service layer to add a new product
        return productServiceImpl.addProduct(product);
    }

    //  to retrieve a product by ID
    @GetMapping("/getById/{p_id}")
    public ProductDTO getProductById(@PathVariable(value = "p_id") int id) throws ProductNotFoundException {
        // Calls the service layer to retrieve a product by ID
        return productServiceImpl.getProductById(id);
    }

    //  to update an existing product
    @PutMapping("/updateProduct/{p_id}")
    public String updateProduct(@PathVariable(value = "p_id") int id, @RequestBody ProductDTO product) {
        // Calls the service layer to update a product
        return productServiceImpl.updateProduct(id, product);
    }

    //  to delete a product by ID
    @DeleteMapping("/deleteProduct/{p_id}")
    public boolean deleteProduct(@PathVariable(value = "p_id") int id) throws ProductNotFoundException {
        // Calls the service layer to delete a product
        productServiceImpl.deleteProduct(id);
        return true;
    }

    //  to retrieve products by brand name
    @GetMapping("/getByBrand/{brandName}")
    public List<ProductDTO> getProductByBrandName(@PathVariable(value = "brandName") String name) throws ProductNotFoundException {
        // Calls the service layer to retrieve products by brand name
        return productServiceImpl.getProductByBrand(name);
    }
    
    //  to retrieve all products
    @GetMapping("/getAllProducts")
    public List<ProductDTO> getAllProducts() throws ProductNotFoundException {
        // Calls the service layer to retrieve all products
        return productServiceImpl.findAll();
    }
    
    //  to retrieve products by category ID
    @GetMapping("/getByCategoryId/{category_id}")
    public List<ProductDTO> getProductByCategoryId(@PathVariable(value = "category_id") int id) {
        // Calls the service layer to retrieve products by category ID
        return productServiceImpl.getProductsByCategoryId(id);
    }

    //  to handle image upload for a product
    @PostMapping("/{productid}/upload-image")
    public String handleImageUpload(
        @PathVariable int productId,
        @RequestParam("image") MultipartFile image) {

        if (image != null) {
            try {
                // Generate a unique filename for the image to avoid naming conflicts
                String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();

                // Set the path where you want to save the image
                String imagePath = "C:/ECommerceImages/" + filename;

                File imageFile = new File(imagePath);

                // Save the image file
                image.transferTo(imageFile);

                // Link the image path to the product in your database
                productServiceImpl.linkImageToProduct(productId, imagePath);

                return "Image uploaded successfully";
            } catch (IOException e) {
                return "Failed to upload image";
            }
        } else {
            return "No image file provided";
        }
    }
}
