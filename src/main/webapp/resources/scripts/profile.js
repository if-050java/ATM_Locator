function hidePopover(element) {
    $('#' + element).popover("destroy");
}
function hidePopoveDelay(element) {
    setTimeout(function () {
        $('#' + element).popover("destroy");
    }, 2000);
};

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
    var result = true;
    var user = getUser();
    if (!validateLogin(user.login)) {
        $('#login').attr("data-content", "Login is too short(min 4 letters) or has unsupported character");
        $('#login').popover("show");
        result = false;
    }
    if (!validateEmail(user.email)) {
        $('#email').attr("data-content", "Email is not valid");
        $('#email').popover("show");
        result = false;
    }
    if (!validatePasswordStrange(user.password)) {
        $('#password').attr("data-content", "Password is invalid. Password must have minimum 6 characters, uppercase letter, lowercase letter and digit");
        $('#password').popover("show");
        result = false;
    }
    if (!validateConfirmPassword(user.password, user.confirmPassword)) {
        $('#confirmPassword').attr("data-content", "Password and confirm is different");
        $('#confirmPassword').popover("show");
        result = false;
    }
    return result;
}
$(document).ready(function () {
    function changeImage(input) {
        var MAX_FILE_SIZE = 716800; //700kb
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $('#userAvatar').attr('src', e.target.result);
            }
            reader.readAsDataURL(input.files[0]);
        }
    }

    $("#image").change(function () {
        changeImage(this);
    });

    $("#save").click(function () {
            if (validateForm()) {
                var user = getUser();
                var fd = new FormData();
                fd.append("id", user.id);
                fd.append("login", user.login);
                fd.append("email", user.email);
                fd.append("password", user.password);
                fd.append("confirmPassword", user.confirmPassword);
                fd.append("avatar", user.avatar);

                var alert = $("div.alert");
                $.ajax({
                    url: "/user/update",
                    type: "POST",
                    data: fd,
                    processData: false,
                    contentType: false,
                    success: function (response) {
                        console.log(response);

                        if (response.status == 'SUCCESS') {
                            alert.removeClass();
                            alert.addClass("alert alert-success");
                            alert.html("<strong>Success!</strong> Your data is saved successfully!");
                            alert.show().fadeOut(4000);

                        } else if (response.status == "INFO") {
                            alert.removeClass();
                            alert.addClass("alert alert-info");
                            alert.html("<strong>Warning!</strong> Nothing to update!");
                            alert.show().fadeOut(4000);
                        } else {
                            alert.removeClass();
                            alert.addClass("alert alert-warning");
                            alert.html("<strong>Warning!</strong> See the validation rules!");
                            alert.show().fadeOut(4000);
                            for (var i = 0; i < response.errorMessageList.length; i++) {
                                var item = response.errorMessageList[i];
                                $('#' + item.fieldName).attr("data-content", item.message);
                                $('#' + item.fieldName).popover("show");
                            }
                        }
                    },
                    error: function (response) {
                        alert.removeClass();
                        alert.addClass("alert alert-danger");
                        alert.html("<strong>Error!</strong> Error saving data!");
                        alert.show().fadeOut(4000);
                    }
                })
            }
        }
    )
});

