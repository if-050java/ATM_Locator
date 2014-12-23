jQuery(document).ready(function(){
    jQuery(".addcomment").click(function(event){
        var atmId = event.target.parentNode.getAttribute("atmid")
        jQuery("#commentModal").attr("atmid",atmId);
        jQuery("#comment").val("");
        $("#commentModal").modal("show");
    })
    jQuery("#comments").niceScroll();
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
                updateCommentsCount(atmId, 1);
            },
            404: function(){
                alert("Comment wasn't added because such atm is not exist ")
            },
            404: function(){
                alert("Comment wasn't added due to server error ")
            }
        }
    })
}

function updateCommentsCount(markerId, count){
    markers.forEach(function(value){
        if(value.id == markerId){
            value.commentsCount += count;
        }
    })
    favoriteMarkers.forEach(function(value){
        if(value.id == markerId){
            value.commentsCount += count;
        }
    })
}

function deleteComment(id){
    var atmId = jQuery("#commentsWindow").prop("atmid");
    jQuery.ajax({
        url: getHomeUrl() + "atms/" + atmId + "/comments/" + id,
        type: "DELETE",
        context: document.body,
        dataType: "json",
        statusCode: {
            200: function () {
                var strId = '#'+id;
                jQuery(strId).remove();
                updateCommentsCount(atmId, -1);
            },
            404: function(){
                alert("Comment wasn't deleted because such comment not exist ")
            },
            500: function(){
                alert("Comment wasn't deleted due to server error ")
            }
        }
    })
}
