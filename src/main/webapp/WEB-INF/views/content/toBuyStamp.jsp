<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>To buy stamps</title>
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

            stampListTable = $("#stampTable").dataTable( {
                    "bProcessing": true,
                    "bServerSide": true,
                    "orderable": false,
                    "bStateSave": false,
                    "iDisplayStart": 0,
                    "fnDrawCallback": function () {
                    },
                    "sCharSet" : "UTF-8",
                    "sAjaxSource": "loadToBuyUserStamps.do",
                    "aoColumns": [
                        { "mData": "catalogNumber" },
                        { "mData": "stampImgId",
                            "mRender": function (stampImgId) {
                                return "<img src='../image/"+ stampImgId +".do'/>";
                            }
                        },
                        { "mData": "title" },
                        { "mData": "dateEdition" }
                    ]
            } );

            $('a.toggle-vis').on( 'click', function (e) {
                e.preventDefault();

                // Get the column API object
                var column = table.column( $(this).attr('data-column') );

                // Toggle the visibility
                column.visible( ! column.visible() );
            } );
        } );


    </script>
</head>
<body>
    <table>
        <tr><td>
            <table data-page-length="5" id="stampTable" class="display" cellspacing="0" width="100%">
                <thead>
                    <tr>
                        <th>Catalog number</th>
                        <th>Image</th>
                        <th>Title</th>
                        <th>Publish date</th>
                    </tr>
                </thead>
            </table>
        </td></tr>
    </table>
</body>
</html>