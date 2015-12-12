package models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sven on 12/12/15.
 */
public class AtomMapping {

    public static final Map<String, Integer> RIBOSE_MAPPING;
    static
    {
        RIBOSE_MAPPING = new HashMap<>();
        RIBOSE_MAPPING.put("C1'", 0);
        RIBOSE_MAPPING.put("C2'", 3);
        RIBOSE_MAPPING.put("C3'", 6);
        RIBOSE_MAPPING.put("C4'", 9);
        RIBOSE_MAPPING.put("O4'", 12);
    }

}
