package com.raymon.taxguide.service.imp;


import com.raymon.taxguide.model.LogTaxguidePollTime;
import com.raymon.taxguide.model.LogTaxmanInfo;
import com.raymon.taxguide.model.TaxguideRecordInfo;
import com.raymon.taxguide.pojo.NsrAndTaskInfo;
import com.raymon.taxguide.repository.BusinessRepository;
import com.raymon.taxguide.repository.TaxguideRepository;
import com.raymon.taxguide.repository.UserRepository;
import com.raymon.taxguide.service.TestService;
import com.raymon.taxguide.web.exception.ApplicationException;
import jdk.nashorn.internal.ir.LiteralNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class TestServiceImp implements TestService {

    @Autowired
    private TaxguideRepository taxguideRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BusinessRepository businessRepository;

    @Override
    public void test() {
//        List<TaxguideRecordInfo> a =  taxguideRepository.getTaxGuideRecordInfosByBussId("402868817611d5a8017611d5de810000");
//        List<NsrAndTaskInfo> a =userRepository.getNsrAndTaskInfoListByCstIds(Arrays.asList("402881e676a747e30176a76f304c0000","402881e676a7257e0176a7265dba0000"));
//        LogTaxmanInfo a = userRepository.findUserLogTaxmanInfoByAccId("21029357");
        LogTaxguidePollTime logTaxguidePollTime =
                new LogTaxguidePollTime("999",1,"123");
        LogTaxguidePollTime a = taxguideRepository.saveLogTaxguidePollTime(logTaxguidePollTime);
//        LogTaxguidePollTime a= taxguideRepository.findLogTaxguidePollTimeById(21L);
//        a.setCreateTime(new Date());
//        taxguideRepository.updateLogTaxguidePollTime(a);
//        throw new ApplicationException("????");
//        System.out.println(a.toString());
    }
}
