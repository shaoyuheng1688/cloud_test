package com.raymon.taxguide.vo.theme;

import lombok.Data;

@Data
public class YcxgzsConfigReportVo {

    public static class Code{
        public final static int NO = 0;
        public final static int YES = 1;
    }

    public static class Type{
        public final static int must = 1;
        public final static int not = 2;
        public final static int maybe = 3;
    }

    /**
     * 1强预审、3弱预审 2、不预审
     */
    private int checkType = 2;
    /**
     * 是否在线：0否、1是
     */
    private int isOnline;
    /**
     * 是否指定单位：0否、1是
     */
    private int isAssignUnit;
}
