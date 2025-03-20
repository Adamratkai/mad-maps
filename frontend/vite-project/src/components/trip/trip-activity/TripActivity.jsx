import React, { useState } from "react";
import PhotosSlideShow from "../../photo/PhotosSlideShow.jsx";

const MAX_RATING = 5;

function TripActivity({ tripActivity: { placeDTO: { name, rating, priceLevel, openingHours, photos}, visitTime } }) {
    const [isOpen, setIsOpen] = useState(false);

    function formatDateISO(date) {
        return date.split("T")[0];
    }

    return (
        <div className="border rounded-lg shadow-sm p-2 bg-base-200">
            <button
                className="w-full text-left font-semibold flex justify-between items-center p-2"
                onClick={() => setIsOpen(!isOpen)}
            >
                {name}
                <span className={`transition-transform ${isOpen ? "rotate-180" : ""}`}>▼</span>
            </button>
            {isOpen && (
                <div className="p-2 bg-base-100 rounded-md mt-2 shadow-inner">
                    <p className="text-lg font-bold text-gray-600">Visit Time: {formatDateISO(visitTime)}</p>
                    <p className="text-m">{"$".repeat(Math.max(1, Math.floor(priceLevel)))}</p>
                    <div className="flex items-center gap-1">
                        {[...Array(MAX_RATING)].map((_, index) => (
                            <div
                                key={index}
                                className={`mask mask-star w-5 h-5 ${index < Math.round(rating) ? "bg-yellow-400" : "bg-gray-300"}`}
                                aria-label={`${index + 1} star`}
                            ></div>
                        ))}
                        <span className="ml-2">{rating}</span>
                    </div>
                    <p className="text-sm">{openingHours}</p>
                    <div className="h-52">
                        <PhotosSlideShow photos={photos}></PhotosSlideShow>
                    </div>
                </div>
            )}
        </div>
    );
}

export default TripActivity;