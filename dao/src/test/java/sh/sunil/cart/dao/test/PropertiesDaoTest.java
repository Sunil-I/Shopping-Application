package sh.sunil.cart.dao.test;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import sh.sunil.cart.dao.impl.PropertiesDao;
import sh.sunil.cart.dao.impl.PropertiesWebObjectFactory;

import static org.junit.Assert.*;

/**
 * @author Sunil Islam
 */
public class PropertiesDaoTest {
    final String file = PropertiesWebObjectFactory.class.getResource("/test.properties").getPath();
    File propertiesFile = new File(file);
    final Logger log = LogManager.getLogger(PropertiesDaoTest.class);


    @Before
    public void before() {
        // delete file if it exists
        propertiesFile = new File(file);
        if (propertiesFile.exists()) {
            log.debug("Deleting test properties file: " + propertiesFile.getAbsolutePath());
            propertiesFile.delete();
        }
    }

    @Test
    public void testPropertiesDao() {
        PropertiesDao propertiesDao = new PropertiesDao(file);
        log.debug("Setting properties for username, password and url");
        propertiesDao.setProperty("username", "username");
        propertiesDao.setProperty("password", "password");
        propertiesDao.setProperty("url", "http://localhost:8080");
        String url = propertiesDao.getProperty("url");
        log.debug("Testing to see if local url ('https://localhost:8080') matches with the set config value of: " + url);
        assertEquals("http://localhost:8080", url);
    }
}
