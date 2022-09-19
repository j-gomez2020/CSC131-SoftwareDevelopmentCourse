//Documentation of the Vending Machine Class
//THIS FILE DOES NOT INCLUDE THE IMPLEMENTATION OF THE METHODS
//DO NOT USE THIS FILE TO RUN CODE
class VendingMachine {
    private Item slots[][] = new Item[8][15];   //8 rows, 15 collumns     each slot will have a linked list of Items
    private int current_day;                    //the day that the vending machine is currently on
                                                //use caution when changing current day as it can have unknown effects
                                                //on how the data is stored and could cause problems down the line

    private int temporary_day;                  //NOT KNOWN IF NEEDED YET   maybe use this instead of current_day
                                                //                          when using dev options

    
    VendingMachine() {    }     //constructor to initialize the object from a database or text files
    void displaySlots() {    }  //display the vending machine inventory in text or a GUI

    int getCurrentDay(){ return current_day; }  //returns current day as an int

    Item getSlotHead(String slot_code) {    }   //takes a String as input   (EX: A5, B12, D1)
                                                //returns the first item in the slot
                                                //will return null if there is no item in that slot

    void stackItem (Item item, String slot_code) {    } //takes an Item, and slot code as input (EX: slot_code = A13)
                                                            //stacks an Item into the linked list at the front
                                                            //first in first out

    boolean inStock(String slot_code) {    }    //takes a String as input   (EX: A5, B12, D1)
                                                //checks if a slot has any stock left
                                                //returns true for in stock     false for out of stock

    int getRowNumber(String slot_code) {    }   //takes a String as input   (EX: A5, B12, D1)
                                                //converts a letter into a row number for the 2d array (slots[][])
                                                    //EX: slot_code = A5    returns row=0               slot_code = C4  returns row=2
                                                //POTENTIAL ISSUE: will break if chars that should be digits are letters
                                                    //EX: slot_code = AAAA12    slot_code = BB1

    int getCollumnNumber(String slot_code) {    }   //takes a String as input   (EX: A5, B12, D1)
                                                    //Convert string version of a number into int version
                                                        //EX:   slot_code=A6      return row=5            
                                                        //EX:   slot_code=C3    return row=2
}                                                   