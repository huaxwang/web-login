(function($){

    /////////////////////////////////////////////////////////////////////////////////
    //              此js中的通用方法适用于 EasyUI 中的grid   author:MeiJian          //
    //                                                                             //
    ////////////////////////////////////////////////////////////////////////////////

   /*   用法：
    *    onHeaderContextMenu: function(e, field) {
    *          $.columnIsDisplay(e, field, "table");
    *    }
    */

     /**
     * 当 grid 的头部被右键单击时选择隐藏 grid中的列
     * @param e
     * @param field
     * @param table 表示grid的id
     */
    $.columnIsDisplay = function(e, field, table) {          //grid上每列控制显示
        var hideColumnMenu = '<div id="tmenu" style="width:120px;"></div>';
        if ($("#tmenu").length == 0) {
            $("body").append($(hideColumnMenu));
        }

        e.preventDefault();
        if ($('#tmenu').text() == "") {
            var fields = $('#' + table).datagrid('getColumnFields');
            for (var i = 0; i < fields.length; i++) {
                var display = $('#' + table).datagrid('getColumnOption', fields[i]);
                var div_var = '<div iconCls="icon-ok" field="' + fields[i] + '">' + display.title + '</div>';
                $('#tmenu').append(div_var);
            }
            $('#tmenu').menu({
                onClick: function(item) {
                    if (item.iconCls == 'icon-ok') {
                        if ($('#tmenu').find("div.menu-icon.icon-ok").length > 1) {  //至少要有留一列在页面上
                            $('#' + table).datagrid('hideColumn', $(item.target).attr("field"));
                            $('#tmenu').menu('setIcon', {
                                        target: item.target,
                                        iconCls: 'icon-empty'
                                    });
                        }
                    } else {
                        $('#' + table).datagrid('showColumn', $(item.target).attr("field"));
                        $('#tmenu').menu('setIcon', {
                                    target: item.target,
                                    iconCls: 'icon-ok'
                                });
                    }
                }
            });
        }
        $('#tmenu').menu('show', {
            left:e.pageX,
            top:e.pageY
        });
    };


    /*   用法：
    *    onDblClickCell:function(rowIndex, field, value) {
    *         $.columnWindow("table", field, value);
    *    }
    */

    /**
     * 双击grid中的一个单元格，单元格中的内容在弹出框中完整显示
     * @param table  表示grid的id
     * @param field
     * @param value  单元格的内容
     */
    $.columnWindow = function(table, field, value) {
        var display = $("#"+table).datagrid('getColumnOption', field);
        var temp_window = '<div id="columnWindow" style="width:350px;height:160px; overflow:hidden;display: none" resizable="true" maximizable="true" modal="true"><textarea class="textarea" id="column_textarea" style="width:100%;height:160px;"></textarea></div>';
        if ($("#columnWindow").length == 0) {
            $("body").append($(temp_window));
        }
        $("#columnWindow").css("display", "block").dialog({
                    "title":display.title?display.title:"无",
                    "maximized":false,
                    "onMove":function(left, top) {
                        $.adjustPosition("columnWindow", left, top)
                    },
                    "onBeforeClose":function() {
                        $.restoreDialog("columnWindow")
                    }
                });
        $("#column_textarea").val(value);
    };


})(jQuery);