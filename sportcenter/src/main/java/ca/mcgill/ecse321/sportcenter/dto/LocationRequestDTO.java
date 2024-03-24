package ca.mcgill.ecse321.sportcenter.dto;

public class LocationRequestDTO {
    private String floor;
    private String room;

    public LocationRequestDTO(String floor, String room) {
        this.floor = floor;
        this.room = room;
    }

    public String getFloor() {
        return this.floor;
    }

    public String getRoom() {
        return this.room;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
