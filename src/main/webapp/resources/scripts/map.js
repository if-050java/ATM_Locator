var map;                //Map element
var radius;
var circle = new google.maps.Circle();
var infowindow;
var showOther = false;
//Create map on load page
google.maps.event.addDomListener(window, 'load', initializeMap);

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

    //getting favorites
    if (typeof getFavorites != 'undefined') {
        getFavorites();
    }
}

//hide popower about finding address
function hidePopover(element) {
    $('#' + element).popover("destroy");
}
function checkShowOtherBanks(bankPropId) {
    var popover = $('#label');
    var showOthers = $("#showOtherBanks");
    popover.popover({trigger: 'hover'});
    if (!bankPropId) {
        console.log(bankPropId + "if");
        showOthers.removeAttr("checked");
        showOthers.attr("disabled","disabled");
        popover.popover('enable');
    } else {
        showOthers.removeAttr("disabled");
        popover.popover('disable');
    }
}
function showHideCheckboxOtherBanks(){
    var bankPropId = $("#banksDropdownInput").prop("bankId");
    checkShowOtherBanks(bankPropId);
}
//Get ATMs from server by filter
function updateFilter() {

    var networkId = $("#networksDropdownInput").prop("networkId");
    var bankId = $("#banksDropdownInput").prop("bankId");
    var bankNetworkId = $("#banksDropdownInput").prop("networkId");
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
    if (networkId == 0 && !showOtherBanks) {
        delete data.networkId;
    } else if(showOtherBanks) {
        data.networkId = bankNetworkId;
    }
    if (!bankId || (showOtherBanks && bankId && networkId)) delete data.bankId;
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
        addMarker(ATMs[i], atmId, {"lat": atmPosition.latitude, "lng": atmPosition.longitude}, atmDescription, atmIcon);
    }

    //exclude favorites from result
    if(typeof deleteFaworitesFromDefaultMarkers != "undefined"){
        deleteFaworitesFromDefaultMarkers();
    }

    var circleOptions = {
        strokeColor: "#c4c4c4",
        strokeOpacity: 0.65,
        strokeWeight: 1,
        fillColor: "#198CFF",
        fillOpacity: 0.15,
        map: map,
        center: userPosition,
        radius: radius

    };
    circle.setMap(null);
    circle = new google.maps.Circle(circleOptions);
    var circleBounds = circle.getBounds();
    myFitBounds(map, circleBounds);
    // map.setZoom(map.getZoom()+1);
};

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
            $("#banksDropdownInput").prop("bankId", bank.data);
            $("#banksDropdownInput").prop("networkId", bank.network_id);
            console.log("!!"+bank.network_id);
            showHideCheckboxOtherBanks();
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
        sources.push({ value: $(this).text(), data: $(this).attr("href"), network_id:$(this).attr("networkId")})
    });
    return sources;
};

//change filters by network and bank
$(document).ready(function () {
    showHideCheckboxOtherBanks();
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
                    banksList.append('<li><a href="' + bank.id + '" networkId="' + bank.network.id + '">' + bank.name + '</a></li>');
                });
                autocompleteBanks();
                showHideCheckboxOtherBanks();
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
    $("#banksDropdownInput").prop("networkId", $(this).attr('networkId'));
    showHideCheckboxOtherBanks();
});

