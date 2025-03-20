import React from "react";

const MAX_RATING = 5;

function TripActivity({tripActivity: {placeDTO: {name, rating, priceLevel, openingHours, photos}, visitTime}}) {

    return (<div>
        <div className="card lg:card-side bg-base-200 shadow-sm w-[200px]">
            <div className="card-body">
                <h2 className="card-title justify-center">{name}</h2>
                <div className="visit-time">{visitTime}
                </div>
                <p>Price: {"$".repeat(Math.max(1, Math.floor(priceLevel)))}</p>
                <div className="rating">
                    {[...Array(MAX_RATING)].map((_, index) => (<div
                            key={index}
                            className="mask mask-star"
                            aria-label={`${index + 1} star`}
                            aria-current={index + 1 === Math.round(rating) ? "true" : undefined}
                        ></div>))}
                    {rating}
                </div>
                <div className="opening-hours">{openingHours}
                </div>
            </div>
        </div>
    </div>);
}

export default TripActivity;