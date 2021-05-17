/**
 * Worker controller for the User related function.
 */
package cms.controller;

import cms.Utility;
import cms.entity.*;
import cms.view.UserInterface;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    protected ConferenceManagementSystem cms;
    protected UserInterface ui;
    protected String pathUserCSV;
    private Utility ut = new Utility();

    public UserController(ConferenceManagementSystem cms, UserInterface ui, String pathUserCSV) {
        this.cms = cms;
        this.ui = ui;
        this.pathUserCSV = pathUserCSV;
        this.importAllUserCSV();
    }


    protected void addUserEntity(String role, String emailAddress, String hashedPassword, String firstName, String lastName, String highestQualification, String occupation, String employerDetail, String mobileNumber, String conference, ArrayList<String> topicAreas, ArrayList<String> paper) {
        /**
         * Create User object and add to the cms user list
         * @param the user information required
         */
        User u = null;
        try {
            if (role.equalsIgnoreCase("admin")) {
                // if is admin
                u = new Admin("admin", emailAddress, hashedPassword);
            } else if (role.equalsIgnoreCase("chair")) {
                // if is chair
                u = new Chair("chair", emailAddress, hashedPassword, firstName, lastName, highestQualification, occupation, employerDetail, mobileNumber, conference);
            } else if (role.equalsIgnoreCase("reviewer")) {
                // if is reviewer
                u = new Reviewer("reviewer", emailAddress, hashedPassword, firstName, lastName, highestQualification, occupation, employerDetail, mobileNumber, conference);
                if (topicAreas != null) {
                    // if user has topic
                    ((Reviewer) u).setTopicArea(topicAreas);
                }
                if (paper != null) {
                    // if user has paper
                    ((Reviewer) u).setAssignedPaper(paper);
                }
            } else if (role.equalsIgnoreCase("author")) {
                // if is author
                u = new Author("author", emailAddress, hashedPassword, firstName, lastName, highestQualification, occupation, employerDetail, mobileNumber, conference);
                if (paper != null) {
                    ((Author) u).setPaper(paper);
                }
            } else if (role.equalsIgnoreCase("normal")) {
                // if is normal user
                u = new NormalUser("normal", emailAddress, hashedPassword, firstName, lastName, highestQualification, occupation, employerDetail, mobileNumber);
            }
            if ( u != null){
                cms.addUser(u);
            } else {
                System.out.println("Error: User is null in createUserEntity.");
            }
        } catch (Exception e) {
            System.out.println("Exception: createUserEntity - " + e);
        }
    }


    private void importAllUserCSV() {
        /**
         * Import all the user data from csv file
         */
        List<String[]> resultUser = ut.readCSV(pathUserCSV);
        try {
            // Add the User Entity from csv file to object list
            if (resultUser.isEmpty() == false) {
                for (String[] line : resultUser) {
                    // Change the topicAreas and paper to ArrayList
                    ArrayList<String> topic = ut.stringToArrayList(line[10], "/");
                    ArrayList<String> paper = ut.stringToArrayList(line[11], "/");
                    // Create user entity and add into the userList
                    addUserEntity(line[0], line[1], line[2], line[3], line[4], line[5], line[6], line[7], line[8], line[9], topic, paper);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception: importAllUserCSV - " + e);
        }
    }


    protected void appendUserCSV (String role, String emailAddress, String hashedPassword, String firstName, String lastName, String highestQualification, String occupation, String employerDetail, String mobileNumber, String conference, String topics, String papers){
        /**
         * Append the user data to the csv file
         * @param the data to be appended
         */
        String[] userData = {role, emailAddress, hashedPassword, firstName, lastName, highestQualification, occupation, employerDetail, mobileNumber, conference, topics, papers};
        // true because append not overwrite
        ut.writeCSV(pathUserCSV, userData,true);
    }


    protected ArrayList<String> retrieveUpdatedUserPaperInfo(String emailAddress, String role, String confName, String paperTitle){
        /**
         * To update the user information in cms userlist to include the paper submitted/assigned for review
         * @param email address of the author/reviewer
         * @param role of the user (author/reviewer)
         * @param conference name of the user joined
         * @param title of paper
         * @return a list of papers submitted/assigned for review
         */
        // find that specific user with unique email address, role, and conference name
        User u = cms.searchSpecificUser(emailAddress, role, confName);
        ArrayList<String> papers = new ArrayList<String>();
        if (u != null){
            if (u.getRole().equalsIgnoreCase("reviewer")){
                // if the user is reviewer
                Reviewer ru = (Reviewer) u;
                System.out.println(ru.retrieveAssignedPaper());
                System.out.println(ru.retrieveAssignedPaper().size());
                if (ru.retrieveAssignedPaper().size() == 0) {
                    // if assigned paper is empty, set the assigned paper
                    papers.add(paperTitle);
                    ru.setAssignedPaper(papers);
                } else {
                    // if reviewer has existing assigned paper, add paper
                    ru.addAssignedPaper(paperTitle);
                }
                papers = ru.retrieveAssignedPaper();
                return papers;
            } else if (u.getRole().equalsIgnoreCase("author")){
                // if the user is author
                Author au = (Author) u;
                if (au.retrievePaper().size() == 0){
                    // if paper is empty, set the paper
                    papers.add (paperTitle);
                    au.setPaper(papers);

                } else {
                    // if author submitted another paper already, add the paper
                    au.addPaper(paperTitle);
                }
                papers = au.retrievePaper();
                return papers;
            }
        } else {
            System.out.println("Error: User is null in updateUserPaperInfo.");
        }
        return papers;
    }


    protected void updateUserCsv(String filePath, String dataToUpdate, int dataIndex, String emailAddress, String role, String confName){
        /**
         *  The method to update user data in csv file at specific col and row
         * @param the file path of the file to update
         * @param the data to update to file
         * @param the index of the data to be modified
         * @param the matching string
         * @param the matching string
         */
        try {
            // read the data
            File theFile = new File(filePath);
            CSVReader csvReader = new CSVReader(new FileReader(theFile));
            List<String[]> csvData = csvReader.readAll();

            // get data to be replaced  at row (i) and column
            for (int i = 0; i < csvData.size() ; i++){
                String[] string = csvData.get(i);
                //col index 0 is role, col index 1 is email and col index 9 is conference name
                if(string[0].equalsIgnoreCase(role) && string[1].equalsIgnoreCase(emailAddress) && string[9].equals(confName)){
                    string[dataIndex] = dataToUpdate;
                }
            }
            csvReader.close();
            // Write to the CSV file
            CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath));
            csvWriter.writeAll(csvData);
            csvWriter.flush();
            csvWriter.close();
        } catch (Exception e){
            System.out.println("User File Write Error: " + e);
        }
    }


    protected boolean checkRegistrationInfo (String firstName, String lastName, String emailAddress, String password, String highestQualification, String occupation, String employerDetail, String mobileNumber) throws InterruptedException {
        /**
         *  Check the user registration information
         * @param the registration information to be checked
         * @return true if data valid; false otherwise
         */
        // check if data input is zero/null
        if ((firstName.length() == 0) || (lastName.length() == 0) || (emailAddress.length() == 0) || (highestQualification.length() == 0) || (occupation.length() == 0) || (employerDetail.length() == 0) || (mobileNumber.length() == 0)) {
            ui.displayMsgWithSleep("Information could not be empty.");
            return false;
        }
        // check validity of email
        if (ut.emailValidator(emailAddress) == false) {
            ui.displayMsgWithSleep("The email address format is wrong.");
            return false;
        }
        //checking password requirement
        if (ut.passwordValidator(password) == false) {
            ui.displayMsgWithSleep("Password must at least 8 characters long, must include at least 1 upper case,\n\t lower case, 1 number. Please try again.");
            return false;
        }
        // if user exists
        if (cms.hasUser(emailAddress) == true) {
            ui.displayMsgWithSleep("The user already exists.\n");
            return false;
        }
        return true;
    }


    protected boolean checkAuthInfo(String emailAddress, String password) throws NoSuchAlgorithmException, InterruptedException {
        /**
         *  Check the user authentication information
         * @param the authentication information to be checked
         * @return true if authenticated; false otherwise
         */
        boolean flag = false;
        String hashedPassword = ut.hashSHA256(password);
        // check is user has that email address, retrieve that user
        if (cms.hasUser(emailAddress)){
            User u = cms.searchUser(emailAddress);
            // if the hashed password match, authentication become true
            if (hashedPassword.equalsIgnoreCase(u.getPassword())){
                flag = true;
            } else {
                // password incorrect
                ui.displayMsgWithSleep("User is not valid.\n\tPlease login again or enter contact admin for any questions.");
                flag = false;
            }
        } else {
            // user does not exist
            ui.displayMsgWithSleep("User is not valid.\n\tPlease login again or enter contact admin for any questions.");
            flag = false;
        }
        return flag;
    }
}





