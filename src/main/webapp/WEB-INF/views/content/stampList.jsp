<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Stamp List</title>
    <script type="text/javascript">
        var isTableShown = true;
        var stampListTable;

        jQuery.fn.dataTableExt.oApi.fnPagingInfo = function ( oSettings )
        {
            return {
                "iStart":         oSettings._iDisplayStart,
                "iEnd":           oSettings.fnDisplayEnd(),
                "iLength":        oSettings._iDisplayLength,
                "iTotal":         oSettings.fnRecordsTotal(),
                "iFilteredTotal": oSettings.fnRecordsDisplay(),
                "iPage":          oSettings._iDisplayLength === -1 ?
                        0 : Math.ceil( oSettings._iDisplayStart / oSettings._iDisplayLength ),
                "iTotalPages":    oSettings._iDisplayLength === -1 ?
                        0 : Math.ceil( oSettings.fnRecordsDisplay() / oSettings._iDisplayLength )
            };
        };

        $(document).ready(function() {

            stampListTable = $("#stampTable").dataTable( {
                "bProcessing": true,
                "bServerSide": true,
                "orderable": false,
                "bStateSave": false,
                "iDisplayStart": 0,
                "fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
                    switch(aData['ub']){
                        case 1:
                            $(nRow).css('background-color', '#b3dced');
                            break;
                    }
                },
                "fnDrawCallback": function () {
                },
                "sCharSet" : "UTF-8",
                "sAjaxSource": "loadStamp.do",
                "bAutoWidth": false,
                "aoColumns": [
                    { "width": "2%",
                      "mData": "id",
                      "mRender": function (stampId) {
                            return "<input type='checkbox' id='" + stampId + "'/>";
                      },
                      "bSortable": false
                    },
                    { "width": "2%",
                      "mData": "catalogNumber",
                      "bSortable": false
                    },
                    { "width": "15%",
                      "mData": "stampImgId",
                      "mRender": function (stampImgId) {
                            if(stampImgId != undefined && stampImgId != null){
                                return "<img src='../image/"+ stampImgId +".do' height='80'/>";
                            } else {
                                return "";
                            }
                        },
                      "bSortable": false
                    },
                    { "width": "25%",
                      "mData": "title",
                      "bSortable": false
                    },
                    { "width": "10%",
                      "mData": "dateEdition",
                      "bSortable": false
                    },
                    { "width": "15%",
                      "mRender": function (oObj, type, full) {
                            return "<div>Full list of stamps<br>" +
                                    "<input type='number' step='1' value='" + full.flq + "' onChange='saveQuantity(" + full.id + ", 1, $(this).val());'/>" +
                                    "</div>" +
                                    "<div>1 line of stamps<br>" +
                                    "<input type='number' step='1' value='" + full.olq + "' onChange='saveQuantity(" + full.id + ", 2, $(this).val());'/>" +
                                    "</div>" +
                                    "<div>One stamp(block)<br>" +
                                    "<input type='number' step='1' value='" + full.opq + "' onChange='saveQuantity(" + full.id + ", 3, $(this).val());'/>" +
                                    "</div>";
                        },
                      "bSortable": false
                    },
                    {   "width": "2%",
                        "mRender": function (oObj, type, full) {
                            return "<a class='editStamp' onClick='editStampFunction(\"../stamp/edit/" + full.id + ".do\"); return false;' >Edit</a>";
                        },
                        "bSortable": false
                    }

                ]
            } );

            $('#stampTable').dataTable().columnFilter({ sPlaceHolder: "head:after",
                aoColumns: [
                    null,
                    { type: "text" },
                    null,
                    { type: "text"},
                    { type: "text" }
                ]
            });


            <c:if test="${pageContext.request.userPrincipal.name == null}">
            //stampListTable.fnSetColumnVis(4, false);
            stampListTable.fnSetColumnVis(5, false);
            </c:if>

        } );

        addNewStamp = function(){
            var stampListDivWrapper = $("#stampListPanel");
            var stampEditPanelWrapper = $("#stampEditPanel");

            $.get("../stamp/add.do", function(data) {
                stampEditPanelWrapper.html(data);
            });

            stampListDivWrapper.hide();
            stampEditPanelWrapper.show();
        };

        editStampFunction = function(link){
            var stampListDivWrapper = $("#stampListPanel");
            var stampEditPanelWrapper = $("#stampEditPanel");

            $.get(link, function(data) {
                stampEditPanelWrapper.html(data);
            });

            stampListDivWrapper.hide();
            stampEditPanelWrapper.show();
        }

    </script>
</head>
<body>



<div id="stampEditPanel" style="display:none"></div>

<div id="stampListPanel">
    <div id="toolsPanel">
        <button type="button" onclick="addNewStamp();">Add new</button>
        <button type="button" onClick="removeSelectedItems();">Delete</button>
    </div>
    <div>
        <table>
            <tr><td>
                <table data-page-length="5" id="stampTable" class="display" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th>Select</th>
                        <th>Catalog Number</th>
                        <th>Image</th>
                        <th>Title</th>
                        <th>Date Edition</th>
                        <th>Quanity</th>
                        <th>Edit</th>
                    </tr>
                    </thead>
                </table>
            </td></tr>
        </table>
    </div>
</div>
</body>
</html>