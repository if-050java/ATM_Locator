function hidePopover(element) {
    $('#' + element).popover("destroy");
}

function getUser() {
    var user = {
        id: $("#id").val(),
        login: $("#login").val(),
        email: $("#email").val(),
        password: $("#password").val(),
        confirmPassword: $("#confirmPassword").val(),
        avatar: ($("#image")[0].files[0] != undefined) ? $("#image")[0].files[0] : null
    };
    return user;
}

function validateForm() {
    var user = getUser();
    if (!validateLogin(user.login)) {
        $('#login').attr("data-content", "Login is too short(min 4 letters) or has unsupported character");
        $('#login').popover("show");
        return false;
    }
    if (!validateEmail(user.email)) {
        $('#email').attr("data-content", "Email is not valid");
        $('#email').popover("show");
        return false;
    }
    if (!validateConfirmPassword(user.password, user.confirmPassword)) {
        $('#confirmPassword').attr("data-content", "Password and confirm is different");
        $('#confirmPassword').popover("show");
        return false;
    }
    if (!validatePasswordStrange(user.password)) {
        $('#password').attr("data-content", "Password is invalid. Password must have minimum 6 characters, uppercase letter, lowercase letter and digit");
        $('#password').popover("show");
        return false;
    }
    return true;
}
$(document).ready(function () {
    $("#save").click(function () {
        if(validateForm()){
        var user = getUser();
        var fd = new FormData();
        fd.append("id", user.id);
        fd.append("login", user.login);
        fd.append("email", user.email);
        fd.append("password", user.password);
        fd.append("confirmPassword", user.confirmPassword);
        fd.append("avatar", user.avatar);
        $.ajax({
            url: "/user/update",
            type: "POST",
            data: fd,
            processData: false,
            contentType: false,
            success: function (response) {
                var alert = $("div.alert");
                if (response.status == 'SUCCESS') {
                   //alert.removeClass();
                   alert.addClass("alert","alert-success");
                   alert.css({"display": "block"});
                   alert.html("<strong>Success!</strong> Your data is saved successfully!");

                } else if(response.status=="INFO"){
                    //alert.removeClass();
                    alert.addClass("alert","alert-info");
                    alert.css({"display": "block"});
                    alert.html("<strong>Warning!</strong> Nothing to update!");
                } else {
                    console.log(response);
                    //alert.removeClass();
                    alert.addClass("alert","alert-danger");
                    alert.css({"display": "block"});
                    for (var i = 0; i < response.errorMessageList.length; i++) {
                        var item = response.errorMessageList[i];
                        $('#'+item.fieldName).attr("data-content",item.message);
                        $('#'+item.fieldName).popover("show");
                        alert.find('strong').html(item.message);
                    }
                }
            },
            error: function (response) {
                console.log(response);
            }
        });}
    });
    window.setTimeout(function() {
        $(".alert").fadeTo(500, 0).slideUp(500, function(){
            $(this).css({"display":"none"});
        });
    }, 2000);
});

