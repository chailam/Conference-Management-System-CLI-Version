/**
 * The text-based user interface of the system, act as boundary for the user.
 */


package cms;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.concurrent.TimeUnit;


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


    public String getUserOption (String[] options, String name){
        /**
         * To display the option available and then get the user input within option length
         * @param the option selected
         * @param the name to be greet
         */
        this.displayHeader();
        this.displayGreeting(name);
        System.out.println("Please enter your options:");

        // Print out all options
        for( int i = 0; i < options.length; i++){
            System.out.println((i+1) + ". " + options[i]);
        }
        // Print the footer
        this.displayFooter();

        int choice = 0; 
		while (choice < 1 || choice > options.length) {//loop until a valid option has been obtained
            if ( choice > options.length){
                System.out.println("Error: Invalid value. Please enter a valid value.");
            }
            System.out.print("Enter option (1 - "+ options.length +"): ");
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
		return options[choice-1];//return the option value selected	
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


    public void displayResult(String result){
    /**
     * To print the result processed by controller
     */
        System.out.println(result);
    }


    public void displayErrorMessage(String errorMsg){
    /**
     * To print the error message
     */
        System.out.println(errorMsg);
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


    public void displayAuthenErrMsg() throws InterruptedException{
    /**
     * The method to show the error message if not authenticated. 
     */
        this.displayHeader();
        this.displayGreeting("Guest");
        this.displayErrorMessage("Error: User is not valid.");
        this.displayErrorMessage("Please login again or enter contact admin for any questions.");
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

}
