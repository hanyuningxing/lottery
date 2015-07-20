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
        var msg = '本期发起已截止';
        $("#show_count").html(msg);
        //$(".tradeMenu ul li a:first").attr('href', 'javascript:alert("'+msg+'");');
        // $(".tradeMenu ul li a:last").attr('href', 'javascript:alert("'+msg+'");');
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
function get_server_time()
{
    var time = new Date();
    $.ajax({
        type: "GET",
        async:false,
        url: BASESITE+"/serviceTime1.jsp",
        data:   "t=" + Math.random().toString(),
        success: function(msg){
            time = new Date(msg);
        }
    });
    return time;
}
