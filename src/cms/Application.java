/**
 * The java file which includes tha main function to start the program
 */

package cms;

import cms.controller.MainController;

public class Application {

    // Kick start the program
    public static void main(String args[]) {
        MainController mainController = new MainController();
        mainController.run();
    }
}
