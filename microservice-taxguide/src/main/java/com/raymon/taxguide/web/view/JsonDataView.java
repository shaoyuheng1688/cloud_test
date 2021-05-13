package com.raymon.taxguide.web.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.raymon.taxguide.utils.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JsonDataView extends
		org.springframework.web.servlet.view.AbstractUrlBasedView {
	private static final Logger log = LoggerFactory.getLogger(JsonDataView.class);
	
	private static Gson gson = new GsonBuilder().disableHtmlEscaping()
		.registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter())
		.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
	@Override
	protected void prepareResponse(HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		super.prepareResponse(request, response);
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> modelMap,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/json");
		try{
			Map<String, Object> value = filterModel(modelMap);
			String json = gson.toJson(value);
			PrintWriter out = response.getWriter();                 
			out.println(json);      
			out.flush();
		}catch(Throwable e){
			log.error(e.getMessage(),e);
		}
	}
	
	protected Map<String, Object> filterModel(Map<String, Object> model) {
		Map<String, Object> tempMap = new HashMap<String, Object>(model.size());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Set<String> renderedAttributes = model.keySet();
		if(model.containsKey("error")){
			Exception ex = (Exception)model.get("error");
			resultMap.put("Message", ex.getMessage());
			resultMap.put("Code", "1");
		}else{
			for (Map.Entry<String, Object> entry : model.entrySet()) {
				if (!(entry.getValue() instanceof BindingResult) && renderedAttributes.contains(entry.getKey())) {
					tempMap.put(entry.getKey(), entry.getValue());
				}
			}
			// 默认调用成功Code值为0
			resultMap.put("Code", 0);
			resultMap.put("Message", "OK");
			Map<String, Object> dataMap = new HashMap<String, Object>();
			Map.Entry<String, Object> lastDataEntry = null;
			for (Map.Entry<String, Object> entry : tempMap.entrySet()) {
				if ("message".equalsIgnoreCase(entry.getKey())) {
					resultMap.put("Message", entry.getValue());
				} else if ("code".equalsIgnoreCase(entry.getKey())) {
					resultMap.put("Code", entry.getValue());
				} else {
//					resultMap.put("Data", entry.getValue());
					dataMap.put((String)entry.getKey(), entry.getValue());
					lastDataEntry = entry;
				}
			}
			if(dataMap.size() > 0){
				if(dataMap.size() == 1 && lastDataEntry != null){
					resultMap.put("Data", lastDataEntry.getValue());
				}else{
					resultMap.put("Data", dataMap);
				}
			}
		}
		resultMap.put("ServerTime", DateTimeUtils.formatDateTime(DateTimeUtils.currentTime()));
		return resultMap;
	}

}
