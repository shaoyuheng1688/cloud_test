package com.raymon.frame.config.guzz;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.guzz.builder.GuzzConfigFileBuilder;
import org.guzz.exception.GuzzException;
import org.guzz.orm.ObjectMapping;
import org.guzz.orm.mapping.ObjectMappingUtil;
import org.guzz.orm.mapping.ResultMapBasedObjectMapping;
import org.guzz.orm.rdms.TableColumn;
import org.guzz.orm.sql.CompiledSQL;
import org.guzz.service.ServiceConfig;
import org.guzz.service.core.impl.AbstractDynamicSQLService;
import org.guzz.util.Assert;
import org.guzz.util.ClassUtil;
import org.guzz.util.StringUtil;
import org.xeustechnologies.jcl.JarResources;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class ResourceDynamicSQLService extends AbstractDynamicSQLService {


    protected String sqljarpath ;
    protected String sqlpackage;

    protected String encoding ;

    protected boolean overrideSqlInGuzzXML ;

    private boolean available = false;

    private String myjardir ;

    private boolean useCache ;

    @SuppressWarnings("rawtypes")
    protected Map cachedCS = new HashMap() ;

    public CompiledSQL getSql(String id) {
        if(!useCache){
            return loadCompiledSQLById(id);
        }

        CachedCompiledSQL ccs = getFromCache(id);
        if(ccs == null){
            CompiledSQL cs = loadCompiledSQLById(id);
            if(cs != null){
                ccs = new CachedCompiledSQL(id, cs);
                putToCache(id, ccs);
            }

            return cs ;
        }else{
            if(isDirtyInCache(ccs)){
                CompiledSQL cs = loadCompiledSQLById(id) ;
                if(cs == null){
                    removeFromCache(id) ;
                }else{
                    ccs.setCompiledSQL(cs) ;
                    putToCache(id, ccs) ;
                }
                return cs ;
            }else{
                return ccs.getCompiledSQL() ;
            }
        }
    }

    public CachedCompiledSQL getFromCache(String id){
        return (CachedCompiledSQL) cachedCS.get(id) ;
    }

    public boolean isDirtyInCache(CachedCompiledSQL ccs){
        File f = null;

        try {
            URL url = new URL(this.myjardir + this.sqljarpath);
            try {
                f = new File(url.toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        if(f == null) return false;
        long timeNow = f.lastModified() ;

        long timeBefore = ((Long) ccs.getMark()).longValue() ;

        return timeNow != timeBefore ;
    }

    public void putToCache(String id, CachedCompiledSQL ccs){
        File f = null;
        try {
            URL url = new URL(this.myjardir + this.sqljarpath);
            f = new File(url.toURI());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        long time = null == f ? 0 : f.lastModified() ;

        ccs.setMark(new Long(time)) ;
        cachedCS.put(id, ccs) ;
    }

    public void removeFromCache(String id){
        cachedCS.remove(id) ;
    }



    protected CompiledSQL loadCompiledSQLById(String id){
        String path = sqlpackage + id.replace(".", "/") + ".xml";
        try {
            JarResources jr = new JarResources();
            URL url = new URL(myjardir + sqljarpath);
            jr.loadJar(url);
            //Map<String, byte[]> data =  jr.getResources();
            InputStream bais = new ByteArrayInputStream(jr.getResource(path));
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
        if(!"sqlMap".equals(root.getName())){
            throw new GuzzException("xml document should be in <sqlMap></sqlMap>") ;
        }

        String m_dbgroup = root.attributeValue("dbgroup") ;

        //select可以接收@xxx的orm，update不允许接收。必须分开。
        Element s_node = (Element) root.selectSingleNode("//sqlMap/select") ;
        if(s_node != null){
            String ormName = s_node.attributeValue("orm") ;
            String resultClass = s_node.attributeValue("result-class") ;
            String sql = s_node.getTextTrim() ;
            sql = StringUtil.replaceString(sql, "\r\n", " ") ;
            sql = StringUtil.replaceString(sql, "\n", " ") ;

            Class beanCls = StringUtil.notEmpty(resultClass) ? ClassUtil.getClass(resultClass) : null ;
            CompiledSQL cs = null ;

            ObjectMapping localORM = this.loadORM(root, ormName, m_dbgroup) ;
            if(localORM != null){
                cs = compiledSQLBuilder.buildCompiledSQL(localORM, sql) ;
            }else{
                cs = compiledSQLBuilder.buildCompiledSQL(ormName, sql) ;
            }

            if(beanCls != null){
                cs.setResultClass(beanCls) ;
            }

            //Register parameters' types.
            GuzzConfigFileBuilder.loadParamPropsMapping(cs, (Element) s_node.selectSingleNode("paramsMapping")) ;

            return cs ;
        }

        Element u_node = (Element) root.selectSingleNode("//sqlMap/update") ;
        if(u_node != null){
            String m_orm = u_node.attributeValue("orm") ;
            String sql = u_node.getTextTrim() ;
            sql = StringUtil.replaceString(sql, "\r\n", " ") ;
            sql = StringUtil.replaceString(sql, "\n", " ") ;

            //首先提取本sqlmap内的orm信息，这些orm优先于global orm定义。
            ObjectMapping localORM = this.loadORM(root, m_orm, m_dbgroup) ;
            CompiledSQL cs = null ;

            if(localORM != null){
                cs = compiledSQLBuilder.buildCompiledSQL(localORM, sql) ;
            }else{
                cs = compiledSQLBuilder.buildCompiledSQL(m_orm, sql) ;
            }

            //Register parameters' types.
            GuzzConfigFileBuilder.loadParamPropsMapping(cs, (Element) u_node.selectSingleNode("paramsMapping")) ;

            return cs ;
        }

        throw new GuzzException("no sql found for id:[" + id + "]") ;
    }

    @SuppressWarnings("rawtypes")
    protected ResultMapBasedObjectMapping loadORM(Element root, String ormId, String parentDbGroup) throws IOException, ClassNotFoundException{
        List ormFragments = root.selectNodes("orm") ;
        if(ormFragments.isEmpty()) return null ;

        for(int i = 0 ; i < ormFragments.size() ; i++){
            Element ormFragment = (Element) ormFragments.get(0) ;

            String m_id = ormFragment.attributeValue("id") ;
            Assert.assertNotEmpty(m_id, "invalid id. xml is:" + ormFragment.asXML()) ;

            if(!ormId.equals(m_id)) continue ;

            String m_class = ormFragment.attributeValue("class") ;
            String m_dbgroup = ormFragment.attributeValue("dbgroup") ;
            String shadow = ormFragment.attributeValue("shadow") ;
            String table = ormFragment.attributeValue("table") ;

            if(StringUtil.isEmpty(m_dbgroup)){
                m_dbgroup = parentDbGroup ;
            }

            ResultMapBasedObjectMapping map =  ObjectMappingUtil.createResultMapping(this.guzzContext, m_id, Class.forName(m_class), m_dbgroup, shadow, table) ;

            List results = ormFragment.selectNodes("result") ;

            for(int k = 0 ; k < results.size() ; k++){
                Element e = (Element) results.get(k) ;

                String loader = e.attributeValue("loader") ;
                String property = e.attributeValue("property") ;
                String column = e.attributeValue("column") ;
                String nullValue = e.attributeValue("null") ;
                String type = e.attributeValue("type") ;

                Assert.assertNotEmpty(property, "invalid property. xml is:" + e.asXML()) ;

                if(StringUtil.isEmpty(column)){
                    column = property ;
                }

                TableColumn col = ObjectMappingUtil.createTableColumn(this.guzzContext, map, property, column, type, loader) ;
                col.setNullValue(nullValue) ;

                ObjectMappingUtil.addTableColumn(map, col) ;
            }

            return map ;
        }

        return null ;
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

        this.sqljarpath = sc.getProps().getProperty("sqljarpath", "");
        this.sqlpackage = sc.getProps().getProperty("sqlpackage", "");
        this.encoding = sc.getProps().getProperty("encoding", "UTF-8") ;
        this.overrideSqlInGuzzXML = StringUtil.toBoolean(sc.getProps().getProperty("overrideSqlInGuzzXML"), false) ;
        this.useCache = StringUtil.toBoolean(sc.getProps().getProperty("useCache"), true) ;

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
        myjardir = JarTool.getJarDir() + "\\";
    }

    @SuppressWarnings("unused")
    private static class JarTool {
        //获取jar绝对路径
        public static String getJarPath(){
            File file = getFile();
            if(file==null)return null;
            return file.getAbsolutePath();
        }
        //获取jar目录
        public static String getJarDir() {
            File file = getFile();
            if(file==null)return null;
            return getFile().getParent();
        }
        //获取jar包名
        public static String getJarName() {
            File file = getFile();
            if(file==null)return null;
            return getFile().getName();
        }

        public static File getFile() {
            //关键是这行...
            String path = JarTool.class.getProtectionDomain().getCodeSource()
                    .getLocation().getFile();
            try{
                path = java.net.URLDecoder.decode(path, "UTF-8");//转换处理中文及空格
            }catch (java.io.UnsupportedEncodingException e){
                return null;
            }
            return new File(path);
        }
    }

    public static class CachedCompiledSQL {
        private CompiledSQL compiledSQL ;
        private String key ;
        private Serializable mark ;

        public CachedCompiledSQL(String key, CompiledSQL compiledSQL){
            this.key = key ;
            this.compiledSQL = compiledSQL ;
        }

        public CompiledSQL getCompiledSQL() {
            return compiledSQL;
        }

        public void setCompiledSQL(CompiledSQL compiledSQL) {
            this.compiledSQL = compiledSQL;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Serializable getMark() {
            return mark;
        }

        public void setMark(Serializable mark) {
            this.mark = mark;
        }
    }
}
