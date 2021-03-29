package com.raymon.frame.config.guzz;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.guzz.builder.GuzzConfigFileBuilder;
import org.guzz.exception.GuzzException;
import org.guzz.orm.sql.CompiledSQL;
import org.guzz.service.ServiceConfig;
import org.guzz.service.core.impl.AbstractDynamicSQLService;
import org.guzz.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class ClassPathSQLService extends AbstractDynamicSQLService {

	
	protected String sqlpackage;
	
	protected String encoding ;
	
	protected boolean overrideSqlInGuzzXML;
	
	protected boolean available;
	
	
	public CompiledSQL getSql(String id) {
		return loadCompiledSQLById(id);
	}
	

	

	protected String getFilePath(String id){
		return sqlpackage + "/" + id.replace(".", "/") + ".xml";
	}
	
	protected CompiledSQL loadCompiledSQLById(String id){
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		String path = getFilePath(id);
		try {
			InputStream bais = loader.getResourceAsStream(path);
			return loadCSFromStream(id, bais);
		} catch (Exception e) {
			log.error("cann't load sql. id:{" + id + "], file:" + path, e) ;
		}
		return null ;
	}
	
	
	protected CompiledSQL loadCSFromStream(String id, InputStream is) throws Exception{
		SAXReader reader = null;
		Document document = null;
		
		reader = new SAXReader();
		reader.setValidation(false) ;
			
		//http://apache.org/xml/features/nonvalidating/load-external-dtd"  
//		reader.setFeature(Constants.XERCES_FEATURE_PREFIX + Constants.LOAD_EXTERNAL_DTD_FEATURE, false);
		
		InputStreamReader isr = new InputStreamReader(is, encoding) ;
		    
		document = reader.read(isr);
		final Element root = document.getRootElement();
			
		CompiledSQL cs = loadCompiledSQL(id, root) ;
	        
	    return cs ;
	}

	/**
	 * 加载配置的sql语句。sql语句加载时自动和ObjectMapping进行关联。其中在<sqlMap></sqlMap>内定义的orm只在本sqlMap有效，
	 * 不会保存到系统的 @link ObjectMappingManager 中，只对本sqlMap内的sql语句有效。
	 * @return (@link Map) id~~CompiledSQL
	 */
	@SuppressWarnings("rawtypes")
	protected CompiledSQL loadCompiledSQL(String id, Element root) throws IOException, ClassNotFoundException{
		Map css = GuzzConfigFileBuilder.loadSQLMap(this.guzzContext, templatedSQLService, this.guzzContext.getObjectMappingManager() , compiledSQLBuilder, root, false) ;
		
		if(css.isEmpty()){
			throw new GuzzException("no sql found for id:" + id) ;
		}else if(css.size() > 1){
			throw new GuzzException("Only one sql is allowed in xml:[" + this.getFilePath(id) + "] for id:" + id) ;
		}else{
			return (CompiledSQL) css.values().iterator().next() ;
		}
	}
	
	public boolean overrideSqlInGuzzXML() {
		return overrideSqlInGuzzXML ;
	}

	public boolean configure(ServiceConfig[] scs) {
		if(scs.length == 0){
			log.warn("FileDynamicSQLServiceImpl is not started, no configuration found.") ;
			return false ;
		}
		
		ServiceConfig sc = scs[0] ;
		
		this.sqlpackage = sc.getProps().getProperty("sqlpackage", "");
		this.encoding = sc.getProps().getProperty("encoding", "UTF-8") ;
		this.overrideSqlInGuzzXML = StringUtil.toBoolean(sc.getProps().getProperty("overrideSqlInGuzzXML"), false) ;
		
		return true ;
	}

	public boolean isAvailable() {
		return available ;
	}

	public void shutdown() {
		available = false ;
	}

	public void startup() {
		available = true ;
	}
}
