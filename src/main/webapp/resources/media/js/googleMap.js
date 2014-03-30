var map;
var markersArray = [];
function initialize() {
	var myLatlng = new google.maps.LatLng(22.279566, 113.540955);
	var myOptions = {
		zoom : 12,
		center : myLatlng,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	}
	map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);

	google.maps.event.addListener(map, 'click', function(event) {
		clearOverlays();
		placeMarker(event.latLng);
	});
}

function placeMarker(location) {
	$("#location").val(location);
	var marker = new google.maps.Marker({
		position : location,
		map : map
	});
	markersArray.push(marker);
	map.setCenter(location);
}

function clearOverlays() {
	if (markersArray) {
		for (i in markersArray) {
			markersArray[i].setMap(null);

		}
	}
}

function initEdit(lat,lng) {
	var lat;
	var lng;
	var myLatlng = new google.maps.LatLng(22.279566, 113.540955);
	var myOptions = {
		zoom : 12,
		center : myLatlng,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	}
	map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
    var location = new google.maps.LatLng(lat,lng);
    placeMarker(location);
	google.maps.event.addListener(map, 'click', function(event) {
		clearOverlays();
		placeMarker(event.latLng);
	});
}
