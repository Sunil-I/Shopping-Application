/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sh.sunil.cart.spring.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sh.sunil.cart.dao.impl.ShoppingItemCatalogRepository;
import sh.sunil.cart.dao.impl.UserRepository;
import sh.sunil.cart.model.dto.ShoppingItem;
import sh.sunil.cart.model.dto.User;
import sh.sunil.cart.model.dto.UserRole;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * @author cgallen
 */
@Component("PopulateDatabaseOnStart")
public class PopulateDatabaseOnStart {

    private static final Logger LOG = LogManager.getLogger(PopulateDatabaseOnStart.class);

    private static final String DEFAULT_ADMIN_USERNAME = "globaladmin";
    private static final String DEFAULT_ADMIN_PASSWORD = "globaladmin";

    private static final String DEFAULT_USER_PASSWORD = "user1234";
    private static final String DEFAULT_USER_USERNAME = "user1234";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShoppingItemCatalogRepository shoppingItemCatalogRepository;


    @PostConstruct
    public void initDatabase() {
        LOG.debug("initialising database with startup values");

        // initialising admin and normal user if dont exist
        User adminUser = new User();
        adminUser.setUsername(DEFAULT_ADMIN_USERNAME);
        adminUser.setFirstName("default administrator");
        adminUser.setPassword(DEFAULT_ADMIN_PASSWORD);
        adminUser.setUserRole(UserRole.ADMINISTRATOR);
        adminUser.setCardNumber("5133880000000012");
        adminUser.setCardSortCode("513388");
        adminUser.setCardExpiry("01/26");
        List<User> users = userRepository.findByUsername(DEFAULT_ADMIN_USERNAME);
        if (users.isEmpty()) {
            userRepository.save(adminUser);
            LOG.info("creating new default admin user:" + adminUser);
        } else {
            LOG.info("default admin user already exists. Not creating new :" + adminUser);
        }

        User defaultUser = new User();
        defaultUser.setUsername(DEFAULT_USER_USERNAME);
        defaultUser.setFirstName("default user");
        defaultUser.setPassword(DEFAULT_USER_PASSWORD);
        defaultUser.setUserRole(UserRole.CUSTOMER);
        defaultUser.setCardNumber("5133880000000012");
        defaultUser.setCardSortCode("513388");
        defaultUser.setCardExpiry("01/26");
        users = userRepository.findByUsername(DEFAULT_USER_USERNAME);
        if (users.isEmpty()) {
            userRepository.save(defaultUser);
            LOG.info("creating new default user:" + defaultUser);
        } else {
            LOG.info("defaultuser already exists. Not creating new :" + defaultUser);
        }
        // add items

        List<ShoppingItem> itemList = Arrays.asList(new ShoppingItem("house", 20000.00, 5),
                new ShoppingItem("hen", 5.00, 10),
                new ShoppingItem("car", 5000.00, 20),
                new ShoppingItem("pet alligator", 65.00, 30)
        );

        for (ShoppingItem item : itemList) {
            shoppingItemCatalogRepository.save(item);
        }

        LOG.debug("database initialised");
    }
}
