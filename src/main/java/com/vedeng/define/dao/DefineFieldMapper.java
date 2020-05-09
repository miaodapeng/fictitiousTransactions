package com.vedeng.define.dao;

import com.vedeng.define.model.DefineField;

public interface DefineFieldMapper {
    int deleteByPrimaryKey(Integer defineId);

    int insert(DefineField record);

    int insertSelective(DefineField record);

    DefineField selectByPrimaryKey(Integer defineId);

    int updateByPrimaryKeySelective(DefineField record);

    int updateByPrimaryKey(DefineField record);

	/** 
	 * <b>Description:</b>获取配置
	 * @param defineField
	 * @return DefineField
	 * @Note
	 * <b>Author：</b> lijie
	 * <b>Date:</b> 2019年3月12日 下午1:46:22
	 */
	DefineField getDefineFeld(DefineField defineField);

	/** 
	 * <b>Description:</b>删除配置
	 * @param defineField void
	 * @Note
	 * <b>Author：</b> lijie
	 * <b>Date:</b> 2019年3月12日 下午1:46:25
	 */
	void deleteMyDefine(DefineField defineField);

	/** 
	 * <b>Description:</b>批量更新
	 * @param defineField void
	 * @Note
	 * <b>Author：</b> lijie
	 * <b>Date:</b> 2019年3月12日 下午1:56:43
	 */
	void updateMyDefine(DefineField defineField);
}