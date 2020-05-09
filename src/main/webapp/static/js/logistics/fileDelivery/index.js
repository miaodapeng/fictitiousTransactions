
/**
 * 导出文件寄送列表
 * @returns
 */
function exportFileDeliverylist(){
	checkLogin();
	location.href = page_url + '/report/logistics/exportfiledeliverylist.do?' + $("#search").serialize();
}