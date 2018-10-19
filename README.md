# Advanced Java - Assignment Two

#### Order Processing in Standalone and Client Server Software Prototypes

---

### Features Completed
Standalone Implementation

- GUI - all aspects of the interface implemented to meet given screenshots from assignment description.
- CSV - programatically read in all values from the CSV file and populate menu item boxes with corrected inputs
- Validations - error messages displayed for any and all incorrect inputs as per the design brief
- Placing, preparing and billing orders functionality achieved
- Code created according to the MVC design principles
- Two methods to display orders added
	1. Clicking on an order and clicking the display order button to view that specific order
	2. Entering a table number and viewing all orders placed for that table

Client Server Implementation

- GUI - all aspects of the interfaces implemented to meet the functional requirements laid out in assignment description.
- Database - programatically create and populate the database with appropriate tables if the database doesn't exist. 
	- Menu items used in the customer form sourced from the database, not just taken from the CSV, although the CSV is used to populate the database
- Validations - the appropriate validations and/or confirmations have been added to each form to ensure usability.
- Code created according to the MVC design principles
- Placing, prepareing and billing orders functionality achieved
- Threading used to automatically update the appropriate forms with new data as they are created

---

### Expected Marks

1. GUI for Standalone								:	50/60
2. Four uses of lambdas/streams in validations 		:	10/10
3. Four uses of lambdas/streams in event handling	:    0/10
4. Four uses of collections/generic methods			:	10/10
5. Programatically creating and processing database	:	10/10
6. Programatically loading combo boxes from CSV		: 	10/10
7. Relate the standalone implementation to MVC		:	30/30
8. GUI for Client Server							: 	60/60
9. Use of multi-threading and database 				: 	15/20
10. Pre-integration folder with experiments			:    5/10
11. Two improvements								:    4/10
12. Work-in-progress demonstrations					:    7/10
13. Javadoc standard documentation					: 	10/10
14. An 'about' dialog from the menu					:	N / A
15. Formally written word report					:	30/30
16. Readme.txt 										: 	10/10 

17. Total											: 261/300

---

### Reasoning for Mark Allocation

1. GUI Standalone (60/60) : GUI recreated to match the design brief. All functionality successfully implemented. Error messages, validations, confirmations, conditional logic and sanitised menu item data all included.
2. Lambdas/streams in validation (10/10) : streams used in multiple instances in both Standalone and Client Server implementations.
3. Lambdas/streams in event handling (0/10) : Not used.
4. Collections/generic methods (10/10) : Collections used extensively throughout prototypes to sort, sift and store data.
5. Creating and processing database (10/10) : Database programatically created along with tables if they do not exist.
6. Loading comboboxes from CSV (10/10) : Comboboxes successfully loaded, and inputs sanitised (remove unnecesary 'beverage' keyword, capitalise inputs etc).
7. MVC (30/30) : the MVC design principles have been followed and used throughout both the standalone and the client server prototypes. The main functionality was divided into a view, model and controller class. The view class contained all the necessary functions to get and set values on the form and to switch between panels in the JPanel card layouts. The model contained all the functions necessary to process and validate data without knowing of the view. The controller facilitated interactions between the two and controlled when to change the view and when to update the model appropriately.
8. GUI for Client Server (50/60) : GUI created contains three views - customer, receptionist and chef views. All functionality successfully implemented. Bill functionality doesn't display a bill in the common sense of the term.
9. Multi-threading and database (15/20) : Database was used, created, interacted with throughout each form. Threading was used however only one thread was ever necessary to complete the functionality required.
10. Pre-integration experiments (5/10) : Whilst experiments were conducted, they were conducted in a messy fashion. Some experiments were done on a seperate file, others were done in branches of the main git repository before being merged, some where integrated on a copy of the main source file, before being integrated into the master branch alongside the changes the other group member had committed.
11. Two improvements (4/10) : the improvement to the display orders button on the Standalone form makes use of a Focus Listener to check if a specific order was attempted to be viewed, or if the users are trying to search for all orders of a specified table.
12. Work-in-progress demonstrations (7/10) : the team had not been made until week nine thus week eight was skipped. All group members attended week nine, ten and eleven demonstrations. There wasn't much to showcase in week nine as the group had been newly formed. In week ten the standalone prototype had been completed but not showcased as the tutor didn't want to see it. In week eleven although the tutorial was attended, as the group was left to last (like every week), a demonstration could not be done as this would of had to occur half an hour past the conclusion of the tutorial, and a group member would have had no way of getting home if that were the case. A supplement week twelve demonstration has been scheduled instead.
13. Javadoc standard documentation (10/10) : All functions and classes and borrowed code have been commented. Long methods have also been commented step by step for added clarity.
14. 'About' dialog (N/A) : No marks were to be allocated.
15. Word report (30/30) : TODO
16. Readme.txt (10/10) : All requirements of the read me file have been laid out and met appropriately.

---

### Borrowed Code

######Note: All location entries include lines which are comments, such as purpose, source, and end of borrowed code

CSVReader 

	- Code to capitalise first letters in a string
	- Source :  https://stackoverflow.com/questions/1892765/how-to-capitalize-the-first-character-of-each-word-in-a-string
	- Location : Lines 108-113

OrderStatus

	- Code to open confirmation dialog and act based on result
	- Source : https://stackoverflow.com/questions/8689122/joptionpane-yes-no-options-confirm-dialog-box-issue
	- Location : Lines 48-54

MainWindow

	- Code to clear JTable values
	- Source : https://stackoverflow.com/questions/6232355/deleting-all-the-rows-in-a-jtable
	- Location : Lines 154-161, Lines 178-185

SQLConnector

	- Code to test connection to mySQL
	- Source : https://stackoverflow.com/questions/7764671/java-jdbc-connection-status
	- Location : Lines 36-41

ClientScreenView

	- Code to clear JTable values (same as MainWindow)
	- Source : https://stackoverflow.com/questions/6232355/deleting-all-the-rows-in-a-jtable
	- Location : Lines 83-90

	- Code to display popup with message
	- Source : https://stackoverflow.com/questions/9119481/how-to-present-a-simple-alert-message-in-java
	- Location : Lines 168-170

ClientScreenController

	- Code to create a password insertion popup
	- Source : https://stackoverflow.com/questions/8881213/joptionpane-to-get-password
	- Location : Lines 120-131

ChefScreenController

	- Code to open confirmation panel
	- Source : https://stackoverflow.com/questions/8689122/joptionpane-yes-no-options-confirm-dialog-box-issue
	- Location : Lines 55-61
