// Copyright (c) jiucheng.org
/**
 * 
 * @param sName 键
 * @param sValue 值
 * @param iExpireDays 天数
 */
function setCookie(sName, sValue, iExpireDays) {
	var path = (typeof(cookiespath)=="undefined") ? "/" : cookiespath;
	if(iExpireDays) {
		var dExpire = new Date();
		dExpire.setTime(dExpire.getTime()+parseInt(iExpireDays*24*60*60*1000));
		document.cookie = sName + "=" + escape(sValue) + "; expires=" + dExpire.toGMTString()+ "; path="+path;
	}else {
		document.cookie = sName + "=" + escape(sValue)+ "; path="+path;
	}
}

/**
 * 
 * @param sName 键
 * @returns
 */
function getCookie(sName) {
	var arr = document.cookie.match(new RegExp("(^| )"+sName+"=([^;]*)(;|$)"));
	if(arr != null) {
		return unescape(arr[2]);
	}
	return null;
}
// --------------------- 华丽的分割线 ------------------
function saveReply() {
	var bolRemember = document.getElementById("chkRemember").checked;
	var strName = document.getElementById("inpName").value;
	var strEmail = document.getElementById("inpEmail").value;
	var strHomePage = document.getElementById("inpHomePage").value;
	setCookie("inpName",strName,365);
	setCookie("inpEmail",strEmail,365);
	setCookie("inpHomePage",strHomePage,365);
	setCookie("chkRemember",bolRemember,365);
}
function loadReply() {
	var strName = getCookie("inpName");
	var strEmail = getCookie("inpEmail");
	var strHomePage = getCookie("inpHomePage");
	var bolRemember = getCookie("chkRemember");
	if(bolRemember == "true") {
		if(strName) {
			document.getElementById("inpName").value = strName;
		}
		if(strEmail) {
			document.getElementById("inpEmail").value = strEmail;
		}
		if(strHomePage) {
			document.getElementById("inpHomePage").value = strHomePage;
		}
		if(bolRemember) { 
			document.getElementById("chkRemember").checked = bolRemember;
		}
	}
}