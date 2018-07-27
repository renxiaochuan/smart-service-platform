
package com.logistics.platform.persistence;



import com.logistics.platform.utils.DateUtils;
import com.logistics.platform.utils.IdGen;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * 数据Entity类
 * 
 * @author dlfc
 * @version 2014-05-16
 */
public abstract class MyDataEntity<T>  implements Serializable {
	private static final Short DEL_FLAG_NORMAL = 1;
	private static final long serialVersionUID = 1L;
	/**
	 * 实体编号（唯一标识）
	 */
	protected String id;
	// 公共字段
	/**
	 * 创建人ID
	 */
	protected String createUser;

	/**
	 * 创建时间
	 */
	protected Date createTime;
    /**
     * 创建者身份
     */
    protected Short createUserIdentity;


    /**
	 * 创建时间格式化字符串
	 */
	protected String createTimeStr;

	/**
	 * 修改时间格式化字符串
	 */
	protected String modifyTimeStr;

	/**
	 * 修改人ID
	 */
	protected String modifyUser;
	/**
	 * 修改者身份
	 */
	protected Short modifyUserIdentity;
	/**
	 * 修改时间
	 */
	protected Date modifyTime;
	/**
	 * 删除标记（0：正常；1：删除；2：审核）
	 */
	protected Short deleteFlg;
	/**
	 * 版本
	 */
	protected Integer version;
	/**
	 * log表描述
	 */
	protected String dcrp;
	/**
	 * 操作事件
	 */
	protected Integer optEvent;
	/**
	 * 操作人
	 */
	protected String operator;
	/**
	 * 操作时间
	 */
	protected Date optTime;

	public MyDataEntity() {
		super();
		this.deleteFlg = DEL_FLAG_NORMAL;
	}

	public MyDataEntity(String id) {
		this();
		this.id = id;
	}
	/**
	 * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
	 * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
	 */
	protected boolean isNewRecord = false;
	/**
	 * 插入之前执行方法，需要手动调用
	 */
 	public void preInsert() {
		// 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
		if (!this.isNewRecord) {
			setId(IdGen.uuid());
		}

		// -------dlfc-----st
		this.modifyTime = DateUtils.getSynchTime();
		this.createTime = this.modifyTime;
		this.version = 0;
		this.deleteFlg = 0;
		// ------dlfc------ed
	}

	/**
	 * 更新之前执行方法，需要手动调用
	 */
 	public void preUpdate() {
//		User user = UserUtils.getUser();
//		if (StringUtils.isNotBlank(user.getId())) {
//			this.modifyUser = user.getId();
//		}
		this.modifyTime = DateUtils.getSynchTime();

	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Short getCreateUserIdentity() {
		return createUserIdentity;
	}

	public void setCreateUserIdentity(Short createUserIdentity) {
		this.createUserIdentity = createUserIdentity;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getModifyTimeStr() {
		return modifyTimeStr;
	}

	public void setModifyTimeStr(String modifyTimeStr) {
		this.modifyTimeStr = modifyTimeStr;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Short getModifyUserIdentity() {
		return modifyUserIdentity;
	}

	public void setModifyUserIdentity(Short modifyUserIdentity) {
		this.modifyUserIdentity = modifyUserIdentity;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Short getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(Short deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 分页-当前页面
	 */
	private String currentPage;
	/**
	 * 分页开始
	 */
	private Integer rowFrom = null;
	/**
	 * 设置排序字段,多个字段中间可用逗号隔开,如 create_time desc, modify_time asc
	 */
	private String orderBy;

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getRowFrom() {
		return rowFrom;
	}

	public void setRowFrom(Integer rowFrom) {
		this.rowFrom = rowFrom;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * 取得UUID
	 * 
	 * @return 无-的UUID
	 */
	public String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 获取对应Log实体的实例
	 * @param t
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	protected Object getLogName(T t) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		String className = t.getClass().getName();
		String classLogName = "";
		if(className.contains("Agr")){
			classLogName = className.replace("Agr", "Log");
		}else{
			classLogName = className + "Log";
		}
		Class<?> clazz = Class.forName(classLogName);
		return clazz.newInstance();
	}
}
