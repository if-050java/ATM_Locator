var map;                //Map element
var userPosition;
var userPositionMarker;
var USER_MARKER_TITLE = "My position"
var markers = [];
var overlay;

//Create map on load page
google.maps.event.addDomListener(window, 'load', initializeMap);
document.onclick = hideMenu;

//get mouse position in window on click
function getMousePos(event) {
    event = event || window.event;
    var mousPositionOnClickX = event.pageX;
    var mousPositionOnClickY = event.pageY;
    $("#userAddress").val(mousPositionOnClickX);
    return {x: mousPositionOnClickX, y: mousPositionOnClickY};
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

    overlay = new google.maps.OverlayView();
    overlay.draw = function() {};
    overlay.setMap(map);

    //getting favorites
    if(getFavorites != undefined){
        getFavorites();
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

//hide popower about finding address
function hidePopover(element){
    $('#'+element).popover("destroy");
}

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

//Get ATMs from server by filter
function updateFilter() {
    var networkId = $("#networksDropdownInput").prop("networkId");
    var bankId = $("#banksDropdownInput").prop("bankId");
    var distance = $("#distance").val();
    var data = {
        networkId: networkId,
        bankId: bankId,
        radius: distance,
        userLat: userPosition.lat,
        userLng: userPosition.lng
    };
    if (!networkId) delete data.networkId;
    if (!bankId) delete data.bankId;
    $.ajax({
        url: getHomeUrl() + "map/getATMs",
        data: data,
        type: "GET",
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
        var atmIcon = ATMs[i].bank.iconAtm;
        var atmId = ATMs[i].id;
        addMarker(atmId, {"lat": atmPosition.latitude, "lng": atmPosition.longitude}, atmDescription, atmIcon);
    }
};

//Adding marker to map
function addMarker(id, position, title, icon) {
    var marker = new google.maps.Marker({
        id : id,
        position: position,
        map: map,
        title: title,
        icon: getHomeUrl() + "resources/images/" + icon
    });
    marker.setMap(map);

    google.maps.event.addListener(marker, 'rightclick', function(event){
        var pos = getMousePos(event);
        markerMenu(pos.x,pos.y, marker.id);
    });

    markers.push(marker);
}

//Deleting marker from map
function deleteMarker(marker) {
    if (marker != null) {
        marker.setMap(null);
    }
    ;
}

//delete all ATM markers from map
function deleteMarkers(){
    for(i = 0; i < markers.length; i++){
        markers[i].setMap(null);
    }
    markers = [];
}

//add marker menu in position x, y
function markerMenu(x,y, ATMId){
    $("#defaultMarkerMenu").attr("atmid", ATMId);
    $("#defaultMarkerMenu")
        .css({top: y + "px", left: x + "px"})
        .show();
}

//hide marker menu
function hideMenu(){
    $(".popup-menu").hide();
}

function setPositionCookies() {
    $.cookie("position", JSON.stringify(userPosition));
}
function autocompleteBanks() {
    $('#banksDropdownInput').autocomplete({
        lookup: getBanks(),
        onSelect: function (bank) {
            $("#banksDropdownInput").prop("bankId", bank.data)
        },
        lookupFilter: function (suggestion, query, queryLowerCase) {
            return suggestion.value.toLowerCase().indexOf(queryLowerCase) === 0;
        },
        lookupLimit: 10,
        noCache: true
    });
}

function getBanks() {
    var sources = [];
    $("#banksDropdown li a").each(function () {
        sources.push({ value: $(this).text(), data: $(this).attr("href")})
    });
    return sources;
};

//change filters by network and bank
$(document).ready(function () {
    autocompleteBanks();
    $('#networksDropdown li a').click(function (e) {
        e.preventDefault();
        $("#networksDropdownInput").val($(this).text());
        $("#networksDropdownInput").prop("networkId", $(this).attr('href'));
        var network_id = $(this).attr('href');
        $.getJSON(getHomeUrl() + "map/getBanksByNetwork", {network_id: network_id }, function (banks) {
                $("#banksDropdown").empty();
                $("#banksDropdownInput").val("");
//                if (network_id != 0) {
//                    $("#banksDropdownInput").val(banks[0].name);
//                    $("#banksDropdownInput").prop("bankId", banks[0].id);
//                }
                $.each(banks, function (i, bank) {
                    $("#banksDropdown").append('<li><a href="' + bank.id + '">' + bank.name + '</a></li>');
                });
                autocompleteBanks();
            }
        );
    });


    $("#distance").TouchSpin({
        initval: 500,
        min: 100,
        max: 5000,
        step: 500
    });


});
$(document).on('click', '#banksDropdown li a', function (e) {
    e.preventDefault();
    $("#banksDropdownInput").val($(this).text());
    $("#banksDropdownInput").prop("bankId", $(this).attr('href'));
});

//======================================================================================================================
//Adding favorites for user
//======================================================================================================================
function addFavorite(ATM){
    $.ajax({
        url: getHomeUrl() + "users/favorites/" + ATM.id,
        type : "PUT",
        context: document.body,
        dataType: "json",
        success: function(){
            console.log("додано")
        }
    })
}
