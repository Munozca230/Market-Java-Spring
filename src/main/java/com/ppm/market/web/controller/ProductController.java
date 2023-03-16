package com.ppm.market.web.controller;

import com.ppm.market.domain.Product;
import com.ppm.market.domain.repository.ProductRepository;
import com.ppm.market.domain.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Operation(summary = "Get all products", description = "Return a list of all products")
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Get product by id", description = "Return a product with the given id or null if not found")
    @Parameter(name = "id", description = "The id of the product to get", required = true)
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") int productId) {
        return ResponseEntity.of(productService.getProduct(productId));
    }

    @Operation(summary = "Get products by category id", description = "Return a list of products that belong to the given category id or null if not found")
    @Parameter(name = "categoryId", description = "The id of the category to filter by", required = true)
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable("categoryId") int categoryId) {
        return ResponseEntity.of(productService.getByCategory(categoryId));
    }

    @Operation(summary = "Get scarce products", description = "Return a list of products that have less stock than the given quantity")
    @Parameter(name = "quantity", description = "The quantity to compare with the stock of each product", required = true)
    @GetMapping("/scarse/{quantity}")
    public ResponseEntity<List<Product>> getScarseProducts(@PathVariable("quantity") int quantity) {
        return ResponseEntity.of(productService.getScarseProducts(quantity));
    }

    @Operation(summary = "Save a product", description = "Save a new or existing product and return it")
    @Parameter(name="product", description="The product to save as JSON object", required=true)
    @ApiResponse(responseCode="201",description="Product created successfully")
    @ApiResponse(responseCode="400",description="Invalid input")
    @ApiResponse(responseCode="409",description="Product already exists")
    @PostMapping("/save")
    public ResponseEntity<Product> save(@RequestBody Product product) {
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @Operation(summary="Delete a product by id",description="Delete a product with the given id and return no content or not found if it does not exist")
    @Parameter(name="id",description="The id of the product to delete",required=true)
    @ApiResponse(responseCode="204",description="Product deleted successfully")
    @ApiResponse(responseCode="404",description="Product not found")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") int productId) {
        return new ResponseEntity(this.productService.delete(productId)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND);
    }
}