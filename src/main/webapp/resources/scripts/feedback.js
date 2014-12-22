function addFeedback(){

    $("#feedbackModal").modal("show");
	
}
function getFeedback(){
    var feedback = jQuery("#feedback").val();
    jQuery.ajax({
        url: getHomeUrl() + "feedback",
        data: feedback,
        type: "PUT",
        context: document.body,
        dataType: "json",
        statusCode: {
            200: function () {
                $("#feedbackModal").modal("hide");
            },
            404: function(){
                alert("feedback wasn't added due to server error ")
            }
        }
    })
}

