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
     * The constructor of the UserInterface. It intiate an input Scanner.
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


    public String getUserOption (ArrayList<String> options, String name){
        /**
         * To display the option available and then get the user input within option length
         * @param the option selected
         * @param the name to be greet
         */
        this.displayHeader();
        if (!name.equals("")){
            this.displayGreeting(name);
        }
        System.out.println("Please enter your options:");

        // Print out all options
        for( int i = 0; i < options.size(); i++){
            System.out.println((i+1) + ". " + options.get(i));
        }
        // Print the footer
        this.displayFooter();

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


    public void displayErrMsg(String message) throws InterruptedException{
    /**
     * The method to show the error message if not authenticated. 
     */
        this.displayHeader();
        this.displayGreeting("Guest");
        this.displayMessage(message);
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
        this.displayMessage("Registration Process (1/2)\n\nEnter user details.");
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

        return new String[] {firstName, lastName, emailAddress, password, highestQualification, occupation, employerDetail, mobileNumber};
    }
    

    public void confirmRegistration(String firstName, String lastName, String emailAddress, String highestQualification, String occupation, String employerDetail, String mobileNumber){
    /**
     * The message to confirm the registration
     * @return the information retrieved
     */
        this.displayMessage("Registration Process (2/2)\n\nPlease confirm your user details.");
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Email Address: " + emailAddress);
        System.out.println("Highest Qualification: " + highestQualification);
        System.out.println("Occupation: " + occupation);
        System.out.println("Employer's details: " + employerDetail);
        System.out.println("Mobile number: " + mobileNumber);


    }


        




}
