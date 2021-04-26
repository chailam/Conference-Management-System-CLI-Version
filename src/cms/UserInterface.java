/**
 * The text-based user interface of the system, act as boundary for the user.
 */


package cms;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.concurrent.TimeUnit;
import java.util.*;


public class UserInterface {
    String [] header = {"                                                     ",
                        "                                                     ",
                        "*****************************************************",
                        "                                                     ",
                        "         Conference Management System (CMS)          ",
                        "                                                     ",
                        "*****************************************************"};

    String [] footer = {"*****************************************************",
                        "            powered by Monash Conference Centre (MCC)",
                        "*****************************************************"};

    private Scanner scanner;


    public UserInterface(){
    /**
     * The constructor of the UserInterface. It initiate an input Scanner.
     */
        scanner = new Scanner(System.in);
        scanner.useDelimiter(System.lineSeparator());
    }
    

    public void displayHeader (){
    /**
     * To print the conststent header of the system
     */
        for(String line: header) {
            System.out.println(line);
        }
    }


    public void displayGreeting (String name){
    /**
     * To greet the user
     * @param the name of user to be greet
     */
        System.out.println("Hi "+ name);
        System.out.println("");
        System.out.println("");
    }


    public String getUserOption (ArrayList<String> options, String name, boolean bo){
        /**
         * To display the option available and then get the user input within option length
         * @param the option selected
         * @param the name to be greet
         * @param true to show header and footer; false otherwise
         */
        if (bo == true){
            this.displayHeader();
            if (!name.equals("")){
                this.displayGreeting(name);
            }
        }
        System.out.println("Please enter your options:");

        // Print out all options
        for( int i = 0; i < options.size(); i++){
            System.out.println((i+1) + ". " + options.get(i));
        }
        if (bo == true){
            // Print the footer
            this.displayFooter();
        }
        int choice = 0; 
		while (choice < 1 || choice > options.size()) {//loop until a valid option has been obtained
            if ( choice > options.size()){
                System.out.println("Error: Invalid value. Please enter a valid value.");
            }
            System.out.print("Enter option (1 - "+ options.size() +"): ");
			try{
				choice = Integer.parseInt(scanner.nextLine());
                // verify boundary value
                if (choice <= 0){
                    System.out.println("Error: Invalid value. Please enter a valid value.");
                }
                
			}catch (InputMismatchException | NumberFormatException e) { //catching the non integer inputs
                choice = 0;
                System.out.println("Error: Invalid value. Please enter a valid value.");
			}
		}
		return options.get(choice-1);//return the option value selected	
    }


    public void displayFooter (){
    /**
     * To print the conststent footer of the system
     */
        System.out.println("");
        System.out.println("");
        for(String line: footer) {
            System.out.println(line);
        }
    }


    public void displayMessage(String msg){
    /**
     * To print the message 
     */
        System.out.println(msg);
    }


    public String[] getAuthentication(){
    /**
     * To get the email address and password of the user
     * @return the emailAdress and Password retrieved
     */
        this.displayHeader();
        this.displayGreeting("Guest");
        
        System.out.println("Please enter your user credentials.");
        System.out.print("Email Address: ");
        String emailAddress = scanner.nextLine(); // Read the user input
        System.out.print("Password: ");
        String password = scanner.nextLine(); // Read the user input
        this.displayFooter();

        return new String[] {emailAddress, password};
    }


    public void displayMsgWithSleep(String message) throws InterruptedException{
    /**
     * The method to show the error message.
     * It will sleep for 2 seconds before jumping to other screen
     */
        this.displayHeader();
        System.out.println(message);
        this.displayFooter();
        TimeUnit.SECONDS.sleep(2);
    }


    public boolean getExitCommand(){
    /**
     * The method to detect exit command 
     * @return true if exit entered
     */
        String input = "";
        while (!input.equals("exit")){
            System.out.print("Enter \"exit\" to return to previous page: ");
            input = scanner.nextLine();
        }
        return true;
    }


    public String[] getRegistration(){
    /**
     * To get the required registration information from the user
     * @return the information retrieved
     */
        this.displayHeader();
        System.out.println("Registration Process (1/2)\n\nEnter user details.");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine(); // Read the user input
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine(); // Read the user input
        System.out.print("Email Address: ");
        String emailAddress = scanner.nextLine(); // Read the user input
        System.out.print("Password: ");
        String password = scanner.nextLine(); // Read the user input
        System.out.print("Highest Qualification: ");
        String highestQualification = scanner.nextLine(); // Read the user input
        System.out.print("Occupation: ");
        String occupation = scanner.nextLine(); // Read the user input
        System.out.print("Employer's details: ");
        String employerDetail = scanner.nextLine(); // Read the user input
        System.out.print("Mobile number: ");
        String mobileNumber = scanner.nextLine(); // Read the user input
        this.displayFooter();

        return new String[] {firstName, lastName, emailAddress, password, highestQualification, occupation, employerDetail, mobileNumber};
    }
    

    public void confirmRegistration(String firstName, String lastName, String emailAddress, String highestQualification, String occupation, String employerDetail, String mobileNumber){
    /**
     * The message to confirm the registration
     */
        this.displayHeader();
        System.out.println("Registration Process (2/2)\n\nPlease confirm your user details.");
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Email Address: " + emailAddress);
        System.out.println("Highest Qualification: " + highestQualification);
        System.out.println("Occupation: " + occupation);
        System.out.println("Employer's details: " + employerDetail);
        System.out.println("Mobile number: " + mobileNumber);
    }

    public String[] getCreateConference() {
        /**
         * To get the required create conference information from the user
         * @return the information retrieved
         */
        this.displayHeader();
        System.out.println("Conference Creation Process (1/3)\n\nPlease enter the conference details.");
        System.out.print("Conference Name: ");
        String confName = scanner.nextLine(); // Read the user input
        System.out.print("Conference Place: ");
        String place = scanner.nextLine(); // Read the user input
        System.out.print("Conference Date (dd/mm/yyyy): ");
        String date = scanner.nextLine(); // Read the user input
        System.out.print("Paper Submission Due Date (dd/mm/yyyy): ");
        String submitDueDate = scanner.nextLine(); // Read the user input
        System.out.print("Paper Review Due Date (dd/mm/yyyy): ");
        String reviewDueDate = scanner.nextLine(); // Read the user input
        this.displayFooter();

        return new String[]{confName,place, date, submitDueDate, reviewDueDate};
    }


    public void confirmConferenceCreation(String confName, String place,String date, String submitDueDate, String reviewDueDate, String topic){
        /**
         * The message to confirm the conference creation
         */
        this.displayHeader();
        System.out.println("Registration Process (3/3)\n\nPlease confirm your conference details.");
        System.out.println("Conference Name: " + confName);
        System.out.println("Conference Place:  " + place);
        System.out.println("Conference Date (dd/mm/yyyy): " + date);
        System.out.println("Paper Submission Due Date (dd/mm/yyyy): " + submitDueDate);
        System.out.println("Paper Review Due Date (dd/mm/yyyy): " + reviewDueDate);
        System.out.println("Topic Areas: " + topic);
    }


    public String[] getTopicAreas(ArrayList<String> topicAreas){
        /**
         * To get the topic areas from the user
         * @return the topic areas retrieved
         */
        this.displayHeader();
        System.out.println("Conference Creation Process (2/3)");
        System.out.println("Please choose relevant the topic areas by \nentering their number index, separated by comma. ");
        System.out.println("For example, input: 1,2,3");
        // print our all topic areas available
        for (int i = 0; i < topicAreas.size() ; i++){
            System.out.println((i+1) + " . " + topicAreas.get(i));
        }
        System.out.print("Please enter your topic areas number index: ");
        String topicsInd = scanner.nextLine(); // Read the user input
        System.out.println("If your topics are not in the list, please \ntype your topic areas here, separated by comma.");
        System.out.println("For example, input: Information Technology, Cybersecurity");
        System.out.print("Please enter your topic areas name: ");
        String topicsName = scanner.nextLine(); // Read the user input
        this.displayFooter();
        return new String[] {topicsInd,topicsName};
    }
}
