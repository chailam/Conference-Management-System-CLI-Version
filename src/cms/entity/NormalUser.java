/**
 * Class that represents a Normal User in the system, such as Author, Chair and Reviewer.
 * 
**/

package cms.entity;

import java.util.*;

public class NormalUser extends User {

    private String firstName;
    private String lastName;;
    private String highestQualification;
    private String occupation;
    private String employerDetail;
    private String mobileNumber;
    private ArrayList<String> notification = new ArrayList<String>();
    private String conferenceName;

    public NormalUser(String role, String emailAddress, String password, String firstName, String lastName, String highestQualification, String occupation, String employerDetail, String mobileNumber){
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
        super(role, emailAddress, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.highestQualification = highestQualification;
        this.occupation = occupation;
        this.employerDetail = employerDetail;
        this.mobileNumber = mobileNumber;
    }

    public NormalUser(String role, String emailAddress, String password, String firstName, String lastName, String highestQualification, String occupation, String employerDetail, String mobileNumber, String conferenceName){
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
     * @param conference of the user attended
    **/
        super(role, emailAddress, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.highestQualification = highestQualification;
        this.occupation = occupation;
        this.employerDetail = employerDetail;
        this.mobileNumber = mobileNumber;
        this.conferenceName = conferenceName;
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

            
    public String getMobileNumber(){
    /**
     * Getter for mobile number
     * @return 	the mobile number of the user 
     **/
        return this.mobileNumber;
    }

            
    public String getConferenceName(){
    /**
     * Getter for conference
     * @return 	the conference of the user 
     **/
        return this.conferenceName;
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
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
    /**
     * Setter for last name
     * @param the last name of the user 
     **/
        this.lastName = lastName;
    }

    public void setHighestQualification(String highestQualification){
    /**
     * Setter for highest qualification
     * @param the highest qualification of the user 
     **/
        this.highestQualification = highestQualification;
    }
            
    public void setOccupation(String occupation){
    /**
     * Setter for occupation
     * @param the occupation of the user
     **/
        this.occupation = occupation;
    }

            
    public void setEmployerDetail(String employerDetail){
    /**
     * Setter for employer detail
     * @param the employer detail of the user 
     **/
        this.employerDetail = employerDetail;
    }

            
    public void setMobileNumber(String mobileNumber){
    /**
     * Setter for mobile number
     * @param the mobile number of the user 
     **/
        this.mobileNumber = mobileNumber;
    }

            
    public void setConference(String conferenceName){
    /**
     * Setter for conference
     * @param the conference of the user 
     **/
        this.conferenceName = conferenceName;
    }

            
    public void addNotification(String notification){
    /**
     * Add notification to the notification list
     * @param 	the notification to be added to the list
     **/
        this.notification.add(notification);
    }  


    //TODO: delete it when submit assignment, this is for developer debug purpose!!!
    @Override
    public String toString(){
        return String.format("emailAddress: " + getEmail() + ", password: " + getPassword() + ", firstName: " + getFirstName() + ", conferenceName: " + getConferenceName());
    }
}
