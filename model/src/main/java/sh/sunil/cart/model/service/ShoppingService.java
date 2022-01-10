/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sh.sunil.cart.model.service;

import sh.sunil.cart.model.dto.ShoppingItem;

import java.util.List;

/**
 * @author cgallen
 */
public interface ShoppingService {

    public List<ShoppingItem> getAvailableItems();

    public boolean purchaseItems(ShoppingCart shoppingCart);

    public boolean checkStock(ShoppingCart shoppingCart, String itemName);

    public ShoppingItem getNewItemByName(String uuid);

}
