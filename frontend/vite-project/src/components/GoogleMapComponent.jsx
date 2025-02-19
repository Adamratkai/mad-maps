import React from 'react';
import {APIProvider, Map} from '@vis.gl/react-google-maps';





const GoogleMapComponent = () => {
  
  function handleClick(event) {
      if(!event.detail.latLng) {
        return
      }
      console.log("clicked: ", event.detail);
      
      event.map.panTo(event.detail.latLng)
  }

  return (
    <APIProvider apiKey={import.meta.env.VITE_GOOGLE_MAPS_API_KEY} onLoad={() => console.log('Maps API has loaded.')}>
    <Map
        style={{width:"100vw", height:"100vh"}}
         defaultZoom={13}
         defaultCenter={{ lat: -33.860664, lng: 151.208138}} 
         onClick={handleClick}
         >
   </Map>
    </APIProvider>)
}

export default GoogleMapComponent