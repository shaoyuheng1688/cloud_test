package com.raymon.frame.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
//POC流转记录
public class WorkFlowInfo implements Serializable {

    private static final long serialVersionUID = -9185220986467959691L;

    /**
     * 环节名称
     */
    public static class Key {
        /**
         * 填单
         */
        public static final String EFORM = "eform";
        /**
         * 导税
         */
        public static final String AUTO_TAX_GUIDE = "autoTaxGuide";
        /**
         * 办理
         */
        public static final String HANDLER = "handler";
        /**
         * 修改主题
         */
        public static final String Theme = "theme";
        /**
         * 审核
         */
        public static final String EXAMINE = "examine";
        /**
         * 人工导税
         */
        public static final String MANUAL_TAX_GUIDE = "manualTaxGuide";
    }

    public WorkFlowInfo() {

    }

    public WorkFlowInfo(String key, Date startTime, Date endTime, String remark, String accCode, String empNumber, Long state, String ztmc, Date createTime) {
        this.key = key;
        setIndex(key);
        this.startTime = startTime;
        this.endTime = endTime;
        this.remark = remark;
        this.accCode = accCode;
        this.empNumber = empNumber;
        this.state = state;
        this.ztmc = ztmc;
        this.createTime = createTime;
        this.sortTime = startTime == null ? createTime : startTime;
    }


    //环节关键字
    private String key;

    //创建时间
    private Date createTime;

    //开始时间
    private Date startTime;

    //结束时间
    private Date endTime;

    //备注意见
    private String remark;

    //税务人员账号
    private String accCode;

    //税务人员工号
    private String empNumber;

    //状态
    private Long state;

    //主题名称
    private String ztmc;

    //排序值，依次递增 智能导税-填单-审核-（人工导税）-办理，用于当创建时间相等时，控制大流程的流转排序
    private int index;

    //排序值，createTime或startTime，用于排序
    private Date sortTime;

    public void setIndex(String key) {
        switch (key) {
            case Key.AUTO_TAX_GUIDE:
                this.index = 0;
                break;
            case Key.EFORM:
                this.index = 1;
                break;
            case Key.EXAMINE:
                this.index = 2;
                break;
            case Key.MANUAL_TAX_GUIDE:
                this.index = 3;
                break;
            case Key.HANDLER:
                this.index = 4;
                break;
        }

    }
}
