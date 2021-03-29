package com.raymon.frame.config.base;

import com.raymon.frame.utils.SpringUtil;
import io.seata.rm.datasource.DataSourceProxy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.guzz.connection.DataSourceProvider;
import org.guzz.util.CloseUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class IgC3P0DataSourceProvider implements DataSourceProvider {
    private static transient final Log log = LogFactory.getLog(IgC3P0DataSourceProvider.class);
    DataSourceProxy c3p0 = null;

    public void configure(Properties props, int maxLoad) {
        if (c3p0 == null) {
            c3p0 = (DataSourceProxy) SpringUtil.getBean("dataSourceProxy");
        }

       /* JavaBeanWrapper bw = BeanWrapper.createPOJOWrapper(c3p0.getClass()) ;
        Enumeration e = props.keys() ;
        while(e.hasMoreElements()){
            String key = (String) e.nextElement() ;
            String value = props.getProperty(key) ;

            try{
                bw.setValueAutoConvert(c3p0, key, value) ;
            }catch(Exception e1){
                log.error("unkown property:[" + key + "=" + value + "]", e1) ;
            }
        }*/

        //数据库最大连接500
        if (maxLoad > 1000 || maxLoad < 1) {
            maxLoad = 500;
        }

        // c3p0.getTargetDataSource().setMaxPoolSize(maxLoad) ;

        //fetch a connection to force c3p0 building the pool
        Connection c = null;
        try {
            c = c3p0.getConnection();
        } catch (SQLException e1) {
            log.error(props, e1);
        } finally {
            CloseUtil.close(c);
        }
    }

    public DataSource getDataSource() {
        return c3p0;
    }

    public void shutdown() {
        if (c3p0 != null) {
            // c3p0.getTargetDataSource().close() ;
            c3p0 = null;
        }
    }


}
