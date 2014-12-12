/**
 * Methods for validating credentials by regular expressions, wich user had entered.
 */

function validateEmail(email){
    var regExp = /^[A-Za-z]([A-Za-z0-9])+([\.\-\_]?[A-Za-z0-9]+)*@([a-z0-9-])+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$/;
    return regExp.test(email);
};

function validatePasswordStrange(password){
    var regExp = /((?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})/;
    return regExp.test(password);
};

function validateConfirmPassword(password, confirm){
    return password == confirm;
};

function validateLogin(login){
    var regExp = /^[A-Za-z](\w){3,}$/;
    return regExp.test(login);
};

function validateNickName(nickName){
    var regExp = /^.*$/;
    return regExp.test(nickName);
};