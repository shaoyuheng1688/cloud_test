package com.raymon.taxguide.service.imp;

import com.raymon.frame.pojo.WorkFlowInfo;
import com.raymon.taxguide.model.TaxguideRecordInfo;
import com.raymon.taxguide.repository.TaxguideRepository;
import com.raymon.taxguide.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QueryServiceImp implements QueryService {

    @Autowired
    private TaxguideRepository taxguideRepository;

    @Override
    public List<WorkFlowInfo> getManualTaxGuideWorkFlowInfos(String recordId) {
        List<WorkFlowInfo> workFlowInfos = new ArrayList<WorkFlowInfo>();
        List<TaxguideRecordInfo> taxguideRecordInfos = taxguideRepository.getTaxGuideRecordInfosByBussId(recordId);
        if (taxguideRecordInfos != null && taxguideRecordInfos.size() > 0) {
            taxguideRecordInfos.forEach(a -> {
                WorkFlowInfo workFlowInfo = new WorkFlowInfo(WorkFlowInfo.Key.MANUAL_TAX_GUIDE, a.getStartTime(), a.getFinishTime(),
                        a.getRemark(), a.getAccCode(), a.getEmpNumber(), (long) a.getState(), null, a.getCreateTime());
                workFlowInfos.add(workFlowInfo);
            });
        }
        return workFlowInfos;
    }
}