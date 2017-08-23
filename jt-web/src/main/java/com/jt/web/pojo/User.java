/**
 * 
 */
package com.jt.web.pojo;

import java.util.Date;

import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jt.common.po.BasePojo;

/**
 * @author Airey
 * @date   2017年8月6日
 */
@Table(name = "tb_user")
public class User extends BasePojo {
	private Long id;
	@Length(min = 6, max = 50, message = "用户名的长度为6~50")
	private String username;
	@JsonIgnore // 转换为json时，此属性值不进行封装
	@Length(min = 6, max = 20, message = "密码长度为6~20")
	private String password;
	@Length(min = 11, max = 11, message = "手机号长度为11")
	private String phone;
	@Email(message = "邮箱格式不正确")
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", phone=" + phone + ", email="
				+ email + "]";
	}

}
