import { useEffect, useState} from "react";
import RecommendedPlace from "./RecommendedPlace.jsx";
import RecommendationDetailedPlace from "./RecommendationDetailedPlace.jsx";
import useAxios from "../../useAxios.js";

function Recommendation({location, onAddPlace}) {
  const [recommendations, setRecommendations] = useState([]);
  const [selectedPlaceId, setSelectedPlaceId] = useState(null);
  const axiosInstance = useAxios();

  useEffect(() => {
    const abortController = new AbortController();
    async function fetchRecommendations () {
      try {

        const response = await axiosInstance.get(`/api/recommendations/`, {
          params: {
            location: `${location.lat},${location.lng}`,
            type: "restaurant",
          },
          signal: abortController.signal,
        });
        setRecommendations(response.data);
      } catch (error) {
        console.error("Error fetching recommendations:", error);
      }
    }
    fetchRecommendations();
    return () => {
      abortController.abort();
    };
  }, [location.lat, location.lng]);

  function handlePlaceClick(placeId) {
    setSelectedPlaceId(placeId);
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
                  <RecommendedPlace key={recommendation.place_id} name={recommendation.name} price={recommendation.price_level} rating={recommendation.rating} onPlaceClick={() => handlePlaceClick(recommendation.place_id)} />
              ))}
            </div>
        </div>
      )}
      {selectedPlaceId && <RecommendationDetailedPlace placeId={selectedPlaceId} onPlaceClose={handleCloseDetailedPlace} onAddPlace={onAddPlace} />}
      </>
    );
}

export default Recommendation;
