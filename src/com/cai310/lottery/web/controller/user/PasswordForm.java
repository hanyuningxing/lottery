package com.cai310.lottery.web.controller.user;

public class PasswordForm {
	/** 旧密码 */
	private String oldPassword;

	/** 新密码 */
	private String newPassword;

	/** 确认密码 */
	private String confirmPassword;

	/**
	 * @return {@link #oldPassword}
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * @param oldPassword the {@link #oldPassword} to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * @return {@link #newPassword}
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword the {@link #newPassword} to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return {@link #confirmPassword}
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword the {@link #confirmPassword} to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
