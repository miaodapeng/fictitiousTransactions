/**
 * 导出采购售后列表
 * @returns
 */
function exportBuyorderAfterSalesList(){
	location.href = page_url + '/report/buy/exportbuyorderaftersaleslist.do?' + $("#search").serialize();
}