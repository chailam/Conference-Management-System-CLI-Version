/**
 * Class that represents a Chair in the system, it is the child class of NormalUser.
 * 
**/

package cms;

import cms.NormalUser;
import cms.Conference;
import cms.Paper;
import java.util.*;

public class Chair extends NormalUser {

    public Chair (String emailAddress, String password, String firstName, String lastName, String highestQualification, String occupation, String employerDetail, String mobileNumber, String conferenceName){
        /**
         * Constructor for the Chair class. 
         * @param emailAddress is the email address of that user to login the account
         * @param password is the hashed password of that user
         * @param firstName is the first name of that user
         * @param lastName is the last name of that user
         * @param highestQualification is the highest qualification of that user
         * @param occupation is the occupation of that user
         * @param employerDetail is the employer detail provided
         * @param mobileNumber is the mobile number of that user
         * @param the conference of the chair attended
        **/
        super(emailAddress, password, firstName, lastName, highestQualification, occupation, employerDetail, mobileNumber,conferenceName);
        }
}
