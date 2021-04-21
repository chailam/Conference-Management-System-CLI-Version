/**
 * Class that represents a Normal User in the system, such as Author, Chair and Reviewer.
 * 
**/

package cms;

import java.util.*;
import cms.User;
import cms.Conference;

public class NormalUser extends User {

    private String firstName;
    private String lastName;;
    private String highestQualification;
    private String occupation;
    private String employerDetail;
    private int mobileNumber;
    private ArrayList<String> notification = new ArrayList<String>();
    private Conference conference;

    public NormalUser(String emailAddress, String password, String firstName, String lastName, String highestQualification, String occupation, String employerDetail, int mobileNumber){
    /**
     * Constructor for the NormalUser class. 
     * @param emailAddress is the email address of that user to login the account
     * @param password is the hashed password of that user
     * @param firstName is the first name of that user
     * @param lastName is the last name of that user
     * @param highestQualification is the highest qualification of that user
     * @param occupation is the occupation of that user
     * @param employerDetail is the employer detail provided
     * @param mobileNumber is the mobile number of that user
    **/
        super(emailAddress, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.highestQualification = highestQualification;
        this.occupation = occupation;
        this.employerDetail = employerDetail;
        this.mobileNumber = mobileNumber;
    }

    public NormalUser(String emailAddress, String password){
    /**
     * Constructor for the NormalUser class. 
     * @param emailAddress is the email address of that user to login the account
     * @param password is the hashed password of that user
    **/
        super(emailAddress, password);
    }

    public String getFirstName(){
    /**
	 * Getter for first name
	 * @return 	the first name of the user 
     **/
        return this.firstName;
    }

    
    public String getLastName(){
    /**
     * Getter for last name
     * @return 	the last name of the user 
     **/
        return this.lastName;
    }

        
    public String getName(){
    /**
     * Getter for name, which is firstname + lastname
     * @return 	the name of the user 
     **/
        String name = this.firstName + this.lastName;
        return name;
    }

        
    public String getHighestQualification(){
    /**
     * Getter for highest qualification
     * @return 	the highest qualification of the user 
     **/
        return this.highestQualification;
    }
            
    public String getOccupation(){
    /**
     * Getter for occupation
     * @return 	the occupation of the user 
     **/
        return this.occupation;
    }

            
    public String getEmployerDetail(){
    /**
     * Getter for employer detail
     * @return 	the employer detail of the user 
     **/
        return this.employerDetail;
    }

            
    public int getMobileNumber(){
    /**
     * Getter for mobile number
     * @return 	the mobile number of the user 
     **/
        return this.mobileNumber;
    }

            
    public Conference getConference(){
    /**
     * Getter for conference
     * @return 	the conference of the user 
     **/
        return this.conference;
    }

            
    public ArrayList<String> retrieveNotification(){
    /**
     * Getter for list of notification
     * @return 	the list of notification of the user 
     **/
        return this.notification;
    }

            
    public void setFirstName(String firstName){
    /**
     * Setter for first name
     * @param the first name of the user 
     **/
        firstName = this.firstName;
    }

    public void setLastName(String lastName){
    /**
     * Setter for last name
     * @param the last name of the user 
     **/
        lastName = this.lastName;
    }

    public void setHighestQualification(String highestQualification){
    /**
     * Setter for highest qualification
     * @param the highest qualification of the user 
     **/
        highestQualification = this.highestQualification;
    }
            
    public void setOccupation(String occupation){
    /**
     * Setter for occupation
     * @param the occupation of the user
     **/
        occupation = this.occupation;
    }

            
    public void setEmployerDetail(String employerDetail){
    /**
     * Setter for employer detail
     * @param the employer detail of the user 
     **/
        employerDetail = this.employerDetail;
    }

            
    public void setMobileNumber(int mobileNumber){
    /**
     * Setter for mobile number
     * @param the mobile number of the user 
     **/
        mobileNumber = this.mobileNumber;
    }

            
    public void setConference(Conference conference){
    /**
     * Setter for conference
     * @param the conference of the user 
     **/
        conference = this.conference;
    }

            
    public void addNotification(String notification){
    /**
     * Add notification to the notification list
     * @param 	the notification to be added to the list
     **/
        this.notification.add(notification);
    }
        
    



    
}
