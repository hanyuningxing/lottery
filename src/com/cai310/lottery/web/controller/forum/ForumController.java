package com.cai310.lottery.web.controller.forum;

import java.util.HashMap;
import java.util.LinkedList;

import javax.xml.ws.WebServiceException;

import com.cai310.lottery.web.controller.WebDataException;
import com.fivestars.interfaces.bbs.client.Client;
import com.fivestars.interfaces.bbs.util.XMLHelper;


public class ForumController{
	
	public static String login(String userName,String pwd){		
		Client e = new Client();
		String result = e.uc_user_login(userName, pwd);
		String $ucsynlogin = "";
		 
		LinkedList<String> rs = XMLHelper.uc_unserialize(result);
		if(rs.size()>0){
			int $uid = Integer.parseInt(rs.get(0));
			String $username = rs.get(1);
			String $password = rs.get(2);
			String $email = rs.get(3);
			if($uid > 0) {
				
				$ucsynlogin = e.uc_user_synlogin($uid);	

			} else if($uid == -1) {
				
				System.out.println("用户不存在,或者被删除");
				
			} else if($uid == -2) {
				System.out.println("密码错");
				
			} else {
				System.out.println("未定义");
				
			}
		}else{
			System.out.println("Login failed");
			System.out.println(result);
			
		}
		return $ucsynlogin;
	}
	
	public static String logout(){
		
		Client uc = new Client();

		String $ucsynlogout = uc.uc_user_synlogout();
		System.out.println("退出成功"+$ucsynlogout);
		
		return $ucsynlogout;
		

	}
	
	public static void reg(String userName, String password, String email) throws WebServiceException{
		
		Client uc = new Client();

		String $returns = uc.uc_user_register(userName, password ,email);
		int $uid = Integer.parseInt($returns);
		if($uid <= 0) {
			if($uid == -1) {
				System.out.print("用户名不合法");
				throw new WebServiceException("用户名不合法");
			} else if($uid == -2) {
				System.out.print("包含要允许注册的词语");
				throw new WebServiceException("包含要允许注册的词语");
			} else if($uid == -3) {
				System.out.print("用户名已经存在");
				throw new WebServiceException("用户名已经存在");
			} else if($uid == -4) {
				System.out.print("Email 格式有误");
				throw new WebServiceException("Email 格式有误");
			} else if($uid == -5) {
				System.out.print("Email 不允许注册");
				throw new WebServiceException("Email 不允许注册");
			} else if($uid == -6) {
				System.out.print("该 Email 已经被注册");
				throw new WebServiceException("该 Email 已经被注册");
			} else {
				System.out.print("未定义");
				throw new WebServiceException("用户名已经存在");
			}
		} else {
			System.out.println("OK:------------------------"+$returns);
		}
		

	}
	
	public static void updatePwd(String userName, String pwd, String new_pwd, String email) {
		Client e = new Client();
		String $returns = e.uc_user_edit(userName, pwd, new_pwd, email, 0, "", "");
		int $uid = Integer.parseInt($returns);
		if($uid==1) {
			System.out.println("密码修改成功.");
		} else{
			throw new WebServiceException("密码修改失败.");
		}
	}
}
