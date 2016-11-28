package student;
/*
 * Java Embedded Raspberry Pi Servo app
 */

        import java.io.FileWriter;
        import java.io.File;

/**
 *
 * @author hinkmond
 */
public class ServoController {

    static final String GPIO_OUT = "out";
    static final String GPIO_ON = "1";
    static final String GPIO_OFF = "0";

    private FileWriter commandChannel;

    static String[] GpioChannels =  { "24" };

    @SuppressWarnings("UseSpecificCatch")
    public ServoController() {
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
            commandChannel = new
                    FileWriter("/sys/class/gpio/gpio" +
                    GpioChannels[0] + "/value");

            // Set initial variables for PWM
            int period = 20;
            int repeatLoop = 25;

            int counter;

            // Loop forever to create Pulse Width Modulation - PWM
            while (true) {

                /*--- Move servo clockwise to 90 degree position ---*/

                // Create a pulse for repeatLoop number of cycles
                for (counter=0; counter<repeatLoop; counter++) {

                    // HIGH: Set GPIO port ON
                    commandChannel.write(GPIO_ON);
                    commandChannel.flush();

                    // Pulse Width determined by amount of
                    //   sleep time while HIGH
                    java.lang.Thread.sleep(0, 800000);

                    // LOW: Set GPIO port OFF
                    commandChannel.write(GPIO_OFF);
                    commandChannel.flush();

                    // Frequency determined by amount of
                    //  sleep time while LOW
                    java.lang.Thread.sleep(period);
                }

                /*--- Move servo counterclockwise to 0
				degree position ---*/

                // Create a pulse for repeatLoop number of cycles
                for (counter=0; counter<repeatLoop; counter++) {

                    // HIGH: Set GPIO port ON
                    commandChannel.write(GPIO_ON);
                    commandChannel.flush();

                    // Pulse Width determined by amount of
                    //   time while HIGH
                    java.lang.Thread.sleep(2, 200000);

                    // LOW: Set GPIO port OFF
                    commandChannel.write(GPIO_OFF);
                    commandChannel.flush();

                    // Frequency determined by amount of
                    //  time while LOW
                    java.lang.Thread.sleep(period);
                }
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
