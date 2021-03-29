package com.raymon.frame.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CSTicketVo implements Serializable {

    private static final long serialVersionUID = 8171306298374796006L;

    public class CSTicketState{
        /**
         * 队列中 0
         */
        public static final int IN_QUEUE = 0;
        /**
         * 待确认 1
         */
        public static final int DE_QUEUE_CONFIRM = 1;
        /**
         * 已出队 2
         */
        public static final int DE_QUEUE = 2;
    }

    //审核票池优先级
    public class CSTicketExaPriority{
        /**
         * 离线任务 0
         */
        public static final int OFFLINE = 0;
        /**
         * 离线再次提交 1
         */
        public static final int SECOND_OFFLINE = 1;
        /**
         * 在线任务 2
         */
        public static final int ONLINE = 2;
        /**
         * 在线再次提交 3
         */
        public static final int SECOND_ONLINE = 3;
    }

    //审核票池预审类型优先级
    public class CSTicketExaCheckTypePriority{
        /**
         * 不预审 0
         */
        public static final int UN_CHECK = 0;
        /**
         * 弱预审 1
         */
        public static final int WEEK_CHECK = 1;
        /**
         * 强预审 2
         */
        public static final int MUST_CHECK = 2;

    }

    //办理票池优先级
    public class CSTicketDealPriority{
        /**
         * 离线任务 0
         */
        public static final int OFFLINE = 0;
        /**
         * 在线任务 0
         */
        public static final int ONLINE = 0;
        /**
         * 离线指定日期任务 1
         */
        public static final int OFFLINE_ASSIGN = 1;
        /**
         * 非即办 9
         */
        public static final int NO_IMMEDIATE = 9;
    }

    private String csTicketId;

    private Date createTime;

    private int state;

    private String ocId;

    private String swjgDm;

    private String ztbm;

    private Date dequeueConfirmTime;

    private Date dequeueTime;

    private String specAccId;

    private String bussId;

    private String accId;

    private int priorty;

    private int queueChannelId;
}
