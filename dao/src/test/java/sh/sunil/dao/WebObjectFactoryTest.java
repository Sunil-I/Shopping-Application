package sh.sunil.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Sunil Islam
 */
public class WebObjectFactoryTest {
    final Logger log = LogManager.getLogger(PropertiesDaoTest.class);

    @Test
    public void testGetPropertiesDao() {
        PropertiesDao propertiesDao = WebObjectFactory.getPropertiesDao();
        String url = propertiesDao.getProperty("url");
        log.debug("Testing to see if local url ('https://localhost:8080') matches with the set config value of: " + url);
        assertEquals("http://localhost:8080", url);
    }
}