function exportCustomerAccount(){
	checkLogin();
	location.href = page_url + '/report/finance/exportCustomerAccount.do?' + $("#search").serialize();
}

function exportSupplierAccount(){
	checkLogin();
	location.href = page_url + '/report/finance/exportSupplierAccount.do?' + $("#search").serialize();
}