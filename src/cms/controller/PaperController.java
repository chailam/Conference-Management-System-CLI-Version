/**
 * Worker controller for the Paper related function.
 */
package cms.controller;

import cms.Utility;
import cms.entity.ConferenceManagementSystem;
import cms.entity.Paper;
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

public class PaperController {

    protected ConferenceManagementSystem cms;
    protected UserInterface ui;
    protected String pathPaperCSV;
    private Utility ut = new Utility();

    public PaperController (ConferenceManagementSystem cms, UserInterface ui, String pathPaperCSV){
        this.cms = cms;
        this.ui = ui;
        this.pathPaperCSV = pathPaperCSV;
        // initialize the paper csv
        this.importAllPaperCSV();
    }


    protected void addPaperEntity(String title, String author, String content, int noOfReviewer, ArrayList<String> evaluation, String conferenceName, ArrayList<String> topic, String progress){
        /**
         * Create Paper object and add to the cms paper list
         * @param the Paper information required
         */
        Paper p = null;
        try {
            // Create a paper object
            p = new Paper(title,author,content,noOfReviewer,evaluation,conferenceName,topic,progress);
            if ( p != null){
                cms.addPaper(p);
            } else {
                System.out.println("Error: Paper is null in createPaperEntity.");
            }
        } catch (Exception e){
            System.out.println("Exception: createPaperEntity - " + e);
        }
    }


    private void importAllPaperCSV(){
        /**
         * Import all the paper data from csv file
         */
        List<String[]> resultPaper = ut.readCSV(pathPaperCSV);
        try {
            // Add the Paper Entity from csv file to object list
            if (resultPaper.isEmpty() == false){
                for (String[] line : resultPaper){
                    // Change the topicAreas to ArrayList
                    ArrayList<String> topic = ut.stringToArrayList(line[6],"/");
                    // Change the evaluation to ArrayList
                    ArrayList<String> evaluation = ut.stringToArrayList(line[4],"/");
                    addPaperEntity(line[0],line[1],line[2],Integer.parseInt(line[3]),evaluation,line[5],topic,line[7]);
                }
            }
        } catch (Exception e){
            System.out.println("Exception: importAllPaperCSV - " + e);
        }
    }


    protected void appendPaperCSV (String title, String authorEmail, String content, String noOfReviewer, String evaluation, String conference, String topics, String progressStatus){
        /**
         * Append the paper data to the csv file
         * @param the data to be appended
         */
        String[] paperData = {title, authorEmail, content, noOfReviewer, evaluation, conference, topics, progressStatus};
        // true because append not overwrite
        ut.writeCSV(pathPaperCSV, paperData,true);
    }


    protected boolean checkPaperSubmitInfo (String title, String path, String confName) throws InterruptedException {
        /**
         *  Check the paper submission information
         * @param the paper submission information to be checked
         * @return true if data valid; false otherwise
         */
        // check if the submission date pass the deadline
        // get the conference paper submission due date
        LocalDate submitDueDate = cms.searchConference(confName).getPaperSubmissionDue();
        if (submitDueDate.isBefore(LocalDate.now(ZoneId.of( "Australia/Sydney" )))){
            ui.displayMsgWithSleep("The paper submission due date has been passed.");
            return false;
        }
        // check if data input is zero
        if ((title.length() == 0) || (path.length() == 0)) {
            ui.displayMsgWithSleep("Information could not be empty.");
            return false;
        }
        // check if duplicated paper
        if (cms.hasPaper(title) == true){
            ui.displayMsgWithSleep("The Paper Title is duplicated.\n    Please try another title.");
            return false;
        }
        // check the path
        if (ut.pathValidator(path) == false){
            ui.displayMsgWithSleep("The file path is incorrect.");
            return false;
        }
        return true;
    }


    protected void updatePaperProgressStatus(String pTitle, String progress){
        /**
         * To update the progress status in cms paperlist and csv file
         * @param title of the paper to be updated
         * @param progress of the paper
         */
        // modify the cms
        Paper p = cms.searchPaper(pTitle);
        p.setProgressStatus(progress);
        // write to csv file,  progress at col index 7
        this.updatePaperCsv(pathPaperCSV, progress, 7, p.getTitle());
    }


    protected void updatePaperNoOfReviewer(String pTitle, int noOfReviewer){
        /**
         * To update the no of reviewer in cms paperlist and csv file
         * @param title of the paper to be updated
         * @param no of reviewer of the paper
         */
        // modify the number of reviewer in paperList
        cms.searchPaper(pTitle).setNoOfReviewer(noOfReviewer);
        // write to csv, ,  noOfReviewer at col index 3
        this.updatePaperCsv(pathPaperCSV,Integer.toString(noOfReviewer),3,pTitle);

    }


    protected void updatePaperEvaluation(String pTitle, String evaluation){
        /**
         * To update the evaluation in cms paperlist and csv file
         * @param title of the paper to be updated
         * @param evaluation of the paper
         */
        // modify cms
        // get the paper and update the evaluation
        Paper p = cms.searchPaper(pTitle);
        ArrayList<String> evaluations = new ArrayList<String>();
        // if the paper has no evaluation before
        if (p.retrieveEvaluation()== null) {
            evaluations.add(evaluation);
            p.setEvaluation(evaluations);
        } else {
            // if there is existing evaluation for the paper
            p.retrieveEvaluation().add(evaluation);
        }
        // get the evaluation
        evaluations = p.retrieveEvaluation();
        // write to csv file the evaluation, evaluation at col index 4
        this.updatePaperCsv(pathPaperCSV, ut.arrayListToString(evaluations, "/"), 4, p.getTitle());
        // check if no of reviewer == number of evaluation, if yes, change status to reviewed
        if (p.getNoOfReviewer() == evaluations.size()) {
            this.updatePaperProgressStatus(pTitle,"Reviewed");
        }
    }


    protected void updatePaperCsv(String filePath, String dataToUpdate, int dataIndex, String title){
        /**
         *  The method to update user data in csv file at specific col and row
         * @param the file path of the file to update
         * @param the data to update to file
         * @param the index of the data to be modified
         * @param the title of the paper
         */
        try {
            // read the data
            File theFile = new File(filePath);
            CSVReader csvReader = new CSVReader(new FileReader(theFile));
            List<String[]> csvData = csvReader.readAll();

            // get data to be replaced  at row (i) and column
            for (int i = 0; i < csvData.size() ; i++){
                String[] string = csvData.get(i);
                //col index 0 is title, which is unique
                if(string[0].equalsIgnoreCase(title)){
                    string[dataIndex] = dataToUpdate;
                }
            }
            csvReader.close();
            // Write to the CSV file
            CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath));
            csvWriter.writeAll(csvData);
            csvWriter.flush();
            csvWriter.close();
        } catch (Exception e){
            System.out.println("Paper File Write Error: " + e);
        }
    }


    protected ArrayList<String> getPaperWithSpecificStatus(String conferenceName, String status) {
        /**
         * Search through the user to get the paper of that conference which match the status
         * @param the conference name
         * @param the status of paper
         * @return the paper title that match the status
         */
        ArrayList<String> papers = new ArrayList<String>();
        for (Paper p: cms.retrievePaperList()){
            if (p.getConferenceName().equals(conferenceName) && p.getProgressStatus().equalsIgnoreCase(status)){
                papers.add(p.getTitle());
            }
        }
        return papers;
    }
}
