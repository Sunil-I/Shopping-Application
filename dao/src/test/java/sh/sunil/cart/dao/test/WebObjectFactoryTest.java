package sh.sunil.cart.dao.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import sh.sunil.cart.dao.impl.PropertiesDao;
import sh.sunil.cart.dao.impl.PropertiesWebObjectFactory;


/**
 *
 * @author Sunil Islam
 */
public class WebObjectFactoryTest {
    final Logger log = LogManager.getLogger(WebObjectFactoryTest.class);

    @Test
    public void testGetPropertiesDao() {
        PropertiesDao propertiesDao = PropertiesWebObjectFactory.getPropertiesDao();
        String BANK_API_URL = propertiesDao.getProperty("BANK_API_URL");
        log.debug("Testing to see if BANK_API_URL exists");
        assertEquals(true, BANK_API_URL != null);
    }
}
