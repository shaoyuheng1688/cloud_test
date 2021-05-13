package com.raymon.taxguide.vo.theme;

import java.io.Serializable;

/**
 * 主题信息表
 */
public class ThemeVO implements Serializable {



    private String ztbm;

    private String ztmc;

    private String synsrlx;

    private String sjztbm;

    /**
     * 个人可办理企业主题
     */
    private String grkbqyzt;

    /**
     * 接口查询标志，0为不需要查询，1为需要查询未办结（通用标志，用于标志主题需要查询什么接口）
     */
    private String jkcxbz;

    private String dsdm;

    private String ztsxgs;


    public String getJkcxbz() {
        return jkcxbz;
    }

    public void setJkcxbz(String jkcxbz) {
        this.jkcxbz = jkcxbz;
    }

    public String getSjztbm() {
        return sjztbm;
    }

    public void setSjztbm(String sjztbm) {
        this.sjztbm = sjztbm;
    }

    public String getGrkbqyzt() {
        return grkbqyzt;
    }

    public void setGrkbqyzt(String grkbqyzt) {
        this.grkbqyzt = grkbqyzt;
    }

    public String getZtbm() {
        return ztbm;
    }

    public void setZtbm(String ztbm) {
        this.ztbm = ztbm;
    }

    public String getZtmc() {
        return ztmc;
    }

    public void setZtmc(String ztmc) {
        this.ztmc = ztmc;
    }

    public String getSynsrlx() {
        return synsrlx;
    }

    public void setSynsrlx(String synsrlx) {
        this.synsrlx = synsrlx;
    }

    public String getDsdm() {
        return dsdm;
    }

    public void setDsdm(String dsdm) {
        this.dsdm = dsdm;
    }

    public String getZtsxgs() {
        return ztsxgs;
    }

    public void setZtsxgs(String ztsxgs) {
        this.ztsxgs = ztsxgs;
    }
}
