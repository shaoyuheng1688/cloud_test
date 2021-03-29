package com.raymon.frame.pojo;

import java.io.Serializable;

/**
 * 流程事件对象
 */
public class ActivitiMsgInfo implements Serializable {

    private static final long serialVersionUID = 6418205504049865229L;

    /**
     * 消息类型
     */
    public static class EventType {
        /**
         * 流程消息
         */
        public static final String PROCESS_EVENT = "processEvent";
        /**
         * totalTransaction维护消息
         */
        public static final String TTC_EVENT = "ttcEvent";
        /**
         * 流程和totalTransaction维护消息
         */
        public static final String BOTH_PROCESS_TTC = "bothProcessTtc";

    }

    /**
     * 流程任务类型
     */
    public static class TaskType {
        /**
         * 启动流程
         */
        public static final String START_PROCESS = "start";
        /**
         * 完成任务
         */
        public static final String COMPLETE_PROCESS = "complete";
        /**
         * 认领任务
         */
        public static final String CLAIM_TASK = "claim";
        /**
         * 回退任务
         */
        public static final String ROLLBACK_TASK = "rollback";
        /**
         * 结束任务
         */
        public static final String CANCEL_TASK = "cancel";
    }

    /**
     * 流程定义
     */
    public static class ProcessDefinitionKey {
        /**
         * POC四预流程
         */
        public static final String BOOKING_PROCESS = "bookingProcess";
        /**
         * POC普通预约流程
         */
        public static final String SIMPLE_BOOKING = "simpleBooking";
    }

    /**
     * 消息类型
     */
    private String eventType;

    /**
     * 所属身份，纳税人传入身份证（或加上纳税人识别号，以 “_”分开），审核员、前台人员请传T_ACCount的accId
     */
    private String identity;

    /**
     * 流程实例自定义唯一标识，用作启动流程，格式：填单ID
     */
    private String businessKey;

    /**
     * 流程任务的类型、分为启动流程、认领任务、完成任务、回退任务
     */
    private String taskType;

    /**
     * 流程参数
     */
    private Variable variable;

    /**
     * 流程定义
     */
    private String processDefinitionKey;

    /**
     * totalTransaction编辑信息
     */
    private TotalTransactionEdit totalTransactionEdit;

    /**
     * 查找totalTransaction的参数
     */
    private TotalTransactionParam totalTransactionParam;

    public static class TotalTransactionParam {

        //预约ID
        private Long sessionId;

        //填单ID
        private String formTotalId;

        //票号ID
        private Long noteId;

        //主题编码
        private String ztbm;

        private Long tranId;

        public Long getSessionId() {
            return sessionId;
        }

        public void setSessionId(Long sessionId) {
            this.sessionId = sessionId;
        }

        public String getFormTotalId() {
            return formTotalId;
        }

        public void setFormTotalId(String formTotalId) {
            this.formTotalId = formTotalId;
        }

        public Long getNoteId() {
            return noteId;
        }

        public void setNoteId(Long noteId) {
            this.noteId = noteId;
        }

        public String getZtbm() {
            return ztbm;
        }

        public void setZtbm(String ztbm) {
            this.ztbm = ztbm;
        }

        public Long getTranId() {
            return tranId;
        }

        public void setTranId(Long tranId) {
            this.tranId = tranId;
        }
    }

    public static class TotalTransactionEdit {

        private Long sessionId;

        private String formTotalId;

        private Long businessId;

        private Long noteId;

        private String ztbm;

        private Long tranId;

        public Long getSessionId() {
            return sessionId;
        }

        public void setSessionId(Long sessionId) {
            this.sessionId = sessionId;
        }

        public String getFormTotalId() {
            return formTotalId;
        }

        public void setFormTotalId(String formTotalId) {
            this.formTotalId = formTotalId;
        }

        public Long getNoteId() {
            return noteId;
        }

        public void setNoteId(Long noteId) {
            this.noteId = noteId;
        }

        public String getZtbm() {
            return ztbm;
        }

        public void setZtbm(String ztbm) {
            this.ztbm = ztbm;
        }

        public Long getTranId() {
            return tranId;
        }

        public void setTranId(Long tranId) {
            this.tranId = tranId;
        }

        public Long getBusinessId() {
            return businessId;
        }

        public void setBusinessId(Long businessId) {
            this.businessId = businessId;
        }
    }

    public static class Variable {
        /**
         * 是否需要人工审核 0.否 1.是
         */
        private Integer isNeedCheck;

        /**
         * 审核是否通过 0.不通过 1.通过且需实时交互 2.通过且不需实时交互
         */
        private Integer checkResult;

        /**
         * 自动审核时，是否需要实时交互 0.否 1.是
         */
        private Integer isNeedExchange;

        /**
         * 是否授权离线办理 0.否 1.是
         */
        private Integer isOffLineDealing;

        /**
         * 离线办理处理结果 0.办理结束 1.需补充资料 2.需实时交互
         */
        private Integer offLineDealResult;

        /**
         * 在线办理时，是否需要补充资料 0.否 1.是
         */
        private Integer onlineDealResult;

        public Integer getIsNeedCheck() {
            return isNeedCheck;
        }

        public void setIsNeedCheck(Integer isNeedCheck) {
            this.isNeedCheck = isNeedCheck;
        }

        public Integer getCheckResult() {
            return checkResult;
        }

        public void setCheckResult(Integer checkResult) {
            this.checkResult = checkResult;
        }

        public Integer getIsNeedExchange() {
            return isNeedExchange;
        }

        public void setIsNeedExchange(Integer isNeedExchange) {
            this.isNeedExchange = isNeedExchange;
        }

        public Integer getIsOffLineDealing() {
            return isOffLineDealing;
        }

        public void setIsOffLineDealing(Integer isOffLineDealing) {
            this.isOffLineDealing = isOffLineDealing;
        }

        public Integer getOffLineDealResult() {
            return offLineDealResult;
        }

        public void setOffLineDealResult(Integer offLineDealResult) {
            this.offLineDealResult = offLineDealResult;
        }

        public Integer getOnlineDealResult() {
            return onlineDealResult;
        }

        public void setOnlineDealResult(Integer onlineDealResult) {
            this.onlineDealResult = onlineDealResult;
        }
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public TotalTransactionEdit getTotalTransactionEdit() {
        return totalTransactionEdit;
    }

    public void setTotalTransactionEdit(TotalTransactionEdit totalTransactionEdit) {
        this.totalTransactionEdit = totalTransactionEdit;
    }

    public TotalTransactionParam getTotalTransactionParam() {
        return totalTransactionParam;
    }

    public void setTotalTransactionParam(TotalTransactionParam totalTransactionParam) {
        this.totalTransactionParam = totalTransactionParam;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }
}
