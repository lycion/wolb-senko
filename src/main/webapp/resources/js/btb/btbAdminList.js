/********************************************************************
Name   : ready
Desc   :
Param  :
********************************************************************/
$(document).ready(function(){

	if($("#start_dt").val() == ""){
		$('#start_dt').val(getStartDate(365));
		$('#end_dt').val($.datepicker.formatDate('yy-mm-dd', new Date()));
	}

	$("#start_dt").datepicker({
		 changeMonth: true,
		onSelect: function(dateText , inst){
			var dateArr = dateText.split("/");
			year = dateArr[2];
			month = dateArr[0];
			day = dateArr[1];
		}
	});

	$("#end_dt").datepicker({
		 changeMonth: true,
		onSelect: function(dateText , inst){
			var dateArr = dateText.split("/");
			year = dateArr[2];
			month = dateArr[0];
			day = dateArr[1];
		}
	});

	$("#keyword").keypress(function(e) {
	   if(e.keyCode==13) {
		    if(!dayDiffCheck($('#start_dt').val(),$('#end_dt').val(),'-')){
			    return false ;
		    };
		   grid.fn_SearchGrid();
	   }
	});


	//초기화 그리드
	grid.fn_initLoadGrid();
	grid.fn_SearchGrid();


    $("#btn_sch").click(function(){
	    if(!dayDiffCheck($('#start_dt').val(),$('#end_dt').val(),'-')){
		    return false ;
	    };
    	grid.fn_SearchGrid();
    });


});


var grid = {
		fn_initLoadGrid:function(){
			$("#btb_admin_list").jqGrid('GridUnload');
			$("#btb_admin_list").jqGrid({
				caption:"B2B 관리자 목록"
				,datatype 	    : 'clientside'
				,height:500
				,width:500
				,rowNum:10
				,rowList:[10,20,30]
				,rownumbers : false
				,viewrecords: true
				,colNames:["번호","아이디","관리자명","사이트명","직급","휴대전화","현재상태","등록일"]
				,colModel:[
			          {name:"ROWNUM", 	    index:"ROWNUM" , 	sortable:true,	width:30,		align:'center'},
			          {name:"ADM_EMAIL", 	index:"ADM_EMAIL", 	sortable:true,	width:30,		align:'center'},
			          {name:"ADM_NM", 	    index:"ADM_NM", 	sortable:true,	width:30,		align:'center'},
			          {name:"BTBS_NM", 	    index:"BTBS_NM", 	sortable:false,	width:30,		align:'center'},
			          {name:"ADM_RANK", 	index:"ADM_RANK", 	sortable:true,	width:30,		align:'center'},
			          {name:"ADM_HP", 	    index:"ADM_HP", 	sortable:true,	width:30,		align:'center'},
			          {name:"CODENAME", 	index:"CODENAME", 	sortable:true,	width:30,		align:'center'},
			          {name:"REG_DTTM1", 	index:"REG_DTTM1", 	sortable:true,	width:30,		align:'center'}
			          ]
				,pager:"#pager"
		        ,sortname     : 'REG_DTTM1'     //초기화 될때 sort할 컬럼을 지정
		        ,sortorder    : 'desc'       //초기화정렬방법
				,jsonReader:{ repeatitems:false
					, id:"ROWNUM"
					, root: "rows"
					, page : "page"
					, total : "total"
					,records : "records" }

				,gridComplete : function(data,status) {
				}
				,recordpos:'right'
				,gridview: true
				,emptyrecords :" 등록된 데이터가 없습니다."
		    	,loadError:function(xhr, status, error) {
		    	}
		    	,onSelectRow   : function(rowid, status, e) {
			     }

			}).trigger('reloadGrid');

		}
		,fn_SearchGrid:function(){

			if($('#start_dt').val()==''){
				alert('등록일을 입력하세요.');
				$('#start_dt').focus();
			}else if ($('#end_dt').val() == '') {
				alert('등록일을 입력하세요.');
				$('#end_dt').focus();
			}else{
				var sDate = $('#start_dt').val();
				var eDate = $('#end_dt').val();
				if($("#nCal").prop("checked") == true){
					sDate = "";
					eDate = "";
				}
				$("#btb_admin_list").jqGrid( 'GridUnload' );
				$("#btb_admin_list").jqGrid({
					url:"/btb/btbAdminListProc"
					,caption:"관리자 목록"
					,datatype 	   : 'json'
				    ,mtype         : 'POST'
			    	, postData	   : {
			        	start_dt   : sDate,
			        	end_dt     : eDate,
			        	category   : $('#category').val(),
			        	keyword    : $('#keyword').val()

			        }
					,height:500
					,width:500
					,rowNum:10
					,rowList:[10,20,30]
					,rownumbers : false
					,viewrecords: true
					,colNames:["고유번호","상태","번호","아이디","관리자명","사이트명","직급","휴대전화","현재상태","등록일"]
					,colModel:[
						  {name:"ADM_SQ", 	    index:"ADM_SQ" , 	sortable:true,	width:30,		align:'center',  hidden:true},
						  {name:"STAT", 	    index:"STAT" , 	    sortable:true,	width:30,		align:'center',  hidden:true},
				          {name:"ROWNUM", 	    index:"ROWNUM" , 	sortable:false,	width:30,		align:'center'},
				          {name:"ADM_EMAIL", 	index:"ADM_EMAIL", 	sortable:true,	width:30,		align:'center'},
				          {name:"ADM_NM", 	    index:"ADM_NM", 	sortable:true,	width:30,		align:'center'},
				          {name:"BTBS_NM",   	index:"BTBS_NM", 	sortable:false,	width:30,		align:'center'},
				          {name:"ADM_RANK", 	index:"ADM_RANK", 	sortable:true,	width:30,		align:'center'},
				          {name:"ADM_HP", 	    index:"ADM_HP", 	sortable:true,	width:30,		align:'center'},
				          {name:"CODENAME", 	index:"CODENAME", 	sortable:true,	width:30,		align:'center'},
				          {name:"REG_DTTM1", 	index:"REG_DTTM1", 	sortable:true,	width:30,		align:'center'}
				          ]
					,pager:"#pager"
			        ,sortname     : 'REG_DTTM1'     //초기화 될때 sort할 컬럼을 지정
			        ,sortorder    : 'desc'       //초기화정렬방법
					,jsonReader:{ repeatitems:false
						, id:"ROWNUM"
						, root: "rows"
						, page : "page"
						, total : "total"
						,records : "records" }

					,gridComplete : function(data,status) {
						var val = jQuery("#btb_admin_list").jqGrid('getGridParam','records');
						$("#totalCnt").html(val);

						// 그리드 데이터의 ID 가져오기
						var ids = $('#btb_admin_list').jqGrid('getDataIDs');
					//	alert(ids);
						// 그리드 데이터 가져오기
						var gridData = $("#btb_admin_list").jqGrid('getRowData');
						// 데이터 확인후 색상 변경
						for (var i = 0; i < gridData.length; i++) {
							if (gridData[i].STAT == '03') {
								$("#"+ids[i]).css("background", "#D5D5D5");
						   }
						}

					}
					,recordpos:'right'
					,gridview: true
					,emptyrecords :" 등록된 데이터가 없습니다."
			    	,loadError:function(xhr, status, error) {
			    	  alert(status + " : " +error);
			    	  console.log(status);
			    	  console.log(xhr);
			    	  console.log(error);
			    	}
			    	,onSelectRow   : function(rowid, status, e) {
				     }
			    	,ondblClickRow: function (rowid, iRow, iCol, e) {

				    	var ret = jQuery("#btb_admin_list").jqGrid('getRowData',rowid);
				    	//상세보기
				    	goView(ret.ADM_SQ);
			    	}

				}).trigger('reloadGrid');

				$("#btb_admin_list").jqGrid(
						"navGrid",
						"#pager",
						{search:false, edit:false, add:false, del:false},
						{closeAfterEdit:false, reloadAfterSubmit:false},
						{closeAfterAdd:false, reloadAfterSubmit:false},
						{reloadAfterSubmit:false}
				);
			}

		}
	}



/**
 *  1달 이전 날짜 가져오기
 */
function getFirstDate(){
	var date = new Date();
	var year = date.getFullYear();                 //yyyy
//	var month = (1 + date.getMonth());             //M
	var month = date.getMonth();
	month = month >= 10 ? month : '0' + month;     // month 두자리로 저장
	var day = date.getDate();                      //d
	day = day >= 10 ? day : '0' + day;             //day 두자리로 저장
	return  year + '-' + month + '-' + day;
}


//상세보기 화면
function goView(val){
//	alert(val);
	$('#adm_sq').val(val);
	$('#selectedShForm').attr('action', '/btb/btbAdminView');
	$("#selectedShForm").submit();
}

/********************************************************************
Name   : getStartDate
Desc   : 특정일 이전 날짜 가져오기
Param  :
********************************************************************/
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

