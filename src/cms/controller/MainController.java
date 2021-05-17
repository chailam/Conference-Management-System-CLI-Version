/**
 * The main controller class to handle the boundary business logic, which is the user input.
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


public class MainController {
    // static variable for role
    public static final String ROLE_ADMIN = "Admin";
    public static final String ROLE_CHAIR = "Chair";
    public static final String ROLE_AUTHOR = "Author";
    public static final String ROLE_REVIEWER = "Reviewer";
    public static final String ROLE_NORMAL = "Normal";
    // static variable for paper status
    public static final String STATUS_BEINGREVIEWED = "Being Reviewed";
    public static final String STATUS_REVIEWED = "Reviewed";
    public static final String STATUS_ACCEPTED = "Accepted";
    public static final String STATUS_REJECTED = "Rejected";
    // static variable for option
    public static final String OPTION_REGISTER = "Register";
    public static final String OPTION_LOGIN = "Login";
    public static final String OPTION_LOGOUT = "Logout";
    public static final String OPTION_BACK = "Back";
    public static final String OPTION_MANAGECONFERENCE = "Manage Your Conference";
    public static final String OPTION_JOINCONFERENCE = "Join Conference";
    public static final String OPTION_CREATECONFERENCE = "Create Conference";
    public static final String OPTION_RETRIEVEUSERINFO = "Retrieve User Information";
    public static final String OPTION_RETRIEVECONFERENCEINFO = "Retrieve Conference Information";
    public static final String OPTION_SUBMITPAPER = "Submit Paper";
    public static final String OPTION_FINALDECISION = "Final Decision on Paper";
    public static final String OPTION_ASSIGNREVIEWER = "Assign Reviewer to Paper";
    public static final String OPTION_SUBMITEVALUATION = "Submit Evaluation of Paper";
    public static final String OPTION_PROCEED = "Proceed";
    public static final String OPTION_EXIT = "Exit";
    public static final String OPTION_CONFIRM = "Confirm";
    public static final String OPTION_JOINREVIEWER = "Join as Reviewer";
    public static final String OPTION_JOINAUTHOR = "Join as Author";
    // Main page, Home Page, Admin, Chair, Author, Reviewer option
    // The option shown here are just the functionalities assigned by tutor.
    // The option for not assigned functionality are not shown here
    private ArrayList<String> mainPageOp  = new ArrayList<>(Arrays.asList(OPTION_REGISTER, OPTION_LOGIN));
    private ArrayList<String> homePageOp   = new ArrayList<>(Arrays.asList(OPTION_MANAGECONFERENCE,OPTION_JOINCONFERENCE,OPTION_CREATECONFERENCE,OPTION_LOGOUT));
    private ArrayList<String> adminOp   = new ArrayList<>(Arrays.asList(OPTION_RETRIEVEUSERINFO, OPTION_RETRIEVECONFERENCEINFO,OPTION_LOGOUT));
    private ArrayList<String> authorOp   = new ArrayList<>(Arrays.asList(OPTION_SUBMITPAPER, OPTION_BACK));
    private ArrayList<String> chairOp   = new ArrayList<>(Arrays.asList(OPTION_FINALDECISION, OPTION_ASSIGNREVIEWER,OPTION_BACK));
    private ArrayList<String> reviewerOp   = new ArrayList<>(Arrays.asList(OPTION_SUBMITEVALUATION,OPTION_BACK));
    private ArrayList<String> proceedOp = new ArrayList<>(Arrays.asList(OPTION_PROCEED,OPTION_BACK,OPTION_EXIT));
    ArrayList<String> roleOption = new ArrayList<>(Arrays.asList(OPTION_JOINREVIEWER, OPTION_JOINAUTHOR, OPTION_BACK));
    private ArrayList<String> availableTopics = new ArrayList<>(Arrays.asList("Artificial Intelligence", "Human Computer Interaction", "Data Mining & Information Retrieval", "Image Processing", "Big Data", "Computer Networks", "Software Engineering ", "Security & Cryptography", "Robotics & Automation", "Database & Information Systems"));
    private Utility ut = new Utility();

    // Define the path to User.csv, Conference.csv, and Paper.csv
    protected String pathUserCSV = "src/resource/User.csv";
    protected String pathConferenceCSV = "src/resource/Conference.csv";
    protected String pathPaperCSV = "src/resource/Paper.csv";

    // Initialize user interface invoked
    protected UserInterface ui = new UserInterface();
    // Initialize the ConferenceManagementSystem (as DB)
    protected ConferenceManagementSystem cms = new ConferenceManagementSystem();

    // Initialize the workers controller
    private PaperController pController = new PaperController(cms,ui,pathPaperCSV);
    private ConferenceController cController = new ConferenceController(cms,ui,pathConferenceCSV);
    private UserController uController = new UserController(cms,ui,pathUserCSV);

    public void run(){
        /**
         * The method to kick start the program.
         */
        try{
            // get user option to "Register" or "Login"
            String opt = ui.getUserOption(mainPageOp,"Guest",true);
            // if user choose to "Register"
            if (opt.equalsIgnoreCase(OPTION_REGISTER)){
                this.registration();
            } else if (opt.equalsIgnoreCase(OPTION_LOGIN)){
                // if user choose to "Login"
                User u = this.authentication();
                if (u != null) {
                    if (u.getRole().equalsIgnoreCase(ROLE_ADMIN)) {
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
        if (op.equalsIgnoreCase(OPTION_MANAGECONFERENCE)){
            // if user choose to manage conference
            // List all the conference available for that user.
            ArrayList<String> userConf = cms.getUserConference(emailAddress);
            userConf.add(OPTION_BACK);
            if (!(userConf.get(0).equalsIgnoreCase(OPTION_BACK))){
                // if user has conference
                // get user chosen conference to be managed
                String conf = ui.getUserOption(userConf, name,true);
                if (conf.equalsIgnoreCase(OPTION_BACK)){
                    // if user choose to go back
                    userConf.remove(OPTION_BACK);
                    this.homePageChoices(name, emailAddress);
                } else {
                    // if user choose the conference
                    // get the role of the user in that conference
                    String role = cms.getUserConferenceRole(emailAddress, conf);
                    if (role == null){
                        System.out.println("Error: role is null in homePageChoices.");
                    }
                    if (role.equalsIgnoreCase(ROLE_CHAIR)){
                        // if user is chair in that conference, direct to chair option
                        userConf.remove(OPTION_BACK);
                        this.chairChoices(name,emailAddress,conf);
                    } else if (role.equalsIgnoreCase(ROLE_AUTHOR)){
                        // if user is author in that conference, direct to author option
                        userConf.remove(OPTION_BACK);
                        this.authorChoices(name,emailAddress,conf);
                    } else if (role.equalsIgnoreCase(ROLE_REVIEWER)){
                        // if user is reviewer in that conference, direct to reviewer option
                        userConf.remove(OPTION_BACK);
                        this.reviewerChoices(name,emailAddress,conf);
                    }
                }
            } else {
                // if conference number is zero, show message and return to home page
                ui.displayMsgWithSleep("You have no conference to manage.");
                userConf.clear();
                this.homePageChoices(name, emailAddress);
            }
        } else if (op.equalsIgnoreCase(OPTION_CREATECONFERENCE)){
            // if user choose to create conference
            this.createConference(name,emailAddress);
        } else if (op.equalsIgnoreCase(OPTION_JOINCONFERENCE)){
            // if user choose to join conference
            this.joinConference(name, emailAddress);
        } else if (op.equalsIgnoreCase(OPTION_LOGOUT)){
            // return to main page
            this.run();
        }
    }


    private void adminChoices(){
        /**
         * The option available for admin and its operation.
         */
        // get the admin option to "Retrieve User Information", "Retrieve Conference Information" or "Logout"
        String op = ui.getUserOption(adminOp, ROLE_ADMIN,true);
        if (op.equalsIgnoreCase(OPTION_RETRIEVEUSERINFO)){
            // if admin choose to Retrieve User Information
            // get the userlist and print out the information
            ArrayList<User> userList = cms.retrieveUserList();
            ui.adminDisplayUserInfo(userList);
            // if exit is entered, return to admin page
            if (ui.getExitCommand() == true){
                this.adminChoices();
            }
        } else if (op.equalsIgnoreCase(OPTION_RETRIEVECONFERENCEINFO)){
            // if admin choose to Retrieve Conference Information
            //get the conferenceList and print out the information
            ArrayList<Conference> confList = cms.retrieveConferenceList();
            ui.adminDisplayConfInfo(confList);
            // if exit is entered, return to admin page
            if (ui.getExitCommand() == true){
                this.adminChoices();
            }
        } else if (op.equalsIgnoreCase(OPTION_LOGOUT)){
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
        if (op.equalsIgnoreCase(OPTION_BACK)) {
            homePageChoices(name, emailAddress);
        } else if (op.equalsIgnoreCase(OPTION_SUBMITPAPER)) {
            // if user choose to submit paper
            this.submitPaper(name, emailAddress, confName);
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
        if (op.equalsIgnoreCase(OPTION_BACK)){
            // if user choose to go back
            this.homePageChoices(name, emailAddress);
        } else if (op.equalsIgnoreCase(OPTION_FINALDECISION)){
            // if user choose to Final Decision on Paper
            this.finalDecisionPaper(name, emailAddress, confName);
        } else if (op.equalsIgnoreCase(OPTION_ASSIGNREVIEWER)){
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
        if (op.equalsIgnoreCase(OPTION_BACK)){
            homePageChoices(name, emailAddress);
        } else if (op.equalsIgnoreCase(OPTION_SUBMITEVALUATION)){
            // if user choose to Submit Evaluation of Paper
            this.submitEvaluation(name,emailAddress,confName);
        }
    }

    private void submitEvaluation(String name, String emailAddress, String confName) throws InterruptedException {
        /**
         * To submit the evaluation of paper
         * @param name of the reviewer
         * @param email address of reviewer
         * @param conference name of the paper evaluation
         */
        // check the date of reviewer submission
        LocalDate reviewDue = cms.searchConference(confName).getPaperReviewDue();
        if (reviewDue.isBefore(LocalDate.now(ZoneId.of( "Australia/Sydney" )))){
            ui.displayMsgWithSleep("The paper review due date has been passed!");
            this.reviewerChoices(name,emailAddress,confName);
        }
        // get a list of assigned paper
        Reviewer ru = (Reviewer) cms.searchSpecificUser(emailAddress,ROLE_REVIEWER,confName);
        ArrayList<String> assignedPapers =  ru.retrieveAssignedPaper();
        // get the user option to choose which paper
        assignedPapers.add(OPTION_BACK);
        // get user option to select which paper or back
        String chosenPaper = ui.getUserOption(assignedPapers, name, true);
        if (chosenPaper.equalsIgnoreCase(OPTION_BACK)) {
            // if user choose to go back
            assignedPapers.remove(OPTION_BACK);
            this.reviewerChoices(name, emailAddress,confName);
        } else {
            // if user choose an paper
            // get the evaluation of paper
            String evaluation = ui.getEvaluation(chosenPaper);
            evaluation = evaluation.trim();
            // check if the evaluation is empty
            if (evaluation == ""){
                ui.displayMsgWithSleep("The evaluation is empty.");
                assignedPapers.remove(OPTION_BACK);
                this.submitEvaluation(name,emailAddress,confName);
            }
            // get confirmation for the evaluation
            ui.confirmEvaluation(chosenPaper,evaluation);
            ArrayList<String> choices = new ArrayList<>(Arrays.asList(OPTION_CONFIRM,OPTION_BACK));
            String op = ui.getUserOption(choices,"",false);
            ui.displayFooter();
            if (op.equalsIgnoreCase(OPTION_CONFIRM)){
                // if user choose to confirm
                // get the paper and update the evaluation
                pController.updatePaperEvaluation(chosenPaper, confName, evaluation);
                // display successful message and redirect
                ui.displayMsgWithSleep("You have successfully uploaded the evaluation.");
                assignedPapers.remove(OPTION_BACK);
                this.homePageChoices(name, emailAddress);
            } else if (op.equalsIgnoreCase(OPTION_BACK)){
                // if user choose to go back
                assignedPapers.remove(OPTION_BACK);
                this.submitEvaluation(name, emailAddress,confName);
            }
        }
    }



    private void submitPaper (String name, String emailAddress, String confName) throws InterruptedException {
        /**
         * To submit the paper for the conference
         * @param name of the submitter
         * @param email address of the submitter
         * @param the conference the paper submit to
         */
        // show to choose topic areas
        ArrayList<String> topicName = topicAreasProcess(name, emailAddress);
        if (topicName == null){
            this.authorChoices(name,emailAddress,confName);
        }
        // get confirmation for the topic
        ui.topicAreasConfirmation(ut.arrayListToString(topicName,","));
        // get the user option to proceed with topic area or go back
        String opt = ui.getUserOption(proceedOp, "", false);
        // if user choose to confirm
        if (opt.equalsIgnoreCase(OPTION_PROCEED)){
            // get the info required for paper submission
            String[] paperInfo = ui.getPaperSubmission();
            // truncate leading white space
            paperInfo = ut.trimWhiteSpace(paperInfo);
            // check the info validity
            boolean flag = pController.checkPaperSubmitInfo(paperInfo[0],paperInfo[1],confName);
            if (flag == false){
                // if the information is not valid
                this.authorChoices(name,emailAddress,confName);
            }
            // if the information is valid
            String title = paperInfo[0];
            // create a new Paper object and add to paper list
            pController.addPaperEntity(title, emailAddress,null,0,null,confName,topicName,STATUS_BEINGREVIEWED);
            //write paper to csv file
            pController.appendPaperCSV(title, emailAddress, null,"0",null,confName,ut.arrayListToString(topicName,"/"), STATUS_BEINGREVIEWED);
            // Update the author information to include this paper
            ArrayList<String> papers = uController.retrieveUpdatedUserPaperInfo(emailAddress,ROLE_AUTHOR,confName,title);
            if (papers != null){
                // update csv file for author to include this paper
                uController.updateUserCsv(pathUserCSV, ut.arrayListToString(papers,"/"),11, emailAddress, ROLE_AUTHOR, confName);
            } else {
                System.out.println("Error: papers is null in submitPaper.");
            }
            // display message & go to author page
            ui.displayMsgWithSleep("Congratulations!\n\tYou have submitted the paper.");
            this.authorChoices(name,emailAddress,confName);
        } else if (opt.equalsIgnoreCase(OPTION_BACK)){
            // if user choose to go back
            this.submitPaper(name,emailAddress,confName);
        }
        else if (opt.equalsIgnoreCase(OPTION_EXIT)){
            // if user choose to go exit
            this.homePageChoices(name,emailAddress);
        }
    }


    private void finalDecisionPaper (String name, String emailAddress, String confName) throws InterruptedException {
        /**
         * To Accept or Reject the paper
         * @param name of the decider
         * @param email address of the decider
         * @param conference name
         */
        // get a list of paper in that conference where status is reviewed
        ArrayList<String> reviewedPaper = pController.getPaperWithSpecificStatus(confName, STATUS_REVIEWED);
        if (reviewedPaper.size() == 0) {
            // if no paper all reviewed
            ui.displayMsgWithSleep("All papers are still under reviewing.");
            this.chairChoices(name, emailAddress,confName);
        } else {
            // if there is paper fully reviewed
            reviewedPaper.add(OPTION_BACK);
            // get the user option to choose a paper
            String opt = ui.getUserOption(reviewedPaper, name, true);
            if (opt.equalsIgnoreCase(OPTION_BACK)) {
                // if user choose back
                reviewedPaper.remove(OPTION_BACK);
                this.chairChoices(name, emailAddress,confName);
            } else {
                // if user choose a paper
                // get the evaluation of the paper
                Paper p = cms.searchPaper(opt,confName);
                ArrayList<String> evaluations = p.retrieveEvaluations();
                ui.confirmEvaluation(opt, ut.arrayListToString(evaluations, ";\n\t"));
                // get user option to reject or accept
                ArrayList<String> finalDecision = new ArrayList<>(Arrays.asList(STATUS_ACCEPTED, STATUS_REJECTED, OPTION_BACK));
                String opt2 = ui.getUserOption(finalDecision, "", false);
                if (opt2.equalsIgnoreCase(OPTION_BACK)) {
                    // if user choose to go back
                    reviewedPaper.remove(OPTION_BACK);
                    this.finalDecisionPaper(name, emailAddress,confName);
                } else if (opt2.equalsIgnoreCase(STATUS_ACCEPTED)) {
                    // set the status of the paper to Accept
                    pController.updatePaperProgressStatus(p.getTitle(),STATUS_ACCEPTED,confName);
                    // display message
                    ui.displayMsgWithSleep("You have successfully Accepted the paper.");
                    reviewedPaper.remove(OPTION_BACK);
                    this.homePageChoices(name, emailAddress);
                } else if (opt2.equalsIgnoreCase(STATUS_REJECTED)) {
                    // set the status of the paper to Reject
                    pController.updatePaperProgressStatus(p.getTitle(),STATUS_REJECTED,confName);
                    // display message
                    reviewedPaper.remove(OPTION_BACK);
                    ui.displayMsgWithSleep("You have successfully Rejected the paper.");
                    this.homePageChoices(name, emailAddress);
                }
            }
        }
    }


    private void createConference (String name, String emailAddress) throws InterruptedException {
        /**
         * To create conference
         * @param name of the creator
         * @param email address of the creator
         */
        // Get the conference information retrieved
        String [] confInfo = ui.getCreateConference();
        // truncate white space and non visible character
        String confName = confInfo[0].trim();
        String place = confInfo[1].trim();
        String sDate = confInfo[2].replaceAll("\\s","");
        String submitDue = confInfo[3].replaceAll("\\s","");
        String reviewDue = confInfo[4].replaceAll("\\s","");
        // check the validity of data
        boolean flag = cController.checkCreateConfInfo(confName,place,sDate,submitDue,reviewDue);
        if (flag == false) {
            // if data is not valid, redirect to home page
            this.homePageChoices(name,emailAddress);
        } else {
            // if data is valid
            LocalDate date = ut.stringToDate(confInfo[2]);
            LocalDate submitDueDate = ut.stringToDate(confInfo[3]);
            LocalDate reviewDueDate = ut.stringToDate(confInfo[4]);
            // get the topic area
            ArrayList<String> topicName = topicAreasProcess (name, emailAddress);
            if (topicName == null){
                this.homePageChoices(name,emailAddress);
            }
            // show the conference confirmation message
            ui.createConfConfirmation(confName,place, ut.dateToString(date), ut.dateToString(submitDueDate), ut.dateToString(reviewDueDate), ut.arrayListToString(topicName,","));
            // get user option to "Submit", "back" or "exit"
            String opt = ui.getUserOption(proceedOp, "", false);
            // if user choose to create
            if (opt.equalsIgnoreCase(OPTION_PROCEED)){
                // create conference entity
                cController.addConferenceEntity(confName, place, topicName, date, submitDueDate, reviewDueDate);
                // append the conference to csv file
                cController.appendConferenceCSV(confName, place, ut.arrayListToString(topicName,"/"), ut.dateToString(date), ut.dateToString(submitDueDate), ut.dateToString(reviewDueDate));
                // create a new Chair user entity with this conference name & add to user list
                User u = cms.searchUser(emailAddress);
                if (!u.getRole().equalsIgnoreCase(ROLE_ADMIN)) {
                    NormalUser nu = (NormalUser) u;
                    uController.addUserEntity(ROLE_CHAIR, u.getEmail(), u.getPassword(), nu.getFirstName(), nu.getLastName(), nu.getHighestQualification(), nu.getOccupation(), nu.getEmployerDetail(), nu.getMobileNumber(), confName, null, null);
                    // write to csv file
                    uController.appendUserCSV(ROLE_CHAIR, u.getEmail(), u.getPassword(), nu.getFirstName(), nu.getLastName(), nu.getHighestQualification(), nu.getOccupation(), nu.getEmployerDetail(), nu.getMobileNumber(), confName, null, null);
                    // display successful message
                    ui.displayMsgWithSleep("Congratulations!\n  You have created the Conference.\n");
                    this.homePageChoices(name,emailAddress);
                }
            } else if (opt.equalsIgnoreCase(OPTION_BACK)){
                // if user choose to go back
                this.createConference(name,emailAddress);
            } else if (opt.equalsIgnoreCase(OPTION_EXIT)){
                // if user choose to exit
                this.homePageChoices(name,emailAddress);
            }
        }
    }


    private void assignReviewer(String name, String emailAddress, String confName) throws InterruptedException {
    /**
     * Assign the reviewer for paper.
     * @param name of the user
     * @param email address of user
     * @param the conference name
     */
        ArrayList<String> availablePaper = new ArrayList<String>();
        // get a list of paper in that conference
        for (Paper p : cms.retrievePaperList()) {
            // if the paper is from the conference
            if (p.getConferenceName().equals(confName)) {
                availablePaper.add(p.getTitle());
            }
        }
        availablePaper.add(OPTION_BACK);
        // get user option to select which paper or back
        String chosenPaper = ui.getUserOption(availablePaper, name, true);
        if (chosenPaper.equalsIgnoreCase(OPTION_BACK)) {
            // if user choose to go back
            availablePaper.remove(OPTION_BACK);
            this.chairChoices(name, emailAddress,confName);
        } else {
            // if user choose the paper
            // check the number of reviewer of that paper, if larger than 4 then show message
            int reviewerNo = cms.searchPaper(chosenPaper,confName).getNoOfReviewer();
            if (reviewerNo >= 4){
                ui.displayMsgWithSleep("The number of reviewers for this paper has reached maximum of 4.");
                availablePaper.remove(OPTION_BACK);
                this.assignReviewer(name, emailAddress,confName);
            }
            // get the list of reviewer from that conference
            ArrayList<String[]> availableReviewer = new ArrayList<String[]>();
            for (User u:cms.retrieveUserList()){
                if (u.getRole().equalsIgnoreCase(ROLE_REVIEWER)){
                    Reviewer ru = (Reviewer) u;
                    // if user is reviewer and participated in this conference and has not been assigned this paper,
                    // get the email address
                    if (ru.getConferenceName().equals(confName) && !ru.retrieveAssignedPaper().contains(chosenPaper)){
                        String[] suitReviewer = {ru.getEmail(),ru.getFirstName(),ru.getLastName()};
                        availableReviewer.add(suitReviewer);
                    }
                }
            }
            // check if the reviewers number for that conference is 0, return message
            if (availableReviewer.size() == 0){
                ui.displayMsgWithSleep("No reviewers joined this conference.");
                availablePaper.remove(OPTION_BACK);
                this.chairChoices(name, emailAddress,confName);
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
                availablePaper.remove(OPTION_BACK);
                this.assignReviewer(name, emailAddress,confName);
            }
            // check number of reviewer assigned
            if (reviewerInt.size()+reviewerNo < 1 || reviewerInt.size()+reviewerNo > 4){
                ui.displayMsgWithSleep("Please current number of reviewers for this paper exceed 4.");
                availablePaper.remove(OPTION_BACK);
                this.assignReviewer(name, emailAddress,confName);
            }
            // get a list of selected reviewer
            ArrayList<String[]> selectedReviewer = new ArrayList<String[]>();
            for (int idx:reviewerInt){
                String[] reviewer = availableReviewer.get(idx);
                selectedReviewer.add(reviewer);
            }
            // modify the number of reviewers in paper csv & paperlist cms
            pController.updatePaperNoOfReviewer(chosenPaper, confName, selectedReviewer.size()+reviewerNo);
            // modify the paper assigned in user csv & userlist cms for each reviewer user
            for (String[] r : selectedReviewer) {
                //r[0] is email address, r[1] is firstname, r[2] is last name
                ArrayList<String> papers = uController.retrieveUpdatedUserPaperInfo(r[0],"reviewer", confName,chosenPaper);
                if (papers != null) {
                    // modify csv file for reviewer to include this paper
                    uController.updateUserCsv(pathUserCSV, ut.arrayListToString(papers, "/"), 11, r[0], "reviewer", confName);
                } else {
                    System.out.println("Error: papers is null in assignReviewer. ");
                }
            }
            // display confirmation message & back to chair page
            ui.reviewerConfirmation(selectedReviewer,chosenPaper);
            availablePaper.remove(OPTION_BACK);
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
        availableConf.add(OPTION_BACK);
        // get user option to join which conference or back
        String chosenConf = ui.getUserOption(availableConf, name, true);
        if (chosenConf.equalsIgnoreCase(OPTION_BACK)) {
            // if user choose to go back
            availableConf.remove(OPTION_BACK);
            this.homePageChoices(name, emailAddress);
        } else {
            // if the conference is selected, retrieve the conference object
            Conference c = cms.searchConference(chosenConf);
            // confirm the conference information
            ui.createConfConfirmation(c.getName(), c.getPlace(), ut.dateToString(c.getDate()), ut.dateToString(c.getPaperSubmissionDue()), ut.dateToString(c.getPaperReviewDue()), ut.arrayListToString(c.retrieveChosenTopicAreas(), ","));
            // get user option to join or exit
            String opt = ui.getUserOption(proceedOp, "", false);
            // if user choose to join
            if (opt.equalsIgnoreCase(OPTION_PROCEED)) {
                // get user option to "join as reviewer" or "join as author"

                String chosenRole = ui.getUserOption(roleOption, name, true);
                if (chosenRole.equalsIgnoreCase(OPTION_BACK)) {
                    // if user choose to go back
                    availableConf.remove(OPTION_BACK);
                    this.joinConference(name, emailAddress);
                } else if (chosenRole.equalsIgnoreCase(OPTION_JOINREVIEWER)) {
                    // if user join as reviewer
                    // get the topic areas
                    ArrayList<String> topicName = topicAreasProcess(name, emailAddress);
                    if (topicName == null){
                        availableConf.remove(OPTION_BACK);
                        this.joinConference(name,emailAddress);
                    }
                    // confirm the topic area
                    ui.topicAreasConfirmation(ut.arrayListToString(topicName, ","));
                    String opt2 = ui.getUserOption(proceedOp, "", false);
                    if (opt2.equalsIgnoreCase(OPTION_PROCEED)) {
                        // if user choose to proceed
                        User u = cms.searchUser(emailAddress);
                        // create a new Reviewer object & add that object into user list
                        if (!u.getRole().equalsIgnoreCase(ROLE_ADMIN)) {
                            NormalUser nu = (NormalUser) u;
                            uController.addUserEntity(ROLE_REVIEWER, nu.getEmail(), nu.getPassword(), nu.getFirstName(), nu.getLastName(), nu.getHighestQualification(), nu.getOccupation(), nu.getEmployerDetail(), nu.getMobileNumber(), c.getName(), topicName, null);
                            // write to csv file
                            uController.appendUserCSV(ROLE_REVIEWER, nu.getEmail(), nu.getPassword(), nu.getFirstName(), nu.getLastName(), nu.getHighestQualification(), nu.getOccupation(), nu.getEmployerDetail(), nu.getMobileNumber(), c.getName(), ut.arrayListToString(topicName, "/"), null);
                            // display successful message & go to home page
                            ui.displayMsgWithSleep("Congratulations!\n\tYou have joined the Conference as Reviewer.");
                            availableConf.remove(OPTION_BACK);
                            this.homePageChoices(name, emailAddress);
                        }
                    } else if (opt2.equalsIgnoreCase(OPTION_BACK)) {
                        availableConf.remove(OPTION_BACK);
                        this.joinConference(name, emailAddress);
                    } else if (opt.equalsIgnoreCase(OPTION_EXIT)) {
                        // if user choose to exit
                        availableConf.remove(OPTION_BACK);
                        this.homePageChoices(name, emailAddress);
                    }
                } else if (chosenRole.equalsIgnoreCase(OPTION_JOINAUTHOR)) {
                    // if user choose to join as author
                    User u = cms.searchUser(emailAddress);
                    // create a new Reviewer object & add that object into user list
                    if (!u.getRole().equalsIgnoreCase(ROLE_ADMIN)) {
                        NormalUser nu = (NormalUser) u;
                        uController.addUserEntity(ROLE_AUTHOR, nu.getEmail(), nu.getPassword(), nu.getFirstName(), nu.getLastName(), nu.getHighestQualification(), nu.getOccupation(), nu.getEmployerDetail(), nu.getMobileNumber(), c.getName(), null, null);
                        // write to csv file
                        uController.appendUserCSV(ROLE_AUTHOR, nu.getEmail(), nu.getPassword(), nu.getFirstName(), nu.getLastName(), nu.getHighestQualification(), nu.getOccupation(), nu.getEmployerDetail(), nu.getMobileNumber(), c.getName(), null, null);
                        // display successful message & go to home page
                        ui.displayMsgWithSleep("Congratulations!\n\tYou have joined the Conference as Author.");
                        availableConf.remove(OPTION_BACK);
                        this.homePageChoices(name, emailAddress);
                    }
                }
            } else if (opt.equalsIgnoreCase(OPTION_EXIT)) {
                // if user choose to exit
                availableConf.remove(OPTION_BACK);
                this.homePageChoices(name, emailAddress);
            } else if (opt.equalsIgnoreCase(OPTION_BACK)) {
                // if user choose to go back
                availableConf.remove(OPTION_BACK);
                this.joinConference(name, emailAddress);
            }
        }
    }

    private ArrayList<String> topicAreasProcess(String name, String emailAddress) throws InterruptedException {
        /**
         * Method to get the topic areas, check the validity, get the topic name from index and
         * concatenate additional topic and index topic
         * @return the topic area list
         */
        // Get topic option
        String topicTmp = ui.getTopicAreas(availableTopics);
        // truncate white space and non visible character
        topicTmp = topicTmp.replaceAll("\\s","");

        // delimit the information
        ArrayList<String> topicInd = ut.stringToArrayList(topicTmp, ",");
        // convert user input index to programmer index
        ArrayList<Integer> topicInt = ut.convertIndexBound(topicInd, availableTopics.size());
        // if is empty
        if (topicInt == null){
            ui.displayMsgWithSleep("Please enter a valid topic number.");
            return null;
        }
        //use index of topic to retrieve index
        ArrayList<String> topicName = ut.indexToElement(topicInt, availableTopics);
        return topicName;
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
        info = ut.trimWhiteSpace(info);
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
            ArrayList<String> confirmOption   = new ArrayList<>(Arrays.asList(OPTION_REGISTER,OPTION_BACK,OPTION_EXIT));
            String op = ui.getUserOption(confirmOption, "", false);
            // if user choose to "Register"
            if (op.equalsIgnoreCase(OPTION_REGISTER)){
                // create user entity and add to cms user list
                uController.addUserEntity(ROLE_NORMAL, info[2], info[3], info[0], info[1], info[4], info[5], info[6], info[7], null, null, null);
                // write new user to csv file
                uController.appendUserCSV(ROLE_NORMAL, info[2], info[3], info[0], info[1], info[4], info[5], info[6], info[7], null, null, null);
                // redirect to main page
                ui.displayMsgWithSleep("You have successfully registered!\n\tEnjoy your conferences!\n\tPlease login again!");
                this.run();
            } else if (op.equalsIgnoreCase(OPTION_BACK)){
                //if user choose to "Back"
                // return to register page
                this.registration();
            } else if(op.equalsIgnoreCase(OPTION_EXIT)){
                //if user choose to "Exit"
                // return back to main page
                this.run();
            }
        }
    }
}

    