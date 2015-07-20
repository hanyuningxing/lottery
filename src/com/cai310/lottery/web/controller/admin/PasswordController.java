package com.cai310.lottery.web.controller.admin;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.providers.encoding.PasswordEncoder;

import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.web.controller.WebDataException;
import com.cai310.utils.RegexUtils;

public class PasswordController extends AdminBaseController {
	private static final long serialVersionUID = -7128890110025210382L;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	public String index(){
		return "password";
	}
	/** 旧密码 */
	private String oldPassword;

	/** 新密码 */
	private String newPassword;

	/** 确认密码 */
	private String confirmPassword;

	public String doPasswordEdit() throws WebDataException{
		AdminUser adminUser = getAdminUser();
		if (null == adminUser) {
			addActionError("权限不足！");
			return index();
		}
		if (StringUtils.isBlank(this.getOldPassword())){
			addActionError("原密码不能为空.");
			return index();
		}

		if (StringUtils.isBlank(this.getNewPassword())){
			addActionError("新密码不能为空.");
			return index();
		}
		else if (!this.getNewPassword().matches(RegexUtils.LetterAndNumberAndUnderline)){
			addActionError("密码只能由英文字母、数字或下划线组成.");
			return index();
		}
		else if (this.getNewPassword().length() < 6 || this.getNewPassword().length() > 20){
			addActionError("密码不合法(长度为6-20个字符).");
			return index();
		}
		else if (!this.getNewPassword().equals(this.getConfirmPassword())){
			addActionError("两次输入的密码不一致.");
			return index();
		}

		adminUser = securityEntityManager.getUser(adminUser.getId());
		if (!adminUser.getPassword().equals(
				passwordEncoder.encodePassword(this.getOldPassword(),null))){
			addActionError("原密码不正确.");
			return index();
		}
		adminUser.setPassword(passwordEncoder.encodePassword(this.getNewPassword(),null));

		adminUser = securityEntityManager.saveUser(adminUser,adminUser);
		addActionMessage("修改密码成功.");
		return index();
	}

	/**
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * @param oldPassword the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
