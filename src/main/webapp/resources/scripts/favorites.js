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
        favoriteElement = '<a class="list-group-item" atmid="' + value.id +
                          '" oncontextmenu="favMenu(event, '+ value.id +
                          ')" onclick="panToFavorite(event)">' + value.address +
                          '</a>';
        jQuery("#favorites_list").append(jQuery.parseHTML(favoriteElement))
        var favoritePosition = value.geoPosition;
        addFavoriteMarker(value, value.id, {"lat": favoritePosition.latitude, "lng": favoritePosition.longitude}, value.address, value.bank.iconAtm);
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
function addFavoriteMarker(atm) {
    var markerPos = new google.maps.LatLng(atm.geoPosition.latitude, atm.geoPosition.longitude);
    var favoiteMarker = new RichMarker({
        id: atm.id,
        position: markerPos,
        map: map,
        draggable: false,
        flat: true,
        anchorPoint: {x: 0, y: -32},
        content: '<div style="position:relative" id="' + atm.id + '" class="favorite_marker">'+
                     '<img src="' + getHomeUrl() + 'resources/images/'+ atm.bank.iconAtm +'" width="32" height="32" alt=""  oncontextmenu="favMenu(event, '+ atm.id +')"/>'+
                    '<img style="position:absolute; left:24px; top:-8px" src="'+getHomeUrl()+'resources/images/favorite.ico" width="16" height="16" alt=""/>'+
                 '</div>'
    });

    var tollTipContent = '<strong>' + atm.bank.name + '</strong><br>'+
                         '<div>' + atm.address + '</div>'
    if(atm.commentsCount > 0){
        tollTipContent += '<div><a href="#" onclick="showComments()">Comments...</a></div>'
    }

    google.maps.event.addListener(favoiteMarker, 'click', function(event){
        if(infowindow != undefined) {
            infowindow.close();
        }
        infowindow = new google.maps.InfoWindow({
            content: tollTipContent,
            maxWidth: 135
        });
        infowindow.open(map, favoiteMarker);
    });

    favoriteMarkers.push(favoiteMarker);
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

//animate marker on panTo
function jampedMarker(id){
    jQuery("#"+id).addClass('animated bounce')
             .one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
                     jQuery(this).removeClass('animated bounce');
                });
}
