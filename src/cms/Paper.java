/**
 * Class that represents a Paper in the system.
 * 
**/

package cms;

import java.util.*;
import cms.Conference;


public class Paper {
    private String title;
    private String author;
    private String content;
    private int noOfReviewer;
    private ArrayList<String> evaluation = new ArrayList<String>();
    private Conference conference;
    private ArrayList<String> chosenTopicAreas= new ArrayList<String>();
    public ArrayList<String> availableProgressStatus = new ArrayList<String>(){
        {
            add("Review Due");
            add("Being Reviewed");
            add("Reviewed");
            add("Rejected");
            add("Accepted");
        }
    };
    private String currentProgressStatus;

    public Paper(String title, String author, Conference conference, ArrayList<String> topic){
    /**
	 * Constructor for the Paper.
     * @param name is the name of the paper
     * @param author is the author of the paper in full name
     * @param conference is the conference where the paper submitted
     * @param topic is the topic areas of the paper
     **/
        title = this.title;
        author = this.author;
        conference = this.conference;
        topic = this.chosenTopicAreas;
        this.currentProgressStatus = "Being Reviewed";
    }


    public String getTitle(){
    /**
     * Getter for title of the paper
     * @return 	the title of the paper
     **/
        return this.title;
    }


    public String getAuthor(){
    /**
     * Getter for author of the paper in full name
     * @return 	the  author of the paper in full name
     **/
        return this.author;
    }


    public Conference getConference(){
    /**
     * Getter for conference where the paper submitted
     * @return 	the conference where the paper submitted
     **/
        return this.conference;
    }


    public String getContent(){
    /**
     * Getter for content of the paper
     * @return 	the content of the paper
     **/
        return this.content;
    }


    public int getNoOfReviewer(){
    /**
     * Getter for the number of reviewer of paper
     * @return 	the number of reviewer of paper
     **/
        return this.noOfReviewer;
    }


    public String getProgressStatus(){
    /**
     * Getter for current progress of paper
     * @return 	the current progress of paper
     **/
        return this.currentProgressStatus;
    }


    public ArrayList<String> retrieveTopicAreas(){
    /**
     * Retrieve the chosen topic areas
     * @return 	the chosen topic areas
     **/
        return this.chosenTopicAreas;
    }


    public void setTitle(String title){
    /**
     * Setter for title of the paper
     * @param 	the title of the paper
     **/
        title = this.title;
    }


    public void setAuthor(String author){
    /**
     * Setter for author of the paper in full name
     * @param 	the  author of the paper in full name
     **/
        author = this.author;
    }


    public void setConference(Conference c){
    /**
     * Setter for conference where the paper submitted
     * @param 	the conference where the paper submitted
     **/
        c = this.conference;
    }


    public void setContent(String content){
    /**
     * Setter for content of the paper
     * @param 	the content of the paper
     **/
        content = this.content;
    }


    public void setNoOfReviewer(int number){
    /**
     * Setter for the number of reviewer of paper
     * @param 	the number of reviewer of paper
     **/
        number = this.noOfReviewer;
    }


    public void setProgressStatus(String progress){
    /**
     * Setter for current progress of paper
     * @param 	the current progress of paper
     **/
        progress = this.currentProgressStatus;
    }


    public void addTopicAreas(String topic){
    /**
     * Add topic into the chosen topic areas
     * @param 	the topic area to be added
     **/
        this.chosenTopicAreas.add(topic);
    }
    
}
