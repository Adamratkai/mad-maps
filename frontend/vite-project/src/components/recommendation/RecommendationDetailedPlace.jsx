import { useState, useEffect } from "react";
import axios from "axios";

function RecommendationDetailedPlace({place_id, handlePlaceClose, onAddPlace}) {

    const [place, setPlace] = useState(null);

    useEffect(() => {
      let isMounted = true;
      async function fetchPlace (){
        try {
          const response = await axios.get(`/api/recommendations/detailed`, {
            params: {
              place_id: place_id,
            }
          });
          if (isMounted) {
            setPlace({...response.data.place, photo: response.data.photo});
          }
        } catch (error) {
          console.error("Error fetching detailed place:", error);
        }
      }
      fetchPlace();
      return () => {
        isMounted = false;
      };
    }, []);

    function handleAddPlace(place) {
        onAddPlace(place);
        handlePlaceClose(place);
    }

    return (
      <>
        {place && (
          <div className="place-details" onClick={handlePlaceClose}>
              <h2>{place.name}</h2>
              <p>Rating: {place.rating}</p>
              <p>Price: {place.price_level}</p>
              <p>Opening hours:</p>
              {place.opening_hours ? (
                  <ul>
                    {place.opening_hours.weekday_text.map((item, index) => (<li key={index}>{item}</li>))}
                  </ul>
              ): (<p>Not available</p>)}


              <img src={`data:image/png;base64,${place.photo}`} alt={"place-image"}/>
              <button onClick={() => handleAddPlace(place)}>Add place</button>
          </div>
        )}
      </>
    );
}

export default RecommendationDetailedPlace;
