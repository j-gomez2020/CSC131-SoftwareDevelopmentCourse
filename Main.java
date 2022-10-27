//Main.java
//main driver of the program
import java.util.Scanner;
import java.util.Random;


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

    //copy constructor      this takes an Item as input. Class is then initialized with the same data as the Item that was provided
    Item(Item original) {
        name = original.getName();
        price = original.getPrice();
        day_added = original.getDayAdded();   //HUGE PROBLEM HERE   ORIGINAL MAY NOT HAVE THE CURRENT DAY
                                            //MAJOR CHANGES MAY NEED TO BE MADE
                                            //OR WE CAN BE LAZY AND USE A GLOBAL VARIABLE :)
        is_instock = original.getInStock();
        next = null;                        //since this is a copy, it shouldnt be pointing to what the original is pointing to
                                            //original should be pointing to null anyways
                                            //unless new ideas are brought forth
        is_head = false;    //leaving this as false because most items added will not be the head
                            //additional code can check if this really should be true

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
    private Item[] a;				//create a fixed array of slots
    private int head;          //pointer to the first item in the slot
    private int tail;			//pointer to the last item in slot
    private String slot_name;   //name of the item in the slot
    private double slot_price;       //price of the item in the slot
    private int item_count;     //number items in the slot

  //constructors
    Slot() {
        System.out.println("If you see this, Slot should never not be initialised without a head. head should never be null. head has a default constructor incase there is no useful head.");
        a = new Item[15];
        head = 0;
        tail = 0;
        slot_name = "DEFAULT";
        slot_price = -1.00;
        item_count = 0;
    }
    public void enqueue(Item head) {
        if(size() == a.length) return;  //no more room in array
        if(tail >= a.length)
            tail = 0;
        a[tail] = head;
        tail++;
        slot_name = head.getName();
        slot_price = head.getPrice();
        item_count++;
    }
    public void enqueueMult(int n, Item head) {
        Item copy;
	    for(int i = 0; i < n; i++) {
            copy = new Item(head);
		    enqueue(copy);
	    }
        //what should happen if we enqueue 0 items and the array is empty?
        //enqueue 0 items but make the slot be the name of item as well as the price
        if(this.tail == 0 && n == 0) {
            //the array is empty
            //System.out.printf("\t\tI was triggered. head:%d     n:%d\n", this.head, n);     //debug line
            slot_name = head.getName();
            slot_price = head.getPrice();
        }
        else {
            //System.out.printf("\t\thead:%d     n:%d\n", this.head, n);      //debug line
        }
        
    }
  
    public Item deque() {
        Item ret = a[head];
        head++;
        item_count--;
        if(head >= a.length)
            head = 0;
        return ret;
    }
  
    public Item peek() {
        return a[head];
    }
    
    public boolean isEmpty() {
        return size() == 0;
    }
    
    public int size() {
        return item_count;
    }
    
    public void clear() {
        head = 0;
        tail = 0;
        item_count = 0;
    }

    //getters and setters
    //Item getSlotHead() { return head; }
    String getSlotName() { return slot_name; }
    double getSlotPrice() { return slot_price; }
    int getSlotItemCount() { return item_count; }

    /*void setSlotHead(Item head) {
        this.head = head;
    } */
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

    private double total_revenue;               //keep track of dollar value of sales

    //PREDEFINED ITEMS  ONLY USE THEM WITH THE RESTOCKER INTERFACE AND MAYBE COORPORATE
    Item cheetos = new Item("Cheetos", 1.49, current_day);
    Item doritos = new Item("Doritos", 1.49, current_day);
    Item sunchips = new Item("Sun Chips", 1.49, current_day);
    Item snickers = new Item("Snickers", 0.99, current_day);
    Item kitkat = new Item("Kit Kat", 0.99, current_day);

    Item array_of_items [] = {cheetos, doritos, sunchips, snickers, kitkat};    //array holding all the items
    int SIZE = array_of_items.length;
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
        int ascii_code = 65;
        char ascii_ch;
        Slot slot;
        System.out.println("THIS IS WHERE THE MACHINE INVENTORY WILL BE DISPLAYED \nCURRENT SYSTEM FOR DISPLAYING NEEDS SLIGHT MODIFICATIONS");
        for(int row_index = 0; row_index < 8; row_index++) {
            ascii_ch = (char)ascii_code;
            System.out.printf("%c/// ",ascii_ch);
            for(int collumn_index = 0; collumn_index < 15; collumn_index++) {
                slot = slots[row_index][collumn_index];
                
                System.out.printf("%s|| ", slot.getSlotName());
                //if(slot.peek() == null) {
                //    System.out.printf("==EMPTY==||");
                //}
                //else {           
                //    System.out.printf("%s||", slot.getSlotName());
                //}
            }
            System.out.printf("\n");
            ascii_code++;
        }
    }

    //get current day
    int getCurrentDay(){ return current_day; }
    
    //get total revenue
    double getTotalRevenue(){ return total_revenue;}

    //get slot head
    Item getSlotHead(String slot_code) {
        Slot slot = null;   
        Item head = null;	//return variable
        int row = getRowNumber(slot_code);
        int collumn = getCollumnNumber(slot_code);

      //bounds checking   if row or collumn is out of bounds, they will always be -1
        if(row == -1 || collumn == -1) {
            //out of bounds, return null
            head = null;
        }
        else {
            slot = slots[row][collumn];
            head = slot.peek();
        }
        
        return head;
    }
    
    //get slot
    Slot getSlot(String slot_code) {
        Slot slot = null;   
        int row = getRowNumber(slot_code);
        int collumn = getCollumnNumber(slot_code);

      //bounds checking   if row or collumn is out of bounds, they will always be -1
        if(row == -1 || collumn == -1) {
            //out of bounds, return null
        	slot = null;
        }
        else {
            slot = slots[row][collumn];
        }
        
        return slot;
    }
    
    //Set total Revenue
    void setTotalRevenue(double total_revenue) {
        this.total_revenue = total_revenue;
    }

    //stack an item into the slot      
    void stackItem (Item item, int row, int collumn, int total) {
        Slot slot = slots[row][collumn];
        slot.enqueueMult(total ,item);
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
            if (slots[row][collumn].isEmpty() == true) {
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
    }//end of getCollumnNumber()

    //initializer for vending class     may be permanent or temporary depending on how things go
    //will initialize each Slot of the Vending class to contain 0-5 of an Item. Can also choose to do nothing (not even put 0 of an Item into a slot)
    void InitVending() {
        int seed = 0;   //MAKE THIS HAVE THE TIME FOR TRUE RANDOM
                        //currently set to 0 for debugging
        Random rand_gen = new Random(seed); //randomize
        int random_num = -1;
        Item item_to_be_added;
        for(int row_index = 0; row_index < 8; row_index++) {
            for(int collumn_index = 0; collumn_index < 15; collumn_index++) {
                //choose a random number
                random_num = rand_gen.nextInt(SIZE+1);      //chooses a random int between 0 and SIZE+3
                                                            //random_num can be outside array length
                                                            //purpose is to have some slots have nothing(null)
                //System.out.printf("num chosen: %d   SIZE: %d\n", random_num, SIZE); //debug line
                if(random_num < SIZE) {
                    //random_num is in the array
                    item_to_be_added = array_of_items[random_num];
                    random_num = rand_gen.nextInt(5); //chooses 0-5 items to be added

                    //System.out.printf("\t\tAdding %d of %s\n", random_num, item_to_be_added.getName());  //debug line
                    slots[row_index][collumn_index].enqueueMult(random_num, item_to_be_added);
                    //System.out.printf("\t\tName of the slot:%s\n", slots[row_index][collumn_index].getSlotName()); //debug line
                }
                else {
                    //System.out.printf("\t\tno item added. Name of the slot:%s\n", slots[row_index][collumn_index].getSlotName()); //debug line
                }

            }
        }
    }


}

//########################################END OF CLASS DEFINITIONS####################################



public class Main {

    //methods for interfaces
    static void customerI(VendingMachine vending) {
        double bill = 0;    //how much the customer owes the machine
        double customerMoney = 0;		//how much the customer enters
        String slot_location;   //letter number combo of the slot (EX: A3, B4, D1)
        boolean wants_more = true;  //the customer wants to order more
        String order_list[] = new String [5];   //array of the slots the customer wants to order from
                                                //array must grow if the customer orders more than 5 items
        int order_list_index = 0;   //index of the first empty space in order_list
                                    //grows by 1 every time the customer orders something
        Scanner user_input = new Scanner(System.in);
        
        //(temporary) add items to test vending machine
        vending.stackItem(vending.cheetos, 0, 0, 5);
        vending.stackItem(vending.doritos, 1, 0, 3);
        vending.stackItem(vending.sunchips, 2, 0, 7);

        //keep asking the customer for item codes until they are ready to check out
        
        do {
        	//checks entire inventory to see if there is no stock in vending machine
            boolean b = false;
        	for(int i = 65; i < 73; i++) {
        		for(int j = 1; j < 16; j++) {
        			String s = "";
        			s = s + (char)i + j;
        			if(vending.inStock(s)) {
        				b = true;
        				break;
        			}
        		}
        	}
            if(!b) {
            	System.out.println("OUT OF STOCK");
            	break;
            }
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
        } while (wants_more);

        //customer is ready to pay for what they ordered
        
        //only goes through these steps if the customer placed an order
        if(order_list_index > 0) {
        	//print out all items that the customer has selected
            System.out.println("These are the items you have selected.");
            for(int i = 0; i < order_list_index; i++) {
            	System.out.println(vending.getSlotHead(order_list[i]).getName());
            }
            System.out.println("Your total is $" + bill);
            
            //take payment one coin or bill at a time
            double moneyEntered = 0;
            System.out.println("Please enter dollar or coin amount (.01, .05, .1, .25, 1, 5, 10)");
            do {
            	System.out.print("Enter amount: ");
            	moneyEntered = user_input.nextDouble();
            	if(moneyEntered == .01 || moneyEntered == .05 || moneyEntered == .1 || moneyEntered == .25 || moneyEntered == 1 || moneyEntered == 5 || moneyEntered == 10) {
            		customerMoney += moneyEntered;
            	}
            	else {
            		System.out.println("Error, not legal tender, please enter correct dollar amount (.01, .05, .1, .25, 1, 5, 10)");
            	}
            } while (customerMoney < bill);

            //give customer each item one at a time
            //when customer is given their item, remove it from the vending machine's slot's stack
            System.out.println("These items have been dispensed!");
            for(int i = 0; i < order_list_index; i++) {
            	System.out.println(vending.getSlotHead(order_list[i]).getName());
            }
            for(int i = 0; i < order_list_index; i++) {
            	vending.getSlot(order_list[i]).deque();
            }
            //give customer their change
            System.out.println("Change Dispensed: " + String.format("%.2f",(customerMoney - bill)));
            
            //update total revenue
            vending.setTotalRevenue(vending.getTotalRevenue() + bill);
            
            System.out.println("\n\n"); //spacing
        }
    }


    static void restockerI(VendingMachine vending) {
        //tell the restocker to remove expire items

        //tell the restocker to remove recalled items

        //tell the restocker to add items
    }


    static void corporateI(VendingMachine vending) {
        
    }
    
    static void devOpt(VendingMachine vending) {
        //for now just immediately call vending.InitVending
        vending.InitVending();
        vending.displaySlots();
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
            System.out.print("Choose an option: ");
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
                    devOpt(v1);
                    break;
            }

            System.out.println(""); //blank line for spacing
        } while(choice != -1);
        
        user_input.close();
    }
    
}
