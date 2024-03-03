package ca.mcgill.ecse321.sportcenter.dto;


public class LocationDTO {

    private String floor;
    private String room;

    public LocationDTO() {

    }

    public LocationDTO(String floor, String room) {
        this.floor = floor;
        this.room = room;
    }

    public String getFloor() {
        return this.floor;
    }

    public String getRoom() {
        return this.room;
    }
}