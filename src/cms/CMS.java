/**
 * The controller of the system.
 */


package cms;

import cms.*;
import java.util.*;


public class CMS {
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

    private ArrayList<User> userList = new ArrayList<User>();
    private ArrayList<Paper> paperList= new ArrayList<Paper>();
    private ArrayList<Conference> conferenceList = new ArrayList<Conference>();
    
    public CMS(){
        /**
         * Constructor for the CMS controller class.
         * 
         **/

        // The user interface invoked
        UserInterface ui = new UserInterface();

    }
}
