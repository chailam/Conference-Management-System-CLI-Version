package cms;

import cms.*;
import jdk.jshell.execution.Util;


public class Application {

    // Kick start the program
    public static void main(String args[]) {
        DataEntityController deC = new DataEntityController();
        deC.initializeConferenceManagementSystem();
        BoundaryController cmsC = new BoundaryController();
        cmsC.run();

//        Utility ut = new Utility();
//        String [] aaa = {"hi","ha","null",null,""};
//        ut.writeToCSV("src/resource/Paper.csv",aaa,true);

    }
}
