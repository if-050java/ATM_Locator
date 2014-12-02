/**
 * Created by Olavin on 03.12.2014.
 */
var SUCCESS_MESSAGE = "<strong>Success!</strong> <span>Your data is saved successfully!</span>";
var INFO_MESSAGE = "<strong>Info!</strong> <span>Nothing to update!</span>";
var WARNING_MESSAGE = "<strong>Warning!</strong> <span>See the validation rules!</span>";
var ERROR_MESSAGE = "<strong>Error!</strong> <span>Error saving data!</span>";

function showAlert(className, html) {
    var element = $("div.alert");
    element.removeClass();
    element.addClass(className);
    element.children(".close").nextAll().remove();
    element.append(html);
    element.show();
}

$(document).ready(function () {

    showAlert("alert alert-info", INFO_MESSAGE);

    function changeImage(input, image) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $(image).attr('src', e.target.result);
            }
            reader.readAsDataURL(input.files[0]);
        }
    }

    $("#imageLogo").change(function () {
        changeImage(this, '#bankLogo');
    });

    $("#iconAtmFile").change(function () {
        changeImage(this, '#bankAtmFile');
    });

    $("#iconOfficeFile").change(function () {
        changeImage(this, '#bankOffice');
    });

});
