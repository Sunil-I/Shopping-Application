/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sh.sunil.cart.service.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sh.sunil.cart.model.service.ShoppingService;
import sh.sunil.cart.service.ServiceObjectFactory;

/**
 *
 * @author cgallen
 */
public class ShoppingServiceTest {
    
    ShoppingService shoppingService = null;

    @Before
    public void before(){
        shoppingService = ServiceObjectFactory.getShoppingService();
        
    }
    
    @Test
    public void test1() {
        assertNotNull(shoppingService);
    }
}
