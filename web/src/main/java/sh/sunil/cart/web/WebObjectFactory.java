/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sh.sunil.cart.web;

import sh.sunil.cart.service.ShoppingServiceImpl;
import sh.sunil.cart.service.ShoppingCartImpl;
import sh.sunil.cart.model.service.ShoppingCart;
import sh.sunil.cart.model.service.ShoppingService;
import sh.sunil.cart.service.ServiceObjectFactory;

/**
 *
 * @author cgallen
 */
public class WebObjectFactory {

    static ShoppingService shoppingService = ServiceObjectFactory.getShoppingService();
    
    // cannot instantiate
    private WebObjectFactory(){
        
    }
    
    public static ShoppingService getShoppingService(){
        return shoppingService;
    }
    
    public static ShoppingCart getNewShoppingCart(){
        return ServiceObjectFactory.getNewShoppingCart();
    }
    
}
