/**
 * Class that represents a User in the system. 
 * 
**/

package cms;


public class User {
    private String emailAddress;
    private String password;

    public User(String emailAddress, String password){
    /**
	 * Constructor for the User.
     * @param emailAddress is the email address of that user to login the account
     * @param password is the hashed password of that user
     **/
        this.emailAddress = emailAddress;
        this.password = password;
    }


    public String getEmail(){
    /**
	 * Getter for email address
	 * @return 	the email address of the user. 
     **/
        return this.emailAddress;
    }
}
