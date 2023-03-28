package com.ppm.market.web.controller;

import com.ppm.market.domain.Purchase;
import com.ppm.market.domain.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/purchases")
public class PurchaseController {
    @Autowired
    PurchaseService purchaseService;

    @Operation(summary = "Get all purchases", description = "Return a list of all purchases")
    @GetMapping("/all")
    public ResponseEntity<List<Purchase>> getAll(){
        return new ResponseEntity<>(purchaseService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Get purchases by client id", description = "Return a list of purchases that belong to the given client id or null if not found")
    @Parameter(name = "idClient", description = "The id of the client to filter by", required = true)
    @GetMapping("/client/{idClient}")
    ResponseEntity<List<Purchase>> getByClient(@PathVariable("idClient") String clientId) {
        return ResponseEntity.of(purchaseService.getByClient(clientId));
    }

    @Operation(summary = "Save a purchase", description = "Save a new or existing purchase and return it")
    @Parameter(name="purchase", description="The purchase to save as JSON object", required=true)
    @ApiResponse(responseCode="201",description="Purchase created successfully")
    @ApiResponse(responseCode="400",description="Invalid input")
    @ApiResponse(responseCode="409",description="Purchase already exists")
    @GetMapping("/save")
    ResponseEntity<Purchase> save(@RequestBody Purchase purchase) {
        return new ResponseEntity<>(purchaseService.save(purchase), HttpStatus.CREATED);
    }

}