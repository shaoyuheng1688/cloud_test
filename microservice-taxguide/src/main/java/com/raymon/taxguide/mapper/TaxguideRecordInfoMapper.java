package com.raymon.taxguide.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.raymon.taxguide.model.TaxguideRecordInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
public interface TaxguideRecordInfoMapper extends BaseMapper<TaxguideRecordInfo> {

    List<TaxguideRecordInfo> getTaxGuideRecordInfosByBussId(@Param("bussId") String bussId);
}