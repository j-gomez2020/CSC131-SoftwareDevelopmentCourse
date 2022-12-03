//Main.java
//main driver of the program
import java.util.Scanner;


//##############################CLASS DEFINITIONS######################################
//Item class        contains information on the item and points to the item behind it
class Item {
    private String name;            //name of the item (Ex: Doritos)
    private double price;           //how much the item costs (Ex: 1.25)
    private int day_added;          //the day that the item was added
    private boolean is_instock;     //NOT KNOWN IF NEEDED YET       may be useful for coorporate to see that a slot is out of stock,
                            //                                      but know what item was in that slot
    private Item next = null;       //points to the next item behind it
    private boolean is_head = true;        //NOT KNOWN IF NEEDED YET

    //default constructor
    Item() {
        name = "DEFAULT";
        price = -1.11;
        is_instock = false;
        day_added = -1;
    }

    //constructor for name, price, and day_added
    Item(String name, double price, int day_added) {
        this.name = name;
        this.price = price;
        this.day_added = day_added;
        is_instock = true;
    }

    //getters and setters
    String getName() { return name; }
    double getPrice() { return price; }
    boolean getIsHead() { return is_head; }
    Item getNext() { return next; }
    int getDayAdded() { return day_added; }
    boolean getInStock() { return is_instock; }

    void setName(String name) {
        this.name = name;
    }
    void setPrice(double price) {
        this.price = price;
    }
    void setIsHead(boolean is_head) {
        this.is_head = is_head;
    }
    void setNext(Item item) {
        next = item;
    }
    void setDayAdded(int day) {
        day_added = day;
    }
    void setInStock(boolean is_instock) {
        this.is_instock = is_instock;
    }
}


//Slot class
class Slot {
    private Item head;          //pointer to the first item in the slot
    private String slot_name;   //name of the item in the slot
    private double slot_price;       //price of the item in the slot
    private int item_count;     //number items in the slot

    //constructors
    Slot() {
        System.out.println("If you see this, Slot should never not be initialised without a head. head should never be null. head has a default constructor incase there is no useful head.");
        slot_name = "DEFAULT";
        slot_price = -1.00;
        item_count = -1;
    }
    Slot(Item head) {
        this.head = head;
        slot_name = head.getName();
        slot_price = head.getPrice();
        
        //count how many Items are in the slot
        int number_of_items = 1;    //slot head is guaranteed to not be null
        for(Item iterator = head; iterator.getNext() != null; iterator = iterator.getNext()) {
            number_of_items++;
        }
        item_count = number_of_items;
    }

    //getters and setters
    Item getSlotHead() { return head; }
    String getSlotName() { return slot_name; }
    double getSlotPrice() { return slot_price; }
    int getSlotItemCount() { return item_count; }

    void setSlotHead(Item head) {
        this.head = head;
    }
    void setSlotName(String slot_name) {
        this.slot_name = slot_name;
    }
    void setSlotPrice(double slot_price) {
        this.slot_price = slot_price;
    }
    void setSlotItemCount(int item_count) {
        this.item_count = item_count;
    }

}

//Vending Machine class     
class VendingMachine {
    private Slot slots[][] = new Slot[8][15];   //8 rows, 15 collumns     each slot will have a linked list of Items
    private int current_day;                    //the day that the vending machine is currently on
                                                //use caution when changing current day as it can have unknown effects
                                                //on how the data is stored and could cause problems down the line

    private int temporary_day;                  //NOT KNOWN IF NEEDED YET   maybe use this instead of current_day
                                                //                          when using dev options

    //PREDEFINED ITEMS  ONLY USE THEM WITH THE RESTOCKER INTERFACE AND MAYBE COORPORATE
    Item cheetos = new Item("Cheetos", 1.49, current_day);
    Item doritos = new Item("Doritos", 1.49, current_day);
    Item sunchips = new Item("Sun Chips", 1.49, current_day);
    //END OF PREDEFINED ITEMS

    //default constructor
    VendingMachine() {
        //initialize every slot[][] with a slot class
        for(int row = 0; row < 8; row++) {
            for(int collumn = 0; collumn < 15; collumn++) {
                slots[row][collumn] = new Slot();       //Jasmine, initialise each slot class using the database. you will have to recreate the linked list from the database and then do `Slot(head_of_that_linked_list)`
            }
        }
    }

    //display slots in all the slots of the vending machine in text or in a gui
    void displaySlots() {
        System.out.println("THIS IS WHERE THE MACHINE INVENTORY WILL BE DISPLAYED");
    }

    //get current day
    int getCurrentDay(){ return current_day; }

    //get slot head
    Item getSlotHead(String slot_code) {
        Slot slot = null;
        Item head = null;   //return variable
        int row = getRowNumber(slot_code);
        int collumn = getCollumnNumber(slot_code);

        //bounds checking   if row or collumn is out of bounds, they will always be -1
        if(row == -1 || collumn == -1) {
            //out of bounds, return null
            head = null;
        }
        else {
            slot = slots[row][collumn];
            head = slot.getSlotHead();
        }
        
        return head;
    }

    //stack an item into the slot      
    void stackItem (Item item, int row, int collumn) {
        Slot slot = slots[row][collumn];
        Item current_head = slot.getSlotHead();
        
        //check if the slot was empty to begin with
        if(current_head == null) {
            //slot is empty
            //just make the item to be stacked be the new head
            slot.setSlotHead(item);
        }
        else{
            item.setNext(current_head);
            current_head.setIsHead(false);
            item.setIsHead(true);
        }
    }

    //check if a slot has any stock
    boolean inStock(String slot_code) {
        boolean is_instock = true; //return variable
        int row = getRowNumber(slot_code);  //get row from slot code
        int collumn = getCollumnNumber(slot_code);  //get collumn from slot code

        //bounds checking for row and collumn
        if(row == -1 || collumn == -1) {
            //out of bounds
            System.out.println("That is an invalid location.");
            is_instock = false;
        }
        else {
            if (slots[row][collumn].getSlotHead() == null) {
                is_instock = false;
            }
        }
        
        return is_instock;
    }

    //Convert a letter into a row number for the 2d array
    //Receieves a slot_code and returns the number corresponding to the letter
    //EX: slot_code = A5    returns row=0               slot_code = C4  returns row=2
    int getRowNumber(String slot_code) {
        int row = -1;   //return variable
        char letter = slot_code.charAt(0);   //get only the letter from the slot code
        letter = Character.toUpperCase(letter);     //convert character to uppercase

        int letter_ascii = (int) letter;    //get the ascii code of the uppercase letter
        row = letter_ascii - 65;    //A would be 0 in array. ascii code for A is 65. subtract 65 to get 0
        return row;
    }

    //Slot code is in string form
    //Convert string version of a number into int version
    //EX: slot_code=A6      return row=5            slot_code=C3    return row=2
    int getCollumnNumber(String slot_code) {
        //METHOD BREAKS IF SECOND CHAR IN SLOT_CODE IS NOT A NUMBER     ADD IN EXCEPTION HANDLING OR GUARANTEE A NUMBER
        int collumn = -1;   //return variable
        int length_of_string = slot_code.length();  //get length of string  A10 length is 3
        
        //determine if the slot collumn is 1 or 2 digits
        if(length_of_string == 3) {
            //slot collumn is 2 digits
            String digits = slot_code.substring(length_of_string - 2, length_of_string);   //get the 2 digits of slot code
            //System.out.println(digits);
            collumn = Integer.parseInt(digits) - 1;
        }
        else if(length_of_string == 2) {
            char digit = slot_code.charAt(1);
            collumn = ((int) digit) - 49;   //1 is ascii code 49. 49 - 1 = index 0
        }
        else {
            //user did not enter a valid ascii code
            collumn = -1;
        }

        //bounds checking to make sure user did not enter something like A20 or B444
        if(collumn > 14 || collumn < 0) {
            //out of bounds
            collumn = -1;
        }

        return collumn;
    }
}

//########################################END OF CLASS DEFINITIONS####################################

class mainstuff {

    //methods for interfaces
    static void customerI(VendingMachine vending) {
        double bill = 0;    //how much the customer owes the machine
        String slot_location;   //letter number combo of the slot (EX: A3, B4, D1)
        boolean wants_more = true;  //the customer wants to order more
        String order_list[] = new String [5];   //array of the slots the customer wants to order from
                                                //array must grow if the customer orders more than 5 items
        int order_list_index = 0;   //index of the first empty space in order_list
                                    //grows by 1 every time the customer orders something
        Scanner user_input = new Scanner(System.in);

        System.out.println(""); //spacer line

        //keep asking the customer for item codes until they are ready to check out
        do {
            vending.displaySlots(); //display contents of the machine
            System.out.print("Enter the letter number combo of the item you want [EX: A5][QQ to procede to checkout]: ");
            slot_location = user_input.nextLine();  //get slot code, QQ to quit and pay

            if(slot_location.toUpperCase().equals("QQ")) {
                //customer is ready to pay
                wants_more = false;
            }
            else {
                //check if slot has any stock
                if(vending.inStock(slot_location)) {
                    //slot location has stock
                    //check if order_list[] has room for new letter number combo
                    if(order_list_index >= order_list.length) {
                        //no space to add another item      double array size
                        String temp[] = new String[order_list.length * 2];
                        System.arraycopy(order_list, 0, temp, 0, order_list.length);
                        order_list = temp;
                    }
                    order_list[order_list_index] = slot_location;   //store the slot location
                    order_list_index++; //increment index to the next empty index in order_list
                    bill += vending.getSlotHead(slot_location).getPrice();
                }
                else {
                    System.out.println("Sorry we're out of stock at that location!");
                }
                
            }
            System.out.println(""); //spacer line
        } while (wants_more);

        //customer is ready to pay for what they ordered
        System.out.print("You owe $" + bill);

        //take payment one coin or bill at a time

        //give customer each item one at a time
        //when customer is given their item, remove it from the vending machine's slot's stack

        //give customer their change


        System.out.println("\n\n"); //spacing
    }


    static void restockerI(VendingMachine vending) {
        //tell the restocker to remove expire items

        //tell the restocker to remove recalled items

        //tell the restocker to add items
    }


    static void corporateI(VendingMachine vending) {
        
    }


    static void devInterface(VendingMachine vending) {
        Scanner user_input = new Scanner(System.in);
        int dev_choice = -1;
        System.out.println(""); //spacer

        do {
            //dev menu
            System.out.println("1. Manually add an item to a slot");
            System.out.println("2. Display vending machine inventory");
            System.out.println("3. placeholder");
            System.out.println("4. placeholder");
            System.out.print("Choose an option [-1 to quit]: ");
            dev_choice = user_input.nextInt();

            switch(dev_choice) {
                case 1:
                    //Manually add an item to a slot
                    int item_number = -1;
                    int row = -1;
                    int collumn = -1;
                    String slot_code = "A-1";
                    do {
                        System.out.println("1. Cheetos");
                        System.out.println("2. Doritos");
                        System.out.println("3. Sun Chips");
                        System.out.println("Enter the number of the item you want to add to the machine [-1 to quit]: ");
                        item_number = user_input.nextInt();
                        
                        
                        if(item_number != -1) {
                            System.out.println("Enter a valid slot code [enter A99 if you already quit]: ");
                            slot_code = user_input.nextLine();
                            slot_code = user_input.nextLine();  //first nextLine() takes the \n from last input. idk how to flush it out
                            row = vending.getRowNumber(slot_code);
                            collumn = vending.getCollumnNumber(slot_code);


                            switch(item_number) {
                                case 1:
                                    //Cheetos
                                    vending.stackItem(vending.cheetos, row, collumn);
                                case 2:
                                    //Doritos
                                    vending.stackItem(vending.doritos, row, collumn);
                                case 3:
                                    //Sun Chips
                                    vending.stackItem(vending.sunchips, row, collumn);
                            }
                        }
                    }   while(item_number != -1);
                    break;
                case 2:
                    //display vending machine inventory
                    vending.displaySlots();

                    break;
                case 3:
                    //placeholder
                    
                    break;
                case 4:
                    //placeholder
                    
                    break;

            }
            System.out.println(""); //spacer
        } while(dev_choice != -1);
    }





    public static void main (String[] args) {
        //create the vending machine object
        VendingMachine v1 = new VendingMachine();   //all data will be initialized from a database or text files

        int choice = 21;
        Scanner user_input = new Scanner(System.in);
        do {
            //menu
            System.out.print("Current Day: ");
            System.out.println(v1.getCurrentDay());
            System.out.println("1. Customer Interface");
            System.out.println("2. Restocker Interface");
            System.out.println("3. Corporate Interface");
            System.out.println("4. Dev options?");
            System.out.print("Choose an option [-1 to quit]: ");
            choice = user_input.nextInt();

            switch(choice) {
                case 1:
                    customerI(v1);
                    break;
                case 2:
                    restockerI(v1);
                    break;
                case 3:
                    corporateI(v1);
                case 4:
                    //dev options
                    devInterface(v1);     //dont uncomment. does not work yet
                    break;
            }

            System.out.println(""); //blank line for spacing
        } while(choice != -1);
        
        user_input.close();
    }
    
}
