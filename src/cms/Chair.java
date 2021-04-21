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

    // The available option for Chair
    public ArrayList <String> availableOption = new ArrayList<String>(){
        {
            add("Modify Deadline for Paper Submission");
            add("Modify Deadline for Paper Review");
            add("Final Decision on Paper");
            add("Conference Information");
            add("Assign Reviewer to Paper");
        }
    };

    public Chair (String emailAddress, String password, String firstName, String lastName, String highestQualification, String occupation, String employerDetail, int mobileNumber){
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
        **/
        super(emailAddress, password, firstName, lastName, highestQualification, occupation, employerDetail, mobileNumber);
        }

    public Chair(String emailAddress, String password) {
        /**
         * Constructor for the Chair class.
         * 
         * @param emailAddress is the email address of that user to login the account
         * @param password     is the hashed password of that user
         **/
        super(emailAddress, password);
    }


    public Conference createConference (String name, String place, ArrayList<String> topic, Date date, Date submissionDue, Date reviewDue){
        /**
         * Chair's method to create a conference in the system.
         * @param name is the name of the conference
         * @param place is the place of the conference held
         * @param topic is the topic area of the conference
         * @param date is the date held of conference
         * @param submissionDue is the paper submission due date of the conference
         * @param reviewDue is the review due date of the conference
         * @return c is the conference created
         */
        Conference c = new Conference (name, place, topic, date, submissionDue, reviewDue);
        return c;
    }

    public void modifySubmissionDueDate (Date date, Conference conference){
        /**
         *  TODO : see whether need to implement this as it is not in the requirements!!!!
         */
    }

    public void modifyReviewDueDate (Date date, Conference conference){
        /**
         *  TODO : see whether need to implement this as it is not in the requirements!!!!
         */
    }

    public void decisionPaper (Paper p){
        /**
         *  TODO : implement this features!!!!
         */

    }

    public void assignedREviewer (Paper p){
        /**
         *  TODO : implement this features!!!!
         */

    }


}
