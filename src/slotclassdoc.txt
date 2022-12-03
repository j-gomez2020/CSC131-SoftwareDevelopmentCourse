//Documentation of the Slot class
//THIS FILE DOES NOT HAVE THE IMPLEMENTATION OF THE METHODS
//DO NOT USE THIS FILE TO RUN CODE

//sorry not much to say about this
//you wont have to use this class much as it is mostly used by the vending machine class internally
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