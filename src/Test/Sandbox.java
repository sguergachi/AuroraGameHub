package Test;

import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JPanel;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

// Code from "http://theuzo007.wordpress.com/2013/10/26/joystick-in-java-with-jinput-v2/"

public class Sandbox {

    private static ArrayList<Controller> foundControllers = new ArrayList<>();

    public static void main(String args[]) {
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

        for (int i = 0; i < controllers.length; i++) {
            Controller controller = controllers[i];

            if (controller.getType() == Controller.Type.STICK
                || controller.getType() == Controller.Type.GAMEPAD
                || controller.getType() == Controller.Type.WHEEL
                || controller.getType() == Controller.Type.FINGERSTICK) {

                System.out.println(controller.getType());

                // Add new controller to the list of all controllers.
                foundControllers.add(controller);

            }
        }
        startShowingControllerData();
    }

    /**
     * Starts showing controller data on the window.
     */
    public static void startShowingControllerData() {
        while (true) {
            // Currently selected controller.
            Controller controller = foundControllers.get(0);

            // Pull controller for current data, and break while loop if controller is disconnected.
            if (!controller.poll()) {
                System.out.println("Controller Disconected");

                break;
            }

            // X axis and Y axis
            int xAxisPercentage = 0;
            int yAxisPercentage = 0;
            // JPanel for other axes.
            JPanel axesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 2));
            axesPanel.setBounds(0, 0, 200, 190);

            // JPanel for controller buttons
            JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 1));
            buttonsPanel.setBounds(6, 19, 246, 110);

            System.out.println("========== CONTROLLER DEBUGER ==============");


            // Go trough all components of the controller.
            Component[] components = controller.getComponents();
            for (int i = 0; i < components.length; i++) {
                Component component = components[i];
                Component.Identifier componentIdentifier = component.getIdentifier();

                // Buttons
                //if(component.getName().contains("Button")){ // If the language is not english, this won't work.
                if (componentIdentifier.getName().matches("^[0-9]*$")) { // If the component identifier name contains only numbers, then this is a button.
                    // Is button pressed?
                    boolean isItPressed = true;
                    if (component.getPollData() == 0.0f) {
                        isItPressed = false;
                    }

                    // Button index
                    String buttonIndex;
                    buttonIndex = component.getIdentifier().toString();

                    // Create and add new button to panel.
                    System.out.println("button Press  " + buttonIndex + " isPressed? " + isItPressed);


                    // We know that this component was button so we can skip to next component.
                    continue;
                }

                // Hat switch
                if (componentIdentifier == Component.Identifier.Axis.POV) {
                    float hatSwitchPosition = component.getPollData();
                    System.out.println("Hatswitch Position " + hatSwitchPosition);

                    // We know that this component was hat switch so we can skip to next component.
                    continue;
                }

                // Axes
                if (component.isAnalog()) {
                    float axisValue = component.getPollData();
                    int axisValueInPercentage = getAxisValueInPercentage(axisValue);

                    // X axis
                    if (componentIdentifier == Component.Identifier.Axis.X) {
                        xAxisPercentage = axisValueInPercentage;
                        System.out.println("xAxisPercentage " + xAxisPercentage);
                        continue; // Go to next component.
                    }
                    // Y axis
                    if (componentIdentifier == Component.Identifier.Axis.Y) {
                        yAxisPercentage = axisValueInPercentage;
                        System.out.println("yAxisPercentage " + yAxisPercentage);

                        continue; // Go to next component.
                    }

                    // Other axis
                    System.out.println("axisValueInPercentage " + axisValueInPercentage);
                }



            }

            System.out.println("================ END ==================");
            // We have to give processor some rest.
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
            }

        }
    }

    /**
     * Given value of axis in percentage.
     * Percentages increases from left/top to right/bottom.
     * If idle (in center) returns 50, if joystick axis is pushed to the left/top
     * edge returns 0 and if it's pushed to the right/bottom returns 100.
     * <p>
     * @return value of axis in percentage.
     */
    public static int getAxisValueInPercentage(float axisValue) {
        return (int) (((2 - (1 - axisValue)) * 100) / 2);
    }
}
