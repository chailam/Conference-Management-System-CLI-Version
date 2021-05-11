/**
 * The class consist of list of Paper, User and Conference.
 */

package cms.entity;

import java.util.*;


public class ConferenceManagementSystem {
    private ArrayList<User> userList = new ArrayList<User>();
    private ArrayList<Paper> paperList = new ArrayList<Paper>();
    private ArrayList<Conference> conferenceList = new ArrayList<Conference>();


    public void addUser(User u) {
        /**
         * To add user to the userList
         * @param user to be added the userList
         */
        userList.add(u);
    }


    public void addPaper(Paper p) {
        /**
         * To add paper to the paperList
         * @param paper to be added the paperList
         */
        paperList.add(p);
    }


    public void addConference(Conference c) {
        /**
         * To add conference to the conferenceList
         * @param conference to be added the conferenceList
         */
        conferenceList.add(c);
    }


    public boolean hasUser(String emailAddress) {
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


    public boolean hasPaper(String pTitle) {
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


    public boolean hasConference(String cName) {
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


    public User searchUser(String emailAddress) {
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


    public Paper searchPaper(String title) {
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


    public Conference searchConference(String name) {
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


    public User searchSpecificUser(String emailAddress, String role, String conference) {
        /**
         * To return specific user where emailAddress, role, and conference matching
         * @param email address of the user
         * @param role of the user in the conference
         * @param conference of the user participate
         * @return user who matched the information provided
         */
        for (User u : userList) {
            if (!u.getRole().equalsIgnoreCase("admin")) {
                NormalUser nu = (NormalUser) u;
                // if matching email address, role and conference, retrieve the index
                if (nu.getEmail().equals(emailAddress) && nu.getRole().equalsIgnoreCase(role) && nu.getConferenceName().equals(conference)) {
                    return nu;
                }
            }
        }
        return null;
    }


    public ArrayList<User> retrieveUserList() {
        /**
         * @return a list of user
         */
        return userList;
    }


    public ArrayList<Paper> retrievePaperList() {
        /**
         * @return a list of paper
         */
        return paperList;
    }


    public ArrayList<Conference> retrieveConferenceList() {
        /**
         * @return a list of conference
         */
        return conferenceList;
    }


    public ArrayList<String> getUserConference(String emailAddress) {
        /**
         * Search through the user to get the conference they participate.
         * @param the email address of that normal user
         * @return a list of conference name they participate
         */
        ArrayList<String> userConf = new ArrayList<String>();
        for (User u : userList) {
            if (u.getEmail().equals(emailAddress)) {
                // admin is the only User type and admin has no conference to participate
                String confName = ((NormalUser) u).getConferenceName();
                if (confName != null) {
                    userConf.add(((NormalUser) u).getConferenceName());
                }
            }
        }
        return userConf;
    }


    public String getUserConferenceRole(String emailAddress, String conferenceName) {
        /**
         * Search through the user to get the role of the conference they participate.
         * This logi cworks because the specification mentioned although user can has many roles in different conferences,
         * but user only has one role in one conference.
         * @param the email address of that normal user
         * @param the conference name
         * @return the role of that user in the conference
         */
        String role = null;
        for (User u : userList) {
            if (!u.getRole().equalsIgnoreCase("admin")) {
                NormalUser nu = (NormalUser) u;
                if ((nu.getEmail().equals(emailAddress)) && (nu.getConferenceName() != null)) {
                    if (nu.getConferenceName().equals(conferenceName)) {
                        role = nu.getRole();
                    }
                }
            }
        }
        return role;
    }
}
