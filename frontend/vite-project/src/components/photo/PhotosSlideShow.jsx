import { useState } from "react";

function PhotosSlideShow({ photos }) {
    const [currentSlide, setCurrentSlide] = useState(0);

    const prevSlide = () => {
        setCurrentSlide((currentSlide) =>
            currentSlide === 0 ? photos.length - 1 : currentSlide - 1
        );
    };

    const nextSlide = () => {
        setCurrentSlide((currentSlide) =>
            currentSlide === photos.length - 1 ? 0 : currentSlide + 1
        );
    };

    return (
        <div className="relative w-full max-h-52">
            {photos.map((photo, index) => (
                <div
                    key={photo + index}
                    className={`carousel-item relative w-full ${
                        index === currentSlide ? "block" : "hidden"
                    }`}
                >
                    <div className="relative w-full">
                        <img
                            src={`/api/photos/${photo}`}
                            alt="Photo"
                            className="mx-auto w-auto max-h-52 h-auto max-w-full object-contain"
                        />
                        <div className="absolute inset-0 flex items-center justify-between px-5">
                            <button
                                onClick={prevSlide}
                                className="btn btn-circle bg-base-200/75"
                            >
                                ❮
                            </button>
                            <button
                                onClick={nextSlide}
                                className="btn btn-circle bg-base-200/75"
                            >
                                ❯
                            </button>
                        </div>
                    </div>
                </div>
            ))}
        </div>
    );
}

export default PhotosSlideShow;
