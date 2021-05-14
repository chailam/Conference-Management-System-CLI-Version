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
import java.util.Arrays;
import java.util.List;

public class PaperController {

    ConferenceManagementSystem cms;
    UserInterface ui;
    String pathPaperCSV;
    Utility ut = new Utility();

    public PaperController (ConferenceManagementSystem cms, UserInterface ui, String pathPaperCSV){
        this.cms = cms;
        this.ui = ui;
        this.pathPaperCSV = pathPaperCSV;
        this.importAllPaperCSV();
    }


    public void createPaperEntity (String title, String author, String content, int noOfReviewer, ArrayList<String> evaluation, String conferenceName, ArrayList<String> topic, String progress){
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


    public void importAllPaperCSV(){
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
                    createPaperEntity(line[0],line[1],line[2],Integer.parseInt(line[3]),evaluation,line[5],topic,line[7]);
                }
            }
        } catch (Exception e){
            System.out.println("Exception: importAllPaperCSV - " + e);
        }
    }


    public void appendPaperCSV (String title, String authorEmail, String content, String noOfReviewer, String evaluation, String conference, String topics,String progressStatus){
        /**
         * Append the conference data to the csv file
         * @param the data to be appended
         */
        String[] conferenceData = {confName, place, topics, date, submitDueDate, reviewDueDate};
        // true because append not overwrite
        ut.writeCSV(pathConferenceCSV, conferenceData,true);
    }


    public boolean checkPaperSubmitInfo (String title, String path, String confName) throws InterruptedException {
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

            // Set the paper, add paper to cms list
            Paper createdp = createPaperEntity(title, emailAddress,null,0,null,confName,topicName,"Being Reviewed");
            cms.addPaper(createdp);
            //write paper to csv file
            String[] paperData = {title, emailAddress, null,"0",null,confName,ut.arrayListToString(topicName,"/"), "Being Reviewed"};
            ut.writeCSV(pathPaperCSV,paperData,true);

            // TODO: may be this is user controller?
            // Update the user information to include this paper
            NormalUser u = cms.searchSpecificUser(emailAddress,"Author",confName);
            if (u != null){
                Author au = (Author) u;
                ArrayList<String> papers = new ArrayList<String>();
                // if paper is empty
                if (au.retrievePaper().get(0) == ""){
                    papers.add(createdp.getTitle());
                    au.setPaper(papers);
                // if author submitted another paper already
                } else{
                    au.addPaper(createdp.getTitle());
                }
                papers = au.retrievePaper();
                // use opencsv to modify csv file for author to include this paper
                ut.updateUserCsv(pathUserCSV, ut.arrayListToString(papers,"/"),11, emailAddress, "author", confName);
            } else{
                System.out.println("Why can't find that user?!!!");
            }
            // display message
            ui.displayMsgWithSleep("Congratulations!\n  You have submitted your paper for "+ confName + " as Reviewer.\n    You can view the review of your paper when result released.");
            // go back to previous page
            return true;
        // if user choose to go back
        } else if (op2.equalsIgnoreCase("Back")){
            return true;
        }
        return true;
    }



    public boolean paperFinalDecision (String name, String emailAddress, String confName) throws InterruptedException {
        /**
         * true for return to chairchoice, false to return to this function
         */
        // get a list of paper in that conference where status is reviewed
        ArrayList<String> reviewedPaper = cms.getPaperWithSpecificStatus(confName, "Reviewed");
        // if no paper all reviewed
        if (reviewedPaper.size() == 0) {
            ui.displayMsgWithSleep("All papers are still under reviewing.");
            return true;
        // if there is paper fully reviewed
        } else {
            reviewedPaper.add("Back");
            // get the user option to choose a paper
            String op = ui.getUserOption(reviewedPaper, name, true);
            // if user choose back
            if (op.equalsIgnoreCase("Back")) {
                return true;
            } else {
                // if user choose a paper
                // get the evaluation of the paper
                Paper p = cms.searchPaper(op);
                ArrayList<String> evaluations = p.retrieveEvaluation();
                ui.confirmEvaluation(op, ut.arrayListToString(evaluations, ";\n\t"));
                // get user option to reject or accept
                ArrayList<String> finalDecision = new ArrayList<>(Arrays.asList("Accept", "Reject", "Back"));
                String op2 = ui.getUserOption(finalDecision, "", false);
                // if user choose to go back
                if (op2.equalsIgnoreCase("Back")) {
                    return false;
                } else if (op2.equalsIgnoreCase("Accept")) {
                    // set the status of the paper to Accept
                    this.updatePaperProgressStatus(p.getTitle(),"Accept");
                    // display message
                    ui.displayMsgWithSleep("You have successfully Accepted the paper.");
                    return true;
                } else if (op2.equalsIgnoreCase("Reject")) {
                    // set the status of the paper to Reject
                    this.updatePaperProgressStatus(p.getTitle(),"Rejected");                    // display message
                    ui.displayMsgWithSleep("You have successfully Rejected the paper.");
                    return true;
                }
            }
        }
        return true;
    }


    public boolean paperEvaluation(String name, String emailAddress, String confName) throws InterruptedException {
        /**
         * true jump to reviewerchoice, false jump to this functiona again
         */
        // check the date of reviewer submission
        LocalDate reviewDue = cms.searchConference(confName).getPaperReviewDue();
        if (reviewDue.isBefore(LocalDate.now(ZoneId.of("Australia/Sydney")))) {
            ui.displayMsgWithSleep("The paper review due date has been passed!");
            return true;
        }
        // retrieve the paper assigned to this reviewer
        Reviewer ru = (Reviewer) cms.searchSpecificUser(emailAddress, "reviewer", confName);
        ArrayList<String> assignedPapers = ru.retrieveAssignedPaper();
        // get the user option to choose which paper
        assignedPapers.add("Back");
        // get user option to select which paper or back
        String chosenPaper = ui.getUserOption(assignedPapers, name, true);
        // if user choose to go back
        if (chosenPaper.equalsIgnoreCase("Back")) {
            return true;
        } else {
            // if user choose an paper
            // get the evaluation of paper
            String evaluation = ui.getEvaluation(chosenPaper);
            evaluation = evaluation.trim();
            // check if the evaluation is empty
            if (evaluation == "") {
                ui.displayMsgWithSleep("The evaluation is empty.");
                return false;
            }
            // get confirmation for the evaluation
            ui.confirmEvaluation(chosenPaper, evaluation);
            ArrayList<String> choices = new ArrayList<>(Arrays.asList("Confirm", "Back"));
            String op = ui.getUserOption(choices, "", false);
            ui.displayFooter();
            // if user choose to confirm
            if (op.equalsIgnoreCase("Confirm")) {
                // get the paper and update the evaluation in cms and csv
                this.updatePaperEvaluation(chosenPaper,evaluation);
                // display successful message and redirect
                ui.displayMsgWithSleep("You have successfully uploaded the evaluation.");
                return true;
            } else if (op.equalsIgnoreCase("Back")) {
                // if user choose to go back
                return false;
            }
        }
        return true;
    }


    public void updatePaperProgressStatus(String pTitle, String progress){
        Paper p = cms.searchPaper(pTitle);
        p.setProgressStatus(progress);
        // write to csv file
        this.updatePaperCsv(pathPaperCSV, progress, 7, p.getTitle());
    }

    public void updatePaperEvaluation(String pTitle, String evaluation){
        // get the paper and update the evaluation
        Paper p = cms.searchPaper(pTitle);
        ArrayList<String> evaluations = new ArrayList<String>();
        // if the paper has no evaluation before
        if (p.retrieveEvaluation().get(0) == "") {
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


    public void updatePaperCsv(String filePath, String dataToUpdate, int dataIndex, String title){
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
}
