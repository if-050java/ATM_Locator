function addFeedback(){
    var userDat = getUserData();
    var defaultName =userDat.name;
    var defaultEmail =userDat.email;
    $('#namef').attr("value", defaultName);
    $('#emailf').attr("value", defaultEmail);
    $("#feedbackModal").modal("show");

}
function getUserData() {
    var userData={
        name :"misterAnonim"
    };
    jQuery.ajax({

        url: getHomeUrl() + "feedback",
        //data: JSON.stringify(userData),
        type: "POST",
        contentType: "application/json; charset=utf-8",
        context: document.body,
        dataType: "json",
        statusCode: {
            200: function (resp) {
                userData = resp;
            },
            404: function(){
                /*alert("feedback wasn't added due to server error ")*/
                userData
            }
        }
    });
    return userData;
}
function getFeedback(){

//    var tmp = userDat.isArray();
//    if(!userDat.empty){
//        userDat.forEach(function(data){
//            $('#namef').attr("value", data);
//        })
//    }
    var feedback = jQuery("#feedback").val();
    var email = jQuery("#emailf").val();
    var name = jQuery("#namef").val();

    //checking login

    if(! validateNickName(name)){
        $('#namef').attr("data-content", "NickName is too short(min 4 letters) or has unsupported character");
        $('#namef').popover("show");
        return;
    }
    //checking E-Mail
    if(!validateEmail(email)){
        $('#emailf').popover("show");
        return;
    };

    var result = {"feedback":feedback,
        "email":email,
        "name":name
    };

    jQuery.ajax({
        url: getHomeUrl() + "feedback",
        data: JSON.stringify(result),
        type: "PUT",
        contentType: "application/json; charset=utf-8",
        context: document.body,
        dataType: "json",
        statusCode: {
            200: function () {
                $("#feedbackModal").modal("hide");
                showAlert("alert alert-success", "Operation successfully processed")
            },
            404: function(){
                /*alert("feedback wasn't added due to server error ")*/
                showAlert("alert alert-danger", "Sorry, try again later!")
            }
        }
    })
}
/*function validateForm(){
 //checking login
 if(! validateNickName($('#name').prop("value"))){
 //        $('#name').attr("data-content", "NickName is too short(min 4 letters) or has unsupported character");
 $('#name').popover("show");
 return;
 }
 //checking E-Mail
 if(!validateEmail($('#email').prop("value"))){
 $('#email').popover("show");
 return;
 };
 }*/
//show alert about result of operation
function showAlert(className, html) {
    $("#message").removeClass();
    $("#message").addClass(className);
    $("#resultDefinition").empty();
    $("#resultDefinition").append(html);
    $("#message").slideDown(1400);
    setTimeout(hideAlert, 14000)
}

//hide alert about result of operation
function hideAlert(){

    $("#message").slideUp(1500);
}


