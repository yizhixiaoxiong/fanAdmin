package com.fan.manage.pojo;

/**
 * 会员类
 * 
 * @author hanxiaofan
 *
 */
public class AppUser {

	private Integer USER_ID; // id
	private String USERNAME; // 用户名
	private String PASSWORD; // 密码
	private String NAME; // 名称
	private String RIGHTS; // 权限
	private Role Role; // 角色
	private String IP; // ip
	private String STATUS; // 状态
	private String BZ; // 备注
	private String PHONE; // 电话
	private String SFID; // 皮肤
	private String START_TIME; // 开始时间
	private String END_TIME; // 结束时间
	private String NUMBER; // 编码
	private String EMAIL; // 邮箱
	private Integer YEARS; // 期限
	private String LAST_LOGIN; // 最后登录

	public Integer getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(Integer uSER_ID) {
		USER_ID = uSER_ID;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getRIGHTS() {
		return RIGHTS;
	}

	public void setRIGHTS(String rIGHTS) {
		RIGHTS = rIGHTS;
	}

	public Role getRole() {
		return Role;
	}

	public void setRole(Role role) {
		Role = role;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getBZ() {
		return BZ;
	}

	public void setBZ(String bZ) {
		BZ = bZ;
	}

	public String getPHONE() {
		return PHONE;
	}

	public void setPHONE(String pHONE) {
		PHONE = pHONE;
	}

	public String getSFID() {
		return SFID;
	}

	public void setSFID(String sFID) {
		SFID = sFID;
	}

	public String getSTART_TIME() {
		return START_TIME;
	}

	public void setSTART_TIME(String sTART_TIME) {
		START_TIME = sTART_TIME;
	}

	public String getEND_TIME() {
		return END_TIME;
	}

	public void setEND_TIME(String eND_TIME) {
		END_TIME = eND_TIME;
	}

	public String getNUMBER() {
		return NUMBER;
	}

	public void setNUMBER(String nUMBER) {
		NUMBER = nUMBER;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public Integer getYEARS() {
		return YEARS;
	}

	public void setYEARS(Integer yEARS) {
		YEARS = yEARS;
	}

	public String getLAST_LOGIN() {
		return LAST_LOGIN;
	}

	public void setLAST_LOGIN(String lAST_LOGIN) {
		LAST_LOGIN = lAST_LOGIN;
	}

}
