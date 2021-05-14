package cms.controller;

import cms.Utility;
import cms.entity.*;
import cms.view.UserInterface;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserController {
    ConferenceManagementSystem cms;
    UserInterface ui;
    String pathUserCSV;
    Utility ut = new Utility();

    public UserController(ConferenceManagementSystem cms, UserInterface ui, String pathUserCSV) {
        this.cms = cms;
        this.ui = ui;
        this.pathUserCSV = pathUserCSV;
        this.importAllUserCSV();
    }


    public void createUserEntity(String role, String emailAddress, String hashedPassword, String firstName, String lastName, String highestQualification, String occupation, String employerDetail, String mobileNumber, String conference, ArrayList<String> topicAreas, ArrayList<String> paper) {
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


    public void importAllUserCSV() {
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
                    createUserEntity(line[0], line[1], line[2], line[3], line[4], line[5], line[6], line[7], line[8], line[9], topic, paper);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception: importAllUserCSV - " + e);
        }
    }


    public void appendUserCSV (String role, String emailAddress, String hashedPassword, String firstName, String lastName, String highestQualification, String occupation, String employerDetail, String mobileNumber, String conference, String topics, String papers){
        /**
         * Append the user data to the csv file
         * @param the data to be appended
         */
        String[] userData = {role, emailAddress, hashedPassword, firstName, lastName, highestQualification, occupation, employerDetail, mobileNumber, conference, topics, papers};
        // true because append not overwrite
        ut.writeCSV(pathUserCSV, userData,true);
    }


    public boolean checkRegistrationInfo (String firstName, String lastName, String emailAddress, String password, String highestQualification, String occupation, String employerDetail, String mobileNumber) throws InterruptedException {
        /**
         *  Check the user registration information
         * @param the registration information to be checked
         * @return true if data valid; false otherwise
         */
        //checking password requirement
        if (ut.passwordValidator(password) == false) {
            ui.displayMsgWithSleep("Password must at least 8 characters long,\n\tmust include at least 1 upper case,\n\t lower case, 1 number.\n\tPlease try again.");
            return false;
        }
        // check validity of email
        if (ut.emailValidator(emailAddress) == false) {
            ui.displayMsgWithSleep("The email address format is wrong.");
            return false;
        }
        // check if data input is zero/null
        if ((firstName.length() == 0) || (lastName.length() == 0) || (emailAddress.length() == 0) || (highestQualification.length() == 0) || (occupation.length() == 0) || (employerDetail.length() == 0) || (mobileNumber.length() == 0)) {
            ui.displayMsgWithSleep("Information could not be empty.");
            return false;
        }
        // if user exists
        if (cms.hasUser(emailAddress) == true) {
            ui.displayMsgWithSleep("The user already exists.\n");
            return false;
        }
        return true;
    }


    public boolean checkAuthInfo(String emailAddress, String password) throws NoSuchAlgorithmException, InterruptedException {
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
            if (hashedPassword.equals(u.getPassword())){
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





