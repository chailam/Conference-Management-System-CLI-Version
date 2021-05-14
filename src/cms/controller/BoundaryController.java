/**
 * The controller class to handle the boundary business logic, which is the user input.
 */
package cms.controller;

import cms.Utility;
import cms.entity.ConferenceManagementSystem;
import cms.view.UserInterface;
import cms.entity.*;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;


public class BoundaryController {
    // Main page, Home Page, Admin, Chair, Author, Reviewer option
    // The option shown here are just the functionalities assigned by tutor.
    // The option for not assigned functionality are not shown here
    private static ArrayList<String> mainPageOp  = new ArrayList<>(Arrays.asList("Register", "Login"));
    private static ArrayList<String> homePageOp   = new ArrayList<>(Arrays.asList("Manage Your Conference","Join Conference","Create Conference","Logout"));
    private static ArrayList<String> adminOp   = new ArrayList<>(Arrays.asList("Retrieve User Information", "Retrieve Conference Information","Logout"));
    private static ArrayList<String> authorOp   = new ArrayList<>(Arrays.asList("Submit Paper", "Back"));
    private static ArrayList<String> chairOp   = new ArrayList<>(Arrays.asList("Final Decision on Paper", "Assign Reviewer to Paper","Back"));
    private static ArrayList<String> reviewerOp   = new ArrayList<>(Arrays.asList("Submit Evaluation of Paper","Back"));
    private static ArrayList<String> proceedOp = new ArrayList<>(Arrays.asList("Proceed","Back","Exit"));
    private static ArrayList<String> avaiableTopics = new ArrayList<>(Arrays.asList("Artificial Intelligence", "Human Computer Interaction", "Data Mining & Information Retrieval", "Image Processing", "Big Data", "Computer Networks", "Software Engineering ", "Security & Cryptography", "Robotics & Automation", "Database & Information Systems"));
    Utility ut = new Utility();

    // Define the path to User.csv, Conference.csv, and Paper.csv
    protected static String pathUserCSV = "src/resource/User.csv";
    protected static String pathConferenceCSV = "src/resource/Conference.csv";
    protected static String pathPaperCSV = "src/resource/Paper.csv";

    // Initialize user interface invoked
    private UserInterface ui = new UserInterface();

    // Initialize the ConferenceManagementSystem (as DB)
    protected static ConferenceManagementSystem cms = new ConferenceManagementSystem();

    // Initialize the workers controller
    PaperController pController = new PaperController(cms,ui,pathPaperCSV);
    ConferenceController cController = new ConferenceController(cms,ui,pathConferenceCSV);
    UserController uController = new UserController(cms,ui,pathUserCSV);

    public BoundaryController(){
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
            // get user option to "Register" or "Login"
            String opt = ui.getUserOption(mainPageOp,"Guest",true);
            // if user choose to "Register"
            if (opt.equalsIgnoreCase("Register")){
                this.registration();
            } else if (opt.equalsIgnoreCase("Login")){
                // if user choose to "Login"
                User u = this.authentication();
                if (u != null) {
                    if (u.getRole().equalsIgnoreCase("Admin")) {
                        // if user is admin
                        this.adminChoices();
                    } else {
                        // if user is non-admin
                        homePageChoices(((NormalUser) u).getFirstName(), u.getEmail());
                    }
                } else {
                    System.out.println("Error: User is null in run.");
                }
            }
        } catch (NoSuchAlgorithmException | InterruptedException e) {
            System.out.println("Exception: run - " + e);
        }
    }


    private void homePageChoices(String name, String emailAddress) throws InterruptedException{
        /**
         * The option available for normal user
         * @param name of the user
         * @param emailAddress of the user
         */
        // get user option to "manage conference", "join conference", "create conference" or "logout"
        String op = ui.getUserOption(homePageOp, name,true);
        if (op.equalsIgnoreCase("Manage Your Conference")){
            // if user choose to manage conference
            // List all the conference available for that user.
            ArrayList<String> userConf = cms.getUserConference(emailAddress);
            userConf.add("Back");
            if (!(userConf.get(0) == "")){
                // if user has conference
                // get user chosen conference to be managed
                String conf = ui.getUserOption(userConf, name,true);
                if (conf.equalsIgnoreCase("Back")){
                    // if user choose to go back
                    userConf.clear();
                    this.homePageChoices(name, emailAddress);
                } else {
                    // if user choose the conference
                    // get the role of the user in that conference
                    String role = cms.getUserConferenceRole(emailAddress, conf);
                    if (role == null){
                        System.out.println("Error: role is null in homePageChoices.");
                    }
                    if (role.equalsIgnoreCase("Chair")){
                        // if user is chair in that conference, direct to chair option
                        chairChoices(name,emailAddress,conf);
                    } else if (role.equalsIgnoreCase("Author")){
                        // if user is author in that conference, direct to author option
                        authorChoices(name,emailAddress,conf);
                    } else if (role.equalsIgnoreCase("Reviewer")){
                        // if user is reviewer in that conference, direct to reviewer option
                        reviewerChoices(name,emailAddress,conf);
                    }
                }
            } else {
                // if conference number is zero, show message and return to home page
                ui.displayMsgWithSleep("You have no conference to manage.");
                userConf.clear();
                this.homePageChoices(name, emailAddress);
            }
        } else if (op.equalsIgnoreCase("Create Conference")){
            // if user choose to create conference
            this.createConference(name,emailAddress);
        } else if (op.equalsIgnoreCase("Join Conference")){
            // if user choose to join conference
            this.joinConference(name, emailAddress);
        } else if (op.equalsIgnoreCase("Logout")){
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
        if (op.equalsIgnoreCase("Retrieve User Information")){
            // if admin choose to Retrieve User Information
            // get the userlist and print out the information
            ArrayList<User> userList = cms.retrieveUserList();
            ui.adminDisplayUserInfo(userList);
            // if exit is entered, return to admin page
            if (ui.getExitCommand() == true){
                this.adminChoices();
            }
        } else if (op.equalsIgnoreCase("Retrieve Conference Information")){
            // if admin choose to Retrieve Conference Information
            //get the conferenceList and print out the information
            ArrayList<Conference> confList = cms.retrieveConferenceList();
            ui.adminDisplayConfInfo(confList);
            // if exit is entered, return to admin page
            if (ui.getExitCommand() == true){
                this.adminChoices();
            }
        } else if (op.equalsIgnoreCase("Logout")){
            // if admin choose to logout
            // return to main page
            this.run();
        }
    }


    private void authorChoices(String name, String emailAddress, String confName) throws InterruptedException {
        /**
         * The option available for author and its operation.
         * @param name of the user
         * @param email address of user
         * @param the conference name
         */
        // get the user option to "submit paper" or "go back"
        String op = ui.getUserOption(authorOp, name, true);
        // if user choose to go back
        if (op.equalsIgnoreCase("Back")) {
            homePageChoices(name, emailAddress);
        } else if (op.equalsIgnoreCase("Submit Paper")) {
            // if user choose to submit paper
            boolean flag = false;
            while (flag == false) {
                flag = pController.paperSubmission(name, emailAddress, confName);
            }
            this.authorChoices(name,emailAddress,confName);
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
        } else if (op.equalsIgnoreCase("Final Decision on Paper")){
            // if user choose to Final Decision on Paper
            boolean flag = false;
            while (flag == false) {
                flag = pController.paperFinalDecision(name, emailAddress, confName);
            }
            this.chairChoices(name,emailAddress,confName);
        } else if (op.equalsIgnoreCase("Assign Reviewer to Paper")){
            // if user choose to Assign Reviewer to Paper
            this.assignReviewer(name,emailAddress,confName);
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
        } else if (op.equalsIgnoreCase("Submit Evaluation of Paper")){
            // if user choose to Submit Evaluation of Paper
            boolean flag = false;
            while (flag == false) {
                flag = pController.paperEvaluation(name, emailAddress, confName);
            }
            this.reviewerChoices(name,emailAddress,confName);
        }
    }

    public void submitPaper (String name, String emailAddress, String confName) throws InterruptedException {
        /**
         * To submit the paper for the conference
         * @param name of the submitter
         * @param email address of the submitter
         * @param the conference the paper submit to
         */
        // show to choose topic areas
        ArrayList<String> topicName = topicAreasProcess(name, emailAddress);
        // get confirmation for the topic
        ui.topicAreasConfirmation(ut.arrayListToString(topicName,","));
        // get the user option to proceed with topic area or go back
        String opt = ui.getUserOption(proceedOp, "", false);
        // if user choose to confirm
        if (opt.equalsIgnoreCase("Confirm")){
            // get the info required for paper submission
            String[] info = ui.getPaperSubmission();
            // check the info validity
            boolean flag = pController.checkPaperSubmitInfo(info[0],info[1],confName);
            if (flag == false){
                // if the information is not valid
                this.authorChoices(name,emailAddress,confName);
            }
            // if the information is valid
            // Set the paper, add paper to cms list
            Paper createdp = createPaperEntity(title, emailAddress,null,0,null,confName,topicName,"Being Reviewed");
            cms.addPaper(createdp);
            //write paper to csv file
            String[] paperData = {title, emailAddress, null,"0",null,confName,ut.arrayListToString(topicName,"/"), "Being Reviewed"};
            ut.writeCSV(pathPaperCSV,paperData,true);

            // TODO: may be this is user controller?
            // Update the user information to include this paper
            NormalUser u = cms.searchSpecificUser(emailAddress,"Author",confName);
            if (u != null){
                Author au = (Author) u;
                ArrayList<String> papers = new ArrayList<String>();
                // if paper is empty
                if (au.retrievePaper().get(0) == ""){
                    papers.add(createdp.getTitle());
                    au.setPaper(papers);
                    // if author submitted another paper already
                } else{
                    au.addPaper(createdp.getTitle());
                }
                papers = au.retrievePaper();
                // use opencsv to modify csv file for author to include this paper
                ut.updateUserCsv(pathUserCSV, ut.arrayListToString(papers,"/"),11, emailAddress, "author", confName);
            } else{
                System.out.println("Why can't find that user?!!!");
            }
            // display message
            ui.displayMsgWithSleep("Congratulations!\n  You have submitted your paper for "+ confName + " as Reviewer.\n    You can view the review of your paper when result released.");
            // go back to previous page
            return true;
            // if user choose to go back
        } else if (opt.equalsIgnoreCase("Back")){
            this.submitPaper(name,emailAddress,confName);
        }
        else if (opt.equalsIgnoreCase("Exit")){
            this.homePageChoices(name,emailAddress);
        }
    }


    public void createConference (String name, String emailAddress) throws InterruptedException {
        /**
         * To create conference
         * @param name of the creator
         * @param email address of the creator
         */
        // Get the conference information retrieved
        String [] confInfo = ui.getCreateConference();
        // truncate white space and non visible character
        confInfo = ut.truncateWhiteSpace(confInfo);
        // check the validity of data
        boolean flag = cController.checkCreateConfInfo(confInfo[0],confInfo[1],confInfo[2],confInfo[3],confInfo[4]);
        if (flag == false) {
            // if data is not valid, redirect to home page
            this.homePageChoices(name,emailAddress);
        } else {
            String confName = confInfo[0];
            String place = confInfo[1];
            // if data is valid
            LocalDate date = ut.stringToDate(confInfo[2]);
            LocalDate submitDueDate = ut.stringToDate(confInfo[3]);
            LocalDate reviewDueDate = ut.stringToDate(confInfo[4]);
            // get the topic area
            ArrayList<String> topicName = topicAreasProcess (name, emailAddress);
            // show the conference confirmation message
            ui.createConfConfirmation(confName,place, ut.dateToString(date), ut.dateToString(submitDueDate), ut.dateToString(reviewDueDate), ut.arrayListToString(topicName,","));
            // get user option to "Submit", "back" or "exit"
            String opt = ui.getUserOption(proceedOp, "", false);
            // if user choose to create
            if (opt.equalsIgnoreCase("Proceed")){
                // create conference entity
                cController.createConferenceEntity(confName, place, topicName, date, submitDueDate, reviewDueDate);
                // append the conference to csv file
                cController.appendConferenceCSV(confName, place, ut.arrayListToString(topicName,"/"), ut.dateToString(date), ut.dateToString(submitDueDate), ut.dateToString(reviewDueDate));
                // create a new Chair user entity with this conference name & add to user list
                User u = cms.searchUser(emailAddress);
                if (!u.getRole().equalsIgnoreCase("admin")) {
                    NormalUser nu = (NormalUser) u;
                    uController.createUserEntity("Chair", u.getEmail(), u.getPassword(), nu.getFirstName(), nu.getLastName(), nu.getHighestQualification(), nu.getOccupation(), nu.getEmployerDetail(), nu.getMobileNumber(), confName, null, null);
                    // write to csv file
                    uController.appendUserCSV("Chair", u.getEmail(), u.getPassword(), nu.getFirstName(), nu.getLastName(), nu.getHighestQualification(), nu.getOccupation(), nu.getEmployerDetail(), nu.getMobileNumber(), confName, null, null);
                    // display successful message
                    ui.displayMsgWithSleep("Congratulations!\n  You have created the Conference.\n");
                    this.homePageChoices(name,emailAddress);
                }
            } else if (opt.equalsIgnoreCase("Back")){
                // if user choose to go back
                this.createConference(name,emailAddress);
            } else if(opt.equalsIgnoreCase("Exit")){
                // if user choose to exit
                this.homePageChoices(name,emailAddress);
            }
        }
    }


    private void assignReviewer(String name, String emailAddress, String confName) throws InterruptedException {
    /**
     * Assign the reviewer for paper.
     * @param name of the chair
     * @param emailaddress of chair
     * @param the conference name
     */
        ArrayList<String> availablePaper = new ArrayList<String>();
        // get a list of paper in that conference
        for (Paper p : cms.retrievePaperList()) {
            // if the paper is from this conference
            if (p.getConferenceName().equals(confName)) {
                availablePaper.add(p.getTitle());
            }
        }
        availablePaper.add("Back");
        // get user option to select which paper or back
        String chosenPaper = ui.getUserOption(availablePaper, name, true);
        // if user choose to go back
        if (chosenPaper.equalsIgnoreCase("Back")) {
            // go back to previous page
            this.chairChoices(name, emailAddress,confName);
        } else {
            // if user choose the paper
            // check the number of reviewer of that paper, if larger than 4 then go back
            int reviewerNo = cms.searchPaper(chosenPaper).getNoOfReviewer();
            if (reviewerNo >= 4){
                ui.displayMsgWithSleep("The number of reviewers for this paper has reached maximum of 4.");
                availablePaper.clear();
                this.assignReviewer(name, emailAddress,confName);
            }
            // get the list of reviewer from that conference
            ArrayList<String[]> availableReviewer = new ArrayList<String[]>();
            for (User u:cms.retrieveUserList()){
                if (u.getRole().equalsIgnoreCase("reviewer")){
                    Reviewer ru = (Reviewer) u;
                    // if user is reviewer and participated in this conference, get the email address
                    if (ru.getConferenceName().equals(confName) && !ru.retrieveAssignedPaper().contains(chosenPaper)){
                        String[] suitReviewer = {ru.getEmail(),ru.getFirstName(),ru.getLastName()};
                        availableReviewer.add(suitReviewer);
                    }
                }
            }
            // get user option to choose reviewer
            String reviewerList = ui.getReviewer(availableReviewer);
            // delimit the information
            ArrayList<String> reviewerInd = ut.stringToArrayList(reviewerList, ",");
            // convert user input index to programmer index
            ArrayList<Integer> reviewerInt = ut.convertIndexBound(reviewerInd,availableReviewer.size());
            // if is empty
            if (reviewerInt == null){
                ui.displayMsgWithSleep("Please enter a valid reviewer number.");
                availablePaper.clear();
                this.assignReviewer(name, emailAddress,confName);
            }
            // check number of reviewer assigned
            if (reviewerInt.size()+reviewerNo < 1 || reviewerInt.size()+reviewerNo > 4){
                ui.displayMsgWithSleep("Please current number of reviewers for this paper exceed 4.");
                availablePaper.clear();
                this.assignReviewer(name, emailAddress,confName);
            }
            // get a list of selected reviewer
            ArrayList<String[]> selectedReviewer = new ArrayList<String[]>();
            for (int idx:reviewerInt){
                String[] reviewer = availableReviewer.get(idx);
                selectedReviewer.add(reviewer);
            }
            // modify the number of reviewers in paper csv
            ut.updatePaperCsv(pathPaperCSV,Integer.toString(selectedReviewer.size()+reviewerNo),3,chosenPaper);
            // modify the number of reviewer in paperList
            cms.searchPaper(chosenPaper).setNoOfReviewer(selectedReviewer.size()+reviewerNo);
            // modify the paper assigned in userlist
            for (String[] r : selectedReviewer) {
                //r[0] is email address, r[1] is firstname, r[2] is last name
                NormalUser nu = cms.searchSpecificUser(r[0], "reviewer", confName);
                if (nu != null) {
                    Reviewer ru2 = (Reviewer) nu;
                    ArrayList<String> papers = new ArrayList<String>();
                    // if the reviewer has no assigned paper
                    if (ru2.retrieveAssignedPaper().get(0) == "") {
                        papers.add(chosenPaper);
                        ru2.setAssignedPaper(papers);
                    } else {
                        // if reviewer has existing assigned paper
                        ru2.addAssignedPaper(chosenPaper);
                    }
                    papers = ru2.retrieveAssignedPaper();
                    // use opencsv to modify csv file for reviewer to include this paper
                    ut.updateUserCsv(pathUserCSV, ut.arrayListToString(papers, "/"), 11, r[0], "reviewer", confName);
                } else {
                    System.out.println("searchSpecificUser is null?! ");
                }
            }
            // display confirmation message
            ui.reviewerConfirmation(selectedReviewer,chosenPaper);
            // return back to home
            this.chairChoices(name, emailAddress,confName);
        }
    }


    private void joinConference(String name, String emailAddress) throws InterruptedException {
        /**
         * To join the conference
         * @param the name of the user
         * @param the email address of the user
         */
        // get the conference held before due date
        ArrayList<Conference> conf = cms.retrieveConferenceList();
        ArrayList<String> availableConf = new ArrayList<String>();
        for (Conference c : conf) {
            // if the date of conference is not past date  and user has not joined the conference before
            if (c.getDate().isAfter(LocalDate.now(ZoneId.of("Australia/Sydney"))) && !cms.getUserConference(emailAddress).contains(c.getName())) {
                availableConf.add(c.getName());
            }
        }
        availableConf.add("Back");
        // get user option to join which conference or back
        String chosenConf = ui.getUserOption(availableConf, name, true);
        if (chosenConf.equalsIgnoreCase("Back")) {
            // if user choose to go back
            this.homePageChoices(name, emailAddress);
        } else {
            // if the conference is selected, retrieve the conference object
            Conference c = cms.searchConference(chosenConf);
            // confirm the conference information
            ui.createConfConfirmation(c.getName(), c.getPlace(), ut.dateToString(c.getDate()), ut.dateToString(c.getPaperSubmissionDue()), ut.dateToString(c.getPaperReviewDue()), ut.arrayListToString(c.retrieveChosenTopicAreas(), ","));
            // get user option to join or exit
            String opt = ui.getUserOption(proceedOp, "", false);
            // if user choose to join
            if (opt.equalsIgnoreCase("Proceed")) {
                // get user option to "join as reviewer" or "join as author"
                ArrayList<String> roleOption = new ArrayList<>(Arrays.asList("Join as Reviewer", "Join as Author", "Back"));
                String chosenRole = ui.getUserOption(roleOption, name, true);
                if (chosenRole.equalsIgnoreCase("Back")) {
                    // if user choose to go back
                    availableConf.clear();
                    this.joinConference(name, emailAddress);
                } else if (chosenRole.equalsIgnoreCase("Join as Reviewer")) {
                    // if user join as reviewer
                    // get the topic areas
                    ArrayList<String> topicName = topicAreasProcess(name, emailAddress);
                    // confirm the topic area
                    ui.topicAreasConfirmation(ut.arrayListToString(topicName, ","));
                    String opt2 = ui.getUserOption(proceedOp, "", false);
                    if (opt2.equalsIgnoreCase("Proceed")) {
                        // if user choose to proceed
                        User u = cms.searchUser(emailAddress);
                        // create a new Reviewer object & add that object into user list
                        if (!u.getRole().equalsIgnoreCase("admin")) {
                            NormalUser nu = (NormalUser) u;
                            uController.createUserEntity("Reviewer", nu.getEmail(), nu.getPassword(), nu.getFirstName(), nu.getLastName(), nu.getHighestQualification(), nu.getOccupation(), nu.getEmployerDetail(), nu.getMobileNumber(), c.getName(), topicName, null);
                            // write to csv file
                            uController.appendUserCSV("Reviewer", nu.getEmail(), nu.getPassword(), nu.getFirstName(), nu.getLastName(), nu.getHighestQualification(), nu.getOccupation(), nu.getEmployerDetail(), nu.getMobileNumber(), c.getName(), ut.arrayListToString(topicName, "/"), null);
                            // display successful message & go to home page
                            ui.displayMsgWithSleep("Congratulations!\n\tYou have joined the Conference as Reviewer.");
                            this.homePageChoices(name, emailAddress);
                        }
                    } else if (opt2.equalsIgnoreCase("Back")) {
                        availableConf.clear();
                        this.joinConference(name, emailAddress);
                    } else if (opt.equalsIgnoreCase("Exit")) {
                        // if user choose to exit
                        this.homePageChoices(name, emailAddress);
                    }
                } else if (chosenRole.equalsIgnoreCase("Join as Author")) {
                    // if user choose to join as author
                    User u = cms.searchUser(emailAddress);
                    // create a new Reviewer object & add that object into user list
                    if (!u.getRole().equalsIgnoreCase("admin")) {
                        NormalUser nu = (NormalUser) u;
                        uController.createUserEntity("Author", nu.getEmail(), nu.getPassword(), nu.getFirstName(), nu.getLastName(), nu.getHighestQualification(), nu.getOccupation(), nu.getEmployerDetail(), nu.getMobileNumber(), c.getName(), null, null);
                        // write to csv file
                        uController.appendUserCSV("Author", nu.getEmail(), nu.getPassword(), nu.getFirstName(), nu.getLastName(), nu.getHighestQualification(), nu.getOccupation(), nu.getEmployerDetail(), nu.getMobileNumber(), c.getName(), null, null);
                        // display successful message & go to home page
                        ui.displayMsgWithSleep("Congratulations!\n\tYou have joined the Conference as Author.");
                        this.homePageChoices(name, emailAddress);
                    }
                }
            } else if (opt.equalsIgnoreCase("Exit")) {
                // if user choose to exit
                this.homePageChoices(name, emailAddress);
            } else if (opt.equalsIgnoreCase("Back")) {
                // if user choose to go back
                availableConf.clear();
                this.joinConference(name, emailAddress);
            }
        }
    }

    public ArrayList<String> topicAreasProcess(String name, String emailAddress) throws InterruptedException {
        /**
         * Method to get the topic areas, check the validity, get the topic name from index and
         * concatenate additional topic and index topic
         * @return the topic area list
         */
        // Get topic option
        String[] topicTmp = ui.getTopicAreas(avaiableTopics);
        // truncate white space and non visible character
        topicTmp[1] = topicTmp[1].replaceAll("\\s","");

        // delimit the information
        ArrayList<String> topicInd = ut.stringToArrayList(topicTmp[0], ",");
        ArrayList<String> topicName = ut.stringToArrayList(topicTmp[1], ",");
        // convert user input index to programmer index
        ArrayList<Integer> topicInt = ut.convertIndexBound(topicInd,avaiableTopics.size());
        // if is empty
        if (topicInt == null){
            ui.displayMsgWithSleep("Please enter a valid topic number.");
            this.createConference(name, emailAddress);
        }
        //use index of topic to retrieve index
        ArrayList<String> topicName2 = ut.indexToElement(topicInt,avaiableTopics);
        if (topicName.get(0) != "") {
            topicName2.addAll(topicName);
        }
        return topicName2;
    }


    private User authentication() throws NoSuchAlgorithmException, InterruptedException{
        /**
         * The method to authenticate user.
         * It gets the user input boundary and check the email address and password
         * @return the user who authenticated; null if not authenticated
         */
        // a variable to check if user is authenticated
        boolean authenticated = false;
        User u = null;
        // get the information
        String[] inputAuthenticate = ui.getAuthentication();
        // check the information
        authenticated = uController.checkAuthInfo(inputAuthenticate[0], inputAuthenticate[1]);
        if (authenticated == false){
            // if not authenticated, return to main page
            this.run();
        } else {
            // if authenticated, retrieve the user
            u = cms.searchUser(inputAuthenticate[0]);
        }
        return u;
    }


    private void registration() throws InterruptedException, NoSuchAlgorithmException{
        /**
         *  The method to register user.
         */
        String [] info = ui.getRegistration();
        // truncate white space and non visible character
        info = ut.truncateWhiteSpace(info);
        // check the validity of information
        // if information not valid, register again; else ask user to confirm information
        boolean flag = uController.checkRegistrationInfo(info[0],info[1],info[2],info[3],info[4],info[5],info[6],info[7]);
        if (flag == false){
            this.run();
        } else {
            // hash the password
            info[3] = ut.hashSHA256(info[3]);
            // confirm registration
            ui.registerConfirmation(info[0], info[1], info[2], info[4], info[5], info[6], info[7]);
            // get user option to "Register", "Back" or "Exit"
            ArrayList<String> confirmOption   = new ArrayList<>(Arrays.asList("Register","Back","Exit"));
            String op = ui.getUserOption(confirmOption, "", false);
            // if user choose to "Register"
            if (op.equalsIgnoreCase("Register")){
                // create user entity and add to cms user list
                uController.createUserEntity("normal", info[2], info[3], info[0], info[1], info[4], info[5], info[6], info[7], null, null, null);
                // write new user to csv file
                uController.appendUserCSV("normal", info[2], info[3], info[0], info[1], info[4], info[5], info[6], info[7], null, null, null);
                // redirect to main page
                ui.displayMsgWithSleep("You have successfully registered!\n\tEnjoy your conferences!\n\tPlease login again!");
                this.run();
            } else if (op.equalsIgnoreCase("Back")){
                //if user choose to "Back"
                // return to register page
                this.registration();
            } else if(op.equalsIgnoreCase("Exit")){
                //if user choose to "Exit"
                // return back to main page
                this.run();
            }
        }
    }
}

    