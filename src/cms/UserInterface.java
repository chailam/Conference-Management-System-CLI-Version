/**
 * The text-based user interface of the system, act as boundary for the user.
 */


package cms;

import java.util.Scanner;


public class UserInterface {
    String [] header = {"*****************************************************",
                        "                                                     ",
                        "         Conference Management System (CMS)          ",
                        "                                                     ",
                        "*****************************************************"};

    String [] footer = {"*****************************************************",
                        "            powered by Monash Conference Centre (MCC)",
                        "*****************************************************"};

    private Scanner instream;

    public UserInterface(){
       instream = new Scanner(System.in);
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
    }


    public void displayFooter (){
    /**
     * To print the conststent footer of the system
     */
        for(String line: footer) {
            System.out.println(line);
        }
    }




    public void getUserSelection (){
        // TODO: see how to get selection!!!!
    }


    public void displayResult(String result){
    /**
     * To print the result processed by controller
     */
        System.out.println(result);
    }


}
