function preview(myDiv) {
	checkLogin();
	$("#btnDiv").hide();
	$("#"+myDiv).printArea();
	$("#btnDiv").show();
};