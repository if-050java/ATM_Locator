/**
 * Created by Vasyl Danylyuk on 17.11.2014.
 */
var xmlhttp;            //AJAX request
var user;               //Instance of current loaded user

//Установка полів вводу дозволеними/блокованими в залежності від вибору параметру пошуку
function SelectFindType() {
    if (document.getElementById("byName").checked == true) {
        document.getElementById("findName").disabled = false;
        document.getElementById("findEmail").disabled = true;
    } else {
        document.getElementById("findName").disabled = true;
        document.getElementById("findEmail").disabled = false;
    }
};

//Створення запиту на пошук користувача
function FindUser(){
    //Об'єкт AJAX запиту
    xmlhttp=GetXmlHttpObject();
    //Перевірка на підтримку AJAX браузером
    if (xmlhttp==null){
        alert ("Your browser does not support Ajax HTTP");
        return;
    }

    //Параметри запиту
    var findBy;
    var findValue;

    //Отримання параметрів запиту з форми
    if(document.getElementById("byName").checked == true){
        findBy = "name";
        findValue = document.getElementById("findName").value;
    } else {
        findBy = "email";
        findValue = document.getElementById("findEmail").value;
    }

    //Створення запиту
    var url = "/findUser?findBy="+findBy+"&findValue="+findValue;

    //Відправка запиту та очікування на відповідь
    xmlhttp.open("GET", url, true);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4) {
            if(xmlhttp.status == 200) {
                fillData();
            }
        }
    };
    xmlhttp.send(null);
};

//Creating GET HTTP request object
function GetXmlHttpObject(){
    if (window.XMLHttpRequest){
        return new XMLHttpRequest();
    }
    if (window.ActiveXObject){
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
    return null;
}

//Заповнення та демонстрація даних користувача
function fillData(){
    //Clear form before updating
    clearForm();
    //Отримуємо обєкт користувача з JSON
    try {
        user = JSON.parse(xmlhttp.responseText);
    }catch (Exception){
        alert("Can't find user with this name or e-mail");
        user = null;
        return;
    }
    //Заповнюємо всі поля
    //Аватар
    document.getElementById("userAvatar").src = "/resources/images/"+user.avatar;//setAttribute("src", "/resources/images/"+user.avatar);
    //Ім'я
    document.getElementById("inputLogin").value = user.login;//setAttribute("value", user.login);
    //E-mail
    document.getElementById("inputEmail").value = user.email;//setAttribute("value", user.email);
    //Password
    document.getElementById("inputPassword").value = user.password;//setAttribute("value", user.email);
    //E-mail
    document.getElementById("inputConfirmPassword").value = user.password;//setAttribute("value", user.email);
    //Enabled
    if(user.enabled != 0){
        document.getElementById("enabled").checked = true;//setAttribute("checked", "true");
    } else {
        document.getElementById("enabled").checked = false;//setAttribute("checked", "false");
    }
    //Відображаємо форму
    document.getElementById("userData").style.display = "block";
};

//Відміна дій адміністратора
function clearForm(){
    //Очищаємо всі поля
    //Аватар
    document.getElementById("userAvatar").src = "";//setAttribute("src", "");
    //Ім'я
    document.getElementById("inputLogin").value = "";//setAttribute("value", "");
    //E-mail
    document.getElementById("inputEmail").value = "";//setAttribute("value", "");
    //password
    document.getElementById("inputPassword").value = "";//setAttribute("value", "");
    //Confirm
    document.getElementById("inputConfirmPassword").value = "";//setAttribute("value", "");
    //Enabled
    document.getElementById("enabled").checked = false;//setAttribute("checked", "false");

    //Приховуємо форму
    document.getElementById("userData").style.display = "none";
}

//Видалення користувача
function deleteUser(){
    //Об'єкт AJAX запиту
    xmlhttp=GetXmlHttpObject();
    //Перевірка на підтримку AJAX браузером
    if (xmlhttp==null){
        alert ("Your browser does not support Ajax HTTP");
        return;
    }

    //Створення запиту
    var url = "/deleteUser?id="+user.id;

    //Відправка запиту та очікування на відповідь
    xmlhttp.open("POST", url, true);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4) {
            if(xmlhttp.status == 200) {
                document.getElementById("ModalLabel").setAttribute("value", "Deleting user");
                showModal();
            }
        }
    };
    xmlhttp.send(null);
}

function showModal(){
    response = JSON.parse(xmlhttp.responseText);
    document.getElementById("ModalBody").innerHTML = response.result;

    var options = {
        "backdrop" : "static"
    }

   // document.getElementById("resultModal").modal(options);
    $('#resultModal').modal(options);
};

function updateUser(){

    //checking login
    if(! validateLogin($('#inputLogin').prop("value"))){
        $('#inputLogin').popover("show");
        return;
    }

    //checking E-Mail
    if(! validateEmail($('#inputEmail').prop("value"))){
        $('#inputEmail').popover("show");
        return;
    };

    //checking confirmed password
    if(! validateConfirmPassword($('#inputPassword').prop("value"),$('#inputConfirmPassword').prop("value"))){
        $('#inputConfirmPassword').popover("show");
        return;
    };

    //checking password strange
    if(! validatePasswordStrange($('#inputPassword').prop("value"))){
        $('#inputPassword').popover("show");
        return;
    }

    //Об'єкт AJAX запиту
    xmlhttp=GetXmlHttpObject();
    //Перевірка на підтримку AJAX браузером
    if (xmlhttp==null){
        alert ("Your browser does not support Ajax HTTP");
        return;
    }

    //Request params
    var login = document.getElementById("inputLogin").value;
    var email = document.getElementById("inputEmail").value;
    var password = document.getElementById("inputPassword").value;
    if(document.getElementById("enabled").checked == true){
        var enabled = 1;
    }else{
        var enabled = 0;
    };

    //Створення запиту
    var url = "/updateUser?id="+user.id+"&login="+login+"&email="+email+"&password="+password+"&enabled="+enabled;

    //Відправка запиту та очікування на відповідь
    xmlhttp.open("POST", url, true);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4) {
            if(xmlhttp.status == 200) {
                document.getElementById("ModalLabel").value =  "Updating user";
                showModal();
            }
        }
    };
    xmlhttp.send(null);
};

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
                }else{
                    $('#inputPassword').attr("data-content", "Password must have a upper case letter");
                    return false//must have a upper case letter data-content
                }
            }else{
                $('#inputPassword').attr("data-content", "Password must have a low case letter");
                return false;//must have a low case letter
            }
        }else{
            $('#inputPassword').attr("data-content", "Password must have a upper case letter");
            return false;//must have a number
        }
    }else{
        $('#inputPassword').attr("data-content", "Password is too short. Min 6 characters");
        return false;//too short
    }
}

function validateConfirmPassword(password, confirm){
    return password == confirm;
}

function validateLogin(login){
    if(login.length >= 4){
        regExp = /^(\w){4,}$/;
        if(regExp.test(login)){
            return true;
        }else{
            $('#inputLogin').attr("data-content", "Unsupported character in login");
            return false;//Unsupported character in login
        }
    }else{
        $('#inputLogin').attr("data-content", "Login is too short. Min 4 characters");
        return false;//Login is too short
    }
}

function hidePopover(element){
    $('#'+element).popover("destroy");
}



