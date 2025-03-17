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
        <div className="grid grid-cols-2 grid-rows-3 gap-4 p-4 h-screen">
            <div className="col-span-1 row-span-2 flex items-center justify-center">
                <GoogleMapComponent onLocationChange={handleLocationChange}/>
            </div>
            <div className="col-span-1 row-span-2 flex items-center justify-center">
                <TripMenu/>
            </div>
            {location &&
                <div className="col-span-2  flex items-center justify-center">
                    <Recommendation location={location}/>
                </div>
            }
        </div>

    )
}

export default Trip
