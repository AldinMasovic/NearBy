package com.example.demo.domain.product.view;

import com.example.demo.data.image.Image;

public class ImageMapper {

    private ImageMapper() {
    }

    public static ImageDto mapToDto(Image image) {
        if (image == null) {
            return null;
        }
        return new ImageDto(image.getId(), image.getImageUrl());
    }
}
