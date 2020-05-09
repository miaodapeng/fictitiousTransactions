
$(function(){

    $("#province").select2();
    $("#zone").select2({
        templateSelection:  getZooList($('#zone'))
    });
    $("#city").select2({
        templateSelection:  getCityList($('#city'))
    });

	})

function getCityList(ele) {
    $.ajax({
        url : page_url+"/system/region/getRegionList.do",
        data :{'cityStart':2},
        dataType : 'json',
        async: false,
        success: function(data) {
            $option = "<option value='0'>请输入城市</option>";
            $.each(data.listData,function(i,n){
                $option += "<option value='"+data.listData[i]['regionId']+"'>"+data.listData[i]['regionName']+"</option>";
            });
            $("select[name='city']").html($option);

        }
    })
}

function getZooList(ele) {
    $.ajax({
        url : page_url+"/system/region/getRegionList.do",
        data :{'cityStart':0},
        dataType : 'json',
        async: false,
        success: function(data) {
            $option = "<option value='0'>请输入县区</option>";
            $.each(data.listData,function(i,n){
                $option += "<option value='"+data.listData[i]['regionId']+"'>"+data.listData[i]['regionName']+"</option>";
            });
            $("select[name='zone']").html($option);
        }
    })
}
