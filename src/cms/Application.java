package cms;

import cms.*;

public class Application {

    // Kick start the program
    public static void main(String args[]) {
        DataEntityController deC = new DataEntityController();
        deC.initializeConferenceManagementSystem();
        BoundaryController cmsC = new BoundaryController();
        cmsC.run();
    }
}
    