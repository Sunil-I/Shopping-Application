/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sh.sunil.cart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import sh.sunil.cart.dao.impl.ShoppingItemCatalogRepository;
import sh.sunil.cart.model.dto.ShoppingItem;
import sh.sunil.cart.model.service.ShoppingCart;
import sh.sunil.cart.model.service.ShoppingService;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@DependsOn({"PopulateDatabaseOnStart"})
public class ShoppingServiceImpl implements ShoppingService {

    // note ConcurrentHashMap instead of HashMap if map can be altered while being read
    private Map<String, ShoppingItem> itemMap = new ConcurrentHashMap<String, ShoppingItem>();

    @Autowired
    private ShoppingItemCatalogRepository shoppingItemCatalogRepository;


    public ShoppingServiceImpl() {

    }

    @Override
    public List<ShoppingItem> getAvailableItems() {
        return shoppingItemCatalogRepository.findAll();
    }

    @Override
    public boolean purchaseItems(ShoppingCart shoppingCart) {
        System.out.println("purchased items");
        for (ShoppingItem shoppingItem : shoppingCart.getShoppingCartItems()) {
            System.out.println(shoppingItem);
        }

        return true;
    }


    @Override
    public boolean checkStock(ShoppingCart cart, String itemName) {
        for (ShoppingItem item : cart.getShoppingCartItems()) {
            //Fetch Item from DB first
            ShoppingItem shoppingItem = shoppingItemCatalogRepository.findByName(item.getName()).get(0);
            if (shoppingItem != null) {
                //Check Quantity
                if ((shoppingItem.getQuantity() - item.getQuantity()) < 1 && itemName.equals(item.getName())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public ShoppingItem getNewItemByName(String name) {
        ShoppingItem templateItem = shoppingItemCatalogRepository.findByName(name).get(0);

        if (templateItem == null) return null;

        ShoppingItem item = new ShoppingItem();
        item.setName(name);
        item.setPrice(templateItem.getPrice());
        item.setQuantity(0);
        item.setUuid(UUID.randomUUID().toString());
        return item;
    }

}
