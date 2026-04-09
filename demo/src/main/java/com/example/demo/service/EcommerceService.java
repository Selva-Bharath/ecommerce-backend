package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EcommerceService {

    private final UserRepository userRepo;
    private final ProductRepository productRepo;
    private final CartRepository cartRepo;
    private final OrderRepository orderRepo;

    @Autowired

    public EcommerceService(UserRepository userRepo,
                            ProductRepository productRepo,
                            CartRepository cartRepo,
                            OrderRepository orderRepo) {
        this.userRepo = userRepo;
        this.productRepo = productRepo;
        this.cartRepo = cartRepo;
        this.orderRepo = orderRepo;
    }

    // ================= AUTH =================

    public String signup(User user) {

        if (user.getRole() == null) {
            user.setRole("user");
        }

        if (userRepo.findByEmail(user.getEmail()) != null) {
            return "Email already exists";
        }

        if ("admin".equalsIgnoreCase(user.getRole())) {
            long adminCount = userRepo.countByRole("admin");

            if (adminCount >= 3) {
                return "Please select the User Role";
            }
        }
        // ✅ DELIVERY BOY LIMIT (ONLY 1)
        if ("delivery".equals(user.getRole())) {
            long deliveryCount = userRepo.countByRole("delivery");

            if (deliveryCount >= 1) {
                return "Please select the User Role";
            }
        }

        userRepo.save(user);
        return "Signup successful";
    }

    public User login(User user) {
        User db = userRepo.findByEmail(user.getEmail());

        if (db == null) throw new RuntimeException("USER_NOT_FOUND");
        if (!db.getPassword().equals(user.getPassword()))
            throw new RuntimeException("WRONG_PASSWORD");

        db.setPassword(null);
        return db;
    }

    // ================= PRODUCT =================

    public Product addProduct(Product product) {
        return productRepo.save(product);
    }

    public List<Product> getProducts() {
        return productRepo.findAll();
    }

    public Product updateProduct(Long id, Product product) {

        Product existing = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setTitle(product.getTitle());
        existing.setPrice(product.getPrice());

        if (product.getImages() != null && !product.getImages().isEmpty()) {
            existing.setImages(product.getImages());
        }

        return productRepo.save(existing);
    }

    // ================= CART =================

    public Data addToCart(Long userId, Data data) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (data.getQuantity() == 0) {
            data.setQuantity(1);
        }

        if (data.getImages() == null) {
            data.setImages("");
        }

        data.setUser(user);

        return cartRepo.save(data);
    }

    public List<Data> getCart(Long userId) {
        return cartRepo.findByUser_Id(userId);
    }

    public Data updateQty(Long id, int qty) {
        Data d = cartRepo.findById(id).orElseThrow();
        d.setQuantity(qty);
        return cartRepo.save(d);
    }

    public void deleteCart(Long id) {
        cartRepo.deleteById(id);
    }

    // ================= ORDER =================

    public CustomerOrder placeOrder(Long userId, CustomerOrder order) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        order.setUser(user);
        order.setStatus(OrderStatus.PLACED);

        return orderRepo.save(order);
    }

    public List<CustomerOrder> getAllOrders() {
        return orderRepo.findAllByOrderByIdDesc();
    }

    public CustomerOrder updateOrderStatus(Long id, String status) {

        CustomerOrder order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.valueOf(status));

        return orderRepo.save(order);
    }

    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }

    public void deleteOrder(Long id) {
        orderRepo.deleteById(id);
    }

    // ================= PASSWORD =================

    // ================= PASSWORD =================

    public String updatePasswordByEmail(String email, String newPassword) {

        User user = userRepo.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        user.setPassword(newPassword);
        userRepo.save(user);

        return "Password updated successfully";
    }

    public String uploadProfileImage(Long userId, byte[] image) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setProfileImage(image);
        userRepo.save(user);

        return "Uploaded";
    }

    public byte[] getProfileImage(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getProfileImage();
    }

}