package com.example.demo.repository;

import com.example.demo.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {

    // ✅ ALL ORDERS (ADMIN)
    List<CustomerOrder> findAllByOrderByIdDesc();

    // ✅ USER SPECIFIC ORDERS 🔥 (THIS WAS MISSING)
    List<CustomerOrder> findByUser_IdOrderByIdDesc(Long userId);
}