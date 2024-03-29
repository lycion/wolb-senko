
/********************************************************************
Name   : ready
Desc   :
Param  :
********************************************************************/
$(document).ready(function(){

	console.log("appUserStatus");

	$(".tabmenu>li").eq(3).addClass("active");

	var now = new Date();
	$("#monthfrom").val(getStartDate(365));
	$("#monthto").val($.datepicker.formatDate($.datepicker.ATOM, new Date()));
	$("#monthfrom").datepicker({
		 changeMonth: true,
		 onSelect: function(dateText , inst){
			var dateArr = dateText.split("/");
			year = dateArr[2];
			month = dateArr[0];
			day = dateArr[1];
		}
	});
	$("#monthto").datepicker({
		changeMonth: true,
		onSelect: function(dateText , inst){
			var dateArr = dateText.split("/");
			year = dateArr[2];
			month = dateArr[0];
			day = dateArr[1];
		}
	});

    $("#userStatusBtn").click(function(){
    	if(!dayDiffCheck($('#monthfrom').val(),$('#monthto').val(),'-')){
    		return false ;
    	};
    	if(!dateDiffDay($('#monthfrom').val(),$('#monthto').val(),'-',365)){
    		return false ;
    	};
    	getGrapData();
    });

	getGrapData();
});

function getStartDate(days) {

	var time = (1000 * 3600 * 24) * days;
	var endDate = new Date();
	var diff = Math.abs(endDate.getTime() - time);
	var date = diff instanceof Date ? diff : new Date(diff);

	var year = date.getFullYear();
	var month = (1 + date.getMonth());
	month = month >= 10 ? month : '0' + month;
	var day = date.getDate();
	day = day >= 10 ? day : '0' + day;
	return  year + '-' + month + '-' + day;

}

/********************************************************************
Name   : 그래프 데이터 조회
Desc   :
Param  :
********************************************************************/
function getGrapData(){

	$.ajax(
		{async : true
		, url: '/app/getAppGrape'
		, dataType: 'JSON'
		, type: 'POST'
		, data: {
			R_MONTHFROM : $('#monthfrom').val().replace(/-/g,''),
			R_MONTHTO : 	$('#monthto').val().replace(/-/g,'')
		}
		, success: function(data) {
			if(data != null){
				if(data.osList != null){
					if(null != data.osList[0]){
						setHiChart(data);
					} else {
						grapeRemove('#containers');
					}
				}
				if(data.deviceList != null){
					if(0 < data.deviceList.length){
						setHiChart2(data);
					} else {
						grapeRemove('#containers2');
					}
				}
				if(data.ageList != null){
					if(null !=  data.ageList[0]){
						setHiChart3(data);
					} else {
						grapeRemove('#containers3');
					}
				}
			}
		}
		,complete : function(data) {
		}
		, error: function(xhr, ajaxOptions, thrownError) {

		}
	});
}




/********************************************************************
Name   : OS 별 그래프
Desc   :
Param  :
********************************************************************/
function setHiChart(data){
	var categoriesdata = [];
	var seriesdata = [];

	$('#containers').highcharts({
		  chart: {
		    plotBackgroundColor: null,
		    plotBorderWidth: null,
		    plotShadow: false,
		    type: 'pie'
		  },
		  title: {
		    text: 'OS별 App Download 통계'
		  },
		  tooltip: {
		    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		  },
		  plotOptions: {
		    pie: {
		      allowPointSelect: true,
		      cursor: 'pointer',
		      dataLabels: {
		        enabled: true
		      },
		      showInLegend: true
		    }
		  },
		  series: [{
		    name: '기기비율:',
		    colorByPoint: true,
		    data: [{
		      name: 'IOS [' + data.osList[0].IOSCNT + ']건',
		      y: data.osList[0].IOSCNT,
		      sliced: true,
		      selected: true
		    }, {
		      name: 'ANDROID [' + data.osList[0].ANDROIDCNT + ']건',
		      y: data.osList[0].ANDROIDCNT,
		      sliced: true,
		      selected: true
		    }]
		  }]
		});
}

/********************************************************************
Name   : 기기별가입통계 그래프
Desc   :
Param  :
********************************************************************/
function setHiChart2(data){
	var categoriesdata = [];
	var seriesdata = [];
	for(var i=0;i <data.deviceList.length ; i++){
		categoriesdata.push(data.deviceList[i].MODEL);
		seriesdata.push(data.deviceList[i].CNT);
	}

	$('#containers2').highcharts({
	    chart: {
	        type: 'bar'
	    },
	    title: {
	        text: '기기별 가입통계'
	    },
	    subtitle: {
	        text: ''
	    },
	    xAxis: {
	        categories:categoriesdata,
	        title: {
	            text: null
	        }
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: 'Population (명)',
	            align: 'high'
	        },
	        labels: {
	            overflow: 'justify'
	        }
	    },
	    tooltip: {
	        valueSuffix: ' 명'
	    },
	    plotOptions: {
	        bar: {
	            dataLabels: {
	                enabled: true
	            }
	        }
	    },
	    legend: {
	        layout: 'vertical',
	        align: 'right',
	        verticalAlign: 'top',
	        x: -40,
	        y: 80,
	        floating: true,
	        borderWidth: 1,
	        backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),
	        shadow: true
	    },
	    credits: {
	        enabled: false
	    },
	    series: [{
	        name: '기기별',
	        data: seriesdata
	    }]
	});
}


/********************************************************************
Name   : 연령대별 기기 사용  통계
Desc   :
Param  :
********************************************************************/
function setHiChart3(data){

	var age = [];
	var jsonArray = new Array();

	if(data.ageList[0].AGE_10 != null && data.ageList[0].AGE_10 != 0){
		var json10 = new Object();
		age.push(data.ageList[0].AGE_10);
		json10.type = 'column';
		json10.name = '10대';
		json10.data = age;
		json10.color = Highcharts.getOptions().colors[0];
		jsonArray.push(json10);
		age = [];
	}

	if(data.ageList[0].AGE_20 != null && data.ageList[0].AGE_20 != 0){
		var json20 = new Object();
		age.push(data.ageList[0].AGE_20);
		json20.type = 'column';
		json20.name = '20대';
		json20.data = age;
		json20.color = Highcharts.getOptions().colors[1];
		jsonArray.push(json20);
		age = [];
	}

	if(data.ageList[0].AGE_30 != null && data.ageList[0].AGE_30 != 0){
		var json30 = new Object();
		age.push(data.ageList[0].AGE_30);
		json30.type = 'column';
		json30.name = '30대';
		json30.data = age;
		json30.color = Highcharts.getOptions().colors[2];
		jsonArray.push(json30);
		age = [];
	}

	if(data.ageList[0].AGE_40 != null && data.ageList[0].AGE_40 != 0){
		var json40 = new Object();
		age.push(data.ageList[0].AGE_40);
		json40.type = 'column';
		json40.name = '40대';
		json40.data = age;
		json40.color = Highcharts.getOptions().colors[3];
		jsonArray.push(json40);
		age = [];
	}

	if(data.ageList[0].AGE_50 != null && data.ageList[0].AGE_50 != 0){
		var json50 = new Object();
		age.push(data.ageList[0].AGE_50);
		json50.type = 'column';
		json50.name = '50대';
		json50.data = age;
		json50.color = Highcharts.getOptions().colors[4];
		jsonArray.push(json50);
		age = [];
	}

	if(data.ageList[0].AGE_60 != null && data.ageList[0].AGE_60 != 0){
		var json60 = new Object();
		age.push(data.ageList[0].AGE_60);
		json60.type = 'column';
		json60.name = '60대';
		json60.data = age;
		json60.color = Highcharts.getOptions().colors[5];
		jsonArray.push(json60);
		age = [];
	}

	if(data.ageList[0].AGE_70 != null && data.ageList[0].AGE_70 != 0){
		var json70 = new Object();
		age.push(data.ageList[0].AGE_70);
		json70.type = 'column';
		json70.name = '70대';
		json70.data = age;
		json70.color = Highcharts.getOptions().colors[6];
		jsonArray.push(json70);
		age = [];
	}

	if(data.ageList[0].AGE_80 != null && data.ageList[0].AGE_80 != 0){
		var json80 = new Object();
		age.push(data.ageList[0].AGE_80);
		json80.type = 'column';
		json80.name = '80대';
		json80.data = age;
		json80.color = Highcharts.getOptions().colors[7];
		jsonArray.push(json80);
		age = [];
	}

	if(data.ageList[0].AGE_90 != null && data.ageList[0].AGE_90 != 0){
		var json90 = new Object();
		age.push(data.ageList[0].AGE_90);
		json90.type = 'column';
		json90.name = '90대';
		json90.data = age;
		json90.color = Highcharts.getOptions().colors[8];
		jsonArray.push(json90);
		age = [];
	}

	$('#containers3').highcharts({
		chart: {
	        type: 'column'
	    },
		 title: {
	            text: '연령대별 기기사용 통계'
	        },
	        xAxis: {
	            categories:['연령대']
	        },
	        yAxis:{
	        	title:{
	        		text : ""
	        	}
	        },
	        labels: {
	            items: [{
	                html: '',
	                style: {
	                    left: '50px',
	                    top: '18px',
	                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'black'
	                }
	            }]
	        },
	        series: jsonArray
	    });

}




