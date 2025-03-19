import { useEffect, useState} from "react";
import RecommendedPlace from "./RecommendedPlace.jsx";
import RecommendationDetailedPlace from "./RecommendationDetailedPlace.jsx";
import useAxios from "../../useAxios.js";

function Recommendation({location, onChange}) {
  const [recommendations, setRecommendations] = useState([]);
  const [selectedPlaceId, setSelectedPlaceId] = useState(null);
  const axiosInstance = useAxios();

  useEffect(() => {
    let isMounted = true;
    async function fetchRecommendations () {
      try {

        const response = await axiosInstance.get(`/recommendations/`, {
          params: {
            location: `${location.lat},${location.lng}`,
            type: "restaurant",
          },
        });
        if (isMounted) {
          setRecommendations(response.data);
        }
      } catch (error) {
        console.error("Error fetching recommendations:", error);
      }
    }
    fetchRecommendations();
    return () => {
      isMounted = false;
    };
  }, [location.lat, location.lng]);

  function handlePlaceClick(place_id) {
    setSelectedPlaceId(place_id);
  }

  function handleCloseDetailedPlace() {
    setSelectedPlaceId(null);
  }

    return (
      <>
      {recommendations && (
        <div className="recommendation-container">
            <div className="flex gap-5 ">
              {recommendations.map((recommendation) => (
                  <RecommendedPlace key={recommendation.place_id} name={recommendation.name} price={recommendation.price_level} rating={recommendation.rating} handlePlaceClick={() => handlePlaceClick(recommendation.place_id)} />
              ))}
            </div>
        </div>
      )}
      {selectedPlaceId && <RecommendationDetailedPlace place_id={selectedPlaceId} handlePlaceClose={handleCloseDetailedPlace} onAddPlace={onChange}/>}
      </>
    );
}

export default Recommendation;
