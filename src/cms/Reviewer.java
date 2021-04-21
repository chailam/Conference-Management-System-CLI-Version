/**
 * Class that represents a Reviewer in the system, it is the child class of NormalUser.
 * 
**/

package cms;

import cms.Paper;
import java.util.*;

public class Reviewer extends NormalUser{

    private ArrayList<String> chosenTopicAreas= new ArrayList<String>();
    private ArrayList<Paper> assignedPaper = new ArrayList<Paper>();
    // The available option for Reviewer
    public ArrayList <String> availableOption = new ArrayList<String>(){
        {
            add("Accept/Reject Paper Review");
            add("Submit Evaluation of Paper");
            add("Conference Information");
        }
    };


    public Reviewer(String emailAddress, String password, String firstName, String lastName, String highestQualification, String occupation, String employerDetail, int mobileNumber){
        /**
         * Constructor for the Reviewer class. 
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

    public Reviewer (String emailAddress, String password) {
        /**
         * Constructor for the Reviewer class.
         * 
         * @param emailAddress is the email address of that user to login the account
         * @param password     is the hashed password of that user
         **/
        super(emailAddress, password);
    }


    public void addTopicArea(String topic){
    /**
     * Add topic into the chosen topic areas
     * @param 	the topic area to be added
     **/
        this.chosenTopicAreas.add(topic);
    }


    public ArrayList<String> retrieveTopicAreas(){
    /**
     * Retrieve the chosen topic areas
     * @return 	the chosen topic areas
     **/
        return this.chosenTopicAreas;
    }


    public void submitEvaluation (Paper p){
        //TODO: implement this functionality!!!!
    }

    
}
