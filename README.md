Requirements:
Need to have a Java runtime environment (JRE) that is at least version 11 or newer to run the
program.

How to run the program:
You can double click on the batch file called “run.bat” if on windows, or you can run the program
manually in a command line by typing “java -jar csc131.jar” (Need to specify file location if your
command line is not in the same directory e.g. “C:\Users\User\Desktop\IntelliJ
Projects\CSC131-Main_branch\out\artifacts\csc131_jar”)

Read Me instructions for Vending Machine

1. Instructions for how to install in case of no executable
This program is made using Java. There is only one main program and all the included classes
and functions are included in the main program. This means that the code can simply be copied
and pasted into any IDE that supports Java. There are no other files that are needed, except for
any libraries specified on the top, but these libraries are the common libraries found in all
common IDE's. There are also no external databases, all data is stored in local or global
variables.

2. Instructions on operating the Vending Machine code

(Part 1: Using the customer Interface)
To run this program, simply run the program. You will first be greeted with the current day of the
program (during the first time that you run this program, this day will be 0), and 3 options to
choose from. The customer interface is where a customer can purchase products, and is what is
usually expected to be running at most times. During startup, if you first try to enter the customer
interface, it will inform you that the vending machine is out of order. This is because
you need to "turn on" the vending machine first. To do this, from the main menu, you need to
enter the Developer menu. This is a secret menu, you simply enter (4). When you do, it will give
you a list of options to choose from. Enter (2) to set the status of the vending machine. To turn
on the vending machine, just enter (y) to turn it on. If you did this correctly, it will tell you that the
current status of the vending machine is true. In this case, true means on, and false means off.
Afterwards, you will be brought back to the developer menu, where you can pick more options if
you like, but you can simply exit if you like. The other options are to look at the inventory (1). To
set the current location of the vending machine (3) in zip code format, and to set the current day
(4). Setting the current day is meant to simulate how many days have passed, so this value is
cumulative of all days passed. To exit, simply enter (5). This will bring you back to the main
menu. Afterwards, you can enter the customer interface again. Now you can order items by
entering the appropriate slot code when prompted, and then entering the correct amount of
money when asked for payment. Only correct entries can be entered, and will constantly prompt
you until a proper entry is entered. This will continue to loop, unless you enter (qq) when asked
for a slot number. This is another secret option that is hidden from the layman. When qq is
entered, you will be brought back to the main menu.

(Part 2: Using the Restocker Interface)
If you try to enter the Restocker Interface when no items have been recalled or asked to be
restocked, then you will simply be told that there are no items to restock or recall. To change this
you need to enter the Corporate Interface, since they are the ones that can make such
decisions. To do this, enter (3) to enter the corporate interface. Once here you will be given
eight options. To suggest a recall, enter (2). You will then be prompted to enter the slot number
that you wish to recall, this will loop until a correct response is entered. After entering a valid slot
code, you will then be asked to confirm your selection, entering (y) will confirm this, and flag that
slot to be recalled. When you pick a slot to be recalled, all items of that slot will be recalled when
the restocker interface is engaged. After you confirm or not, you will be asked to enter another
slot number, this action will continue to loop until (qq) is entered. This will bring you back to the
corporate interface. To suggest a restock of a slot, enter (3) in the corporate interface. You will
then be asked which slot number you want to restock. After entering a valid slot code, you are
then asked for the name of the item that you want to enter into that slot number. After entering a
name (be careful, any input is valid, so whatever you enter exactly will be the name of this new
item), you will be asked for the price of this item. Lastly, you will be asked how many items you
wish to enter. Be advised, each slot only holds 15 items, you can enter more, but any extra will
be ignored. Also, if you do not empty the slot first, these new items will be entered as long as
there is space at the end, and the name and the price of this new item will take the place of the
name and the price of this slot. The vending machine will continue to dispense the items in front
first at the correct price, so this can cause confusion for the customer, it is advised to empty a
slot first before entering new items in that slot. After entering all the information, you will be
asked to confirm with a simple (y) or (n), entering y confirms your choice, and sets the flag at
that location and creates a new item ready to be stored. Any other input invalidates your choices
until now. Again, this will continue to loop until you enter (qq). This brings you back to the
corporate interface. Exit by entering (8), and then enter (2) to enter the restocker interface. If
you have made any recall or restock suggestions, it will suggest all recalls first, and then it will
list all restocks. When asked to recall or restock, simply enter (y) to confirm your action, and the
vending machine will do the rest, any other input is regarded as a no and will skip that slot unto
the next one. That is all there is to the restocker interface.

(Part 3: The Corporate Interface)
In the main menu, enter (3) to enter the corporate interface, here you can enter (1) to see a
more detailed view of the inventory, including the name, price, and how many of those items are
in each slot. (2) and (3) take you to the recall and restock menu (please see Part 2 for how to
use these options). Entering (4) lets you see the current revenue, which is the accumulation of
prices of all items sold by the vending machine. Entering (5) lets you see the purchase history,
which is a list of items purchased in order from first purchase to last, with the name of the items
and their prices. After seeing the list, you will be asked if you would like to clear this list, entering
(y) clears this list, and any other input does not. Entering (6) lets you see if the vending machine
is on or off, along with how many days it has been on for. This number is simply the current day
of the machine. Entering (7) lets you see the zip code location of the vending machine. And
lastly, entering (8) lets you exit the corporate interface, and brings you back to the main menu.
