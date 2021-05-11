package cms;


import java.time.LocalDate;
import java.util.*;

public abstract class Controller {

    // Initialize the ConferenceManagementSystem class
    protected static ConferenceManagementSystem cms = new ConferenceManagementSystem();

    //Instantiate utility class
    protected Utility ut = new Utility();

    // Define the path to User.csv, Conference.csv, and Paper.csv
    protected String pathUserCSV = "src/resource/User.csv";
    protected String pathConferenceCSV = "src/resource/Conference.csv";
    protected String pathPaperCSV = "src/resource/Paper.csv";


    protected User createUserEntity (String role, String emailAddress, String hashedPassword, String firstName, String lastName, String highestQualification, String occupation, String employerDetail, String mobileNumber, String conference, ArrayList<String> topicAreas, ArrayList<String>paper){
    /**
     * To create the User entity
     * @param all the user data
     * @return user object created
     */
        User u = null;
        try {
            // if is admin
            if (role.equalsIgnoreCase("admin")){
                u = new Admin("admin",emailAddress,hashedPassword);
            }
            // if is chair
            else if (role.equalsIgnoreCase("chair")){
                u = new Chair("chair",emailAddress,hashedPassword,firstName,lastName,highestQualification,occupation,employerDetail,mobileNumber,conference);
            }
            // if is reviewer
            else if (role.equalsIgnoreCase("reviewer")){
                u = new Reviewer("reviewer",emailAddress,hashedPassword,firstName,lastName,highestQualification,occupation,employerDetail,mobileNumber,conference);
                if (topicAreas != null){
                    ((Reviewer)u).setTopicArea(topicAreas);
                }
                if (paper != null){
                    ((Reviewer)u).setAssignedPaper(paper);
                }
            }
            // if is author
            else if (role.equalsIgnoreCase("author")){
                u = new Author("author",emailAddress,hashedPassword,firstName,lastName,highestQualification,occupation,employerDetail,mobileNumber,conference);
                if (paper != null){
                    ((Author)u).setPaper(paper);
                }
            }
            // if normal usernormal user
            else if (role.equalsIgnoreCase("normal")){
                u = new NormalUser("normal",emailAddress,hashedPassword,firstName,lastName,highestQualification,occupation,employerDetail,mobileNumber);
            }
        }
        catch (Exception e){
            System.out.println("User Entity Create Error: " + e);
        }
        return u;
    }


    public boolean setupNormalUserEntity (String emailAddress, String role, String confName, ArrayList<String> topics) {
    /**
     * Add the user entity into userList, then write to csv file
     * @param the user data
     * @return true is all successful; false otherwise
     */
        // Search the user
        User u = cms.searchUser(emailAddress);
        // check if it is admin
        if (!u.getRole().equalsIgnoreCase("admin")) {
            // Set the user type to normal user
            NormalUser nu = (NormalUser) u;
            User createdU = null;
            String[] userData = new String[12];
            // if it is reviewer
            if (nu.getRole().equalsIgnoreCase("reviewer")) {
                // Create an User entity and data with topic area
                createdU = createUserEntity(role, u.getEmail(), u.getPassword(), nu.getFirstName(), nu.getLastName(), nu.getHighestQualification(), nu.getOccupation(), nu.getEmployerDetail(), nu.getMobileNumber(), confName, topics, null);
                userData = new String[]{role, u.getEmail(), u.getPassword(), nu.getFirstName(), nu.getLastName(), nu.getHighestQualification(), nu.getOccupation(), nu.getEmployerDetail(), nu.getMobileNumber(), confName, ut.arrayListToString(topics, "/"), null};
            }
            else if (nu.getRole().equalsIgnoreCase("author") || nu.getRole().equalsIgnoreCase("chair")) {
                // Create an User entity and data without topic area
                createdU = createUserEntity(role, u.getEmail(), u.getPassword(), nu.getFirstName(), nu.getLastName(), nu.getHighestQualification(), nu.getOccupation(), nu.getEmployerDetail(), nu.getMobileNumber(), confName, null, null);
                userData = new String[]{role, u.getEmail(), u.getPassword(), nu.getFirstName(), nu.getLastName(), nu.getHighestQualification(), nu.getOccupation(), nu.getEmployerDetail(), nu.getMobileNumber(), confName, null, null};
            }
            if (createdU != null){
                // Add user to the list
                cms.addUser(createdU);
                // write to csv file
                ut.writeCSV(pathUserCSV, userData, true);
            }
            else{
                System.out.println("User is null in setupNormalUserEntity?!");
                return false;
            }
            return true;
        }
        else{
            System.out.println("User is admin in setupNormalUserEntity?!");
            return false;
        }
    }
    

    
    protected Conference createConferenceEntity (String name, String place, ArrayList<String> topic, LocalDate date, LocalDate submissionDue, LocalDate reviewDue){
        /**
         * To create the Conference entity
         * @param the user data
         * @return conference object created 
         */
        Conference c = null;
        try {
            // Create a conference class
            c = new Conference(name,place,topic,date,submissionDue,reviewDue);
        }
        catch (Exception e){
            System.out.println("Conference Entity Create Error: " + e);
            }
        return c;
    }



    protected Paper createPaperEntity (String title, String author, String content, int noOfReviewer, ArrayList<String> evaluation, String conferenceName, ArrayList<String> topic, String progress){
        /**
         * To create the Paper entity and added to the Paper array list
         * @param the list of user data 
         * @return paper object created
         */
        Paper p = null;
        try {
            // Create a paper object
            p = new Paper(title,author,content,noOfReviewer,evaluation,conferenceName,topic,progress);
        }
        catch (Exception e){
                System.out.println("Paper Entity Create Error: " + e);
            }
        return p;
    }
}
