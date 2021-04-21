/**
 * Class that represents an Author in the system, it is the child class of NormalUser.
 * 
**/

package cms;

import cms.Paper;
import java.util.*;


public class Author extends NormalUser {
    private ArrayList<Paper> paper = new ArrayList<Paper>();

    // The available option for Author
    public ArrayList <String> availableOption = new ArrayList<String>(){
        {
            add("Submit Paper");
            add("Paper Review Result");
            add("Conference Information");
        }
    };

    public Author (String emailAddress, String password, String firstName, String lastName, String highestQualification, String occupation, String employerDetail, int mobileNumber){
        /**
         * Constructor for the Auhor class. 
         * @param emailAddress is the email address of that user to login the account
         * @param password is the hashed password of that user
         * @param firstName is the first name of that user
         * @param lastName is the last name of that user
         * @param highestQualification is the highest qualification of that user
         * @param occupation is the occupation of that user
         * @param employerDetail is the employer detail provided
         * @param mobileNumber is the mobile number of that user
        **/
        super(emailAddress, password, firstName, lastName, highestQualification, occupation, employerDetail, mobileNumber);
    }
    
    public Author (String emailAddress, String password) {
        /**
         * Constructor for the Author class.
         * 
         * @param emailAddress is the email address of that user to login the account
         * @param password     is the hashed password of that user
         **/
        super(emailAddress, password);
    }

    public Paper submitPaper(String title, String author, Conference conf, ArrayList<String> topic){
        /**
         * Author's method to submit paper in the system.
         * @param name is the name of the paper
         * @param author is the author of the paper in full name
         * @param conference is the conference where the paper submitted
         * @param topic is the topic areas of the paper
         */
        Paper p = new Paper (title, author, conf, topic);
        return p;
    }

    public void viewProgressStatus (){
        // TODO : see whether need to implement this as it is not in the requirements!!!!
    }
}
