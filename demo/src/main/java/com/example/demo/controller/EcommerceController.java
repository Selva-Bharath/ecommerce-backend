package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.service.EcommerceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EcommerceController {

    private final EcommerceService service;

    public EcommerceController(EcommerceService service) {
        this.service = service;
    }

    // ================= AUTH =================

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        return ResponseEntity.ok(service.signup(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            return ResponseEntity.ok(service.login(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ================= PRODUCT =================

    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@RequestBody Product p) {
        return ResponseEntity.ok(service.addProduct(p));
    }

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(service.getProducts());
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody Product product) {

        return ResponseEntity.ok(service.updateProduct(id, product));
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.ok("Deleted Successfully");
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        service.deleteOrder(id);
        return ResponseEntity.ok("Deleted Successfully");
    }

    // ================= CART =================

    @PostMapping("/cart/{userId}")
    public ResponseEntity<Data> addCart(
            @PathVariable Long userId,
            @RequestBody Data data) {

        return ResponseEntity.ok(service.addToCart(userId, data));
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<List<Data>> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getCart(userId));
    }

    @PutMapping("/cart/{id}")
    public ResponseEntity<Data> update(
            @PathVariable Long id,
            @RequestParam int quantity) {

        return ResponseEntity.ok(service.updateQty(id, quantity));
    }

    @DeleteMapping("/cart/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.deleteCart(id);
        return ResponseEntity.ok("Deleted");
    }

    // ================= ORDER =================

    @PostMapping("/orders/{userId}")
    public ResponseEntity<CustomerOrder> placeOrder(
            @PathVariable Long userId,
            @RequestBody CustomerOrder order) {

        return ResponseEntity.ok(service.placeOrder(userId, order));
    }

    @GetMapping("/admin/orders")
    public ResponseEntity<List<CustomerOrder>> getOrders() {
        return ResponseEntity.ok(service.getAllOrders());
    }

    @PutMapping("/admin/order/{id}")
    public ResponseEntity<CustomerOrder> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return ResponseEntity.ok(service.updateOrderStatus(id, status));
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id) {
        try {
            service.deleteOrder(id);
            return ResponseEntity.ok("Order Cancelled Successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ================= FORGOT PASSWORD (FIXED) =================

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePasswordByEmail(
            @RequestParam String email,
            @RequestParam String password) {

        try {
            return ResponseEntity.ok(service.updatePasswordByEmail(email, password));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/user/upload-profile/{userId}")
    public ResponseEntity<?> uploadProfile(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file) {

        try {
            return ResponseEntity.ok(
                    service.uploadProfileImage(userId, file.getBytes())
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/profile-image/{userId}")
    public ResponseEntity<byte[]> getProfile(@PathVariable Long userId) {

        byte[] image = service.getProfileImage(userId);

        if (image == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity
                .ok()
                .header("Content-Type", "image/jpeg")
                .body(image);
    }
}