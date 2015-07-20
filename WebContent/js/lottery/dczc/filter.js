$(function() {
	var CONDTION_FLAG = [100,31,32,21,22,11,12,101,102];//条件标识 100标识容错 101,102分别表示左右下拉选项

	var hashObjArray = [];
	var step=0;//记录处理次数
	var chgFlag = true;
	var size = $("#size").val();
	var html = '';
	function FilterModel(){ 
        this.seletedCondCount = 0;// 当前选中的条件个数
        this.condArray = [];//当前存放条件选项
        this.step = 0;// 当前容错条件次数
        this.flag='n';//y表示已处理，n表示未处理
        this.seletedFaultCount = 0;
        this.units=0;
        this.state='未处理';
        this.totalFaultCount = 0;//当前步骤容错的总数
        this.ltCond = 0;//当前步骤容错小于项的值
        this.gtCond = 0;//当前步骤容错大于项的值

        this.condArray_push = function (index, obj){
        	this.condArray[index] = obj;
        	this.seletedFaultCount++;
    	}
    	
    	this.condArray_remove = function(index) {
    		delete this.condArray[index];
    		if(index < this.condArray.length-1){
	    		for ( var i = index; i < this.condArray.length; i++) {
	    			this.condArray[i] = this.condArray[i+1];
	    		}
    		}
    		this.condArray.length--;
    		this.seletedFaultCount--;
    	}
    	
    	this.displayOption = function(){
    		var html = '';
    		var size = 0;
    		for ( var i = 0; i < this.condArray.length; i++) {
    			var conds = this.condArray[i];
    			var cond = conds.split(",");
    			var obj = cond[4];
    			if (obj != null&&(obj==3||obj==1||obj==0)) {
    				html += this.addCondtion310(i,cond[0],cond[1],cond[2],cond[4]);
    			}else if(obj != null&&obj=='hz'){
    				html += this.addCondtionHz(i,cond[0],cond[1],cond[2]);
    			}else if(obj != null&&obj=='dd'){
    				html += this.addCondtionDd(i,cond[0],cond[1],cond[2]);
    			}else if(obj != null&&obj=='lh'){
    				html += this.addCondtionLh(i,cond[0],cond[1],cond[2]);
    			}else if(obj != null&&(obj=='ls3'||obj=='ls1'||obj=='ls0')){
    				html += this.addCondtionLs(i,cond[0],cond[1],cond[2],cond[4]);
    			}else if(obj != null&&(obj=='bls3'||obj=='bls1'||obj=='bls0')){
    				html += this.addCondtionBls(i,cond[0],cond[1],cond[2],cond[4]);
    			}else if(obj != null&&(obj=='sxmz'||obj=='cxmz'||obj=='mxmz')){
    				html += this.addCondtionMz(i,cond[0],cond[1],cond[2],cond[4]);
    			}else if(obj != null&&(obj=='zsh'||obj=='zsj'||obj=='jjfw')){
    				html += this.addCondtionZS(i,cond[0],cond[1],cond[2],cond[4]);
    			}else if(obj != null&&(obj=='dyzs'||obj=='dezs'||obj=='dszs')){
    				html += this.addCondtionYESMZ(i,cond[0],cond[1],cond[2],cond[4]);
    			}else if(obj != null&&(obj=='oddsDesc'||obj=='oddsAsc')){
    				html += this.addCondtionOrder(i,cond[0],cond[1],cond[2],cond[4]);
    			}else if(obj != null&&(obj.indexOf('ccmz')!=-1||obj.indexOf('lmgl')!=-1||obj.indexOf('djgl')!=-1)){
    				var newConds = this.condArray[i];
    				var newCond = newConds.split("|");
    				html += this.addCondtionSet(i,newCond.length-1,cond[1],cond[2],cond[4]);
    			}else if(obj != null&&(obj=='sjjq'||obj=='jjzg')||obj=='glzg'){
    				html += this.addCondtionRange(i,cond[0],cond[1],cond[2],cond[4]);
    			}else if(obj != null&&obj=='xzgg'){
    				html += this.addCondtionAllUp(i,cond[0],cond[1],cond[2],cond[4]);
    			}else if(obj != null&&(obj.indexOf('fzhz')!=-1||obj.indexOf('fzdd')!=-1||obj.indexOf('fzzshz')!=-1||obj.indexOf('fzzsj'!=-1))){
    				var newConds = this.condArray[i];
    				var newCond = newConds.split("|");
    				html += this.addCondtionGroup(i,newCond.length-1,cond[1],cond[2],cond[4]);
    			}
    		}	
    		var faultHtml = this.addFaultTolerant(this.totalFaultCount,this.ltCond,this.gtCond);
    		return html+faultHtml;
    	}
    	
    	this.addFaultTolerant = function(size,ltCond,gtCond){
    		var selectFaultContent1 = '';
    		var selectFaultContent2 = '';
    		if(this.totalFaultCount>0){
    			selectFaultContent1+='<select id="totalFaultCount1_'+this.step+'" size="1" class="rc" onchange="chkFaultValue('+this.step+','+CONDTION_FLAG[7]+',this.options[this.selectedIndex].value);">';
    			selectFaultContent2+='<select id="totalFaultCount2_'+this.step+'" size="1" class="rc" onchange="chkFaultValue('+this.step+','+CONDTION_FLAG[8]+',this.options[this.selectedIndex].value);">';
    			for(var i=0;i<=this.totalFaultCount;i++){
    				if(ltCond==i){
        				selectFaultContent1+='<option selected>'+i+'</option>';
    				}else{
        				selectFaultContent1+='<option>'+i+'</option>';
    				}
    				if(gtCond==i){
        				selectFaultContent2+='<option selected>'+i+'</option>';
    				}else{
        				selectFaultContent2+='<option>'+i+'</option>';
    				}
    				
        		}
    			selectFaultContent1+='</select>';
    			selectFaultContent2+='</select>';
    		}else{
    			selectFaultContent1+='<select id="totalFaultCount1_'+this.step+'" size="1" class="rc" disabled onchange="chkFaultValue('+this.step+','+CONDTION_FLAG[7]+',this.options[this.selectedIndex].value);"><option>0</option></select>';
    			selectFaultContent2+='<select id="totalFaultCount2_'+this.step+'" size="1" class="rc" disabled onchange="chkFaultValue('+this.step+','+CONDTION_FLAG[8]+',this.options[this.selectedIndex].value);"><option>0</option></select>';
    		}
    		
    		var html = '';
    		html+='<tr style="background:#f8f8ab"><td height="28"><a href="javascript:void(0);" onclick="delStep('+this.step+');"><img src="/pages/images/glq/jc1_11.gif" width="16" height="16" border="0" /></a></td>';
    		html+='<td width="32" height="28">第'+(this.step+1)+'步</td><td width="48">条件数：</td><td width="15" height="28" id="condCount">'+(this.condArray.length)+'</td><td width="40">容错：</td><td width="40" height="28">';
    		html+=selectFaultContent1+'</td><td width="7">-</td><td width="48">'+selectFaultContent2;
    		html+='</td><td width="75" height="28" id="syzs_'+(this.step)+'">剩余注数:'+this.units+'</td><td width="49" name="sfcl" id="sfcl_'+(this.step)+'">'+this.state+'</td><td width="" height="28" id="syzs_'+(this.step)+'"></td></tr>';
    		return html;
    	}
    	
    	
    	//添加条件310
    	this.addCondtion310 = function(index,cond1,cond2,isCheck,key){  
    		var select1 = '';
    		var select2 = '';
    		var rc = '';
    		var typeName = '';
    		select1 = this.createSelection(cond1,size);
    		select2 = this.createSelection(cond2,size);
    		if("true"==isCheck){
        		rc+='<input class="cht" name="condtion3'+this.step+'_'+index+'" id="condtion3_'+this.step+'_'+index+'" type="checkbox" checked value="" onclick="clickFault('+this.step+','+index+','+CONDTION_FLAG[0]+');"/></td>';
    		}else{
        		rc+='<input class="cht" name="condtion3'+this.step+'_'+index+'" id="condtion3_'+this.step+'_'+index+'" type="checkbox" value="" onclick="clickFault('+this.step+','+index+','+CONDTION_FLAG[0]+');"/></td>';
    		}  		
    		if(3==key){
    			typeName = '≤3个数≤';
    		}else if(1==key){
    			typeName = '≤1个数≤';
    		}else if(0==key){
    			typeName = '≤0个数≤';
    		}
    		var row = '<tr><td width="41" height="24"><a href="javascript:void(0);" onclick="delCond('+index+','+this.step+');"><img src="/pages/images/glq/jc_11.gif" width="16" height="16" border="0" /></a></td>';
    		row+='<td height="24" colspan="2"><select size="1" id="lt3_'+this.step+'_'+index+'" onchange="chkCond('+this.step+','+index+','+CONDTION_FLAG[1]+',this.options[this.selectedIndex].value);">'+select1+'</select></td>';
    		row+='<td height="24" colspan="3">'+typeName+'</td><td height="24" colspan="3"><select size="1"  id="gt3_'+this.step+'_'+index+'" onchange="chkCond('+this.step+','+index+','+CONDTION_FLAG[2]+',this.options[this.selectedIndex].value);">'+select2+'</select></td>';							
    		row+='<td height="24" colspan="2">'+rc+'</tr>';					
    		return row;
    	}	
    	
    	//添加条件和值
    	this.addCondtionHz = function(index,cond1,cond2,isCheck){  
    		var select1 = '';
    		var select2 = '';
    		var rc = '';
    		select1 = this.createSelection(cond1,3*size);
    		select2 = this.createSelection(cond2,3*size);
    		if("true"==isCheck){
        		rc+='<input class="cht" name="condtion3'+this.step+'_'+index+'" id="condtion3_'+this.step+'_'+index+'" type="checkbox" checked value="" onclick="clickFault('+this.step+','+index+','+CONDTION_FLAG[0]+');"/></td>';
    		}else{
        		rc+='<input class="cht" name="condtion3'+this.step+'_'+index+'" id="condtion3_'+this.step+'_'+index+'" type="checkbox" value="" onclick="clickFault('+this.step+','+index+','+CONDTION_FLAG[0]+');"/></td>';
    		}  		
    		
    		var row = '<tr><td width="41" height="24"><a href="javascript:void(0);" onclick="delCond('+index+','+this.step+');"><img src="/pages/images/glq/jc_11.gif" width="16" height="16" border="0" /></a></td>';
    		row+='<td height="24" colspan="2"><select size="1" id="lt3_'+this.step+'_'+index+'" onchange="chkCond('+this.step+','+index+','+CONDTION_FLAG[1]+',this.options[this.selectedIndex].value);">'+select1+'</select></td>';
    		row+='<td height="24" colspan="3">≤号码和值≤</td><td height="24" colspan="3"><select size="1"  id="gt3_'+this.step+'_'+index+'" onchange="chkCond('+this.step+','+index+','+CONDTION_FLAG[2]+',this.options[this.selectedIndex].value);">'+select2+'</select></td>';							
    		row+='<td height="24" colspan="2">'+rc+'</tr>';					
    		return row;
    	}
    	//添加条件断点
    	this.addCondtionDd = function(index,cond1,cond2,isCheck){  
    		var select1 = '';
    		var select2 = '';
    		var rc = '';
    		select1 = this.createSelection(cond1,size-1);
    		select2 = this.createSelection(cond2,size-1);
    		if("true"==isCheck){
        		rc+='<input class="cht" name="condtion3'+this.step+'_'+index+'" id="condtion3_'+this.step+'_'+index+'" type="checkbox" checked value="" onclick="clickFault('+this.step+','+index+','+CONDTION_FLAG[0]+');"/></td>';
    		}else{
        		rc+='<input class="cht" name="condtion3'+this.step+'_'+index+'" id="condtion3_'+this.step+'_'+index+'" type="checkbox" value="" onclick="clickFault('+this.step+','+index+','+CONDTION_FLAG[0]+');"/></td>';
    		}  		
    		
    		var row = '<tr><td width="41" height="24"><a href="javascript:void(0);" onclick="delCond('+index+','+this.step+');"><img src="/pages/images/glq/jc_11.gif" width="16" height="16" border="0" /></a></td>';
    		row+='<td height="24" colspan="2"><select size="1" id="lt3_'+this.step+'_'+index+'" onchange="chkCond('+this.step+','+index+','+CONDTION_FLAG[1]+',this.options[this.selectedIndex].value);">'+select1+'</select></td>';
    		row+='<td height="24" colspan="3">≤断点个数≤</td><td height="24" colspan="3"><select size="1"  id="gt3_'+this.step+'_'+index+'" onchange="chkCond('+this.step+','+index+','+CONDTION_FLAG[2]+',this.options[this.selectedIndex].value);">'+select2+'</select></td>';							
    		row+='<td height="24" colspan="2">'+rc+'</tr>';					
    		return row;
    	}
    	
    	//添加条件连号
    	this.addCondtionLh = function(index,cond1,cond2,isCheck){  
    		var select1 = '';
    		var select2 = '';
    		var rc = '';
    		select1 = this.createSelection(cond1,size-1);
    		select2 = this.createSelection(cond2,size-1);
    		if("true"==isCheck){
        		rc+='<input class="cht" name="condtion3'+this.step+'_'+index+'" id="condtion3_'+this.step+'_'+index+'" type="checkbox" checked value="" onclick="clickFault('+this.step+','+index+','+CONDTION_FLAG[0]+');"/></td>';
    		}else{
        		rc+='<input class="cht" name="condtion3'+this.step+'_'+index+'" id="condtion3_'+this.step+'_'+index+'" type="checkbox" value="" onclick="clickFault('+this.step+','+index+','+CONDTION_FLAG[0]+');"/></td>';
    		}  		
    		
    		var row = '<tr><td width="41" height="24"><a href="javascript:void(0);" onclick="delCond('+index+','+this.step+');"><img src="/pages/images/glq/jc_11.gif" width="16" height="16" border="0" /></a></td>';
    		row+='<td height="24" colspan="2"><select size="1" id="lt3_'+this.step+'_'+index+'" onchange="chkCond('+this.step+','+index+','+CONDTION_FLAG[1]+',this.options[this.selectedIndex].value);">'+select1+'</select></td>';
    		row+='<td height="24" colspan="3">≤连号个数≤</td><td height="24" colspan="3"><select size="1"  id="gt3_'+this.step+'_'+index+'" onchange="chkCond('+this.step+','+index+','+CONDTION_FLAG[2]+',this.options[this.selectedIndex].value);">'+select2+'</select></td>';							
    		row+='<td height="24" colspan="2">'+rc+'</tr>';					
    		return row;
    	}
    	//添加条件主场连胜 主场连平 主场连负
    	this.addCondtionLs = function(index,cond1,cond2,isCheck,key){  
    		var select1 = '';
    		var select2 = '';
    		var rc = '';
    		var typeName = '';
    		select1 = this.createSelection(cond1,size);
    		select2 = this.createSelection(cond2,size);
    		if("true"==isCheck){
        		rc+='<input class="cht" name="condtion3'+this.step+'_'+index+'" id="condtion3_'+this.step+'_'+index+'" type="checkbox" checked value="" onclick="clickFault('+this.step+','+index+','+CONDTION_FLAG[0]+');"/></td>';
    		}else{
        		rc+='<input class="cht" name="condtion3'+this.step+'_'+index+'" id="condtion3_'+this.step+'_'+index+'" type="checkbox" value="" onclick="clickFault('+this.step+','+index+','+CONDTION_FLAG[0]+');"/></td>';
    		}  		
    		if('ls3'==key){
    			typeName = '≤主场连胜≤';
    		}else if('ls1'==key){
    			typeName = '≤主场连平≤';
    		}else if('ls0'==key){
    			typeName = '≤主场连负≤';
    		}
    		var row = '<tr><td width="41" height="24"><a href="javascript:void(0);" onclick="delCond('+index+','+this.step+');"><img src="/pages/images/glq/jc_11.gif" width="16" height="16" border="0" /></a></td>';
    		row+='<td height="24" colspan="2"><select size="1" id="lt3_'+this.step+'_'+index+'" onchange="chkCond('+this.step+','+index+','+CONDTION_FLAG[1]+',this.options[this.selectedIndex].value);">'+select1+'</select></td>';
    		row+='<td height="24" colspan="3">'+typeName+'</td><td height="24" colspan="3"><select size="1"  id="gt3_'+this.step+'_'+index+'" onchange="chkCond('+this.step+','+index+','+CONDTION_FLAG[2]+',this.options[this.selectedIndex].value);">'+select2+'</select></td>';							
    		row+='<td height="24" colspan="2">'+rc+'</tr>';					
    		return row;
    	}
    	
    	//添加条件不胜连续个数 不平连胜个数  不败连胜个数
    	this.addCondtionBls = function(index,cond1,cond2,isCheck,key){  
    		var select1 = '';
    		var select2 = '';
    		var rc = '';
    		var typeName = '';
    		select1 = this.createSelection(cond1,size);
    		select2 = this.createSelection(cond2,size);
    		if("true"==isCheck){
        		rc+='<input class="cht" name="condtion3'+this.step+'_'+index+'" id="condtion3_'+this.step+'_'+index+'" type="checkbox" checked value="" onclick="clickFault('+this.step+','+index+','+CONDTION_FLAG[0]+');"/></td>';
    		}else{
        		rc+='<input class="cht" name="condtion3'+this.step+'_'+index+'" id="condtion3_'+this.step+'_'+index+'" type="checkbox" value="" onclick="clickFault('+this.step+','+index+','+CONDTION_FLAG[0]+');"/></td>';
    		}  		
    		if('bls3'==key){
    			typeName = '≤不胜连续个数≤';
    		}else if('bls1'==key){
    			typeName = '≤不平连胜个数≤';
    		}else if('bls0'==key){
    			typeName = '≤不败连胜个数≤';
    		}
    		var row = '<tr><td width="41" height="24"><a href="javascript:void(0);" onclick="delCond('+index+','+this.step+');"><img src="/pages/images/glq/jc_11.gif" width="16" height="16" border="0" /></a></td>';
    		row+='<td height="24" colspan="2"><select size="1" id="lt3_'+this.step+'_'+index+'" onchange="chkCond('+this.step+','+index+','+CONDTION_FLAG[1]+',this.options[this.selectedIndex].value);">'+select1+'</select></td>';
    		row+='<td height="24" colspan="3">'+typeName+'</td><td height="24" colspan="3"><select size="1"  id="gt3_'+this.step+'_'+index+'" onchange="chkCond('+this.step+','+index+','+CONDTION_FLAG[2]+',this.options[this.selectedIndex].value);">'+select2+'</select></td>';							
    		row+='<td height="24" colspan="2">'+rc+'</tr>';					
    		return row;
    	}
    	
    	//添加条件首选命中 次选命中  末选命中
    	this.addCondtionMz = function(index,cond1,cond2,isCheck,key){  
    		var select1 = '';
    		var select2 = '';
    		var rc = '';
    		var typeName = '';
    		select1 = this.createSelection(cond1,size);
    		select2 = this.createSelection(cond2,size);
    		if("true"==isCheck){
        		rc+='<input class="cht" name="condtion3'+this.step+'_'+index+'" id="condtion3_'+this.step+'_'+index+'" type="checkbox" checked value="" onclick="clickFault('+this.step+','+index+','+CONDTION_FLAG[0]+');"/></td>';
    		}else{
        		rc+='<input class="cht" name="condtion3'+this.step+'_'+index+'" id="condtion3_'+this.step+'_'+index+'" type="checkbox" value="" onclick="clickFault('+this.step+','+index+','+CONDTION_FLAG[0]+');"/></td>';
    		}  		
    		if('sxmz'==key){
    			typeName = '≤首选命中≤';
    		}else if('cxmz'==key){
    			typeName = '≤次选命中≤';
    		}else if('mxmz'==key){
    			typeName = '≤末选命中≤';
    		}
    		var row = '<tr><td width="41" height="24"><a href="javascript:void(0);" onclick="delCond('+index+','+this.step+');"><img src="/pages/images/glq/jc_11.gif" width="16" height="16" border="0" /></a></td>';
    		row+='<td height="24" colspan="2"><select size="1" id="lt3_'+this.step+'_'+index+'" onchange="chkCond('+this.step+','+index+','+CONDTION_FLAG[1]+',this.options[this.selectedIndex].value);">'+select1+'</select></td>';
    		row+='<td height="24" colspan="3">'+typeName+'</td><td height="24" colspan="3"><select size="1"  id="gt3_'+this.step+'_'+index+'" onchange="chkCond('+this.step+','+index+','+CONDTION_FLAG[2]+',this.options[this.selectedIndex].value);">'+select2+'</select></td>';							
    		row+='<td height="24" colspan="2">'+rc+'</tr>';					
    		return row;
    	}
    	
    	//添加条件指数和
    	this.addCondtionZS = function(index,cond1,cond2,isCheck,key){  
    		var rc = '';
    		var typeName = '';
    		if("true"==isCheck){
        		rc+='<input class="cht" name="condtion3'+this.step+'_'+index+'" id="condtion3_'+this.step+'_'+index+'" type="checkbox" checked value="" onclick="clickFault('+this.step+','+index+','+CONDTION_FLAG[0]+');"/></td>';
    		}else{
        		rc+='<input class="cht" name="condtion3'+this.step+'_'+index+'" id="condtion3_'+this.step+'_'+index+'" type="checkbox" value="" onclick="clickFault('+this.step+','+index+','+CONDTION_FLAG[0]+');"/></td>';
    		}  		
    		if('zsh'==key){
    			typeName = '<td width="81"><select name="select" size="1"><option>SP值</option></select></td><td width="81">指数和≤</td>';
    		}else if('zsj'==key){
    			typeName = '<td width="81"><select name="select" size="1"><option>SP值</option></select></td><td width="81">指数积≤</td>';
    		}else if('jjfw'==key){
    			typeName = '<td width="81">奖金范围</td><td width="81">≤</td>';
    		}
    		var row = '<tr><td width="66" height="24"><a href="javascript:void(0);" onclick="delCond('+index+','+this.step+');"><img src="/pages/images/glq/jc_11.gif" width="16" height="16" border="0" /></a></td>';
    		row+='<td height="24" colspan="2"><input size="5" class="ipt" type="text" value="'+cond1+'" name="lt3_'+this.step+'_'+index+'" id="lt3_'+this.step+'_'+index+'" onchange="chkCond('+this.step+','+index+','+CONDTION_FLAG[1]+',this.value);"/></td>';
    		row+='<td width="27" height="24">≤</td>';							
    		row+=typeName+'<td width="58" height="24"><input size="5" type="text" value="'+cond2+'" class="ipt" id="gt3_'+this.step+'_'+index+'" name="gt3_'+this.step+'_'+index+'" onchange="chkCond('+this.step+','+index+','+CONDTION_FLAG[2]+',this.value);"/></td><td height="24" colspan="2">'+rc+'</tr>';					
    		return row;
    	}
    	
    	//添加条件第一二三命中指数
    	this.addCondtionYESMZ = function(index,cond1,cond2,isCheck,key){  
    		var select1 = '';
    		var select2 = '';
    		var rc = '';
    		var typeName = '';
    		select1 = this.createSelection(cond1,size);
    		select2 = this.createSelection(cond2,size);
    		if("true"==isCheck){
        		rc+='<input class="cht" name="condtion3'+this.step+'_'+index+'" id="condtion3_'+this.step+'_'+index+'" type="checkbox" checked value="" onclick="clickFault('+this.step+','+index+','+CONDTION_FLAG[0]+');"/></td>';
    		}else{
        		rc+='<input class="cht" name="condtion3'+this.step+'_'+index+'" id="condtion3_'+this.step+'_'+index+'" type="checkbox" value="" onclick="clickFault('+this.step+','+index+','+CONDTION_FLAG[0]+');"/></td>';
    		}  		
    		if('dyzs'==key){
    			typeName = '≤第一指数命中≤';
    		}else if('dezs'==key){
    			typeName = '≤第二指数命中≤';
    		}else if('dszs'==key){
    			typeName = '≤第三指数命中≤';
    		}
    		var row = '<tr><td width="41" height="24"><a href="javascript:void(0);" onclick="delCond('+index+','+this.step+');"><img src="/pages/images/glq/jc_11.gif" width="16" height="16" border="0" /></a></td>';
    		row+='<td height="24" colspan="2"><select size="1" id="lt3_'+this.step+'_'+index+'" onchange="chkCond('+this.step+','+index+','+CONDTION_FLAG[1]+',this.options[this.selectedIndex].value);">'+select1+'</select></td>';
    		row+='<td height="24" colspan="2">'+typeName+'</td><td height="24" colspan="3"><select size="1"  id="gt3_'+this.step+'_'+index+'" onchange="chkCond('+this.step+','+index+','+CONDTION_FLAG[2]+',this.options[this.selectedIndex].value);">'+select2+'</select></td>';							
    		row+='<td height="24" colspan="2">'+rc+'</tr>';					
    		return row;
    	}
    	
    	//添加条件赔率升序 降序
    	this.addCondtionOrder = function(index,cond1,cond2,isCheck,key){  
    		var rc = '';
    		var typeName = '';
    		if("true"==isCheck){
        		rc+='<input class="cht" name="condtion3'+this.step+'_'+index+'" id="condtion3_'+this.step+'_'+index+'" type="checkbox" checked value="" onclick="clickFault('+this.step+','+index+','+CONDTION_FLAG[0]+');"/></td>';
    		}else{
        		rc+='<input class="cht" name="condtion3'+this.step+'_'+index+'" id="condtion3_'+this.step+'_'+index+'" type="checkbox" value="" onclick="clickFault('+this.step+','+index+','+CONDTION_FLAG[0]+');"/></td>';
    		}  		
    		if('oddsAsc'==key){
    			typeName = '从低到高排序';
    		}else if('oddsDesc'==key){
    			typeName = '从高到低排序';
    		}
    		var row = '<tr><td width="41" height="24"><a href="javascript:void(0);" onclick="delCond('+index+','+this.step+');"><img src="/pages/images/glq/jc_11.gif" width="16" height="16" border="0" /></a></td>';
    		row+='<td height="24" colspan="2"><select size="1" id="lt3_'+this.step+'_'+index+'" onchange="chkCond('+this.step+','+index+','+CONDTION_FLAG[1]+',this.options[this.selectedIndex].value);"><option>SP值</option></select></td>';
    		row+='<td height="24" colspan="2">'+typeName+'</td><td width="60"><input size="5" class="ipt" type="text" value="'+cond1+'" name="lt3_'+this.step+'_'+index+'" id="lt3_'+this.step+'_'+index+'" onchange="chkCond('+this.step+','+index+','+CONDTION_FLAG[1]+',this.value);"/></td>';	
    		row+='<td width="46">注--第</td><td width="46" height="24"><input size="5" class="ipt" type="text" value="'+cond2+'" name="gt3_'+this.step+'_'+index+'" id="lt3_'+this.step+'_'+index+'" onchange="chkCond('+this.step+','+index+','+CONDTION_FLAG[2]+',this.value);"/></td><td width="15">注</td>';
    		row+='</tr>';		
    		
    		return row;
    	}
    	
    	//添加条件集合过滤
    	this.addCondtionSet = function(index,cond1,cond2,isCheck,key){  
   		  		var rc = '';
		  		var typeName = '';
		  		var value = '';
		  		if("true"==isCheck){
		      		rc+='<input class="cht" name="condtion3'+this.step+'_'+index+'" id="condtion3_'+this.step+'_'+index+'" type="checkbox" checked value="" onclick="clickJHFault('+this.step+','+index+','+CONDTION_FLAG[0]+');"/></td>';
		  		}else{
		      		rc+='<input class="cht" name="condtion3'+this.step+'_'+index+'" id="condtion3_'+this.step+'_'+index+'" type="checkbox" value="" onclick="clickJHFault('+this.step+','+index+','+CONDTION_FLAG[0]+');"/></td>';
		  		}  		
		  		if(key.indexOf('ccmz')!=-1){
		  			typeName = '命中场次';
		  		}else if(key.indexOf('lmgl')!=-1){
		  			typeName = '冷门过滤';
		  		}else if(key.indexOf('djgl')!=-1){
		  			typeName = '叠加过滤';
		  		}
		  		if(cond1<=0){
		  			value+='请点击设置按钮设定条件';
		  		}else{
		  			value+='共选择了'+cond1+'个条件';
		  		}
		  		var row = '<tr><td width="41" height="24"><a href="javascript:void(0);" onclick="delCond('+index+','+this.step+');"><img src="/pages/images/glq/jc_11.gif" width="16" height="16" border="0" /></a></td><td height="24" colspan="2">'+typeName+'</td>';
		  		row+='<td height="24" colspan="2"><input type="text" name="textfield" id="lt3_'+this.step+'_'+index+'" value="'+value+'" onchange="chkCond('+this.step+','+index+','+CONDTION_FLAG[1]+',this.value);"/></td>';
		  		row+='<td height="24" colspan="3" class="rt_sz"><a href="javascript:void(0);"><img src="/pages/images/glq/sc.gif" width="60" height="20" border="0" onclick="openRestDialog('+this.step+','+index+');"/></a></td>';							
		  		row+='<td height="24" colspan="2">'+rc+'</tr>';					
		  		return row;
    	}
    	
    	//添加条件范围截取
    	this.addCondtionRange = function(index,cond1,cond2,isCheck,key){  
    		var typeName = '';
    		if('sjjq'==key){
    			typeName = '随机截取';
    		}else if('jjzg'==key){
    			typeName = '奖金最高';
    		}else if('glzg'==key){
    			typeName = '概率最高';
    		}
    		
    		var row = '<tr><td width="41" height="24"><a href="javascript:void(0);" onclick="delCond('+index+','+this.step+');"><img src="/pages/images/glq/jc_11.gif" width="16" height="16" border="0" /></a></td>';
    		row+='<td height="24" colspan="2">'+typeName+'</td><td height="24" colspan="2"><input size="10" class="ipt" type="text" value="'+cond1+'" name="lt3_'+this.step+'_'+index+'" id="lt3_'+this.step+'_'+index+'" onchange="chkCond('+this.step+','+index+','+CONDTION_FLAG[1]+',this.value);"/></td>';
    		row+='<td width="59">注</td><td width="50">&nbsp;</td><td width="51" height="24">&nbsp;</td></tr>';	
    		return row;
    	}
    	//添加条件过关方式
    	this.addCondtionAllUp = function(index,cond1,cond2,isCheck,key){  
			var select1 = '';
	  		for(var t=0;t<=size-1;t++){
	  			if(t==0){
    				select1+="<option selected value="+t+">单关</option>";
				}else{
					if(t==cond1){
		  				select1+="<option selected value="+t+">"+(t+1)+"串1</option>";
	    			}else{
	    				select1+="<option value="+t+">"+(t+1)+"串1</option>";
    				}
				}				
	  		}
	  		var row = '<tr><td width="41" height="24"><a href="javascript:void(0);" onclick="delCond('+index+','+this.step+');"><img src="/pages/images/glq/jc_11.gif" width="16" height="16" border="0" /></a></td>';
	  		row+='<td height="24" colspan="2">过关方式</td><td height="24" colspan="3"><select size="1" id="lt3_'+this.step+'_'+index+'" onchange="chkPassTypeCond('+this.step+','+index+','+CONDTION_FLAG[1]+',this.options[this.selectedIndex].value);">'+select1+'</select></td></tr>';							
	  		return row;	
    	}
    	
    	//添加条件分组过滤
    	this.addCondtionGroup = function(index,cond1,cond2,isCheck,key){  
		  		var typeName = '';
		  		var value = '';
		  		var code = 0;		
		  		if(key.indexOf('fzhz')!=-1){
		  			typeName = '分组和值';
		  			code = 0;
		  		}else if(key.indexOf('fzdd')!=-1){
		  			typeName = '分组断点';
		  			code = 1;
		  		}else if(key.indexOf('fzzshz')!=-1){
		  			typeName = '分组指数和值';
		  			code = 2;
		  		}else if(key.indexOf('fzzsj')!=-1){
		  			typeName = '分组指数积值';
		  			code = 3;
		  		}
		  		if(cond1<=0){
		  			value+='请点击设置按钮设定条件';
		  		}else{
		  			value+='共选择了'+cond1+'个条件';
		  		}
		  		var row = '<tr><td width="41" height="24"><a href="javascript:void(0);" onclick="delCond('+index+','+this.step+');"><img src="/pages/images/glq/jc_11.gif" width="16" height="16" border="0" /></a></td><td height="24" width="115" colspan="2">'+typeName+'</td>';
		  		row+='<td height="24" colspan="5"><input type="text" name="textfield" id="lt3_'+this.step+'_'+index+'" value="'+value+'" onchange="chkCond('+this.step+','+index+','+CONDTION_FLAG[1]+',this.value);"/></td>';
		  		row+='<td height="24" colspan="8" class="rt_sz"><a href="javascript:void(0);"><img src="/pages/images/glq/sc.gif" width="60" height="20" border="0" onclick="openGroupDialog('+this.step+','+index+','+code+');"/></a></td></tr>';					
		  		return row;
    	}
    	
    	this.createSelection = function(condition,count){
    		var selectStr = '';
    		for(var i=0;i<=count;i++){
    			if(i==condition){
    				selectStr+="<option value='"+i+"' selected>"+i+"</option>";
    			}else{
    				selectStr+="<option value='"+i+"'>"+i+"</option>";
    			}
    		}
    		return selectStr;
    	}
	}
	
	
	model = new FilterModel();
	
	function hashObjArray_push(index, obj) {
		hashObjArray[index] = obj;
	}
	
	function hashObjArray_remove(index) {
		delete hashObjArray[index];
		if(index < hashObjArray.length-1){
			for ( var i = index; i < hashObjArray.length; i++) {	
				hashObjArray[i] = hashObjArray[i+1];
			}
		}		
		hashObjArray.length--;
	}
	
	function displayAllConditions(){
		var html= '';
		if(hashObjArray.length==0){
			$("#condition").html('');	
			$("#filterIntroduce").css("display","");
		}else{
			for ( var i = 0; i < hashObjArray.length; i++) {
				var data =hashObjArray[i];
				if(null!=data){
					html+=data.displayOption();			
				}
			}
			$("#condition").html(html);	
		}
	}	
	
	
	//添加过滤条件
	window.clickCond = function(value){
		$("#filterIntroduce").css("display","none");
		if(model.flag=='y'){
			html+=model.displayOption();
			model = new FilterModel();
			model.step=step;
		}

		model.condArray_push(model.seletedFaultCount,"0,0,false,"+step+","+value);
		hashObjArray_push(step,model);

		displayAllConditions();
	}
	
	window.clickAllCond = function(key){
		$("#filterIntroduce").css("display","none");
		if(model.flag=='y'){
			html+=model.displayOption();
			model = new FilterModel();
			model.step=step;
		}
		if(key=='jhgl'){
			model.condArray_push(model.seletedFaultCount,"0,0,false,"+step+",3");
			model.condArray_push(model.seletedFaultCount,"0,0,false,"+step+",1");
			model.condArray_push(model.seletedFaultCount,"0,0,false,"+step+",0");
			model.condArray_push(model.seletedFaultCount,"0,0,false,"+step+",hz");
			model.condArray_push(model.seletedFaultCount,"0,0,false,"+step+",dd");
			model.condArray_push(model.seletedFaultCount,"0,0,false,"+step+",lh");
			model.condArray_push(model.seletedFaultCount,"0,0,false,"+step+",ls3");
			model.condArray_push(model.seletedFaultCount,"0,0,false,"+step+",ls1");
			model.condArray_push(model.seletedFaultCount,"0,0,false,"+step+",ls0");
			model.condArray_push(model.seletedFaultCount,"0,0,false,"+step+",bls0");
			model.condArray_push(model.seletedFaultCount,"0,0,false,"+step+",bls1");
			model.condArray_push(model.seletedFaultCount,"0,0,false,"+step+",bls3");
		}else if(key=='scmgl'){
			model.condArray_push(model.seletedFaultCount,"0,0,false,"+step+",sxmz");
			model.condArray_push(model.seletedFaultCount,"0,0,false,"+step+",cxmz");
			model.condArray_push(model.seletedFaultCount,"0,0,false,"+step+",mxmz");
		}else if(key=='zsgl'){
			model.condArray_push(model.seletedFaultCount,"0,0,false,"+step+",zsh");
			model.condArray_push(model.seletedFaultCount,"0,0,false,"+step+",zsj");
			model.condArray_push(model.seletedFaultCount,"0,0,false,"+step+",jjfw");
			model.condArray_push(model.seletedFaultCount,"0,0,false,"+step+",dyzs");
			model.condArray_push(model.seletedFaultCount,"0,0,false,"+step+",dezs");
			model.condArray_push(model.seletedFaultCount,"0,0,false,"+step+",dszs");
		}
		
		hashObjArray_push(step,model);
		displayAllConditions();
	}
	//删除所有条件
	window.clearAll = function(){
		//删除对象中的所有数组
		var obj_size = hashObjArray.length;
		for(var index=0;index<obj_size;index++){
			var cond_size= hashObjArray[0].condArray.length;
			for (var i = 0; i < cond_size; i++) {
				hashObjArray[0].condArray_remove(0);//删除条件			
			}
			hashObjArray_remove(0);
		}
		displayAllConditions();
		step=0;
		model = new FilterModel();
	}
	//删除条件
	window.delCond = function(index,curStep){
		if(hashObjArray[curStep].condArray.length<=1){
			hashObjArray[curStep].condArray_remove(index);//删除条件		
			
			//当期步骤是否选中，是则减1
			if($("#condtion3_"+curStep+"_"+index).attr("checked")){
				hashObjArray[curStep].totalFaultCount--;
			}
			
			dealFaultSelect(curStep);
					
			hashObjArray_remove(curStep);
			if(curStep>=0){
				for (var i = curStep; i < hashObjArray.length; i++) {
					if(hashObjArray[i].step>0){
						hashObjArray[i].step--;
						//重新组合参数 "0,0,false,"+step+","+value
						var condArray = hashObjArray[i].condArray;
						var curStep = hashObjArray[i].step;
						hashObjArray[i].condArray = reArrangeParams(condArray,curStep,i);
					}					
				}
			}	
			if(hashObjArray.length==0){
				step=0;
				model = new FilterModel();
			}else{
				step--;
			}
		}else{
			hashObjArray[curStep].condArray_remove(index);//删除条件		
			
			//当期步骤是否选中，是则减1
			if($("#condtion3_"+curStep+"_"+index).attr("checked")){
				hashObjArray[curStep].totalFaultCount--;
			}
			
			dealFaultSelect(curStep);
			
			hashObjArray_push(curStep,hashObjArray[curStep]);
		}

		displayAllConditions();
	}
	
	//点击容错
	window.clickFault = function(curStep,index,key){
		var newParams=analyseParams(hashObjArray[curStep].condArray[index],curStep,key,$("#condtion3_"+curStep+"_"+index).attr("checked"));
		hashObjArray[curStep].condArray[index]=newParams;
		
		if($("#condtion3_"+curStep+"_"+index).attr("checked")){
			hashObjArray[curStep].totalFaultCount++;
		}else{
			hashObjArray[curStep].totalFaultCount--;
		}
		dealFaultSelect(curStep);
		chgState(curStep);		
	}
	//集合容错
	window.clickJHFault = function(curStep,index,key){
		var newParams=analyseJHParams(hashObjArray[curStep].condArray[index],curStep,key,$("#condtion3_"+curStep+"_"+index).attr("checked"));
		hashObjArray[curStep].condArray[index]=newParams;
		
		if($("#condtion3_"+curStep+"_"+index).attr("checked")){
			hashObjArray[curStep].totalFaultCount++;
		}else{
			hashObjArray[curStep].totalFaultCount--;
		}
		dealFaultSelect(curStep);
		chgState(curStep);
	}
	
	//改变下了列表的值
	window.chkCond = function(curStep,index,key,value){	
		var newParams=analyseParams(hashObjArray[curStep].condArray[index],curStep,key,value);
		hashObjArray[curStep].condArray[index] = newParams;
		chgState(curStep);
	}
	//改变下了列表的值 同时改变过关方式
	window.chkPassTypeCond = function(curStep,index,key,value){	
		var newParams=analyseParams(hashObjArray[curStep].condArray[index],curStep,key,value);
		hashObjArray[curStep].condArray[index] = newParams;
		chgState(curStep);
		//改变过关方式
		var typeArr = PassTypeUtil.getSinglePassType($("#size").val());
		if(value>0){
			$("#createForm_passTypes").val(typeArr[value-1].key);
		}
	}
	
	//改变容错下拉列表值
	window.chkFaultValue = function(curStep,key,value){
		if(CONDTION_FLAG[7]==key){
			hashObjArray[curStep].ltCond = value;
		}else if(CONDTION_FLAG[8]==key){
			hashObjArray[curStep].gtCond = value;
		}
	}
		
	//解析添加每行的参数
	function analyseParams(params,curStep,key,value){
		var newParams ='';
		if(''==params||null==params){
			alert("选择条件有误");
			return false;
		}
		var param = params.split(",");

		if(CONDTION_FLAG[0]==key){
			newParams+=param[0]+","+param[1]+","+value+","+param[3]+","+param[4];
		}else if(CONDTION_FLAG[1]==key){
			newParams+=value+","+param[1]+","+param[2]+","+param[3]+","+param[4];
		}else if(CONDTION_FLAG[2]==key){
			newParams+=param[0]+","+value+","+param[2]+","+param[3]+","+param[4];
		} 
		return newParams;
	}
	
	//解析添加每行的参数
	function analyseJHParams(params,curStep,key,value){
		var newParams ='';
		if(''==params||null==params){
			alert("选择条件有误");
			return false;
		}
		var prefixParam = params.split("|");
		var param = prefixParam[0].split(",");
		if(CONDTION_FLAG[0]==key){
			newParams+=param[0]+","+param[1]+","+value+","+param[3]+","+param[4];
		}else if(CONDTION_FLAG[1]==key){
			newParams+=value+","+param[1]+","+param[2]+","+param[3]+","+param[4];
		}else if(CONDTION_FLAG[2]==key){
			newParams+=param[0]+","+value+","+param[2]+","+param[3]+","+param[4];
		} 
		for(var t=1;t<prefixParam.length;t++){
			newParams+="|"+prefixParam[t];
		}
		return newParams;
	}
	//处理容错下拉框
	function dealFaultSelect(curStep){
		
		if(hashObjArray[curStep].totalFaultCount>0){
			$("#totalFaultCount1_"+curStep).attr("disabled",false);
			$("#totalFaultCount2_"+curStep).attr("disabled",false);
			
			var selectContent = '';
			for(var i=0;i<=hashObjArray[curStep].totalFaultCount;i++){
				selectContent+='<option>'+i+'</option>';
    		}
			$("#totalFaultCount1_"+curStep).html(selectContent);
			$("#totalFaultCount2_"+curStep).html(selectContent);
		}else{
			$("#totalFaultCount1_"+curStep).attr("disabled",true);
			$("#totalFaultCount2_"+curStep).attr("disabled",true);
			
			var selectContent = '<option>0</option>';
			$("#totalFaultCount1_"+curStep).html(selectContent);
			$("#totalFaultCount2_"+curStep).html(selectContent);
		}	
	}
	
	function chgState(curStep){
		if(curStep<step&&step!=0){//如果改变非当期步骤,则改变所有步骤状态
			for(var i=0;i<hashObjArray.length;i++){
				hashObjArray[i].units=0;
				hashObjArray[i].state='未处理';
				$("#syzs_"+hashObjArray[i].step).html('剩余注数:0');
				$("#sfcl_"+hashObjArray[i].step).html('未处理');
			}
		}else{
			hashObjArray[step].units=0;
			hashObjArray[step].state='未处理';
			$("#syzs_"+step).html('剩余注数:0');
			$("#sfcl_"+step).html('未处理');
		}		
	}
	
	function reArrangeParams(condArray,curStep,index){
		//重新组合参数 
		for(var j=0;j<condArray.length;j++){
			var params = condArray[j].split(",");
			var newParams=params[0]+","+params[1]+","+params[2]+","+curStep+","+params[4];
			condArray[j]=newParams;
		}
		return condArray;
	}
	
	//删除一整步
	window.delStep = function(index){
		//删除对象中的所有数组
		for (var i = 0; i < hashObjArray[index].condArray.length; i++) {
			hashObjArray[index].condArray_remove(i);//删除条件			
		}	
		hashObjArray_remove(index);

		for (var i = index; i < hashObjArray.length; i++) {
			if(hashObjArray[i].step>0){
				hashObjArray[i].step--;
				//重新组合参数 "0,0,false,"+step+","+value
				var condArray = hashObjArray[i].condArray;
				var curStep = hashObjArray[i].step;
				hashObjArray[i].condArray = reArrangeParams(condArray,curStep,i);
			}
		}	
		
		if(hashObjArray.length==0){
			step=0;
			model = new FilterModel();
		}else{
			step--;
		}
		
		displayAllConditions();
	}
	
	window.dealCond = function(){//常规:"0,0,false,"+step+","+value 小于项，大于项，是否容错，当前步骤，key值  容错:大小值,当前步骤,容错个数
		var condition='';
		var spf='';
		var size = 0;
		var rcCondtion = '';
		var sps = '';
		var jhCondtion = '';//集合条件
		
		for ( var i = 0; i < hashObjArray.length; i++) {
			var m = hashObjArray[i];	
			for(var j=0;j < m.condArray.length; j++  ){
				var cond = m.condArray[j];
				condition+='&&filterForm.condition['+(size++)+']='+cond;						
			}		
		}
		if(''==condition){
			alert("请选择过滤条件");
			return false;
		}
		for ( var i = 0; i < hashObjArray.length; i++) {
			rcCondtion+='&&filterForm.rcCondtion['+i+']='+hashObjArray[i].ltCond+","+hashObjArray[i].gtCond+","+hashObjArray[i].step+","+hashObjArray[i].totalFaultCount;
		}
		
		$("input[name='matchCk'][type='checkbox']").each(function(i){
			var temp='';
			var spsTemp = '';
			var win = $("#spf_win_"+$(this).val()).html();
			var draw = $("#spf_draw_"+$(this).val()).html();
			var lose = $("#spf_lose_"+$(this).val()).html();
			
			var winSps = $("#sps_win_"+$(this).val()).html();
			var drawSps = $("#sps_draw_"+$(this).val()).html();
			var loseSps = $("#sps_lose_"+$(this).val()).html();
			if(winSps!=null&&winSps!=''){
				spsTemp+=winSps+',';
			}
			if(drawSps!=null&&drawSps!=''){
				spsTemp+=drawSps+',';
			}
			if(loseSps!=null&&loseSps!=''){
				spsTemp+=loseSps+',';
			}
			if(win!=null&&win!=''){
				temp+=win+",";
			}
			if(draw!=null&&draw!=''){
				temp+=draw+",";
			}
			if(lose!=null&&lose!=''){
				temp+=lose+",";
			}
			spf+='&&filterForm.spfs['+i+']='+temp.substring(0,temp.length-1);
			sps+='&&filterForm.sp['+i+']='+spsTemp.substring(0,spsTemp.length-1);
		});
		var units = $("#units").val();
		var url = "/dczc/filter!filter.action?filterForm.units="+units+condition+"&&filterForm.current="+chgFlag+spf+rcCondtion+sps;
//ralert(url);
		$.ajax({
			type : 'post',
			cache : false,
			dataType : 'json',
			url : url,
			success:function(jsonObj){	
				if(jsonObj.success){
					var html='';
					var datas=jsonObj.datas;
					for(var i=0;i<datas.length;i++){
						if(i%2==0){
							html+='<tr><td width="66" height="22">'+(i+1)+'</td><td width="380">'+datas[i]+'</td></tr>';						  						
						}else{
							html+='<tr  style="background:#fafaf1"><td height="22">'+(i+1)+'</td><td height="22">'+datas[i]+'</td></tr>';
						}
					}	
					$("#result").html(html);
					$("#syzs_"+model.step).html("剩余注数:"+datas.length);
					$("#sfcl_"+model.step).html("已处理");
					var counts = jsonObj.count;
					for(var i=0;i<step;i++){
						hashObjArray[i].units=counts[i];
						hashObjArray[i].state='已处理';
						$("#syzs_"+i).html('剩余注数:'+counts[i]);
						$("#sfcl_"+i).html('已处理');
					}
					//计算金额跟注数
					$("#finallyCount").html("注数：<b>"+jsonObj.finallyCount+"</b>");
					$("#totalCost").html("总金额："+jsonObj.totalCost);
					$("#createForm_units").val(jsonObj.finallyCount);
					$("#createForm_schemeCost").val(jsonObj.totalCost);
					$("#createForm_content").val(jsonObj.datas);

					model.units=datas.length;
					model.state='已处理';
					if(model.flag=='n'){
						step++;
						model.flag='y';
					}

				}				
				alert(jsonObj.msg);		
			},
			beforeSend : function(XMLHttpRequest) {
				document.getElementById('dealCond').style.display = 'none';
				document.getElementById('span_createForm_waiting').style.display = '';
			},
			complete : function(XMLHttpRequest, textStatus) {
				document.getElementById('dealCond').style.display = '';
				document.getElementById('span_createForm_waiting').style.display = 'none';
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(jsonObj.msg);
			}
		});	
	}
	
	window.chgMenu = function(menu){
		if('jhgl'==menu){
			$("#jhgl").css("display","");
			$("#scmgl").css("display","none");
			$("#zsgl").css("display","none");
			$("#pxjq").css("display","none");
			$("#setgl").css("display","none");
			$("#fwgl").css("display","none");
			$("#ggfs").css("display","none");
			$("#fzgl").css("display","none");
		}else if('scmgl'==menu){
			$("#scmgl").css("display","");
			$("#jhgl").css("display","none");
			$("#zsgl").css("display","none");
			$("#pxjq").css("display","none");
			$("#setgl").css("display","none");
			$("#fwgl").css("display","none");
			$("#ggfs").css("display","none");
			$("#fzgl").css("display","none");
		}else if('zsgl'==menu){
			$("#zsgl").css("display","");
			$("#scmgl").css("display","none");
			$("#jhgl").css("display","none");
			$("#pxjq").css("display","none");
			$("#setgl").css("display","none");
			$("#fwgl").css("display","none");
			$("#ggfs").css("display","none");
			$("#fzgl").css("display","none");
		}else if('pxjq'==menu){
			$("#pxjq").css("display","");
			$("#zsgl").css("display","none");
			$("#scmgl").css("display","none");
			$("#jhgl").css("display","none");
			$("#setgl").css("display","none");
			$("#fwgl").css("display","none");
			$("#ggfs").css("display","none");
			$("#fzgl").css("display","none");
		}else if('setgl'==menu){
			$("#pxjq").css("display","none");
			$("#zsgl").css("display","none");
			$("#scmgl").css("display","none");
			$("#jhgl").css("display","none");
			$("#setgl").css("display","");
			$("#fwgl").css("display","none");
			$("#ggfs").css("display","none");
			$("#fzgl").css("display","none");
		}else if('fwgl'==menu){
			$("#pxjq").css("display","none");
			$("#zsgl").css("display","none");
			$("#scmgl").css("display","none");
			$("#jhgl").css("display","none");
			$("#setgl").css("display","none");
			$("#fwgl").css("display","");
			$("#ggfs").css("display","none");
			$("#fzgl").css("display","none");
		}else if('ggfs'==menu){
			$("#pxjq").css("display","none");
			$("#zsgl").css("display","none");
			$("#scmgl").css("display","none");
			$("#jhgl").css("display","none");
			$("#setgl").css("display","none");
			$("#ggfs").css("display","");
			$("#fwgl").css("display","none");
			$("#fzgl").css("display","none");
		}else if('fzgl'==menu){
			$("#pxjq").css("display","none");
			$("#zsgl").css("display","none");
			$("#scmgl").css("display","none");
			$("#jhgl").css("display","none");
			$("#setgl").css("display","none");
			$("#ggfs").css("display","none");
			$("#fwgl").css("display","none");
			$("#fzgl").css("display","");
		}
	}
	
	//改变首次末的值
	var SPF = ["3","1","0",""];
	var spfIndex = 0;
	
	window.chgSPFValues = function(index,lineId){
		if(index==0){
			var sValue = $("#spf_win_"+lineId).html();
			for(var t=0;t<SPF.length;t++){
				if(SPF[t]==sValue){
					spfIndex = t;
					break;
				}
			}
			if(spfIndex<SPF.length-2){
				spfIndex++;
			}else{
				spfIndex=0;
			}
			$("#spf_win_"+lineId).html(SPF[spfIndex]);
			$("#spf_draw_"+lineId).html("");
			$("#spf_lose_"+lineId).html("");
		}else if(index==1){
			var sValue = $("#spf_win_"+lineId).html();
			var pValue = $("#spf_draw_"+lineId).html();
			var fValue = $("#spf_lose_"+lineId).html();
			var newSPF = [];
			var newIndex = 0;
			for(var t=0;t<SPF.length;t++){
				if(SPF[t]!=sValue){
					newSPF[newIndex++]=SPF[t];
				}
			}
			for(var t=0;t<newSPF.length;t++){
				if(newSPF[t]==pValue){
					spfIndex = t;
					break;
				}
			}
			if(spfIndex<newSPF.length-1){
				spfIndex++;
			}else{
				spfIndex=0;
			}
			$("#spf_draw_"+lineId).html(newSPF[spfIndex]);
			$("#spf_lose_"+lineId).html("");
		}else{
			var sValue = $("#spf_win_"+lineId).html();
			var pValue = $("#spf_draw_"+lineId).html();
			var fValue = $("#spf_lose_"+lineId).html();
			if(""==sValue||""==pValue){
				return ;
			}
			var newSPF = [];
			var newIndex = 0;
			for(var t=0;t<SPF.length;t++){
				if(SPF[t]!=sValue&&SPF[t]!=pValue){
					newSPF[newIndex++]=SPF[t];
				}
			}
			for(var t=0;t<newSPF.length;t++){
				if(newSPF[t]==fValue){
					spfIndex = t;
					break;
				}
			}
			if(spfIndex<newSPF.length-1){
				spfIndex++;
			}else{
				spfIndex=0;
			}
			$("#spf_lose_"+lineId).html(newSPF[spfIndex]);
		}
		var newUnits=1;
		//var spIndex = 0;
		//var spValues = [];
		$("input[name='matchCk'][type='checkbox']").each(function(i){
			var win = $("#spf_win_"+$(this).val()).html();
			var draw = $("#spf_draw_"+$(this).val()).html();
			var lose = $("#spf_lose_"+$(this).val()).html();
			/*
			var winSp = $("#sps_win_"+$(this).val()).html();
			var drawSp = $("#sps_draw_"+$(this).val()).html();
			var loseSp = $("#sps_lose_"+$(this).val()).html();*/
			
			var max = 0;
			var count = 0;
			if(""!=win){
				count++;
			}
			if(""!=draw){
				count++;
			}
			if(""!=lose){
				count++;
			}
			newUnits*=count;
			/*if(""!=winSp&&max<parseFloat(winSp)){
				max = parseFloat(winSp);
			}
			if(""!=drawSp&&max<parseFloat(drawSp)){
				max = parseFloat(drawSp);
			}
			if(""!=loseSp&&max<parseFloat(loseSp)){
				max = parseFloat(loseSp);
			}
			spValues[spIndex++]=max;*/
		});
		$("#units").val(newUnits);
		$("#filterForm_units").html(newUnits);
		$("#filterForm_schemeCost").html(newUnits*2);
		
	}
	
//-----------------------------------------集合过滤
	var i=0;
	var jhglIndex;
	var selectCondition=[];//存储选中的所有条件
	var ltValue = 0;//下拉表左边值
	var gtValue = 0;//右边值
	var jhCurStep = 0;//记录当前打开按钮的步骤
	var jhIndex = 0;//记录当前打开按钮的下标
	var selectValues=[];

	window.openRestDialog=function(curStep,index){
		jhCurStep = curStep;
		jhIndex = index;
		ltValue = 0;
		gtValue = 0;
		
		$("input[name='matchCk'][type='checkbox']").each(function(i){
			selectValues[$(this).val()]='-1,-1,-1';
		});
		
		$("#selectConditions").html("");
		displayAllSelectionConditions();
		dialog("场次命中","id:div_pop_content","680px","auto","/pages/css/fz.css");
	}
	//选择单个
	window.selectSingle=function(lineId,value){
		if(value==3){
			if($("#ss2_"+lineId+"_3")[0].className=="ss2"){
				$("#ss2_"+lineId+"_3").removeClass("ss2");
				$("#ss2_"+lineId+"_3").addClass("ss1");
				selectValues[lineId]=arrangeSelectValue(lineId,value);
			}else{
				$("#ss2_"+lineId+"_3").removeClass("ss1");
				$("#ss2_"+lineId+"_3").addClass("ss2");
				selectValues[lineId]=arrangeUnSelectValue(lineId,value);
			}
		}else if(value==1){
			if($("#ss2_"+lineId+"_1")[0].className=="ss2"){
				$("#ss2_"+lineId+"_1").removeClass("ss2");
				$("#ss2_"+lineId+"_1").addClass("ss1");
				selectValues[lineId]=arrangeSelectValue(lineId,value);
			}else{
				$("#ss2_"+lineId+"_1").removeClass("ss1");
				$("#ss2_"+lineId+"_1").addClass("ss2");
				selectValues[lineId]=arrangeUnSelectValue(lineId,value);
			}
		}else if(value==0){
			if($("#ss2_"+lineId+"_0")[0].className=="ss2"){
				$("#ss2_"+lineId+"_0").removeClass("ss2");
				$("#ss2_"+lineId+"_0").addClass("ss1");
				selectValues[lineId]=arrangeSelectValue(lineId,value);
			}else{
				$("#ss2_"+lineId+"_0").removeClass("ss1");
				$("#ss2_"+lineId+"_0").addClass("ss2");
				selectValues[lineId]=arrangeUnSelectValue(lineId,value);
			}
		}
		$("#floatBox .content").html($("#div_pop_content").html());
	}
	//全选或者全删
	window.selectAll=function(key){
		if(key=='select'){
			$("input[name='matchCk'][type='checkbox']").each(function(i){
				selectValues[$(this).val()]='3,1,0';
				$("#ss2_"+$(this).val()+"_3").removeClass("ss2");
				$("#ss2_"+$(this).val()+"_1").removeClass("ss2");
				$("#ss2_"+$(this).val()+"_0").removeClass("ss2");

				$("#ss2_"+$(this).val()+"_3").addClass("ss1");
				$("#ss2_"+$(this).val()+"_1").addClass("ss1");
				$("#ss2_"+$(this).val()+"_0").addClass("ss1");
			});	
			$("#floatBox .content").html($("#div_pop_content").html());
		}else{
			$("input[name='matchCk'][type='checkbox']").each(function(i){
				selectValues[$(this).val()]='-1,-1,-1';
				$("#ss2_"+$(this).val()+"_3").removeClass("ss1");
				$("#ss2_"+$(this).val()+"_1").removeClass("ss1");
				$("#ss2_"+$(this).val()+"_0").removeClass("ss1");				
				
				$("#ss2_"+$(this).val()+"_3").addClass("ss2");
				$("#ss2_"+$(this).val()+"_1").addClass("ss2");
				$("#ss2_"+$(this).val()+"_0").addClass("ss2");			
			});
			$("#floatBox .content").html($("#div_pop_content").html());
		}

	}
	
	window.selectKey=function(key){
		$("input[name='matchCk'][type='checkbox']").each(function(i){
			if(key==3){
				selectValues[$(this).val()]=arrangeSelectValue($(this).val(),3);
				$("#ss2_"+$(this).val()+"_3").removeClass("ss2");
				$("#ss2_"+$(this).val()+"_3").addClass("ss1");
			}else if(key==1){
				selectValues[$(this).val()]=arrangeSelectValue($(this).val(),1);
				$("#ss2_"+$(this).val()+"_1").removeClass("ss2");
				$("#ss2_"+$(this).val()+"_1").addClass("ss1");
			}else if(key==0){
				selectValues[$(this).val()]=arrangeSelectValue($(this).val(),0);
				$("#ss2_"+$(this).val()+"_0").removeClass("ss2");
				$("#ss2_"+$(this).val()+"_0").addClass("ss1");
			}
		});
		$("#floatBox .content").html($("#div_pop_content").html());
	}
	
	window.operaConditions=function(key,value){
		if(key=='add'){
			var cond = buildCondtions();
			selectConditionArray_push(i,cond);
			i++;
		}else if(key=='replace'){
			if(typeof(jhglIndex)=='undefined'){
				alert('请选择要替换的行');
				return false;
			}
			var cond = buildCondtions();
			selectCondition[jhglIndex]=cond;
		}else if(key=='cancel'){
			if(typeof(jhglIndex)=='undefined'){
				alert('请选择要删除的行');
				return false;
			}
			if(i>=0){
				$("#selectCondtion_"+jhglIndex).remove();
				selectConditionArray_remove(jhglIndex);
				i--;
			}
		}else if(key=='cancelAll'){
			for(var j=0;j<i;j++){
				selectConditionArray_remove(j);
			}
			i=0;
		}
		displayAllSelectionConditions();
		$("#floatBox .content").html($("#div_pop_content").html());
	}
	//改变选择条件的下标
	window.chgIndex=function(index){
		jhglIndex = index;
	}
	//改变集合下拉列表值
	window.chgSelectionCondtionState=function(key,value){
		if('lt'==key){
			ltValue = value;
		}else{
			gtValue = value;
		}
	}
	
	window.clickOK = function(){
		alert("共选择了"+selectCondition.length+"个条件");
		$("#lt3_"+jhCurStep+"_"+jhIndex).val("共选择了"+selectCondition.length+"个条件");

		var curStepCond = hashObjArray[jhCurStep].condArray[jhIndex];
		var conds = curStepCond.split("|");
		var cond = conds[0];
		if(selectCondition.length>0){
			cond+='|';
			for(var i=0;i<selectCondition.length;i++){
				cond+=selectCondition[i];
				if(i<selectCondition.length-1)
					cond+="|";
			}
		}
		hashObjArray[jhCurStep].condArray[jhIndex]=cond;
		//$("#floatBox").attr("class","");
		//$("#floatBox").css("display","none");
	}
	
	function arrangeSelectValue(lineId,value){
		var lineValue = selectValues[lineId];
		var values = lineValue.split(",");
		var newLineValue = '';
		if(value==3){
			newLineValue+='3,'+values[1]+','+values[2];
		}else if(value==1){
			newLineValue+=values[0]+',1,'+values[2];
		}else if(value==0){
			newLineValue+=values[0]+','+values[1]+',0';
		}
		return newLineValue;
	}
	
	function arrangeUnSelectValue(lineId,value){
		var lineValue = selectValues[lineId];
		var values = lineValue.split(",");
		var newLineValue = '';
		if(value==3){
			newLineValue+='-1,'+values[1]+','+values[2];
		}else if(value==1){
			newLineValue+=values[0]+',-1,'+values[2];
		}else if(value==0){
			newLineValue+=values[0]+','+values[1]+',-1';
		}
		return newLineValue;
	}
	
	function buildCondtions(){
		var conditions = '';
		var newCondition='';
		$("input[name='matchCk'][type='checkbox']").each(function(i){
			var values = selectValues[$(this).val()];
			if(values=='-1,-1,-1'){
				conditions+="* ";
			}else{
				var value = values.split(",");
				for(var j=0;j<value.length;j++){
					if(value[j]!=-1){
						conditions+=value[j];
					}
					if(j==value.length-1){
						conditions+=",";
					}
				}
			}			
		});
		newCondition = conditions.substr(0,conditions.length-1);
		newCondition+=":"+ltValue+"-"+gtValue;
		return newCondition;
	}
	
	function selectConditionArray_push(index, obj) {
		selectCondition[index] = obj;
	}
	
	function selectConditionArray_remove(index) {
		delete selectCondition[index];
		if(index < selectCondition.length-1){
			for ( var i = index; i < selectCondition.length; i++) {			
				selectCondition[i] = selectCondition[i+1];
			}
		}		
		selectCondition.length--;
	}
	//显示所有选择条件
	function displayAllSelectionConditions(){
		var html= '';
		for ( var t = 0; t < selectCondition.length; t++) {
			var data =selectCondition[t];
			if(null!=data){
				if(t%2==0){
					html+='<tr class="nr_bga"><td height="23" id="selectCondtion_'+t+'" onclick="chgIndex('+t+');">'+data+'</td></tr>';
				}else{
					html+='<tr><td height="23" id="selectCondtion_'+t+'" onclick="chgIndex('+t+');">'+data+'</td></tr>';
				}
			}
		}
		$("#selectConditions").html(html);	
	}	
	
	//-----------------------------------------分组过滤	
	
	var gi=0;
	var groupFilterIndex;
	var groupCondition=[];//存储选中的所有条件
	var gltValue = 0;//下拉表左边值
	var ggtValue = 0;//右边值
	var gCurStep = 0;//记录当前打开按钮的步骤
	var gIndex = 0;//记录当前打开按钮的下标
	var groupValues=[];
	window.openGroupDialog=function(curStep,index,code){
		gCurStep = curStep;
		gIndex = index;
		gltValue = 0;
		ggtValue = 0;
		
		$("#selectGroupConditions").html("");
		displayAllGroupnConditions();
		if(code==0){
			$("#groupSum").css("display","");
			$("#groupDd").css("display","none");
			$("#groupZshz").css("display","none");
			$("#groupZsj").css("display","none");
			dialog("分组和值","id:div_group_content","680px","auto","/pages/css/fz.css");
		}else if(code==1){
			$("#groupDd").css("display","");
			$("#groupSum").css("display","none");
			$("#groupZshz").css("display","none");
			$("#groupZsj").css("display","none");
			dialog("分组断点","id:div_group_content","680px","auto","/pages/css/fz.css");
		}else if(code==2){
			$("#groupZshz").css("display","");
			$("#groupSum").css("display","none");
			$("#groupDd").css("display","none");
			$("#groupZsj").css("display","none");
			dialog("分组指数和值","id:div_group_content","680px","auto","/pages/css/fz.css");
		}else if(code==3){
			$("#groupZshz").css("display","none");
			$("#groupSum").css("display","none");
			$("#groupDd").css("display","none");
			$("#groupZsj").css("display","");
			dialog("分组指数积值","id:div_group_content","680px","auto","/pages/css/fz.css");
		}
	}
	
	//改变选择条件的下标
	window.chgGroupIndex=function(index){
		groupFilterIndex = index;
	}
	window.chgGroupCk=function(value){
		if($("#groupMatch_"+value).attr("checked")==true){
			$("#groupMatch_"+value).attr("checked","");
		}else{
			$("#groupMatch_"+value).attr("checked",true);
		}
	}
	
	window.operaGroupConditions=function(key,value){
		if(key=='add'){
			var cond = buildGroupCondtions();
			groupConditionArray_push(gi,cond);
			gi++;
		}else if(key=='replace'){
			if(typeof(groupFilterIndex)=='undefined'){
				alert('请选择要替换的行');
				return false;
			}
			var cond = buildGroupCondtions();
			groupCondition[groupFilterIndex]=cond;
		}else if(key=='cancel'){
			if(typeof(groupFilterIndex)=='undefined'){
				alert('请选择要删除的行');
				return false;
			}
			if(gi>0){
				$("#groupCondtion_"+groupFilterIndex).remove();
				groupConditionArray_remove(groupFilterIndex);
				gi--;
			}
		}else if(key=='cancelAll'){
			for(var j=0;j<gi;j++){
				groupConditionArray_remove(j);
			}
			gi=0;
		}
		displayAllGroupnConditions();
		$("#floatBox .content").html($("#div_group_content").html());
	}
	
	function buildGroupCondtions(){
		var cond = '';
		var newCondition='';
		$("input[name='matchCk'][type='checkbox']").each(function(i){
			if($("#groupMatch_"+$(this).val()).attr("checked")==true){
				cond+='y,';
			}else{
				cond+='n,';
			}
		});
		newCondition+=cond.substr(0,cond.length-1)+":"+gltValue+"-"+ggtValue;
		return newCondition;
	}
	//改变集合下拉列表值
	window.chgGroupCondtionState=function(key,value){
		if('lt'==key){
			gltValue = value;
		}else{
			ggtValue = value;
		}
	}
	//显示所有选择条件
	function displayAllGroupnConditions(){
		var html= '';
		for ( var t = 0; t < groupCondition.length; t++) {
			var data =groupCondition[t];
			if(null!=data){
				if(t%2==0){
					html+='<tr class="nr_bga"><td height="23" id="groupCondtion_'+t+'" onclick="chgGroupIndex('+t+');">'+data+'</td></tr>'
				}else{
					html+='<tr><td height="23" id="groupCondtion_'+t+'" onclick="chgGroupIndex('+t+');">'+data+'</td></tr>'
				}
			}
		}
		$("#selectGroupConditions").html(html);	
	}	
	
	function groupConditionArray_push(index, obj) {
		groupCondition[index] = obj;
	}
	
	function groupConditionArray_remove(index) {
		delete groupCondition[index];
		if(index < groupCondition.length-1){
			for ( var i = index; i < groupCondition.length; i++) {			
				groupCondition[i] = groupCondition[i+1];
			}
		}		
		groupCondition.length--;
	}
	
	window.clickGroupOK = function(){
		alert("共选择了"+groupCondition.length+"个条件");
		$("#lt3_"+gCurStep+"_"+gIndex).val("共选择了"+groupCondition.length+"个条件");

		var curStepCond = hashObjArray[gCurStep].condArray[gIndex];
		var conds = curStepCond.split("|");
		var cond = conds[0];
		if(groupCondition.length>0){
			cond+='|';
			for(var i=0;i<groupCondition.length;i++){
				cond+=groupCondition[i];
				if(i<groupCondition.length-1)
					cond+="|";
			}
		}
		hashObjArray[gCurStep].condArray[gIndex]=cond;
	}
	
	
//--------------------过滤完毕后提交数据	
	window.handlerItems = function(){
		var units = $("#createForm_units").val();
		var schemeCost = $("#createForm_schemeCost").val();
		var datas = $("#createForm_content").val();
		if(null==datas||''==datas){
			alert('提交内容不能为空!');
			return false;
		}
		if(schemeCost<=0){
			alert('方案金额有误！');
			return false;
		}
		var html = '';
		
		$("input[name='matchCk'][type='checkbox']").each(function(i){
			var lineId = $(this).val();
			$("#matchFilterDatas_"+lineId).remove();
		});
		
		$("input[name='matchCk'][type='checkbox']").each(function(i){
			var lineId = $("#lineId_"+$(this).val()).html();
			var homeTeamName = $("#homeTeamName_"+$(this).val()).html();
			var handicap = $("#handicap_"+$(this).val()).html();
			var guestTeamName = $("#guestTeamName_"+$(this).val()).html();
			
			var win = $("#spf_win_"+$(this).val()).html();
			var draw = $("#spf_draw_"+$(this).val()).html();
			var lose = $("#spf_lose_"+$(this).val()).html();
			
			var winSps = $("#sps_win_"+$(this).val()).html();
			var drawSps = $("#sps_draw_"+$(this).val()).html();
			var loseSps = $("#sps_lose_"+$(this).val()).html();
			
			var items = lineId+","+homeTeamName+","+handicap+","+guestTeamName+","+win+","+draw+","+lose+","+winSps+","+drawSps+","+loseSps;
			html+='<input type=\"hidden\" name=\"matchFilterDatas['+i+']\" id=\"matchFilterDatas_'+lineId+'\" value=\"'+items+'\"/>';
		});
		$("#filterForm").append(html);
		//var typeArr = PassTypeUtil.getSinglePassType($("#size").val());
		$("#filterForm").submit();
	}
	
	window.updateBet = function(){
		var multiple = $("#createForm_multiple").val();
		var units = $("#createForm_units").val();
		$("#createForm_schemeCost").val(multiple*units*2);
		$("#totalCost").html("总金额："+(multiple*units*2));
	}
	
});


