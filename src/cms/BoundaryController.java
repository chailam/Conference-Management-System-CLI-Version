/**
 * The controller class to handle the boundary business logic, which is the user input.
 */
package cms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
            System.out.println(userConf);
            // if user has conference
            if (userConf.size() >= 2 ){
                String conf = ui.getUserOption(userConf, name,true);
                if (conf.equals("Back")){
                    // go back to previous page
                    this.homePageChoices(name, emailAddress);
                }
                else{
                    String role = cms.getUserConferenceRole(emailAddress, conf);
                    if (role == null){
                        ui.displayMsgWithSleep("The role is null!");
                    }
                    if (role.equalsIgnoreCase("Chair")){
                        chairChoices(name,emailAddress);
                    }
                    else if (role.equalsIgnoreCase("Author")){
                        authorChoices(name,emailAddress);
                    }
                    else if (role.equalsIgnoreCase("Reviewer")){
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
            this.createConferenceOption(name, emailAddress);



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
        // truncate white space and non visible character
        confName = confName.replaceAll("\\s","");
        place = place.replaceAll("\\s","");
        // // check if data input is zero
        if ((confName.length() == 0) || (place.length() == 0)){
            ui.displayMsgWithSleep("Information could not be empty.");
            // jump back to home page.
            this.createConferenceOption(name, emailAddress);
        }
        try {
            // check date validity
            if ((confInfo[2].length() != 10) || (confInfo[3].length() != 10) || (confInfo[4].length() != 10)) {
                ui.displayMsgWithSleep("Please enter a valid date.");
                // jump back to home page.
                this.createConferenceOption(name, emailAddress);
            }
            Date date = new SimpleDateFormat("dd/mm/yyyy").parse(confInfo[2]);
            Date submitDueDate = new SimpleDateFormat("dd/mm/yyyy").parse(confInfo[3]);
            Date reviewDueDate = new SimpleDateFormat("dd/mm/yyyy").parse(confInfo[4]);

            // check if existing conference
            if (cms.hasConference(confName) == true) {
                ui.displayMsgWithSleep("The conference already exists. \nPlease try to input other name.");
                // jump back to home page.
                this.homePageChoices(name, emailAddress);
            }
            // if new conference
            ArrayList<String> topicName = topicAreasProcess(name, emailAddress);

            // Conference Confirmation
            ui.confirmConferenceCreation(confName,place, ut.dateToString(date), ut.dateToString(submitDueDate), ut.dateToString(reviewDueDate), ut.arrayListToString(topicName,","));
            ArrayList<String> confirmOption   = new ArrayList<>(Arrays.asList("Create","Back","Exit"));
            String op = ui.getUserOption(confirmOption, "", false);
            ui.displayFooter();
            if (op.equals("Create")){
                // create conference entity
                Conference c = createConferenceEntity(confName, place, topicName, date, submitDueDate, reviewDueDate);
                // add conference entity to conferenceList
                if (c != null){
                    //Write conference entity to csv file
                    String[] confData = {confName, place, ut.arrayListToString(topicName,"/"), ut.dateToString(date), ut.dateToString(submitDueDate), ut.dateToString(reviewDueDate)};
                    ut.writeToCSV(pathConferenceCSV,confData,true);
                    ui.displayMsgWithSleep("Congratulations!\nYou have created the Conference.\n");
                    cms.addConference(c);
                    // Set the conference of the user to this conference
                    User u = cms.searchUser(emailAddress);
                    NormalUser nu = (NormalUser)u;
                    User createdu = createUserEntity("Chair",u.getEmail(),u.getPassword(),nu.getFirstName(),nu.getLastName(),nu.getHighestQualification(),nu.getOccupation(),nu.getEmployerDetail(),nu.getMobileNumber(),confName,null,null);
                    cms.addUser(createdu);
                    String[] userData = {"Chair",u.getEmail(),u.getPassword(),nu.getFirstName(),nu.getLastName(),nu.getHighestQualification(),nu.getOccupation(),nu.getEmployerDetail(),nu.getMobileNumber(),confName,null,null};
                    ut.writeToCSV(pathNormalUserCSV,userData,true);
                }
                else{
                    ui.displayMsgWithSleep("Conference is null?!!!");
                }
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
        // if is empty
        if (topicInd == null){
            ui.displayMsgWithSleep("Please enter a valid topic number.");
            this.topicAreasProcess(name, emailAddress);
        }
        // convert user input index to programmer index
        ArrayList<Integer> topicInt = ut.convertUserIndToSysInd(topicInd);
        // if is empty
        if (topicInt == null){
            ui.displayMsgWithSleep("Please enter a valid topic number.");
            this.topicAreasProcess(name, emailAddress);
        }
        // check index bound
        Boolean result = ut.indexCheck(topicInt,avaiableTopics.size());
        // if topic incorrect, try again
        if ((topicInt == null) || (result == false)){
            ui.displayMsgWithSleep("Please enter a valid topic number.");
            this.topicAreasProcess(name, emailAddress);
        }
        //use index of topic to retrieve index
        ArrayList<String> topicName2 = ut.indexToElement(topicInt,avaiableTopics);
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
        //checking password requirement
        if (this.passwordValidator(info[3]) == false){
            ui.displayMsgWithSleep("Password must at least 8 characters long, \nmust include at least 1 upper case, \n1 lower case, 1 number.\n Please try again.");
            this.registration();
        }
        String hashedPassword = ut.hashSHA256(info[3]);
        String highestQualification = info[4];
        String occupation = info[5];
        String employerDetail = info[6];
        String mobileNumber = info[7];
        // truncate white space and non visible character
        firstName = firstName.replaceAll("\\s","");
        lastName = lastName.replaceAll("\\s","");
        emailAddress = emailAddress.replaceAll("\\s","");
        highestQualification = highestQualification.replaceAll("\\s","");
        occupation = occupation.replaceAll("\\s","");
        employerDetail = employerDetail.replaceAll("\\s","");
        mobileNumber = mobileNumber.replaceAll("\\s","");
        // check if data input is zero/null
        if ((firstName.length()==0) || (lastName.length()==0)||(emailAddress.length()==0)||(highestQualification.length()==0)||(occupation.length()==0)||(employerDetail.length()==0)||(mobileNumber.length()==0)){
            ui.displayMsgWithSleep("Information could not be empty.!");
            this.registration();
        }
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
                //create user entity
                User u = createUserEntity("normal", emailAddress, hashedPassword, firstName, lastName, highestQualification, occupation, employerDetail, mobileNumber, null, null, null);
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


    private boolean passwordValidator(String password){
        /**
         * To check whether fulfill the requirement of password,
         * which are must at least 8 characters long, must include at least 1 upper case, 1 lower case, 1 number.
         * @param the password to be checked
         * @return true if valid, false otherwise
         */
        boolean checker = true;
        // regex inorder for uppercase at least one, lower case at least one, number at least one, no white space and more than 8 characters
        String regexCheck = "(?=.*[A-Z])" + "(?=.*[a-z])" +  "(?=.*[0-9])"+ "(?=\\S+$)" + ".{8,}" + "$";
        Pattern passPattern = Pattern.compile(regexCheck);
        Matcher matcher = passPattern.matcher(password);
        return matcher.matches();
    }
}

    