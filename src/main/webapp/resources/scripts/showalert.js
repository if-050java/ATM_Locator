/**
 * Created by Olavin on 12.01.2015.
 */
function showAlert(className, html) {
    var message = $('<div class="errormessage '+className+'" style="display: none;">');
    var close = $('<button type="button" class="close" data dismiss="alert">&times</button>');
    message.append(close); // adding the close button to the message
    message.append(html); // adding the error response to the message
    message.appendTo($('body')).fadeIn(10).delay(2000).fadeOut(300);
}

