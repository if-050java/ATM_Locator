var SUCCESS_MESSAGE = "<strong>Success!</strong> <span>Your data is saved successfully!</span>";
var ERROR_MESSAGE = "<strong>Error!</strong> <span>Error saving data!</span>";
var WARNING_MESSAGE = "<strong>Warning!</strong> <span>See the validation rules!</span>";

var INVALID_LOGIN = "Login is too short(min 4 letters) or has unsupported character";
var INVALID_EMAIL = "Email isn't valid (example: someone@domain.com)";
var INVALID_PASSWORD = "Password is invalid. Password must have minimum 6 characters, uppercase letter, lowercase letter and digit";
var DIFFERENT_PASSWORD = "Password and confirm password are different";

function hidePopover(element) {
    $('#' + element).popover("destroy");
}
function showPopover(element, message, result) {
    $('#'+element).attr("data-content", message);
    $('#'+element).popover("show");
    result = false;
}

function hidePopoveDelay(element) {
    setTimeout(function () {
        $('#' + element).popover("destroy");
    }, 2000);
};
function isNotEmptyField(field) {
    return (field.length > 0);
}
function getUser() {
    var form = $("#user").serializeArray();
    var user = {};
    $.each(form, function (i, field) {
        user[field.name] = field.value;
    });
    //user["avatar"] = ($("#image")[0].files[0] != undefined) ? $("#image").val().split('\\').pop() : null;
    //user["file"] = ($("#image")[0].files[0] != undefined) ? $("#image")[0].files[0] : null;
    console.log(user);
    return user;
}
function validateForm() {
    var result = true;
    //return result;
    var updatedUser = getUser();

    if (!validateLogin(updatedUser.login)) {
        showMessage("login", INVALID_LOGIN, result);
    }
    if (!validateEmail(updatedUser.email)) {
        showMessage("login", INVALID_EMAIL, result);
    }
    if (updatedUser.password.length != 0 && !validatePasswordStrange(updatedUser.password)) {
        showMessage("login", INVALID_PASSWORD, result);
    }
    if (!validateConfirmPassword(updatedUser.password, updatedUser.confirmPassword)) {
        showMessage("login", DIFFERENT_PASSWORD, result);
    }
    return result;
}

function showAlert(className, html) {
    var element = $("div.alert");
    element.removeClass();
    element.addClass(className);
    element.children(".close").nextAll().remove();
    element.append(html);
    element.show();
}

function prepareUser(updatedUser, persistedUser){
    if(updatedUser.login==persistedUser.login){
        delete updatedUser.login;
    }
}

$(document).ready(function () {
    var persistedUser = getUser();

    function changeImage(input) {
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

            if (validateForm(persistedUser)) {
                var user = getUser();
                var fd = new FormData();
                for(prop in user){
                    if(user[prop]!=null){
                        fd.append(prop, user[prop]);
                    }

                }
                $.ajax({
                    url: getHomeUrl() + "users/"+user.id,
                    type: "PATCH",
                    data: user,
                    processData: false,
                    contentType: false,
                    statusCode: {
                        200: function (response) {
                            showAlert("alert alert-success", SUCCESS_MESSAGE);
                        },
                        500: function () {
                            showAlert("alert alert-danger", ERROR_MESSAGE)
                        },
                        406: function (response) {
                            console.log(response);
                            showAlert("alert alert-warning", WARNING_MESSAGE);
                            for (var i = 0; i < response.responseJSON.length; i++) {
                                var item = response.responseJSON[i];
                                $('#' + item.field).attr("data-content", item.code);
                                $('#' + item.field).popover("show");
                            }
                        }
                    },
                    error: function () {
                        showAlert("alert alert-danger", ERROR_MESSAGE);
                    }
                })
            }
        }
    )
});

