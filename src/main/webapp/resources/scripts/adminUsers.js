var user;               //Instance of current loaded user

//initialize autocomplite for finding
window.onload = function() {
    jQuery("#findName").autocomplete({
        serviceUrl: getHomeUrl() + "users",
        minChars: 1,
        deferRequestBy: 50,
        delimiter: /(,|;)\s*/,
        onSelect: function(){
            FindUser();
        },
        zIndex: 1000
    })
}

//request to find user by name or email
function FindUser(){
    user = null;
    var findValue = $("#findName").val().replace('.','*');

    //Send request
    $.ajax({
        url: getHomeUrl()+"users/"+findValue,
        type : "GET",
        context: document.body,
        dataType: "json",
        statusCode: {
            200: function(response) {
                showData(response);
                $("#findName").val("");
            },
            404: couldNotFind
        }
    })

    return false;
}

//show popover if cant find such user
function couldNotFind(){
    //show popover if user not found
    $('#findName').attr("data-content", "Can't find user with this name or e-mail");
    $('#findName').popover("show");
    //hide user form
    $("#userData").slideUp();
}

//show user profile
function showData(response){
    user = response;
    //fill fields in form by user data
    fillFields(user);
    //show form
    $("#userData").slideDown();
}

//fill user profile
function fillFields(user){
    $("#avatar").attr("src",getHomeUrl()+"resources/images/"+user.avatar);
    $("#login").val(user.login);
    $("#name").val(user.name);
    $("#email").val(user.email);
    if(user.enabled != 0){
        $("#enabled").prop("checked" ,true).change();
    } else {
        $("#enabled").prop("checked" ,false).change();
    }
    $("#genPassword").prop("checked" ,false).change();
    $("#save").prop('disabled', true);
    for(i=0;i < user.roles.length; i++){
        if (user.roles[i].name === "ADMIN") {
            $("#enabled").bootstrapToggle('disable');
            $("#delete").prop('disabled', true);
            return;
        } else {
            $("#enabled").bootstrapToggle('enable');
            $("#delete").prop('disabled', false);
        }
    }
}

//hide user profile
function clearForm(){
    $("#userData").slideUp();
}

//to ask about deleting
function askForDeleting(){
    $("#questionModal").modal("show");
}

//delete user from server
function deleteUser(){
    //Send request
    $.ajax({
        url:getHomeUrl()+"users/"+user.id,
        type : "DELETE",
        context: document.body,
        dataType: "json",
        statusCode: {
            200: showAlert("alert alert-success", "Operation successfully processed"),
            404: showAlert("alert alert-warning", "No such user"),
            422: showAlert("alert alert-warning", "Can't delete administrator")
        }
    })
    //hide user form
    $("#questionModal").modal("hide");
    //   $("#userData").slideUp();
}

//show alert about result of operation
function showAlert(className, html) {
    $("#message").removeClass();
    $("#message").addClass(className);
    $("#resultDefinition").empty();
    $("#resultDefinition").append(html);
    $("#message").slideDown(500);
    setTimeout(hideAlert, 8000)
}

//hide alert about result of operation
function hideAlert(){

    $("#message").slideUp(500);
}

//create PATCH data from updated fields
function getUpdatedFields(){
    updatedUser = {
        login : $("#login").val(),
        name : $("#name").val(),
        email : $("#email").val(),
        enabled : $("#enabled").prop("checked") == true ? 1 : 0
    }

    if(updatedUser.login === user.login){
        delete updatedUser.login;
    }

    if(updatedUser.name === user.name){
        delete updatedUser.name;
    }

    if(updatedUser.email === user.email){
        delete updatedUser.email;
    }

    if(updatedUser.enabled === user.enabled){
        delete updatedUser.enabled;
    }

    return updatedUser;
}

//request to update user
function updateUser(){
    //checking login
    if(! validateLogin($('#login').prop("value"))){
        $('#login').attr("data-content", "Login is too short(min 4 letters) or has unsupported character");
        $('#login').popover("show");
        return;
    }
    //checking login
    if(! validateNickName($('#name').prop("value"))){
        $('#name').attr("data-content", "NickName is too short(min 4 letters) or has unsupported character");
        $('#name').popover("show");
        return;
    }
    //checking E-Mail
    if(!validateEmail($('#email').prop("value"))){
        $('#email').popover("show");
        return;
    };


    var data = JSON.stringify(getUpdatedFields());
    if (data === "{}" && $("#genPassword").prop("checked") == false){
        showAlert("alert alert-info", "Nothing to update");
        return;
    };
    var url =getHomeUrl()+"users/"+user.id+"?generatePassword="+($("#genPassword").prop("checked") == true ? true : false);
    $.ajax({
        url : url,
        type : "PATCH",
        context : document.body,
        contentType: "application/json; charset=utf-8",
        data : data,
        dataType : "json",
        statusCode: {
            200: function(){
                showAlert("alert alert-success", "Operation successfully processed");
                user.login = $("#login").val();
                user.name = $("#name").val();
                user.email = $("#email").val();
                user.enabled = $("#enabled").prop("checked") == true ? 1 : 0;
                $("#genPassword").prop("checked" ,false).change();
                $("#save").prop('disabled', true);
            },
            406: function (response) {
                showAlert("alert alert-warning", "Validation error");
                for (var i = 0; i < response.responseJSON.length; i++) {
                    var item = response.responseJSON[i];
                    $('#' + item.field).attr("data-content", item.code);
                    $('#' + item.field).popover("show");
                }
            },
            422: function(){
                showAlert("alert alert-warning", "Can't disable administrator");
            },
            500: function(){ showAlert("alert alert-danger", "Saving failed because of a server error")},
            503: function(){ showAlert("alert alert-danger", "Sending email failed because of a server error")}
        }
    })
}

//hide aditional information about validation fields
function hidePopover(element){
    $('#'+element).popover("destroy");
}

//enable save button if some fields was modified by user
function setModified(){
    if(JSON.stringify(getUpdatedFields()) != "{}") {
        $("#save").prop('disabled', false);
    }else{
        $("#save").prop('disabled', true);
    }
}

$(function() {
    $('#enabled').change(function() {
        $("#save").prop('disabled', false);
    })
})

$(function() {
    $('#genPassword').change(function() {
        $("#save").prop('disabled', false);
    })
})