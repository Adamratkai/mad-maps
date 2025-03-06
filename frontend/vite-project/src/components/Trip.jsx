import GoogleMapComponent from './GoogleMapComponent'
import TripMenu from "./tripmenu/TripMenu.jsx";
import Recommendation from "./recommendation/Recommendation.jsx";
import {useState} from "react";

function Trip() {
    const [location, setLocation] = useState(null);

    function handleLocationChange(location) {
        setLocation(location);
    }

    return (
        <div className="App">
            <div className="GoogleMap">
                <GoogleMapComponent handleLocationChange={handleLocationChange}/>
            </div>
            <div className="TripMenu">
                <TripMenu/>
            </div>
            <div className="Recommendation">
                {location && <Recommendation location={location}/>}
            </div>
        </div>
    )
}

export default Trip
