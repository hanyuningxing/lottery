package com.cai310.lottery.service.user;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.transform.ResultTransformer;

import com.cai310.lottery.common.FundDetailType;
import com.cai310.lottery.common.FundMode;
import com.cai310.lottery.common.Lottery;
import com.cai310.lottery.common.MessageType;
import com.cai310.lottery.common.PlatformInfo;
import com.cai310.lottery.common.PrepaymentType;
import com.cai310.lottery.common.ScoreDetailType;
import com.cai310.lottery.common.TransactionType;
import com.cai310.lottery.entity.lottery.Scheme;
import com.cai310.lottery.entity.security.AdminUser;
import com.cai310.lottery.entity.ticket.TicketPlatformInfo;
import com.cai310.lottery.entity.user.BankInfo;
import com.cai310.lottery.entity.user.DrawingOrder;
import com.cai310.lottery.entity.user.FundDetail;
import com.cai310.lottery.entity.user.Ipsorder;
import com.cai310.lottery.entity.user.LuckDetail;
import com.cai310.lottery.entity.user.Media;
import com.cai310.lottery.entity.user.PhonePopu;
import com.cai310.lottery.entity.user.Popu;
import com.cai310.lottery.entity.user.Prepayment;
import com.cai310.lottery.entity.user.RandomMessage;
import com.cai310.lottery.entity.user.ScoreDetail;
import com.cai310.lottery.entity.user.Transaction;
import com.cai310.lottery.entity.user.User;
import com.cai310.lottery.entity.user.UserGrade;
import com.cai310.lottery.entity.user.UserInfo;
import com.cai310.lottery.entity.user.UserLogin;
import com.cai310.lottery.entity.user.UserReport;
import com.cai310.lottery.service.lottery.SchemeEntityManager;
import com.cai310.lottery.web.controller.user.BankInfoForm;
import com.cai310.orm.Page;
import com.cai310.orm.PropertyFilter;

/**
 * 用户相关实体的管理接口, 包括用户,交易,预付款,资金明细等.
 * 
 */
public interface UserEntityManager {
	
	
	Page<User> searchUser(final Page<User> page, final List<PropertyFilter> filters);

	/**
	 * 获取用户
	 * 
	 * @param id 用户编号
	 * @return 用户对象
	 */
	User getUser(Long id);
	/**
	 * 获取接票平台信息
	 * @param id
	 * @return
	 */
	TicketPlatformInfo getTicketPlatformInfo(Long id);

	/**
	 * 获取用户
	 * 
	 * @param 用户名字
	 * @return 用户对象
	 */
	User getUserBy(String userName);
	/**
	 * 获取QQ用户
	 * @param id
	 * @return
	 */
	User getQqUserById(String id);

	/**
	 * 保存用户信息
	 * 
	 * @param user 用户信息
	 * @return 保存后的对象
	 */
	User saveUser(User user);
	/**
	 * 保存接票平台信息
	 * @param ticketPlatformInfo
	 * @return
	 */
	TicketPlatformInfo saveTicketPlatformInfo(TicketPlatformInfo ticketPlatformInfo);
	/**
	 * 保存用户意见反馈
	 * 
	 * @param userReport 意见反馈
	 * @return 保存后的对象
	 */
	UserReport saveUserReport(UserReport userReport); 
	/**
	 * 获取媒体
	 * 
	 * @param id 媒体编号
	 * @return 媒体对象
	 */
	Media getMedia(Long id);

	/**
	 * 获取媒体
	 * 
	 * @param 媒体名字
	 * @return 媒体对象
	 */
	Media getMediaBy(String mediaName);

	/**
	 * 保存媒体信息
	 * 
	 * @param media 媒体信息
	 * @return 保存后的对象
	 */
	Media saveMedia(Media media);
	
	/**
	 * 获取推广
	 * 
	 * @param id 推广编号
	 * @return 推广对象
	 */
	Popu getPopu(Long id);

	/**
	 * 保存推广信息
	 * 
	 * @param popu 推广信息
	 * @return 保存后的对象
	 */
	Popu savePopu(Popu popu);
	
	/**
	 * 获取推广
	 * 
	 * @param id 推广编号
	 * @return 推广对象
	 */
	PhonePopu getPhonePopu(Long id);

	/**
	 * 保存推广信息
	 * 
	 * @param phonePopu 推广信息
	 * @return 保存后的对象
	 */
	PhonePopu savePhonePopu(PhonePopu phonePopu);

	/**
	 * 保存用户信息(后台)
	 * 
	 * @param user 用户信息
	 * @return 保存后的对象
	 */
	User saveUser(User user, AdminUser adminUser,Double rebate);

	/**
	 * 保存用户信息
	 * 
	 * @param user 账户信息
	 * @param info 个人资料
	 * @param bank 银行信息
	 * @return 保存后的对象
	 */
	User saveUser(User user, UserInfo info, BankInfo bank);
	
	User saveUser(User user, UserInfo info, BankInfo bank, String password);
	
	User saveUser(User user, String oldPwd, String newPwd);
	/**
	 * 储存用户登录记录
	 * 
	 * @param UserLogin userLogin
	 */
	UserLogin saveUserLogin(UserLogin userLogin);

	/**
	 * 得到用户登录失败次数
	 * 
	 * @param User user
	 */
	Integer getUserLoginNum(User user);

	/**
	 * 清除用户登录不成功信息(登录成功后)
	 * 
	 * @param User user
	 */
	void clearFailUserLogin(User user);

	/**
	 * 创建交易记录
	 * 
	 * @param type 交易类型
	 * @param remark 备注
	 * @return 创建的交易记录
	 */
	Transaction createTransaction(TransactionType type, String remark);

	/**
	 * 创建预付款
	 * 
	 * @param transactionId 交易ID
	 * @param userId 用户ID
	 * @param amount 预付款金额
	 * @param prepaymentType 预付款类型
	 * @param fundDetailType 资金明细类型
	 * @param remark 备注
	 * @return 创建的预付款
	 */
	Prepayment createPrepayment(Long transactionId, Long userId, BigDecimal amount, PrepaymentType prepaymentType,
			FundDetailType fundDetailType, String remark,PlatformInfo platformInfo,Lottery lottery);
	
	/**
	 * 创建预付款
	 * 
	 * @param transactionId 交易ID
	 * @param userId 用户ID
	 * @param amount 预付款金额
	 * @param prepaymentType 预付款类型
	 * @param fundDetailType 资金明细类型
	 * @param remark 备注
	 * @return 创建的预付款
	 */
	Prepayment createKenoPrepayment(Long transactionId, Long userId, BigDecimal amount, PrepaymentType prepaymentType,
			FundDetailType fundDetailType, String remark,PlatformInfo platformInfo,Lottery lottery);

	/**
	 * 取消交易
	 * 
	 * @param transactionId 交易ID
	 * @param fundDetailType 资金明细类型
	 * @param cause 原因
	 */
	void cancelTransaction(Long transactionId, FundDetailType fundDetailType, String cause);

	/**
	 * 取消预付款
	 * 
	 * @param prepaymentId 预付款ID
	 * @param fundDetailType 资金明细类型
	 * @param cause 原因
	 */
	void cancelPrepayment(Long prepaymentId, FundDetailType fundDetailType, String cause);

	/**
	 * 交易退款
	 * 
	 * @param transactionId 交易ID
	 * @param fundDetailType 资金明细类型
	 * @param cause 原因
	 */
	void refundmentTransaction(Long transactionId, FundDetailType fundDetailType, String cause);

	/**
	 * 预付款退款
	 * 
	 * @param transactionId 交易ID
	 * @param fundDetailType 资金明细类型
	 * @param cause 原因
	 */
	void refundmentPrepayment(Long prepaymentId, FundDetailType fundDetailType, String cause);

	/**
	 * 转换预付款
	 * 
	 * @param transactionId 交易ID
	 * @param fromId 来源预付款ID
	 * @param transferCost 转换的金额
	 * @param prepaymentType 新预付款的类型
	 * @param fundDetailType 资金明细类型
	 * @param remark 备注
	 * @return 新创建的预付款
	 */
	Prepayment transferPrepayment(Long transactionId, Long fromId, BigDecimal transferCost,
			PrepaymentType prepaymentType, FundDetailType fundDetailType, String remark,PlatformInfo platformInfo,Lottery lottery);
	/**
	 * 转换预付款
	 * 
	 * @param transactionId 交易ID
	 * @param fromId 来源预付款ID
	 * @param transferCost 转换的金额
	 * @param prepaymentType 新预付款的类型
	 * @param fundDetailType 资金明细类型
	 * @param remark 备注
	 * @return 新创建的预付款
	 */
	Prepayment transferKenoPrepayment(Long transactionId, Long fromId, BigDecimal transferCost,
			PrepaymentType prepaymentType, FundDetailType fundDetailType, String remark,PlatformInfo platformInfo,Lottery lottery);
	/**
	 * 完成交易
	 * 
	 * @param transactionId
	 */
	void commitTransaction(Long transactionId);
	/**
	 * @param user 用户
	 * @param money 金额
	 * @param remark 备注
	 * @param mode 种类
	 * @param type 类型
	 * @param adminUser 后台用户。可null
	 * @return
	 */
	FundDetail oprUserMoney(User user, BigDecimal money, String remark, FundMode mode, FundDetailType type,
			AdminUser adminUser);

	/**
	 * 得到提款订单
	 * 
	 * @param id
	 * @return
	 */
	DrawingOrder getDrawingOrder(Long id);

	/**
	 * 保存提款订单
	 * 
	 * @param drawingOrder
	 * @return
	 */
	DrawingOrder saveDrawingOrder(DrawingOrder drawingOrder);

	/**
	 * 保存提款订单(后台)
	 * 
	 * @param drawingOrder
	 * @return
	 */
	DrawingOrder saveAdminDrawingOrder(AdminUser adminUser, DrawingOrder drawingOrder);

	/**
	 * 删除提款订单
	 * 
	 * @param drawingOrder
	 * @return
	 */
	DrawingOrder deleteDrawingOrder(AdminUser adminUser, DrawingOrder drawingOrder);

	/**
	 * @param user 用户
	 * @param money 金额
	 * @param bankAccounts 银行账号
	 * @param tel 电话
	 * @return
	 */
	DrawingOrder oprDrawingOrder(User user, BigDecimal money, BankInfo bankInfo, String tel);

	/**
	 * 保存用户个人信息
	 * 
	 * @param info 用户个人信息
	 * @return 保存后的对象
	 */
	UserInfo saveUserInfo(UserInfo info);
	
	/**
	 * 保存用户战绩
	 * @param info
	 * @return
	 */
	UserGrade saveUserGrade(UserGrade info);
	
	/**
	 * 保存用户战绩
	 * @param info
	 * @return
	 */
	UserGrade saveUserGrade(User user,UserGrade info);
	
	/**
	 * 更新用户战绩
	 * @param userId
	 * @param schemeManager
	 * @param schemes
	 * @param prizesPtJson
	 * @param medalsPtJson
	 */
	void updateUserGrade(Long userId,SchemeEntityManager schemeManager,List<Scheme> schemes,UserGrade userGrade);

	/**
	 * 保存用户银行信息
	 * 
	 * @param bank 用户银行信息
	 * @return 保存后的对象
	 */
	BankInfo saveBankInfo(BankInfo bank);

	/**
	 * 保存在线支付
	 * 
	 * @param ipsorder 在线支付订单
	 * @return 保存后的对象
	 */
	Ipsorder saveIpsorder(Ipsorder ipsorder);
	
	/**
	 * 通过在线支付(后台手动)
	 * 
	 * @param ipsorder 在线支付订单
	 * @return 保存后的对象
	 */
	Ipsorder confirmIps(Ipsorder ipsorder,User user,AdminUser adminUser);

	/**
	 * get在线支付
	 * 
	 * @param 在线支付订单Id
	 * @return Ipsorder
	 */
	Ipsorder getIpsorder(Long id);
	
	
	/**
	 * 代理商给下属加钱
	 * 
	 * @param agentUser 代理商用户
	 * @param user 操作用户
	 * @return 保存后的对象
	 */
	void agentOprUserMoney(User agentUser,User user,BigDecimal money);

	List<Ipsorder> countIpsorder(List<Criterion> restrictions,
			ProjectionList prop, ResultTransformer resultTransformer);

	List<DrawingOrder> countDrawingOrder(List<Criterion> restrictions,
			ProjectionList prop, ResultTransformer resultTransformer);
	
	boolean resetPasswordAndSendEmail(String userName, String email) throws Exception;
	
	boolean resetPasswordAndSendMessage(String userName, String mobile) throws Exception ;
	/**
	 * 保存用户详情送彩金
	 * 
	 * @param info 用户详情
	 * @param user 操作用户
	 * @return 保存后的对象
	 */
   // UserInfo saveUserInfoWithLuckSend(UserInfo info,User user) ;
    /**
	 * 保存用户送彩金
	 * 
	 * @param user 操作用户
	 * @param info 用户详情
	 * @param bank 操作用户
	 * @return 保存后的对象
	 */
   // User saveUserWithLuckSend(User user, UserInfo info, BankInfo bank) ;
	/**
	 * 随机验证码
	 * 
	 * @param user
	 *            用户实体
	 * @param mobile
	 *            电话号码
	 * @param messageType
	 *            活动消息类型，参考com.cai310.lottery.common.MessageType
	 * @return RandomMessage实体
	 */
	RandomMessage SendRandomMessage(User user, String mobile, MessageType checkphone);
	
	void saveBankInfoWith(BankInfoForm bankInfoForm, BankInfo bankInfo);
	
	void userRandomMessage(String mobile, MessageType checkphone, Integer msgCode); 
	
	UserLogin getUserLoginBy(Long userId);
	
	void deleteBankInfo(BankInfo bankInfo);

	LuckDetail saveLuckDetail(LuckDetail luckDetail);
	
	String getRandomPassword();
	
	/**
	 * @param user 用户
	 * @param score 金额
	 * @param remark 备注
	 * @param adminUser 后台用户。可null
	 * @return
	 */
	ScoreDetail oprUserScore(User user, BigDecimal score, String remark, FundMode mode, ScoreDetailType type,AdminUser adminUser);
}
