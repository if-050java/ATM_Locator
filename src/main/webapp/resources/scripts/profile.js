var SUCCESS_MESSAGE = "<strong>Success!</strong> <span>Your data is saved successfully!</span>";
var ERROR_MESSAGE = "<strong>Error!</strong> <span>Error saving data!</span>";
var WARNING_MESSAGE = "<strong>Warning!</strong> <span>See the validation rules!</span>";

var INVALID_LOGIN = "Login is too short(min 4 letters) or has unsupported character";
var INVALID_NAME = "Login is too short(min 4 letters) or has unsupported character";
var INVALID_EMAIL = "Email isn't valid (example: someone@domain.com)";
var INVALID_PASSWORD = "Password is invalid. Password must have minimum 6 characters, uppercase letter, lowercase letter and digit";
var DIFFERENT_PASSWORD = "Password and confirm password are different";

var MAX_FILE_SIZE = 716800;
var EXTENTIONS = ['jpg', 'jpeg', 'png', 'gif'];
var uploadOK = false;

function hidePopover(element) {
    $('#' + element).popover("destroy");
}
function showPopover(element, message, result) {
    $('#' + element).attr("data-content", message);
    $('#' + element).popover("show");
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
    var user = {
        /*id: $("#id").val(),
         login: $("#login").val(),
         name: $("#name").val(),
         email: $("#email").val(),
         password: $("#password").val(),
         confirmPassword: $("#confirmPassword").val()*/
    };
    $.each(form, function (i, field) {
        user[field.name] = field.value;
    });
    ////user["avatar"] = ($("#image")[0].files[0] != undefined) ? $("#image").val().split('\\').pop() : null;
    ////user["file"] = ($("#image")[0].files[0] != undefined) ? $("#image")[0].files[0] : null;
    //console.log(user);
    return user;
}
function validateForm() {
    var result = true;
    return true;
    var updatedUser = getUser();

    if (!validateLogin(updatedUser.login)) {
        showPopover("login", INVALID_LOGIN, result);
    }
    if (!validateLogin(updatedUser.name)) {
        showPopover("name", INVALID_NAME, result);
    }
    if (!validateEmail(updatedUser.email)) {
        showPopover("email", INVALID_EMAIL, result);
    }
    if (updatedUser.password.length != 0 && !validatePasswordStrange(updatedUser.password)) {
        showPopover("password", INVALID_PASSWORD, result);
    }
    if (!validateConfirmPassword(updatedUser.password, updatedUser.confirmPassword)) {
        showPopover("confirmPassword", DIFFERENT_PASSWORD, result);
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

function prepareUser(updatedUser, persistedUser) {
    if (updatedUser.name == persistedUser.name) {
        delete updatedUser.name;
    }
    if (updatedUser.email == persistedUser.email) {
        delete updatedUser.email;
    }
    if (updatedUser.password == persistedUser.password) {
        delete updatedUser.password;
    }
    delete updatedUser.confirmPassword;
}

function checkFileExtention(extention) {
    return EXTENTIONS.indexOf(extention) > -1;
}
function checkFileSize(size) {
    return size <= MAX_FILE_SIZE;
}



function changeImage(input) {
    if (input.files && input.files[0]) {
        var user = {
            id: $("#id").val(),
            avatar: ($("#image")[0].files[0] != undefined) ? $("#image")[0].files[0] : null
        };
        var reader = new FileReader();
        var extension = input.files[0].name.split('.').pop().toLowerCase()  //file extension from input fileisSuccess =  //is extension in acceptable types
        reader.onload = function (e) {
            //if (!checkFileExtention(extension)) {
            //    alert("ext");
            //} else if (!checkFileSize(e.total)) {
            //    alert("size");
            //} else {
            uploadFile(user, input.files[0])
            if(uploadOK){
                $('#userAvatar').attr('src', e.target.result);
                uploadOK=false;
            }

            //   }
        }
        reader.readAsDataURL(input.files[0]);
    }
}
function uploadFile(user, file) {
    var fd = new FormData();
    fd.append("file", user.avatar);
    $.ajax({
        url: getHomeUrl() + "users/" + user.id + "/avatar",
        type: "POST",
        data: fd,
        async:false,
        processData: false,
        contentType: false,
        statusCode: {
            200: function (response) {
                showAlert("alert alert-success", SUCCESS_MESSAGE);
                uploadOK = true;
            },
            500: function () {
                showAlert("alert alert-danger", ERROR_MESSAGE);
            },
            406: function (response) {
                var message = WARNING_MESSAGE;
                for (var i = 0; i < response.responseJSON.length; i++) {
                    var item = response.responseJSON[i];
                    message+=("<div>" + item.code +"</div>");
                    //console.log(item.code);
                }
                showAlert("alert alert-warning", message);

            }
        }
    })
}

$(document).ready(function () {
    var persistedUser = getUser();

    $("#image").change(function () {
        changeImage(this);
    });

    $("#save").click(function () {

            if (validateForm()) {
                var user = getUser();
                prepareUser(user, persistedUser);
                // var fd = new FormData();
                var url = getHomeUrl() + "users/" + user.id;
                $.ajax({
                    url: url,
                    type: "PATCH",
                    context: document.body,
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify(user),
                    dataType: "json",
                    statusCode: {
                        200: function (response) {
                            showAlert("alert alert-success", SUCCESS_MESSAGE);
                        },
                        500: function () {
                            showAlert("alert alert-danger", ERROR_MESSAGE);
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
                    }
                })
            }
        }
    );
    //$("#save").click(function () {
//
//            if (validateForm()) {
//                var user = getUser();
//               // var fd = new FormData();
//                $.ajax({
//                    url: getHomeUrl() + "users/"+user.id,
//                    type: "PATCH",
//                    data: user,
//                    processData: false,
//                    contentType: false,
//                    statusCode: {
//                        200: function (response) {
//                            showAlert("alert alert-success", SUCCESS_MESSAGE);
//                        },
//                        500: function () {
//                            showAlert("alert alert-danger", ERROR_MESSAGE)
//                        },
//                        406: function (response) {
//                            console.log(response);
//                            showAlert("alert alert-warning", WARNING_MESSAGE);
//                            for (var i = 0; i < response.responseJSON.length; i++) {
//                                var item = response.responseJSON[i];
//                                $('#' + item.field).attr("data-content", item.code);
//                                $('#' + item.field).popover("show");
//                            }
//                        }
//                    },
//                    error: function () {
//                        showAlert("alert alert-danger", ERROR_MESSAGE);
//                    }
//                })
//            }
//        }
//    );
});

