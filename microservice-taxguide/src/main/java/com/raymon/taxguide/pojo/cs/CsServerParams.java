package com.raymon.taxguide.pojo.cs;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class CsServerParams implements Serializable {

    private static final long serialVersionUID = 3253344639228455065L;

    public CsServerParams(Builder builder){
        this.swjgDm = builder.swjgDm;
        this.ztbm = builder.ztbm;
        this.priority = builder.priority;
        this.queueChannelId = builder.queueChannelId;
        this.bussId = builder.bussId;
        this.specAccId = builder.specAccId;
        this.accId = builder.accId;
        this.cstId = builder.cstId;
        this.pageNo = builder.pageNo;
        this.pageSize = builder.pageSize;
        this.searchCstIds = builder.searchCstIds;
    }

    private String swjgDm;
    private String ztbm;
    private int priority;
    private int queueChannelId;
    private String bussId;
    private String specAccId;
    private String accId;
    private String cstId;
    private int pageNo;
    private int pageSize;
    private String searchCstIds;

    public static CsServerParams.Builder newBuilder() {
        return new CsServerParams.Builder();
    }

    public static class Builder {
        private String swjgDm;
        private String ztbm;
        private int priority;
        private int queueChannelId;
        private String bussId;
        private String specAccId;
        private String accId;
        private String cstId;
        private int pageNo;
        private int pageSize;
        private String searchCstIds;

        public Builder swjgDm(String swjgDm) {
            this.swjgDm = swjgDm;
            return this;
        }
        public Builder ztbm(String ztbm) {
            this.ztbm = ztbm;
            return this;
        }
        public Builder priority(int priority) {
            this.priority = priority;
            return this;
        }
        public Builder queueChannelId(int queueChannelId) {
            this.queueChannelId = queueChannelId;
            return this;
        }
        public Builder bussId(String bussId) {
            this.bussId = bussId;
            return this;
        }
        public Builder specAccId(String specAccId) {
            this.specAccId = specAccId;
            return this;
        }
        public Builder accId(String accId) {
            this.accId = accId;
            return this;
        }
        public Builder cstId(String cstId) {
            this.cstId = cstId;
            return this;
        }
        public Builder pageNo(int pageNo) {
            this.pageNo = pageNo;
            return this;
        }
        public Builder pageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }
        public Builder searchCstIds(String searchCstIds) {
            this.searchCstIds = searchCstIds;
            return this;
        }
        public CsServerParams build() {
            return new CsServerParams(this);
        }
    }

}
