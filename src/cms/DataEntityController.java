/**
 * This controller class is used to control the ConferenceManagementSystem. When the program start,
 * it reads the data from NormalUser.csv, Conference.csv and Paper.csv, which acts as our Database.
 * Then based on the data read, it creates the entity and adds those entities to the userList, paperList, and conferenceList,
 * which located in the ConferenceManagementSystem cms. Since cms is a static variable of Controller parent class, DataEntityController
 * and CoundaryController can share the same cms (data).
 */

package cms;

import java.time.LocalDate;
import java.util.*;


public class DataEntityController extends Controller {

    
    public void initializeConferenceManagementSystem(){
    /**
     * It initialized the ConferenceManagementSystem, to read all data from csv file,
     * which act as database, and create entity for each of them.
     */
        List<String[]> resultUser = ut.readCSV(pathNormalUserCSV);
        List<String[]> resultConference = ut.readCSV(pathConferenceCSV);
        List<String[]> resultPaper = ut.readCSV(pathPaperCSV);

        String delimit = ",";
        String [] tmp;
        try {
            // Add the User Entity from csv file to object list
            if (resultUser.isEmpty() == false){
                for (String[] line : resultUser){
                    // Change the topicAreas and paper to ArrayList
                    ArrayList<String> topic = ut.stringToArrayList(line[10],"/");
                    ArrayList<String> paper = ut.stringToArrayList(line[11],"/");
                    // Create user entity and add into the userList
                    User u = createUserEntity(line[0],line[1],line[2],line[3],line[4],line[5],line[6],line[7],line[8],line[9],topic,paper);
                    if (u != null){
                        cms.addUser(u);
                    }
                }
            }
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
                    Conference c = createConferenceEntity(line[0],line[1],topic,date,submitDueDate,reviewDueDate);
                    if (c != null){
                        cms.addConference(c);
                    }
                }
            }
            // Add the Paper Entity from csv file to object list
            if (resultPaper.isEmpty() == false){
                for (String[] line : resultPaper){
                    // Change the topicAreas to ArrayList
                    ArrayList<String> topic = ut.stringToArrayList(line[6],"/");
                    // Change the evaluation to ArrayList
                    ArrayList<String> evaluation = ut.stringToArrayList(line[4],"/");
                    Paper p = createPaperEntity(line[0],line[1],line[2],Integer.parseInt(line[3]),evaluation,line[5],topic,line[7]);
                    if (p != null){
                        cms.addPaper(p);
                    }
                }
            }
        }
        catch (Exception e){
            System.out.println("File Error: " + e);
        }
    }
}
