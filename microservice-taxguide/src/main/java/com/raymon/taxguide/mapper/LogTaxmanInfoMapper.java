package com.raymon.taxguide.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.raymon.taxguide.model.LogTaxmanInfo;
import com.raymon.taxguide.pojo.NsrAndTaskInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogTaxmanInfoMapper extends BaseMapper<LogTaxmanInfo> {

    List<NsrAndTaskInfo> getNsrAndTaskInfoListByCstIds(@Param("cstIds")List<String> cstIds);
}