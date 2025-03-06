import React, { useState, useEffect } from "react";
import axios from "axios";

const MAX_RATING = 5;
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

    function handleAddPlace() {
        onAddPlace(place);
        handlePlaceClose();
    }

    return (
      <>
        {place && (
            <dialog id="my_modal_1" className="modal modal-open" onClick={handlePlaceClose}>
                <div className="modal-box">
                    <div className="card lg:card-side bg-base-100 shadow-sm">
                        <figure>
                            <img src={`data:image/png;base64,${place.photo}`} alt="Album"/>
                        </figure>
                        <div className="card-body">
                            <h2 className="card-title">{place.name}</h2>
                            <p>Price: {"$".repeat(Math.max(1, Math.floor(place.price_level)))}</p>
                            <div className="rating">
                                {[...Array(MAX_RATING)].map((_, index) => (
                                    <div
                                        key={index}
                                        className="mask mask-star"
                                        aria-label={`${index + 1} star`}
                                        aria-current={index + 1 === Math.round(place.rating) ? "true" : undefined}
                                    ></div>
                                ))}
                                {place.rating}
                            </div>
                            <p>Opening hours:</p>
                            {place.opening_hours ? (
                                <div>
                                    {place.opening_hours.weekday_text.map((item, index) => (<p key={index}>{item}</p>))}
                                </div>
                            ): (<p>Not available</p>)}
                            <div className="card-actions justify-end">
                                <button className="btn btn-primary" onClick={handleAddPlace}>Add place</button>
                            </div>
                        </div>
                    </div>
                </div>
            </dialog>
        )}
      </>
    );
}

export default RecommendationDetailedPlace;
