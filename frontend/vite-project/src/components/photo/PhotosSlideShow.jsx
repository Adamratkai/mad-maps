function PhotosSlideShow({photos}) {
    return (
        <div className="carousel w-full">
            {photos.map((photo, index) => {
                const prevSlide = index === 0 ? photos.length - 1 : index - 1;
                const nextSlide = index === photos.length - 1 ? 0 : index + 1;

                return (
                    <div key={photo} id={"slide" + index} className="carousel-item relative w-full">
                        <img src={"/api/photos/" + photo}
                             alt="Photo"
                             className="w-full h-auto max-w-full max-h-full object-contain"/>
                        <div
                            className="absolute left-5 right-5 top-1/2 flex -translate-y-1/2 transform justify-between">
                            <a href={`#slide${prevSlide}`} className="btn btn-circle bg-base-200/75">
                                ❮
                            </a>
                            <a href={`#slide${nextSlide}`} className="btn btn-circle bg-base-200/75">
                                ❯
                            </a>
                        </div>
                    </div>
                );
            })}
        </div>
    );
}

export default PhotosSlideShow;
