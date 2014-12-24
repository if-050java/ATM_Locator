var userPosition;
var userPositionMarker;

//Getting location from browser
function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(setLocation);
    }
};

//Setting current location to position received from browser
function setLocation(position) {
    userPosition = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
    setLocationByLatLng(userPosition);
};

//Setting current location to position defined by LatLng value
function setLocationByLatLng(position) {
    deleteMarker(userPositionMarker);
    addUserMarker(position)
}

function addUserMarker(position){
    userPosition = new google.maps.LatLng(position.lat(), position.lng());
    var iconUrl = "/resources/images/userMarker.ico";
    var userAva = "/resources/images/defaultUserAvatar.jpg";
    userPositionMarker = new RichMarker({
        position: userPosition,
        map: map,
        draggable: false,
        flat: true,
        content: '<div style="position:relative">' +
        '<img style = "z-index:1000; position: absolute;" src="' + iconUrl + '" width=64 height=64/>' +
        '<img style = "z-index:999; position: absolute; left:16px; top:8px" src="' + userAva + '" width=32 height=32/>' +
        '</div>'
    });
    map.panTo(userPosition);
    setPositionCookies();
}

//Setting user position by address
function setLocationByAddress() {
    var userAddress = $("#userAddress").val();
    if (userAddress != "") {
        var geocoder = new google.maps.Geocoder();
        geocoder.geocode({'address': userAddress}, setMapByGeocode)
    }
    return false;
};

//Setting user position by google geocoder
function setMapByGeocode(data, status) {
    if (status == google.maps.GeocoderStatus.OK) {//if google found this address
        deleteMarker(userPositionMarker);
        var position = new google.maps.LatLng(data[0].geometry.location.lat(), data[0].geometry.location.lng());
        addUserMarker(position);
    } else {//if address is invalid or google didn't find it
        $('#userAddress').attr("data-content", "Can't find this address");
        $('#userAddress').popover("show");
    }
}

//Setting cookies about user's position
function setPositionCookies() {
    var cookiesPosition = {lat: userPosition.lat(), lng: userPosition.lng()};
    $.cookie("position", JSON.stringify(cookiesPosition));
}
