
//避免全局变量重复
if(typeof(compute)=="undefined")
{
	compute={};
}
function comshow()
{
	compute.view.init();
}
compute.view={
	    lotteryType:"",
	    //显示方案
	    tzplanShow:"",
	    //当前彩期
	    curPeriod:"",
	    //投注倍数
	    computebeishu:"",
	    getprize:function(){
	
	    },
	    //从主页面获取参数
	    getValue:function(){
					  	compute.model.tzplan=document.getElementById("content").value;
					    compute.model.tzmoney=parseInt(document.getElementById("computezhushu").value)*2;
					  	
					    this.curPeriod=document.getElementById("createFormPeriodId").value;	
					    if(!isDigit(this.curPeriod))
					    {
					       alert("页面未获取到彩期，暂时不能计算，请在页面获取到彩期后再进行计算");	
					       return false;
					   }
					    
					    //显示页面，并且对页面进行初始化操作
					    return true;
					   },
		   //将所需参数设置在计算页面
	       show:function(){
	    	 return true;
	    	
	    },
	  
	    //获取追号彩期
	    getTerm:function()
	   {
	    	var lotteryType_key=document.getElementById("lotteryType_key").value;
	    	var content=document.getElementById("content").value;
	    	
	    	//var betType=document.getElementById("betType_input").value;
	    	
	        var mzzs_select=document.getElementById("mzzs_select");
	        var createForm = getCreateForm();
	    	mzzs_select.options.length = 0;
	    	
			 var betType_prizeselect = document.getElementById("betType_prize");
			 var  prize = 0 ;
	    	 var playType = createForm.elements['createForm.playType'].value;
			    var betTypePrizeUrl= window.BASESITE+"/"+lotteryType_key+"/scheme!getBetTypePrize.action?createForm.playType="+playType;
			    $.ajax({ 
					type: 'GET',
					cache : false,
					url: betTypePrizeUrl,
					success : function(data) {
					    var betType_prizeselect = document.getElementById("betType_prize");
					    betType_prizeselect.options.length=0;
						betType_prizeselect.options.add(new Option(data.prize,data.prize));
						prize= data.prize;
					}
			});
			   
		    var periodId = createForm.elements['createForm.periodId'].value;
		    var chaseNumUrl= window.BASESITE+"/"+lotteryType_key+"/scheme!canCapacityChaseIssue.action?id="+periodId;
		    $.ajax({ 
						type: 'GET',
						cache : false,
						url: chaseNumUrl,
						success : function(data) {
			        		mzzs_select.options.length=0;
			        		mzzs_select.options.add(new Option(1,prize));
							var periodList = data.periodList.split("|");
					        for(var i=0;i<periodList.length;i++){
					        	if(periodList[i]!="") {
					        		compute.model.termList.push(periodList[i]);
					        	}
					        }
						}
				});
		    
		   
	   },
	    
	  	init:function(){
	  	  if(this.getValue()&&this.show())
	  	   {
	  	   	
	  	    document.getElementById("createForm_capacityChase_periodSizeOfChase").options[0].selected=true;
	  	    document.getElementById("startbeishu").value=1;
	  	   	document.getElementById("lucre").checked=true;
	  	   	document.getElementById("alllucre").checked=false;
	  	   	document.getElementById("lucrep").checked=true;
	  	   	document.getElementById("alllucrep").checked=false;
	  	   	document.getElementById("lucret").checked=false;
	  	  	document.getElementById("lucrept").checked=false;
	  	  	document.getElementById("allafterlucre").value=10;
	  	  	document.getElementById("befortermmember").value=5;
	  	  	document.getElementById("beforelc").value=10;
	  	  	document.getElementById("aferlc").value=10;
	  	  	document.getElementById("befortermmemberp").value=5;
	  	  	document.getElementById("all_lucrep_select").options[0].selected=true;
	  	  	document.getElementById("before_lcp_select").options[0].selected=true;
	  	  	document.getElementById("aferlcp_select").options[0].selected=true;
	  	   	document.getElementById("showtbody").innerHTML="<table width='100%' border='0' cellspacing='0' cellpadding='0'  ><tr> <td>奖期</td><td>倍数</td><td>本期投入</td> <td>累计投入</td><td>本期收益</td><td>累计收益</td> <td>收益率</td></tr></table>";
		    this.getTerm();
	  	   }
	
	  	
	    },
	    
	    
	    getpizemoney:function(){
	    	//DOTO 根据玩法
	    },
	    
	    
	    
	    close:function(){
	    document.getElementById("sd_beitu").style.display="none";
	    },
	    clear:function(){
	    	
		//彩期列表
		compute.model.termList=new Array();compute.model.terms=new Array();compute.model.beishus=new Array();compute.model.amounts=new Array();compute.model.amount=0;
		//投注内容
		compute.model.tzplan="";compute.model.tzmoney=0;compute.model.prize=0;compute.model.termnumber=5;compute.model.startterm=""; compute.model.startbeishu=1;
		//收益是否计算
		compute.model.lucre=true;compute.model.befortermmember=-1;compute.model.beforelc=10;compute.model.aferlc=10;compute.model.lucrep=true;compute.model.befortermmemberp=-1;compute.model.beforelcp=1;compute.model.aferlcp=0.2;
        this.close();
	    }
	  	
}

//获取需要技术的数据
compute.model={
	
	//彩期列表
	termList:new Array(),
	terms:new Array(),
	beishus:new Array(),
	amounts:new Array(),
	amount:0,
	//投注内容
	tzplan:"",
	tzplanShow:"",
	tzmoney:0,
	//已经投入
	hasInvested:0,
	//对应的奖金
	prize:0,
	prizedefault:0,
	//追号期数
	termnumber:5,
	//开始计算的彩期
	startterm:"",
	
	//开始计算的倍数
    startbeishu:1,
    
	//收益是否计算
	lucre:true,
	
	//收益前多少期
	befortermmember:-1,
	//前多少区收益多少钱
	beforelc:10,
	
	//后多少期收益多少钱
	aferlc:10,
	
	//收益率是否计算
	lucrep:true,
	//收益率前多少期
	befortermmemberp:-1,
	beforelcp:1,
	aferlcp:0.2
}

compute.controlpage={
	//校验页面参数是否正确--并对实体填充数据
	verify:function(){
		//收益是否计算
	    compute.model.befortermmember=-1;
	    compute.model.beforelc=10;
	    compute.model.aferlc=10;
	    compute.model.befortermmemberp=-1;
	    compute.model.beforelcp=1;
	    compute.model.aferlcp=0.2;
	    var  termselect= document.getElementById("startChasePeriod"); 
	    compute.model.startterm=termselect.options[termselect.selectedIndex].value; 
		compute.model.startbeishu=document.getElementById("startbeishu").value;
		if(!this.alertnumber("倍数",compute.model.startbeishu,10000))return false;
		
		//校验是否设置盈利方式，并将值保持在model中
		  if(document.getElementById("lucre").checked)
        {
        	if(document.getElementById("alllucre").checked||document.getElementById("lucret").checked) {
        		compute.model.lucre=true;
        	} else {
        		 compute.model.lucre=false;
        	}
            
        } else {
        	   compute.model.lucre=false;
        }
        	//累计收益率
        if(document.getElementById("lucrep").checked)
        {
        	if(document.getElementById("alllucrep").checked||document.getElementById("lucrept").checked) {
        		compute.model.lucrep=true;
        	} else {
        		compute.model.lucrep=false;
        	}
        	
        }
        else
        {
        	compute.model.lucrep=false;
        }
        if(!compute.model.lucre && !compute.model.lucrep )
        {
        	alert("请选择一个盈利方式，进行计算");
        	 document.getElementById("lucre").focus;
        	return false;
        }
        return true;
	},
	alertnumber:function(message,val,max)
	{
		if(!/^\d+$/.test(val))
        {
           alert(message+"错误,请输入正确的参数");
           return  false;
        }
       if(parseInt(val)>parseInt(max)||parseInt(val)<1)
		{
			alert(message+"错误,请输入1-"+max+"之间的整数数");
			return false;
		}
	 return true;
	},
    getPageValue:function(){

    	var startermselect=document.getElementById("startChasePeriod");
        compute.model.startterm=startermselect.options[startermselect.selectedIndex].text;
    	
    	compute.model.tzplan=document.getElementById("content").value;
	    compute.model.tzmoney=parseInt(document.getElementById("computezhushu").value)*2;
    	
        termmerberselect=document.getElementById("createForm_capacityChase_periodSizeOfChase");
        compute.model.termnumber=termmerberselect.options[termmerberselect.selectedIndex].value;
        var yjmz= document.getElementById("yjmz");
        
        var lotteryType_key=document.getElementById("lotteryType_key").value;
        this.curPeriod =document.getElementById("createFormPeriodId");

        compute.model.prize=parseInt(document.getElementById("mzzs_select").value);
        var  schemeCost =  parseInt(document.getElementById("schemeCost").value);
        if(compute.model.prize<=schemeCost){
        	alert("该方案不盈利，不适合倍投追号");
        	return;
        }
        compute.model.hasInvested = parseInt(0);
	 	if(null!=document.getElementById("hasInvested").value&&''!=document.getElementById("hasInvested").value) {
	 		compute.model.hasInvested = document.getElementById("hasInvested").value;
	 	}
	
        //累计收入
        if(compute.model.lucre==true)
        {
        	
        	
        	if(document.getElementById("alllucre").checked)
        	{
        		compute.model.beforelc=0;
        		compute.model.aferlc= parseInt(document.getElementById("allafterlucre").value);
        		if(!this.alertnumber("全程累计收益",compute.model.aferlc,100000))return false;
        	}
        	else
        	{
        		compute.model.befortermmember=document.getElementById("befortermmember").value;
        		//if(!this.alertnumber("累计收益期数",compute.model.befortermmember,compute.model.termnumber))return false;
        		
        		if(parseInt(compute.model.befortermmember)>parseInt(compute.model.termnumber)) {
        			compute.model.befortermmember = compute.model.termnumber;
        		}
        		
        		compute.model.beforelc=parseInt(document.getElementById("beforelc").value) ;
        			if(!this.alertnumber("前期累计收益",compute.model.beforelc,100000))return false;
        		compute.model.aferlc=parseInt(document.getElementById("aferlc").value);
        			if(!this.alertnumber("后期累计收益",compute.model.aferlc,100000))return false;
        	}
              
             
        }
             
        if(compute.model.lucrep==true)
        {
        	if(document.getElementById("alllucrep").checked)
        	{
        		var alllucrepselect=document.getElementById("all_lucrep_select");
        		compute.model.beforelcp=alllucrepselect.options[alllucrepselect.selectedIndex].text;
        	}
        	else
        	{
        	    compute.model.befortermmemberp=document.getElementById("befortermmemberp").value;
        	    //	if(!this.alertnumber("收益率期数个数",compute.model.befortermmemberp,compute.model.termnumber))return false;
        	    
        	    if(parseInt(compute.model.befortermmemberp)>parseInt(compute.model.termnumber)) {
        			compute.model.befortermmemberp = compute.model.termnumber;
        		}
        	    
        	    var beforelcpselect=document.getElementById("before_lcp_select");
        	    compute.model.beforelcp=beforelcpselect.options[beforelcpselect.selectedIndex].text;
        	    var aferlcpselect=document.getElementById("aferlcp_select");
        	    compute.model.aferlcp=aferlcpselect.options[aferlcpselect.selectedIndex].text;
        	}
        }
      
        return true;
    }
}
//通过数据进行计算
compute.manage={
	beishu1:false,
	beishu2:false,
	beishu3:false,
	beishu4:false,

	//计算
	cbeishu:function(){
		this.beishu1=false;
		this.beishu2=false;
		this.beishu3=false;
		this.beishu4=false;
		var beishu=compute.model.startbeishu;
	
		var minmoney1=0;
		var minmoney2=0;
		if(compute.model.lucre)
		{
		   minmoney1=compute.model.aferlc;
		    this.beishu3=compute.model.prize-compute.model.tzmoney;
		  
			if(compute.model.befortermmember!=-1)
			{
			 this.beishu3=compute.model.prize-compute.model.tzmoney;
			 this.beishu4=compute.model.prize-compute.model.tzmoney;
		      minmoney1=compute.model.beforelc;
		      minmoney2=compute.model.aferlc;
		     
			}
			else
			{
				  compute.model.befortermmember=compute.model.termnumber;
			}
		}
		if(compute.model.lucrep)
		{
			this.beishu1=parseFloat(compute.model.prize/(1+parseFloat(compute.model.beforelcp)/100))-parseFloat(compute.model.tzmoney);
			if(compute.model.befortermmemberp!=-1)
			{
				this.beishu2=parseFloat(compute.model.prize)/(1+parseFloat(compute.model.aferlcp)/100)-parseFloat(compute.model.tzmoney);
				if(this.beishu2<0 )
			 {
				
				return !!alert("该方案不适合倍投追号");
			  }
			}
			else
			{
				  compute.model.befortermmemberp=compute.model.termnumber;
			}
			if(this.beishu1<0 )
			{
				return !!alert("该方案不适合倍投追号");
			}
		}
		var tablelist=[];
		var sum=parseInt(compute.model.hasInvested), ljtz,countlucre;
		var starttermindex=0;
		for(var ik=0;ik<compute.model.termList.length;ik++)
		{
			 if(compute.model.startterm==compute.model.termList[ik])
			 {
			 	starttermindex=ik;
			 	break;
			 }
		}
		for(var i=0;i<compute.model.termnumber;i++)
		{
				//取满足4个条件的最大倍数（核心）
		    	beishu=Math.max(beishu,this.beishu1?Math.ceil(sum/(i<compute.model.befortermmemberp?this.beishu1:this.beishu2)):0,this.beishu3?(i<compute.model.befortermmember?Math.ceil((parseFloat(sum)+parseFloat(minmoney1))/this.beishu3):Math.ceil((parseFloat(sum)+parseFloat(minmoney2))/this.beishu4)):0);	
		    	if(beishu>100000)return !!alert("倍数过大");
			   
			    ljtz=compute.model.tzmoney*beishu ;
			    sum+=ljtz;
			    countlucre=compute.model.prize*beishu;
			  
			    tablelist[i]={term:compute.model.termList[parseInt(starttermindex+i)],beishu:beishu,ljtz:ljtz,sum:sum,countlucre:countlucre};
			     
			      
		}
      return  tablelist;
	}
	
}

function manage()
{
	if(!compute.controlpage.verify())return false;
	if(!compute.controlpage.getPageValue()) return false;
    var showlist={};
     showlist= compute.manage.cbeishu();
    var pagelist=new Array();
    compute.model.terms=new Array();
    compute.model.beishus=new Array();
    compute.model.amounts=new Array();
    compute.model.amount=0;
    if(undefined!=showlist.length)
    {
    	
	    for(var i=0;i<showlist.length;i++)
	    {
	    	var itemId = 'chase_item_'+ i;
	    	pagelist.push("<tr>" +
	    			"<td>"+parseInt(i+1)+"</td>"+
	    			"<td><input id=checkbox_"+itemId+" type='checkbox' checked='checked'/>第"+showlist[i].term+"期</td>" +
	    			"<td><span  id=beishu_" + itemId + ">"+showlist[i].beishu+"</span>倍" +
	    			"<input type='hidden' id=multiple_"+ itemId + " name='createForm.multiplesOfChase' value='"+showlist[i].beishu+"'/>"+
	    			"</td>" +
	    			"<td><span id=cost_" + itemId + ">"+showlist[i].ljtz+"</span>元" +
	    			"</td>" +
	    			"<td id='sum_'" + itemId + ">"+showlist[i].sum+"元</td>" +
	    			"<td id='countlucre_'" + itemId + ">"+showlist[i].countlucre+"元</td>" +
	    			"<td id='ljsy_'" + itemId + ">"+parseInt(showlist[i].countlucre-showlist[i].sum)+"元</td>" +
	    			"<td id='syl_'" + itemId + ">"+Math.round(parseInt(showlist[i].countlucre-showlist[i].sum)/parseInt(showlist[i].sum)*10000)/100+"%</td></tr>");
	    	compute.model.terms.push(showlist[i].term);
		    compute.model.beishus.push(showlist[i].beishu);
		    compute.model.amounts.push(showlist[i].ljtz);
		    compute.model.amount+=showlist[i].ljtz;
	    }

		document.getElementById("showtbody").innerHTML="<table class='znzh_table' width='100%' border='0' cellspacing='0' cellpadding='0' id='showtbody'><tr><th width='7%'>序号</th><th width='18%'>期号</th><th width='10%'>倍数</th><th width='12%'>本期投入</th><th width='13%'>累计投入</th><th width='13%'>本期收益</th><th width='14%'>盈利收益</th><th width='13%'>利润率</th></tr>"
		+pagelist.join("")+
		"</table>";
		chaseitemBackCall();
		document.getElementById('createForm_capacityChase_totalCostOfChase').value = compute.model.amount;
		document.getElementById('span_capacityChase_Cost').innerHTML = compute.model.amount;
    }
    
}


function chaseitemBackCall(){
	var createForm = getCreateForm();
	$("input[id^='checkbox_chase_item']").bind('click', function(e){
    	if(this.id=='checkbox_chase_item_0'){
    		return false;
    	}
    	var beishuId=this.id.replace("checkbox","beishu");//用于显示
    	var multipleId=this.id.replace("checkbox","multiple");//用于后台计算
    	var costId=this.id.replace("checkbox","cost");
    	var beishuId=this.id.replace("checkbox","beishu");
    	var sumId=this.id.replace("checkbox","sum");
    	var countlucreId=this.id.replace("checkbox","countlucre");
    	var ljsyId=this.id.replace("checkbox","ljsy");
    	var sylId=this.id.replace("checkbox","syl");

    	var beishuId  = parseInt(document.getElementById(beishuId).innerHTML);
    	var multiple  = parseInt(document.getElementById(multipleId).value);

    	
    	var cost  = parseInt(document.getElementById(costId).innerHTML);
    	var countNums = parseInt(document.getElementById('createForm_capacityChase_periodSizeOfChase').value);
		var totalCost = parseInt(document.getElementById('createForm_capacityChase_totalCostOfChase').value);
		if(this.checked){ 
			document.getElementById(multipleId).value=beishuId;
    		countNums++;
    		totalCost += cost;
    	}else{
			document.getElementById(multipleId).readOnly=true;
			document.getElementById(multipleId).value=0;
    		countNums--;
    		totalCost -= cost;
    	}
		compute.model.amount = totalCost;
		document.getElementById('createForm_capacityChase_totalCostOfChase').value = totalCost;
		document.getElementById('span_capacityChase_Cost').innerHTML = totalCost;
		});
}


function isDigit(str){ 
  var reg = /^\d*$/; 
  return reg.test(str); 
 } 
