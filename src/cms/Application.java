package cms;


import java.util.ArrayList;
import java.util.Arrays;

public class Application {

    // Kick start the program
    public static void main(String args[]) {
        DataEntityController deC = new DataEntityController();
        deC.initializeConferenceManagementSystem();
        BoundaryController cmsC = new BoundaryController();
        cmsC.run();


//        Utility ut = new Utility();
//        ArrayList<String> avaiableTopics = new ArrayList<>(Arrays.asList("Software Engineering ", "Security & Cryptography", "Robotics & Automation", "Database & Information Systems"));
//
//        System.out.println(ut.arrayListToString(avaiableTopics,","));
//        String[] aa = {"hihi","haha",null,"null",""};
//        ut.writeCSV("src/resource/Paper.csv",aa,true);
//
//        List<String[]> bb = ut.readCSV ("src/resource/Paper.csv");
//        for (String[] ss:bb){
//            System.out.println(ss[2] + "  " + ss[3] + "  " + ss[4]);
//            System.out.println(ss[2] == null);
//            System.out.println(ss[3] == null);
//            System.out.println(ss[4] == null);
//            System.out.println(ss[2] == "null");
//            System.out.println(ss[3] == "null");
//            System.out.println(ss[4] == "null");
//            System.out.println(ss[2] == ""); //true
//            System.out.println(ss[3] == "");
//            System.out.println(ss[4] == "");//true
//        }
    }
}
