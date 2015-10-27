package visualization;

import alignmentletters.Sequence;
import java.util.ArrayList;

/**
 * Created by fillinger on 10/27/15.
 */
public class Visualization {

    /**
     * Prints the sequence alignment nicely on the console
     * @param sequenceList
     */
    public static String printAlignment(ArrayList<Sequence> sequenceList, int width) throws NullPointerException{

        if (sequenceList.isEmpty() || sequenceList.equals(null)) {
            throw new NullPointerException();
        }

        StringBuilder alignmentString = new StringBuilder();

        int lengthAlignment = sequenceList.get(0).getLength();

        int numberLines = (int) Math.ceil((double) lengthAlignment / width);

        String leftMargin = Settings.showHeaders() ? "%-30s " : "%s ";

        for(int line = 1; line < numberLines; line++){
            if(Settings.showNumbering()) {
                alignmentString.append((String.format(leftMargin + "%-" + (width - 1) + "s %s\n", "", (line - 1) * width + 1, line * width)));
            }
            if(Settings.showSequence()) {
                for (Sequence seq : sequenceList) {
                    alignmentString.append((String.format(leftMargin + "%s\n", Settings.showHeaders() ? seq.getName() : "",
                            seq.getSequence((line - 1) * width, line * width))));
                }
                alignmentString.append("\n");
            }
        }

        /*
        Handle the last alignment line
         */
        if(Settings.showNumbering()){
            alignmentString.append((String.format(leftMargin + "%-" + (Math.floorMod(lengthAlignment, width) - 2) + "s %s\n", "", lengthAlignment - Math.floorMod(lengthAlignment, width) + 1, lengthAlignment)));
        }
        if (Settings.showSequence()) {
            for (Sequence seq : sequenceList) {
                alignmentString.append((String.format(leftMargin + "%s\n", Settings.showHeaders() ? seq.getName() : "",
                        seq.getSequence(lengthAlignment - Math.floorMod(lengthAlignment, width), lengthAlignment - 1))));            }
        }

     return alignmentString.toString();

    }


}
