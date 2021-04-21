/**
 * Class that represents a Conference in the system.
 * 
**/

package cms;

import java.util.*;


public class Conference {
    private String name;
    private String place;
    private ArrayList<String> chosenTopicAreas= new ArrayList<String>();
    private Date date;
    private Date paperSubmissionDueDate;
    private Date paperReviewDueDate;

    public Conference (String name, String place, ArrayList<String> topic, Date date, Date submissionDue, Date reviewDue){
    /**
	 * Constructor for the Conference.
     * @param name is the name of the conference
     * @param place is the place of the conference held
     * @param topic is the selected topic area of the conference
     * @param date is the date held of conference
     * @param submissionDue is the paper submission due date of the conference
     * @param reviewDue is the paper review due date of the conference
     **/
        name = this.name;
        place = this.place;
        topic = this.chosenTopicAreas;
        date = this.date;
        submissionDue = this.paperSubmissionDueDate;
        reviewDue = this.paperReviewDueDate;
    }

    public String getName(){
    /**
     * Getter for name of the conference
     * @return 	the name of the conference
     **/
        return this.name;
    }


    public String getPlace(){
    /**
     * Getter for the place of the conference held
     * @return 	the place of the conference held
     **/
        return this.place;
    }


    public ArrayList<String> retrieveChosenTopicAreas(){
    /**
     * Getter for the selected topic area of the conference
     * @return 	the selected topic area of the conference
     **/
        return this.chosenTopicAreas;
    }


    public Date getDate(){
    /**
     * Getter for the date held of conference
     * @return 	the date held of conference
     **/
        return this.date;
    }


    public Date getPaperSubmissionDue(){
    /**
     * Getter for the paper submission due date of the conference
     * @return 	the paper submission due date of the conference
     **/
        return this.paperSubmissionDueDate;
    }


    public Date getPaperReviewDue(){
    /**
     * Getter for the paper review due date of the conference
     * @return 	the paper review due date of the conference
     **/
        return this.paperReviewDueDate;
    }


    public void setName(String name){
    /**
     * Setter for name of the conference
     * @param 	the name of the conference
     **/
        this.name = name;
    }


    public void setPlace(String place){
    /**
     * Setter for the place of the conference held
     * @param 	the place of the conference held
     **/
        this.place = place;
    }


    public void addChosenTopicAreas(String topic){
    /**
     * Add topic into the chosen topic areas
     * @param 	the topic area to be added
     **/
        this.chosenTopicAreas.add(topic);
    }


    public void setDate(Date date){
    /**
     * Setter for the date held of conference
     * @param 	the date held of conference
     **/
        this.date = date;
    }


    public void setSubmissionDue(Date date){
    /**
     * Setter for the paper submission due date of the conference
     * @param 	the paper submission due date of the conference
     **/
        this.paperSubmissionDueDate = date;
    }


    public void getReviewDue(Date date){
    /**
     * Setter for the paper review due date of the conference
     * @param 	the paper review due date of the conference
     **/
       this.paperReviewDueDate = date;
    }
}
