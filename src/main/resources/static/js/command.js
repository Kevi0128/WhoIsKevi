$(function() {
    //回车输入绑定
    commandBind();
    //暂时不知道是什么的绑定
    bind();
    //定时任务
    timeTask();
});

function activeCMD(e)
{
    if(e.target.id=="main")
    {
        setFocus("command");
    }
}

function bind(){
    document.onmouseup = document.ondbclick= function(){
        var txt;
        if(document.selection){
            txt = document.selection.createRange().text
        }else{
            txt = window.getSelection()+'';
        }
        if(txt){
            copyIn.value=txt;
            copyIn.select();
            document.execCommand('Copy');
        }
    }
}

function commandBind(){
    $('#command').bind('keydown',function(event){
        if(event.keyCode == "13")
        {
            //构建指令回显
            //构建一个div
            var d = $('<div>');
            //构建提示
            var tip = '<span>kevi@(<span>'+ (new Date()).format("yyyy/MM/dd HH:mm:ss") +'</span>):~#</span>';
            d.append(tip);
            var s=$('<span>');
            var value=$('#command').val();
            s.text(" " + value);
            d.append(s)
            $("#result").append(d);
            $('#command').val("");
            if(value.toLowerCase()=="cls")
            {
                $("#result").empty();
                return false;
            }
            if(lastCMD.length==0 || lastCMD[0]!=value)
            {
                lastCMD.unshift(value);
                last=0;
            }
            sendCMD(value);

        }else if(event.keyCode == "38"){
            if(last<=lastCMD.length-1)
            {
                $('#command').val(lastCMD[last]);
                last++;
            }
            setTimeout('setFocus("command");',300);
        }else if(event.keyCode == "40"){
            if(last>0)
            {
                last--;
                $('#command').val(lastCMD[last]);
            }
        }
    });
}

var lastCMD=[];
var last=1;

var setFocus=function(id){
    var t=$("#"+id).val();
    $("#"+id).val("").focus().val(t);
}

function sendCMD(code)
{
    $.ajax({
        type:"POST",
        url: "cmd/code",
        // async:false,
        data:{
            // "type":cmd[0].toLowerCase(),
            "code":code
        },
        dataType:"json",
        success: function (res) {
            //返回消息处理
            //构建一个div
            var d = $('<div>');
            //构建提示
            var tip = '<span>kevi@(<span>'+ (new Date()).format("yyyy/MM/dd HH:mm:ss") +'</span>):~#</span>';
            d.append(tip);
            //具体返回内容
            var s = $('<span class="tipOutRet">');
            s.html(res.code);
            d.append(s);
            $("#result").append(d);
        },
        error: function (res) {
            //todo 详细的错误处理
            return res;
        }
    });
}

function timeTask(){
    //每秒刷新
    window.setInterval(alertTime, 1000);
}

function alertTime(){
    var time = (new Date()).format("yyyy/MM/dd HH:mm:ss");
    $("#time").text(time);
}

Date.prototype.format = function(fmt){
    var o = {"M+": this.getMonth()+1,                 //月份
        "d+": this.getDate(),                    //日
        "H+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth()+3)/3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt)){
        fmt=fmt.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    for(var k in o){
        if(new RegExp("("+ k +")").test(fmt)){
            fmt= fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
}