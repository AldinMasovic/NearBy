package com.example.demo.domain.product.gps;

import com.example.demo.data.gps.GpsCoordinate;

public class GpsCoordinateMapper {

    protected GpsCoordinateMapper() {
    }

    public static GpsCoordinateDto mapToDto(GpsCoordinate gpsCoordinate) {
        if (gpsCoordinate == null) {
            return null;
        }
        return new GpsCoordinateDto(gpsCoordinate.getLatitude(), gpsCoordinate.getLongitude());
    }

    public static GpsCoordinate mapToGpsCoordinate(GpsCoordinateDto dto) {
        if (dto == null) {
            return null;
        }
        return new GpsCoordinate(dto.getLatitude(), dto.getLongitude());
    }
}
