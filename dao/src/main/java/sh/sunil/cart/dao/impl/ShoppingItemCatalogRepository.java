package sh.sunil.cart.dao.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sh.sunil.cart.model.dto.ShoppingItem;

import java.util.List;


@Repository
public interface ShoppingItemCatalogRepository extends JpaRepository<ShoppingItem, Long> {

    @Query("select i from ShoppingItem i where i.name = i.name")
    public List<ShoppingItem> findByName(@Param("name") String name);

    @Query("select i from ShoppingItem i")
    public List<ShoppingItem> findAll();
}
