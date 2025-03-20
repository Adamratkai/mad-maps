import GoogleMapComponent from '../components/trip/google-map/GoogleMapComponent.jsx'
import TripListPage from "./TripListPage.jsx";
import Recommendation from "../components/trip/recommendation/Recommendation.jsx";
import {useEffect, useState} from "react";
import {useParams} from "react-router";
import useAxios from "../components/useAxios.js";

function TripPage() {
    const [location, setLocation] = useState(null);
    const [tripDetail, setTripDetail] = useState(null);
    const [activities, setActivities] = useState(null);
    const {tripId} = useParams();
    const axiosInstance = useAxios();

    function handleLocationChange(location) {
        setLocation(location);
    }

    async function handleAddPlace(place) {
        try {
            console.log(place);
            const response = await axiosInstance.post(`/api/trip-activities/${tripId}`, {
                placeId: place.placeId,
                visitTime: tripDetail.startDate + "T00:00:00",
            });
            setActivities((prev) => [...prev, {placeDTO: place, visitTime: tripDetail.visitTime}]);
            return response.data;
        } catch (error) {
            console.error("Error fetching places:", error);
        }
    }

    useEffect(() => {
        const abortController = new AbortController();

        async function fetchPlaces() {
            try {

                const response = await axiosInstance.get(`/api/trips/${tripId}`, {
                    signal: abortController.signal,
                });
                setTripDetail(response.data);
                setActivities(response.data.tripActivities);
            } catch (error) {
                console.error("Error fetching places:", error);
            }
        }

        fetchPlaces();
        return () => {
            abortController.abort();
        };
    }, []);

    return (
        <div className="grid grid-cols-2 grid-rows-3 gap-4 p-4 h-screen">
            <div className="col-span-1 row-span-2 flex items-center justify-center">
                <GoogleMapComponent onLocationChange={handleLocationChange}/>
            </div>
            {activities &&
                <div className="col-span-1 row-span-2 flex items-center justify-center">
                    <TripListPage/>
                </div>
            }
            {location &&
                <div className="col-span-2  flex items-center justify-center">
                    <Recommendation location={location} onAddPlace={handleAddPlace}/>
                </div>
            }
        </div>

    )
}

export default TripPage
