/**
 * The utility class that has all tools required.
 */

package cms;

import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        for (String st : list) {
            sb.append(st);
            sb.append(delimit);
        }
        return sb.toString();
    }


    public String dateToString(Date date){
    /**
     * To convert the date format to string format
     * @param the date
     * @return the string
     */
        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
        String str = dateFormat.format(date);
        return str;
    }


    public ArrayList<String> indexToElement(ArrayList<String> index,ArrayList<String> list){
    /**
     * To find the element in the list using index
     * @param the list of index
     * @param the list of elements
     * @return list with all elements from index
     */
        ArrayList<String> elements = new ArrayList<String>();
        for (String ind : index){
            int no = Integer.parseInt(ind);
            String element = list.get(no);
            elements.add(element);
        }
        return elements;
    }


    public boolean indexCheck (ArrayList<String> index, int listLength){
        /**
         * Check the bound and type of index.
         * @param the index list
         * @param the length of the list
         * @return true if index valid, false otherwise
         */
        boolean check = true;
        // check if the index invalid
        for (String ind : index) {
            try {
                int no = Integer.parseInt(ind);
                if (no > listLength || no < 0) {
                    check = false;
                }
            } catch (NumberFormatException e) {
                check = false;
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
                for (int i = 0; i < dataToWrite.length; i++){
                    fileWriter.append(dataToWrite[i]);
                    if (i != dataToWrite.length-1){
                        // no need to append comma for last item
                        fileWriter.append(",");
                    }
                    else{
                        fileWriter.append("\n");
                    }
                }
                fileWriter.flush();
                fileWriter.close();
            }
            catch (Exception e){
                System.out.println("File Write Error: " + e);
                System.exit(0);
            }
        }
}
