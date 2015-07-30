
var removeSelectedItems =  function(){
    var selectedItems = selectedCheckBoxToArray();
    for(var i=0; i<selectedItems.length; i++){
        $.ajax({
            url: 'deleteStamp.do',
            data: 'id='+selectedItems[i],
            type: 'GET',
            success: function(data){
                refreshStampTable();
            },
            error: function(data) {

            }
        });
    }
}

var refreshStampTable = function() {
    $('#stampTable').dataTable()._fnAjaxUpdate();
}

var selectedCheckBoxToArray = function(){
    var selected = [];
    $('#stampTable input:checked').each(function() {
        selected.push($(this).attr('id'));
    });
    return selected;
}

var addUserStamp = function(stampId){
    var quanity = $('#quantity'+stampId).val();

    if (quanity == 0 || quanity == "") {
        alert("You should add quantity" );
        return;
    }

    $.ajax({
        url: 'addUserStamp/' + stampId + '/' + quanity + '.do',
        type: 'GET',
        success: function(data){
            if(data.result == true) {
                alert("Added!");
            } else {
                alert("Error during adding user's stamp!");
            }

        },
        error: function(data) {

        }
    });
}

saveStamp = function(){
    alert("a");
}

var loadEditStampDialog = function(){
    $("#dialog-stamp-edit").dialog(
        {
            modal: true,
            resizable: false,
            width: 500,
            height: 400,
            buttons: {
                "Change stamp": saveStamp,
                Cancel: function() {
                    dialog.dialog( "close" );
                }
            },
            open: function(event, ui){
                $.ajax({
                    url: '/stamp/edit/24088',
                    type: 'GET',
                    success: function(data){
                    },
                    error: function(data) {

                    }
                });

            }
        });
    $(".edit").click(function(event)
    {
        alert("ku")
        event.preventDefault();
        var link = $(this).attr('href');
        alert(link);
        $("#dialog-stamp-edit-inner").load(link,function(){
            $( "#dialog-stamp-edit" ).dialog( "open" );
        });
    });

}


var saveQuantity = function (stampId, quantityType, quantity){
    $.ajax({
        url: 'updateUserStampQuantity.do',
        type: 'GET',
        dataType: 'json',
        data: 'stampId=' + stampId + '&quantityType=' + quantityType
        + '&quantity='  + quantity,
        contentType: 'application/json',
        mimeType: 'application/json',
        success: function(data) {
            // alert(data.id + " " + data.name);
        },
        error:function(data,status,er) {
            alert("error: "+data+" status: "+status+" er:"+er);
        }
    });

}
