function run(start_utc_time,end_utc_time,call)
{
    time += 1000;
    start_utc_time += time;
    var diff = (end_utc_time - start_utc_time)/1000;
    s = diff%60;
    i = (Math.floor(diff/60))%60;
    h = (Math.floor(diff/60/60))%24;
    d = Math.floor(diff/60/60/24);

    if(s<0||i<0||h<0||d<0)
    {
        var msg = '本次发起已截止';
        $("#show_count").html(msg);
        // $(".tradeMenu ul li a:first").attr('href',
		// 'javascript:alert("'+msg+'");');
        // $(".tradeMenu ul li a:last").attr('href',
		// 'javascript:alert("'+msg+'");');
		if(arguments[2]!=undefined)
		{
			call();
		}
        return 0;
    }

    $("#count_d").html(d.toString());
    $("#count_h").html(h.toString());
    $("#count_i").html(i.toString());
    $("#count_s").html(s.toString());
    setTimeout("run(start_utc_time,end_utc_time)",1000);
}
var time_out_handle;
var time_out_handle1;
// 高频彩时间显示
function split_qihao(qihao,type)
{
    var date_len = 0;
    var qihao_len = 0;
    switch(type)
    {
        case 'ssc':
            date_len = 8;
            qihao_len = 3;
            break;
        case 'ssl':
            date_len = 8;
            qihao_len = 2;
            break;
        case 'el11to5':
            date_len = 8;
            qihao_len = 2;
            break;
        case 'jxssc':
            date_len = 8;
            qihao_len = 3;
            break;
    }
    var splited_qihao = qihao.substring(0,date_len)+'-'+qihao.substring(date_len,date_len+qihao_len);
    if(type=='kl123')
        splited_qihao = qihao;
    return splited_qihao;
}
function run_kuaipin(start_utc_time,end_utc_time,cp_type)
{

    var start_utc_time=start_utc_time;
    var end_utc_time=end_utc_time;
    time += 1000;
    start_utc_time += 1000;
    var diff = (end_utc_time - start_utc_time)/1000;
    s = diff%60;
    i = (Math.floor(diff/60))%60;
    h = (Math.floor(diff/60/60))%24;
    // alert(h+':'+i+':'+s);
    if(s<0||i<0||h<0)
    {
        /*
		 * var msg = '本次购买已经截止'; $("#show_count").html(msg); return 0;
		 */
        // alert(cp_type);
        time=0;
        $.ajax({
            type: "post",
            url : BASESITE+"/keno/"+cp_type+"/time.js",
            dataType:'json',
            data: "name=bocaiwawa",
            success:function(json){
            var curr_qihao=$("#curr_qihao").text();
            $("#curr_qihao").text(split_qihao(json.issueNumber,cp_type));
            $("#curr_qihao2").text(json.issueNumber);
			// $("#leave_kai").text(json.leave.kai);
			// $("#leave_sheng").text(json.leave.sheng);
            $("#current_end_time").val(json.endTime);
            $("#current_qihao").val(json.issueNumber);
            $("#current_qihao_id").val(json.periodId);
                /*
				 * $floaterTip({ width:350, height:140, html:"<div
				 * class='note_alert'><b>请注意您的投注期号</b></br>"+curr_qihao+"期已截止，</br>当前期是<em>"+json.curr_qihao+"</em>期</div>",
				 * title:'温馨提示', fix:'true', style:'stand' });
				 * close_alert_window = setInterval("CloseAlertWindow()",
				 * 60000);
				 */
                up_zhuihao_mes(json.lastIssueId,json.periodId,json.issueNumber);// 更新追号区的期号信息
                var time = 0;
                var start_time = new Date(json.nowTime);
                var end_time = new Date(json.leftTime);
                var end_utc_time = Date.UTC(end_time.getYear(),end_time.getMonth(),end_time.getDate(),end_time.getHours(),end_time.getMinutes(),end_time.getSeconds());
                var start_utc_time = Date.UTC(start_time.getYear(),start_time.getMonth(),start_time.getDate(),start_time.getHours(),start_time.getMinutes(),start_time.getSeconds());
                up_qihao_mes(start_utc_time,end_utc_time,cp_type);
            },
            error:function(){
                alert('期号相关信息读取出错,请联系网站工作人员！');
            }
        });
        return 0;
    }


    var diffTime=null;
    if(h>0)
    {
        diffTime=geweijial(h.toString())+":"+geweijial(i.toString())+":"+geweijial(s.toString());
        $("#count_i_s").hide();
        $("#count_i_s2").show();
        $("#count_i_s2").html(diffTime);
    }
    else
    {
        diffTime=geweijial(i.toString())+":"+geweijial(s.toString());
        $("#count_i_s2").hide();
        $("#count_i_s").show();
        $("#count_i_s").html(diffTime);
    }


    time_out_handle=setTimeout("run_kuaipin("+start_utc_time+","+end_utc_time+",'"+cp_type+"')",1000);
}
function GetFormatTime(MTime)
{
    var PreAwardTime=parseInt(MTime+'000');
    PreAwardTimeObj=new Date(PreAwardTime);
    var PreMonth=PreAwardTimeObj.getMonth();
    PreMonth=parseInt(PreMonth)+1;
    var ReturnTime=PreAwardTimeObj.getFullYear()+"-"+((PreMonth<10)?'0'+PreMonth:PreMonth)+"-"+((PreAwardTimeObj.getDate()<10)?'0'+PreAwardTimeObj.getDate():PreAwardTimeObj.getDate())+" "+((PreAwardTimeObj.getHours()<10)?'0'+PreAwardTimeObj.getHours():PreAwardTimeObj.getHours())+":"+((PreAwardTimeObj.getMinutes()<10)?'0'+PreAwardTimeObj.getMinutes():PreAwardTimeObj.getMinutes());
    return ReturnTime;
}


function RefreshHisQihao(cp_type)
{
    TRADE.RefreshKjhm(cp_type);
}



function up_qihao_mes(start_utc_time,end_utc_time,cp_type)     // 更新截止时间
{
    var time = 0;
    clearTimeout(time_out_handle);
    run_kuaipin(start_utc_time,end_utc_time,cp_type);
}



function up_zhuihao_mes(prror_id,current_id,curr_qihao)              // 更新追号区的期号信息
{
    $("#today_q tr").each(
        function()
        {
           var this_id=this.id;
           if(this_id<prror_id)
           {
                $("#"+this_id).remove();
           }
        }
    );
    $("#"+prror_id).remove();
    $("#q"+current_id).html("<span class='colorRed'>"+curr_qihao+"期 [当前期]</span>");
}
function up_haoma(lists)      // 更新标题栏栏头的开奖号码
{
    var html='';
    var lists=new String(lists);
    var lists_a=lists.split(",");
    for(var i=0;i<lists_a.length;i++)
    {
        html+="<span class=\"ball_blue\">"+lists_a[i]+"</span> ";
    }
    if(html.length<1)
    {
        html="未更新";
    }
    $("#ssc_haoma").html(html);
}
function geweijial(e)
{
    if(e.length<2)
    {
        return "0"+e;
    }
    return e;
}
// 更新历史期开奖信息
function UpHisQihao(award_list,cp_type)
{
    var Html='';
    var ListLength=award_list.length;
    Html+="<table width='100%' border='0' cellpadding='0' cellspacing='1' class='bordertable'>"+
        "<thead>"+
    "<tr align='center'>"+
    "<td ><b>期号</b></td>"+
    "<td ><b>开奖时间</b></td>"+
    "<td ><b>号码</b></td>"+
    "</tr>"+
"</thead>";
    for(var i=0;i<ListLength;i++)
    {
        var AeardTime=parseInt(award_list[i].award_time+'000');
        AeardTime=new Date(AeardTime);

        Html+="<tr id='item_0'>"+
        "<td>"+award_list[i].qihao+"</td>"+
        "<td >"+award_list[i].award_time.split(" ")[1]+"</td>"+
        "<td  class='bbfont'>"+award_list[i].kjhm+"</td>"+
        "</tr>";
    }
    var Lab='SSC';
    if(cp_type=='ssc')
    {
        Lab='SSC';
    }
    if(cp_type =='ssl')
    {
        Lab='SSL';
    }
    if( cp_type =='dlc')
    {
        Lab = "11X5";
    }
    if(cp_type =='jxssc')
    {
        Lab = "JXSSC";
    }
    if(cp_type =='kl123')
    {
        Lab = "KL123";
    }
    
    Html+="<tr align='right' >"+
    "<td colspan='3'><a href='/info/award/details_kuaicai.php?lab="+Lab+"' class='colorBlack' target='_blank'>更多...</a>&nbsp;</td>"+
    "</tr>";
    Html+="</table>";
    $("#HistoryQihao").html(Html);
}

// 全角转换为半角
function toDBC(Str)
{
	var DBCStr = "" ;
	for (var i= 0 ; i<Str.length; i++){
		var c = Str.charCodeAt(i);
		if (c ==  12288 ) {
			DBCStr += String.fromCharCode(32 );
			continue ;
		}
		if  (c >  65280  && c <  65375 ) {
			DBCStr += String.fromCharCode(c - 65248 );
			continue ;
		}
		DBCStr += String.fromCharCode(c);
	}
	return  DBCStr;
}

// 校验用户们是否是会员
function check_names(str)
{
    var data = new Object();
    data.a = 'check_usernames';
    // debugger;
    str = str.trim();
	str = str.replace(/，/i, ",");

    var users = str.split(",");
    users = unique(users);
    str = users.join(",");
	
    data.usernames = escape(str).replace(/\%2C/g,',');
    var dataString = $.toJSON(data);

    var ret = '';
    $.ajax({
        type: "post",
        url : "/my/common.php",
        dataType:'json',
        data: {
            data:dataString
        },
        async:false,
        success:function(json){
            // var result = json.result;
            // debugger;
			if(json.msg!='')
			{
				ret = unescape(json.msg);
				ret = ret.replace(/,/i, "','");
				ret = "'" + ret + "'";
			}
        },
        error:function(){
            alert('用户名检查出错,请联系网站工作人员！');
        }
    });
    return ret;
}

function getFormatMoney(money)
{
    money = parseFloat(money);
    if(money!=Math.ceil(money))
        return '￥'+money;
    else
        return '￥'+money+'.00';
}

function unique(arr)
{
    var a = [],
    o = {},
    i,
    v,
    len = arr.length;
    if (len < 2) {
        return arr;
    }
    for (i = 0; i < len; i++) {
        v = arr[i];
        if (o[v] !== 1) {
            a.push(v);
            o[v] = 1;
        }
    }
    return a;
}

function refresh_zx_short( lottery )
{

    $.get(
        '/channel/include/consulting_load_ajax.php',
        'lottery='+lottery,
        function(response)
        {
            $('#questionBox').html(response);
        }
        );
}

var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
var base64DecodeChars = new Array(
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
    52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,
    -1,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14,
    15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
    -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
    41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);

function base64encode(str) {
    var out, i, len;
    var c1, c2, c3;

    len = str.length;
    i = 0;
    out = "";
    while(i < len) {
        c1 = str.charCodeAt(i++) & 0xff;
        if(i == len)
        {
            out += base64EncodeChars.charAt(c1 >> 2);
            out += base64EncodeChars.charAt((c1 & 0x3) << 4);
            out += "==";
            break;
        }
        c2 = str.charCodeAt(i++);
        if(i == len)
        {
            out += base64EncodeChars.charAt(c1 >> 2);
            out += base64EncodeChars.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4));
            out += base64EncodeChars.charAt((c2 & 0xF) << 2);
            out += "=";
            break;
        }
        c3 = str.charCodeAt(i++);
        out += base64EncodeChars.charAt(c1 >> 2);
        out += base64EncodeChars.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4));
        out += base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >>6));
        out += base64EncodeChars.charAt(c3 & 0x3F);
    }
    return out;
}

function base64decode(str) {
    var c1, c2, c3, c4;
    var i, len, out;

    len = str.length;
    i = 0;
    out = "";
    while(i < len) {
        /* c1 */
        do {
            c1 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
        } while(i < len && c1 == -1);
        if(c1 == -1)
            break;

        /* c2 */
        do {
            c2 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
        } while(i < len && c2 == -1);
        if(c2 == -1)
            break;

        out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));

        /* c3 */
        do {
            c3 = str.charCodeAt(i++) & 0xff;
            if(c3 == 61)
                return out;
            c3 = base64DecodeChars[c3];
        } while(i < len && c3 == -1);
        if(c3 == -1)
            break;

        out += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));

        /* c4 */
        do {
            c4 = str.charCodeAt(i++) & 0xff;
            if(c4 == 61)
                return out;
            c4 = base64DecodeChars[c4];
        } while(i < len && c4 == -1);
        if(c4 == -1)
            break;
        out += String.fromCharCode(((c3 & 0x03) << 6) | c4);
    }
    return out;
}

function utf16to8(str) {
    var out, i, len, c;

    out = "";
    len = str.length;
    for(i = 0; i < len; i++) {
        c = str.charCodeAt(i);
        if ((c >= 0x0001) && (c <= 0x007F)) {
            out += str.charAt(i);
        } else if (c > 0x07FF) {
            out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
            out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));
            out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));
        } else {
            out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));
            out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));
        }
    }
    return out;
}

function utf8to16(str) {
    var out, i, len, c;
    var char2, char3;

    out = "";
    len = str.length;
    i = 0;
    while(i < len) {
        c = str.charCodeAt(i++);
        switch(c >> 4)
        {
            case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 7:
                // 0xxxxxxx
                out += str.charAt(i-1);
                break;
            case 12: case 13:
                // 110x xxxx 10xx xxxx
                char2 = str.charCodeAt(i++);
                out += String.fromCharCode(((c & 0x1F) << 6) | (char2 & 0x3F));
                break;
            case 14:
                // 1110 xxxx 10xx xxxx 10xx xxxx
                char2 = str.charCodeAt(i++);
                char3 = str.charCodeAt(i++);
                out += String.fromCharCode(((c & 0x0F) << 12) |
                    ((char2 & 0x3F) << 6) |
                    ((char3 & 0x3F) << 0));
                break;
        }
    }

    return out;
}


function copyToClipboard(txt) {
    if(window.clipboardData) {
        window.clipboardData.clearData();
        window.clipboardData.setData("Text", txt);
    } else if(navigator.userAgent.indexOf("Opera") != -1) {
        window.location = txt;
    } else if (window.netscape) {
        try {
            netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
        } catch (e) {
            alert("被浏览器拒绝！\n请在浏览器地址栏输入'about:config'并回车\n然后将'signed.applets.codebase_principal_support'设置为'true'");
        }
        var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
        if (!clip)
            return;
        var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
        if (!trans)
            return;
        trans.addDataFlavor('text/unicode');
        var str = new Object();
        var len = new Object();
        var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
        var copytext = txt;
        str.data = copytext;
        trans.setTransferData("text/unicode",str,copytext.length*2);
        var clipid = Components.interfaces.nsIClipboard;
        if (!clip)
            return false;
        clip.setData(trans,null,clipid.kGlobalClipboard);
    // alert("复制成功！")
    }
}

function get_server_time()
{
    var time = new Date();
    $.ajax({
        type: "GET",
        async:false,
        url: "/channel/include/get_server_time.php",
        data:   "t=" + Math.random().toString(),
        success: function(msg){
            time = new Date(msg);
        }
    });
    return time;
}
document.write('<script type="text/javascript" src="/new_js/quick_login.js?v=2010120316"></script>');
