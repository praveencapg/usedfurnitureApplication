package com.product.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.dto.ProductDTO;
import com.product.entity.Product;
import com.product.exception.ProductNotFoundException;
import com.product.repository.ProductRepository;
import com.product.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public ProductDTO addProduct(Product product) {
		// Save the product to the database
		productRepository.save(product);
		
		// Create a ProductDTO object and populate it with product details
		ProductDTO productDTO = new ProductDTO();
		productDTO.setBrand(product.getBrand());
		productDTO.setCategoryId(product.getCategoryId());
		productDTO.setProductId(product.getProductId());
		productDTO.setProductImage(product.getProductImage());
		productDTO.setProductName(product.getProductName());
		productDTO.setProductPrice(product.getProductPrice());

		return productDTO;
	}

	@Override
	public ProductDTO getProductById(int id) throws ProductNotFoundException {
		// Retrieve product from the database using the provided ID
		Product product = productRepository.findById(id).orElse(null);
		
		// Throw ProductNotFoundException if product is not found
		if(product == null) {
			throw new ProductNotFoundException("Product Not Found by the id");
		}
		
		// Create a ProductDTO object and set its values from the retrieved product
		ProductDTO productDTO = new ProductDTO();
		productDTO.setBrand(product.getBrand());
		productDTO.setCategoryId(product.getCategoryId());
		productDTO.setProductId(product.getProductId());
		productDTO.setProductImage(product.getProductImage());
		productDTO.setProductName(product.getProductName());
		productDTO.setProductPrice(product.getProductPrice());

		return productDTO;
	}

	@Override
	public String updateProduct(int id, ProductDTO productDTO) {
		Product product;
		try {
			// Retrieve product from the database using the provided ID
			product = productRepository.findById(id)
					.orElseThrow(() -> new ProductNotFoundException("Product Not found by the given Id"));

			// Update product details if corresponding fields in the DTO are not null/empty
			if (productDTO.getProductName() != null)
				product.setProductName(productDTO.getProductName());
			if (productDTO.getBrand() != null)
				product.setBrand(productDTO.getBrand());
			if (!(productDTO.getCategoryId() <= 0))
				product.setCategoryId(productDTO.getCategoryId());
			if (productDTO.getProductImage() != null)
				product.setProductImage(productDTO.getProductImage());
			if (productDTO.getProductPrice() != 0)
				product.setProductPrice(productDTO.getProductPrice());

			// Save the updated product to the database
			productRepository.save(product);
		} catch (ProductNotFoundException e) {
			System.out.println(e);
			return "Product data not updated";
		}
		return "Product updated Successfully";
	}

	@Override
	public boolean deleteProduct(int id) throws ProductNotFoundException {
		// Retrieve product from the database using the provided ID
		Product product = productRepository.findById(id).orElse(null);
		
		// Throw ProductNotFoundException if product is not found
		if (product == null) {
			throw new ProductNotFoundException("Product Not Found by the id");
		}
		
		// Delete the product from the database
		productRepository.delete(product);
		return true;
	}

	@Override
	public List<ProductDTO> findAll() throws ProductNotFoundException {
		// Retrieve all products from the database
		List<Product> products = productRepository.findAll();
		
		// Throw ProductNotFoundException if no products are found
		if (products.isEmpty()) {
			throw new ProductNotFoundException("Products Not Found. Add Some Products");
		}
		
		// Create a list to hold ProductDTO objects
		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		
		// Populate the list with ProductDTO objects containing product details
		for (Product product : products) {
			ProductDTO productDTO = new ProductDTO();
			productDTO.setBrand(product.getBrand());
			productDTO.setCategoryId(product.getCategoryId());
			productDTO.setProductId(product.getProductId());
			productDTO.setProductImage(product.getProductImage());
			productDTO.setProductName(product.getProductName());
			productDTO.setProductPrice(product.getProductPrice());

			productDTOs.add(productDTO);
		}
		return productDTOs;
	}

	@Override
	public List<ProductDTO> getProductByBrand(String brandName) throws ProductNotFoundException {
		// Retrieve products from the database by brand name
		List<Product> products = productRepository.findByBrand(brandName.toLowerCase());
		
		// Throw ProductNotFoundException if no products are found
		if (products.isEmpty()) {
			throw new ProductNotFoundException("Products of this brand Not Found");
		}
		
		// Create a list to hold ProductDTO objects
		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		
		// Populate the list with ProductDTO objects containing product details
		for (Product product : products) {
			ProductDTO productDTO = new ProductDTO();
			productDTO.setCategoryId(product.getCategoryId());
			productDTO.setBrand(brandName);
			productDTO.setProductId(product.getProductId());
			productDTO.setProductImage(product.getProductImage());
			productDTO.setProductName(product.getProductName());
			productDTO.setProductPrice(product.getProductPrice());

			productDTOs.add(productDTO);
		}
		return productDTOs;
	}

	@Override
	public List<ProductDTO> getProductsByCategoryId(int id) {
		// Retrieve products from the database by category ID
		List<Product> products = productRepository.findProductsByCatogory(id);
		
		// Create a list to hold ProductDTO objects
		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		
		// Populate the list with ProductDTO objects containing product details
		for (Product product : products) {
			ProductDTO productDTO = new ProductDTO();
			productDTO.setBrand(product.getBrand());
			productDTO.setCategoryId(product.getCategoryId());
			productDTO.setProductId(product.getProductId());
			productDTO.setProductImage(product.getProductImage());
			productDTO.setProductName(product.getProductName());
			productDTO.setProductPrice(product.getProductPrice());

			productDTOs.add(productDTO);
		}

		return productDTOs;
	}
	
	public void linkImageToProduct(int productId, String imagePath) {
		// Retrieve product from the database using the provided ID
		Product product = productRepository.findById(productId).orElse(null);
		
		// Set the product image path
		product.setProductImage(imagePath);

		// Save the updated product to the database
		productRepository.save(product);
	}

}
