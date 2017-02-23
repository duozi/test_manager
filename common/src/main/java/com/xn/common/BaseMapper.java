package com.xn.common;

import java.util.List;
import java.util.Map;

/**
 * BaseMapper Mapper基类
 * @param <E> 需要持久化的实体类型
 * @param <K> 实体类型对应的主键类型
 * @author Zhoutao
 * @version v1.0 BaseMapper.java 2013-10-14 下午09:11:48
 */

public interface BaseMapper<E, K> {

	/** 插入对象 返回插入的数量 **/
	int save(E entity);

	/** 批量插入对象 返回插入的数量 **/
	int saveBatch(List<? extends E> entityList);

	/** 更新对象 返回更新的数量 **/
	int update(E entity);

	/** 批量更新对象 返回更新的数量 **/
	int updateBatch(List<E> entityList);

	/** 删除对象 返回删除的数量 **/
	int deleteByPK(K key);

	/** 批量删除对象 返回删除的数量 **/
	int deleteBatchByPK(List<K> keyList);

	int delete(E e);

	int deleteBatch(E e);

	/** 用对象/主键/Map查询一个对象 **/
	E get(Object condition);


	/** 按条件查询对象 **/
	List<E> list(Map<String, Object> parameters);

	/** 按条件查询对象 **/
	List<E> list(Object condition);

	/**
	 *
	 * 级联查询列表
	 * @author Zhoutao
	 * @date 2014-8-6
	 * @recently Zhoutao
	 * @param condition 查询对象，map/复杂对象
	 * @return
	 */
	List<E> listCascade(Object condition);

	/** 按条件统计对象数量 **/
	long count(Map<String, Object> parameters);

	/** 按条件统计对象数量 **/
	long count(Object condition);

}

