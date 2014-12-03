/**
 * Created by Olavin on 03.12.2014.
 */
var SUCCESS_MESSAGE = "<strong>Success!</strong> <span>Your data is saved successfully!</span>";
var INFO_MESSAGE = "<strong>Info!</strong> <span>Nothing to update!</span>";
var WARNING_MESSAGE = "<strong>Warning!</strong> <span>See the validation rules!</span>";
var ERROR_MESSAGE = "<strong>Error!</strong> <span>Error saving data!</span>";

function showAlert(className, html) {
    var element = $("div.alert");
    element.removeClass();
    element.addClass(className);
    element.children(".close").nextAll().remove();
    element.append(html);
    element.show();
}

// get uploaded file from input control
function getFile(input) {
    return ($(input)[0].files[0] != undefined) ? $(input)[0].files[0] : null;
}

function addFormData(fd, input){
    fd.append(input,$("#"+input).val());
}

function bankFormData(){
    var fd = new FormData();
    addFormData(fd,"id");
    addFormData(fd,"name");
    addFormData(fd,"logo");
    addFormData(fd,"mfoCode");
    addFormData(fd,"webSite");
    addFormData(fd,"iconAtm");
    addFormData(fd,"iconOffice");
    addFormData(fd,"network_id");
    fd.append("imageLogo",getFile("#imageLogo"));
    fd.append("iconAtmFile",getFile("#iconAtmFile"));
    fd.append("iconOfficeFile",getFile("#iconOfficeFile"));
    return fd;
}


$(document).ready(function () {

    //showAlert("alert alert-info", INFO_MESSAGE);

    function changeImage(input, image) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $(image).attr('src', e.target.result);
            }
            reader.readAsDataURL(input.files[0]);
        }
    }

    $("#imageLogo").change(function () {
        changeImage(this, '#bankLogo');
    });

    $("#iconAtmFile").change(function () {
        changeImage(this, '#bankAtmFile');
    });

    $("#iconOfficeFile").change(function () {
        changeImage(this, '#bankOffice');
    });

    /* On select item in ATM Network dropdown
     *  set dropdown title to name of the ATM Network
     * */
    var network_id = -1;
    $("#networks_menu li a").click(function(){
        var selText = $(this).text();
        $(this).parents('.btn-group').find('.dropdown-toggle').html(selText+' <span class="caret"></span>');
        network_id = $(this).attr("id");
        document.getElementById("network_id").value = network_id;
    });

    $("#adminBankEdit").click(function () {
        var fd = bankFormData();

        if (fd == undefined) {
            alert("Can't save bank - no formdata");
        } else {
            $.ajax({
                url: getHomeUrl() + "adminBankSaveAjax",
                type: "POST",
                data: fd,
                processData: false,
                contentType: false,
                success: function (response) {
                    console.log(response);

                    if (response.status == 'SUCCESS') {
                        showAlert("alert alert-success", SUCCESS_MESSAGE);
                    } else if (response.status == "ERROR") {
                        showAlert("alert alert-danger", ERROR_MESSAGE);
                    } else {
                        showAlert("alert alert-warning", WARNING_MESSAGE);
                        for (var i = 0; i < response.errorMessageList.length; i++) {
                            var item = response.errorMessageList[i];
                            $('#' + item.cause).attr("data-content", item.message);
                            $('#' + item.cause).popover("show");
                        }
                    }
                },
                error: function () {
                    showAlert("alert alert-danger", ERROR_MESSAGE);
                }
            });
        }
    });


    $("#adminBankDelete").click(function () {
        var fd = new FormData();
        form_bank_id = $("#bank_id").val();
        fd.append("bank_id", form_bank_id);
        if (form_bank_id == 0 || form_bank_id == undefined) {
            alert("Can't delete bank with id# = 0");
        } else {
            $.ajax({
                url: getHomeUrl() + "adminBankDeleteAjax",
                type: "POST",
                data: fd,
                processData: false,
                contentType: false,
                success: function (response) {
                    console.log(response);

                    if (response.status == 'SUCCESS') {
                        showAlert("alert alert-success", SUCCESS_MESSAGE);
                    } else if (response.status == "ERROR") {
                        showAlert("alert alert-danger", ERROR_MESSAGE);
                    } else {
                        showAlert("alert alert-warning", WARNING_MESSAGE);
                        for (var i = 0; i < response.errorMessageList.length; i++) {
                            var item = response.errorMessageList[i];
                            $('#' + item.cause).attr("data-content", item.message);
                            $('#' + item.cause).popover("show");
                        }
                    }
                },
                error: function () {
                    showAlert("alert alert-danger", ERROR_MESSAGE);
                }
            });
        }
    });

});
