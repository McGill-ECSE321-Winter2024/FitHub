package ca.mcgill.ecse321.sportcenter.dto;

import ca.mcgill.ecse321.sportcenter.model.Location;

public class LocationResponseDTO {
    private Integer id;
    private String floor;
    private String room;
    
    public LocationResponseDTO() {
        
    }

    public LocationResponseDTO(Location location) {
        this.floor = location.getFloor();
        this.room = location.getRoom();
    }

    public Integer getId() {
        return this.id;
    }

    public String getFloor() {
        return this.floor;
    }

    public String getRoom() {
        return this.room;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
