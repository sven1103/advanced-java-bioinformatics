import org.apache.commons.cli.*;

/**
 * Created by sven on 10/18/15.
 */

public class CommandLine {
    private static double VERSION = 0.1;
    private String CLASS_NAME = this.getClass().getSimpleName();


    public static void main(String[] args){

        Options opt = new Options();

        System.out.println("Sven's program v" + VERSION);
        // the command line parameters
        Options helpOptions = new Options();
        helpOptions.addOption("h", "help", false, "show this help page");
        Options options = new Options();
        options.addOption("h", "help", false, "show this help page");
        options.addOption("i", "input", true, "the input file if this option is not specified,\nthe input is expected to be piped in");
        options.addOption("o", "output", true, "the output folder. Has to be specified if input is set.");
        HelpFormatter helpformatter = new HelpFormatter();
        CommandLineParser parser = new BasicParser();
        try {
            org.apache.commons.cli.CommandLine cmd = parser.parse(helpOptions, args);
            if (cmd.hasOption('h')) {
                helpformatter.printHelp("Sven's Program", options);
                System.exit(0);
            }
        } catch (ParseException e1) {
        }
        }

}
