import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class to create our Hotel, rooms, and make reservation.
 *
 */
public class Main {
    private static Hotel myHotel = Hotel.getInstance();    // create our hotel

    public static void main(String[] args) {
        // scanner to name the hotel and welcome user
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the name of the hotel: ");
        String hotelName = input.nextLine();
        System.out.println("Welcome to " + hotelName + " hotel!");

        mainMenu();   // call to mainMenu method to build the rooms
        reserve();    // call to reserve method to reserve the rooms
    }


    /**
     * Method to build the hotel rooms
     *
     */
    public static void mainMenu() {

        boolean finished = false;                 // boolean to let the program know when we are finished creating rooms
        Scanner input = new Scanner(System.in);
        // loop that will keep running until we choose option 4 and finished variable is set to true
        while (!finished) {
            System.out.println("Please select the room you would like to have in your hotel:");
            System.out.println("1. Single Room  $50\n2. Double Room  $80\n3. Deluxe Room  $100\n4. Done, go to reservation");
            try {
                int choice = Integer.parseInt(input.next());   // user input

                switch (choice) {
                    case (1):    // if user chooses 1 (Single Room), call the extraFeature method with a new SingleRoom as its parameter
                        extraFeature(RoomFactory.generateRoom(RoomType.SINGLEROOM));
                        continue;
                    case (2):    // if user chooses 2 (Double Room), call the extraFeature method with a new DoubleRoom as its parameter
                        extraFeature(RoomFactory.generateRoom(RoomType.DOUBLEROOM));
                        continue;
                    case (3):   // if user chooses 3 (Deluxe Room), call the extraFeature method with a new DeluxeRoom as its parameter
                        extraFeature(RoomFactory.generateRoom(RoomType.DELUXEROOM));
                        continue;
                    case (4):   // if user chooses 4, update finished variable's value to exit the loop
                        finished = true;
                        continue;
                    default:    // if user chooses any number <0 or >4
                        System.out.println("Please choose a number within the possible options");
                        mainMenu();
                }
            } catch (NumberFormatException e) {   // if user chooses anything other than a number
                System.out.println("Please choose a number within the possible options");
                mainMenu();
            }
        }
    }

    /**
     * Method to add the room's extra features
     *
     * @param room, room we will be decorating
     */
    public static void extraFeature(Room room) {
        List<Integer> added = new ArrayList<>();   // list to keep track of the added features
        boolean finished = false;                  // boolean to let the program know when we are finished adding features
        Scanner input = new Scanner(System.in);
        // loop that will keep running until we choose option 4 and finished variable is set to true
        while (!finished) {
            System.out.println("Which feature would you like the room to have?");
            System.out.println("1. With View  $50\n2. Netflix Access  $20\n3. Nothing\n4. Done");

            try {
                int choice = Integer.parseInt(input.next());   // user input
                // check if the feature selected has already been added, and if true inform the user
                if (added.contains(choice)) {
                    System.out.println("\nThis option has already been selected, please choose something else or choose 4 to exit selection menu\n");
                    continue;
                }

                switch (choice) {
                    case (1):    // if user chooses 1 (View), decorate the room with a view and add (1) to our list of added features
                        room = new WithView(room);
                        added.add(1);
                        System.out.println("\nView has been added!\n");
                        continue;
                    case (2):   // if user chooses 2 (Netflix), decorate the room with Netflix and add (2) to our list of added features
                        room = new WithNetflixAccess(room);
                        added.add(2);
                        System.out.println("\nNetflix has been added!\n");
                        continue;
                    case (3):
                        added.add(3);   // if user chooses 3 (Nothing), confirm/inform user and add (3) to our list of added features
                        System.out.println("\nNo additional feature selected. If you are sure, please enter 4 to exit selection menu\n");
                        continue;
                    case (4):    // if user chooses 4, update finished variable's value to exit the loop
                        finished = true;
                        continue;
                    default:     // if user chooses any number <0 or >4
                        System.out.println("Please choose a number within the possible options");
                        extraFeature(room);
                }
            } catch (NumberFormatException e) {    // if user chooses anything other than a number
                System.out.println("Please choose a number within the possible options");
                extraFeature(room);
            }
        }
        myHotel.addRoom(room);    // add the newly created and decorated room to the hotel (list of rooms)
    }

    /**
     * Method to reserve the rooms and display the Hotel revenue at the end
     */
    public static void reserve() {

        boolean finished = false;              // boolean to let the program know when we are finished reserving
        Scanner input = new Scanner(System.in);
        // loop that will keep running until we choose option 4 and finished variable is set to true
        while (!finished) {

            System.out.println("\nCurrent Room Status:");
            System.out.println(myHotel.getRooms());          // show the rooms available in the hotel (previously created)
            System.out.println("\nHi Customer, what room would you like?\n1. Single Room\n2. Double Room\n3. Deluxe Room\n4. Done, show hotel revenue");

            try {
                int choice = Integer.parseInt(input.next());    // user input

                switch (choice) {
                    case (1):         // if user chooses 1, update reservation status of the first single room found
                        myHotel.reserveRoom(RoomType.SINGLEROOM);
                        continue;
                    case (2):         // if user chooses 2, update reservation status of the first double room found
                        myHotel.reserveRoom(RoomType.DOUBLEROOM);
                        continue;
                    case (3):        // if user chooses 3, update reservation status of the first deluxe room found
                        myHotel.reserveRoom(RoomType.DELUXEROOM);
                        continue;
                    case (4):        // if user chooses 4, show hotel's revenue and update finished variable's value to exit the loop
                        System.out.println("\nHotel Revenue: $" + myHotel.getRevenue());
                        finished = true;
                        continue;
                    default:        // if user chooses any number <0 or >4
                        System.out.println("Please choose a number within the possible options");
                        reserve();
                }
            } catch (NumberFormatException e) {     // if user chooses anything other than a number
                System.out.println("Please choose a number within the possible options");
                reserve();
            }
        }
    }
}

/**
 * Class Hotel, using singleton pattern to create a single instance of a hotel
 */
class Hotel {

    private static Hotel singleInstance;     // variable to hold the hotel's single instance
    private List <Room> rooms = new ArrayList<>();    // list to hold the rooms we create
    private Hotel(){};    // private creator (singleton pattern)

    /**
     * Method to add a room to the hotel's list
     *
     * @param room, room we want to add
     */
    public void addRoom(Room room){
        rooms.add(room);
    }

    /**
     * Method to reserve a room in the hotel's list
     *
     * @param type, the type of room we want to reserve
     * @return boolean to indicate if the operation is successful
     */
    public boolean reserveRoom(RoomType type){
        // loop to search within the room list for the type of room we want to reserve by using each room's reserve method,
        // if successful the room's method will be implemented, and we will exit the loop.
        // The first available room of that type will be reserved.
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).reserveTheRoom(type)){
                System.out.println("\nReservation successful");
                return true;
            }
        }
        // if no such room available, inform the user and return false
        System.out.println("\nSorry, room not available");
        return false;
    }

    /**
     * Method to get the hotel's revenue. Based on the rooms reserved
     *
     * @return total revenue
     */
    public int getRevenue(){
        int total = 0;   // variable to hold the total revenue
        // loop to identify the reserved rooms and add their price to the total
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getReservationStatus()){
                total += rooms.get(i).getPrice();
            }
        }
        return total;   // return total revenue
    }

    /**
     * Method to create the single instance of the hotel (singleton pattern)
     *
     * @return the hotel's instance
     */
    public static Hotel getInstance(){
        // if no hotel has been created, create one and return it. Otherwise, return the existing hotel.
        if (singleInstance == null){
            singleInstance = new Hotel();
        }
        return singleInstance;
    }

    /**
     * Method to access the list of rooms in the hotel
     *
     * @return the list of rooms
     */
    public List<Room> getRooms() {
        return rooms;
    }
}

/**
 * Enum values for the types of room
 */
enum RoomType {
    SINGLEROOM, DOUBLEROOM, DELUXEROOM
}

/**
 * Class to create the rooms
 */
class RoomFactory {
    public static Room generateRoom(RoomType type){
        // determine the given type of room we want and create a new instance of it
        if (type == RoomType.SINGLEROOM){
            return new SingleRoom();
        }
        if (type == RoomType.DOUBLEROOM){
            return new DoubleRoom();
        }
        if (type == RoomType.DELUXEROOM){
            return new DeluxeRoom();
        }
        return null;
    }
}

/**
 * Interface for the rooms
 *
 * All methods are explained in the room classes
 */
interface Room {
    public int getPrice();
    public String getDescription();
    public boolean getReservationStatus();
    public boolean reserveTheRoom(RoomType type);
    public RoomType getType();
}

/**
 * Class for a Single Room, it implements the Room interface
 *
 */
class SingleRoom implements Room{

    private final RoomType TYPE = RoomType.SINGLEROOM;   // specify the type of room, using enum
    private boolean reservationStatus = false;           // boolean to track the reservation

    /**
     * Method to get the price of the room
     *
     * @return price
     */
    @Override
    public int getPrice() {
        return 50;
    }

    /**
     * Method to get the description of the room
     *
     * @return description, in this case a single room
     */
    @Override
    public String getDescription() {
        return "Single Room";
    }

    /**
     * Method to see reservation status of the room
     *
     * @return value of reservationStatus variable
     */
    @Override
    public boolean getReservationStatus() {
        return reservationStatus;
    }

    /**
     * Method to reserve the room by updating the reservationStatus variable
     *
     * @param type, type of room we want to reserve to compare with this room
     * @return boolean with result of operation
     */
    @Override
    public boolean reserveTheRoom(RoomType type) {
        //compare the type of room we want with the type of room we have. If same, check if available (reservationStatus false)
        // and if available update the value to true (reserved) and return true. Otherwise, return false
        if (TYPE == type){
            if (!getReservationStatus()){
                reservationStatus = true;
                return true;
            }
        }
        return false;
    }

    /**
     *  Method to get the type of the room
     *
     * @return value of the TYPE variable
     */
    @Override
    public RoomType getType() {
        return TYPE;
    }

    /**
     * Method to display the room information
     *
     * @return String with the description, price, and reservation status
     */
    public String toString(){
        return "\nType = " + getDescription() + ", Price = $" + getPrice() + ", isReserved ? = " + getReservationStatus();
    }
}

/**
 * Class for a Double Room, implements Room interface
 */
class DoubleRoom implements Room{

    private final RoomType TYPE = RoomType.DOUBLEROOM;     // specify the type of room, using enum
    private boolean reservationStatus = false;            // boolean to track the reservation

    /**
     * Method to get the price of the room
     *
     * @return price
     */
    @Override
    public int getPrice() {
        return 80;
    }

    /**
     * Method to get the description of the room
     *
     * @return description, in this case a double room
     */
    @Override
    public String getDescription() {
        return "Double Room";
    }

    /**
     * Method to see reservation status of the room
     *
     * @return value of reservationStatus variable
     */
    @Override
    public boolean getReservationStatus() {
        return reservationStatus;
    }

    /**
     * Method to reserve the room by updating the reservationStatus variable
     *
     * @param type, type of room we want to reserve to compare with this room
     * @return boolean with result of operation
     */
    @Override
    public boolean reserveTheRoom(RoomType type) {
        if (TYPE == type){
            if (!getReservationStatus()){
                reservationStatus = true;
                return true;
            }
        }
        return false;
    }

    /**
     *  Method to get the type of the room
     *
     * @return value of the TYPE variable
     */
    @Override
    public RoomType getType() {
        return TYPE;
    }

    /**
     * Method to display the room information
     *
     * @return String with the description, price, and reservation status
     */
    public String toString(){
        return "\nType = " + getDescription() + ", Price = $" + getPrice() + ", isReserved ? = " + getReservationStatus();
    }
}
/**
 * Class for a Deluxe Room, implements Room interface
 */
class DeluxeRoom implements Room{

    private final RoomType TYPE = RoomType.DELUXEROOM;     // specify the type of room, using enum
    private boolean reservationStatus = false;             // boolean to track the reservation

    /**
     * Method to get the price of the room
     *
     * @return price
     */
    @Override
    public int getPrice() {
        return 100;
    }

    /**
     * Method to get the description of the room
     *
     * @return description, in this case a deluxe room
     */
    @Override
    public String getDescription() {
        return "Deluxe Room";
    }

    /**
     * Method to see reservation status of the room
     *
     * @return value of reservationStatus variable
     */
    @Override
    public boolean getReservationStatus() {
        return reservationStatus;
    }

    /**
     * Method to reserve the room by updating the reservationStatus variable
     *
     * @param type, type of room we want to reserve to compare with this room
     * @return boolean with result of operation
     */
    @Override
    public boolean reserveTheRoom(RoomType type) {
        if (TYPE == type){
            if (!getReservationStatus()){
                reservationStatus = true;
                return true;
            }
        }
        return false;
    }

    /**
     *  Method to get the type of the room
     *
     * @return value of the TYPE variable
     */
    @Override
    public RoomType getType() {
        return TYPE;
    }

    /**
     * Method to display the room information
     *
     * @return String with the description, price, and reservation status
     */
    public String toString(){
        return "\nType = " + getDescription() + ", Price = $" + getPrice() + ", isReserved ? = " + getReservationStatus();
    }
}

/**
 * Abstract class to implement Decorator Pattern
 */
abstract class RoomDecorator implements Room{
    Room room;     // room we will be decorating

    /**
     * Method to get the room's description after adding extra feature
     *
     * @return String with description
     */
    public abstract String getDescription();

    /**
     * Method to display the room information after decorated
     *
     * @return String with the description, price, and reservation status
     */
    public String toString(){
        return "\nType = " + getDescription() + ", Price = $" + getPrice() + ", isReserved ? = " + getReservationStatus();
    }

    /**
     * Method to get the type of room
     *
     * @return the base room's type
     */
    public RoomType getType(){
        return room.getType();
    }
}

/**
 * Class with Netflix feature, extends RoomDecorator class
 */
class WithNetflixAccess extends RoomDecorator{

    /**
     * Constructor with a base room
     *
     * @param room, base room to add feature to
     */
    public WithNetflixAccess(Room room){
        this.room = room;
    }

    /**
     * Method to get new price
     *
     * @return room price plus netflix price
     */
    @Override
    public int getPrice() {
        return room.getPrice() + 20;
    }

    /**
     * Method to get the room's reservation status by calling the room's respective method
     *
     * @return room's reservation status
     */
    @Override
    public boolean getReservationStatus() {
        return room.getReservationStatus();
    }

    /**
     * Method to reserve the room
     *
     * @param type, type of room we want to reserve
     * @return result of room's reserveTheRoom method
     */
    @Override
    public boolean reserveTheRoom(RoomType type) {
        return room.reserveTheRoom(type);          // call to the base room's reserve method
    }

    /**
     * Method to get new description
     *
     * @return base room's description plus netflix access
     */
    @Override
    public String getDescription() {
        return room.getDescription() + " + Netflix Access";
    }
}

/**
 * Class with View feature, extends RoomDecorator class
 */
class WithView extends RoomDecorator{

    /**
     * Constructor with a base room
     *
     * @param room, base room to add feature to
     */
    public WithView(Room room){
        this.room = room;
    }

    /**
     * Method to get new price
     *
     * @return room price plus view price
     */
    @Override
    public int getPrice() {
        return room.getPrice() + 50;
    }

    /**
     * Method to get the room's reservation status by calling the room's respective method
     *
     * @return room's reservation status
     */
    @Override
    public boolean getReservationStatus() {
        return room.getReservationStatus();
    }

    /**
     * Method to reserve the room
     *
     * @param type, type of room we want to reserve
     * @return result of room's reserveTheRoom method
     */
    @Override
    public boolean reserveTheRoom(RoomType type) {
        return room.reserveTheRoom(type);
    }

    /**
     * Method to get new description
     *
     * @return base room's description plus view
     */
    @Override
    public String getDescription() {
        return room.getDescription() + " + View";
    }
}