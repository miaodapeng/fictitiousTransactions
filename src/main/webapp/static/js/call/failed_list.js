$(function(){
});


/**
 * 漏接来电 回拨
 * @param id
 * @param phone
 * @returns
 */
function dialBack(id,phone){
	checkLogin();
	if(id != "" && phone != "" && self.parent.document.getElementById("callFailedId") != undefined){
		self.parent.document.getElementById("callFailedId").val=id;
		callout(phone);
	}
}