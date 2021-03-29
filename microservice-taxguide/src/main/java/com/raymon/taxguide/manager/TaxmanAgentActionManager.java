package com.raymon.taxguide.manager;

import com.raymon.taxguide.action.AbstractAction;

public interface TaxmanAgentActionManager {
    AbstractAction<?> getAction(String actionName);
    void registerAction(String actionName, AbstractAction<?> action);
}
