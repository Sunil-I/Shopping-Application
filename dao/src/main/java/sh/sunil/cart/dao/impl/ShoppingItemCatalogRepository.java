package sh.sunil.cart.dao.impl;

import sh.sunil.cart.model.dto.ShoppingItem;
import sh.sunil.cart.model.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingItemCatalogRepository  extends JpaRepository<ShoppingItem,Long>{
    
}
