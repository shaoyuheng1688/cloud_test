package com.raymon.frame.config.base;

import com.raymon.frame.generator.UUIDHexGenerator;
import com.raymon.frame.web.aop.PackageBeanNameAutoProxyCreator;
import com.raymon.frame.web.view.JsonDataView;
import com.raymon.frame.web.view.LoggingHandlerExceptionResolver;
import com.raymon.frame.web.view.MultipleViewResolver;
import com.raymon.frame.web.view.XmlDataView;
import com.raymon.frame.web.view.XmlDataViewSD;
import com.raymon.frame.web.view.XsltView;
import io.seata.rm.datasource.DataSourceProxy;
import org.guzz.GuzzContext;
import org.guzz.dao.GuzzBaseDao;
import org.guzz.web.context.spring.GuzzContextBeanFactory;
import org.guzz.web.context.spring.GuzzTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.xslt.XsltViewResolver;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Configuration
@PropertySource(value = "classpath:guzz/fms.properties")
public class ServletContextConfig {
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public CookieLocaleResolver localeResolver() {
        return new CookieLocaleResolver();
    }

    @Bean
    public LoggingHandlerExceptionResolver loggingHandlerExceptionResolver() {
        return new LoggingHandlerExceptionResolver();
    }

    @Bean
    public MultipleViewResolver multipleViewResolver() {
        MultipleViewResolver multipleViewResolver = new MultipleViewResolver();
        multipleViewResolver.setViewClass(InternalResourceView.class);

        Map<String, ViewResolver> resolvers = new HashMap<String, ViewResolver>();
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/WEB-INF/jsp/");
        internalResourceViewResolver.setSuffix(".jsp");
        internalResourceViewResolver.setApplicationContext(applicationContext);
        resolvers.put(".do", internalResourceViewResolver);

        XsltViewResolver xsltViewResolver_xsl = new XsltViewResolver();
        xsltViewResolver_xsl.setViewClass(XsltView.class);
        xsltViewResolver_xsl.setCacheTemplates(false);
        xsltViewResolver_xsl.setPrefix("/WEB-INF/xslt/");
        xsltViewResolver_xsl.setSuffix(".xsl");
        xsltViewResolver_xsl.setApplicationContext(applicationContext);
        resolvers.put(".xsl", xsltViewResolver_xsl);

        XsltViewResolver xsltViewResolver_xml = new XsltViewResolver();
        xsltViewResolver_xml.setViewClass(XmlDataView.class);
        xsltViewResolver_xml.setApplicationContext(applicationContext);
        resolvers.put(".xml", xsltViewResolver_xml);

        XsltViewResolver xsltViewResolver_xmlsd = new XsltViewResolver();
        xsltViewResolver_xmlsd.setViewClass(XmlDataViewSD.class);
        xsltViewResolver_xmlsd.setSourceKey("queue.sourcekey");
        xsltViewResolver_xmlsd.setApplicationContext(applicationContext);
        resolvers.put(".xmlsd", xsltViewResolver_xmlsd);

        UrlBasedViewResolver urlBasedViewResolver = new UrlBasedViewResolver();
        urlBasedViewResolver.setViewClass(JsonDataView.class);
        urlBasedViewResolver.setApplicationContext(applicationContext);
        resolvers.put(".json", urlBasedViewResolver);

        multipleViewResolver.setResolvers(resolvers);
        return multipleViewResolver;
    }


    @Bean
    @ConditionalOnProperty(prefix = "seata", name = "enabled", havingValue = "true", matchIfMissing = true)
    @ConfigurationProperties(prefix = "seata")
    public DataSource dataSource() {
        return DataSourceBuilder.create().type(com.mchange.v2.c3p0.ComboPooledDataSource.class).build();
    }

    @Bean
    @ConditionalOnProperty(prefix = "seata", name = "enabled", havingValue = "true", matchIfMissing = true)
    public DataSourceProxy dataSourceProxy(DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

    @Bean(name = "guzzContext")
    @DependsOn("springUtil")
    public GuzzContext guzzContext() throws Exception {
//		Resource resource = new ClassPathResource("guzz/guzz.xml");
//		return GuzzContextBeanFactory.createGuzzContext(resource);
//		guzz支持springboot使用内置的服务启动
        return GuzzContextBeanFactory.createGuzzContextByPath("classpath:guzz/guzz.xml");
    }

    //	定义事务管理器（声明式的事务）
    @Bean
    public GuzzTransactionManager transactionMgmter(GuzzContext guzzContext) {
        GuzzTransactionManager guzzTransactionManager = new GuzzTransactionManager();
        guzzTransactionManager.setGuzzContext(guzzContext);
        return guzzTransactionManager;
    }

    @Bean
    public GuzzBaseDao guzzBaseDao(GuzzContext guzzContext) {
        GuzzBaseDao guzzBaseDao = new GuzzBaseDao();
        guzzBaseDao.setGuzzContext(guzzContext);
        return guzzBaseDao;
    }


    @Bean
    public TransactionInterceptor guzzTransactionInterceptor(GuzzTransactionManager transactionMgmter) {
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
        transactionInterceptor.setTransactionManager(transactionMgmter);
        Properties properties = new Properties();
        properties.setProperty("*", "PROPAGATION_REQUIRED");
        transactionInterceptor.setTransactionAttributes(properties);
        return transactionInterceptor;
    }

    @Bean
    public PackageBeanNameAutoProxyCreator packageBeanNameAutoProxyCreator() {
        PackageBeanNameAutoProxyCreator packageBeanNameAutoProxyCreator = new PackageBeanNameAutoProxyCreator();

        String[] beanNamesArray = {"*Dao", "*DaoImp", "*Repository", "*RepositoryImp", "*Service", "*ServiceImp", "*Manager", "*DataFetch"};

        packageBeanNameAutoProxyCreator.setBeanNames(beanNamesArray);

        packageBeanNameAutoProxyCreator.setInterceptorNames("guzzTransactionInterceptor");

        List<String> includePackages = new ArrayList<String>();
        includePackages.add("com.raymon");
        packageBeanNameAutoProxyCreator.setIncludePackages(includePackages);

        return packageBeanNameAutoProxyCreator;
    }

    /**
     * 配置内置tomcat中url可接收的特殊字符
     *
     * @return
     */
    @Bean
    public ServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
        tomcatServletWebServerFactory.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> connector.setProperty("relaxedQueryChars", "[]{}"));
        return tomcatServletWebServerFactory;
    }

    @Bean
    public UUIDHexGenerator uuidHexGenerator() {
        return new UUIDHexGenerator();
    }
}
