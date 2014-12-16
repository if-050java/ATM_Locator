var favoriteMarkers = [];

//send request for favorites t oserver
function getFavorites(){
    jQuery.ajax({
        url: getHomeUrl()+"favorites/",
        type : "GET",
        context: document.body,
        dataType: "json",
        statusCode: {
            200: function(response) {
                showFavorites(response);
            }
        }
    })
}

//add favorites markers and elements to favorites list
function showFavorites(favorites){
    clearFavorites();
    favorites.forEach(function(value){
        favoriteElement = '<a class="list-group-item" atmid="' + value.id + '" oncontextmenu="favMenu(event, '+ value.id +')" onclick="panToFavorite(event)">' + value.address + '</a>';
        jQuery("#favorites_list").append(jQuery.parseHTML(favoriteElement))
        var favoritePosition = value.geoPosition;
        addFavoriteMarker(value.id, {"lat": favoritePosition.latitude, "lng": favoritePosition.longitude}, value.address, value.bank.iconAtm);
    })
}

//clear all favorites from map and list
function clearFavorites(){
    jQuery("#favorites_list").empty();
    favoriteMarkers.forEach(function(value){
        value.setMap(null);
    })
    favoriteMarkers = [];
}

//Adding favorite marker to map
function addFavoriteMarker(id, position, title, icon) {

    var markerPos = new google.maps.LatLng(position.lat, position.lng);
    marker = new RichMarker({
        id: id,
        position: markerPos,
        map: map,
        draggable: false,
        flat: true,
        content:'<div style="position:relative" id="' + id + '" class="favorite_marker">'+
                    '<img src="'+getHomeUrl()+'resources/images/'+ icon +'" width="32" height="32" alt=""  oncontextmenu="favMenu(event, '+ id +')"/>'+
                    '<img style="position:absolute; left:24px; top:-8px" src="'+getHomeUrl()+'resources/images/favorite.ico" width="16" height="16" alt=""/>'+
                '</div>'
    });
    favoriteMarkers.push(marker);
}

//show context menu on favorite marker and element of favorites list
function favMenu(event, ATMId){
    var x = event.pageX;
    var y = event.pageY;
    jQuery("#favoriteMarkerMenu").attr("atmid", ATMId);
    jQuery("#favoriteMarkerMenu")
        .css({top: y + "px", left: x + "px"})
        .show();
    event.preventDefault();
}

//add current atm to user favorites
function addFavorite(){
    var ATMId = jQuery("#defaultMarkerMenu").attr("atmid");
    jQuery.ajax({
        url: getHomeUrl() + "favorites/" + ATMId,
        type: "PUT",
        context: document.body,
        dataType: "json",
        statusCode: {
            200: function () {
                markers.forEach(function(value){
                    if (value.id == ATMId) {
                        value.setMap(null);
                        var position = {lat: value.position.lat(), lng: value.position.lng()}
                        addFavoriteMarker(value.id, position, value.title, value.icon);
                        getFavorites();
                    }
                })
            }
        }
    })
    hideMenu();
}

//delete current atm from user favorites
function deleteFavorite(){
    var ATMId = jQuery("#favoriteMarkerMenu").attr("atmid");
    jQuery.ajax({
        url: getHomeUrl() + "favorites/" + ATMId,
        type: "DELETE",
        context: document.body,
        dataType: "json",
        statusCode: {
            200: function () {
                favoriteMarkers.forEach(function(value, index){
                    if(value.id == ATMId){
                        value.setMap(null);
                        getFavorites();
                    }
                })
            }
        }
    })
    hideMenu();
}

//canter map on selected favorite
function panToFavorite(event){
    atmId = event.target.getAttribute("atmid")
    favoriteMarkers.forEach(function(value){
        if(value.id == atmId){
            map.panTo(value.position);
            jampedMarker(atmId);
        }
    })
}

function jampedMarker(id){
    $("#"+id).addClass('animated bounce')
             .one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
                     $(this).removeClass('animated bounce');
                });
}
