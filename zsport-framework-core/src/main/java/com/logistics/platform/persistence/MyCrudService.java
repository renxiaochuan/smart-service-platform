
/**
* @name: MyCrudService.java 
*
* @Copyright: (c) 2017 DLFC. All rights reserved. 
*
* @description: 
*
* @version: 1.0
* @date : 2017年1月13日 
* @author: Alex.Ge 
*
* @Modification  History:<br>
*  Date          Author         Version        Discription
*  2017年1月13日       Alex.Ge        1.0             <修改原因描述>
*/
package com.logistics.platform.persistence;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;

import com.logistics.platform.exception.ApplicationException;
import com.logistics.platform.utils.DateUtils;
import com.logistics.platform.utils.IdGen;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import cn.housecenter.dlfc.common.config.Global;
import cn.housecenter.dlfc.common.exception.ApplicationException;
import cn.housecenter.dlfc.common.service.BaseService;
import cn.housecenter.dlfc.common.utils.DateUtils;
import cn.housecenter.dlfc.common.utils.IdGen;

/**
 * @name: MyCrudService
 * @description:
 * 
 * @version 1.0
 * @author Alex.Ge
 *
 */
public abstract class MyCrudService<D extends MyCrudDao<E, T>, T extends MyDataEntity<T>, E> extends BaseService {

	@Autowired
	private D mapper;

	public D getMapper() {
		return mapper;
	}

	public long countByExample(E t) {
		return mapper.countByExample(t);
	}

	public int deleteByExample(E example) {
		return mapper.deleteByExample(example);
	}

	public int deleteByPrimaryKey(String id) {
		return mapper.deleteByPrimaryKey(id);
	}

	@Deprecated
	public int insert(T record) {
		return mapper.insert(record);
	}

	public int insertSelective(T record) {
		return mapper.insertSelective(record);
	}

	public List<T> selectByExample(E example) {
		return mapper.selectByExample(example);
	}

	public T selectByPrimaryKey(String id) {
		return mapper.selectByPrimaryKey(id);
	}

	public int updateByExampleSelective(@Param("record") T record, @Param("example") E example) {
		return mapper.updateByExampleSelective(record, example);
	}

	public int updateByExample(@Param("record") T record, @Param("example") E example) {
		return mapper.updateByExample(record, example);
	}

	public int updateByPrimaryKeySelective(T record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(T record) {
		return mapper.updateByPrimaryKey(record);
	}

	/**
	 * 
	 * @Title: create
	 * @Description: 设置id，createtime，modifytime，deleteflg，version属性；insert数据库对象，
	 *               空属性将不插入数据库。
	 * @date : 2017年2月10日
	 * @author: Alex.Ge
	 *
	 * @Modification History:<br>
	 * 
	 * @param record
	 * @return 数据库对象
	 * @throws ApplicationException
	 *
	 */
	public T create(T record) throws ApplicationException {
		try {
			record.setId(IdGen.uuid());
			record.setCreateTime(DateUtils.getSynchTime());
			record.setModifyTime(DateUtils.getSynchTime());
			record.setDeleteFlg(new Short(Global.NO));
			record.setVersion(0);
			insertSelective(record);
			return record;
		} catch (Exception e) {
			throw new ApplicationException("insert失败", e);
		}
	}

	/**
	 * 
	 * @Title: modify
	 * @Description: 设置modifytime，version属性；update数据库对象， 空属性将不插入数据库。
	 * @date : 2017年2月15日
	 * @author: Alex.Ge
	 *
	 * @Modification History:<br>
	 * 
	 * @param record
	 * @return
	 * @throws ApplicationException
	 *
	 */
	public T modify(T record) throws ApplicationException {
		try {
			record.setModifyTime(DateUtils.getSynchTime());
			record.setVersion(record.getVersion() + 1);
			updateByPrimaryKeySelective(record);
			return record;
		} catch (Exception e) {
			throw new ApplicationException("update失败", e);
		}
	}

	public void delete(T record) throws ApplicationException {
		try {
			record.setDeleteFlg(new Short(Global.YES));
			record.setModifyTime(DateUtils.getSynchTime());
			record.setVersion(record.getVersion() + 1);
			updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			throw new ApplicationException("delete失败", e);
		}
	}

	/**
	 * 
	 * @Title: getUniqueByExample
	 * @Description: 通过example查询唯一结果对象，如果不唯一，将返回异常，未查询到结果，返回null
	 * @date : 2017年2月15日
	 * @author: Alex.Ge
	 *
	 * @Modification History:<br>
	 * 
	 * @param example
	 * @return
	 * @throws ApplicationException
	 *
	 */
	public T getUniqueByExample(E example) throws ApplicationException {
		List<T> result = selectByExample(example);
		if (result.isEmpty()) {
			return null;
		} else if (result.size() > 1) {
			throw new ApplicationException("查询结果不唯一");
		}
		return result.get(0);
	}

	/**
	 * 
	 * @Title: getFirstByExample 
	 * @Description: 通过example查询结果集，返回第一条记录
	 * @date : 2017年3月30日 
	 * @author: Alex.Ge 
	 *
	 * @Modification  History:<br>
	 * 
	 * @param example
	 * @return
	 * @throws ApplicationException
	 *
	 */
	public T getFirstByExample(E example) throws ApplicationException {
		List<T> result = selectByExample(example);
		if (result.isEmpty()) {
			return null;
		} else {
			return result.get(0);
		}
	}

	/**
	 * 验证某个bean的参数
	 * 
	 * @param object
	 *            被校验的参数
	 * @throws ValidationException
	 *             如果参数校验不成功则抛出此异常
	 */
	@Deprecated
	public <O> void validate(O object) throws ApplicationException {
		// 获得验证器
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		// 执行验证
		try {
			Set<ConstraintViolation<O>> constraintViolations = validator.validate(object);
			if (constraintViolations != null) {
				for (ConstraintViolation<O> constraintViolation : constraintViolations) {
					if (constraintViolation != null) {
						throw new ApplicationException(constraintViolation.getMessage());
					}
				}
			}
		} catch (Exception e) {
			throw new ApplicationException("对象验证参数失败");
		}
	}

	/**
	 * 
	 * @Title: copyProperties
	 * @Description: Copy property values from the origin bean to the
	 *               destination bean for all cases where the property names are
	 *               the same.
	 * 
	 * 
	 * @date : 2017年2月10日
	 * @author: Alex.Ge
	 *
	 * @Modification History:<br>
	 * 
	 * @param dest
	 * @param orig
	 * @throws ApplicationException
	 *
	 */
	@Deprecated
	public Object copyProperties(Object dest, Object orig) throws ApplicationException {
		try {
			BeanUtils.copyProperties(dest, orig);
		} catch (Exception e) {
			throw new ApplicationException("对象赋值错误", e);
		}
		return dest;
	}

}
