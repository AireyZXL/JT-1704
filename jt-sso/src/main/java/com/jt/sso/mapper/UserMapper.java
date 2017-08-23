/**
 * 
 */
package com.jt.sso.mapper;

import org.apache.ibatis.annotations.Param;

import com.jt.common.mapper.SysMapper;
import com.jt.sso.pojo.User;

/**
 * @author Airey
 * @date   2017年8月6日
 */
public interface UserMapper extends SysMapper<User>{

	/**
	 * 检验数据是否可用
	 * @param param
	 * @param paramType
	 * @return
	 */
	Integer check(@Param("param") String param, @Param("paramType") String paramType);

}
