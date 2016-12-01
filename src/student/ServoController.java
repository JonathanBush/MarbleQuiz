/*
 * Java Embedded Raspberry Pi Servo app
 */
package student;

import java.io.FileWriter;
import java.io.File;

public class ServoController implements Runnable {

    static final String GPIO_OUT = "out";
    static final String GPIO_ON = "1";
    static final String GPIO_OFF = "0";
    static final int PERIOD = 20000000;

    static String[] GpioChannels =  { "18" };

    private int position;
    private double dutyCycle;

    public ServoController(int start) {
        this.setPosition(start);
    }

    public void setPosition(int position) {
        this.dutyCycle = .025 + .099*((double)position/180.);
        //this.position = position;
    }

    public void setDutyCycle(double dutyCycle) {
        this.dutyCycle = dutyCycle;
    }

    public void run() {
        FileWriter[] commandChannels;

        try {

            /*** Init GPIO port for output ***/

            // Open file handles to GPIO port unexport and export controls
            FileWriter unexportFile =
                    new FileWriter("/sys/class/gpio/unexport");
            FileWriter exportFile =
                    new FileWriter("/sys/class/gpio/export");

            // Loop through all ports if more than 1
            for (String gpioChannel : GpioChannels) {
                System.out.println(gpioChannel);

                // Reset the port, if needed
                File exportFileCheck =
                        new File("/sys/class/gpio/gpio"+gpioChannel);
                if (exportFileCheck.exists()) {
                    unexportFile.write(gpioChannel);
                    unexportFile.flush();
                }

                // Set the port for use
                exportFile.write(gpioChannel);
                exportFile.flush();

                // Open file handle to port input/output control
                FileWriter directionFile =
                        new FileWriter("/sys/class/gpio/gpio" + gpioChannel +
                                "/direction");

                // Set port for output
                directionFile.write(GPIO_OUT);
                directionFile.flush();
            }

            /*** Send commands to GPIO port ***/

            // Set up a GPIO port as a command channel
            FileWriter commandChannel = new
                    FileWriter("/sys/class/gpio/gpio" +
                    GpioChannels[0] + "/value");

            // Set initial variables for PWM
            int period = 20;
            int repeatLoop = 25;

            int counter;

            // Loop forever to create Pulse Width Modulation - PWM
            while (true) {

                int nshigh = (int)(PERIOD * dutyCycle);
                int nslow = PERIOD - nshigh;
                int mshigh = nshigh / 1000000;
                int mslow = nslow / 1000000;
                nshigh %= 1000000;
                nslow %= 1000000;

                commandChannel.write(GPIO_ON);
                commandChannel.flush();
                java.lang.Thread.sleep(mshigh, nshigh);
                commandChannel.write(GPIO_OFF);
                commandChannel.flush();
                java.lang.Thread.sleep(mslow, nslow);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}