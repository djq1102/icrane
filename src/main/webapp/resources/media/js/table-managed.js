var TableManaged = function() {

	return {

		// main function to initiate the module
		init : function(ajax_data) {

			if (!jQuery().dataTable) {
				return;
			}

			// begin second table
			$('#sample_2')
					.dataTable(
							{
								"aLengthMenu" : [ [ 5, 15, 20, -1 ],
										[ 5, 15, 20 ] // change per
								// page values
								// here
								],
								// set the initial value
								"bSort": false,
								"bFilter": false,//去掉搜索框
								"bServerSide" : true,
								"sAjaxSource" : ajax_data.ajax_url,
								//"fnServerData":receiveTableData,
								"iDisplayLength" : 5,
								"sDom" : "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
								"sPaginationType" : "bootstrap",
								"oLanguage" : {
									"sLengthMenu" : "_MENU_ 每页",
									"oPaginate" : {
										"sPrevious" : "前一页",
										"sNext" : "后一页"
									},
									"sZeroRecords": "对不起，查询不到相关数据！",
									"sEmptyTable" : "表中无数据存在！",
					                "sInfo" : "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
									
								},
								"aoColumnDefs" : [ {
									'bSortable' : false,
									'aTargets' : [ ajax_data.last_index], 
									'mRender': function ( data, type, full ) {
								        return '<a href="'+ajax_data.edit_url+data+'">修改</a>'+'&nbsp;<a href="'+ajax_data.delete_url+data+'">删除</a>';
									}
								}]
							});

			jQuery('#sample_2 .group-checkable').change(function() {
				var set = jQuery(this).attr("data-set");
				var checked = jQuery(this).is(":checked");
				jQuery(set).each(function() {
					if (checked) {
						$(this).attr("checked", true);
					} else {
						$(this).attr("checked", false);
					}
				});
				jQuery.uniform.update(set);
			});

			jQuery('#sample_2_wrapper .dataTables_length select').addClass(
					"m-wrap small"); // modify table per page dropdown
			jQuery('#sample_2_wrapper .dataTables_length select').select2(); // initialzie
			// select2
			// dropdown
		},
	
	reload:function(tableId, urlData){
		  oTable = $(tableId).dataTable();
//		  /* Example call to load a new file */
		  oTable.fnReloadAjax( urlData );

		  /* Example call to reload from original file */
//		  oTable.fnReloadAjax();
		}

	};

}();

function receiveTableData(sSource, aoData, fnCallback) {
	$.ajax({
		type : "POST",
		// contentType: "application/json", //这段代码不要加，我时延的是否后台会接受不到数据
		url : sSource,
		dataType : "json",
		data : {
			aoData : JSON.stringify(aoData)
		}, // 以json格式传递(struts2后台还是以string类型接受),year和month直接作为参数传递。
		success : function(data) {
			// $("#url_sortdata").val(data.aaData);
			fnCallback(data); // 服务器端返回的对象的returnObject部分是要求的格式
		}
	});
};
