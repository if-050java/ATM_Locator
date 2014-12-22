
function initCommentsClick(){
    jQuery("a#showComments").click(function(event){
        jQuery("#comments").empty();
        var atmId = event.target.getAttribute("atmid");
        var comments = getcomments(atmId);
        comments.forEach(function(value){
            var commentContainer = jQuery.parseHTML('<div style="width: 100%"></div>');
            var nameTimeDiv = jQuery.parseHTML('<div style=" display: table; width: 100%; background-color: #eeeeee"></div>');
            jQuery(nameTimeDiv).append(jQuery.parseHTML('<strong>' + value.user.name + '</strong>'));
            jQuery(nameTimeDiv).append(jQuery.parseHTML('<i style="float: right">' + timestamptoFormatedtime(value.timeCreated) + '</i>'));
            jQuery(nameTimeDiv).append(jQuery.parseHTML('<br>'));
            jQuery(commentContainer).append(nameTimeDiv);
            jQuery(commentContainer).append(jQuery.parseHTML('<div style="display: table; width: 100%">' + value.text + '</div>'));
            jQuery("#comments").append(commentContainer);
            jQuery("#comments").append(jQuery.parseHTML('<hr>'));
        });
        $("#commentsWindow").modal("show");
    })
};

function timestamptoFormatedtime(timestamp){
    var date = new Date(timestamp);
    var day = date.getDate();
    var month = date.getMonth()+1;
    var year = date.getYear()-100;
    var hours = date.getHours();
    var minutes = "0" + date.getMinutes();
    var seconds = "0" + date.getSeconds();

    var formattedTime = day + '.' + month + '.' + year + ' ' +
        hours + ':' + minutes.substr(minutes.length-2) + ':' + seconds.substr(seconds.length-2);

    return formattedTime;
}

function getcomments(atmId){
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