package ca.mcgill.ecse321.sportcenter.dto;

import java.util.List;

public class LocationListDTO {
    private List<LocationResponseDTO> locations;

    public LocationListDTO(List<LocationResponseDTO> locations) {
        this.locations = locations;
    }

    public List<LocationResponseDTO> getLocations() {
        return this.locations;
    }

    public void setLocations(List<LocationResponseDTO> locations) {
        this.locations = locations;
    }
}
