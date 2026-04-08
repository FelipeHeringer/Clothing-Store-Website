package com.fhcs.clothing_store.repository.order;
 
import java.util.List;
 
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.fhcs.clothing_store.entity.order.Order;
import com.fhcs.clothing_store.entity.order.OrderStatus;
 
public interface OrderRepository extends JpaRepository<Order, Integer> {
 
    Page<Order> findByIndividual_IndividualId(Integer individualId, Pageable pageable);
 
    List<Order> findByIndividual_IndividualIdAndStatus(Integer individualId, OrderStatus status);
}