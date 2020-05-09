
function exportAccountPeriod(num){
	checkLogin();
	if(num == 1){
		location.href = page_url + '/finance/accountperiod/export_'+getNowDateStr()+'.do?' + $("#search").serialize();
	}else{
		location.href = page_url + '/finance/accountperiod/exportAccountperiod.do?' + $("#search").serialize();
	}
}

function exportAccountPeriodList(){
	checkLogin();
	location.href = page_url + '/report/finance/exportAccountPeriodList.do?' + $("#search").serialize();
}