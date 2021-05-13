package com.raymon.taxguide.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

//返回当前时间片和提交资料的截止时间
@Data
public class SliceTimeInfo implements Serializable {

    private Date beginSliceTime;

    private Date endSliceTime;
}
