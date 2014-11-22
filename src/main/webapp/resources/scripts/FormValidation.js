/**
 * Created by Vasyl Danylyuk on 22.11.2014.
 */

function validateEmail(email){
    regExp = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return regExp.test(email);
};

function validatePasswordStrange(password){
    if(password.length > 6){
        regExp = /[0-9]/;
        if(regExp.test(password)){
            regExp = /[a-z]|[а-ї]/;
            if(regExp.test(password)){
                regExp = /[A-Z]|[Є-Я]/
                if(regExp.test(password)){
                    return true;
                }else{return false}//must have a upper case letter data-content
            }else{return false}//must have a low case letter
        }else{return false}//must have a number
    }else{return false}//too short
}

function validateConfirmPassword(password, confirm){
    return password == confirm;
}

function validateLogin(login){
    if(login.length >= 4){
        regExp = /^(\w){4,}$/;
        if(regExp.test(login)){
            return true;
        }else{return false}//Unsupported character in login
    }else{return false}//Login is too short
}