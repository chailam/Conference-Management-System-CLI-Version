package cms;


import java.util.*;

public abstract class Controller {

    // Initialize the ConferenceManagementSystem class
    protected static ConferenceManagementSystem cms = new ConferenceManagementSystem();

    //Instantiate utility class
    protected Utility ut = new Utility();

    // Define the path to NormalUser.csv, Conference.csv, and Paper.csv
    protected String pathNormalUserCSV = "../resource/NormalUser.csv";
    protected String pathConferenceCSV = "../resource/Conference.csv";
    protected String pathPaperCSV = "../resource/Paper.csv";


    protected User createUserEntity (String role, String emailAddress, String hashedPassword, String firstName, String lastName, String highestQualification, String occupation, String employerDetail, String mobileNumber, String conference, ArrayList<String> topicAreas, ArrayList<String>paper){
        /**
         * To create the User entity 
         * @param all the user data 
         * @return user object created
         */
            User u = null;
            try {
                // if is admin
                if (role.equals("admin")){
                    u = new Admin("Admin",emailAddress,hashedPassword);
                } 
                // if is chair
                else if (role.equals("chair")){
                    u = new Chair("Chair",emailAddress,hashedPassword,firstName,lastName,highestQualification,occupation,employerDetail,mobileNumber,conference);
                }
                // if is reviewer
                else if (role.equals("reviewer")){
                    u = new Reviewer("Reviewer",emailAddress,hashedPassword,firstName,lastName,highestQualification,occupation,employerDetail,mobileNumber,conference);
                    if (topicAreas != null){
                        ((Reviewer)u).setTopicArea(topicAreas);
                    }
                    if (paper != null){
                        ((Reviewer)u).setAssignedPaper(paper);
                    }
                }
                // if is author
                else if (role.equals("author")){
                    u = new Author("Author",emailAddress,hashedPassword,firstName,lastName,highestQualification,occupation,employerDetail,mobileNumber,conference);
                    if (paper != null){
                        ((Author)u).setPaper(paper);
                    }
                }
                // if normal user
                else if (role.equals("normal")){
                    u = new NormalUser("Normal",emailAddress,hashedPassword,firstName,lastName,highestQualification,occupation,employerDetail,mobileNumber);
                }
            }
            catch (Exception e){
                System.out.println("User Entity Create Error: " + e);
            }
            return u;
        }
    

    
    protected Conference createConferenceEntity (String name, String place, ArrayList<String> topic, Date date, Date submissionDue, Date reviewDue){
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
