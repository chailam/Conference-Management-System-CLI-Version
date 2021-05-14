/**
 * Class that represents a User in the system. 
 * 
**/

package cms.entity;


public class User {
    private String emailAddress;
    private String password;
    public String role;

    public User (String role, String emailAddress, String password){
    /**
	 * Constructor for the User.
     * @param emailAddress is the email address of that user to login the account
     * @param password is the hashed password of that user
     **/
        this.emailAddress = emailAddress;
        this.password = password;
        this.role = role;
    }


    public String getEmail(){
    /**
	 * Getter for email address
	 * @return 	the email address of the user. 
     **/
        return this.emailAddress;
    }


    public String getPassword(){
    /**
     * Getter for Password
     * @return 	the Password of the user. 
     **/
        return this.password;
    }


    public String getRole(){
    /**
     * Getter method to get the user roles 
     */
        return this.role;
    }
    

    //TODO: delete it when submit assignment, this is for developer debug purpose!!!
    @Override
    public String toString(){
        return String.format("emailAddress: " + getEmail()+ ", password: " + getPassword());
    }
}
