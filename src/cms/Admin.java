/**
 * Class that represents an Admin in the system, it is the child class of User as Admin only required emailaddress and password.
 * 
**/

package cms;


public class Admin extends User{

    public Admin (String role, String emailAddress, String password) {
        /**
         * Constructor for the Admin class.
         * 
         * @param emailAddress is the email address of that user to login the account
         * @param password     is the hashed password of that user
         **/
        super(role, emailAddress, password);
    }
}
