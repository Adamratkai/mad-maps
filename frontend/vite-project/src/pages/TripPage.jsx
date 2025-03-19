import GoogleMapComponent from '../components/trip/google-map/GoogleMapComponent.jsx'
import TripListPage from "./TripListPage.jsx";
import Recommendation from "../components/trip/recommendation/Recommendation.jsx";
import {useState} from "react";
import TripDetails from "../components/trip/trip-details/TripDetails.jsx";

function TripPage() {
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
                <TripDetails/>
            </div>
            {location &&
                <div className="col-span-2  flex items-center justify-center">
                    <Recommendation location={location}/>
                </div>
            }
        </div>

    )
}

export default TripPage
