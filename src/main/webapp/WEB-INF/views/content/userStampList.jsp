<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>User's stamp</title>
    <script type="text/javascript">

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

            stampListTable = $("#userStampTable").dataTable( {
                    "bProcessing": true,
                    "bServerSide": true,
                    "orderable": false,
                    "bStateSave": false,
                    "iDisplayStart": 0,
                    "fnDrawCallback": function () {
                    },
                    "sCharSet" : "UTF-8",
                    "sAjaxSource": "loadUserStamps.do",
                    "aoColumns": [
                        { "mData": "catalogNumber" },
                        { "mData": "stampImgId",
                            "mRender": function (stampImgId) {
                                return "<img src='../image/"+ stampImgId +".do' height='80'/>";
                            }
                        },
                        { "mData": "title" },
                        { "mData": "dateEdition" },
                        { "mData": "quantity",
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
                            }
                        }
                    ]
            } );

            $('#userStampTable').dataTable().columnFilter({ sPlaceHolder: "head:after",
                aoColumns: [
                    { type: "text" },
                    null,
                    { type: "text"},
                    { type: "text"},
                    null
                ]
            });
        } );

    </script>
</head>
<body>
    <table>
        <tr><td>
            <table data-page-length="5" id="userStampTable" class="display" cellspacing="0" width="100%">
                <thead>
                    <tr>
                        <th>Catalog number</th>
                        <th>Image</th>
                        <th>Title</th>
                        <th>Publish date</th>
                        <th>Quanity</th>
                    </tr>
                </thead>
            </table>
        </td></tr>
    </table>
</body>
</html>