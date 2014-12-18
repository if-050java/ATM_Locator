jQuery(document).ready(function(){
    jQuery(".addcomment").click(function(event){
        var atmId = event.target.parentNode.getAttribute("atmid")
        jQuery("#commentModal").attr("atmid",atmId);
        jQuery("#comment").val("");
        $("#commentModal").modal("show");
    })

    jQuery(".addcomment").click(function(event){
        var atmId = event.target.parentNode.getAttribute("atmid")
        jQuery("#commentModal").attr("atmid",atmId);
        jQuery("#comment").val("");
        $("#commentModal").modal("show");
    })
    commentsWindow
})

function addComment(){
    var comment = jQuery("#comment").val();
    var atmId = jQuery("#commentModal").attr("atmid");
    jQuery.ajax({
        url: getHomeUrl() + "atms/" + atmId + "/comments",
        data: comment,
        type: "PUT",
        context: document.body,
        dataType: "json",
        statusCode: {
            200: function () {
                $("#commentModal").modal("hide");
            },
            404: function(){
                alert("Comment wasn't added due to server error ")
            }
        }
    })
}

function showComments(){

}


