//Documentation of the Item Class
//THIS FILE DOES NOT HAVE THE IMPLEMENTATION OF THE METHODS
//DO NOT USE THIS FILE TO RUN CODE
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
    String getName() { return name; }                   //returns item name as a String
    double getPrice() { return price; }                 //returns item price as a double
    boolean getIsHead() { return is_head; }             //returns a boolean saying if the item is the head of the linked list or not
    Item getNext() { return next; }                     //returns the item that is behind the current item
    int getDayAdded() { return day_added; }             //returns the day the item was added to the machine as an int
    boolean getInStock() { return is_instock; }         //returns if the item is in stock or not as a boolean

    void setName(String name) {    }                    //set the item name as a String
    void setPrice(double price) {    }                  //set the item price as double
    void setIsHead(boolean is_head) {    }              //set is_head as a boolean
    void setNext(Item item) {    }                      //set the item behind it as an Item
    void setDayAdded(int day) {    }                    //set the day the item was added as an int
    void setInStock(boolean is_instock) {    }          //set if the item is in stock or not as a boolean
}