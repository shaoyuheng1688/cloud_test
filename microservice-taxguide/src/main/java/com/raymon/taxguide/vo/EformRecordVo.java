package com.raymon.taxguide.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class EformRecordVo implements Serializable {

	private static final long serialVersionUID = 7413237861211099541L;

	//审核状态
	public static class CheckState{
		//未提交
		public final static String UNSUBMITTED = "-1";
		//暂存
		public final static String TEMPORARY = "0";
		//未审核
		public final static String UNCHECKED = "1";
		//审核中
		public final static String CHECKING = "2";
		//转派
		public final static String TRANSFER = "3";
		//通过
		public final static String PASS = "4";
		//不通过(需重新提交资料)
		public final static String UNPASS_NEED_RESUBMIT = "5";
		//不通过
		public final static String UNPASS = "6";
		//通过(须重新提交资料)
		public final static String PASS_NEED_RESUBMIT = "7";
	}

	public static class Channel{
		/**
		 * 小程序
		 */
		public final static long APP = 8;
		/**
		 * POC
		 */
		public final static long POC = 10;
	}

	//是否允许修改
	public static class Modify{
		public final static long UN_MODIFY = 0;
		public final static long MODIFY = 1;
	}

	//是否授权离线办理
	public static class OfflineDealing{
		public final static long UNPERMIT = 0;
		public final static long PERMIT = 1;
	}

	private String recordId;

	// 填单用户ID
	private String userId;

	// 填单用户身份证
	private String userCard;

	// 填单用户姓名
	private String userName;

	// 填单记录状态 0暂存 1未审核 2审核中 3审核通过 4审核不通过（需重新提交资料）5审核不通过
	private String checkState;

	// 创建时间
	private Date createTime;

	// 最后修改时间
	private Date lastUpdateTime;

	// 纳税人识别号
	private String taxpayerNumber;

	// 填单渠道代码
	private String ditchDom;

	// 手机号
	private String phone;

	// 单位ID
	private String muId;

	// 填单台ID
	private String fillformId;

	// 流水号ID
	private String serialNumber;

	// 是否删除
	private String isDelete;


	// 主题编码
	private String themeCode;

	// 是否可以预约
	private String isBooking;

	// 是否发生转派
	private String isForward;

	// 一次性告知书ID
	private String oneOffNoticeId;

	// 预约ID
	private String sessionId;

	// 审批截至时间
	private Date deadline;

	private String recordMuId;

	//纳税人名称
	private String nsrmc;

	//渠道id，1.小程序四预 2.POC四预
	private Long channelId;

	//是否允许修改，0否 1是
	private Long isModify;

	//是否授权离线办理，0否 1是
	private Long offlineDealing;

	private String swjgDm;

	//集中调度审核状态
	private Long examineState;

	//纳税人指定税务机构代码
	private String assignSwjgDm;
}
