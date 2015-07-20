//双色球	
function ssq(){
		var period = $("#expectselect").val();
		//var fxtype = $("input[name=RadioGroup1][@checked]").val(); 
		var fxtype;	
		if(document.getElementById("RadioGroup1_0").checked){
			fxtype = document.getElementById("RadioGroup1_0").value;
		}else if(document.getElementById("RadioGroup1_1").checked){
			fxtype = document.getElementById("RadioGroup1_1").value;
		}else{
			fxtype = document.getElementById("RadioGroup1_2").value;
		}
		var ssq_default = $("#ssq_default").val();
		var url = window.BASESITE +"/admin/security/fetch!ssq.action?period="+period+"&fxtype="+fxtype+"&sort=1&ssq_default="+ssq_default;
		$.ajax({
		type : 'POST',
		cache : false,
		dataType : 'json',
		url : url,
		success : function(jsonObj) {
			if (jsonObj.success == true) {
				setCommonResult(jsonObj.msg, true);
				} else {		
					setCommonResult(jsonObj.msg, false);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});		
	}

//大乐透	
function dlt(){
		var period = $("#dlt_expectselect").val();
		var fxtype;	
		if(document.getElementById("dlt_1").checked){
			fxtype = document.getElementById("dlt_1").value;
		}else{
			fxtype = document.getElementById("dlt_2").value;
		}
		var dlt_default = $("#dlt_default").val();
		var url = window.BASESITE +"/admin/security/fetch!dlt.action?period="+period+"&fxtype="+fxtype+"&sort=1&dlt_default="+dlt_default;
		$.ajax({
		type : 'POST',
		cache : false,
		dataType : 'json',
		url : url,
		success : function(jsonObj) {
			if (jsonObj.success == true) {
				setCommonResult(jsonObj.msg,true);
				} else {		
					alert(jsonObj.msg,false);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});		
	}

//福彩3d 直选复式分析
function fc3d_zx(){
		var period = $("#fc3d_num_expectselect").val();
		var fc3d_num_default = $("#fc3d_num_default").val();
		var fxtype;	
		var url; 
		if(document.getElementById("fc3d_num_1").checked){
			fxtype = document.getElementById("fc3d_num_1").value;
			url = window.BASESITE +"/admin/security/fetch!fc3d_num.action?period="+period+"&fxtype="+fxtype+"&sort=1&fc3d_num_default="+fc3d_num_default;
		}else{
			fxtype = document.getElementById("fc3d_num_2").value;
			url = window.BASESITE +"/admin/security/fetch!fc3d_pos.action?period="+period+"&fxtype="+fxtype+"&sort=1&fc3d_num_default="+fc3d_num_default;
		}
		alert(url);
		$.ajax({
		type : 'POST',
		cache : false,
		dataType : 'json',
		url : url,
		success : function(jsonObj) {
			if (jsonObj.success == true) {
					alert(jsonObj.msg);
				} else {		
					alert(jsonObj.msg);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});		
	}


//福彩3d 组选复式分析
function fc3d_gx(){
		var period = $("#fc3d_group_expectselect").val();
		var fc3d_group_default = $("#fc3d_group_default").val();
		var fxtype;	
		var url; 
		var zxtype;
		if(document.getElementById("fc3d_group_1").checked){
			zxtype = document.getElementById("fc3d_group_1").value;
		}else{
			zxtype = document.getElementById("fc3d_group_2").value;
		}
		
		if(document.getElementById("fc3d_dz_2").checked){
			fxtype = document.getElementById("fc3d_dz_2").value;
			url = window.BASESITE +"/admin/security/fetch!fc3d_dz.action?period="+period+"&fxtype="+fxtype+"&sort=1&fc3d_group_default="+fc3d_group_default+"&zxtype="+zxtype;
		}else{
			fxtype = document.getElementById("fc3d_dg_2").value;
			url = window.BASESITE +"/admin/security/fetch!fc3d_dg.action?period="+period+"&fxtype="+fxtype+"&sort=1&fc3d_group_default="+fc3d_group_default+"&zxtype="+zxtype;
		}
		alert(url);
		$.ajax({
		type : 'POST',
		cache : false,
		dataType : 'json',
		url : url,
		success : function(jsonObj) {
			if (jsonObj.success == true) {
					alert(jsonObj.msg);
				} else {		
					alert(jsonObj.msg);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});		
	}


//数字排列  直选复式分析
function szpl_zx(){
		var period = $("#szpl_num_expectselect").val();
		var szpl_num_default = $("#szpl_num_default").val();
		var fxtype;	
		var url; 
		if(document.getElementById("szpl_num_1").checked){
			fxtype = document.getElementById("szpl_num_1").value;
			url = window.BASESITE +"/admin/security/fetch!szpl_num.action?period="+period+"&fxtype="+fxtype+"&sort=1&szpl_num_default="+szpl_num_default;
		}else{
			fxtype = document.getElementById("fc3d_num_2").value;
			url = window.BASESITE +"/admin/security/fetch!szpl_pos.action?period="+period+"&fxtype="+fxtype+"&sort=1&szpl_num_default="+szpl_num_default;
		}
		alert(url);
		$.ajax({
		type : 'POST',
		cache : false,
		dataType : 'json',
		url : url,
		success : function(jsonObj) {
			if (jsonObj.success == true) {
					alert(jsonObj.msg);
				} else {		
					alert(jsonObj.msg);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});		
	}


//数字排列  组选复式分析
function szpl_gx1(){
		var period = $("#szpl_group_expectselect").val();
		var szpl_group_default = $("#szpl_group_default").val();
		var fxtype;	
		var url; 
		var zxtype;
		if(document.getElementById("szpl_group_1").checked){
			zxtype = document.getElementById("szpl_group_1").value;
		}else{
			zxtype = document.getElementById("szpl_group_2").value;
		}
		
		if(document.getElementById("szpl_dz_2").checked){
			fxtype = document.getElementById("szpl_dz_2").value;
			url = window.BASESITE +"/admin/security/fetch!szpl_dz.action?period="+period+"&fxtype="+fxtype+"&sort=1&szpl_group_default="+szpl_group_default+"&zxtype="+zxtype;
		}else{
			fxtype = document.getElementById("fc3d_dg_2").value;
			url = window.BASESITE +"/admin/security/fetch!szpl_dg.action?period="+period+"&fxtype="+fxtype+"&sort=1&szpl_group_default="+szpl_group_default+"&zxtype="+zxtype;
		}
		alert(url);
		$.ajax({
		type : 'POST',
		cache : false,
		dataType : 'json',
		url : url,
		success : function(jsonObj) {
			if (jsonObj.success == true) {
					alert(jsonObj.msg);
				} else {		
					alert(jsonObj.msg);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});		
	}


/**
 * 以下是前端展现的方法
 */




