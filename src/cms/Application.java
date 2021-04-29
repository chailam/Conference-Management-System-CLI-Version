package cms;

import cms.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Application {

    // Kick start the program
    public static void main(String args[]) throws ParseException {
        DataEntityController deC = new DataEntityController();
        deC.initializeConferenceManagementSystem();
        BoundaryController cmsC = new BoundaryController();
        cmsC.run();

//
//        Utility ut = new Utility();
//        System.out.println(ut.stringToDate("01/01/1111"));
//        System.out.println(ut.dateToString(ut.stringToDate("01/01/1111")));


    }
}
