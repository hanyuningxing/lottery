<html>
<head>
<meta name="menu" content="index"/>
<meta name="menuItem" content="staticIndex"/>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<link href="<@c.url value= "/pages/css/cindex.css" />" rel="stylesheet" type="text/css" />
<script type="text/javascript">window.BASESITE='${base}';</script>
<script type="text/javascript" src="/js/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript" src="/js/cookie.js"></script>
<script type="text/javascript" src="/js/lottery/count.js"></script>	
<script type="text/javascript" src="/js/thinkbox/thinkbox.js"></script>		
<script type="text/javascript" src="/js/lottery/luck.js"></script>	
<script type="text/javascript" src="/js/ssologin.js"></script>
<script type="text/javascript">
	function confirmIps(){
	    if (confirm("您确定手动通过该订单吗？")) {
		          return true;
		}else{
		     return false;
		}
	}
</script>
<style>
<!--
.main_view {position: relative;}
.window {height: 185px;width: 340px;overflow: hidden;position: relative;margin-bottom:5px;}
.image_reel {position: absolute;top: 0;left: 0;}
.image_reel img {float: left;}
.paging {position: absolute;top: 150px;left: 0;width: 340px;height: 40px;z-index: 100;text-align:right;line-height: 40px;background: url(../images/local/slider_pic_nav_bg.png) repeat-x 0 0;display: none;}
.paging a {padding: 5px;text-decoration: none;color: #ffffff;margin-right:10px;}
.paging a.active {font-weight: bold;background: #920000;border: 1px solid #610000;-moz-border-radius: 3px;-khtml-border-radius: 3px;-webkit-border-radius: 3px;}
.paging a:hover {font-weight: bold;}
-->
</style>
</head>
<body>
  <table width="100%" border="0" cellpadding="1" cellspacing="1" bgcolor="#DCDCDC">
    <tr class="trlbrown">
	  <td height="22">编号</td>
      <td>标题</td>
      <td>联接</td>
      <td>操作</td>
    </tr>
   <#if adImagesList??&& adImagesList?size gt 0>
		<#list adImagesList as data>
					<#if data_index%2==0><#assign trColor="trw" /><#else><#assign trColor="trgray" /></#if>
    	<tr class="${trColor}" onmouseover="this.className='trlbro'" onmouseout="this.className='${trColor}'">
					  <td height="22">${data.pos}</td>
				      <td>${data.word}</td>
				      <td>${data.link}</td>
				      <td><a href="<@c.url value= "/admin/info/index!deletePic.action?id=${data.id}" />" onclick="return confirmIps();">删除</a></td>
				    </tr>			                   					
		</#list>
	</#if>
    
    
   
  </table>
  <form action ="<@c.url value= "/admin/info/index!uploadPic.action" />" method ="POST" enctype ="multipart/form-data" > 
      <table width="70%" border="0" cellpadding="6" cellspacing="1" bgcolor="#E6E2D6">
					        <tr bgcolor="#FFFFFF" height=250px;>
				              <td bgcolor="#FFFFFF"  colspan="2">
				              <div class="main_view">
								                <div class="window">
								                    <div class="image_reel">
								                        <#if adImagesList??&& adImagesList?size gt 0>
													    	  <#list adImagesList as data>
													    	     <a href="${data.link!}" target='_blank'><img src="${base}${data.name!}" alt="${data.word!}" width="340" height="185" title="${data.word!}"/></a>
													          </#list>
													    </#if>
								                    </div>
								                </div>
								                <div class="paging">
								                    <#if adImagesList??&& adImagesList?size gt 0>
								                    			<#list adImagesList as data>
								                   					 <a href="#" rel="${data_index+1}">${data_index+1}</a>
													    	    </#list>
												    </#if>
								                </div>
								</div>
				              
				              <script type="text/javascript">
							            $(document).ready(function() {
							                $(".paging").show();
							                $(".paging a:first").addClass("active");
							                var imageWidth = $(".window").width();
							                var imageSum = $(".image_reel img").size();
							                var imageReelWidth = imageWidth * imageSum;
							                $(".image_reel").css({
							                    'width' : imageReelWidth
							                });
							                var rotate = function() {
							                    var triggerID = $active.attr("rel") - 1;
							                    var image_reelPosition = triggerID * imageWidth;
							                    $(".paging a").removeClass('active');
							                    $active.addClass('active');
							                    $(".image_reel").animate({
							                        left : -image_reelPosition
							                    }, 500);
							                };
							                var rotateSwitch = function() {
							                    play = setInterval(function() {
							                        $active = $('.paging a.active').next();
							                        if($active.length === 0) {
							                            $active = $('.paging a:first');
							                        }
							                        rotate();
							                    }, 3000);
							                };
							                rotateSwitch();
							                $(".image_reel a").hover(function() {
							                    clearInterval(play);
							                }, function() {
							                    rotateSwitch();
							                });
							                $(".paging a").click(function() {
							                    $active = $(this);
							                    clearInterval(play);
							                    rotate();
							                    return false;
							                });
							            });
							</script>
				              </td>
				            </tr>
				             <tr bgcolor="#FFFFFF">
				              <td width="130" align="center" bgcolor="#F5f5f5">广告1</td>
				              <td bgcolor="#FFFFFF">
				               <span class="ssq_tr_2">
				                                                  图片 ：<input type="file" name ="pic1_file"/><br/>   
								       文字 ：<input type="text" name ="pic1_word"/><br/>   
								       连接 ：<input type="text" name ="pic1_link"/>没有连接请输入#号
					           </span>
				              </td>
				            </tr>
				            <tr bgcolor="#FFFFFF">
				              <td width="130" align="center" bgcolor="#F5f5f5">广告2</td>
				              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
					                                       图片 ：<input type="file" name ="pic2_file"/><br/>   
								       文字 ：<input type="text" name ="pic2_word"/><br/>   
								       连接 ：<input type="text" name ="pic2_link"/>没有连接请输入#号
					           </span>
				              </td>
				            </tr>
				            <tr bgcolor="#FFFFFF">
				              <td width="130" align="center" bgcolor="#F5f5f5">广告3</td>
				              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
					                                       图片 ：<input type="file" name ="pic3_file"/><br/>   
								       文字 ：<input type="text" name ="pic3_word"/><br/>   
								       连接 ：<input type="text" name ="pic3_link"/>没有连接请输入#号
					           </span>
				              </td>
				            </tr>
				            <tr bgcolor="#FFFFFF">
				              <td width="130" align="center" bgcolor="#F5f5f5">广告4</td>
				              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
					                                       图片 ：<input type="file" name ="pic4_file"/><br/>   
								       文字 ：<input type="text" name ="pic4_word"/><br/>   
								       连接 ：<input type="text" name ="pic4_link"/>没有连接请输入#号
					           </span>
				              </td>
				            </tr>
				            <tr bgcolor="#FFFFFF">
				              <td width="130" align="center" bgcolor="#F5f5f5">广告5</td>
				              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
					                                       图片 ：<input type="file" name ="pic5_file"/><br/>   
								       文字 ：<input type="text" name ="pic5_word"/><br/>   
								       连接 ：<input type="text" name ="pic5_link"/>没有连接请输入#号
					           </span>
				              </td>
				            </tr>
				            <tr bgcolor="#FFFFFF">
				              <td width="130" align="center" bgcolor="#F5f5f5">广告6</td>
				              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
					                                       图片 ：<input type="file" name ="pic6_file"/><br/>   
								       文字 ：<input type="text" name ="pic6_word"/><br/>   
								       连接 ：<input type="text" name ="pic6_link"/>没有连接请输入#号
					           </span>
				              </td>
				            </tr>
				            <tr bgcolor="#FFFFFF">
				              <td width="130" align="center" bgcolor="#F5f5f5"></td>
				              <td bgcolor="#FFFFFF"><span class="ssq_tr_2">
					           <input type="submit"  value="提交" />
					           </span>
				              </td>
				            </tr>
	  </table> 
       </form > 
       
   </body>
</html>   