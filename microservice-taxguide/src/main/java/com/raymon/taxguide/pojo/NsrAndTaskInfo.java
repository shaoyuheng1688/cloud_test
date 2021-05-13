package com.raymon.taxguide.pojo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@ApiModel("可办理的票号信息")
@Data
@Alias("nsrAndTaskInfo")
public class NsrAndTaskInfo {

    @ApiModelProperty("导税主记录ID")
    private String tgId;

    @ApiModelProperty("CS票号ID")
    private String cstId;

    @ApiModelProperty("身份证号")
    private String identityNumber;

    @ApiModelProperty("姓名")
    private String userName;

    @ApiModelProperty("纳税人识别号")
    private String nsrsbh;

    @ApiModelProperty("纳税人名称")
    private String nsrmc;

    @ApiModelProperty("发起人工导税时间")
    private Date createTime;

    @ApiModelProperty("主题编码")
    private String ztbm;

    @ApiModelProperty("主题名称")
    private String ztmc;
}
