/**
 * 
 */
package com.jt.web.threadLocal;

import com.jt.web.pojo.User;

/**
 * @author Airey
 * @date   2017年8月10日
 */
// 共享用户的信息
public class UserThreadLocal {
	private static ThreadLocal<User> USER = new ThreadLocal<User>();

	// 获取User对象
	public User get() {
		return USER.get();
	}

	// 设置User对象
	public static void set(User user) {
		USER.set(user);
	}

	// 获取userId
	public static Long getUserId() {
		try {
			return USER.get().getId();
		} catch (Exception e) {
			return null; //不能抛出错误，而返回null值，调用者通过是否是null来判断
		}

	}

}
