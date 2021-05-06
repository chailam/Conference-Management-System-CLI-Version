/**
 * The utility class that has all tools required.
 */

package cms;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets; 
import java.math.BigInteger;
import java.io.*;

public class Utility {

    public String hashSHA256(String text) throws NoSuchAlgorithmException{
        /**
         * This method is used to hashed the text, mainly the password, using SHA256. 
         * @param the text to be hashed
         * @return the hashed text
         */
            // Use the SHA256
            MessageDigest md = MessageDigest.getInstance("SHA-256"); 
            byte[] hashedByte = md.digest(text.getBytes(StandardCharsets.UTF_8));
            BigInteger theNumber = new BigInteger(1, hashedByte); 
            StringBuilder hashedText = new StringBuilder(theNumber.toString(16));
            // Pad with leading zeros
            while (hashedText.length() < 32){ 
                hashedText.insert(0, '0'); 
            } 
            return hashedText.toString(); 
        }


    public ArrayList<String> stringToArrayList (String longString, String delimit){
        /**
         * To change the split the list using delimit specify and then convert to ArrayList
         * @param the string to be splitted into array list
         * @param the delimit to be used to split the string
         * @return the arraylist or null if list is empty
         */
            String tmp [];
            ArrayList<String> theArrayList = new ArrayList<String>();
            tmp = longString.split(delimit);
            for (String s : tmp){
                theArrayList.add(s);
            }
            if (theArrayList.size() > 0){
                return theArrayList;
            }
            else{
                return null;
            }
        }


    public String arrayListToString (ArrayList<String> list, String delimit){
    /**
     * To change the combine arraylist with delimit to string
     * @param the arraylist to be combined into string
     * @param the delimit to be used to combine the string
     * @return the string or null
     */
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size() ; i++) {
            sb.append(list.get(i));
            if (i != list.size()-1) {
                sb.append(delimit);
            }
        }
        return sb.toString();
    }


    public String dateToString(LocalDate date){
    /**
     * To convert the date format to string format
     * @param the date
     * @return the string
     */
        String strDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return strDate;
    }


    public LocalDate stringToDate(String date){
    /**
     * To convert the string format to date format
     * @param the string
     * @return the local date
     */
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(date, dateFormatter);
        return localDate;
    }


    public ArrayList<String> indexToElement(ArrayList<Integer> index, ArrayList<String> list){
    /**
     * To find the element in the list using index.
     * @param the list of index
     * @param the list of elements
     * @return list with all elements from index; null if error
     */
        ArrayList<String> elements = new ArrayList<String>();
        for (Integer ind : index){
            try {
                String element = list.get(ind);
                elements.add(element);
            } catch (IndexOutOfBoundsException e){
                return null;
            }
        }
        return elements;
    }

    public ArrayList<Integer> convertUserIndToSysInd(ArrayList<String> index){
        /**
         * Convert the user input index, which starting from 1,
         * to programmer index, which starting from 0
         * @param the index list inputted by user in list of string
         * @return the index of programmer in list of integer
         */
        ArrayList<Integer> list = new ArrayList<>();
        for (String ind : index) {
            try {
                int number = Integer.parseInt(ind);
                number = number - 1; // since the input is entered by user, starting is 1
                list.add(number);
            }
            catch (NumberFormatException | NullPointerException e){
                return null;
            }
        }
        return list;
    }


    public boolean indexCheck(ArrayList<Integer> index, int listLength){
        /**
         * Check the bound and type of index inputted by user.
         * @param the index list inputted by user
         * @param the length of the list
         * @return the list of input starting from 0
         */
        boolean check = true;
        System.out.println(index);
        if (index != null){
            // check if the index invalid
            for (Integer ind : index) {
                if ((ind >= listLength) || (ind < 0)) {
                    check = false;
                }
            }
        }
        return check;
    }


    public void writeToCSV(String filePath, String [] dataToWrite, boolean bol){
        /**
         *  The method to write data to csv file
         * @param the file path of the file to write
         * @param the data to write to file
         * @boolean where true is append and false is overwrite
         */
            try {
                FileWriter fileWriter = new FileWriter(filePath, bol);
                CSVWriter writer = new CSVWriter(fileWriter);
                writer.writeNext(dataToWrite);
                writer.close();
            }
            catch (Exception e){
                System.out.println("File Write Error: " + e);
            }
        }


    public void updateCSVPaper (String filePath, String dataToUpdate, String emailAddress, String role, String confName){
        /**
         *  The method to update data in csv file
         * @param the file path of the file to update
         * @param the data to update to file
         * @param the matching string
         * @param the matching string
         * @boolean where true is append and false is overwrite
         */
        try {
            // read the data
            File theFile = new File(filePath);
            CSVReader csvReader = new CSVReader(new FileReader(theFile));
            List<String[]> csvData = csvReader.readAll();

            // get data to be replaced  at row (i) and column
            for (int i = 0; i < csvData.size() ; i++){
                String[] string = csvData.get(i);
                //col index 0 is role, col index 1 is email and col index 9 is conference name
                if(string[0].equalsIgnoreCase(role) && string[1].equalsIgnoreCase(emailAddress) && string[9].equals(confName)){
                    string[11] = dataToUpdate;
                }
            }
            csvReader.close();
            // Write to the CSV file
            CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath));
            csvWriter.writeAll(csvData);
            csvWriter.flush();
            csvWriter.close();
        }
        catch (Exception e){
            System.out.println("File Write Error: " + e);
        }
    }
}
