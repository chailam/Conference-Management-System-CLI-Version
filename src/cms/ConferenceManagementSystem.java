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

    public ConferenceManagementSystem(){
        /**
         * Constructor for the ConferenceManagementSystem class.
         * 
         **/
    }


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


    public boolean hasUser(User u){
    /**
     * To check whether the user is in the userList
     * @param user to be checked
     */
        return userList.contains(u);
    }


    public boolean hasPaper(Paper p){
    /**
     * To check whether the paper is in the paperList
     * @param paper to be checked
     */
        return paperList.contains(p);
    }


    public boolean hasConference(Conference c){
    /**
     * To check whether the conference is in the conferenceList
     * @param conference to be checked
     */
        return conferenceList.contains(c);
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


    public void printUser(){
    //TODO: to be implemented! should it be here? or in controller?
    /**
     * To print the list of users 
     */
    }


    public void printPaper(){
    //TODO: to be implemented! should it be here? or in controller?
    /**
     * To print the list of papers 
     */
    }


    public void printConference(){
    //TODO: to be implemented! should it be here? or in controller?
    /**
     * To print the list of conferences 
     */

    }

}
