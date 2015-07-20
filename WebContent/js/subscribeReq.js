var subscribe_current = null;
var indexCost = null;
var lotteryk = null;
	function submitSubscribeReq(schemeId,lottery_key) {
		lotteryk = lottery_key;
		
		var el = document.getElementById('subscribe_'+schemeId);
		if(!/\d+/.test(el.value)){
			setCommonResult('认购金额必须是整数', false);
			return;
		}
		 indexCost = parseInt(el.value,10);
		if(indexCost <= 0){
			setCommonResult('认购金额不能小于零', false);
			return;
		}
		subscribe_current = schemeId;
		$SSO.login_auth(checkUMInfo);
	};
	
	function checkUMInfo(){
	
		$SSO.login_auth(function(){
		
			if (confirm('您确认要购买？')) {
				
				$.ajax({
					url : window.BASESITE+"/"+lotteryk+"/scheme!subscribe.action",
					type : 'POST',
					cache : false,
					data : {
						"id" : subscribe_current,
						"subscribeForm.subscriptionCost" : indexCost,
						"ajax" : true
					},
					success : function(data, textStatus) {
				
						var jsonObj = toJsonObject(data);
						var msg = getCommonMsg(jsonObj);
						if (jsonObj.success == true) {
						var url = window.BASESITE+"/"+lotteryk+"/scheme!show.action?id="+subscribe_current;
						setCommonResult(msg+' 页面正在跳转...', true)							
						window.setTimeout(function(){window.location.href = url;},2000);
													
						} else {
							window.alert(msg);
						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						setCommonResult('提交请求失败.', false);
						subscribe_current = null;
						indexCost=null;
						lotteryk =null;
					},
					complete : function(XMLHttpRequest, textStatus) {
						subscribe_current = null;
						indexCost=null;
						lotteryk =null;
					}
				});
			}else{
				subscribe_current = null;
				indexCost=null;
				lotteryk =null;
			}
		});
	
	}