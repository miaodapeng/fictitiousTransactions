function getYear(){
	var mydate = new Date();
	return mydate.getFullYear();
}

function getTime(){
	var myDate = new Date();
	//获取当前年
	var year=myDate.getFullYear();
	//获取当前月
	var month=myDate.getMonth()+1;
	//获取当前日
	var date=myDate.getDate(); 
	var h=myDate.getHours();       //获取当前小时数(0-23)
	var m=myDate.getMinutes();     //获取当前分钟数(0-59)
	var s=myDate.getSeconds();  

	return year+'-'+p(month)+"-"+p(date)+" "+p(h)+':'+p(m)+":"+p(s);
}

function getLocalTime(){
	var myDate = new Date();
//	myDate.toLocaleTimeString() //yyyy/MM/dd HH:MI:SS
	return myDate.toLocaleTimeString().replace(new RegExp("/","gm"),"-");
}

function getLocalDate(){
	return myDate.toLocaleDateString();//  yyyy/MM/dd
}

function dateAdd(interval, number) {
	var date = new Date();
    switch (interval) {
    case "y ": {
        date.setFullYear(date.getFullYear() + number);
        return date.toLocaleDateString().replace(new RegExp("/","gm"),"-");
        break;
    }
    case "q ": {
        date.setMonth(date.getMonth() + number * 3);
        return date.toLocaleDateString().replace(new RegExp("/","gm"),"-");
        break;
    }
    case "m ": {
        date.setMonth(date.getMonth() + number);
        return date.toLocaleDateString().replace(new RegExp("/","gm"),"-");
        break;
    }
    case "w ": {
        date.setDate(date.getDate() + number * 7);
        return date.toLocaleDateString().replace(new RegExp("/","gm"),"-");
        break;
    }
    case "d ": {
        date.setDate(date.getDate() + number);
        return date.toLocaleDateString().replace(new RegExp("/","gm"),"-");
        break;
    }
    case "h ": {
        date.setHours(date.getHours() + number);
        return date.toLocaleDateString().replace(new RegExp("/","gm"),"-");
        break;
    }
    case "m ": {
        date.setMinutes(date.getMinutes() + number);
        return date.toLocaleDateString().replace(new RegExp("/","gm"),"-");
        break;
    }
    case "s ": {
        date.setSeconds(date.getSeconds() + number);
        return date.toLocaleDateString().replace(new RegExp("/","gm"),"-");
        break;
    }
    default: {
        date.setDate(d.getDate() + number);
        return date.toLocaleDateString().replace(new RegExp("/","gm"),"-");
        break;
    }
    }
}


/*// 加五天.
var newDate = DateAdd("d ", 5, now);
alert(newDate.toLocaleDateString())
// 加两个月.
newDate = DateAdd("m ", 2, now);
alert(newDate.toLocaleDateString())
// 加一年
newDate = DateAdd("y ", 1, now);
alert(newDate.toLocaleDateString())*/