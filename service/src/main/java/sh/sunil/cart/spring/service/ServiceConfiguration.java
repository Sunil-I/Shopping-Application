/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sh.sunil.cart.spring.service;

import sh.sunil.cart.dao.impl.PersistenceJPAConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *
 * @author cgallen
 */
@Configuration

@ComponentScan(basePackages = {"sh.sunil.cart.service",
    "sh.sunil.cart.spring.service"
})
@Import({PersistenceJPAConfig.class})
public class ServiceConfiguration {

}
