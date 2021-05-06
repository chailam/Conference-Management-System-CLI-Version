/**
 * The controller class to handle the boundary business logic, which is the user input.
 */
package cms;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainController extends Controller{
    // The user interface invoked
    private UserInterface ui = new UserInterface();

    private ArrayList<String> avaiableTopics = new ArrayList<>(Arrays.asList("Artificial Intelligence", "Human Computer Interaction", "Data Mining & Information Retrieval", "Image Processing", "Big Data", "Computer Networks", "Software Engineering ", "Security & Cryptography", "Robotics & Automation", "Database & Information Systems"));
    private ArrayList<String> availableProgressStatus = new ArrayList<>(Arrays.asList("Review Due", "Being Reviewed", "Reviewed", "Rejected", "Accepted"));

    // Main page, Home Page, Admin, Chair, Author, Reviewer option
    // The option shown here are just the functionalities assigned by tutor.
    // The option for not assigned functionality are not shown here
    ArrayList<String> mainPageOp  = new ArrayList<>(Arrays.asList("Register", "Login"));
    ArrayList<String> homePageOp   = new ArrayList<>(Arrays.asList("Manage Your Conference","Join Conference","Create Conference","Logout"));
    ArrayList<String> adminOp   = new ArrayList<>(Arrays.asList("Retrieve User Information", "Retrieve Conference Information","Logout"));
    ArrayList<String> authorOp   = new ArrayList<>(Arrays.asList("Submit Paper", "Back"));
    ArrayList<String> chairOp   = new ArrayList<>(Arrays.asList("Final Decision on Paper", "Assign Reviewer to Paper","Back"));
    ArrayList<String> reviewerOp   = new ArrayList<>(Arrays.asList("Submit Evaluation of Paper","Back"));


    public MainController(){
        /**
         * The constructor of the controller.
         * It called function to initialize the ConferenceManagementSystem.
         */

        // /////////TESTTTTTTTTTTTTTTTT///
        // String op = ui.getUserOption(adminOp);


        // System.out.println("option selecteddddddd: "+op);

//        ArrayList<Conference> testC = new ArrayList<>();
//        ArrayList<Paper> testP = new ArrayList<>();
//        ArrayList<User> testU = new ArrayList<>();
//        testC = cms.retrieveConferenceList();
//        testP = cms.retrievePaperList();
//        testU = cms.retrieveUserList();
//
//        System.out.println(testC);
//        System.out.println(testP);
//        System.out.println(testU);
        ///////************TEST END */

    }


    public void run(){
        /**
         * The method to kick start the program.
         */
        try{
            // get user option to register or login
            String op;
            op = ui.getUserOption(mainPageOp,"Guest",true);
            // if register
            if (op.equalsIgnoreCase("Register")){
                this.registration();
            }
            // if login
            else if (op.equalsIgnoreCase("Login")){
                User u = authentication();
                if (u.getRole().equalsIgnoreCase("Admin")){
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
         * @param name of the user
         * @param emailAddress of the user
         */
        // get user option to "manage conference", "join conference", "create conference" or "logout"
        String op = ui.getUserOption(homePageOp, name,true);
        if (op.equalsIgnoreCase("Manage Your Conference")){
            // List all the conference available for that user.
            ArrayList<String> userConf = cms.getUserConference(emailAddress);
            userConf.add("Back");
            // if user has conference
            if (!(userConf.get(0) == "")){
                // get user chosen conference to be managed
                String conf = ui.getUserOption(userConf, name,true);
                // if user choose to go back
                if (conf.equalsIgnoreCase("Back")){
                    // go back to previous page
                    this.homePageChoices(name, emailAddress);
                }
                else{
                    // if user choose the conference
                    String role = cms.getUserConferenceRole(emailAddress, conf);
                    if (role == null){
                        ui.displayMsgWithSleep("The role is null!");
                    }
                    // if user is chair in that conference
                    if (role.equalsIgnoreCase("Chair")){
                        chairChoices(name,emailAddress,conf);
                    }
                    // if user is author in that conference
                    else if (role.equalsIgnoreCase("Author")){
                        authorChoices(name,emailAddress,conf);
                    }
                    // if user is reviewer in that conference
                    else if (role.equalsIgnoreCase("Reviewer")){
                        reviewerChoices(name,emailAddress,conf);
                    }
                }
            }
            else {
                // if conference number is zero
                ui.displayMsgWithSleep("You have no conference to manage.");
                homePageChoices(name, emailAddress);
            }
        }
        // if user choose to create conference
        else if (op.equalsIgnoreCase("Create Conference")){
            this.createConferenceOption(name, emailAddress);
        }
        // if user choose to join conference
        else if (op.equalsIgnoreCase("Join Conference")){
            this.joinConferenceOption(name, emailAddress);
        }
        else if (op.equalsIgnoreCase("Logout")){
            // return to main page
            this.run();
        }
    }


    private void adminChoices(){
        /**
         * The option available for admin and its operation.
         */
        // get the admin option to "Retrieve User Information", "Retrieve Conference Information" or "Logout"
        String op = ui.getUserOption(adminOp, "Admin",true);
        // if admin choose to Retrieve User Information
        if (op.equalsIgnoreCase("Retrieve User Information")){
            // get the userlist and print out the information
            ArrayList<User> userList = cms.retrieveUserList();
            ui.adminDisplayUserInfo(userList);
            // if exit is entered, return to admin page
            if (ui.getExitCommand() == true){
                this.adminChoices();
            }
        }
        // if admin choose to Retrieve Conference Information
        else if (op.equalsIgnoreCase("Retrieve Conference Information")){
            //get the conferenceList and print out the information
            ArrayList<Conference> confList = cms.retrieveConferenceList();
            ui.adminDisplayConfInfo(confList);
            // if exit is entered, return to admin page
            if (ui.getExitCommand() == true){
                this.adminChoices();
            }
        }
        // if admin choose to logout
        else if (op.equalsIgnoreCase("Logout")){
            // return to main page
            this.run();
        }
    }


    private void authorChoices(String name, String emailAddress, String confName) throws InterruptedException{
        /**
         * The option available for author and its operation.
         * @param name of the user
         * @param email address of user
         * @param the conference name
         */
        // get the user option to "submit paper" or "go back"
        String op = ui.getUserOption(authorOp, name,true);
        // if user choose to go back
        if (op.equalsIgnoreCase("Back")){
            homePageChoices(name, emailAddress);
        }
        // if user choose to submit paper
        else if (op.equalsIgnoreCase("Submit Paper")){
            // show to choose topic areas
            ArrayList<String> topicName = topicAreasProcess(name, emailAddress);
            ui.topicAreasConfirmation(ut.arrayListToString(topicName,","));
            // get the user option to confirm topic area or go back
            ArrayList<String> confirmOption   = new ArrayList<>(Arrays.asList("Confirm","Back"));
            String op2 = ui.getUserOption(confirmOption, "", false);
            // if user choose to confirm
            if (op2.equalsIgnoreCase("Confirm")){
                String[] info = ui.getPaperSubmission();
                String title = info[0];
                String path = info[1];
                // check the submission date pass the deadline
                // get the conference paper submission due date
                LocalDate submitDueDate = cms.searchConference(confName).getPaperSubmissionDue();
                if (submitDueDate.isBefore(LocalDate.now(ZoneId.of( "Australia/Sydney" )))){
                    ui.displayMsgWithSleep("The paper submission due date has been passed!");
                    this.authorChoices(name,emailAddress,confName);
                }
                // check if duplicated paper
                if (cms.hasPaper(title) == true){
                    ui.displayMsgWithSleep("The Paper Title is duplicated!\n    Please try another title!");
                    this.authorChoices(name,emailAddress,confName);
                }
                // check the path
                if (pathValidator(path) == false){
                    ui.displayMsgWithSleep("The file path is incorrect");
                    this.authorChoices(name,emailAddress,confName);
                }
                // Set the paper, add paper to cms list
                Paper createdp = createPaperEntity(title, emailAddress,null,0,null,confName,topicName,"Being Reviewed");
                cms.addPaper(createdp);
                //write paper to csv file
                String[] paperData = {title, emailAddress, null,"0",null,confName,ut.arrayListToString(topicName,"/"), "Being Reviewed"};
                ut.writeCSV(pathPaperCSV,paperData,true);

                // Update the user information
                User u = cms.searchSpecificUser(emailAddress,"Author",confName);
                if (u != null){
                    Author au = (Author) u;
                    ArrayList<String> papers = new ArrayList<String>();
                    // if paper is empty
                    if (au.retrievePaper().get(0) == ""){
                        papers.add(createdp.getTitle());
                        au.setPaper(papers);
                    }
                    // if author submitted another paper already
                    else{
                        au.addPaper(createdp.getTitle());
                    }
                    papers = au.retrievePaper();
                    // use opencsv to modify csv file for author to include this paper
                    ut.updateCSVPaper(pathNormalUserCSV, ut.arrayListToString(papers,"/"), emailAddress, "author", confName);
                }
                else{
                    System.out.println("Why can't find that user?!!!");
                }
                // display message
                ui.displayMsgWithSleep("Congratulations!\n  You have submitted your paper for "+ confName + " as Reviewer.\n    You can view the review of your paper when result released.");
                // go back to previous page
                this.homePageChoices(name, emailAddress);
            }
            // if user choose to go back
            else if (op2.equalsIgnoreCase("Back")){
                this.authorChoices(name,emailAddress,confName);
            }
        }
    }


    private void chairChoices(String name, String emailAddress, String confName) throws InterruptedException{
        /**
         * The option available for chair and its operation.
         * @param name of the user
         * @param email address of user
         * @param the conference name
         */
        // get user decision to "Final Decision on Paper", "Assign Reviewer to Paper" or "Back"
        String op = ui.getUserOption(chairOp, name,true);
        // if user choose to go back
        if (op.equalsIgnoreCase("Back")){
            homePageChoices(name, emailAddress);
        }
        // if user choose to Final Decision on Paper
        else if (op.equalsIgnoreCase("Final Decision on Paper")){
            //TODO: implement here the final decision on paper, either approve or reject
            // write to csv file
        }
        // if user choose to Assign Reviewer to Paper
        else if (op.equalsIgnoreCase("Assign Reviewer to Paper")){
            //TODO: implement here the manually assign reviewer
            // write to csv file

        }
    }


    private void reviewerChoices(String name,String emailAddress,String confName) throws InterruptedException{
        /**
         * The option available for reviewer and its operation.
         * @param name of the user
         * @param email address of user
         * @param the conference name
         */
        // get user decision to "Submit Evaluation of Paper" or "Back"
        String op = ui.getUserOption(reviewerOp, name,true);
        // if user choose to go back
        if (op.equalsIgnoreCase("Back")){
            homePageChoices(name, emailAddress);
        }
        // if user choose to Submit Evaluation of Paper
        else if (op.equalsIgnoreCase("Submit Evaluation of Paper")){
            //TODO: implement here the submit evaluation
            // write to csv file

        }

    }


    private void joinConferenceOption (String name, String emailAddress) throws InterruptedException {
        /**
         * To create the conference
         * @param the name of the creator
         * @param the email address of the creator
         */
        // List out the conference happen before due date
        ArrayList<Conference> conf = cms.retrieveConferenceList();
        ArrayList<String> availableConf = new ArrayList<String>();
        for (Conference c : conf) {
            // if the date of conference is not past date
            if (c.getDate().isAfter(LocalDate.now(ZoneId.of("Australia/Sydney")))) {
                availableConf.add(c.getName());
            }
        }
        availableConf.add("Back");
        // get user option to join which conference or back
        String preferConf = ui.getUserOption(availableConf, name, true);
        // if user choose to go back
        if (preferConf.equalsIgnoreCase("Back")) {
            // go back to previous page
            this.homePageChoices(name, emailAddress);
        } else {
            // if the conference is selected
            Conference c = cms.searchConference(preferConf);
            // show the conference information
            ui.createConfConfirmation(c.getName(), c.getPlace(), ut.dateToString(c.getDate()), ut.dateToString(c.getPaperSubmissionDue()), ut.dateToString(c.getPaperReviewDue()), ut.arrayListToString(c.retrieveChosenTopicAreas(), ","));
            // get user option to join or exit
            ArrayList<String> confirmOption = new ArrayList<>(Arrays.asList("Join", "Exit"));
            String op = ui.getUserOption(confirmOption, "", false);
            // if user choose to join
            if (op.equalsIgnoreCase("Join")) {
                // get user option to join as reviewer to author
                ArrayList<String> confirmOption2 = new ArrayList<>(Arrays.asList("Join as Reviewer", "Join as Author", "Back"));
                String op2 = ui.getUserOption(confirmOption2, name, true);
                // if user join as reviewer
                if (op2.equalsIgnoreCase("Join as Reviewer")) {
                    // ask for topic areas
                    ArrayList<String> topicName = topicAreasProcess(name, emailAddress);
                    ui.topicAreasConfirmation(ut.arrayListToString(topicName, ","));
                    ArrayList<String> confirmOption3 = new ArrayList<>(Arrays.asList("Confirm", "Back"));
                    String op3 = ui.getUserOption(confirmOption3, "", false);
                    if (op3.equalsIgnoreCase("Confirm")) {
                        boolean flag = setupNormalUserEntity(emailAddress,"reviewer",c.getName(),topicName);
                        if (flag == false){
                            System.out.println("setupNormalUserEntity Error!");
                        }
                        // display message
                        ui.displayMsgWithSleep("Congratulations!\n  You have joined the Conference " + c.getName() + " as Reviewer.\n");
                        // go back to previous page
                        this.homePageChoices(name, emailAddress);
                    } else if (op3.equalsIgnoreCase("Back")) {
                        this.joinConferenceOption(name, emailAddress);
                    }
                }
                // if user join as author
                else if (op2.equalsIgnoreCase("Join as Author")) {
                    boolean flag = setupNormalUserEntity(emailAddress,"author",c.getName(),null);
                    if (flag == false){
                        System.out.println("setupNormalUserEntity Error!");
                    }
                    // display message
                    ui.displayMsgWithSleep("Congratulations!\n  You have joined the Conference " + c.getName() + " as Author.\n");
                    // go back to previous page
                    this.homePageChoices(name, emailAddress);
                // if user choose to go back
                } else if (op2.equalsIgnoreCase("Back")) {
                    // go back to previous page
                    this.joinConferenceOption(name, emailAddress);
                }
            }
            // if user choose to exit
            else if (op.equalsIgnoreCase("Exit")) {
                // return back to main page
                this.homePageChoices(name, emailAddress);
            }
        }
    }



    private void createConferenceOption(String name, String emailAddress) throws InterruptedException {
    /**
     * To create the conference
     * @param the name of the creator
     * @param the email address of the creator
     */
        // Get the conference information retrieved
        String [] confInfo = ui.getCreateConference();
        String confName = confInfo[0];
        String place = confInfo[1];
        // truncate white space and non visible character
        confName = confName.replaceAll("\\s","");
        place = place.replaceAll("\\s","");
        // check if data input is zero
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
            LocalDate date = ut.stringToDate(confInfo[2]);
            LocalDate submitDueDate = ut.stringToDate(confInfo[3]);
            LocalDate reviewDueDate = ut.stringToDate(confInfo[4]);

            //check if it is passed date
            if (date.isBefore(LocalDate.now(ZoneId.of( "Australia/Sydney" ))) ||  submitDueDate.isBefore(LocalDate.now(ZoneId.of( "Australia/Sydney" ))) || reviewDueDate.isBefore(LocalDate.now(ZoneId.of( "Australia/Sydney" )))){
                ui.displayMsgWithSleep("Please enter a valid date after today.");
                // jump back to home page.
                this.createConferenceOption(name, emailAddress);
            }

            // check if existing conference
            if (cms.hasConference(confName) == true) {
                ui.displayMsgWithSleep("The conference already exists. \n   Please try to input other name.");
                // jump back to home page.
                this.homePageChoices(name, emailAddress);
            }
            // if new conference
            ArrayList<String> topicName = topicAreasProcess(name, emailAddress);

            // Conference Confirmation
            ui.createConfConfirmation(confName,place, ut.dateToString(date), ut.dateToString(submitDueDate), ut.dateToString(reviewDueDate), ut.arrayListToString(topicName,","));
            // get user option to "create", "back" or "exit"
            ArrayList<String> confirmOption   = new ArrayList<>(Arrays.asList("Create","Back","Exit"));
            String op = ui.getUserOption(confirmOption, "", false);
            // if user choose to create
            if (op.equalsIgnoreCase("Create")){
                // create conference entity
                Conference c = createConferenceEntity(confName, place, topicName, date, submitDueDate, reviewDueDate);
                // add conference entity to conferenceList
                if (c != null){
                    //Write conference entity to csv file
                    String[] confData = {confName, place, ut.arrayListToString(topicName,"/"), ut.dateToString(date), ut.dateToString(submitDueDate), ut.dateToString(reviewDueDate)};
                    ut.writeCSV(pathConferenceCSV,confData,true);
                    ui.displayMsgWithSleep("Congratulations!\n  You have created the Conference.\n");
                    cms.addConference(c);
                    // Set the conference of the user to this conference
                    User u = cms.searchUser(emailAddress);
                    NormalUser nu = (NormalUser)u;
                    User createdu = createUserEntity("Chair",u.getEmail(),u.getPassword(),nu.getFirstName(),nu.getLastName(),nu.getHighestQualification(),nu.getOccupation(),nu.getEmployerDetail(),nu.getMobileNumber(),confName,null,null);
                    cms.addUser(createdu);
                    String[] userData = {"Chair",u.getEmail(),u.getPassword(),nu.getFirstName(),nu.getLastName(),nu.getHighestQualification(),nu.getOccupation(),nu.getEmployerDetail(),nu.getMobileNumber(),confName,null,null};
                    ut.writeCSV(pathNormalUserCSV,userData,true);
                }
                else{
                    ui.displayMsgWithSleep("Conference is null?!!!");
                }
                // jump to home page
                this.homePageChoices(name, emailAddress);
            }
            // if user choose to go back
            else if (op.equalsIgnoreCase("Back")){
                // return to register page
                this.createConferenceOption(name, emailAddress);
            }
            // if user choose to exit
            else if(op.equalsIgnoreCase("Exit")){
                // return back to main page
                this.homePageChoices(name, emailAddress);
            }
        }
        catch (Exception e) {
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
        ArrayList<Integer> topicInt = ut.convertUserIdxToSysIdx(topicInd);
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

            if (cms.hasUser(emailAddress)){
                String hashedPassword = ut.hashSHA256(password);
                if (hashedPassword.equalsIgnoreCase(u.getPassword())){
                    // set choice to be true as user is authenticated
                    authenticated = true;
                }
                // password incorrect
                else{
                    ui.displayMsgWithSleep("Error: User is not valid. \n\tPlease login again or enter contact admin for any questions.");
                }
            }
            // user does not exist
            else{
                ui.displayMsgWithSleep("Error: User is not valid. \n\tPlease login again or enter contact admin for any questions.");
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
            ui.displayMsgWithSleep("Password must at least 8 characters long, \n    must include at least 1 upper case, \n  1 lower case, 1 number.\n   Please try again.");
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
        // check validity of email
        if (this.emailValidator(emailAddress) == false){
            ui.displayMsgWithSleep("The email address format is wrong");
            this.run();
        }
        // check if data input is zero/null
        if ((firstName.length()==0) || (lastName.length()==0)||(emailAddress.length()==0)||(highestQualification.length()==0)||(occupation.length()==0)||(employerDetail.length()==0)||(mobileNumber.length()==0)){
            ui.displayMsgWithSleep("Information could not be empty.!");
            this.registration();
        }
        // if user exists
        if (cms.hasUser(emailAddress) == true){
            ui.displayMsgWithSleep("The user already exists!\n  Return back to Main Page...");
            this.run();
        }
        // if new user
        else {
            // confirm registration
            ui.registerConfirmation(firstName, lastName, emailAddress, highestQualification, occupation, employerDetail, mobileNumber);
            // get user option to "Register", "Back" or "Exit"
            ArrayList<String> confirmOption   = new ArrayList<>(Arrays.asList("Register","Back","Exit"));
            String op = ui.getUserOption(confirmOption, "", false);
            //if user choose to "register"
            if (op.equalsIgnoreCase("Register")){
                //create user entity
                User u = createUserEntity("normal", emailAddress, hashedPassword, firstName, lastName, highestQualification, occupation, employerDetail, mobileNumber, null, null, null);
                // add new user to userList
                cms.addUser(u);
                //add new user to csv file
                String[] regisData = {"Normal", emailAddress, hashedPassword, firstName, lastName, highestQualification, occupation, employerDetail, mobileNumber, null, null, null};
                ut.writeCSV(pathNormalUserCSV, regisData,true);
                // redirect to main page
                ui.displayMsgWithSleep("You have successfully registered!\n Enjoy your conferences!\n   Please login again!");
                this.run();
            }
            //if user choose to "Back"
            else if (op.equalsIgnoreCase("Back")){
                // return to register page
                this.registration();
            }
            //if user choose to "Exit"
            else if(op.equalsIgnoreCase("Exit")){
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
         * reference: https://www.geeksforgeeks.org/how-to-validate-a-password-using-regular-expressions-in-java/
         */
        boolean checker = true;
        // regex inorder for uppercase at least one, lower case at least one, number at least one, no white space and more than 8 characters
        String regexCheck = "(?=.*[A-Z])" + "(?=.*[a-z])" +  "(?=.*[0-9])"+ "(?=\\S+$)" + ".{8,}" + "$";
        Pattern passPattern = Pattern.compile(regexCheck);
        Matcher matcher = passPattern.matcher(password);
        return matcher.matches();
    }


    private boolean emailValidator(String emailAddress){
        /**
         * To check whether fulfill the requirement of email,
         * @param the email to be checked
         * @return true if valid, false otherwise
         * reference: https://howtodoinjava.com/java/regex/java-regex-validate-email-address/
         */
        boolean checker = true;
        // regex simple email checking
        String regexCheck = "^(.+)@(.+)$";
        Pattern passPattern = Pattern.compile(regexCheck);
        Matcher matcher = passPattern.matcher(emailAddress);
        return matcher.matches();
    }


    private boolean pathValidator(String path){
        /**
         * To check whether fulfill the requirement of path,
         * @param the path to be checked
         * @return true if valid, false otherwise
         * reference: reference: https://www.codeproject.com/Tips/216238/Regular-Expression-to-Validate-File-Path-and-Exten
         */
        boolean checker = true;
        // regex simple email checking
        String regexCheck = "^(?:[\\w]\\:|\\\\)(\\\\[a-zA-Z_\\-\\s0-9\\.]+)+\\.(pdf|doc|docx)$";
        Pattern pathPattern = Pattern.compile(regexCheck);
        Matcher matcher = pathPattern.matcher(path);
        return matcher.matches();
    }
}

    