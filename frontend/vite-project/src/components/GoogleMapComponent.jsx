import { useState } from "react";
import { LoadScript } from "@react-google-maps/api";
import MapComponent from "./MapComponent";
import SearchBarComponent from "./SearchBarComponent";

const GOOGLE_MAPS_API_KEY =import.meta.env.VITE_GOOGLE_MAPS_API_KEY ;



const googlaMapsLibrary = ["places"]

const GoogleMapSearchBox = () => {
  const [markerPosition, setMarkerPosition] = useState(null);

  const onPlacesChanged = (searchBox) => {
    if (!searchBox) return;

    const places = searchBox.getPlaces();
    if (!places.length) return;
    const targetPlaceLocation = places[0].geometry.location;
    setMarkerPosition(targetPlaceLocation);

  };
  
  const onMapClick = (event,map) => {
        console.log(event)
            if (map) {
              map.panTo(event.latLng);
              setMarkerPosition(event.latLng);
            }
          }

  return (
    <LoadScript googleMapsApiKey={GOOGLE_MAPS_API_KEY} libraries={googlaMapsLibrary}>
      <div style={{ marginBottom: "10px" }}>
        <SearchBarComponent onPlacesChanged={onPlacesChanged} />
      </div>

    <MapComponent markerPosition={markerPosition} onClick={onMapClick}/>
    </LoadScript>
  );
};

export default GoogleMapSearchBox;
