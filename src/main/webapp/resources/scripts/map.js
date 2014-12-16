var map;                //Map element
var userPosition;
var userPositionMarker;
var USER_MARKER_TITLE = "My position"
var markers = [];
var radius;
var circle = new google.maps.Circle();
//Create map on load page
google.maps.event.addDomListener(window, 'load', initializeMap);
document.onclick = hideMenu;

//autocentering map
function myFitBounds(myMap, bounds) {
    myMap.fitBounds(bounds); // calling fitBounds() here to center the map for the bounds

    var overlayHelper = new google.maps.OverlayView();
    overlayHelper.draw = function () {
        if (!this.ready) {
            var extraZoom = getExtraZoom(this.getProjection(), bounds, myMap.getBounds());
            if (extraZoom > 0) {
                myMap.setZoom(myMap.getZoom() + extraZoom);
            }
            this.ready = true;
            google.maps.event.trigger(this, 'ready');
        }
    };
    overlayHelper.setMap(myMap);
}

function getExtraZoom(projection, expectedBounds, actualBounds) {

    // in: LatLngBounds bounds -> out: height and width as a Point
    function getSizeInPixels(bounds) {
        var sw = projection.fromLatLngToContainerPixel(bounds.getSouthWest());
        var ne = projection.fromLatLngToContainerPixel(bounds.getNorthEast());
        return new google.maps.Point(Math.abs(sw.y - ne.y), Math.abs(sw.x - ne.x));
    }

    var expectedSize = getSizeInPixels(expectedBounds),
        actualSize = getSizeInPixels(actualBounds);

    if (Math.floor(expectedSize.x) == 0 || Math.floor(expectedSize.y) == 0) {
        return 0;
    }

    var qx = actualSize.x / expectedSize.x;
    var qy = actualSize.y / expectedSize.y;
    var min = Math.min(qx, qy);

    if (min < 1) {
        return 0;
    }

    return Math.floor(Math.log(min) / Math.LN2 /* = log2(min) */);
}

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
    var networkId = $("#networksDropdownInput").prop("networkId");
    var bankId = $("#banksDropdownInput").prop("bankId");
    var showAtms = $("#ATMs").prop("checked");
    var showOffices = $("#offices").prop("checked");
    console.log($("#distance").val());
    radius = parseInt($("#distance").val());
    console.log(radius);
    var data = {
        networkId: networkId,
        bankId: bankId,
        radius: radius,
        userLat: userPosition.lat,
        userLng: userPosition.lng,
        showAtms: showAtms,
        showOffices: showOffices
    };
    if (!networkId || networkId == 0 ) delete data.networkId;
    if (!bankId) delete data.bankId;
    $.ajax({
        url: getHomeUrl() + "map/getATMs",
        data: data,
        type: "GET",
        context: document.body,
        dataType: "json",
        success: displayAtms
    })
}

//Receiving data about markers from server and adding marker to map
function displayAtms(data) {
    deleteMarkers();
    var ATMs = data;
    for (var i = 0; i < ATMs.length; i++) {
        var atmPosition = ATMs[i].geoPosition;
        var atmDescription = data.name + "\n" + ATMs[i].address;
        var atmIcon = ATMs[i].bank.iconAtm;
        addMarker({"lat": atmPosition.latitude, "lng": atmPosition.longitude}, atmDescription, atmIcon);
    }

    var circleOptions = {
        strokeColor: "#c4c4c4",
        strokeOpacity: 0.35,
        strokeWeight: 0,
        fillColor: "#198CFF",
        fillOpacity: 0.35,
        map: map,
        center: userPosition,
        radius: radius,

    };
    circle.setMap(null);
    circle = new google.maps.Circle(circleOptions);
    console.log(map.getZoom());
    var circleBounds = circle.getBounds();
    myFitBounds(map, circleBounds);
   // map.setZoom(map.getZoom()+1);
};

//Adding marker to map
function addMarker(position, title, icon) {
    var marker = new google.maps.Marker({
        position: position,
        map: map,
        title: title,
        icon: getHomeUrl() + "resources/images/" + icon
    });
    marker.setMap(map);

    /*    google.maps.event.addListener(marker, 'rightclick', function(event){
     var pos = getMousePos(event);
     markerMenu(pos.x,pos.y);
     });*/

    markers.push(marker);
}

function deleteMarkers() {
    for (i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
}

//add marker menu in position x, y
function markerMenu(x, y) {
    $(".popup-menu")
        .css({top: y + "px", left: x + "px"})
        .show();
}

//hide marker menu
function hideMenu() {
    $(".popup-menu").hide();
}

//hide popower about finding address
function hidePopover(element) {
    $('#' + element).popover("destroy");
}

//add cookies about user position(lat lng)
function setPositionCookies() {
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
    var networksInput = $("#networksDropdownInput");
    var networksList = $("#networksDropdown");
    var banksInput = $("#banksDropdownInput");
    var banksList = $("#banksDropdown");
    networksInput.val(networksList.find("a").first().text());
    networksInput.prop("networkId", networksList.find("a").first().attr('href'));
    networksList.find("a").click(function (e) {
        e.preventDefault();
        networksInput.val($(this).text());
        networksInput.prop("networkId", $(this).attr('href'));
        var network_id = $(this).attr('href');
        $.getJSON(getHomeUrl() + "map/getBanksByNetwork", {network_id: network_id }, function (banks) {
                banksList.empty();
                banksInput.val("");
                banksInput.removeProp("bankId");
                $.each(banks, function (i, bank) {
                    banksList.append('<li><a href="' + bank.id + '">' + bank.name + '</a></li>');
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

