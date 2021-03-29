package com.raymon.taxguide.manager.imp;


import com.raymon.taxguide.manager.TaxmanAgentActionManager;
import com.raymon.taxguide.action.AbstractAction;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TaxmanAgentActionManagerImp implements TaxmanAgentActionManager {

	private Map<String, AbstractAction<?>> actionMap = new HashMap<String , AbstractAction<?>>();

	@Override
	public AbstractAction<?> getAction(String actionName){
			return actionMap.get(actionName);
	}

	@Override
	public void registerAction(String actionName, AbstractAction<?> action) {
		actionMap.put(actionName, action);
	}

}
