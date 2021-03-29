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
import org.springframework.validation.BindingResult;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("rawtypes")
public class XmlDataView extends
		org.springframework.web.servlet.view.xslt.XsltView {

	private static Logger log = LoggerFactory.getLogger(XmlDataView.class);

	private static final ConcurrentHashMap<Class, JAXBContext> jctxMap = new ConcurrentHashMap<Class, JAXBContext>();

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
		} else if (className.equals("long")){
			return Long.valueOf(value).longValue();
		} else if (className.equals("java.lang.Long")){
			return Long.valueOf(value);
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
			} else {
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

	@Override
	protected Source getStylesheetSource() {
		String style = "<?xml version=\"1.0\" encoding=\""
				+ "UTF-8"
				+ "\"?>"
				+ "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">"
				+ "<xsl:output method = \"xml\"  omit-xml-declaration = \"yes\" indent = \"yes\"/>"
				+ "<xsl:template match=\"/ | @* | node()\">"
				+ "<xsl:copy><xsl:apply-templates select=\"@* | node()\"/></xsl:copy>"
				+ "</xsl:template></xsl:stylesheet>";
		ByteArrayInputStream bais = new ByteArrayInputStream(style.getBytes());
		StreamSource styleSource = new StreamSource(bais);
		return styleSource;
	}

	private Element getObjectElement(Object object) {
		try {
			String paramClassName = object.getClass().getName();
			log.debug("start marshal class: " + paramClassName);
			String packageName = object.getClass().getPackage().getName();
			log.debug("package name: " + packageName);
			JAXBContext jctx;
			// jctx = JAXBContext.newInstance(object.getClass());
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
	@SuppressWarnings("unchecked")
	protected Source locateSource(Map<String, Object> model) throws Exception {
		Document doc = DocumentHelper.createDocument();
		Map<String, Object> tempMap = new HashMap<String, Object>(model.size());
		Set<String> renderedAttributes = model.keySet();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if (model.containsKey("error")) {
			Exception ex = (Exception)model.get("error");
			setXmlResult("1", ex.getMessage(), null, doc);
		} else {
			resultMap.put("Code", 0);
			resultMap.put("Message", "OK");
			for (Entry<String, Object> entry : model.entrySet()) {
				if (!(entry.getValue() instanceof BindingResult) && renderedAttributes.contains(entry.getKey())) {
					tempMap.put(entry.getKey(), entry.getValue());
				}
			}
			for (Entry<String, Object> entry : tempMap.entrySet()) {
				if ("message".equalsIgnoreCase(entry.getKey())) {
					resultMap.put("Message", entry.getValue());
				} else if ("code".equalsIgnoreCase(entry.getKey())) {
					resultMap.put("Code", entry.getValue());
				} else {
					resultMap.put("Data", entry.getValue());
				}
			}
			
			if (this.sourceKey != null){
				setXmlResult(resultMap.get("Code").toString(), resultMap.get("Message").toString(), model.get(this.sourceKey), doc);
			}else {
				Map filterMap = new HashMap();
				for (Object obj : model.keySet()) {
					if (obj instanceof String) {
						String key = (String) obj;
						if (key.indexOf("org.springframework") == -1)
							filterMap.put(key, model.get(key));
					}
				}
				setXmlResult(resultMap.get("Code").toString(), resultMap.get("Message").toString(), filterMap, doc);
			}
		}
		Source s = new DocumentSource(doc);
		return s;
	}

	private void setXmlResult(String code, String message, Object resultObj,
			Document doc) throws IOException {

		// Document doc = DocumentHelper.createDocument();
		Element rootEl = doc.addElement("Result");
		rootEl.addElement("Code").setText(code);
		rootEl.addElement("Message").setText(message);
		rootEl.addElement("ServerTime").setText(
				DateFormatUtils.format(new Date(), "yyyy-MM-dd'T'HH:mm:ssZZ"));

		if (resultObj != null) {
			Element data = rootEl.addElement("Data");
			setXmlResult(resultObj, data);
		}
	}

	private void setXmlResult(Object resultObj, Element elem) {
		if (resultObj != null) {
			Element data = elem;
			if (getSimpleTypeObject(resultObj.getClass(), resultObj.toString()) != null) {
				data.setText(resultObj.toString());
			} else if (resultObj instanceof Collection) {
				for (Object obj : (Collection) resultObj) {
					Element el = getObjectElement(obj);
					if (el != null)
						data.add(el);
				}
			} else if (resultObj instanceof Map) {
				Iterator iter = ((Map) resultObj).entrySet().iterator();
				while (iter.hasNext()) {
					Entry en = (Entry) iter.next();
					Element el = elem.addElement(en.getKey().toString());
					setXmlResult(en.getValue(), el);
				}
			} else {
				Element el = getObjectElement(resultObj);
				if (el != null)
					data.add(el);
			}
		}
	}

	private JAXBContext getJAXBContext(Class clz) throws JAXBException {
		// String packageName = clz.getPackage().getName();
		if (jctxMap.containsKey(clz)) {
			return jctxMap.get(clz);
		} else {
			JAXBContext ctx = JAXBContext.newInstance(clz);
			jctxMap.put(clz, ctx);
			return ctx;
		}
	}
}
