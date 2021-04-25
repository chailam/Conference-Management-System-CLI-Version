/**
 * The controller class to handle the business logic.
 */
package cms;

import cms.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets; 
import java.math.BigInteger; 


public class CMSController{
    // The user interface invoked
    UserInterface ui = new UserInterface();

    // Initialize the ConferenceManagementSystem
    ConferenceManagementSystem cmsSystem = new ConferenceManagementSystem();

    List<String> avaiableTopics = new ArrayList<>(Arrays.asList("Artificial Intelligence", "Human Computer Interaction", "Data Mining & Information Retrieval", "Image Processing", "Big Data", "Computer Networks", "Software Engineering ", "Security & Cryptography", "Robotics & Automation", "Database & Information Systems"));
    List<String> availableProgressStatus = new ArrayList<>(Arrays.asList("Review Due", "Being Reviewed", "Reviewed", "Rejected", "Accepted"));
    
    // Main page, Home Page, Admin, Chair, Author, Reviewer option
    // The option shown here are just the functionalities assigned by tutor.
    // The option for not assigned functionality are not shown here
    String[] mainPageOp = {"Register", "Login"};
    String[] homePageOp = {"Manage Your Conference","Create Conference"};
    String[] adminOp = {"Retrieve User Information", "Retrieve Conference Information","Logout"};
    String[] authorOp = {"Submit Paper", "Previous"};
    String[] chairOp = {"Final Decision on Paper", "Assign Reviewer to Paper","Previous"};
    String[] reviewerOp = {"Submit Evaluation of Paper","Previous"};
    
    // Define the path to NormalUser.csv, Conference.csv, and Paper.csv
    String p2NormalUser = "../resource/NormalUser.csv";
    String p2Conference = "../resource/Conference.csv";
    String p2Paper = "../resource/Paper.csv";


    public CMSController(){
        /**
         * The constructor of the controller.
         * It called function to initialize the ConferenceManagementSystem.
         */

        this.initializeConferenceManagementSystem();

        // /////////TESTTTTTTTTTTTTTTTT///
        // String op = ui.getUserOption(adminOp);

    
        // System.out.println("option selecteddddddd: "+op);

        // ArrayList<Conference> testC = new ArrayList<>();
        // ArrayList<Paper> testP = new ArrayList<>();
        // ArrayList<User> testU = new ArrayList<>();
        // testC = cmsSystem.retrieveConferenceList();
        // testP = cmsSystem.retrievePaperList();
        // testU = cmsSystem.retrieveUserList();

        // System.out.println(testC);
        // System.out.println(testP);
        // System.out.println(testU);
        // ///////************TEST END */
        
    }

    //***********************************Kick Start Method********************************************************** */
    private void run(){
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
                    adminPage();
                }
                else if (u.getRole().equals("Chair")){
                    // TODO: chairPage function will be here!!
                }
                else if (u.getRole().equals("Author")){
                    // TODO: authorPage function will be here!!
                }
                else if (u.getRole().equals("Reviewer")){
                    // TODO: reviewerPage function will be here!!
                }
                



            }
        }
        catch (NoSuchAlgorithmException | InterruptedException e) { 
            System.out.println("Exception: " + e); 
        }
    }


    private void adminPage(){
        String op = ui.getUserOption(adminOp, "Admin");
        if (op.equals("Retrieve User Information")){
            //TODO: get the userlist and use UserInterface displayResult method to print out the information
            // remember to print the header and footer as shown in other method


            // if exit is entered, return to admin page
            if (ui.getExitCommand() == true){
                this.adminPage();
            }

        }
        else if (op.equals("Retrieve Conference Information")){
            //TODO: get the userlist and use UserInterface displayResult method to print out the information
            // remember to print the header and footer as shown in other method


            // if exit is entered, return to admin page
            if (ui.getExitCommand() == true){
                this.adminPage();
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
            u = this.cmsSystem.searchUser(emailAddress);

            // if the user exists, validate password
            if (u != null){
                String hashedPassword = this.hashSHA256(password);
                if (hashedPassword.equalsIgnoreCase(u.getPassword())){
                // set choice to be true as user is authenticated
                authenticated = true;
                }
            }
            // if not, loop the authenticate method
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



//***********************************Utility Method********************************************************** */
    private String hashSHA256(String text) throws NoSuchAlgorithmException{
    /**
     * This method is used to hashed the text, mainly the password, using SHA256. 
     * @param the text to be hashed
     * @return the hashed text
     */
        // Use the SHA256
        MessageDigest md = MessageDigest.getInstance("SHA-256"); 
        byte[] hashedByte = md.digest(text.getBytes(StandardCharsets.UTF_8));
        BigInteger theNumber = new BigInteger(1, hashedByte); 
        StringBuilder hashedText = new StringBuilder(theNumber.toString(16));
        // Pad with leading zeros
        while (hashedText.length() < 32){ 
            hashedText.insert(0, '0'); 
        } 
        return hashedText.toString(); 
    }


    public ArrayList<String> stringToArrayList (String longString, String delimit){
        /**
         * To change the split the list using delimit specify and then convert to ArrayList
         * @param the string to be splitted into array list
         * @param the delimit to be used to split the string
         * @return the arraylist or null
         */
            String tmp [];
            ArrayList<String> theArrayList = new ArrayList<String>();
            tmp = longString.split(delimit);
            for (String s : tmp){
                theArrayList.add(s);
            }
            if (theArrayList.size() > 0){
                return theArrayList;
            }
            else{
                return null;
            }
        }


//***********************************Create Entity Method********************************************************** */

    private User createUserEntity (String role, String emailAddress, String hashedPassword, String firstName, String lastName, String highestQualification, String occupation, String employerDetail, String mobileNumber, String conference, ArrayList<String>topicAreas, ArrayList<String>paper){
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


    private Conference createConferenceEntity (String name, String place, ArrayList<String> topic, Date date, Date submissionDue, Date reviewDue){
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


    private Paper createPaperEntity (String title, String author, String content, int noOfReviewer, ArrayList<String> evaluation, String conferenceName, ArrayList<String> topic, String progress){
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

//***********************************Write to CSV Method********************************************************** */




//************************Read CSV to initialize ConferenceManagementSystem Method**************************************** */
    private ArrayList<String> processCSV (String filePath){
    /**
     * To read the data from csv file (as database) and put the data to the arraylist structure
     * @param the file path of the csv file to be read
     */
        String currentLine;
        ArrayList<String> csvData = new ArrayList<String>();
        try {
            java.net.URL url = getClass().getResource(filePath);
            FileReader fileReader = new FileReader(url.getPath());
            BufferedReader bufReader = new BufferedReader(fileReader);
            int counter = 0;  // not reading the first line

            while((currentLine = bufReader.readLine()) != null) {
                if (counter != 0){
                    csvData.add(currentLine);
                }
                counter += 1;
            }
            bufReader.close();
        }
        catch (Exception e){
            System.out.println("File Error: " + e);
        }
        return csvData;
    }


    private void initializeConferenceManagementSystem(){
        /**
         * It initialized the ConferenceManagementSystem, to read all data from csv file, 
         * which act as database, and create entity for each of them.
         */
        ArrayList<String> resultUser = processCSV (this.p2NormalUser);
        ArrayList<String> resultConference = processCSV (this.p2Conference);
        ArrayList<String> resultPaper = processCSV (this.p2Paper);

        String delimit = ",";
        String [] tmp;
        try {
            // Add the User Entity from csv file to object list
            if (resultUser.isEmpty() == false){
                for (String line : resultUser){
                    tmp = line.split(delimit);
                    // Change the topicAreas and paper to ArrayList
                    ArrayList<String> topic = stringToArrayList(tmp[10],"/");
                    ArrayList<String> paper = stringToArrayList(tmp[11],"/");
                    // Create user entity and add into the userList
                    User u = createUserEntity(tmp[0],tmp[1],tmp[2],tmp[3],tmp[4],tmp[5],tmp[6],tmp[7],tmp[8],tmp[9],topic,paper);
                    if (u != null){
                        this.cmsSystem.addUser(u);
                    }
                }
            }
            // Add the Conference Entity from csv file to object list
            if (resultConference.isEmpty() == false){
                for (String line : resultConference){
                    tmp = line.split(delimit);
                    // Change the data to Date format
                    Date date = new SimpleDateFormat("dd/MM/yyyy").parse(tmp[3]);
                    Date submitDueDate = new SimpleDateFormat("dd/MM/yyyy").parse(tmp[4]);
                    Date reviewDueDate = new SimpleDateFormat("dd/MM/yyyy").parse(tmp[5]);
                    // Change the topicAreas to ArrayList
                    ArrayList<String> topic = stringToArrayList(tmp[2],"/");
                    // Create conference entity and add into the conferenceList
                    Conference c = createConferenceEntity(tmp[0],tmp[1],topic,date,submitDueDate,reviewDueDate);
                    if (c != null){
                        this.cmsSystem.addConference(c);
                    }
                }
            }
            // Add the Paper Entity from csv file to object list
            if (resultPaper.isEmpty() == false){
                for (String line : resultPaper){
                    tmp = line.split(delimit);
                    // Change the topicAreas to ArrayList
                    ArrayList<String> topic = stringToArrayList(tmp[6],"/");
                    // Change the evaluation to ArrayList
                    ArrayList<String> evaluation = stringToArrayList(tmp[4],"/");
                    Paper p = createPaperEntity(tmp[0],tmp[1],tmp[2],Integer.parseInt(tmp[3]),evaluation,tmp[5],topic,tmp[7]);
                    if (p != null){
                        this.cmsSystem.addPaper(p);
                    }
                }
            }
        }
        catch (Exception e){
            System.out.println("File Error: " + e);
        }
    }


//***********************************Main**************************************** */

    public static void main(String args[]) {
        CMSController cmsC = new CMSController();
        cmsC.run();


        }
}
