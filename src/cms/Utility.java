/**
 * The utility class that has all tools required.
 */

package cms;

import java.security.NoSuchAlgorithmException;
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
         * @return the arraylist or null
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
            }
        }
}
