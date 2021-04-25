/**
 * Class that represents an Author in the system, it is the child class of NormalUser.
 * 
**/

package cms;

import cms.Paper;
import java.util.*;


public class Author extends NormalUser {
    private ArrayList<String> paper = new ArrayList<String>();

    public Author (String role, String emailAddress, String password, String firstName, String lastName, String highestQualification, String occupation, String employerDetail, String mobileNumber, String conferenceName){
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
         * @param the conference of the author attended
        **/
        super(role, emailAddress, password, firstName, lastName, highestQualification, occupation, employerDetail, mobileNumber,conferenceName);
    }
    


    public void addPaper(String pName){
        /**
         * Add paper name to the paper list
         * @param name of the paper to be added
         */
        this.paper.add(pName);
    }

    public ArrayList<String> retrievePaper(){
        /**
         * Retrieve a list of paper name
         * @return a list of paper name the author submitted
         */
        return this.paper;
    }


    public  void setPaper(ArrayList<String> papers){
        /**
         * Set the paper list
         * @return a list of paper name
         */
        this.paper = papers;
    }


    //TODO: delete it when submit assignment, this is for developer debug purpose!!!
    @Override
    public String toString(){
        return String.format("emailAddress: " + getEmail() + ", password: " + getPassword() + ", firstName: " + getFirstName() + ", conferenceName: " + getConferenceName() + ", paperlist: " + retrievePaper());
    }
}
