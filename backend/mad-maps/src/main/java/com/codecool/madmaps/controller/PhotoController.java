package com.codecool.madmaps.controller;

import com.codecool.madmaps.service.PlaceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/photos")
public class PhotoController {

    PlaceService placeService;

    public PhotoController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("/{photoId}")
    public byte[] getPhoto(@PathVariable UUID photoId) {
        return placeService.getPhotoById(photoId);
    }

    @GetMapping("/all/{placeId}")
    public List<UUID> getIdsFromPhotos(@PathVariable String placeId) {
        return placeService.getPhotoIdsByPlaceId(placeId);
    }
}
