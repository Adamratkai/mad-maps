import React, {useState, useEffect, useRef} from "react";
import useAxios from "../../useAxios.js";
import {data} from "react-router";

const MAX_RATING = 5;

function RecommendationDetailedPlace({placeId, onPlaceClose, onAddPlace}) {
    const [place, setPlace] = useState(null);
    const modalRef = useRef(null);
    const axiosInstance = useAxios();
    useEffect(() => {
        const abortController = new AbortController();
        async function fetchPlace() {
            try {
                const response = await axiosInstance.get(`/api/places/${placeId}`,{
                    signal: abortController.signal,
                });
                setPlace(response.data);
            } catch (error) {
                console.error("Error fetching detailed place:", error);
            }
        }
        fetchPlace();
        return () => {
            abortController.abort();
        }
    }, []);

    function handleAddPlace() {
        onPlaceClose();
        onAddPlace(place);
    }

    function closeModal(e) {
        if (e.target === modalRef.current) {
            onPlaceClose();
        }
    }

    return (
        <>
            {place && (
                <dialog ref={modalRef} id="my_modal_2" className="modal modal-open" onClick={closeModal}>
                    <div className="modal-box">
                        <div className="card lg:card-side bg-base-100 shadow-sm">
                            <figure>
                                <img src={`/api/photos/${place.photos[0]}`} alt="Album"/>
                            </figure>
                            <div className="card-body">
                                <h2 className="card-title">{place.name}</h2>
                                <p>Price: {"$".repeat(Math.max(1, Math.floor(place.priceLevel)))}</p>
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
                                {place.openingHours ? (
                                    <div>
                                        {place.openingHours.map((item, index) => (<p key={index}>{item}</p>))}
                                    </div>
                                ) : (<p>Not available</p>)}
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
