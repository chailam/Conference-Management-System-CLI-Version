/**
 * Class that represents a Reviewer in the system, it is the child class of NormalUser.
 * 
**/

package cms.entity;

import java.util.*;

public class Reviewer extends NormalUser{

    private ArrayList<String> chosenTopicAreas= new ArrayList<String>();
    private ArrayList<String> assignedPaper = new ArrayList<String>();

    public Reviewer(String role, String emailAddress, String password, String firstName, String lastName, String highestQualification, String occupation, String employerDetail, String mobileNumber, String conferenceName){
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
         * @param the conference the reviewer attended
        **/
        super(role, emailAddress, password, firstName, lastName, highestQualification, occupation, employerDetail, mobileNumber,conferenceName);
    }


    public void setTopicArea(ArrayList<String> topicareas){
    /**
     * Set topic into the chosen topic areas
     * @param 	the topic area to be set
     **/
        this.chosenTopicAreas = topicareas;
    }


    public ArrayList<String> retrieveTopicAreas(){
    /**
     * Retrieve the chosen topic areas
     * @return 	the chosen topic areas
     **/
        return this.chosenTopicAreas;
    }

    public void addAssignedPaper(String pName){
    /**
     * Add the assigned paper
     * @param the assigned paper
     **/
        this.assignedPaper.add(pName);
    }

    
    public ArrayList<String> retrieveAssignedPaper(){
    /**
     * Retrieve the assigned paper
     * @return 	the assigned paper
     **/
        return this.assignedPaper;
    }


    public void setAssignedPaper(ArrayList<String> papers){
    /**
     * Set the assigned paper
     * @return 	the assigned paper
     **/
        this.assignedPaper = papers;
    }
    

    //TODO: delete it when submit assignment, this is for developer debug purpose!!!
    @Override
    public String toString(){
        return String.format("emailAddress: " + getEmail() + ", password: " + getPassword() + ", firstName: " + getFirstName() + ", conferenceName: " + getConferenceName() + ", paperlist: " + retrieveAssignedPaper() + ", topic: " + retrieveTopicAreas());
    }
}