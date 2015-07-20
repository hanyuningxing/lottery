
function Bluck_HoverLi(m,n)
{
	var counter = 6;
    for(var i=1;i<=counter;i++)
    {
        $('#Bluck_tb_'+m+i).removeClass();
        $('#Bluck_tbc_'+m+i).css('display','none');
    }
    $('#Bluck_tbc_'+m+n).css('display','block');
    $('#Bluck_tb_'+m+n).addClass('now');
}


var g_RndNum1_no_arr = new Array();
var g_Rnd_config_arr = {
    'sx':1,
    'xz':2,
    'sr':3,
    'qq':4,
    'xm':5,
    'sj':6
};
//choose_auto_select(1,'xm')
function choose_auto_select(type,auto_type){
    var rand_type = type;
    var arg1 = g_Rnd_config_arr[auto_type];
    var arg2 = 0;
    if('sr' == auto_type){
    	  //生日
          arg2 = document.getElementsByName('sr_v_'+type+'Year')[0].value*document.getElementsByName('sr_v_'+type+'Month')[0].value*document.getElementsByName('sr_v_'+type+'Day')[0].value;
    }else{
        arg2 = $('#'+auto_type+'_v_'+type).val();
    }
    if('' == arg2){
        var msg = '';
        if( auto_type == 'qq'){  
             
            msg = '请输入qq号码';
        }
        if(auto_type == 'xm'){
            msg = '请输入姓名';
        }
        if(auto_type == 'sj'){
            msg = '请输入手机号码';
        }
        alert(msg);
        return;
    }
    //qq验证
    if( 'qq' == auto_type){
        var rs =  qq_valid( arg2);
        if(!rs){
            return ;
        }
    }
    if('sj' == auto_type){
        var rs = phone_valid(arg2);
        if(!rs){
            return;
        }
    }
    var ret_str = '';
    for(var i in g_config_arr[rand_type].min)
    {
        var str1 = '';
        var str_arr = new Array();
        for(var j = 1; j<=g_config_arr[rand_type].min[i] ; j++){
            var max = g_config_arr[rand_type].max_v[i];
            var num1 = getRndNum1_no(arg1,arg2,type,j,i,max,true);
            str_arr.push(num1);
        }
        str_arr.sort(asc);
        g_config_arr[rand_type].value[i]=str_arr;
    }
    if('1'==rand_type||'5'==rand_type||'22'==rand_type){
    	//双色球,大乐透
    	luckRandomSelect(g_config_arr[rand_type].value[1],g_config_arr[rand_type].value[2]);
    }else if('2'==rand_type){
    	luckRandomSelect(g_config_arr[rand_type].value[1],g_config_arr[rand_type].value[2],g_config_arr[rand_type].value[3]);
    }else if('3'==rand_type){
    	luckP3RandomSelect(g_config_arr[rand_type].value[1],g_config_arr[rand_type].value[2],g_config_arr[rand_type].value[3]);
    }else if('4'==rand_type){
    	luckP5RandomSelect(g_config_arr[rand_type].value[1],g_config_arr[rand_type].value[2],g_config_arr[rand_type].value[3],g_config_arr[rand_type].value[4],g_config_arr[rand_type].value[5]);
    }else if('6'==rand_type){
    	luckRandomSelect(g_config_arr[rand_type].value[1]);
    }else if('23'==rand_type){
    	luckRandomSelect(g_config_arr[rand_type].value[1]);
    }
    g_RndNum1_no_arr = new Array();
    return false;
}

function getRndNum1_no(arg1,arg2,type,index,road,max,no_same,machine){
    no_same = undefined == no_same ? true : no_same;
    machine = undefined == machine ? false : machine;
    var num = 0;
    if(machine)
    {
        num =Math.round(Math.random()*max);
    }
    else
    {
        num = _getRndNum1_no(arg1,arg2,type,index,road);
    }
    num = num % max +1;

    while(!check_is_g_Rndnum_arr(type,no_same,num,road))
    {
        num += Math.ceil(max / 2)+1;
        num = num % max +1;
    }
   // return num % max + 1;
   return num;

}
//全局配置数组
var g_config_arr = {
    '1':	{
        'min':{
            1:6,
            2:1
        },
        'max_v':{
            1:33,
            2:16
        },
        'value':{
            1:-1,
            2:-1
        }
    },
    '2':	{
        'name':'f3d',
        'color':{
            1:'Blue',
            2:'Blue',
            3:'Blue'
        },
        'limit':{
            1:10,
            2:10,
            3:10
        },
        'min':{
            1:1,
            2:1,
            3:1
        },
        'max_v':{
            1:9,
            2:9,
            3:9
        },
        'split':{
            1:'',
            2:','
        },
        'value':{
            1:-1,
            2:-1,
            3:-1
        },
        'computer':'f3d_computer',//注数计算
        'front':''
    },
    '3':	{
        'name':'pl3',
        'color':{
            1:'Blue',
            2:'Blue',
            3:'Blue'
        },
        'limit':{
            1:10,
            2:10,
            3:10
        },
        'min':{
            1:1,
            2:1,
            3:1
        },
        'max_v':{
            1:9,
            2:9,
            3:9
        },
        'split':{
            1:'',
            2:','
        },
        'value':{
            1:-1,
            2:-1,
            3:-1
        },
        'computer':'f3d_computer',//注数计算
        'front':''
    },
    '4':	{
        'name':'pl5',
        'color':{
            1:'Blue',
            2:'Blue',
            3:'Blue',
            4:'Blue',
            5:'Blue'
        },
        'limit':{
            1:10,
            2:10,
            3:10,
            4:10,
            5:10
        },
        'min':{
            1:1,
            2:1,
            3:1,
            4:1,
            5:1
        },
        'max_v':{
            1:9,
            2:9,
            3:9,
            4:9,
            5:9
        },
        'split':{
            1:'',
            2:','
        },
        'value':{
            1:-1,
            2:-1,
            3:-1,
            4:-1,
            5:-1
        },
        'computer':'pl5_computer',//注数计算
        'front':''
    },
    '5':	{
        'name':'dlt',
        'color':{
            1:'Red',
            2:'Blue'
        },
        'limit':{
            1:18,
            2:12
        },
        'min':{
            1:5,
            2:2
        },
        'max_v':{
            1:35,
            2:12
        },
        'split':{
            1:',',
            2:'|'
        },
        'value':{
            1:-1,
            2:-1
        },
        'computer':'dlt_computer',//注数计算
        'front':'0'
    },

    '6':	{
        'name':'qlc',
        'color':{
            1:'Red'
        },
        'limit':{
            1:15
        },
        'min':{
            1:7
        },
        'max_v':{
            1:30
        },
        'split':{
            1:','
        },
        'value':{
            1:-1
        },
        'computer':'qlc_computer',//注数计算
        'front':''
    },

    '7':	{
        'name':'f3d_z3',
        'color':{
            1:'Blue'
        },
        'limit':{
            1:10
        },
        'min':{
            1:2
        },
        'max_v':{
            1:9
        },
        'split':{
            1:',',
            2:'@'
        },
        'computer':'f3d_z3_computer',//注数计算
        'front':''
    },


    '8':	{
        'name':'f3d_hz',
        'color':{
            1:'Blue'
        },
        'limit':{
            1:28
        },
        'min':{
            1:1
        },
        'max_v':{
            1:27
        },
        'split':{
            1:',',
            2:'@'
        },
        'computer':'f3d_hz_computer',//注数计算
        'front':''
    },

    '9':	{
        'name':'f3d_z6_hz',
        'color':{
            1:'Blue'
        },
        'limit':{
            1:22
        },
        'min':{
            1:1
        },
        'max_v':{
            1:24
        },
        'split':{
            1:',',
            2:'@'
        },
        'computer':'f3d_hz_computer',//注数计算
        'front':'0'
    },

    '10':	{
        'name':'f3d_z3_hz',
        'color':{
            1:'Blue'
        },
        'limit':{
            1:26
        },
        'min':{
            1:1
        },
        'max_v':{
            1:26
        },
        'split':{
            1:',',
            2:'@'
        },
        'computer':'f3d_hz_computer',//注数计算
        'front':'0'
    },
                
    '11':	{
        'name':'pl3_z6',
        'color':{
            1:'Blue'
        },
        'limit':{
            1:10
        },
        'min':{
            1:3
        },
        'max_v':{
            1:9
        },
        'split':{
            1:',',
            2:'@'
        },
        'computer':'f3d_z6_computer',//注数计算
        'front':''
    },
    '12':	{
        'name':'pl3_z3',
        'color':{
            1:'Blue'
        },
        'limit':{
            1:10
        },
        'min':{
            1:2
        },
        'max_v':{
            1:9
        },
        'split':{
            1:',',
            2:'@'
        },
        'computer':'f3d_z3_computer',//注数计算
        'front':''
    },
    '13':	{
        'name':'pl3_hz',
        'color':{
            1:'Blue'
        },
        'limit':{
            1:28
        },
        'min':{
            1:1
        },
        'max_v':{
            1:27
        },
        'split':{
            1:',',
            2:'@'
        },
        'computer':'f3d_hz_computer',//注数计算
        'front':''
    },
    '14':	{
        'name':'pl3_z_hz',
        'color':{
            1:'Blue'
        },
        'limit':{
            1:27
        },
        'min':{
            1:1
        },
        'max_v':{
            1:26
        },
        'split':{
            1:',',
            2:'@'
        },
        'computer':'f3d_hz_computer',//注数计算
        'front':''
    },
    '15':	{
        'name':'dlt_12fs',
        'color':{
            1:'Blue'
        },
        'limit':{
            1:12
        },
        'min':{
            1:2
        },
        'max_v':{
            1:12
        },
        'split':{
            1:',',
            2:'@'
        },
        'computer':'dlt_12fs_computer',//注数计算
        'front':'0'
    },
     '20':{
        'name':'qxc',
        'color':{1:'Blue',2:'Blue',3:'Blue',4:'Blue',5:'Blue',6:'Blue',7:'Blue'},
        'limit':{1:11,2:11,3:11,4:11,5:11,6:11,7:11},
        'min':{1:1,2:1,3:1,4:1,5:1,6:1,7:1},
        'max_v':{1:9,2:9,3:9,4:9,5:9,6:9,7:9},
        'split':{
            1:'',
            2:','
        },
        'computer':'qxc_computer',//注数计算
        'front':''
    },
    '21':{
        'name':'ssq_dt',
        'color':{1:'Red',2:'Red',3:'Blue'},
        'limit':{1:33,2:33,3:16},
        'min':{1:1,2:1,3:1},
        'max_v':{1:5,2:33,3:16},
        'split':{
            1:'-',
            2:',',
            3:'|'
        },
        'computer':'ssq_dt_computer',//注数计算
        'front':''
    },
    '22':	{
        'name':'tc22to5',
        'color':{
            1:'Red'
        },
        'limit':{
            1:18
        },
        'min':{
            1:5
        },
        'max_v':{
            1:22
        },
        'split':{
            1:','
        },
        'value':{
            1:-1
        },
        'computer':'tc22to5_computer',//注数计算
        'front':'0'
    },
    '23':	{
        'name':'welfare36to7',
        'color':{
            1:'Red',
            2:'Red',
            3:'Red',
            4:'Red'
        },
        'limit':{
            1:18,
            2:5,
            3:5,
            4:5
        },
        'min':{
            1:1,
            2:1,
            3:1,
            4:1
        },
        'max_v':{
            1:36,
            2:5,
            3:5,
            4:5
        },
        'split':{
            1:',',
            1:',',
            1:',',
            1:','
        },
        'value':{
            1:-1,
            2:-1,
            3:-1,
            4:-1
        },
        'computer':'welfare36to7_computer',//注数计算
        'front':'0'
    }
};

function check_is_g_Rndnum_arr(type,no_same,num,road)
{//debugger;
    if(no_same)
    {
         //dump(g_RndNum1_no_arr[type]);
        //alert(g_RndNum1_no_arr[type]);
        if(undefined == g_RndNum1_no_arr[type])
        {
            g_RndNum1_no_arr[type] = new Array();
            g_RndNum1_no_arr[type][road] = new Array();
            g_RndNum1_no_arr[type][road][num] = num;
            return true;
        }
        else
        {
            if(undefined == g_RndNum1_no_arr[type][road])
            {
                g_RndNum1_no_arr[type][road] = new Array();
                g_RndNum1_no_arr[type][road][num] = num;
                return true;
            }
            else
            {
                if(undefined == g_RndNum1_no_arr[type][road][num])
                {
                    g_RndNum1_no_arr[type][road][num] = num;
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
    }
    return true;
}

/*
* Configurable variables. You may need to tweak these to be compatible with
* the server-side, but the defaults work in most cases.
*/
var hexcase = 0; /* hex output format. 0 - lowercase; 1 - uppercase        */
var b64pad = ""; /* base-64 pad character. "=" for strict RFC compliance   */
var chrsz   = 8; /* bits per input character. 8 - ASCII; 16 - Unicode      */

/*
* These are the functions you'll usually want to call
* They take string arguments and return either hex or base-64 encoded strings
*/
function hex_md5(s){ return binl2hex(core_md5(str2binl(s), s.length * chrsz));}
function b64_md5(s){ return binl2b64(core_md5(str2binl(s), s.length * chrsz));}
function str_md5(s){ return binl2str(core_md5(str2binl(s), s.length * chrsz));}
function hex_hmac_md5(key, data) { return binl2hex(core_hmac_md5(key, data)); }
function b64_hmac_md5(key, data) { return binl2b64(core_hmac_md5(key, data)); }
function str_hmac_md5(key, data) { return binl2str(core_hmac_md5(key, data)); }

/*
* Perform a simple self-test to see if the VM is working
*/
function md5_vm_test()
{
return hex_md5("abc") == "900150983cd24fb0d6963f7d28e17f72";
}


/*
* Calculate the MD5 of an array of little-endian words, and a bit length
*/
function core_md5(x, len)
{
/* append padding */
x[len >> 5] |= 0x80 << ((len) % 32);
x[(((len + 64) >>> 9) << 4) + 14] = len;

var a = 1732584193;
var b = -271733879;
var c = -1732584194;
var d = 271733878;

for(var i = 0; i < x.length; i += 16)
{
    var olda = a;
    var oldb = b;
    var oldc = c;
    var oldd = d;

    a = md5_ff(a, b, c, d, x[i+ 0], 7 , -680876936);
    d = md5_ff(d, a, b, c, x[i+ 1], 12, -389564586);
    c = md5_ff(c, d, a, b, x[i+ 2], 17, 606105819);
    b = md5_ff(b, c, d, a, x[i+ 3], 22, -1044525330);
    a = md5_ff(a, b, c, d, x[i+ 4], 7 , -176418897);
    d = md5_ff(d, a, b, c, x[i+ 5], 12, 1200080426);
    c = md5_ff(c, d, a, b, x[i+ 6], 17, -1473231341);
    b = md5_ff(b, c, d, a, x[i+ 7], 22, -45705983);
    a = md5_ff(a, b, c, d, x[i+ 8], 7 , 1770035416);
    d = md5_ff(d, a, b, c, x[i+ 9], 12, -1958414417);
    c = md5_ff(c, d, a, b, x[i+10], 17, -42063);
    b = md5_ff(b, c, d, a, x[i+11], 22, -1990404162);
    a = md5_ff(a, b, c, d, x[i+12], 7 , 1804603682);
    d = md5_ff(d, a, b, c, x[i+13], 12, -40341101);
    c = md5_ff(c, d, a, b, x[i+14], 17, -1502002290);
    b = md5_ff(b, c, d, a, x[i+15], 22, 1236535329);

    a = md5_gg(a, b, c, d, x[i+ 1], 5 , -165796510);
    d = md5_gg(d, a, b, c, x[i+ 6], 9 , -1069501632);
    c = md5_gg(c, d, a, b, x[i+11], 14, 643717713);
    b = md5_gg(b, c, d, a, x[i+ 0], 20, -373897302);
    a = md5_gg(a, b, c, d, x[i+ 5], 5 , -701558691);
    d = md5_gg(d, a, b, c, x[i+10], 9 , 38016083);
    c = md5_gg(c, d, a, b, x[i+15], 14, -660478335);
    b = md5_gg(b, c, d, a, x[i+ 4], 20, -405537848);
    a = md5_gg(a, b, c, d, x[i+ 9], 5 , 568446438);
    d = md5_gg(d, a, b, c, x[i+14], 9 , -1019803690);
    c = md5_gg(c, d, a, b, x[i+ 3], 14, -187363961);
    b = md5_gg(b, c, d, a, x[i+ 8], 20, 1163531501);
    a = md5_gg(a, b, c, d, x[i+13], 5 , -1444681467);
    d = md5_gg(d, a, b, c, x[i+ 2], 9 , -51403784);
    c = md5_gg(c, d, a, b, x[i+ 7], 14, 1735328473);
    b = md5_gg(b, c, d, a, x[i+12], 20, -1926607734);

    a = md5_hh(a, b, c, d, x[i+ 5], 4 , -378558);
    d = md5_hh(d, a, b, c, x[i+ 8], 11, -2022574463);
    c = md5_hh(c, d, a, b, x[i+11], 16, 1839030562);
    b = md5_hh(b, c, d, a, x[i+14], 23, -35309556);
    a = md5_hh(a, b, c, d, x[i+ 1], 4 , -1530992060);
    d = md5_hh(d, a, b, c, x[i+ 4], 11, 1272893353);
    c = md5_hh(c, d, a, b, x[i+ 7], 16, -155497632);
    b = md5_hh(b, c, d, a, x[i+10], 23, -1094730640);
    a = md5_hh(a, b, c, d, x[i+13], 4 , 681279174);
    d = md5_hh(d, a, b, c, x[i+ 0], 11, -358537222);
    c = md5_hh(c, d, a, b, x[i+ 3], 16, -722521979);
    b = md5_hh(b, c, d, a, x[i+ 6], 23, 76029189);
    a = md5_hh(a, b, c, d, x[i+ 9], 4 , -640364487);
    d = md5_hh(d, a, b, c, x[i+12], 11, -421815835);
    c = md5_hh(c, d, a, b, x[i+15], 16, 530742520);
    b = md5_hh(b, c, d, a, x[i+ 2], 23, -995338651);

    a = md5_ii(a, b, c, d, x[i+ 0], 6 , -198630844);
    d = md5_ii(d, a, b, c, x[i+ 7], 10, 1126891415);
    c = md5_ii(c, d, a, b, x[i+14], 15, -1416354905);
    b = md5_ii(b, c, d, a, x[i+ 5], 21, -57434055);
    a = md5_ii(a, b, c, d, x[i+12], 6 , 1700485571);
    d = md5_ii(d, a, b, c, x[i+ 3], 10, -1894986606);
    c = md5_ii(c, d, a, b, x[i+10], 15, -1051523);
    b = md5_ii(b, c, d, a, x[i+ 1], 21, -2054922799);
    a = md5_ii(a, b, c, d, x[i+ 8], 6 , 1873313359);
    d = md5_ii(d, a, b, c, x[i+15], 10, -30611744);
    c = md5_ii(c, d, a, b, x[i+ 6], 15, -1560198380);
    b = md5_ii(b, c, d, a, x[i+13], 21, 1309151649);
    a = md5_ii(a, b, c, d, x[i + 4], 6,   - 145523070);
    d = md5_ii(d, a, b, c, x[i+11], 10, -1120210379);
    c = md5_ii(c, d, a, b, x[i+ 2], 15, 718787259);
    b = md5_ii(b, c, d, a, x[i+ 9], 21, -343485551);

    a = safe_add(a, olda);
    b = safe_add(b, oldb);
    c = safe_add(c, oldc);
    d = safe_add(d, oldd);
}
return Array(a, b, c, d);

}

/*
* These functions implement the four basic operations the algorithm uses.
*/
function md5_cmn(q, a, b, x, s, t)
{
return safe_add(bit_rol(safe_add(safe_add(a, q), safe_add(x, t)), s),b);
}
function md5_ff(a, b, c, d, x, s, t)
{
return md5_cmn((b & c) | ((~b) & d), a, b, x, s, t);
}
function md5_gg(a, b, c, d, x, s, t)
{
return md5_cmn((b & d) | (c & (~d)), a, b, x, s, t);
}
function md5_hh(a, b, c, d, x, s, t)
{
return md5_cmn(b ^ c ^ d, a, b, x, s, t);
}
function md5_ii(a, b, c, d, x, s, t)
{
return md5_cmn(c ^ (b | (~d)), a, b, x, s, t);
}

/*
* Calculate the HMAC-MD5, of a key and some data
*/
function core_hmac_md5(key, data)
{
var bkey = str2binl(key);
if(bkey.length > 16) bkey = core_md5(bkey, key.length * chrsz);

var ipad = Array(16), opad = Array(16);
for(var i = 0; i < 16; i++)
{
    ipad[i] = bkey[i] ^ 0x36363636;
    opad[i] = bkey[i] ^ 0x5C5C5C5C;
}

var hash = core_md5(ipad.concat(str2binl(data)), 512 + data.length * chrsz);
return core_md5(opad.concat(hash), 512 + 128);
}

/*
* Add integers, wrapping at 2^32. This uses 16-bit operations internally
* to work around bugs in some JS interpreters.
*/
function safe_add(x, y)
{
var lsw = (x & 0xFFFF) + (y & 0xFFFF);
var msw = (x >> 16) + (y >> 16) + (lsw >> 16);
return (msw << 16) | (lsw & 0xFFFF);
}

/*
* Bitwise rotate a 32-bit number to the left.
*/
function bit_rol(num, cnt)
{
return (num << cnt) | (num >>> (32 - cnt));
}

/*
* Convert a string to an array of little-endian words
* If chrsz is ASCII, characters >255 have their hi-byte silently ignored.
*/
function str2binl(str)
{
var bin = Array();
var mask = (1 << chrsz) - 1;
for(var i = 0; i < str.length * chrsz; i += chrsz)
    bin[i>>5] |= (str.charCodeAt(i / chrsz) & mask) << (i%32);
return bin;
}

/*
* Convert an array of little-endian words to a string
*/
function binl2str(bin)
{
var str = "";
var mask = (1 << chrsz) - 1;
for(var i = 0; i < bin.length * 32; i += chrsz)
    str += String.fromCharCode((bin[i>>5] >>> (i % 32)) & mask);
return str;
}

/*
* Convert an array of little-endian words to a hex string.
*/
function binl2hex(binarray)
{
var hex_tab = hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
var str = "";
for(var i = 0; i < binarray.length * 4; i++)
{
    str += hex_tab.charAt((binarray[i>>2] >> ((i%4)*8+4)) & 0xF) +
           hex_tab.charAt((binarray[i>>2] >> ((i%4)*8 )) & 0xF);
}
return str;
}

/*
* Convert an array of little-endian words to a base-64 string
*/
function binl2b64(binarray)
{
var tab = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
var str = "";
for(var i = 0; i < binarray.length * 4; i += 3)
{
    var triplet = (((binarray[i   >> 2] >> 8 * ( i   %4)) & 0xFF) << 16)
                | (((binarray[i+1 >> 2] >> 8 * ((i+1)%4)) & 0xFF) << 8 )
                | ((binarray[i+2 >> 2] >> 8 * ((i+2)%4)) & 0xFF);
    for(var j = 0; j < 4; j++)
    {
      if(i * 8 + j * 6 > binarray.length * 32) str += b64pad;
      else str += tab.charAt((triplet >> 6*(3-j)) & 0x3F);
    }
}
return str;
}


var g_md5_obj = new Object();
function _getRndNum1_no(arg1,arg2,type,index,road){

    var date = new Date();
    var temp = 'd'+arg1.toString()+arg2.toString()+type.toString()+road.toString()+date.getUTCFullYear().toString()+date.getMonth().toString()+date.getDate().toString()+date.getUTCHours();
    var md5_temp = '';
    //if('d12112009519' == temp){debugger;}
    if(undefined == g_md5_obj[temp])
    {
        md5_temp = hex_md5(temp);
        g_md5_obj[temp] = md5_temp;
    }
    else
    {
        md5_temp = g_md5_obj[temp];
    }
    return (parseInt(md5_temp.substr(4*(index-1),4),16));
}
//qq号码验证

function qq_valid( str_qq ){
    if( str_qq.length<5 ){
        alert("请填写正确的qq号码");
        return false;
    }else{
        return true;
    }
}

//验证手机号

function phone_valid( str_phone ){
    if( str_phone.length !==11 ){
        alert("请填写正确的手机号码");
        return false;
    }else{
        return true;
    }
}

