/**
 * Created by Vasyl Danylyuk on 26.11.2014.
 */
google.maps.event.addDomListener(window, 'load', initializeMap);
var map;                //Map element
function initializeMap() {
    var defaultMapOptions = {
        center: {lat : 48.9501, lng : 24.701},
        zoom: 14
    };
    map = new google.maps.Map(document.getElementById('map_container'), defaultMapOptions);
}