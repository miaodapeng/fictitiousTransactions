$(function(){
    var params = window.location.href.split('randomStr=')[1];
    var richTxt = '';

    var richTxtStr = window.localStorage.getItem('operateInfo') || '[]';
    var richTxtArr = JSON.parse(richTxtStr);

    if (params && richTxtArr[params]){
        richTxt = richTxtArr[params].value;
    }

    $('.J-rich-txt').html(richTxt);
})