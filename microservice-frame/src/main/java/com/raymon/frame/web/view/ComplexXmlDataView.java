package com.raymon.frame.web.view;

import org.apache.commons.lang.time.DateFormatUtils;
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
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Chan
 *
 */
@SuppressWarnings("rawtypes")
public class ComplexXmlDataView extends
		org.springframework.web.servlet.view.xslt.XsltView {

	private static Logger log = LoggerFactory.getLogger(ComplexXmlDataView.class);

	private static final ConcurrentHashMap<String, JAXBContext> jctxMap = new ConcurrentHashMap<String, JAXBContext>();

	private String sourceKey;

	@Override
	public void setSourceKey(String sourceKey) {
		this.sourceKey = sourceKey;
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
			log.info("start marshal class: " + paramClassName);
			String packageName = object.getClass().getPackage().getName();
			log.info("package name: " + packageName);
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
	protected Source locateSource(Map model) throws Exception {
		Document doc = DocumentHelper.createDocument();
		if (this.sourceKey != null)
			setXmlResult("0", "ok", model.get(this.sourceKey), doc);
		else
			setXmlResult("0", "ok", model, doc);
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
	
	private void setXmlResult(Object resultObj, Element elem) throws IOException {
		try {
			
			if (resultObj != null) {
				marshallerData(resultObj, elem, null);
			}

		} catch (MarshallerException e) {
			e.printStackTrace();
		}
	}
/*
	@SuppressWarnings("unchecked")
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
*/
	private void getCollectionElement(String key, Object object,
			Element parentEl) throws MarshallerException {
		if (object instanceof Collection) {
			for (Object obj : (Collection<?>) object) {
				Element el = parentEl.addElement(key);
				marshallerData(obj, el, null);
			}
		}
	}
	
	
	private void getMapElement(Object object, Element parentEl)
			throws MarshallerException {
		if (object instanceof Map) {
			Map map = (Map) object;
			for (Object en : map.entrySet()) {
				Entry mapItem = (Entry) en;
				String strKey = (String) mapItem.getKey();
				Object obj = mapItem.getValue();
				if (null != obj) {
					String valText = formatSimpleTypeObject(obj);
					if (valText != null) {
						parentEl.addAttribute(strKey, valText);
					} else {
						// Element el = new Element(strKey);
						// parentEl.addContent(el);
						marshallerData(obj, parentEl, strKey);
					}
				}
			}
		}
	}

	
	private String formatSimpleTypeObject(Object obj) {
		if(null == obj)
			return null;
		Class<?> clazz = obj.getClass();
		String className = clazz.getName();
		if (className.equals("java.lang.String")) {
			return (String)obj;
		} else if (className.equals("int")) {
			return obj.toString();
		} else if (className.equals("bool")) {
			return obj.toString();
		} else if (className.equals("java.lang.Boolean")) {
			return obj.toString();
		} else if (className.equals("java.lang.Integer")) {
			return obj.toString();
		} else if (className.equals("float")) {
			return obj.toString();
		} else if (className.equals("java.lang.Float")) {
			return obj.toString();
		} else if (className.equals("double")) {
			return obj.toString();
		} else if (className.equals("java.lang.Double")) {
			return obj.toString();
		} else if (className.equals("java.lang.Long")) {
			return obj.toString();
		} else if (className.equals("java.math.BigDecimal")) {
			return obj.toString();
		} else if (className.equals("java.util.Date")) {
			return DateFormatUtils.format((Date)obj, "yyyy-MM-dd HH:mm:ss");
		} else if (className.equals("java.sql.Timestamp")) {
			return DateFormatUtils.format((Date)obj, "yyyy-MM-dd HH:mm:ss");
		} else if (className.equals("java.sql.Date")) {
			return DateFormatUtils.format((Date)obj, "yyyy-MM-dd HH:mm:ss");
		} else if (className.equals("java.sql.Time")) {
			return DateFormatUtils.format((Date)obj, "HH:mm:ss");
		} else if (clazz.isEnum()) {
			return obj.toString();
		} else {
			return null;
		}
	}

	
	private void marshallerData(Object obj, Element parentElement,
			String specElementName) throws MarshallerException {
		if (null != obj) {
			String valText = formatSimpleTypeObject(obj);
			if ( valText != null) {
				parentElement.setText(valText);
			} else if (obj instanceof Collection) {
				getCollectionElement(null == specElementName ? "item"
						: specElementName, obj, parentElement);
			} else if (obj instanceof Map) {
				getMapElement(obj, parentElement);
			} else {
				Element el = getObjectElement(obj);
				if (null != specElementName) {
					Element e = parentElement.addElement(specElementName);
					if (el != null) {
						e.add(el);
					}
				} else {
					if (el != null) {
						parentElement.add(el);
					}
				}

			}
		}
	}

	private JAXBContext getJAXBContext(Class clz) throws JAXBException {
		// String packageName = clz.getPackage().getName();
		String key = clz.getName();
		if (jctxMap.containsKey(key)) {
			return jctxMap.get(key);
		} else {
			JAXBContext ctx = JAXBContext.newInstance(clz);
			jctxMap.put(key, ctx);
			return ctx;
		}
	}
	
	static public class MarshallerException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8979330367814514137L;
		
		private String code;
		
		public MarshallerException(String code, String message){
			super(message);
			this.code = code;
		}
		public String getCode(){
			return code;
		}
	}
}
