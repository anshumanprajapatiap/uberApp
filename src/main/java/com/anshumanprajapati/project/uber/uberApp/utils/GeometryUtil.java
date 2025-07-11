package com.anshumanprajapati.project.uber.uberApp.utils;

import com.anshumanprajapati.project.uber.uberApp.dto.PointDto;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

public class GeometryUtil {

    public static Point createPoint(PointDto pointDto){
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        Coordinate coordinate = new Coordinate(pointDto.getCoordinates()[0], pointDto.getCoordinates()[1]);
        return geometryFactory.createPoint(coordinate);
    }

    public static PointDto createPointDto(Point point) {
        PointDto pointDto = new PointDto();
        pointDto.setCoordinates( new double[]{point.getX(), point.getY()} );
        return  pointDto;
    }
}
