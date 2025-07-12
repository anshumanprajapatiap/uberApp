package com.anshumanprajapati.project.uber.uberApp.services.impl;

import com.anshumanprajapati.project.uber.uberApp.services.DistanceService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@Slf4j
public class DistanceServiceOSRMImpl implements DistanceService {
    //https://project-osrm.org/docs/v5.24.0/api/#general-options

    private static final String OSRM_API_BASE_URL = "https://router.project-osrm.org/route/v1/driving/";

    @Override
    public double calculateDistance(Point src, Point dest) {

        try {
            String uri = src.getX()+","+src.getY()+";"+dest.getX()+","+dest.getY();
            RestClient.ResponseSpec response = RestClient.builder()
                    .baseUrl(OSRM_API_BASE_URL)
                    .build()
                    .get()
                    .uri(uri)
                    .retrieve();
            log.info("OSRM API URL: {}", OSRM_API_BASE_URL + uri);
            log.info("OSRM API Response: {}", response);
            OSRMResponseDto responseDto = response
                    .body(OSRMResponseDto.class);

            return responseDto.getRoutes().get(0).getDistance() / 1000.0;
        } catch (Exception e) {
            throw new RuntimeException("Error getting data from OSRM "+e.getMessage());
        }

    }
}

@Data
class OSRMResponseDto {
    private List<OSRMRoute> routes;
}

@Data
class OSRMRoute {
    private Double distance;
}


/*

http://router.project-osrm.org/route/v1/driving/13.388860,52.517037;13.397634,52.529407;13.428555,52.523219?overview=false
Lat-> 12.895558123986028
Long -> 80.22115124626815


Lat -> 12.897113782158453
Long ->  80.22751246117785
 */