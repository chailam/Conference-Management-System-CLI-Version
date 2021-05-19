/**
 * Worker controller for the Conference related function.
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

    protected void addConferenceEntity(String name, String place, ArrayList<String> topic, LocalDate date, LocalDate submissionDue, LocalDate reviewDue, ArrayList<String> listOfPaperSubmitted){
        /**
         * Create Conference object and add to the cms conference list
         * @param the conference information required
         */
        Conference c = null;
        try {
            // Create a conference class
            c = new Conference(name,place,topic,date,submissionDue,reviewDue, listOfPaperSubmitted);
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
                    // Change the list of paper submitted to ArrayList
                    ArrayList<String> listPaper = ut.stringToArrayList(line[6],"/");
                    // Create conference entity and add into the conferenceList
                    addConferenceEntity(line[0],line[1],topic,date,submitDueDate,reviewDueDate, listPaper);
                }
            }
        } catch (Exception e){
            System.out.println("Exception: importConferenceCSV - " + e);
        }
    }


    protected void appendConferenceCSV (String confName, String place, String topics, String date, String submitDueDate, String reviewDueDate,String listPaper){
        /**
         * Append the conference data to the csv file
         * @param the data to be appended
         */
        String[] conferenceData = {confName, place, topics, date, submitDueDate, reviewDueDate, listPaper};
        // true because append not overwrite
        ut.writeCSV(pathConferenceCSV, conferenceData,true);
    }


    protected boolean checkCreateConfInfo (String confName, String place, String date, String submitDueDate, String reviewDueDate, String listPaper) throws InterruptedException {
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

    protected void updateConferenceCsv(String filePath, String dataToUpdate, int dataIndex, String conferenceName) {
        /**
         *  The method to update user data in csv file at specific col and row
         * @param the file path of the file to update
         * @param the data to update to file
         * @param the index of the data to be modified
         * @param the matching string
         */
        try {
            // read the data
            File theFile = new File(filePath);
            CSVReader csvReader = new CSVReader(new FileReader(theFile));
            List<String[]> csvData = csvReader.readAll();

            // get data to be replaced  at row (i) and column
            for (int i = 0; i < csvData.size(); i++) {
                String[] string = csvData.get(i);
                //col index 0 is name
                if (string[0].equalsIgnoreCase(conferenceName)) {
                    string[dataIndex] = dataToUpdate;
                }
            }
            csvReader.close();
            // Write to the CSV file
            CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath));
            csvWriter.writeAll(csvData);
            csvWriter.flush();
            csvWriter.close();
        } catch (Exception e) {
            System.out.println("Conference File Write Error: " + e);
            }
        }

    protected ArrayList<String> retrieveConferencePaper(String confName, String paperTitle){
        /**
         * To update the conference information in cms conferencelist to include the paper submitted
         * @param conference name of the user joined
         * @return a list of papers submitted
         */
        // find that specific conference with unique conference name
        Conference c  = cms.searchConference(confName);
        ArrayList<String> papers = new ArrayList<String>();
        if (c != null){
            if (c.retrieveListOfPaperSubmitted().size() == 0) {
                // if assigned paper is empty, set the assigned paper
                papers.add(paperTitle);
                c.setListOfPaperSubmitted(papers);
            } else {
                // if reviewer has existing assigned paper, add paper
                c.addListOfPaperSubmitted(paperTitle);
            }
            papers = c.retrieveListOfPaperSubmitted();
                return papers;
        } else {
            System.out.println("Error: Conference is null in retrieveConferencePaper.");
        }
        return papers;
    }



}

