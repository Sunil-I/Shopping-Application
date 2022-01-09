package sh.sunil.cart.dao.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sh.sunil.cart.model.dto.ShoppingItem;

import java.util.List;
import java.util.Optional;


@Repository
public interface ShoppingItemCatalogRepository extends JpaRepository<ShoppingItem, Long> {

    @Query("select i from ShoppingItem i where i.name like concat('%', :name,'%')")
    public List<ShoppingItem> findByName(@Param("name") String name);

    @Query("select i from ShoppingItem i where i.id = i.id")
    public List<ShoppingItem> findById(@Param("id") Integer id);


    @Query("select i from ShoppingItem i")
    public List<ShoppingItem> findAll();
}
