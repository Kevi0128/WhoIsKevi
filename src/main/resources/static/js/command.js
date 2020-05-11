$(function() {
    //回车输入绑定
    commandBind();
    //暂时不知道是什么的绑定
    bind();
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
            var p=$('<p class="resp">');
            var value=$('#command').val();
            p.text('$host:>'+value);
            $("#result").append(p);
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
            var cmd=value.split(" ");
            var resp=sendCMD(cmd);
            p=$('<p class="resp">');
            p.html(resp);
            $("#result").append(p);
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

function sendCMD(cmd)
{
    //todo ajax修改
    var htmlobj=$.ajax({url:"cmd/code",type:"POST",async:false,data:{"type":cmd[0].toLowerCase(),"code":JSON.stringify(cmd)}});
    var data=GetJSON(htmlobj.responseText);
    if(data.stateOK=='OK')
    {
        data=data.data;
        if(data.url!=undefined)
        {
            var htmlobj=$.ajax({url:data.url,type:data.method,async:false,data:data.data});
            return htmlobj.responseText;
        }
        if(data.type!=undefined)
        {
            if(data.type=="table")
            {
                return makeTable(data.dataHead,data.dataRow);
            }
        }
        return data;
    }else{
        return "无正确响应";
    }
}

function makeTable(dataHead,dataRow)
{
    if(typeof(dataHead)!="object" || typeof(dataRow)!="object")
        return false;
    var table=$('<table>');
    var tr=$('<tr>');
    for(var i=0;i<dataHead.length;i++)
    {
        var th=$('<th>');
        th.html(dataHead[i]);
        tr.append(th);
    }
    table.append(tr);
    for(var i=0;i<dataRow.length;i++)
    {
        tr=$('<tr>');
        for(var j=0;j<dataRow[i].length;j++)
        {
            var td=$("<td>");
            td.html(dataRow[i][j]);
            tr.append(td);
        }
        table.append(tr);
    }
    return table;
}

function GetJSON(string)
{
    if(string.indexOf("stateOK")>=0)
    {
        var res=eval("("+string+")");
        return res;
    }else{
        return {stateOK:"NO",data:string,msg:"通讯不正常"};
    }
}