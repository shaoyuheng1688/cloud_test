package com.raymon.taxguide.service;

import com.raymon.frame.pojo.WorkFlowInfo;

import java.util.List;

public interface QueryService {

    List<WorkFlowInfo> getManualTaxGuideWorkFlowInfos(String recordId);
}
