jQuery(document).ready(function(){
    jQuery(document).on("click", "div.right", function(event){
        var commentId = event.currentTarget.parentElement.id;
        deleteComment(commentId);
    })
})

function initCommentsClick(){
    jQuery("a#showComments").click(function(event){
        jQuery("#comments").empty();
        var atmId = event.target.getAttribute("atmid");
        var comments = getComments(atmId);
        var userName = jQuery("#login").text();
        comments.forEach(function(comment){
            var commentContainerText =  '<div id="' + comment.id + '" class="container commentsContainer">' +
                                            '<div class="commentHeader">' +
                                                '<strong>' + comment.user.name + '</strong>' +
                                                '<i class="time">' + timestampToFormattedTime(comment.timeCreated) + '</i>' +
                                            '</div>' +
                                            '<div class="commentText col-md-11">' + comment.text + '</div>';
            if(comment.user.login === userName){
                commentContainerText += '<div class="right glyphicon glyphicon-remove" aria-hidden="true"></div>';
            }
            commentContainerText += '</div>';

            jQuery("#comments").append(jQuery.parseHTML(commentContainerText));
        });
        jQuery("#commentsWindow").prop("atmid", atmId);
        $("#commentsWindow").modal("show");
    })
};

function timestampToFormattedTime(timestamp){
    var date = new Date(timestamp);
    var dateFormat = {
        year:"numeric",
        month:"long",
        day: "numeric"
    }
    return date.toLocaleTimeString(navigator.language, dateFormat);
}

function getComments(atmId){
    var comments;
    jQuery.ajax({
        url: getHomeUrl() + "atms/" + atmId + "/comments",
        type: "GET",
        context: document.body,
        dataType: "json",
        async: false,
        statusCode: {
            200: function (response) {
                comments = response;
            },
            404: function(){
                comments = [];
            }
        }
    });
    return comments;
}