package com.raymon.taxguide.config;

import com.raymon.taxguide.web.view.JsonDataView;
import com.raymon.taxguide.web.view.LoggingHandlerExceptionResolver;
import com.raymon.taxguide.web.view.MultipleViewResolver;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.*;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.xslt.XsltViewResolver;

import javax.sql.DataSource;
import java.util.*;

@Configuration
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

        UrlBasedViewResolver urlBasedViewResolver = new UrlBasedViewResolver();
        urlBasedViewResolver.setViewClass(JsonDataView.class);
        urlBasedViewResolver.setApplicationContext(applicationContext);
        resolvers.put(".json", urlBasedViewResolver);

        multipleViewResolver.setResolvers(resolvers);
        return multipleViewResolver;
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

    @Bean("txManager")
    public DataSourceTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /*事务拦截器*/
    @Bean("txAdvice")
    public TransactionInterceptor txAdvice(DataSourceTransactionManager txManager){

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        /*只读事务，不做更新操作*/
        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        readOnlyTx.setReadOnly(true);
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED );
        //当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务
        //RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        //requiredTx.setRollbackRules(
        //    Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        //requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED,
                Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        requiredTx.setTimeout(5);
        Map<String, TransactionAttribute> txMap = new HashMap<>();
        txMap.put("*", requiredTx);
        txMap.put("get*", readOnlyTx);
        txMap.put("find*", readOnlyTx);
        txMap.put("search*", readOnlyTx);
        source.setNameMap( txMap );
        return new TransactionInterceptor(txManager ,source) ;
    }

    /**切面拦截规则 参数会自动从容器中注入*/
    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor(TransactionInterceptor txAdvice){
        DefaultPointcutAdvisor pointcutAdvisor = new DefaultPointcutAdvisor();
        pointcutAdvisor.setAdvice(txAdvice);
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution (* com.raymon.taxguide.repository.*.*(..)) || execution (* com.raymon.taxguide.service.*.*(..))");
        pointcutAdvisor.setPointcut(pointcut);
        return pointcutAdvisor;
    }
}
