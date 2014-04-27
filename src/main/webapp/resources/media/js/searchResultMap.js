    var map;   
    function initialize() {   
      var myLatlng = new google.maps.LatLng(31.2517, 121.4838);   
      var myOptions = {   
        zoom: 4,   
        center: myLatlng,   
        mapTypeId: google.maps.MapTypeId.ROADMAP   
      }   
      map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);   
      init();   
    }   
    function init() {   
       $.getJSON("/getMapListServer",null,function call(data){
 		 console.log(data);
         addSites(data);   
       });   
    }   
    function addSites(data) {   
      for(var one in data){   
        var name= data[one].name;
        var deviceId = data[one].deviceId
        var latitude = data[one].latitude;   
        var longitude = data[one].longitude;   
        addSite(map,deviceId,name,latitude,longitude)   
      }   
    }   
    function addSite(map,deviceId,name, lat, lng) {   
         var location = new google.maps.LatLng(lat,lng);
         var image='/media/image/crane.png';
         var marker = new google.maps.Marker({   
         position: location, 
         map: map,
         icon:image
      });   
      attachSecretMessage(marker,name,deviceId);   
    }   
    function attachSecretMessage(marker,name,deviceId) {   
      var infowindow = new google.maps.InfoWindow(   
          { content: '<b>设备名称: </b><a href="/front/device/index.htm?deviceId='+deviceId+'" target="_BLANK">'+name+'</a></br>',   
            size: new google.maps.Size(50,50)   
          });   
        google.maps.event.addListener(marker, 'click', function() {   
        infowindow.open(map,marker);   
      });   
    }   
