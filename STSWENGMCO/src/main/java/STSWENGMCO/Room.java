package STSWENGMCO;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import static org.apache.commons.lang3.Validate.notBlank;

public class Room {
    private String nameOfRoom;
    private final int maxCap;
    private int currCap;


    public Room(String nameOfRoom, int maxCap) {
        notBlank(nameOfRoom, "Please provide room name. Room name cannot be left blank");
        Validate.isTrue(StringUtils.isAlphanumeric(nameOfRoom),"Room Name must be in alphanumeric, was: " + nameOfRoom);

        this.nameOfRoom = nameOfRoom;
        this.maxCap = maxCap;
        this.currCap = 0;
    }

    @Override
    public String toString() {
        return nameOfRoom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        return nameOfRoom != null ? nameOfRoom.equals(room.nameOfRoom) : room.nameOfRoom == null;
    }

    @Override
    public int hashCode() {
        return nameOfRoom != null ? nameOfRoom.hashCode() : 0;
    }



    void checkRoomCapacity () {
        if (this.currCap >= maxCap) {
            throw new RuntimeException("Warning: Full Capacity!");
        }
    }


    public String getNameOfRoom(){
        return nameOfRoom;
    }

    public int getMaxCapacity(){
        return maxCap;
    }

    public int addToRoom(){
        return this.currCap++;
    }

    public int removeFromRoom(){
        return this.currCap--;
    }
}
