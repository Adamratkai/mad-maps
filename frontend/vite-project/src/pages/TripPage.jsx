import GoogleMapComponent from '../components/trip/google-map/GoogleMapComponent.jsx'
import TripListPage from "./TripListPage.jsx";
import Recommendation from "../components/trip/recommendation/Recommendation.jsx";
import {useEffect, useState} from "react";
import {useParams} from "react-router";
import useAxios from "../components/useAxios.js";
import TripDetails from "../components/trip/trip-details/TripDetails.jsx";

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
            const response = await axiosInstance.post(`/api/trip-activities/${tripId}`, {
                placeId: place.placeId,
                visitTime: tripDetail.startDate + "T00:00:00",
            });
            setActivities((prev) => [...prev, {placeDTO: place, visitTime: (tripDetail.startDate + "T00:00:00")}]);
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
        <div className="flex flex-col p-4">
        <div className="flex flex-grow justify-center gap-4 mb-4">
                <GoogleMapComponent onLocationChange={handleLocationChange}/>
            {activities &&
                <TripDetails tripDetail={tripDetail} activities={activities}/>
            }</div>
            {location &&
                <div className="flex justify-center mt-4">
                    <Recommendation location={location} onAddPlace={handleAddPlace}/>
                </div>
            }
        </div>

    )
}

export default TripPage
