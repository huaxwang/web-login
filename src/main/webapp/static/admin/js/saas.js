function saveColumnFilter(){
	
	var rows = $('#columnTable').datagrid('getRows');
	var selectionsRow = $('#columnTable').datagrid('getSelections');
	$(rows).each(function(index, item){
		 $('#table').datagrid('hideColumn', item['@name']);
	 });
	
	$(selectionsRow).each(function(index, item){
		 $('#table').datagrid('showColumn', item['@name']);
	 });
	
	$columnWin.window('close'); 
	
}


function refreshColumnFilter(){
	$('#columnTable').datagrid('unselectAll');
	var objs = $('#table').datagrid('getColumnFields');
	$(objs).each(function(index, item){
		var obj = $('#table').datagrid('getColumnOption',item);
		 if(!obj.hidden){
			 $('#columnTable').datagrid('selectRow',index);
		 }
	 });
	
}

