/**
 * Created by Vasyl Danylyuk on 17.11.2014.
 */
var xmlhttp;            //AJAX request
var user;               //Instance of current loaded user

//Установка полів вводу дозволеними/блокованими в залежності від вибору параметру пошуку
function SelectFindType() {
    if ($("#byName").prop("checked") == true) {
        $("#findName").prop("disabled", false);
        $("#findEmail").prop("disabled", true);
    } else {
        $("#findName").prop("disabled", true);
        $("#findEmail").prop("disabled", false);
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
    if($("#byName").prop("checked") == true){
        findBy = "login";
        findValue = $("#findName").val();
    } else {
        findBy = "email";
        findValue = $("#findEmail").val();
    }

    //Створення запиту
    var url = "/findUser?findBy="+findBy+"&findValue="+findValue;

    //Відправка запиту та очікування на відповідь
    xmlhttp.open("GET", url, true);
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState == 4) {
            if(xmlhttp.status == 200) {
                showData();
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
function showData(){
    //Clear form before updating
    clearForm();
    //Отримуємо обєкт користувача з JSON
    try {
        user = JSON.parse(xmlhttp.responseText);
    }catch (Exception){
       // alert("Can't find user with this name or e-mail");
        $('#findBtn').attr("data-content", "Can't find user with this name or e-mail");
        $('#findBtn').popover("show");
        user = null;
        return;
    }
    fillFields();
    //Відображаємо форму
    $("#userData").show();
};

function fillFields(){
    //Заповнюємо всі поля
    //Аватар
    $("#userAvatar").attr("src", "/resources/images/"+user.avatar);//setAttribute("src", "/resources/images/"+user.avatar);
    //Ім'я
    $("#inputLogin").val(user.login);//setAttribute("value", user.login);
    //E-mail
    $("#inputEmail").val(user.email);//setAttribute("value", user.email);
    //Password
    $("#inputPassword").val(user.password);//setAttribute("value", user.email);
    //E-mail
    $("#inputConfirmPassword").val(user.password);//setAttribute("value", user.email);
    //Enabled
    if(user.enabled != 0){
        $("#enabled").prop("checked" ,true);//setAttribute("checked", "true");
    } else {
        $("#enabled").attr("checked",false);//setAttribute("checked", "false");
    }
}


//Відміна дій адміністратора
function clearForm(){
    //hide form
    $("#userData").hide();
    //Очищаємо всі поля
    //Аватар
    clearFields();
}

function clearFields(){
    $("#userAvatar").attr("src","");//setAttribute("src", "");
    //Ім'я
    $("#inputLogin").val("");//setAttribute("value", "");
    //E-mail
    $("#inputEmail").val("");//setAttribute("value", "");
    //password
    $("#inputPassword").val("");//setAttribute("value", "");
    //Confirm
    $("#inputConfirmPassword").val("");//setAttribute("value", "");
    //Enabled
    $("#enabled").attr("checked",false);//setAttribute("checked", "false");
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
                document.getElementById("ModalLabel").innerHTML = "Deleting user";
                showModal();
                $("#userData").slideUp();
            }
        }
    };
    xmlhttp.send(null);
}

function showModal(){
    response = JSON.parse(xmlhttp.responseText);
    document.getElementById("ModalBody").innerHTML = response;

    var options = {
        "backdrop" : "static"
    }

   // document.getElementById("resultModal").modal(options);
    $('#resultModal').modal(options);
};

function updateUser(){

    /*//checking login
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
    if(!validatePasswordStrange($('#inputPassword').prop("value"))){
        $('#inputPassword').attr("data-content", "Password is invalid. Password must have minimum 6 characters, uppercase letter, lowercase letter and digit");
        $('#inputPassword').popover("show");
        return;
    }*/

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
                document.getElementById("ModalLabel").innerHTML =  "Updating user";
                showModal();
            }
        }
    };
    xmlhttp.send(null);
};

function hidePopover(element){
    $('#'+element).popover("destroy");
}



