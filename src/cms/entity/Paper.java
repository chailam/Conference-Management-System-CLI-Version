/**
 * Class that represents a Paper in the system.
 * 
**/

package cms.entity;

import java.util.*;


public class Paper {
    private String title;
    private String author;
    private String content;
    private int noOfReviewer;
    private ArrayList<String> evaluation = new ArrayList<String>();
    private String conferenceName;
    private ArrayList<String> chosenTopicAreas= new ArrayList<String>();
    private String currentProgressStatus;

    public Paper(String title, String author, String conferenceName, ArrayList<String> topic){
    /**
	 * Constructor for the Paper.
     * @param name is the name of the paper
     * @param author is the author of the paper in full name
     * @param conference is the conference where the paper submitted
     * @param topic is the topic areas of the paper
     **/
        this.title = title;
        this.author = author;
        this.conferenceName = conferenceName;
        this.chosenTopicAreas = topic;
        this.currentProgressStatus = "Being Reviewed";
    }


    public Paper(String title, String author, String content, int noOfReviewer, ArrayList<String> evaluation, String conferenceName, ArrayList<String> topic, String progress){
        /**
         * Constructor for the Paper.
         * @param name is the name of the paper
         * @param author is the author of the paper in full name
         * @param content of the paper
         * @param noOfReviewer assigned to this paper
         * @param evaluation submitted
         * @param conference is the conference where the paper submitted
         * @param topic is the topic areas of the paper
         * @param the progress of current paper
         **/
            this.title = title;
            this.author = author;
            this.content = content;
            this.noOfReviewer = noOfReviewer;
            this.evaluation = evaluation;
            this.conferenceName = conferenceName;
            this.chosenTopicAreas = topic;
            this.currentProgressStatus = progress;
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


    public String getConferenceName(){
    /**
     * Getter for conference where the paper submitted
     * @return 	the conference name where the paper submitted
     **/
        return this.conferenceName;
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


    public ArrayList<String> retrieveEvaluation(){
    /**
     * Retrieve the evaluation
     * @return 	the evaluation
     **/
        return this.evaluation;
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
        this.title = title;
    }


    public void setAuthor(String author){
    /**
     * Setter for author of the paper in full name
     * @param 	the  author of the paper in full name
     **/
        this.author = author;
    }


    public void setConference(String cName){
    /**
     * Setter for conference where the paper submitted
     * @param 	the conference name where the paper submitted
     **/
        this.conferenceName = cName;
    }


    public void setContent(String content){
    /**
     * Setter for content of the paper
     * @param 	the content of the paper
     **/
        this.content = content;
    }


    public void setNoOfReviewer(int number){
    /**
     * Setter for the number of reviewer of paper
     * @param 	the number of reviewer of paper
     **/
        this.noOfReviewer = number;
    }


    public void setProgressStatus(String progress){
    /**
     * Setter for current progress of paper
     * @param 	the current progress of paper
     **/
        this.currentProgressStatus = progress;
    }


    public void setEvaluation(ArrayList<String> evaluations){
        /**
         * Setter for the evaluation of paper
         * @param 	the evaluation list
         **/
        this.evaluation = evaluations;
    }


    public void addTopicAreas(String topic){
    /**
     * Add topic into the chosen topic areas
     * @param 	the topic area to be added
     **/
        this.chosenTopicAreas.add(topic);
    }


    public void addEvaluation(String eva){
    /**
     * Retrieve the evaluation
     * @param 	the evaluation
     **/
        this.evaluation.add(eva);
    }
    



    //TODO: delete it when submit assignment, this is for developer debug purpose!!!
    @Override
    public String toString(){
        return String.format("title: " + getTitle() + ", author: " + getAuthor() + ", content : " + getContent() + ", no of reviewer: " + getNoOfReviewer() + ", evaluation: " + retrieveEvaluation() + ", topic: " + retrieveTopicAreas() + ", conference: " + getConferenceName() + ", progress: " + getProgressStatus());
    }
}
