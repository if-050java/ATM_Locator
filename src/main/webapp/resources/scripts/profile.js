function hidePopover(element) {
    $('#' + element).popover("destroy");
}



function validateForm() {
    var user = {
        login: $("#login").val(),
        email: $("#email").val(),
        password: $("#password").val(),
        confirmPassword: $("#confirmPassword").val()
    };
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
}