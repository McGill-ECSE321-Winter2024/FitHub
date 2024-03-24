package ca.mcgill.ecse321.sportcenter.dto;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.sportcenter.model.Location;

public class LocationListDTO {
    private List<LocationResponseDTO> locations;

    public LocationListDTO() {
        this.locations = new ArrayList<>();
    }

    public LocationListDTO(List<LocationResponseDTO> locations) {
        this.locations = locations;
    }

    public List<LocationResponseDTO> getLocations() {
        return this.locations;
    }

    public void setLocations(List<LocationResponseDTO> locations) {
        this.locations = locations;
    }

    public static <T> List<LocationResponseDTO> locationListToLocationResponseDTOList(List<T> locations) {
    List<LocationResponseDTO> list = new ArrayList<>();
    for (T location : locations) {
        LocationResponseDTO dto = new LocationResponseDTO((Location) location);
        list.add(dto);
    }
    return list;
    }
}
