package com.raymon.taxguide.pojo;

import com.raymon.taxguide.model.LogTaxmanInfo;
import com.raymon.taxguide.model.TaxguideRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Transient;

@ApiModel("导税客户端信息")
@Data
public class TaxguideClientInfo {

    //TODO以后可以添加返回配置信息或导税员信息

    @ApiModelProperty("排队人数")
    private int waitCount;

    @ApiModelProperty("导税员信息")
    private LogTaxmanInfo logTaxmanInfo;

    @ApiModelProperty("当前导税记录")
    private TaxguideRecord taxguideRecord;
}
