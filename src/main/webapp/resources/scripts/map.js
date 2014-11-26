var map;                //Map element
var userPositionMarker;
var USER_MARKER_TITLE = "My position"

//Create map on load page
google.maps.event.addDomListener(window, 'load', initializeMap);
function initializeMap() {
    getLocation();
    var defaultMapOptions = {
        center: {lat : 48.9501, lng : 24.701},
        zoom: 14
    };
    map = new google.maps.Map(document.getElementById('map_container'), defaultMapOptions);
}

//Getting location from browser
function getLocation(){
    if(navigator.geolocation){
        navigator.geolocation.getCurrentPosition(setLocation);
    }
};

//Setting current location in position received from browser
function setLocation(position){
    currentPosition = { lat: position.coords.latitude, lng: position.coords.longitude};
    deleteMarker(userPositionMarker);
    userPositionMarker = new google.maps.Marker({
        position: currentPosition,
        map: map,
        title: USER_MARKER_TITLE
    });
    userPositionMarker.setMap(map);
    map.panTo(currentPosition);
};

//Seting user position by address
function setLocationByAddress(){
    var userAddress = $("#userAddress").val();
    if(userAddress != ""){
        var geocoder = new google.maps.Geocoder();
        geocoder.geocode({'address' : userAddress}, setMapByGeocode)
    }
    return false;
};
//Seting user position by google geocoder
function setMapByGeocode(data, status){
    if(status == google.maps.GeocoderStatus.OK){//if google found this address
        deleteMarker(userPositionMarker);
        currentPosition = {lat : data[0].geometry.location.lat(), lng : data[0].geometry.location.lng()};
        userPositionMarker = new google.maps.Marker({
            position: currentPosition,
            map: map,
            title: USER_MARKER_TITLE
        });
        userPositionMarker.setMap(map);
        map.panTo(currentPosition);
    } else {//if address is invalid or google didn't find it
        window.alert("Address is invalid");
    }
}
//Deleting marker from map
function deleteMarker(marker){
    if(marker != null){
        marker.setMap(null);
    };
}