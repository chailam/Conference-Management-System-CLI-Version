/**
 * This controller class is used to control the ConferenceManagementSystem. When the program start,
 * it reads the data from NormalUser.csv, Conference.csv and Paper.csv, which acts as our Database.
 * Then based on the data read, it creates the entity and adds those entities to the userList, paperList, and conferenceList,
 * which located in the ConferenceManagementSystem cms. Since cms is a static variable of Controller parent class, DataEntityController
 * and CoundaryController can share the same cms (data).
 */

package cms;

import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;


public class DataEntityController extends Controller {

    private ArrayList<String> processCSV (String filePath){
        /**
         * To read the data from csv file (as database) and put the data to the arraylist structure
         * @param the file path of the csv file to be read
         */
            String currentLine;
            ArrayList<String> csvData = new ArrayList<String>();
            try {
                FileReader fileReader = new FileReader(filePath);
                BufferedReader bufReader = new BufferedReader(fileReader);
                int counter = 0;  // not reading the first line
    
                while((currentLine = bufReader.readLine()) != null) {
                    if (counter != 0){
                        csvData.add(currentLine);
                    }
                    counter += 1;
                }
                bufReader.close();
            }
            catch (Exception e){
                System.out.println("File Read Error: " + e);
            }
            return csvData;
        }
    

    
    public void initializeConferenceManagementSystem(){
        /**
         * It initialized the ConferenceManagementSystem, to read all data from csv file, 
         * which act as database, and create entity for each of them.
         */
        ArrayList<String> resultUser = processCSV (pathNormalUserCSV);
        ArrayList<String> resultConference = processCSV (pathConferenceCSV);
        ArrayList<String> resultPaper = processCSV (pathPaperCSV);

        String delimit = ",";
        String [] tmp;
        try {
            // Add the User Entity from csv file to object list
            if (resultUser.isEmpty() == false){
                for (String line : resultUser){
                    tmp = line.split(delimit);
                    // Change the topicAreas and paper to ArrayList
                    ArrayList<String> topic = ut.stringToArrayList(tmp[10],"/");
                    ArrayList<String> paper = ut.stringToArrayList(tmp[11],"/");
                    // Create user entity and add into the userList
                    User u = createUserEntity(tmp[0],tmp[1],tmp[2],tmp[3],tmp[4],tmp[5],tmp[6],tmp[7],tmp[8],tmp[9],topic,paper);
                    if (u != null){
                        cms.addUser(u);
                    }
                }
            }
            // Add the Conference Entity from csv file to object list
            if (resultConference.isEmpty() == false){
                for (String line : resultConference){
                    tmp = line.split(delimit);
                    // Change the data to Date format
                    Date date = new SimpleDateFormat("dd/MM/yyyy").parse(tmp[3]);
                    Date submitDueDate = new SimpleDateFormat("dd/MM/yyyy").parse(tmp[4]);
                    Date reviewDueDate = new SimpleDateFormat("dd/MM/yyyy").parse(tmp[5]);
                    // Change the topicAreas to ArrayList
                    ArrayList<String> topic = ut.stringToArrayList(tmp[2],"/");
                    // Create conference entity and add into the conferenceList
                    Conference c = createConferenceEntity(tmp[0],tmp[1],topic,date,submitDueDate,reviewDueDate);
                    if (c != null){
                        cms.addConference(c);
                    }
                }
            }
            // Add the Paper Entity from csv file to object list
            if (resultPaper.isEmpty() == false){
                for (String line : resultPaper){
                    tmp = line.split(delimit);
                    // Change the topicAreas to ArrayList
                    ArrayList<String> topic = ut.stringToArrayList(tmp[6],"/");
                    // Change the evaluation to ArrayList
                    ArrayList<String> evaluation = ut.stringToArrayList(tmp[4],"/");
                    Paper p = createPaperEntity(tmp[0],tmp[1],tmp[2],Integer.parseInt(tmp[3]),evaluation,tmp[5],topic,tmp[7]);
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
