import { GoogleMap, Marker } from '@react-google-maps/api';
import React, { useState } from 'react'

const containerStyle = {
    width: "400px",
    height: "400px",
  };
const defaultCenter = { lat: -33.8688, lng: 151.2195 };

const MapComponent = ({ markerPosition, onClick}) => {
    const [map, setMap] = useState(null);
    
    return (

      <GoogleMap
        mapContainerStyle={containerStyle}
        center={markerPosition || defaultCenter}
        zoom={15}
        onLoad={setMap}
        onClick={(event)=>onClick(event,map)}
      >
        {markerPosition && <Marker position={markerPosition} />}
      </GoogleMap>
    );
  };

export default MapComponent