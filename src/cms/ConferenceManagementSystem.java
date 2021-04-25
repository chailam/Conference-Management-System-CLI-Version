/**
 * The class consist of list of Paper, User and Conference.
 */


package cms;

import cms.*;
import java.util.*;


public class ConferenceManagementSystem {
    private ArrayList<User> userList = new ArrayList<User>();
    private ArrayList<Paper> paperList= new ArrayList<Paper>();
    private ArrayList<Conference> conferenceList = new ArrayList<Conference>();



    public void addUser(User u){
    /**
     * To add user to the userList
     * @param user to be added the userList
     */
        userList.add(u);
    }


    public void addPaper(Paper p){
    /**
     * To add paper to the paperList
     * @param paper to be added the paperList
     */
        paperList.add(p);
    }


    public void addConference(Conference c){
    /**
     * To add conference to the conferenceList
     * @param conference to be added the conferenceList
     */
        conferenceList.add(c);
    }


    public boolean hasUser(String emailAddress){
    /**
     * To check whether the user is in the userList
     * @param user email address to be checked
     * @return true if exists, false otherwise
     */
        for (User u : userList) {
            if (u.getEmail().equals(emailAddress)) {
                return true;
            }
        }
        return false;
    }


    public boolean hasPaper(String pTitle){
    /**
     * To check whether the paper is in the paperList
     * @param paper title to be checked
     * @return true if exists, false otherwise
     */
        for (Paper p : paperList) {
            if (p.getTitle().equals(pTitle)) {
                return true;
            }
        }
        return false;
    }


    public boolean hasConference(String cName){
    /**
     * To check whether the conference is in the conferenceList
     * @param conference name to be checked
     * @return true if exists, false otherwise
     */
        for (Conference c : conferenceList) {
            if (c.getName().equals(cName)) {
                return true;
            }
        }
        return false;
    }    


    public User searchUser(String emailAddress){
    /**
     * To return user where emailAddress (unique) matching
     * @param email address of the user, it has to be unique so that user can login
     * @return user who matched the email address
     */
        for (User u : userList) {
            if (u.getEmail().equals(emailAddress)) {
                return u;
            }
        }
        return null;
    }


    public Paper searchPaper(String title){
    /**
     * To return user where title of the paper (unique) matching
     * @param title of the paper
     * @return paper which matched the title
     */
        for (Paper p : paperList) {
            if (p.getTitle().equals(title)) {
                return p;
            }
        }
        return null;
    }

    
    public Conference searchConference(String name){
    /**
     * To return user where name of the conference (unique) matching     
     * @param name of the conference
     * @return conference which matched the name
     */
        for (Conference c : conferenceList) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }


    public ArrayList<User> retrieveUserList(){
    /**
     * @return a list of user
     */
        return userList;
    }


    public ArrayList<Paper> retrievePaperList(){
    /**
     * @return a list of paper
     */
        return paperList;
    }


    public ArrayList<Conference> retrieveConferenceList(){
    /**
     * @return a list of conference
     */
        return conferenceList;
    }


    public ArrayList<String> getUserConference(String emailAddress){
    /**
     * Search through the user to get the conference they participate.
     * @param the email address of that normal user
     * @return a list of conference name they participate
     */
        ArrayList<String> userConf = new ArrayList<String>();
        for (User u: userList){
            if (u.getEmail().equals(emailAddress)){
                // admin is the only User type and admin has no conference to participate
                userConf.add(((NormalUser)u).getConferenceName());
            }
        }
        return userConf;
    }


    public String getUserConferenceRole (String emailAddress, String conferenceName){
    /**
     * Search through the user to get the role of the conference they participate.
     * @param the email address of that normal user
     * @param the conference name 
     * @return the role of that user in the conference
     */
        String role = null;
        for (User u: userList){
            if (u.getEmail().equals(emailAddress) && ((NormalUser)u).getConferenceName().equals(conferenceName)){
                role = ((NormalUser)u).getRole();
            }
        }
        return role;
    }
}
