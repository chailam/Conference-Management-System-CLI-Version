/**
 * The controller class to handle the boundary business logic, which is the user input.
 */
package cms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.security.NoSuchAlgorithmException;



public class BoundaryController extends Controller{
    // The user interface invoked
    private UserInterface ui = new UserInterface();

    private ArrayList<String> avaiableTopics = new ArrayList<>(Arrays.asList("Artificial Intelligence", "Human Computer Interaction", "Data Mining & Information Retrieval", "Image Processing", "Big Data", "Computer Networks", "Software Engineering ", "Security & Cryptography", "Robotics & Automation", "Database & Information Systems"));
    private ArrayList<String> availableProgressStatus = new ArrayList<>(Arrays.asList("Review Due", "Being Reviewed", "Reviewed", "Rejected", "Accepted"));
    
    // Main page, Home Page, Admin, Chair, Author, Reviewer option
    // The option shown here are just the functionalities assigned by tutor.
    // The option for not assigned functionality are not shown here
    ArrayList<String> mainPageOp  = new ArrayList<>(Arrays.asList("Register", "Login"));
    ArrayList<String> homePageOp   = new ArrayList<>(Arrays.asList("Manage Your Conference","Create Conference","Logout"));
    ArrayList<String> adminOp   = new ArrayList<>(Arrays.asList("Retrieve User Information", "Retrieve Conference Information","Logout"));
    ArrayList<String> authorOp   = new ArrayList<>(Arrays.asList("Submit Paper", "Back"));
    ArrayList<String> chairOp   = new ArrayList<>(Arrays.asList("Final Decision on Paper", "Assign Reviewer to Paper","Back"));
    ArrayList<String> reviewerOp   = new ArrayList<>(Arrays.asList("Submit Evaluation of Paper","Back"));


    public BoundaryController(){
        /**
         * The constructor of the controller.
         * It called function to initialize the ConferenceManagementSystem.
         */

        // /////////TESTTTTTTTTTTTTTTTT///
        // String op = ui.getUserOption(adminOp);

    
        // System.out.println("option selecteddddddd: "+op);

        // ArrayList<Conference> testC = new ArrayList<>();
        // ArrayList<Paper> testP = new ArrayList<>();
        // ArrayList<User> testU = new ArrayList<>();
        // testC = cms.retrieveConferenceList();
        // testP = cms.retrievePaperList();
        // testU = cms.retrieveUserList();

        // System.out.println(testC);
        // System.out.println(testP);
        // System.out.println(testU);
        ///////************TEST END */
        
    }


    public void run(){
    /**
     * The method to kick start the program. 
     */
        try{
            // string to represent the user input
            String op;
            op = ui.getUserOption(mainPageOp,"Guest",true);

            if (op.equals("Register")){
                this.registration();
            }
            else if (op.equals("Login")){
                User u = authentication();
                if (u.getRole().equals("Admin")){
                    adminChoices();
                }
                else{
                    homePageChoices(((NormalUser)u).getFirstName(),u.getEmail());
                }
            }
        }
        catch (NoSuchAlgorithmException | InterruptedException e) { 
            System.out.println("Exception: " + e); 
        }
    }


    private void homePageChoices(String name, String emailAddress) throws InterruptedException{
    /**
     * The option available for user and its operation.
     */
        String op = ui.getUserOption(homePageOp, name,true);
        if (op.equals("Manage Your Conference")){
        // List all the conference available for that user.
             ArrayList<String> userConf = cms.getUserConference(emailAddress);
             userConf.add("Back");
             // if user has conference
             if (userConf.size() > 1 && userConf.get(0) != null){ 
                String conf = ui.getUserOption(userConf, name,true);
                if (conf.equals("Back")){
                    // go back to previous page
                    this.homePageChoices(name, emailAddress);
                }
                else{
                    String role = cms.getUserConferenceRole(emailAddress, conf);
                    if (role.equals("Chair")){
                        chairChoices(name,emailAddress);
                    }
                    else if (role.equals("Author")){
                        authorChoices(name,emailAddress);
                    }
                    else if (role.equals("Reviewer")){
                        reviewerChoices(name,emailAddress);
                    }
                }
             }
             else {
                 // if conference number is zero
                ui.displayMsgWithSleep("You have no conference to manage.");
                homePageChoices(name, emailAddress);
             }
        }
        else if (op.equals("Create Conference")){
        // TODO: Implement create conference functionality here
        // ask user enter option, validate option, use create conference entity function on Controller to create conference object
        // add conference object to listConference, then add conference object to csv file
            String [] confInfo = ui.getCreateConference();

        

        }
        else if (op.equals("Logout")){
            // return to main page
            this.run();
        }
    }



    private void chairChoices(String name, String emailAddress) throws InterruptedException{
    /**
     * The option available for chair and its operation.
     */
        String op = ui.getUserOption(chairOp, name,true);
        if (op.equals("Back")){
            homePageChoices(name, emailAddress);
        }
        else if (op.equals("Final Decision on Paper")){
            //TODO: implement here the final decision on paper, either approve or reject
            // write to csv file
        }
        else if (op.equals("Assign Reviewer to Paper")){
            //TODO: implement here the manually assign reviewer
            // write to csv file

        }


    }

    private void createConferenceOption(String name, String emailAddress) throws InterruptedException {
        /**
         * To create the conference
         */
        // Get the information retrieved
        String [] confInfo = ui.getCreateConference();
        String confName = confInfo[0];
        String place = confInfo[1];
        try {
            // check date validity
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(confInfo[2]);
            Date submitDueDate = new SimpleDateFormat("dd/mm/yyyy").parse(confInfo[3]);
            Date reviewDueDate = new SimpleDateFormat("dd/mm/yyyy").parse(confInfo[4]);

            // check if existing conference
            if (cms.hasConference(confName) == true) {
                ui.displayHeader();
                ui.displayMsgWithSleep("The conference already exists. \nPlease try to inout other name.");
                // jump back to home page.
                homePageChoices(name, emailAddress);
            }
            // if new conference
            ArrayList<String> topicName = topicAreasProcess(name, emailAddress);

            // Conference Comfirmation
            ui.confirmConferenceCreation(confName,place, ut.dateToString(date), ut.dateToString(submitDueDate), ut.dateToString(reviewDueDate), ut.arrayListToString(topicName,","));
            ArrayList<String> confirmOption   = new ArrayList<>(Arrays.asList("Create","Back","Exit"));
            String op = ui.getUserOption(confirmOption, "", false);
            ui.displayFooter();
            if (op.equals("Create")){
                // create conference entity
                Conference c = createConferenceEntity(name, place, topicName, date, submitDueDate, reviewDueDate);
                // add conference entity to conferenceList
                if (c != null){
                    ui.displayMsgWithSleep("Congratulations!\nYou have created the Conference.\n");
                    cms.addConference(c);
                }
                else{
                    ui.displayMsgWithSleep("Conference is null?!!!");
                }
                //Write conference entity to csv file
                String[] confData = {name, place, ut.arrayListToString(topicName,"/"), ut.dateToString(date), ut.dateToString(submitDueDate), ut.dateToString(reviewDueDate)};
                ut.writeToCSV(pathConferenceCSV,confData,true);
                // jump to home page
                this.homePageChoices(name, emailAddress);
            }
            else if (op.equals("Back")){
                // return to register page
                this.createConferenceOption(name, emailAddress);
            }
            else if(op.equals("Exit")){
                // return back to main page
                this.homePageChoices(name, emailAddress);
            }
        }
        catch (ParseException e) {
            System.out.println(e);
            ui.displayMsgWithSleep("Please enter a valid date.");
            // jump back to create conference screen.
            this.createConferenceOption(name, emailAddress);
        }
    }


    private ArrayList<String> topicAreasProcess(String name, String emailAddress) throws InterruptedException {
        /**
         * Method to get the topic areas, check the validity, get the topic name from index and
         * concatenate additional topic and index topic
         * @return the topic area list
         */
        // Get topic option
        String[] topicTmp = ui.getTopicAreas(avaiableTopics);
        // delimit the information
        ArrayList<String> topicInd = ut.stringToArrayList(topicTmp[0], ",");
        ArrayList<String> topicName = ut.stringToArrayList(topicTmp[1], ",");
        // check if the topic areas invalid
        Boolean result = ut.indexCheck(topicInd,avaiableTopics.size());
        // if topic incorrect, try again
        if (result == false) {
            ui.displayMsgWithSleep("Please enter a valid topic number.");
            this.createConferenceOption(name, emailAddress);
        }
        //use index of topic to retrieve index
        ArrayList<String> topicName2 = ut.indexToElement(topicInd,avaiableTopics);
        if (topicName.size() > 0) {
            topicName2.addAll(topicName);
        }
        return topicName2;
    }

    private void authorChoices(String name, String emailAddress) throws InterruptedException{
    /**
     * The option available for author and its operation.
     */
        String op = ui.getUserOption(authorOp, name,true);
        if (op.equals("Back")){
            homePageChoices(name, emailAddress);
        }
        else if (op.equals("Submit Paper")){
            //TODO: implement here the submit paper
            //create paper object using method in Controller.java
            // write to csv file

        }
    }


    private void reviewerChoices(String name,String emailAddress) throws InterruptedException{
    /**
     * The option available for reviewer and its operation.
     */
        String op = ui.getUserOption(reviewerOp, name,true);
        if (op.equals("Back")){
            homePageChoices(name, emailAddress);
        }
        else if (op.equals("Submit Evaluation of Paper")){
            //TODO: implement here the submit evaluation
            // write to csv file

        }

    }


    private void adminChoices(){
    /**
     * The option available for admin and its operation.
     */
        String op = ui.getUserOption(adminOp, "Admin",true);
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
            // return to main page
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
                    ui.displayMsgWithSleep("Error: User is not valid. \nPlease login again or enter contact admin for any questions.");
                }
            }
            // user does not exist
            else{
                ui.displayMsgWithSleep("Error: User is not valid. \nPlease login again or enter contact admin for any questions.");
            }
        }
        return u;
    }


    private void registration() throws InterruptedException, NoSuchAlgorithmException{
    /**
     *  The method to register user.
     */
        String [] info = ui.getRegistration();
        String firstName = info[0];
        String lastName = info[1];
        String emailAddress = info[2]; 
        String hashedPassword = ut.hashSHA256(info[3]);
        String highestQualification = info[4];
        String occupation = info[5];
        String employerDetail = info[6];
        String mobileNumber = info[7];
        // if user exists
        if (cms.hasUser(emailAddress) == true){
            ui.displayMsgWithSleep("The user already exists!\nReturn back to Main Page...");
            this.run();
        }
        // if new user
        else {
            // confirm registration
            ui.confirmRegistration(firstName, lastName, emailAddress, highestQualification, occupation, employerDetail, mobileNumber);
            ArrayList<String> confirmOption   = new ArrayList<>(Arrays.asList("Register","Back","Exit"));
            String op = ui.getUserOption(confirmOption, "", false);
            ui.displayFooter();
            if (op.equals("Register")){
                 //creat user entity
                User u = createUserEntity("normal", emailAddress, hashedPassword, firstName, lastName, highestQualification, occupation, employerDetail, mobileNumber, "null", null, null);
                // add new user to userList
                cms.addUser(u);
                //add new user to csv file
                String[] regisData = {"Normal", emailAddress, hashedPassword, firstName, lastName, highestQualification, occupation, employerDetail, mobileNumber, "null", "null", "null"};
                ut.writeToCSV(pathNormalUserCSV, regisData,true);
                // redirect to main page
                ui.displayMsgWithSleep("You have succcessfully registered!\nEnjoy your conferences!\nPlease login again!");
                this.run();
            }
            else if (op.equals("Back")){
                // return to register page
                this.registration();
            }
            else if(op.equals("Exit")){
                // return back to main page
                this.run();
            }
        }
    }
}
    