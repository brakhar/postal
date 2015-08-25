<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <script type="text/javascript">

        $(document).ready(function() {

            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $(document).ajaxSend(function(e, xhr, options) {
                xhr.setRequestHeader(header, token);
            });

            $("#stamp-image").fileinput(
                    {
                        type: 'POST',
                        uploadUrl: "../image/uploadImage.do",
                        maxFileCount: 1,
                        uploadAsync: false
                    });

            $('#stamp-image').on('filebatchuploadsuccess', function(event, data, previewId, index) {
                $("#stampImageId").val(data.response.imageId);
            });

            $("#full-list-image").fileinput(
                    {
                        type: 'POST',
                        uploadUrl: "../image/uploadImage.do",
                        maxFileCount: 1,
                        uploadAsync: false
                    });

            $('#full-list-image').on('filebatchuploadsuccess', function(event, data, previewId, index) {
                $("#fullListImageId").val(data.response.imageId);
            });

        });

        returnToList = function(){
            var stampListDivWrapper = $("#stampListPanel");
            var stampEditPanelWrapper = $("#stampEditPanel");

            stampListDivWrapper.show();
            stampEditPanelWrapper.hide();
            refreshStampTable();
        }

        setCheckBoxValue = function(checkboxId){
            var currentValue = $('#'+checkboxId).attr('checked');
            if(currentValue == undefined){
                $('#'+checkboxId).attr('checked', 'checked');
            } else {
                $('#'+checkboxId).removeAttr('checked');
            }
        }

    </script>
</head>
<body>

<spring:url value="../stamp/save.do" var="formJsonUrl" />

<button id="returnToList" onclick="returnToList()">Return to list</button>
<div class="form-actions">
    <button type="button" class="btn btn-primary" onclick="saveStamp();">Save changes</button>
    <button type="button" class="btn" onclick="returnToList();">Cancel</button>
</div>

<form:form modelAttribute="stamp" class="form-horizontal" id="add-edit-stamp-form">
    <form:hidden path="id" />
    <form:hidden path="fullListImageId" />
    <form:hidden path="stampImageId" />

    <fieldset style="float: left">
        <div class="control-group" id="catalogNumber">
            <label class="control-label">Catalog number:</label>
            <form:input path="catalogNumber" />
            <span class="help-inline"><form:errors path="catalogNumber" /></span>
        </div>
        <div class="control-group" id="circulation">
            <label class="control-label">Circulation:</label>
            <form:input path="circulation" />
            <span class="help-inline"><form:errors path="circulation" /></span>
        </div>
        <div class="control-group" id="title">
            <label class="control-label">Title:</label>
            <form:input path="title" />
            <span class="help-inline"><form:errors path="title" /></span>
        </div>
        <div class="control-group" id="design">
            <label class="control-label">Design:</label>
            <form:input path="design" />
            <span class="help-inline"><form:errors path="design" /></span>
        </div>
        <div class="control-group" id="format">
            <label class="control-label">Format:</label>
            <form:input path="format" />
            <span class="help-inline"><form:errors path="format" /></span>
        </div>
        <div class="control-group" id="perforation">
            <label class="control-label">Perforation:</label>
            <form:input path="perforation" />
            <span class="help-inline"><form:errors path="perforation" /></span>
        </div>
        <div class="control-group" id="denomination">
            <label class="control-label">Denomination:</label>
            <form:input path="denomination" />
            <span class="help-inline"><form:errors path="denomination" /></span>
        </div>
        <div class="control-group" id="numberStampInPiecePaper">
            <label class="control-label">Number stamp in piece of paper:</label>
            <form:input path="numberStampInPiecePaper" />
            <span class="help-inline"><form:errors path="numberStampInPiecePaper" /></span>
        </div>
        <div class="control-group" id="originOfPublish">
            <label class="control-label">Origin of publish:</label>
            <form:input path="originOfPublish" />
            <span class="help-inline"><form:errors path="originOfPublish" /></span>
        </div>
        <div class="control-group" id="publishDate">
            <label class="control-label">Publish date:</label>
            <form:input path="publishDate" class="form-control" data-provide="datepicker" data-date-format="dd.mm.yyyy"/>
            <span class="help-inline"><form:errors path="publishDate" /></span>
        </div>
        <div class="control-group" id="security">
            <label class="control-label">Security:</label>
            <form:input path="security" />
            <span class="help-inline"><form:errors path="security" /></span>
        </div>
        <div class="control-group" id="specialNotes">
            <label class="control-label">Special notes:</label>
            <form:input path="specialNotes" />
            <span class="help-inline"><form:errors path="specialNotes" /></span>
        </div>
        <div class="control-group" id="typePublish">
            <label class="control-label">Type publish:</label>
            <form:input path="typePublish" />
            <span class="help-inline"><form:errors path="typePublish" /></span>
        </div>
        <div class="control-group" id="barCode">
            <label class="control-label">Bar code:</label>
            <form:input path="barCode" />
            <span class="help-inline"><form:errors path="barCode" /></span>
        </div>
    </fieldset>
    <fieldset style="float: right">
        <div class="control-group" id="returnToList()">
            <label class="control-label">Block:</label>
            <form:checkbox path="block" onclick="setCheckBoxValue('block1');"/>
            <span class="help-inline"><form:errors path="block" /></span>
        </div>
        <div class="control-group" id="blockNumber">
            <label class="control-label">Block number:</label>
            <form:input path="blockNumber" />
            <span class="help-inline"><form:errors path="blockNumber" /></span>
        </div>
        <div class="control-group" id="smallPaper">
            <label class="control-label" >Small paper:</label>
            <form:checkbox path="smallPaper" onclick="setCheckBoxValue('smallPaper1');"/>
            <span class="help-inline"><form:errors path="smallPaper" /></span>
        </div>
        <div class="control-group" id="smallPaperNumber">
            <label class="control-label">Small paper number:</label>
            <form:input path="smallPaperNumber" />
            <span class="help-inline"><form:errors path="smallPaperNumber" /></span>
        </div>
        <c:choose>
            <c:when test="${not empty actionType && actionType == 'add'}">
                <div class="control-group" id="stampImage">
                    <label class="control-label">Select stamp image:</label>
                    <input id="stamp-image" type="file" class="file">
                </div>
            </c:when>
            <c:when test="${not empty actionType && actionType == 'edit' && not empty stamp.stampImageId && stamp.stampImageId != ''}">
                <div class="control-group">
                    <label class="control-label">Stamp image:</label>
                    <img src="${pageContext.request.contextPath}/image/${stamp.stampImageId}.do">
                </div>
            </c:when>
            <c:when test="${not empty actionType && actionType == 'edit'}">
                <div class="control-group" id="stampImage">
                    <label class="control-label">Select stamp image:</label>
                    <input id="stamp-image" type="file" class="file">
                </div>
            </c:when>
        </c:choose>
        <c:choose>
            <c:when test="${not empty actionType && actionType == 'add'}">
                <div class="control-group" id="fullListImage">
                    <label class="control-label">Select full list of stamps:</label>
                    <input id="full-list-image" type="file" class="file">
                </div>
            </c:when>
            <c:when test="${not empty actionType && actionType == 'edit' && not empty stamp.fullListImageId && stamp.fullListImageId != ''}">
                <div class="control-group">
                    <label class="control-label">Full list of stamp images:</label>
                    <img src="${pageContext.request.contextPath}/image/${stamp.fullListImageId}.do"/>
                </div>
            </c:when>
            <c:when test="${not empty actionType && actionType == 'edit'}">
                <div class="control-group" id="fullListImage">
                    <label class="control-label">Select full list of stamps:</label>
                    <input id="full-list-image" type="file" class="file">
                </div>
            </c:when>
        </c:choose>
    </fieldset>
</form:form>
<script type="text/javascript">
    function collectFormData(fields) {
        var data = {};
        for (var i = 0; i < fields.length; i++) {
            var $item = $(fields[i]);
            if(($item.attr('name') == 'block' && $('#block1').attr('checked') == undefined) ||
               ($item.attr('name') == 'smallPaper' && $('#smallPaper1').attr('checked') == undefined)
            ){
                data[$item.attr('name')] = false;
            } else {
                data[$item.attr('name')] = $item.val();
            }


        }
        return data;
    }

    $(document).ready(function() {
        var $form = $('#add-edit-stamp-form');
        saveStamp = function() {
            // Ajax validation
            var $inputs = $form.find('input');
            var data = collectFormData($inputs);


            $.post('${formJsonUrl}', data, function(response) {
                $form.find('.control-group').removeClass('error');
                $form.find('.help-inline').empty();
                $form.find('.alert').remove();

                if (response.status == 'FAIL') {
                    for (var i = 0; i < response.result.length; i++) {
                        var item = response.result[i];
                        var $controlGroup = $('#' + item.fieldName);
                        $controlGroup.addClass('error');
                        $controlGroup.find('.help-inline').html(item.message);
                    }
                } else {
                    returnToList();
                }
            }, 'json');
            return false;
        };
    });
</script>

</body>
</html>