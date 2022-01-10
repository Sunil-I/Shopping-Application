package sh.sunil.cart.dao.impl;

import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Sunil Islam
 */
public class PropertiesWebObjectFactory {

    final static Logger LOG = LogManager.getLogger(PropertiesWebObjectFactory.class);

    private static PropertiesDao propertiesDao = null;

    public static PropertiesDao getPropertiesDao() {
        if (propertiesDao == null) {
            synchronized (PropertiesWebObjectFactory.class) {
                if (propertiesDao == null) {
                    // creates a single instance of the dao
                    String file = PropertiesWebObjectFactory.class.getResource("/config.properties").getPath();
                    File propertiesFile = new File(file);

                    LOG.debug("using application properties file : " + propertiesFile.getAbsolutePath());
                    propertiesDao = new PropertiesDao(propertiesFile.getAbsolutePath());
                }
            }
        }
        return propertiesDao;
    }

}
