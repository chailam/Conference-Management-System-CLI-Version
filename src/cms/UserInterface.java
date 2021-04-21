/**
 * The text-based user interface of the system, act as boundary for the user.
 */


package cms;

import java.util.Scanner;


public class UserInterface {
    String [] header = {"DISPLAY HEADER IMAGE HERE"};
    String [] footer = {"DISPLAY FOOTER IMAGE HERE"};
    private Scanner instream;

    public UserInterface(){
       instream = new Scanner(System.in);
    }
    

    public void displayHeader (){
    /**
     * To print the conststent header of the system
     */
        System.out.println(header);
    }


    public void displayFooter (){
    /**
     * To print the conststent footer of the system
     */
        System.out.println(footer);
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
