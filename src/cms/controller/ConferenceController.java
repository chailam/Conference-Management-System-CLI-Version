/**
 * Worker controller for the Conference related function.
 */
package cms.controller;

import cms.Utility;
import cms.entity.Conference;
import cms.entity.ConferenceManagementSystem;
import cms.view.UserInterface;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ConferenceController {
    protected ConferenceManagementSystem cms;
    protected UserInterface ui;
    protected String pathConferenceCSV;
    private Utility ut = new Utility();


    public ConferenceController(ConferenceManagementSystem cms, UserInterface ui, String pathConferenceCSV){
        this.cms = cms;
        this.ui = ui;
        this.pathConferenceCSV = pathConferenceCSV;
        this.importAllConferenceCSV();
    }

    protected void addConferenceEntity(String name, String place, ArrayList<String> topic, LocalDate date, LocalDate submissionDue, LocalDate reviewDue){
        /**
         * Create Conference object and add to the cms conference list
         * @param the conference information required
         */
        Conference c = null;
        try {
            // Create a conference class
            c = new Conference(name,place,topic,date,submissionDue,reviewDue);
            if ( c != null){
                cms.addConference(c);
            } else {
                System.out.println("Error: Conference is null in createConferenceEntity.");
            }
        } catch (Exception e){
            System.out.println("Exception: createConferenceEntity - " + e);
        }
    }


    private void importAllConferenceCSV(){
        /**
         * Import all the conference data from csv file
         */
        List<String[]> resultConference = ut.readCSV(pathConferenceCSV);
        try {
            // Add the Conference Entity from csv file to object list
            if (resultConference.isEmpty() == false){
                for (String[] line : resultConference){
                    // Change the data to Date format
                    LocalDate date = ut.stringToDate(line[3]);
                    LocalDate submitDueDate = ut.stringToDate(line[4]);
                    LocalDate reviewDueDate = ut.stringToDate(line[5]);
                    // Change the topicAreas to ArrayList
                    ArrayList<String> topic = ut.stringToArrayList(line[2],"/");
                    // Create conference entity and add into the conferenceList
                    addConferenceEntity(line[0],line[1],topic,date,submitDueDate,reviewDueDate);
                }
            }
        } catch (Exception e){
            System.out.println("Exception: importConferenceCSV - " + e);
        }
    }


    protected void appendConferenceCSV (String confName, String place, String topics, String date, String submitDueDate, String reviewDueDate){
        /**
         * Append the conference data to the csv file
         * @param the data to be appended
         */
        String[] conferenceData = {confName, place, topics, date, submitDueDate, reviewDueDate};
        // true because append not overwrite
        ut.writeCSV(pathConferenceCSV, conferenceData,true);
    }


    protected boolean checkCreateConfInfo (String confName, String place, String date, String submitDueDate, String reviewDueDate) throws InterruptedException {
        /**
         * Check the conference creation information
         * @param the conference information to be checked
         * @return true if conference info valid; false otherwise
         */
        // check if data input is zero
        if ((confName.length() == 0) || (place.length() == 0)) {
            ui.displayMsgWithSleep("Information could not be empty.");
            return false;
        }
        // check date validity
        try {
            LocalDate theDate = ut.stringToDate(date);
            LocalDate theSubmitDueDate = ut.stringToDate(submitDueDate);
            LocalDate theReviewDueDate = ut.stringToDate(reviewDueDate);

            //check if it is passed date
            if (theDate.isBefore(LocalDate.now(ZoneId.of("Australia/Sydney"))) || theSubmitDueDate.isBefore(LocalDate.now(ZoneId.of("Australia/Sydney"))) || theReviewDueDate.isBefore(LocalDate.now(ZoneId.of("Australia/Sydney")))) {
                ui.displayMsgWithSleep("Please enter a valid date after today.");
                return false;
            }
            // check if existing conference
            if (cms.hasConference(confName) == true) {
                ui.displayMsgWithSleep("The conference already exists.\n\tPlease try to other name.");
                return false;
            }
        } catch (Exception e) {
            ui.displayMsgWithSleep("Please enter a valid date.");
            System.out.println("Exception: checkCreateConfInfo - " + e);
            return false;
        }
        return true;
    }
}
