///////////////////////////////////////////////////////////////////////////////
//              Z-Blog
// 作    者:    朱煊(zx.asd)
// 版权所有:    RainbowSoft Studio
// 技术支持:    rainbowsoft@163.com
// 程序名称:    
// 程序版本:    
// 单元名称:    common.js
// 开始时间:    2004.07.25
// 最后修改:    
// 备    注:    全局脚本
///////////////////////////////////////////////////////////////////////////////




//*********************************************************
// 目的：    加载样式表
// 输入：    无
// 返回：    无
//*********************************************************
function LoadActiveStyleSheet(){

	var title=GetCookie("sk");
	var a;

	if (title) {

		a = document.getElementsByTagName("link")[0];

		a.href=bloghost+"style/"+title+".css";

	}

}
//*********************************************************




//*********************************************************
// 目的：    设置样式表
// 输入：    title
// 返回：    无
//*********************************************************
function SetActiveStyleSheet(title){

	var a;

	if (title) {

		{
			SetCookie("sk",title,365);
		}
		a = document.getElementsByTagName("link")[0];

		a.href=bloghost+"style/"+title+".css";

	}

}
//*********************************************************




//*********************************************************
// 目的：    设置Cookie
// 输入：    sName, sValue,iExpireDays
// 返回：    无
//*********************************************************
function SetCookie(sName, sValue,iExpireDays) {
	var path=(typeof(cookiespath)=="undefined") ? "/":cookiespath;
	if (iExpireDays){
		var dExpire = new Date();
		dExpire.setTime(dExpire.getTime()+parseInt(iExpireDays*24*60*60*1000));
		document.cookie = sName + "=" + escape(sValue) + "; expires=" + dExpire.toGMTString()+ "; path="+path;
	}
	else{
		document.cookie = sName + "=" + escape(sValue)+ "; path="+path;
	}
}
//*********************************************************




//*********************************************************
// 目的：    返回Cookie
// 输入：    Name
// 返回：    Cookie值
//*********************************************************
function GetCookie(sName) {

	var arr = document.cookie.match(new RegExp("(^| )"+sName+"=([^;]*)(;|$)"));
	if(arr !=null) {
		return unescape(arr[2]);
	};
	return null;

}
//*********************************************************




//*********************************************************
// 目的：    验证信息
// 输入：    无
// 返回：    无
//*********************************************************

function VerifyMessage() {

	var strName=$("#inpName").val();
	var strEmail=$("#inpEmail").val();
	var strHomePage=$("#inpHomePage").val();
	var strArticle=$("#txaArticle").length>0?$("#txaArticle").val():$("#inpArticle").val();
	var strFormAction=$("#frmSumbit").attr("action");
	var intRevID=$("#inpRevID").val()==""?0:$("#inpRevID").val();

	if(strName==""){
		alert(str01);
		return false;
	}
	else{
		re = new RegExp("^[\.\_A-Za-z0-9\u4e00-\u9fa5]+$");
		if (!re.test(strName)){
			alert(str02);
			return false;
		}
	}

	if(strEmail==""){
		//alert(str01);
		//return false;
	}
	else{
		re = new RegExp("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");
		if (!re.test(strEmail)){
			alert(str02);
			return false;
		}
	}

	if(typeof(strArticle)=="undefined"){
		alert(str03);
		return false;
	}

	if(typeof(strArticle)=="string"){
		if(strArticle==""){
			alert(str03);
			return false;
		}
		if(strArticle.length>intMaxLen)
		{
			alert(str03);
			return false;
		}
	}

	if($("#txaArticle").length>0){$("#inpArticle").val(strArticle);}

	var bolRemember=document.getElementById("chkRemember").checked;

	if(bolRemember==true){
		SaveRememberInfo();
	}
	else{
		SetCookie("chkRemember",bolRemember,365);
	}


	//ajax comment begin
	var strSubmit=$("#frmSumbit :submit").val();
	$("#frmSumbit :submit").val("Waiting...").attr("disabled","disabled").addClass("loading");

	$.post(strFormAction,
		{
		"inpAjax":true,
		"inpID":$("#inpId").val(),
		"inpVerify":($("#inpVerify").length>0?$("#inpVerify").val():""),
		"inpEmail":strEmail,
		"inpName":strName,
		"inpArticle":strArticle,
		"inpHomePage":strHomePage,
		"inpRevID":intRevID
		},
		function(data){
			var s =data;
			if((s.search("faultCode")>0)&&(s.search("faultString")>0))
			{
				alert(s.match("<string>.+?</string>")[0].replace("<string>","").replace("</string>",""))
			}
			else{
				var i=Math.round(Math.random()*1000);
				var s =data;
				if(intRevID==0){
					$(s).insertBefore("#AjaxCommentEnd");
				}else{
					$(s).insertBefore("#AjaxCommentEnd"+intRevID);
					window.location="#cmt"+intRevID
				}
				$("#divAjaxComment"+i).fadeIn("slow");
				$("#txaArticle").val("");
			}
			if($("#inpVerify").length>0){
				$("#inpVerify").val("");
				var objImageValid=$("img[src^='"+bloghost+"zb_system/function/c_validcode.asp?name=commentvalid']");
				objImageValid.attr("src",bloghost+"zb_system/function/c_validcode.asp?name=commentvalid"+"&random="+Math.random());
			}

			$("#frmSumbit :submit").removeClass("loading");
			$("#frmSumbit :submit").removeAttr("disabled");
			$("#frmSumbit :submit").val(strSubmit);

		}
	);

	return false;
	//ajax comment end

}
//*********************************************************




//*********************************************************
// 目的：    加载信息
// 输入：    无
// 返回：    无
//*********************************************************
function LoadRememberInfo() {

	var strName=GetCookie("inpName");
	var strEmail=GetCookie("inpEmail");
	var strHomePage=GetCookie("inpHomePage");
	var bolRemember=GetCookie("chkRemember");

	if(bolRemember=="true"){

		if(strName){document.getElementById("inpName").value=strName;};
		if(strEmail){document.getElementById("inpEmail").value=strEmail;};
		if(strHomePage){document.getElementById("inpHomePage").value=strHomePage;};
		if(bolRemember){document.getElementById("chkRemember").checked=bolRemember;};

	}

	if(GetCookie("username")){
		document.getElementById("inpName").value=unescape(GetCookie("username"));
	}

}
//*********************************************************




//*********************************************************
// 目的：    保存信息
// 输入：    无
// 返回：    无
//*********************************************************
function SaveRememberInfo() {

	var strName=document.getElementById("inpName").value;
	var strEmail=document.getElementById("inpEmail").value;
	var strHomePage=document.getElementById("inpHomePage").value;
	var bolRemember=document.getElementById("chkRemember").checked;


	SetCookie("inpName",strName,365);
	SetCookie("inpEmail",strEmail,365);
	SetCookie("inpHomePage",strHomePage,365);
	SetCookie("chkRemember",bolRemember,365);

}
//*********************************************************





//*********************************************************
// 目的：    输出UBB
// 输入：    无
// 返回：    无
//*********************************************************
function ExportUbbFrame() {

	return false;

	if(!objActive){objActive="txaArticle"};

	document.write("<p id=\"UbbFrame\" style=\"display:none;\"></p>");

	document.write("<p>");

	document.write("<a alt=\"\" onmousedown=\"InsertText(objActive,ReplaceText(objActive,'[URL]','[/URL]'),true);\" style=\"padding:2px;cursor:pointer;\">[URL]</a>  ");
	document.write("<a alt=\"\" onmousedown=\"InsertText(objActive,ReplaceText(objActive,'[URL=http://]','[/URL]'),true);\" style=\"padding:2px;cursor:pointer;\">[URL2]</a>  ");
	document.write("<a alt=\"\" onmousedown=\"InsertText(objActive,ReplaceText(objActive,'[EMAIL]','[/EMAIL]'),true);\" style=\"padding:2px;cursor:pointer;\">[EMAIL]</a>  ");
	document.write("<a alt=\"\" onmousedown=\"InsertText(objActive,ReplaceText(objActive,'[EMAIL=@]','[/EMAIL]'),true);\" style=\"padding:2px;cursor:pointer;\">[EMAIL2]</a>  ");
	document.write("<a alt=\"\" onmousedown=\"InsertText(objActive,ReplaceText(objActive,'[B]','[/B]'),true);\" style=\"padding:2px;cursor:pointer;\">[B]</a>  ");
	document.write("<a alt=\"\" onmousedown=\"InsertText(objActive,ReplaceText(objActive,'[I]','[/I]'),true);\" style=\"padding:2px;cursor:pointer;\">[I]</a>  ");
	document.write("<a alt=\"\" onmousedown=\"InsertText(objActive,ReplaceText(objActive,'[U]','[/U]'),true);\" style=\"padding:2px;cursor:pointer;\">[U]</a>  ");
	document.write("<a alt=\"\" onmousedown=\"InsertText(objActive,ReplaceText(objActive,'[S]','[/S]'),true);\" style=\"padding:2px;cursor:pointer;\">[S]</a>  ");
	document.write("<a alt=\"\" onmousedown=\"InsertText(objActive,ReplaceText(objActive,'[QUOTE]','[/QUOTE]'),true);\" style=\"padding:2px;cursor:pointer;\">[QUOTE]</a>  ");

	document.write("<u><a style=\"cursor:pointer;text-align:right;\" onclick=\"InsertUbbFace();if(document.getElementById('UbbFrame').style.display=='none'){document.getElementById('UbbFrame').style.display='block';}else{document.getElementById('UbbFrame').style.display='none'};this.style.display='none'\">"+str06+"</a></u> ");

	document.write("</p>");
}
//*********************************************************




//*********************************************************
// 目的：    插入表情图片HTML代码
// 输入：    无
// 返回：    无
//*********************************************************
function InsertUbbFace() {

	if(!document.getElementById("UbbFrame").innerHTML && strFaceName){

		var aryFileName="";
		var strFileName="";
		var strFaceHtml="";

		aryFileName = strFaceName.split("|");

		for (var i=0;i<aryFileName.length;i++)
		{
			strFileName = aryFileName[i];
			strFaceHtml=strFaceHtml + "<img src=\""+bloghost+"zb_system/image/face/"+strFileName+"."+strFaceType+"\" title=\""+strFileName+"\" alt=\""+strFileName+"\" width=\""+strFaceSize+"\" height=\""+strFaceSize+"\" onclick=\"InsertText(objActive,'[F]'+this.alt+'[/F]',false);\" style=\"padding:2px;cursor:pointer;\">";
		}
		document.getElementById("UbbFrame").innerHTML=strFaceHtml;
	}

}
//*********************************************************




//*********************************************************
// 目的：    自动插入并替换
// 输入：    无
// 返回：    无
//*********************************************************
var objActive;
function GetActiveText(objHTML) {
	objActive=objHTML;
	if(document.selection){
		var obj=document.getElementById(objHTML);
		obj.currPos = document.selection.createRange().duplicate();
	}
}

function InsertText(objHTML,strText,bolReplace) {
	if(strText==""){return("")}
	if(!document.getElementById(objHTML)){return false;}
	var obj=document.getElementById(objHTML);
	if(document.selection){
		if (obj.currPos){
			if(bolReplace && (obj.value=="")){
				obj.currPos.text=strText;
			}
			else{
				obj.currPos.text+=strText;
			}
		}
		else{
			obj.value+=strText;
		}
	}
	else{
		if(bolReplace){
			obj.value=obj.value.slice(0,obj.selectionStart) + strText + obj.value.slice(obj.selectionEnd,obj.value.length);
		}
		else{
			obj.value=obj.value.slice(0,obj.selectionStart) + strText + obj.value.slice(obj.selectionStart,obj.value.length);
		}
	}
	obj.focus();
}

function ReplaceText(objHTML,strPrevious,strNext) {
	var obj=document.getElementById(objHTML);
	var strText;
	if(document.selection && document.selection.type == "Text"){
		if (obj.currPos){
			var range = document.selection.createRange();
			range.text = strPrevious + range.text + strNext;
			return("");
		}
		else{
			strText=strPrevious + strNext;
			return(strText);
		}
	}
	else{
		if(obj.selectionStart || obj.selectionEnd){
			strText=strPrevious + obj.value.slice(obj.selectionStart,obj.selectionEnd) + strNext;
			return(strText);
		}
		else{
			strText=strPrevious + strNext;
			return(strText);
		}
	}
}
//*********************************************************




//*********************************************************
// 目的：    ShowMedia By UBB
// 输入：    无
// 返回：    无
//*********************************************************
function ShowMedia(objHTML,strURL,strType,intWidth,intHeight) {
	var strMedia="";
	var blnShow = false;
	var objMedia=objHTML;

	if(objMedia.innerHTML){blnShow = true};

	var re = new RegExp("\.[a-z0-9]+$","ig");

	var strExtend = re.exec(strURL);
	strExtend="|"+String(strExtend).toLowerCase()+"|";

	if(strType=="AUTO"){
		if(String("|.swf|").indexOf(strExtend)>=0){strType="SWF"};
		if(String("|.mov|.qt|").indexOf(strExtend)>=0){strType="QT"};
		if(String("|.wmv|.wmp|.wm|.avi|.mp4|.mpg|.mpeg|.m3u|.pls|.wvx|.wax|.wmx|").indexOf(strExtend)>=0){strType="WMV"};
		if(String("|.asf|.wma|.asx|.wav|.mp3|.mpa|.mp2|.m1a|.m2a|.aac|").indexOf(strExtend)>=0){strType="WMA"};
		if(String("|.rm|.ram|.rmvb|.rpm|.amr|.3gp|.3gpp|.3g2|.3gp2|.rt|.rp|.smi|.smil|").indexOf(strExtend)>=0){strType="RM"};
		if(String("|.ra|").indexOf(strExtend)>=0){strType="RA"};
	}

	if(blnShow){
		objMedia.innerHTML = strMedia;
	}
	else{
		switch(strType){
			case "SWF":
				strMedia="<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0\" width=\""+intWidth+"\" height=\""+intHeight+"\"><param name=\"movie\" value=\""+strURL+"\"><param name=\"quality\" value=\"high\"><param name=\"play\" value=\"true\"><embed src=\""+strURL+"\" quality=\"high\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" type=\"application/x-shockwave-flash\" width=\""+intWidth+"\" height=\""+intHeight+"\" play=\"true\"></embed></object>";
				break;
			case "QT":
				strMedia="<object classid=\"clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B\" codebase=\"http://www.apple.com/qtactivex/qtplugin.cab\" width=\""+intWidth+"\" height=\""+intHeight+"\" ><param name=\"src\" value=\""+strURL+"\" ><param name=\"autoplay\" value=\"true\" ><embed  src=\"qtmimetype.pntg\" type=\"image/x-macpaint\"pluginspage=\"http://www.apple.com/quicktime/download\" qtsrc=\""+strURL+"\" width=\""+intHeight+"\" height=\""+intHeight+"\" autoplay=\"true\" ></embed></object>";
				break;
			case "WMV":
				strMedia="<object classid=\"clsid:22D6F312-B0F6-11D0-94AB-0080C74C7E95\" width=\""+intWidth+"\" height=\""+intHeight+"\"><param name=\"ShowStatusBar\" value=\"-1\"><param name=\"AutoStart\" value=\"true\"><param name=\"Filename\" value=\""+strURL+"\"><embed type=\"application/x-mplayer2\" pluginspage=\"http://www.microsoft.com/Windows/MediaPlayer/\" src=\""+strURL+"\" autostart=\"true\" width=\""+intWidth+"\" height=\""+intHeight+"\"></embed></object>";
				break;
			case "WMA":
				strMedia="<object classid=\"clsid:22D6F312-B0F6-11D0-94AB-0080C74C7E95\" height=\"68\" width=\"350\"><param name=\"ShowStatusBar\" value=\"-1\"><param name=\"AutoStart\" value=\"true\"><param name=\"Filename\" value=\""+strURL+"\"><embed type=\"application/x-mplayer2\" pluginspage=\"http://www.microsoft.com/Windows/MediaPlayer/\" src=\""+strURL+"\" autostart=\"true\" width=\"350\" height=\"45\"></embed></object>";
				break;
			case "RM":
				strMedia="<object classid=\"clsid:CFCDAA03-8BE4-11CF-B84B-0020AFBBCCFA\" width=\""+intWidth+"\" height=\""+intWidth+"\"><param name=\"src\" value=\""+strURL+"\"><param name=\"controls\" value=\"imagewindow\"><param name=\"console\" value=\"one\"><param name=\"AutoStart\" value=\"true\"><embed src=\""+strURL+"\" width=\""+intWidth+"\" height=\""+intWidth+"\" type=\"audio/x-pn-realaudio-plugin\" nojava=\"true\" controls=\"imagewindow,ControlPanel,StatusBar\" console=\"one\" autostart=\"true\"></object>";
				break;
			case "RA":
				strMedia="<object classid=\"clsid:CFCDAA03-8BE4-11CF-B84B-0020AFBBCCFA\" width=\"350\" height=\"36\"><param name=\"src\" value=\""+strURL+"\"><param name=\"controls\" value=\"ControlPanel\"><param name=\"console\" value=\"one\"><param name=\"AutoStart\" value=\"true\"><embed src=\""+strURL+"\" type=\"audio/x-pn-realaudio-plugin\" nojava=\"true\" controls=\"ControlPanel,StatusBar\" console=\"one\" autostart=\"true\" width=\"350\" height=\"36\"></object>";
		}
		objMedia.innerHTML = strMedia;
	}
}
//*********************************************************




//*********************************************************
// 目的：    InsertQuote To txaArticle
// 输入：    无
// 返回：    无
//*********************************************************
function InsertQuote(strName,strText) {
	if(!objActive){objActive="txaArticle"};

	var re;
	re=new RegExp("<br/?>","ig");
	strText=strText.replace(re, "\n");
	re=new RegExp("<[^>]*>","ig");
	strText=strText.replace(re, "");

	InsertText(objActive,"[QUOTE="+strName+"]"+strText+"[/QUOTE]");
}
//*********************************************************





//*********************************************************
// 目的：    回复留言
// 输入：    无
// 返回：    无
//*********************************************************
function RevertComment(i) {
	if($("#inpRevID").length==0){
		intRevID=i;
	}else{
		$("#inpRevID").val(i);
	}
	
	$("#cancel-reply").show().bind("click", function(){ if($("#inpRevID").length==0){intRevID=0;}else{$("#inpRevID").val(0);};$(this).hide();window.location.hash="#comment";return false; });

	window.location.hash="#comment";
}
//*********************************************************





//*********************************************************
// 目的：    
//*********************************************************
function LoadFunction(name){


	strBatchInculde+="mod_"+name+"="+name+","

}
//*********************************************************




//*********************************************************
// 目的：    
//*********************************************************
function LoadViewCount(id){

	strBatchView+="spn"+id+"="+id+","

}
//*********************************************************



//*********************************************************
// 目的：    
//*********************************************************
function AddViewCount(id){

	strBatchCount+="spn"+id+"="+id+","

}
//*********************************************************



//*********************************************************
// 目的：    
//*********************************************************
function GetComments(logid,page){

	 $('span.commentspage').html("Waiting...");

	$.get(bloghost+"zb_system/cmd.asp?act=CommentGet&logid="+logid+"&page="+page, function(data){
	  $('#AjaxCommentBegin').nextUntil('#AjaxCommentEnd').remove();
	  $('#AjaxCommentBegin').after(data);
	});

}
//*********************************************************



//*********************************************************
// 目的：    
//*********************************************************
function sidebar(){
	this.list=[];
	this.add=function(d){
		this.list.push(d);
	}
	this.execute=function(){
		for ( var i = 0 ; i < this.list.length ; i++ ){
			this.list[i]();
		}
	}
}
var sidebarloaded=new sidebar();
//*********************************************************



//*********************************************************
// 目的：  预留空函数,留给主题用  
//*********************************************************
function BatchComplete(){
}
//*********************************************************



//*********************************************************
// 目的：  预留空函数,留给主题用  
//*********************************************************
function AutoinfoComplete(){
}
//*********************************************************