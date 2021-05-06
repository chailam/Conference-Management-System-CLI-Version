package cms;


public class Application {

    // Kick start the program
    public static void main(String args[]) {
        DataEntityController deC = new DataEntityController();
        deC.initializeConferenceManagementSystem();
        MainController cmsC = new MainController();
        cmsC.run();


//        Utility ut = new Utility();
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
