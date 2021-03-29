package com.raymon.frame.web.view;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Source;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("rawtypes")
public class XsltView extends
		org.springframework.web.servlet.view.xslt.XsltView {
	
	private static Logger log = LoggerFactory.getLogger(JsonDataView.class);

	private static final ConcurrentHashMap<String, JAXBContext> jctxMap = new ConcurrentHashMap<String, JAXBContext>();
	
	
	private String sourceKey;
	
	@Override
	public void setSourceKey(String sourceKey) {
		this.sourceKey = sourceKey;
	}
	
	private Object getSimpleTypeObject(Class clazz, String value) {

		String className = clazz.getName();
		if (className.equals("java.lang.String")) {
			String s = value;
			return s;
		} else if (className.equals("int")) {
			return Integer.valueOf(value).intValue();
		} else if (className.equals("java.lang.Integer")) {
			return Integer.valueOf(value);
		} else if (className.equals("float")) {
			return Float.valueOf(value).floatValue();
		} else if (className.equals("java.lang.Float")) {
			return Float.valueOf(value);
		} else if (className.equals("java.util.Date")) {
			Date d = null;
			try {
				d = DateUtils.parseDate(value, new String[] { "yyyy",
						"yyyy-MM", "yyyy-MM-dd", "yyyy/MM", "yyyy/MM/dd",
						"yyyy-MM-dd HH:mm:ss" });
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return d;
		} else if (clazz.isEnum()) {
			Object[] es = clazz.getEnumConstants();
			if (NumberUtils.isNumber(value)) {
				for (Object o : es) {
					Enum<?> e = (Enum<?>) o;
					if (e.ordinal() == Integer.valueOf(value)) {
						return e;
					}
				}
			}else{
				for (Object o : es) {
					Enum<?> e = (Enum<?>) o;
					if (e.name().equals(value)) {
						return e;
					}
				}
			}
			return null;
		} else {
			return null;
		}
	}
	
	private Element getObjectElement(Object object) {
		try {
			String paramClassName = object.getClass().getName();
			log.info("start marshal class: " + paramClassName);
			String packageName = object.getClass().getPackage().getName();
			log.info("package name: " + packageName);
			JAXBContext jctx;
			//jctx = JAXBContext.newInstance(object.getClass());
			jctx = this.getJAXBContext(object.getClass());
			Marshaller mar = jctx.createMarshaller();
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			mar.marshal(object, outStream);
			SAXReader sb = new SAXReader();
			Document resultDoc = sb.read(new ByteArrayInputStream(outStream
					.toByteArray()));
			Element resultEl = resultDoc.getRootElement();
			resultEl.detach();
			return resultEl;
		} catch (JAXBException e) {

			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected Source locateSource(Map model) throws Exception {
		Document doc = DocumentHelper.createDocument();
		if(this.sourceKey != null)
			setXmlResult("0", "ok", model.get(this.sourceKey), doc);
		else
			setXmlResult("0", "ok", model, doc);
		Source s = new DocumentSource(doc);
		return s;
	}

	private void setXmlResult(String code, String message, Object resultObj,
			Document doc) throws IOException {

			//Document doc = DocumentHelper.createDocument();
			Element rootEl = doc.addElement("Result");
			rootEl.addElement("Code").setText(code);
			rootEl.addElement("Message").setText(message);
			rootEl.addElement("ServerTime").setText(DateFormatUtils.format(new Date(), "yyyy-MM-dd'T'HH:mm:ssZZ"));
			
			if (resultObj != null) {
				Element data = rootEl.addElement("Data");
				setXmlResult(resultObj, data);
			}
	}
	
	private void setXmlResult(Object resultObj, Element elem){
		if (resultObj != null) {
			Element data = elem;
			if (getSimpleTypeObject(resultObj.getClass(), resultObj
					.toString()) != null) {
				data.setText(resultObj.toString());
			} else if (resultObj instanceof Collection) {
				for (Object obj : (Collection) resultObj) {
					Element el = getObjectElement(obj);
					if (el != null)
						data.add(el);
				}
			} else if (resultObj instanceof Map) {
				Iterator iter = ((Map)resultObj).entrySet().iterator();
				while(iter.hasNext()){
					Entry en = (Entry)iter.next();
					Element el = elem.addElement(en.getKey().toString());
					setXmlResult(en.getValue(), el);
				}
			} else{
				Element el = getObjectElement(resultObj);
				if (el != null)
					data.add(el);
			}
		}
	}
	
	private JAXBContext getJAXBContext(Class clz) throws JAXBException{
		//String packageName = clz.getPackage().getName();
		String key = clz.getName();
		if(jctxMap.containsKey(key)){
			return jctxMap.get(key);
		}else{
			JAXBContext ctx = JAXBContext.newInstance(clz);
			jctxMap.put(key, ctx);
			return ctx;
		}
	}
}
