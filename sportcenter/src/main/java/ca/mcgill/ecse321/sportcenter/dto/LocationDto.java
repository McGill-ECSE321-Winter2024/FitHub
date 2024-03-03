package ca.mcgill.ecse321.sportcenter.dto;


public class LocationDto {
    private String floor;
    private String room;

    public LocationDto() {

    }

    public LocationDto(String floor, String room) {
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