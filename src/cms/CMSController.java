/**
 * The controller of the system.
 */


package cms;

import cms.*;
import java.util.*;


public class CMSController {
    public ArrayList<String> availableTopicAreas = new ArrayList<String>(){
        {
            add("Artificial Intelligence");
            add("Human Computer Interaction");
            add("Data Mining & Information Retrieval");
            add("Image Processing");
            add("Big Data");
            add("Computer Networks");
            add("Software Engineering ");
            add("Security & Cryptography");
            add("Robotics & Automation");
            add("Database & Information Systems");
        }
    };
    
    public CMSController(){
        /**
         * Constructor for the CMS controller class.
         * 
         **/

        // The user interface invoked
        UserInterface ui = new UserInterface();

    }

    public void verifyInput(){
        // TODO: To be implemented

    }
}
