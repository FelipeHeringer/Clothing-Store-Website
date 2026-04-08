package com.fhcs.clothing_store.repository.cart;
 
import java.util.Optional;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.fhcs.clothing_store.entity.cart.CartItem;
 
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
 
    Optional<CartItem> findByCart_CartIdAndVariation_VariationId(Integer cartId, Integer variationId);
}