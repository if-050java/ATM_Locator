var map;                //Map element
var userPosition;
var userPositionMarker;
var USER_MARKER_TITLE = "My position"
var markers = [];

//Create map on load page
google.maps.event.addDomListener(window, 'load', initializeMap);
document.onclick = hideMenu;

//get mouse position in window on click
function getMousePos(event){
    event = event || window.event;
    var mousPositionOnClickX = event.pageX;
    var mousPositionOnClickY = event.pageY;
    $("#userAddress").val(mousPositionOnClickX);
	return {x:mousPositionOnClickX, y:mousPositionOnClickY};
}

//add map to page and set to default position
function initializeMap() {
    var defaultMapOptions = {
        center: {lat: 48.9501, lng: 24.701},
        zoom: 14
    };
    map = new google.maps.Map(document.getElementById('map_container'), defaultMapOptions);

    //if user has cookies with his position set map to this position else get position from browser
    if ($.cookie("position")) {
        setLocationByLatLng(JSON.parse($.cookie("position")));
    } else {
        getLocation();
    }
}

//Getting location from browser
function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(setLocation);
    }
};

//Setting current location to position received from browser
function setLocation(position) {
    userPosition = { lat: position.coords.latitude, lng: position.coords.longitude};
    setLocationByLatLng(userPosition);
};

//seting current location to position defined by LatLng value
function setLocationByLatLng(position) {
    userPosition = position;
    deleteMarker(userPositionMarker);
    userPositionMarker = new google.maps.Marker({
        position: userPosition,
        map: map,
        title: USER_MARKER_TITLE
    });
    userPositionMarker.setMap(map);
    map.panTo(userPosition);
    setPositionCookies();
}

//Seting user position by address
function setLocationByAddress() {
    var userAddress = $("#userAddress").val();
    if (userAddress != "") {
        var geocoder = new google.maps.Geocoder();
        geocoder.geocode({'address': userAddress}, setMapByGeocode)
    }
    return false;
};

//Seting user position by google geocoder
function setMapByGeocode(data, status) {
    if (status == google.maps.GeocoderStatus.OK) {//if google found this address
        deleteMarker(userPositionMarker);
        userPosition = {lat: data[0].geometry.location.lat(), lng: data[0].geometry.location.lng()};
        userPositionMarker = new google.maps.Marker({
            position: userPosition,
            map: map,
            title: USER_MARKER_TITLE
        });
        userPositionMarker.setMap(map);
        map.panTo(userPosition);
        setPositionCookies();
    } else {//if address is invalid or google didn't find it
        $('#userAddress').attr("data-content", "Can't find this address");
        $('#userAddress').popover("show");
    }
}

//Deleting marker from map
function deleteMarker(marker) {
    if (marker != null) {
        marker.setMap(null);
    }
    ;
}

//Get ATMs from server by filter
function updateFilter() {
    var bankId = $("#banksDropdownInput").prop("bankId");
    $.ajax({
        url: getHomeUrl()+"map/getATMs?id="+bankId+"&userLat="+userPosition.lat+"&userLng="+userPosition.lng+"&radius="+$("#distance").val(),
        type : "GET",
        context: document.body,
        dataType: "json",
        success: showAtms
    })
}

//Receiving data about markers from server and adding marker to map
function showAtms(data) {
    deleteMarkers();
    var ATMs = data;
    for (var i = 0; i < ATMs.length; i++) {
        var atmPosition = ATMs[i].geoPosition;
        var atmDescription = data.name + "\n" + ATMs[i].address;
        addMarker({"lat": atmPosition.latitude, "lng": atmPosition.longitude}, atmDescription);
    }
};

//Adding marker to map
function addMarker(position, title) {
    var marker = new google.maps.Marker({
        position: position,
        map: map,
        title: title,
        icon: getHomeUrl()+"resources/images/privat_icon.jpg"
    });
    marker.setMap(map);

/*    google.maps.event.addListener(marker, 'rightclick', function(event){
		var pos = getMousePos(event);
        markerMenu(pos.x,pos.y);
    });*/

    markers.push(marker);
}

function deleteMarkers(){
    for(i = 0; i < markers.length; i++){
        markers[i].setMap(null);
    }
}

//add marker menu in position x, y
function markerMenu(x,y){
    $(".popup-menu")
        .css({top: y + "px", left: x + "px"})
		.show();
}

//hide marker menu
function hideMenu(){
	$(".popup-menu").hide();
}

//hide popower about finding address
function hidePopover(element){
    $('#'+element).popover("destroy");
}

//add cookies about user position(lat lng)
function setPositionCookies(){
    $.cookie("position", JSON.stringify(userPosition));
    google.maps.event.addListener(marker, 'click', function removeMarker() {
        if (window.confirm("Are you sure?")) {//If this marker not in current position marker then remove
            marker.setMap(null);
            //   removeReq(marker.getPosition());
        }
    });
}

function hidePopover(element) {
    $('#' + element).popover("destroy");
}

function setPositionCookies() {
    $.cookie("position", JSON.stringify(userPosition));
}
//change filters by network and bank
$(document).ready(function () {
    $('#networksDropdown li a').click(function (e) {
        e.preventDefault();
        $("#networksDropdownInput").val($(this).text());
        var network_id = $(this).attr('href');
        $.getJSON(getHomeUrl() + "map/getBanksByNetwork", {network_id: network_id }, function (banks) {
                $("#banksDropdown").empty();
                $("#banksDropdownInput").val(banks[0].name);
                $("#banksDropdownInput").prop("bankId", banks[0].id);
                $.each(banks, function (i, bank) {
                    $("#banksDropdown").append('<li><a href="' + bank.id + '">' + bank.name + '</a></li>');
                });
            }
        );
    });
});
$(document).on('click', '#banksDropdown li a', function (e) {
    e.preventDefault();
    $("#banksDropdownInput").val($(this).text());
    $("#banksDropdownInput").prop("bankId", $(this).attr('href'));
});

