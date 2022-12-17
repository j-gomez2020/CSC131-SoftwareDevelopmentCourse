//Main.java
//main driver of the program
import java.util.Scanner;
import java.util.Random;

//################################################NOTES TO BE DONE BEFORE FINALIZATION#############################
//1. "DELETE-PILE"          delete all lines and associated lines with this comment
//2. "POTENTIAL-DELETE"     check if this line and associated lines with this comment need to be deleted
//3. "FIX-ISSUE"            theres an issue here. read the comments where this is found to find the problem
//3. ctrl+f to search for the strings above ^^^^^^^^^
//#############################################################END OF NOTES########################################


//################################################Class Definitions################################################
            //########################################Item Class###################################################
//Item class    contains information on the item
class Item {
    //=============private member variables======================================
        //being used
    private String name;            //name of the item              (Ex: Doritos)
    private double price;           //how much the item costs       (Ex: 1.25)
    private int day_added;          //the day the item was added    (Ex: 5)     day 5 was the day the item was added

    //=============end of private member variable definitions====================

    //===============methods and constructors====================================
        //default constructor
    Item() {
        name = "EMPTY";
        price = 0.00;
        day_added = -1;
    }

        //constructor for name, price and day_added
    Item(String name, double price, int day_added) {
        this.name = name;
        this.price = price;
        this.day_added = day_added;
    }

        //copy constructor      takes an Item as input. Class is then initialized with the same data as the Item that was provided
    Item(Item original) {
        name = original.getName();
        price = original.getPrice();
        day_added = original.getDay_added();    //FIX-ISSUE     copies day_added from original.   
                                                                    //day_added should be updated to the actual day it
                                                                    //was actually added by the restocker
    }

        //getters and setters
    String getName() { return name; }
    double getPrice() { return price; }
    int getDay_added() { return day_added; }

    void setName(String name) {
        this.name = name;
    }
    void setPrice(double price) {
        this.price = price;
    }
    void setDay_added(int day_added) {
        this.day_added = day_added;
    }
    //===============end of methods and constructors=============================

}//end of item class
            //#####################################END OF Item Class###############################################

            //########################################Slot Class###################################################
//Slot class
class Slot {
    //=============private member variables======================================
    private Item[] queue;           //queue data structure to hold 15 Item objects
    private int head;               //index of where the head of the queue is
    private int tail;               //index of where the tail of the queue is
    private String slot_name;       //name of the slot                  (Ex: Doritos)
    private double slot_price;      //price of the item in the slot     (Ex: 1.49)
    private int item_count;         //number of items in the slot       (Ex: 4)
    private boolean restock;        //true of the slot needs to be restocked
    private boolean recall;         //true of the slot needs to be recalled
    private int restock_count;      //number of items that need to be restocked
    private int recall_count;       //number of items that need to be recalled
    private String restock_name;    //name of the new item to be added to the slot
    private double restock_price;   //price of the new item to be added
    //=============end of private member variable definitions====================

    //===============methods and constructors====================================
        //constructor
    Slot() {
        queue = new Item[15];
        head = 0;
        tail = 0;
        slot_name = "EMPTY";
        slot_price = 0.00;
        item_count = 0;
    }
    
    Slot(int length) {
        queue = new Item[length];
        head = 0;
        tail = 0;
        slot_name = "EMPTY";
        slot_price = 0.00;
        item_count = 0;
    }
        
        //getters setters
    String getSlotName() { return slot_name; }
    double getSlotPrice() { return slot_price; }
    int getSlotItemCount() { return item_count; }
    boolean getRestock() { return restock; }
    boolean getRecall() { return recall;}
    int getRestockCount() { return restock_count; }
    int getRecallCount() { return recall_count; }
    String getRestockName() { return restock_name; }
    double getRestockPrice() { return restock_price; }

    void setSlotName(String slot_name) { this.slot_name = slot_name; }
    void setSlotPrice(double slot_price) { this.slot_price = slot_price; }
    void setSlotItemCount(int item_count) { this.item_count = item_count; }
    void setRestock(boolean restock) { this.restock = restock; }
    void setRecall(boolean recall) { this.recall = recall; }
    void setRestockCount(int restock_count) { this.restock_count = restock_count; }
    void setRecallCount(int recall_count) { this.recall_count = recall_count; }
    void setRestockName(String restock_name) { this.restock_name = restock_name; }
    void setRestockPrice(double restock_price) {this.restock_price = restock_price; }

        //enqueue one item onto the queue
    void enqueue(Item head) {
        if(size() == queue.length) {
            //no more room in the queue
            return;
        }

        if(tail >= queue.length) {
            tail = 0;
        }

        queue[tail] = head;
        tail++;
        slot_name = head.getName();
        slot_price = head.getPrice();
        item_count++;
    }//end of enqueue()

        //enqueue multiple items onto the queue
    void enqueueMult(int n, Item head) {
        Item copy;
        
        for(int i = 0; i < n; i++) {
            copy = new Item(head);
            enqueue(copy);
        }

        //what should happen if we enqueue 0 items and the array is empty?
        //enqueue 0 items but make the slot be the name of the item as well as the price
        if(this.tail == 0 && n == 0) {
            slot_name = head.getName();
            slot_price = head.getPrice();
        }
    }//end of enqueueMult()

        //dequeue one item
    Item deque() {
        Item ret = queue[head];

        head++;
        item_count--;
        if(item_count == 0) {
        	slot_name = "EMPTY";
            slot_price = 0;
        }
        if(head >= queue.length) {
            head = 0;
        }
        
        return ret;
    }//end of deque()

        //dequeue head of the queue
    void dequeHead() {
        head++;
        item_count--;
        if(item_count == 0) {
        	slot_name = "EMPTY";
            slot_price = 0;
        }
        if(head >= queue.length) {
            head = 0;
        }
    }//end of dequeHead()

        //dequeue multiple items off the queue
    void dequeMult(int n) {
        for(int i = 0; i <n; i++) {
            dequeHead();
        }
    }//end of dequeMult()

        //peek at the head Item
    Item peek() {
        return queue[head];
    }

    boolean isEmpty() {
        return size() == 0;
    }

    int size() {
        return item_count;
    }

    void clear() {
        head = 0;
        tail = 0;
        item_count = 0;
    }
    //===============end of methods and constructors=============================
}//end of slot class
            //#####################################END OF Slot Class###############################################

            //########################################Vending Machine Class###################################################
//vending machine class
class VendingMachine {
    //=============private member variables======================================
        //being used
    private Slot slots[][] = new Slot[10][8];       //10 rows, 8 collumns       each Slot is a queue that holds 15 Items
    private int current_day;                        //day that the vending machine is currently on
    private int row_length = slots.length;          //amount of rows in slots class
    private int collumn_length = slots[0].length;   //amount of collumns in slots class
    private boolean status = false;					//turns on vending machine for customer use
    private int location;
    public Item[] history = new Item[1000];			//public array for purchase history
    public int history_count = 0;					//public int for history count

        //unsure if being used
    private double total_revenue;                   //keeps track of machine total revenue

        //predefined Items
    private Item cheetos = new Item("Cheetos", 1.49, current_day);
    private Item doritos = new Item("Doritos", 1.49, current_day);
    private Item sunchips = new Item("Sun Chips", 1.49, current_day);
    private Item snickers = new Item("Snickers", 0.99, current_day);
    private Item kitkat = new Item("Kit Kat", 0.99, current_day);

    private Item array_of_items [] = {cheetos, doritos, sunchips, snickers, kitkat};    //array holding all predefined items
    private int SIZE = array_of_items.length;
    //=============end of private member variable definitions====================

    //===============methods and constructors====================================
        //constructor
    VendingMachine() {
        //initialize every Slot in slots array
        for(int row = 0; row < row_length; row++) {
            for(int collumn = 0; collumn < collumn_length; collumn++) {
                slots[row][collumn] = new Slot();
            }
        }
        initVending();
    }

        //constructor helper
    void initVending() {
        int seed = 0;   //FIX-ISSUE     change 0 to time so that it is random
        Random rand_gen = new Random(seed); //randomize
        int random_num = -1;
        Item item_to_be_added;

        for(int row_index = 0; row_index < row_length; row_index++) {
            for(int collumn_index = 0; collumn_index < collumn_length; collumn_index++) {
                //choose a random number
                random_num = rand_gen.nextInt(SIZE+1);  //chooses a random int between 0 and SIZE+1
                                                        //random_num can be outside array length
                                                        //purpose is to have some slots have nothing(null)
                
                if(random_num < SIZE) {
                    //random_num is in array
                    item_to_be_added = array_of_items[random_num];
                    random_num = rand_gen.nextInt(5); //chooses 0-5 items to be added

                    slots[row_index][collumn_index].enqueueMult(random_num, item_to_be_added);  //enqueue the items
                }
            }
        }
    }//end of initVending()

        //getters setters
    int getCurrentDay() { return current_day; }
    double getTotalRevenue() { return total_revenue; }
    boolean getStatus() { return status; }
    int getLocation() {return location; }    
    Item getSlotHead(String slot_code) {
        Slot slot = null;
        Item head = null;   //return variable
        int row = getRowNumber(slot_code);
        int collumn = getCollumnNumber(slot_code);

        //bounds checking
        if(row == -1 || collumn == -1) {
            //out of bounds return null
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

    void setTotalRevenue(double total_revenue) { this.total_revenue = total_revenue; }
    void setCurrentDay(int current_day) { this.current_day = current_day; }
    void setStatus(boolean status) { this.status = status; }
    void setLocation(int location) { this.location = location; }

        //display information each slot
    void displaySlots() {
        int ascii_code = 65;    //ascii code for the row letters
        char ascii_ch;          //char version of the ascii_code
        Slot slot;              //holds data of the slot
        String output;          //output for information on each slot

        //label the collumn numbers before displaying the items
        System.out.printf("----");
        for(int col_index = 0; col_index < 8; col_index++) {
            System.out.printf(String.format("%" + 10 + "s", col_index+1));
            System.out.printf(String.format("%-" + 10 + "s", ""));
        }
        System.out.printf("\n");

        //display info on every slot as well as row letter
        for(int row_index = 0; row_index < row_length; row_index++) {
            ascii_ch = (char)ascii_code;    //get the row letter from ascii_code
            System.out.printf("%c/// ", ascii_ch);  //display the row letter
            
            for(int collumn_index = 0; collumn_index < collumn_length; collumn_index++) {
                slot = slots[row_index][collumn_index]; //get the slot from the array
                output = slot.getSlotName() + " $" + slot.getSlotPrice();   //prepare string for display
                System.out.printf(String.format("%-" + 20 + "s", output));  //display string
            }
            System.out.printf("\n"); //next row
            ascii_code++;                   //next row letter
        }
    }//end of displaySlots()

        //enqueue one or multiple Items into the slot
    void stackItem(Item item, int row, int collumn, int total) {
        Slot slot = slots[row][collumn];
        slot.enqueueMult(total, item);
    }

        //check if a slot has any stock
    boolean inStock(String slot_code) {
        boolean is_instock = true;  //return variable
        if(slot_code.length() == 0) {
        	System.out.println("That is an invalid location.");
        	is_instock = false;
        	return is_instock;
        }
        int row = getRowNumber(slot_code); //get row from slot code
        int collumn = getCollumnNumber(slot_code); //get collumn from slot code

        //bounds checking for row and collumn
        if(row == -1 || collumn == -1) {
            //out of bounds
            System.out.println("That is an invalid location.");
            is_instock = false;
        }
        else {
            if(slots[row][collumn].isEmpty() == true) {
                is_instock = false;
            }
        }

        return is_instock;
    }//end of inStock()
    
    boolean isValid(String slot_code) {
        boolean is_valid = true;  //return variable
        if(slot_code.length() == 0) {
        	is_valid = false;
        	return is_valid;
        }
        int row = getRowNumber(slot_code); //get row from slot code
        int collumn = getCollumnNumber(slot_code); //get collumn from slot code

        //bounds checking for row and collumn
        if(row == -1 || collumn == -1) {
            //out of bounds
            is_valid = false;
        }

        return is_valid;
    }//end of isValid()

        //get a row number from a slot code
    int getRowNumber(String slot_code) {
        int row = -1;   //return variable

        char letter = slot_code.charAt(0);  //get only the letter at index 0 of slot_code string
        letter = Character.toUpperCase(letter);     //make the letter upper case

        int letter_ascii = (int) letter;    //get the ascii code of the upper case letter
        row = letter_ascii - 65;    //adjust ascii code to reflect the index of the array

        //bounds checking
        if(row >= row_length || row < 0) {
            row = -1;
        }
        return row;
    }

        //get the collumn number from a slot code
        //FIX-ISSUE Method breaks if second char in slot_code is not a number
        //add exception handling or guarantee a number
    int getCollumnNumber(String slot_code) {
        int collumn = -1;   //return variable
        int length_of_string = slot_code.length();  //get length of string

        //determine if slot collumn is 1 or 2 digits    there are only 8 collumns, but add functionality to easily make it double digits
        if(length_of_string == 2) {
            //slot collumn should be 1 digit
            char digit = slot_code.charAt(1);
            collumn = ( (int) digit) - 49;
        }

        //bounds checking
        if(collumn >= collumn_length || collumn < 0) {
            collumn = -1;
        }

        return collumn;
    }//end of getCollumnNumber()

        //display slot information in greater detail
    void displaySlotsDetailed() {
        int ascii_code = 65;    //ascii code for the row letters
        char ascii_ch;          //char version of the ascii_code
        Slot slot;              //holds data of the slot
        String output;          //output for information on each slot

        //label the collumn numbers before displaying the items
        System.out.printf("\n----");
        for(int col_index = 0; col_index < 8; col_index++) {
            System.out.printf(String.format("%" + 10 + "s", col_index+1));
            System.out.printf(String.format("%-" + 10 + "s", ""));
        }
        System.out.printf("\n");

        //display info on every slot as well as row letter
        for(int row_index = 0; row_index < row_length; row_index++) {
            ascii_ch = (char)ascii_code;    //get the row letter from ascii_code
            System.out.printf("%c/// ", ascii_ch);  //display the row letter
            
            for(int collumn_index = 0; collumn_index < collumn_length; collumn_index++) {
                slot = slots[row_index][collumn_index]; //get the slot from the array
                output = slot.getSlotName() + " $" + slot.getSlotPrice() + "(" + slot.getSlotItemCount() + ")";   //prepare string for display
                System.out.printf(String.format("%-" + 20 + "s", output));  //display string
            }
            System.out.printf("\n"); //next row
            ascii_code++;                   //next row letter
        }
    }
}//end of vending machine class
            //####################################End of Vending Machine Class############################################
//################################################END OF Class Definitions################################################


//#####################################################MAIN###############################################################
public class Main {
    //methods for interfaces
    static void customerI(VendingMachine vending) {
        double bill = 0;        //how much the customer owes the machine
        double customerMoney = 0;   //how much the customer entered
        String slot_location;   //letter number code of the slot
        boolean wants_more = true;  //FIX-ISSUE     we are no longer letting the customer buy multiple items at once
        String order_list[] = new String [5];   //FIX ISSUE     same as above
        int order_list_index = 0;   //FIX-ISSUE same as above

        Scanner user_input;

        do {
            user_input = new Scanner(System.in);
            //ask the customer for the item they want
            System.out.println("\n"); //spacer
            
            //check the status of the vending machine
            if(!vending.getStatus()) {
                System.out.println("This vending machine is currently OUT OF ORDER");
                return;
            }

            //check if the machine has any stock
            boolean no_stock = true;   //is the machine out of stock?
            String s = "";                   //slot code for checking if machine has any stock
            for(int i = 65; i < 75; i++) {  //65 = A    74 = J  rows
                for(int j = 1; j < 9; j++) {    //1 - 9 collumns
                    s = "";
                    s = s + (char)i + j;    //create the slot code
                    if(vending.inStock(s)) {
                        //there is stock
                        no_stock = false;
                        break;
                    }
                }
            }
            if(no_stock) {
                System.out.println("OUT OF STOCK");
                return;
            }

            //display invent since there is stock
            vending.displaySlots();

            //ask what item the customer wants
            boolean is_not_valid = true;
            do {
                System.out.print("\nEnter the letter number combo of the item you want [EX: A5]: ");
                slot_location = user_input.nextLine();  //get user input

                if(slot_location.toUpperCase().equals("QQ")) {
                    //return
                    return;
                }

                //check if the slot has any stock
                if(vending.inStock(slot_location)) {
                    //slot has stock
                    bill = vending.getSlotHead(slot_location).getPrice();
                    is_not_valid = false;
                }
                else {
                    System.out.printf("Sorry. We're out of stock at %s\nPlease choose a different item.\n", slot_location);
                }
            } while(is_not_valid);
            
            //ask the customer to pay
            double money_entered = 0;
            System.out.printf("\nBalance: %s\n", String.format("%.2f", (bill)));
            System.out.println("Please enter the dollar or coin amount (.01, .05, 0.1, .25, 1, 5, 10)");

            do {
                System.out.print("Enter amount: ");
                while (!user_input.hasNextDouble()) {
                	System.out.println("Error, not legal tender. Please enter correct dollar amount (.01, .05, 0.1, .25, 1, 5, 10)");
                	user_input.next();
                }
                money_entered = user_input.nextDouble();

                //check if correct tender
                if(money_entered == .01 || money_entered == .05 || money_entered == .1 || money_entered == .25 || money_entered == 1 || money_entered == 5 || money_entered == 10) {
                    customerMoney += money_entered;
                }
                else {
                    System.out.println("Error, not legal tender. Please enter correct dollar amount (.01, .05, 0.1, .25, 1, 5, 10)");
                }
            } while(customerMoney < bill);
            
            //give customer their item
            System.out.printf("%s was dispensed.\n", vending.getSlotHead(slot_location).getName());

            //give customer their change
            if(customerMoney != bill) {
                System.out.printf("Change dispensed: " + String.format("%.2f", (customerMoney - bill)));
            }

            //update total revenue, remove item from slot and update purchase history
            vending.setTotalRevenue(vending.getTotalRevenue() + bill);
            vending.history[vending.history_count] = vending.getSlot(slot_location).peek();
            vending.history_count++;
            vending.getSlot(slot_location).deque();
            System.out.println("\n\n"); //spacing
            customerMoney = 0;
        } while(true);
    }//end of customer interface


    //restocker interface
    static void restockerI(VendingMachine vending) {
    	boolean recall = false;
    	boolean restock = false;
    	Scanner user_input = new Scanner(System.in);
    	System.out.println("\n");
        //tell the restocker to remove expire items

        //tell the restocker to remove recalled items
    	for(int i = 65; i < 75; i++) {
    		for(int j = 1; j < 9; j++) {
    			String s = "";
    			s = s + (char)i + j;
    			if(vending.getSlot(s).getRecall()) {
    				recall = true;
    				System.out.println("Slot " + s + " needs " + vending.getSlot(s).getRecallCount() + " Items to be recalled");
    				System.out.print("Recall these items? Type Y for yes and N for no: ");
    				String answer = user_input.nextLine();
    				if(answer.toUpperCase().equals("Y")) {
    					vending.getSlot(s).dequeMult(vending.getSlot(s).getRecallCount());
    					vending.getSlot(s).setRecall(false);
    					vending.getSlot(s).setRecallCount(0);
    				}
    			}
    		}
    	}
    	if(!recall){
    		System.out.println("There are no items to recall \n");
    	}
    	

        //tell the restocker to add items
    	for(int i = 65; i < 75; i++) {
    		for(int j = 1; j < 9; j++) {
    			String s = "";
    			s = s + (char)i + j;
    			if(vending.getSlot(s).getRestock()) {
    				restock = true;
    				System.out.println("Slot " + s + " needs " + vending.getSlot(s).getRestockCount() + " " + vending.getSlot(s).getRestockName() + " with a price of " + vending.getSlot(s).getRestockPrice());
    				System.out.print("Restock these items? Type Y for yes and N for no: ");
    				String answer = user_input.nextLine();
    				if(answer.toUpperCase().equals("Y")) {
    					Item temp = new Item(vending.getSlot(s).getRestockName(), vending.getSlot(s).getRestockPrice(), vending.getCurrentDay());
    					vending.getSlot(s).enqueueMult(vending.getSlot(s).getRestockCount(), temp);
    					vending.getSlot(s).setRestock(false);
    					vending.getSlot(s).setRestockCount(0);
    				}
    				
    			}
    		}
    	}
    	if(!restock){
    		System.out.println("There are no items to restock \n");
    	}
    }   //end of restockerI()


    //corporate interface
    static void corporateI(VendingMachine vending) {
    	int choice = 21;
        Scanner user_input = new Scanner(System.in);
        do {
            //menu
        	System.out.println("\n");
            System.out.println("1. Check Inventory");
            System.out.println("2. Suggest Recall");
            System.out.println("3. Suggest Stock");
            System.out.println("4. View Current Revenue");
            System.out.println("5. View Purchase History");
            System.out.println("6. View Status");
            System.out.println("7. View Logistics");
            System.out.println("8. Exit Corporate Interface");
            do {
            	System.out.print("Choose an option: ");
            	while (!user_input.hasNextInt()) {
            		System.out.println("Please enter a valid choice");
            		user_input.next();
            	}
            	choice = user_input.nextInt();
            } while (choice < 1 | choice > 8);
            

            switch(choice) {
                case 1:
                    inventory(vending);
                    break;
                case 2:
                    recallMenu(vending);
                    break;
                case 3:
                    stockMenu(vending);
                    break;
                case 4:
                    currentRevenue(vending);
                    break;
                case 5:
                	purchaseHistory(vending);
                    break;
                case 6:
                    viewStatus(vending);
                    break;
                case 7:
                    viewLogistics(vending);
                    break;
                case 8:
                	choice = -1;
                	break;
            }

            System.out.println(""); //blank line for spacing
        } while(choice != -1);
    }   //end of corporateI()

    // to show inventory
    static void inventory(VendingMachine vending) {
        vending.displaySlotsDetailed();
    }

    //allow corporate to suggest recall 
    static void recallMenu(VendingMachine vending) {
    	Scanner user_input = new Scanner(System.in);
    	String slot_location = "";
    	System.out.println("\n");
    	boolean is_not_valid = true;
    	do {
    		do {
        		System.out.print("\nEnter the slot number that you want to recall or enter QQ to exit: ");
        		slot_location = user_input.nextLine();
        		
        		if(slot_location.toUpperCase().equals("QQ")) {
        			return;
        		}
        		
        		if(vending.isValid(slot_location)) {
        			is_not_valid = false;
        		}
        		else {
        			System.out.println("That is not a valid slot code");
        		}
        	} while(is_not_valid);
        	
        	System.out.print("Would you like to recall " + vending.getSlot(slot_location).getSlotName() + " from slot " + slot_location + "? Type Y for yes and N for no: ");
    		String answer = user_input.nextLine();
    		if(answer.toUpperCase().equals("Y")) {
    			vending.getSlot(slot_location).setRecall(true);
                vending.getSlot(slot_location).setRecallCount(vending.getSlot(slot_location).getSlotItemCount());
    		}
    	} while(true);	
    }//end of recallMenu()

    //allow corporate to suggest restock
    static void stockMenu(VendingMachine vending) {
    	Scanner user_input = new Scanner(System.in);
    	String slot_location;
    	int count = 0;
    	String name;
    	double price = 0.00 ;
    	System.out.println("\n");
    	
    	boolean is_not_valid = true;
    	do {
    		do {
        		System.out.print("\nEnter the slot number that you want to recall or enter QQ to exit: ");
        		slot_location = user_input.nextLine();
        		
        		if(slot_location.toUpperCase().equals("QQ")) {
        			return;
        		}
        		
        		if(vending.isValid(slot_location)) {
        			is_not_valid = false;
        		}
        		else {
        			System.out.println("That is not a valid slot code");
        		}
        	} while(is_not_valid);
        	
    		System.out.print("Name of the Item that you want to restock?: ");
        	name = user_input.nextLine();
        	do {
        		System.out.print("What is the price of this Item: ");
        		while (!user_input.hasNextDouble()) {
        			System.out.println("Please enter a valid price");
        			user_input.next();
        		}
        		price = user_input.nextDouble();
        		price = Math.round(price*100.0)/100.0;
        	} while (price < 0);
        	do {
        		System.out.print("How many items do you want to restock?: ");
        		while (!user_input.hasNextInt()) {
        			System.out.println("Please enter a valid integer between 1 and 15");
        			user_input.next();
        		}
        		count = user_input.nextInt();
        	} while (count < 1 | count > 15);
            user_input.nextLine();
            
            System.out.print("Would you like to stock slot " + slot_location + " with " + count + " " + name + " with a price of $" + price + " ? Type Y for yes and N for no: ");
    		String answer = user_input.nextLine();
    		if(answer.toUpperCase().equals("Y")) {
    			vending.getSlot(slot_location).setRestockName(name);
                vending.getSlot(slot_location).setRestockPrice(price);
                vending.getSlot(slot_location).setRestock(true);
                vending.getSlot(slot_location).setRestockCount(count);
    		}
    	} while(true);
    }
    //allow corporate to see current revenue
    static void currentRevenue(VendingMachine vending) {
    	System.out.println("\n");
    	System.out.println("The current revenue is " + vending.getTotalRevenue() + "\n");
    }
    //allows corporate to see purchae history of the machine
    static void purchaseHistory(VendingMachine vending) {
    	Scanner user_input = new Scanner(System.in);
    	System.out.println("\n");
    	if(vending.history_count > 0) {
    		System.out.println("List of Purchase History:");
    		for(int i = 0; i < vending.history_count; i++) {
    			int j = i+1;
    			System.out.println(j + ". " + vending.history[i].getName() + ", " + vending.history[i].getPrice());
    		}
    		System.out.print("Clear the Purchase History? Type Y for yes and N for no: ");
			String answer = user_input.nextLine();
			if(answer.toUpperCase().equals("Y")) {
				vending.history_count = 0;
				System.out.println("Purchase History has been cleared \n");
			}
    	}
    	else {
    		System.out.println("There have been no new purchases since the last time Purchase History was cleared.");
    	}
    }
    //allow corporate to see if vending machine is on and for how long, false means off and true means on
    static void viewStatus(VendingMachine vending) {
    	System.out.println("\n");
    	if(vending.getStatus()) {
    		System.out.println("This vending machine has been on for " + vending.getCurrentDay() + " days \n");
    	}
    	else
    		System.out.println("This vending machine is currently off. \n");
    }
    //allows corporate to see the current location of the vending machine
    static void viewLogistics(VendingMachine vending) {
    	System.out.println("This vending machine is located at zip code: " + vending.getLocation() + "\n");
    }

    //dev options
    static void devOpt(VendingMachine vending) {
    	int choice = 21;
        Scanner user_input = new Scanner(System.in);
        do {
            //menu
        	System.out.println("\n");
            System.out.print("\nCurrent Day: ");
            System.out.println(vending.getCurrentDay());
            System.out.println("1. View Detailed Inventory");
            System.out.println("2. Set Status of Vending Machine");
            System.out.println("3. Set Location of Vending Machine");
            System.out.println("4. Set Current day of Vending Machine");
            System.out.println("5. Exit Development Interface");
            do {
            	System.out.print("Choose an option: ");
            	while (!user_input.hasNextInt()) {
            		System.out.println("Please enter a valid choice");
            		user_input.next();
            	}
            	choice = user_input.nextInt();
            } while (choice < 1 | choice > 5);

            switch(choice) {
                case 1:
                    inventory(vending);
                    break;
                case 2:
                    setVendingStatus(vending);
                    break;
                case 3:
                    setVendingLocation(vending);
                    break;
                case 4:
                    setCurrentDay(vending);
                    break;
                case 5:
                	choice = -1;
                	break;
            }

            System.out.println(""); //blank line for spacing
        } while(choice != -1);
    }   //end of devOpt()
    
    //Functions for devOpt
    //allows the dev to set the status of the vending machine
    static void setVendingStatus(VendingMachine vending) {
    	Scanner user_input = new Scanner(System.in);
    	System.out.println("\n");
    	System.out.println("The current status of the vending machine is " + vending.getStatus());
    	System.out.print("Enter Y to turn on vending machine, or enter N to turn it off: ");
    	String answer = user_input.nextLine();
		if(answer.toUpperCase().equals("Y")) {
			vending.setStatus(true);
		}
		else if(answer.toUpperCase().equals("N")) {
			vending.setStatus(false);
		}
		System.out.println("The current status of the vending machine is " + vending.getStatus());
    }
    //allows the dev to set the location of the vending machine
    static void setVendingLocation(VendingMachine vending) {
    	Scanner user_input = new Scanner(System.in);
    	int answer;
    	System.out.println("\n");
    	System.out.println("The current location of the vending machine is " + vending.getLocation());
    	do {
    		System.out.print("Enter the zip code location for the vending machine: ");
        	while (!user_input.hasNextInt()) {
        		System.out.println("Please enter a valid zipcode");
        		user_input.next();
        	}
        	answer = user_input.nextInt();
        } while (String.valueOf(answer).length() != 5);
		vending.setLocation(answer);
		System.out.println("The current location of the vending machine is " + vending.getLocation());
    }
    //allows the dev to set the current day of the vending machine, or how many days have passed since turning the machine on
    static void setCurrentDay(VendingMachine vending) {
    	Scanner user_input = new Scanner(System.in);
    	int answer;
    	System.out.println("\n");
    	System.out.println("The current day of the vending machine is " + vending.getCurrentDay());
    	do {
    		System.out.print("Enter the day you want the vending machine to be set to: ");
        	while (!user_input.hasNextInt()) {
        		System.out.println("Please enter a valid integer");
        		user_input.next();
        	}
        	answer = user_input.nextInt();
        } while (answer < 0);
    	vending.setCurrentDay(answer);
		System.out.println("The current day of the vending machine is " + vending.getCurrentDay());
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
            //System.out.println("4. Dev options?");
            do {
            	System.out.print("Choose an option: ");
            	while (!user_input.hasNextInt()) {
            		System.out.println("Please enter a valid choice");
            		user_input.next();
            	}
            	choice = user_input.nextInt();
            } while (choice < 1 | choice > 4);
            

            switch(choice) {
                case 1:
                    customerI(v1);
                    break;
                case 2:
                    restockerI(v1);
                    break;
                case 3:
                    corporateI(v1);
                    break;
                case 4:
                    //dev options
                    devOpt(v1);
                    break;
            }

            System.out.println(""); //blank line for spacing
        } while(choice != -1);
        
        user_input.close();
    }

}//end of Main class
