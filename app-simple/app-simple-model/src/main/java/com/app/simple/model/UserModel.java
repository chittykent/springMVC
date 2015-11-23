package com.app.simple.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.app.simple.utils.DateUtil;
import com.app.tracker.model.annotation.Key;
import com.app.tracker.model.annotation.Table;

/**
 * @Title: UserModel.java
 * @Package com.sim.health.system.model
 * @Description: 
 * @revision :     V1.0
 */
@Table(name="cx_user")
public class UserModel implements Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	public static final String USER_TYPE_MEMBER = "member";//用户类型:会员
	public static final String USER_TYPE_STAFF = "staff";//用户类型:职工
	
	@Key
	private Integer userId;//用户ID
	private String userPassword;//用户密码
	private String userNotes;//用户备注
	private String userSex;//性别
	private String userMobile;//手机
	private String userTel;//电话
	private String userEmail;//邮箱
	private String isDelete;//删除标记
	private java.util.Date addTime;//创建时间
	private java.util.Date editTime;//修改时间
	private String cardNo;//智能定位卡号
	private Integer memberId;//会员id
	private Integer employeeId;//职员id
	private String persionBond;//0:会员 1:职员
	
	private String userName;	//系统账号(登录系统的账号,即系统的用户)
	private String userRealName;//系统账号昵称(登录系统后显示的账号昵称)
	private Integer addUserId;	//创建人id(创建该用户的另一个系统账号id)
	private Integer editUserId;	//修改人id(修改该用户的另一个系统账号id)
	private String addUserName;	//创建人名(创建该用户的另一个系统账号名称)
	private String editUserName;//修改人名(修改该用户的另一个系统账号名称)
	private String name;		//会员姓名(老人的真实姓名)
	private String empName;		//职工名称(职工的真实姓名)

	/**
	 * 获取会员或职工姓名
	 * @param userType 用户类型
	 * @return
	 */
	public String getMemberOrStaffName(String userType){
		return isStaffUser(userType)? empName:name;
	}
	/**
	 * 是会员用户
	 * @param userType 用户类型
	 * @return true:是,false:否
	 */
	public static boolean isMemberUser(String userType){
		return USER_TYPE_MEMBER.equals(userType);
	}
	/**
	 * 是职工用户
	 * @param userType 用户类型
	 * @return true:是,false:否
	 */
	public static boolean isStaffUser(String userType){
		return USER_TYPE_STAFF.equals(userType);
	}
	public UserModel(){
	}

	public UserModel(
		Integer userId
	){
		this.userId = userId;
	}

	public void setUserId(Integer value) {
		this.userId = value;
	}
	
	public Integer getUserId() {
		return this.userId;
	}
	
	public void setUserName(String value) {
		this.userName = value;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserPassword(String value) {
		this.userPassword = value;
	}
	
	public String getUserPassword() {
		return this.userPassword;
	}
	
	public void setUserRealName(String value) {
		this.userRealName = value;
	}
	
	public String getUserRealName() {
		return this.userRealName;
	}
	
	public void setUserNotes(String value) {
		this.userNotes = value;
	}
	
	public String getUserNotes() {
		return this.userNotes;
	}
	
	public void setUserSex(String value) {
		this.userSex = value;
	}
	
	public String getUserSex() {
		return this.userSex;
	}
	
	public void setUserMobile(String value) {
		this.userMobile = value;
	}
	
	public String getUserMobile() {
		return this.userMobile;
	}
	
	public void setUserTel(String value) {
		this.userTel = value;
	}
	
	public String getUserTel() {
		return this.userTel;
	}
	
	public void setUserEmail(String value) {
		this.userEmail = value;
	}
	
	public String getUserEmail() {
		return this.userEmail;
	}
	
	public void setIsDelete(String value) {
		this.isDelete = value;
	}
	
	public String getIsDelete() {
		return this.isDelete;
	}
	
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public void setAddTime(String value) {
		this.addTime = DateUtil.parseDateFormat(value,"yyyy-MM-dd HH:mm:ss");
	}
	public String getAddTime() {
		return DateUtil.getDateSecondFormat(this.addTime);
	}
	
	
	public void setAddUserId(Integer value) {
		this.addUserId = value;
	}
	
	public Integer getAddUserId() {
		return this.addUserId;
	}
	
	public void setEditTime(String value) {
		this.editTime = DateUtil.parseDateFormat(value,"yyyy-MM-dd HH:mm:ss");
	}
	public String getEditTime() {
		return DateUtil.getDateSecondFormat(this.editTime);
	}
	
	
	public void setEditUserId(Integer value) {
		this.editUserId = value;
	}
	
	public Integer getEditUserId() {
		return this.editUserId;
	}
	
	public void setAddUserName(String value) {
		this.addUserName = value;
	}
	
	public String getAddUserName() {
		return this.addUserName;
	}
	
	public void setEditUserName(String value) {
		this.editUserName = value;
	}
	
	public String getEditUserName() {
		return this.editUserName;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getPersionBond() {
		return persionBond;
	}

	public void setPersionBond(String persionBond) {
		this.persionBond = persionBond;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("UserId",getUserId())
			.append("UserName",getUserName())
			.append("UserPassword",getUserPassword())
			.append("UserRealName",getUserRealName())
			.append("UserNotes",getUserNotes())
			.append("UserSex",getUserSex())
			.append("UserMobile",getUserMobile())
			.append("UserTel",getUserTel())
			.append("UserEmail",getUserEmail())
			.append("IsDelete",getIsDelete())
			.append("AddTime",getAddTime())
			.append("AddUserId",getAddUserId())
			.append("EditTime",getEditTime())
			.append("EditUserId",getEditUserId())
			.append("AddUserName",getAddUserName())
			.append("EditUserName",getEditUserName())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getUserId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserModel == false) return false;
		if(this == obj) return true;
		UserModel other = (UserModel)obj;
		return new EqualsBuilder()
			.append(getUserId(),other.getUserId())
			.isEquals();
	}
}

