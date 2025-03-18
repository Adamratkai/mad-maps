package com.codecool.madmaps.model.Photo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID photoId;

    @Column(nullable = false)
    private byte[] photo;

    @PrePersist
    public void generateUUID() {
        this.photoId = UUID.randomUUID();
    }
}
