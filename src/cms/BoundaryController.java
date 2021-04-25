/**
 * The controller class to handle the boundary business logic, which is the user input.
 */
package cms;

import cms.*;
import java.util.*;
import java.security.NoSuchAlgorithmException;



public class BoundaryController extends Controller{
    // The user interface invoked
    private UserInterface ui = new UserInterface();

    private List<String> avaiableTopics = new ArrayList<>(Arrays.asList("Artificial Intelligence", "Human Computer Interaction", "Data Mining & Information Retrieval", "Image Processing", "Big Data", "Computer Networks", "Software Engineering ", "Security & Cryptography", "Robotics & Automation", "Database & Information Systems"));
    private List<String> availableProgressStatus = new ArrayList<>(Arrays.asList("Review Due", "Being Reviewed", "Reviewed", "Rejected", "Accepted"));
    
    // Main page, Home Page, Admin, Chair, Author, Reviewer option
    // The option shown here are just the functionalities assigned by tutor.
    // The option for not assigned functionality are not shown here
    String[] mainPageOp = {"Register", "Login"};
    String[] homePageOp = {"Manage Your Conference","Create Conference"};
    String[] adminOp = {"Retrieve User Information", "Retrieve Conference Information","Logout"};
    String[] authorOp = {"Submit Paper", "Previous"};
    String[] chairOp = {"Final Decision on Paper", "Assign Reviewer to Paper","Previous"};
    String[] reviewerOp = {"Submit Evaluation of Paper","Previous"};


    public BoundaryController(){
        /**
         * The constructor of the controller.
         * It called function to initialize the ConferenceManagementSystem.
         */

        // /////////TESTTTTTTTTTTTTTTTT///
        // String op = ui.getUserOption(adminOp);

    
        // System.out.println("option selecteddddddd: "+op);

        ArrayList<Conference> testC = new ArrayList<>();
        ArrayList<Paper> testP = new ArrayList<>();
        ArrayList<User> testU = new ArrayList<>();
        testC = cms.retrieveConferenceList();
        testP = cms.retrievePaperList();
        testU = cms.retrieveUserList();

        System.out.println(testC);
        System.out.println(testP);
        System.out.println(testU);
        ///////************TEST END */
        
    }

    //***********************************Kick Start Method********************************************************** */
    public void run(){
    /**
     * The method to kick start the program. 
     */
        try{
            // string to represent the user input
            String op;
            op = ui.getUserOption(mainPageOp,"Guest");

            if (op.equals("Register")){
                // create user u (check if duplicated!)
                // add to list
                // write to csv


            }
            else if (op.equals("Login")){
                User u = authentication();
                if (u.getRole().equals("Admin")){
                    adminChoices();
                }
                else if (u.getRole().equals("Chair")){
                    // TODO: chairChoices function will be here!!
                }
                else if (u.getRole().equals("Author")){
                    // TODO: authorChoices function will be here!!
                }
                else if (u.getRole().equals("Reviewer")){
                    // TODO: reviewerChoices function will be here!!
                }
                



            }
        }
        catch (NoSuchAlgorithmException | InterruptedException e) { 
            System.out.println("Exception: " + e); 
        }
    }


    private void adminChoices(){
        String op = ui.getUserOption(adminOp, "Admin");
        if (op.equals("Retrieve User Information")){
            //TODO: get the userlist and use UserInterface displayResult method to print out the information
            // remember to print the header and footer as shown in other method


            // if exit is entered, return to admin page
            if (ui.getExitCommand() == true){
                this.adminChoices();
            }

        }
        else if (op.equals("Retrieve Conference Information")){
            //TODO: get the userlist and use UserInterface displayResult method to print out the information
            // remember to print the header and footer as shown in other method


            // if exit is entered, return to admin page
            if (ui.getExitCommand() == true){
                this.adminChoices();
            }
        }
        else if (op.equals("Logout")){
            this.run();
        }
    }


    private User authentication() throws NoSuchAlgorithmException, InterruptedException{
    /**
     * The method to authenticate user.
     * It gets the user input boundary and check the email address and password
     * @return the user who authenticated
     */
        // a variable to check if user is authenticated
        boolean authenticated = false;
        User u = null;
        while (authenticated == false){
            // Retrieve the input from boundary
            String[] inputAuthenticate = ui.getAuthentication();
            String emailAddress = inputAuthenticate[0];
            String password = inputAuthenticate[1];

            // Get the user with that email address
            u = cms.searchUser(emailAddress);

            // if the user exists, validate password
            if (u != null){
                String hashedPassword = ut.hashSHA256(password);
                if (hashedPassword.equalsIgnoreCase(u.getPassword())){
                // set choice to be true as user is authenticated
                authenticated = true;
                }
                // password incorrect
                else{
                    ui.displayAuthenErrMsg();
                }
            }
            // user does not exist
            else{
                ui.displayAuthenErrMsg();
            }
        }
        return u;
    }


    private void registration(){
    /**
     *  The method to register user.
     */
        
    }
}
    