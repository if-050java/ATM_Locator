var map;                //Map element
var userPosition;
var userPositionMarker;
var USER_MARKER_TITLE = "My position"
var markers = [];
var radius;
var circle = new google.maps.Circle();
var infowindow;
//Create map on load page
google.maps.event.addDomListener(window, 'load', initializeMap);
document.onclick = function () {
    hideMenu();
}

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
    autocompleteMap();
    //if user has cookies with his position set map to this position else get position from browser
    if ($.cookie("position")) {
        setLocationByLatLng(JSON.parse($.cookie("position")));
    } else {
        getLocation();
    }

    overlay = new google.maps.OverlayView();
    overlay.draw = function () {
    };
    overlay.setMap(map);

    //getting favorites
    if (getFavorites != undefined) {
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
function hidePopover(element) {
    $('#' + element).popover("destroy");
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
    var showAtms = $("#ATMs").prop("checked");
    var showOffices = $("#offices").prop("checked");
    var showOtherBanks = $("#showOtherBanks").prop("checked");
    radius = parseInt($("#distance").val());
    var data = {
        networkId: networkId,
        bankId: bankId,
        radius: radius,
        userLat: userPosition.lat,
        userLng: userPosition.lng,
        showAtms: showAtms,
        showOffices: showOffices
    };
    console.log((bankId && networkId && showOtherBanks));
    if (!networkId || networkId == 0) delete data.networkId;
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
        var atmId = ATMs[i].id;
        addMarker(atmId, {"lat": atmPosition.latitude, "lng": atmPosition.longitude}, atmDescription, atmIcon);
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
function addMarker(id, position, title, icon) {
    var markerPos = new google.maps.LatLng(position.lat, position.lng);
    var marker = new RichMarker({
        id: id,
        position: markerPos,
        map: map,
        title: title,
        draggable: false,
        flat: true,
        content: '<div style="position:relative" id="' + id + '" class="favorite_marker">' +
            '<img src="' + getHomeUrl() + 'resources/images/' + icon + '" width="32" height="32" alt=""  oncontextmenu="markerMenu(event, ' + id + ')"/>' +
            '</div>'
    });
    marker.setMap(map);

    google.maps.event.addListener(marker, 'rightclick', function (event) {
        var pos = getMousePos(event);
        markerMenu(pos.x, pos.y, marker.id);
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
function deleteMarkers() {
    for (i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}

//add marker menu in position x, y
function markerMenu(event, ATMId) {
    var x = event.pageX;
    var y = event.pageY;
    $("#defaultMarkerMenu").attr("atmid", ATMId);
    $("#defaultMarkerMenu")
        .css({top: y + "px", left: x + "px"})
        .show();
}

//hide marker menu
function hideMenu() {
    $(".popup-menu").hide();
}

function setPositionCookies() {
    $.cookie("position", JSON.stringify(userPosition));
}
function autocompleteMap() {
    $("#userAddress").geocomplete()
        .bind("geocode:result", function () {
            $("#findLocation").trigger("click");
        });
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
        min: 50,
        forcestepdivisibility: 'none',
        max: 5000,
        step: 250
    });


});
$(document).on('click', '#banksDropdown li a', function (e) {
    e.preventDefault();
    $("#banksDropdownInput").val($(this).text());
    $("#banksDropdownInput").prop("bankId", $(this).attr('href'));
});

