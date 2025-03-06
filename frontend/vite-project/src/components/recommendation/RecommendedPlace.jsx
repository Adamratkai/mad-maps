import React from "react";

const MAX_RATING = 5;
const RecommendedPlace = ({ name, price, rating, handlePlaceClick }) => {
    return (
        <div className="card lg:card-side bg-base-100 shadow-sm join-item">
            <div className="card-body">
                <h2 className="card-title">{name}</h2>
                <p>Price: {"$".repeat(Math.max(1, Math.floor(price)))}</p>
                <div className="rating">
                    {[...Array(MAX_RATING)].map((_, index) => (
                        <div
                            key={index}
                            className="mask mask-star"
                            aria-label={`${index + 1} star`}
                            aria-current={index + 1 === Math.round(rating) ? "true" : undefined}
                        ></div>
                    ))}
                    {rating}
                </div>
                <div className="card-actions justify-end">
                    <button className="btn btn-primary" onClick={handlePlaceClick}>Details</button>
                </div>
            </div>
        </div>
            );
};

export default RecommendedPlace;