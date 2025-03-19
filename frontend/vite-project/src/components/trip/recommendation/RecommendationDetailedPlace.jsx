import React, {useState, useEffect, useRef} from "react";
import useAxios from "../../useAxios.js";

const MAX_RATING = 5;
function RecommendationDetailedPlace({place_id, handlePlaceClose, onAddPlace}) {
    const [place, setPlace] = useState(null);
    const modalRef = useRef(null);
    const axiosInstance = useAxios();
    useEffect(() => {
      let isMounted = true;
      async function fetchPlace (){
        try {
          const response = await axiosInstance.get(`/recommendations/detailed`, {
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
        handlePlaceClose();
        onAddPlace(place);
    }

    function closeModal(e) {
        if (e.target === modalRef.current) {
            handlePlaceClose();
        }
    }

    return (
      <>
        {place && (
            <dialog ref={modalRef} id="my_modal_2" className="modal modal-open" onClick={closeModal}>
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
