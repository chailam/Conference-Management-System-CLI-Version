/**
 * Class that represents a Conference in the system.
 * 
**/

package cms.entity;

import java.time.LocalDate;
import java.util.*;


public class Conference {
    private String name;
    private String place;
    private ArrayList<String> chosenTopicAreas= new ArrayList<String>();
    private LocalDate date;
    private LocalDate paperSubmissionDueDate;
    private LocalDate paperReviewDueDate;

    public Conference (String name, String place, ArrayList<String> topic, LocalDate date, LocalDate submissionDue, LocalDate reviewDue){
    /**
	 * Constructor for the Conference.
     * @param name is the name of the conference
     * @param place is the place of the conference held
     * @param topic is the selected topic area of the conference
     * @param date is the date held of conference
     * @param submissionDue is the paper submission due date of the conference
     * @param reviewDue is the paper review due date of the conference
     **/
        this.name = name;
        this.place = place;
        this.chosenTopicAreas = topic;
        this.date = date;
        this.paperSubmissionDueDate = submissionDue;
        this.paperReviewDueDate = reviewDue;
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


    public LocalDate getDate(){
    /**
     * Getter for the date held of conference
     * @return 	the date held of conference
     **/
        return this.date;
    }


    public LocalDate getPaperSubmissionDue(){
    /**
     * Getter for the paper submission due date of the conference
     * @return 	the paper submission due date of the conference
     **/
        return this.paperSubmissionDueDate;
    }


    public LocalDate getPaperReviewDue(){
    /**
     * Getter for the paper review due date of the conference
     * @return 	the paper review due date of the conference
     **/
        return this.paperReviewDueDate;
    }


    public ArrayList<String> retrieveChosenTopicAreas(){
        /**
         * Getter for the selected topic area of the conference
         * @return 	the selected topic area of the conference
         **/
        return this.chosenTopicAreas;
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


    public void setDate(LocalDate date){
    /**
     * Setter for the date held of conference
     * @param 	the date held of conference
     **/
        this.date = date;
    }


    public void setSubmissionDue(LocalDate date){
    /**
     * Setter for the paper submission due date of the conference
     * @param 	the paper submission due date of the conference
     **/
        this.paperSubmissionDueDate = date;
    }


    public void setReviewDue(LocalDate date){
    /**
     * Setter for the paper review due date of the conference
     * @param 	the paper review due date of the conference
     **/
       this.paperReviewDueDate = date;
    }


    public void addChosenTopicAreas(String topic){
        /**
         * Add topic into the chosen topic areas
         * @param 	the topic area to be added
         **/
        this.chosenTopicAreas.add(topic);
    }


    //This toString method is for developer debugging purpose!
    @Override
    public String toString(){
        return String.format("name: " + getName() + ", topic: " + retrieveChosenTopicAreas() + ", submit due: " + getPaperSubmissionDue() + ", review due: " + getPaperReviewDue());
    }
}
