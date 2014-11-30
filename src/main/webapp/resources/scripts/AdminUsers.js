var user;               //Instance of current loaded user

//Enable/disable input fields by radio
function SelectFindType() {
    if ($("#byName").prop("checked") == true) {
        $("#findName").prop("disabled", false);
        $("#findEmail").prop("disabled", true);
    } else {
        $("#findName").prop("disabled", true);
        $("#findEmail").prop("disabled", false);
    }
};

//Request to find user by name or email
function FindUser(){
    //Request parameters
    var findBy = ($("#byName").prop("checked") == true) ? "login" : "email";
    var findValue = ($("#byName").prop("checked") == true) ? $("#findName").val() : $("#findEmail").val();

   //Send request
    $.ajax({
        url: "/findUser?findBy="+findBy+"&findValue="+findValue,
        type : "GET",
        context: document.body,
        dataType: "json",
        success : showData
    })
};

//Show user profile
function showData(response){
    if(response.id < 0){
        //show popover if user not found
        $('#findBtn').attr("data-content", "Can't find user with this name or e-mail");
        $('#findBtn').popover("show");
        //hide user form
        $("#userData").slideUp();
        return;
    }
    user = response;
    //fill fields in form by user data
    fillFields(user);
    //show form
    $("#userData").slideDown();
};
//Fill user profile
function fillFields(user){
    $("#userAvatar").attr("src","/resources/images/"+user.avatar);
    $("#inputLogin").val(user.login);
    $("#inputEmail").val(user.email);
    $("#inputPassword").val(user.password);
    $("#inputConfirmPassword").val(user.password);
    if(user.enabled != 0){
        $("#enabled").prop("checked" ,true).change();
    } else {
        $("#enabled").prop("checked" ,false).change();
    }
}
//hide form
function clearForm(){
    $("#userData").slideUp();
}

function askForDeleting(){
    $("#questionModal").modal("show");
}
//Delete user from server
function deleteUser(){
    //Send request
    $.ajax({
        url:"/deleteUser?id="+user.id,
        type : "DELETE",
        context: document.body,
        dataType: "json",
        success : showAlert
    })
    //hide user form
    $("#questionModal").modal("hide");
    $("#userData").slideUp();
}

function showAlert(response){
    var text = "";
    for(i = 0; i < response.errorMessageList.length; i++) {
        text = text + response.errorMessageList[i].message+"; "
    }

    $("#resultDefinition").text(text);

    if(response.status == "ERROR"){
        $("#message").removeClass("alert-success alert-info").addClass("alert-danger")
    }else if(response.status == "INFO") {
        $("#message").removeClass("alert-danger alert-success").addClass("alert-info")
    }else {
        $("#message").removeClass("alert-danger alert-info").addClass("alert-success")
    };
    //show alert about result of operation
    $("#message").show();
};
//hiding alert
function hideAlert(){
    $("#message").hide();
}

function getUserFromForm(){
    updatedUser = {
        id : user.id,
        login : $("#inputLogin").val(),
        email : $("#inputEmail").val(),
        password : $("#inputPassword").val(),
        enabled : $("#enabled").prop("checked") == true ? 1: 0
    }
    return updatedUser;
}

function updateUser(){
    //checking login
    if(! validateLogin($('#inputLogin').prop("value"))){
        $('#inputLogin').attr("data-content", "Login is too short(min 4 letters) or has unsupported character");
        $('#inputLogin').popover("show");
        return;
    }

    //checking E-Mail
    if(!validateEmail($('#inputEmail').prop("value"))){
        $('#inputEmail').popover("show");
        return;
    };

    //checking confirmed password
    if(!validateConfirmPassword($('#inputPassword').prop("value"),$('#inputConfirmPassword').prop("value"))){
        $('#inputPassword').attr("data-content", "Password and confirm is different");
        $('#inputConfirmPassword').popover("show");
        return;
    };

    //checking password strange
    //don'tvalidate if password didn't change
    if($('#inputPassword').prop("value") != user.password){
        if(!validatePasswordStrange($('#inputPassword').prop("value"))){
            $('#inputPassword').attr("data-content", "Password is invalid. Password must have minimum 6 characters, uppercase letter, lowercase letter and digit");
            $('#inputPassword').popover("show");
            return;
        }
    }

    $.ajax({
        url:"/updateUser",
        type : "POST",
        context: document.body,
        data: getUserFromForm(),
        dataType: "json",
        success : showAlert
    })
};

function hidePopover(element){
    $('#'+element).popover("destroy");
}



