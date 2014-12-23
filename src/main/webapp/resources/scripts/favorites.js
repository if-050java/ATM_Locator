var favoriteMarkers = [];

jQuery(document).ready(function(){
    //change scroll in favorites
    jQuery("#favorites_list").niceScroll();

    //Show/hide favorites by checker
    jQuery("#showFavorites").change(function() {
        if(jQuery("#showFavorites").prop("checked")) {
            getFavorites();
        } else {
            clearFavoriteMarkers();
        }
    });

    jQuery("#favorites_list").on("click", ".list-group-item", function(event){
        atmId = event.currentTarget.getAttribute("id")
        favoriteMarkers.forEach(function(value){
            if(value.id == atmId){
                map.panTo(value.position);
                jampedMarker(atmId);
            }
        })
    })

    //show context menu on favorite list item
    jQuery("#favorites_list").on("contextmenu", ".list-group-item", function(event){
        favMenu(event);
    })
});

//send request for favorites to server and process response
function getFavorites(){
    jQuery.ajax({
        url: getHomeUrl()+"favorites/",
        type : "GET",
        context: document.body,
        dataType: "json",
        statusCode: {
            200: function(response) {
                showFavorites(response);                //add favorites to map and favorites list
                deleteFaworitesFromDefaultMarkers();    //delete default marker if atm  is in favorites
            }
        }
    })
}

//add favorites markers and elements to favorites list
function showFavorites(favorites){
    clearFavorites();//clear map and favorites list
    favorites.forEach(function(favoriteAtm){
        var iconName;
        if(favoriteAtm.type == "IS_OFFICE" || favoriteAtm.type == "IS_ATM_OFFICE"){
            iconName = favoriteAtm.bank.iconOffice;
        }else{
            iconName = favoriteAtm.bank.iconAtm;
        }
        var iconUrl = getHomeUrl() + 'resources/images/'+ iconName;
        var atmId = favoriteAtm.id;
        var atmAddress = favoriteAtm.address;
        favoriteElement =   '<div class="list-group-item" id="' + atmId +'">'+
                                '<img class="img-rounded" style="max-width: 20%" src="' + iconUrl + '"/>'+
                                '<div class="favorite-item-address">' + atmAddress + '</div>' +
                            '</div>';
        jQuery("#favorites_list").append(jQuery.parseHTML(favoriteElement));//append favorite element to list
        if(jQuery("#showFavorites").prop("checked")){//add marker if need
            addFavoriteMarker(favoriteAtm);
        }
    })
}

//Delete default marker if this atm is in favorites
function deleteFaworitesFromDefaultMarkers(){
    markers.forEach(function(defaultMarker, index){
        favoriteMarkers.forEach(function(favoriteMarker){
            if(defaultMarker.id == favoriteMarker.id ){
                defaultMarker.setMap(null);
            }
        })
    })
}

//clear all favorites from map and list
function clearFavorites(){
    clearFavoritesList();
    clearFavoriteMarkers();

}

function clearFavoritesList(){
    jQuery("#favorites_list").empty();
}

//clear all favorite markers from map
function clearFavoriteMarkers(){
    favoriteMarkers.forEach(function(favoriteMarker){
        markers.forEach(function(defaultMarker){
            if(favoriteMarker.id == defaultMarker.id){
                defaultMarker.setMap(map);
            }
        })
        favoriteMarker.setMap(null);
    })
    favoriteMarkers = [];
}

//Adding favorite marker to map
function addFavoriteMarker(atm) {
    var markerPos = new google.maps.LatLng(atm.geoPosition.latitude, atm.geoPosition.longitude);
    var favoiteMarker = new RichMarker({
        id: atm.id,
        commentsCount : atm.commentsCount,
        position: markerPos,
        map: map,
        draggable: false,
        flat: true,
        anchorPoint: {x: 0, y: -32},
        content: '<div style="position:relative" id="' + atm.id + '" class="favorite favorite_marker"  oncontextmenu="favMenu(event)">'+
                     '<img src="' + getHomeUrl() + 'resources/images/'+ ((atm.type == "IS_OFFICE" || atm.type == "IS_ATM_OFFICE") ? atm.bank.iconOffice :  atm.bank.iconAtm) + '" width="32" height="32" alt=""/>'+
                    '<img style="position:absolute; left:24px; top:-8px" src="'+getHomeUrl()+'resources/images/favorite.ico" width="16" height="16" alt=""/>'+
                 '</div>'
    });



    google.maps.event.addListener(favoiteMarker, 'click', function(event){
        var tollTipContent = '<strong>' + atm.bank.name + '</strong><br>'+
            '<div>' + atm.address + '</div>'
        if(favoiteMarker.commentsCount > 0){
            tollTipContent += '<div><a href="#" atmid="' + atm.id + '" id="showComments">Comments(' + favoiteMarker.commentsCount + ')...</a></div>'
        }

        if(infowindow != undefined) {
            infowindow.close();
        }
        infowindow = new google.maps.InfoWindow({
            content: tollTipContent,
            maxWidth: 135
        });
        infowindow.open(map, favoiteMarker);
        setTimeout(initCommentsClick, 200);
    });
    favoriteMarkers.push(favoiteMarker);
}

//show context menu on favorite marker and element of favorites list
function favMenu(event){
    var x = event.pageX;
    var y = event.pageY;
    var atmid = event.currentTarget.getAttribute("id")
    jQuery("#favoriteMarkerMenu").attr("atmid", atmid);
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
                        if(jQuery("#showFavorites").prop("checked")){
                            value.setMap(null);

                        }
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

//animate marker on click on favorite
function jampedMarker(id){
    jQuery("#"+id).addClass('animated bounce')
             .one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
                     jQuery(this).removeClass('animated bounce');
                });
}
