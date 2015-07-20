/**
 * 操作金额
 * 参数。表单
 */
function oprUserMoneyForm(form){
			    if(window.confirm('您确认要提交吗？')){
			        var form=$(form);
					var schemeFormOption = {
						onSuccess: function(responseText, responseXML){
						// 请求成功后执行的方法
						   var jsonObj = JSON.decode(responseText);
						   var msg=jsonObj["actionMessages"];
							   if(null==msg){
							   	   msg=jsonObj["actionErrors"];
							   	   if(null==msg){
							   	  	 msg=jsonObj["fieldErrors"];
							  	   }
							   }
						   if(jsonObj["success"]){
						      window.alert(msg);
						      window.setTimeout(function(){window.location.reload();},1000);
						   }else{
						      window.alert(msg);
						   }
						},
						onFailure: function(instance){
						// 请求失败后执行的方法
						   window.alert('失败');
						}
					};
					form.set('send', schemeFormOption);// 设置form提交异步请求的处理方法
					form.send();// 提交异步请求 
				}
				return false;
} 