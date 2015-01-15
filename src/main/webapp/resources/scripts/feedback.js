function addFeedback(){
    //var userDat = getUserData();
    getUserData();

    /*var defaultName =userDat.name;
    var defaultEmail =userDat.email;*/

    $("#feedbackModal").modal("show");

}
function getUserData() {
//    var userData={
//        name :"misterAnonymus"
//    };
    jQuery.ajax({

        url: getHomeUrl() + "feedback",
        //data: JSON.stringify(userData),
        type: "POST",
        contentType: "application/json; charset=utf-8",
        context: document.body,
       /* async: false,*/
        dataType: "json",
        statusCode: {
            200: function (resp) {

                $('#namef').attr("value", resp.name);
                $('#emailf').attr("value", resp.email);

            }
        }

    });
//    return userData;
}
function getFeedback(){

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
    if(feedback.length<=20){
        $('#feedback').attr("data-content", "field must be greater than 20 characters");
        $('#feedback').popover("show");
        return;
    }

    var result = {
        "feedback":feedback,
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
                $("#feedbackModal").modal("hide");
                /*alert("feedback wasn't added due to server error ")*/
                showAlert("alert alert-danger", "Sorry, try again later!")
            },
            500: function(){
                $("#feedbackModal").modal("hide");
                /*alert("feedback wasn't added due to server error ")*/
                showAlert("alert alert-danger", "Sorry, try again later!")
            }
        }
    })
}

function showAlert(className, html) {
    $("#message").removeClass();
    $("#message").addClass(className);
    $("#resultDefinition").empty();
    $("#resultDefinition").append(html);
    $("#message").slideDown(700);
    setTimeout(hideAlert, 11000)
}

//hide alert about result of operation
function hideAlert(){

    $("#message").slideUp(100);
}

//////////////////////////////////



