/**
 * The text-based user interface of the system, act as boundary for the user.
 */


package cms.view;

import cms.Utility;
import cms.entity.*;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.concurrent.TimeUnit;
import java.util.*;


public class UserInterface {
    public String [] header = {"                                                                                                          ",
                        "                                                                                                          ",
                        "**********************************************************************************************************",
                        "                                                                                                          ",
                        "                                      Conference Management System (CMS)                                  ",
                        "                                                                                                          ",
                        "**********************************************************************************************************"};

    public String [] footer = {"***********************************************************************************************************",
                        "                                                              powered by Monash Conference Centre (MCC)   ",
                        "***********************************************************************************************************"};

    private Scanner scanner;
    private Utility ut = new Utility();


    public UserInterface(){
    /**
     * The constructor of the UserInterface. It initiate an input Scanner.
     */
        scanner = new Scanner(System.in);
        scanner.useDelimiter(System.lineSeparator());
    }
    

    public void displayHeader (){
    /**
     * To print the conststent header of the system
     */
        for(String line: header) {
            System.out.println(line);
        }
    }


    public void displayGreeting (String name){
    /**
     * To greet the user
     * @param the name of user to be greet
     */
        this.displayMessageLn("Hi "+ name );
        this.displayMessageLn("");
        this.displayMessageLn("");
    }


    public void displayFooter (){
    /**
     * To print the consistent footer of the system
     */
        this.displayMessageLn("");
        this.displayMessageLn("");
        for(String line: footer) {
            System.out.println(line);
        }
    }


    public void displayMessage(String msg){
    /**
     * To print the message without new line
     */
        System.out.print("    " + msg);
    }


    public void displayMessageLn(String msg){
        /**
         * To print the message with new line
         */
        System.out.println("    " + msg);
    }


    public void displayMsgWithSleep(String message) throws InterruptedException{
    /**
     * The method to show the error message.
     * It will sleep for 2 seconds before jumping to other screen
     */
        this.displayHeader();
        this.displayMessageLn(message);
        this.displayFooter();
        TimeUnit.SECONDS.sleep(2);
    }


    public String getUserOption (ArrayList<String> options, String name, boolean bo){
        /**
         * To display the option available and then get the user input within option length
         * @param the option selected
         * @param the name to be greet
         * @param true to show header and footer; false otherwise
         */
        if (bo == true){
            this.displayHeader();
            if (!name.equals("")){
                this.displayGreeting(name);
            }
        }
        this.displayMessageLn("Please enter your options:");

        // Print out all options
        for( int i = 0; i < options.size(); i++){
            this.displayMessageLn((i+1) + ". " + options.get(i));
        }
        int choice = 0;
        while (choice < 1 || choice > options.size()) {//loop until a valid option has been obtained
            if ( choice > options.size()){
                this.displayMessageLn("Error: Invalid value. Please enter a valid value.");
            }
            this.displayMessage("Enter option (1 - "+ options.size() +"): ");
            try{
                choice = Integer.parseInt(scanner.nextLine());
                // verify boundary value
                if (choice <= 0){
                    this.displayMessageLn("Error: Invalid value. Please enter a valid value.");
                }

            }catch (InputMismatchException | NumberFormatException e) { //catching the non integer inputs
                choice = 0;
                this.displayMessageLn("Error: Invalid value. Please enter a valid value.");
            }
        }
        // Print the footer
        this.displayFooter();
        return options.get(choice-1);//return the option value selected
    }


    public boolean getExitCommand(){
        /**
         * The method to detect exit command
         * @return true if exit entered
         */
        String input = "";
        while (!input.equals("exit")){
            this.displayMessageLn("Enter \"exit\" to return to previous page: ");
            input = scanner.nextLine();
        }
        return true;
    }


    public void adminDisplayUserInfo (ArrayList<User> userList){
        /**
         * Admin ui to print out the User Info
         * @param the userlist to be printed
         */
        this.displayHeader();
        this.displayMessageLn("---------------------------------------Registered User Information---------------------------------------");
        System.out.format("%10s %10s %20s %25s %25s %35s %15s %30s %10s","First Name","Last Name","Email","Highest Qualification","Occupation","Employer's Details","Mobile Number","Conference","Role");
        this.displayMessageLn("");
        for (User u: userList){
            if (!u.getRole().equalsIgnoreCase("admin")){
                NormalUser nu = (NormalUser) u;
                System.out.format("%10s %10s %20s %25s %25s %35s %15s %30s %10s",nu.getFirstName(),nu.getLastName(),nu.getEmail(),nu.getHighestQualification(),nu.getOccupation(),nu.getEmployerDetail(),nu.getMobileNumber(),nu.getConferenceName(),nu.getRole());
                this.displayMessageLn("");
            }
        }
        this.displayFooter();
    }


    public void adminDisplayConfInfo (ArrayList<Conference> confList){
        /**
         * Admin ui to print out the Conference Info
         * @param the conferenceList to be printed
         */
        this.displayHeader();
        this.displayMessageLn("---------------------------------------Registered Conference Information---------------------------------------");
        System.out.format("%50s %20s %15s %15s %15s %40s","Name","Place","Date","Submission Due","Review Due","Topic Areas");
        this.displayMessageLn("");
        for (Conference c: confList){
            System.out.format("%50s %20s %15s %15s %15s %40s",c.getName(),c.getPlace(),ut.dateToString(c.getDate()),ut.dateToString(c.getPaperSubmissionDue()),ut.dateToString(c.getPaperSubmissionDue()),ut.arrayListToString(c.retrieveChosenTopicAreas(),","));
            this.displayMessageLn("");
        }
        this.displayFooter();
    }


    public String[] getAuthentication(){
    /**
     * To get the email address and password of the user
     * @return the emailAddress and Password retrieved
     */
        this.displayHeader();
        this.displayGreeting("Guest");

        this.displayMessageLn("Please enter your user credentials.");
        this.displayMessage("Email Address: ");
        String emailAddress = scanner.nextLine(); // Read the user input
        this.displayMessage("Password: ");
        String password = scanner.nextLine(); // Read the user input
        this.displayFooter();
        return new String[] {emailAddress, password};
    }


    public String[] getRegistration(){
    /**
     * To get the required registration information from the user
     * @return the information retrieved
     */
        this.displayHeader();
        this.displayMessageLn("Registration Process (1/2)");
        this.displayMessageLn("Enter user details.");
        this.displayMessage("First Name: ");
        String firstName = scanner.nextLine(); // Read the user input
        this.displayMessage("Last Name: ");
        String lastName = scanner.nextLine(); // Read the user input
        this.displayMessage("Email Address: ");
        String emailAddress = scanner.nextLine(); // Read the user input
        this.displayMessage("Password: ");
        String password = scanner.nextLine(); // Read the user input
        this.displayMessage("Highest Qualification: ");
        String highestQualification = scanner.nextLine(); // Read the user input
        this.displayMessage("Occupation: ");
        String occupation = scanner.nextLine(); // Read the user input
        this.displayMessage("Employer's details: ");
        String employerDetail = scanner.nextLine(); // Read the user input
        this.displayMessage("Mobile number: ");
        String mobileNumber = scanner.nextLine(); // Read the user input
        this.displayFooter();
        return new String[] {firstName, lastName, emailAddress, password, highestQualification, occupation, employerDetail, mobileNumber};
    }
    

    public void registerConfirmation(String firstName, String lastName, String emailAddress, String highestQualification, String occupation, String employerDetail, String mobileNumber){
    /**
     * The message to confirm the registration
     * @param the info required for registration
     */
        this.displayHeader();
        this.displayMessageLn("Registration Process (2/2)");
        this.displayMessageLn("Please confirm your user details.");
        this.displayMessageLn("First Name: " + firstName);
        this.displayMessageLn("Last Name: " + lastName);
        this.displayMessageLn("Email Address: " + emailAddress);
        this.displayMessageLn("Highest Qualification: " + highestQualification);
        this.displayMessageLn("Occupation: " + occupation);
        this.displayMessageLn("Employer's details: " + employerDetail);
        this.displayMessageLn("Mobile number: " + mobileNumber);
    }


    public String[] getCreateConference() {
    /**
     * To get the required create conference information from the user
     * @return the information retrieved
     */
        this.displayHeader();
        this.displayMessageLn("Please enter the conference details.");
        this.displayMessageLn("Note: day and month must be two digits.");
        this.displayMessageLn("For example: 1/1/2021 must be 01/01/2021.");
        this.displayMessageLn("");
        this.displayMessage("Conference Name: ");
        String confName = scanner.nextLine(); // Read the user input
        this.displayMessage("Conference Place: ");
        String place = scanner.nextLine(); // Read the user input
        this.displayMessage("Conference Date (dd/mm/yyyy): ");
        String date = scanner.nextLine(); // Read the user input
        this.displayMessage("Paper Submission Due Date (dd/mm/yyyy): ");
        String submitDueDate = scanner.nextLine(); // Read the user input
        this.displayMessage("Paper Review Due Date (dd/mm/yyyy): ");
        String reviewDueDate = scanner.nextLine(); // Read the user input
        this.displayMessageLn("");
        this.displayMessageLn("");
        this.displayFooter();
        return new String[]{confName,place, date, submitDueDate, reviewDueDate};
    }


    public void createConfConfirmation(String confName, String place, String date, String submitDueDate, String reviewDueDate, String topic){
    /**
     * The message to confirm the conference information
     * @param the info required for conference
     */
        this.displayHeader();
        this.displayMessageLn("Please confirm the conference details.");
        this.displayMessageLn("Conference Name: " + confName);
        this.displayMessageLn("Conference Place:  " + place);
        this.displayMessageLn("Conference Date (dd/mm/yyyy): " + date);
        this.displayMessageLn("Paper Submission Due Date (dd/mm/yyyy): " + submitDueDate);
        this.displayMessageLn("Paper Review Due Date (dd/mm/yyyy): " + reviewDueDate);
        this.displayMessageLn("Topic Areas: " + topic);
    }


    public String[] getTopicAreas(ArrayList<String> topicAreas){
        /**
         * To get the topic areas from the user
         * @param the available topic
         * @return the topic areas retrieved
         */
        this.displayHeader();
        this.displayMessageLn("Please choose relevant the topic areas by entering their number index, separated by comma. ");
        this.displayMessageLn("For example, input: 1,2,3");
        // print our all topic areas available
        for (int i = 0; i < topicAreas.size() ; i++){
            this.displayMessageLn((i+1) + " . " + topicAreas.get(i));
        }
        this.displayMessage("Please enter your topic areas number index: ");
        String topicsInd = scanner.nextLine(); // Read the user input
        this.displayMessageLn("If your topics are not in the list, please type your topic areas here, separated by comma.");
        this.displayMessageLn("For example, input: Information Technology, Cybersecurity");
        this.displayMessage("Please enter your topic areas name: ");
        String topicsName = scanner.nextLine(); // Read the user input
        this.displayFooter();
        return new String[] {topicsInd,topicsName};
    }


    public void topicAreasConfirmation(String topic){
    /**
     * The message to confirm the topics areas information
     * @param the topic areas
     */
        this.displayHeader();
        this.displayMessageLn("The topics that fall into your area of expertise is/are:");
        this.displayMessageLn("");
        this.displayMessageLn("");
        this.displayMessageLn(topic);
        this.displayMessageLn("");
        this.displayMessageLn("");
    }

    public String getReviewer(ArrayList<String[]> availableReviewer){
        /**
         * To get the reviewer choices from the chair
         * @param the available reviewers
         * @return the reviewer choices
         */
        this.displayHeader();
        this.displayMessageLn("Please select 1 - 4 reviewers fro this paper:  ");
        this.displayMessageLn("For example, input: 1,2,3");
        this.displayMessageLn("");
        this.displayMessageLn("");
        // print our all reviewers available
        for (int i = 0; i < availableReviewer.size() ; i++){
            this.displayMessageLn((i+1) + " . " + availableReviewer.get(i)[1] +  "  " + availableReviewer.get(i)[2] + " (" + availableReviewer.get(i)[0] + ") ");
        }
        this.displayMessage("Please enter your reviewer number index: ");
        String reviewerInd = scanner.nextLine(); // Read the user input
        this.displayFooter();
        return reviewerInd;
    }


    public void reviewerConfirmation(ArrayList<String[]> reviewers, String pTitle) throws InterruptedException {
        /**
         * The message to confirm the reviewer assign
         * @param the reviewer
         */
        this.displayHeader();
        this.displayMessageLn("The reviewer(s) assigned to Paper " + pTitle + " is/are: ");
        this.displayMessageLn("");
        for (String[] r:reviewers){
            this.displayMessageLn(r[1] + " " + r[2] + " (" + r[0] + " )");
        }
        this.displayMessageLn("");
        TimeUnit.SECONDS.sleep(2);
    }


    public String[] getPaperSubmission(){
    /**
     * To get the paper submission info from user
     * @return the information retrieved
     */
        this.displayHeader();
        this.displayMessageLn("Please enter the title of your paper and the path of your paper to upload and submit your paper");
        this.displayMessageLn("[File format: PDF , Word only]");
        this.displayMessageLn("Example path: c:\\my_folder\\my_folder\\file.pdf");
        this.displayMessageLn("");
        this.displayMessage("Title: ");
        String title = scanner.nextLine(); // Read the user input
        this.displayMessage("Path: ");
        String path = scanner.nextLine(); // Read the user input
        this.displayMessage("");
        return new String[] {title,path};
    }


    public String getEvaluation(String pTitle){
    /**
     * To get the evaluation submitted by reviewer
     * @param the title of paper
     * @return the evaluation entered
     */
        this.displayHeader();
        this.displayMessageLn("Please enter you evaluation for paper " + pTitle + " .");
        this.displayMessageLn("");
        this.displayMessage("Evaluation: ");
        String evaluation = scanner.nextLine(); // Read the user input
        this.displayMessage("");
        return evaluation;
    }


    public void confirmEvaluation(String pTitle, String evaluation){
    /**
     * To get the evaluation submitted by reviewer
     * @param the title of paper
     * @return the evaluation entered
     */
        this.displayHeader();
        this.displayMessageLn("The evaluation for paper " + pTitle + " is: ");
        this.displayMessageLn("");
        this.displayMessageLn("Evaluation: ");
        this.displayMessageLn("");
        this.displayMessageLn(evaluation);
        this.displayMessageLn("");
        this.displayMessageLn("");
    }
}
