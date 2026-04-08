package com.fhcs.clothing_store.repository.cart;
 
import java.util.Optional;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.fhcs.clothing_store.entity.cart.Cart;
import com.fhcs.clothing_store.entity.cart.CartStatus;
 
public interface CartRepository extends JpaRepository<Cart, Integer> {
 
    Optional<Cart> findByIndividual_IndividualIdAndStatus(Integer individualId, CartStatus status);
}