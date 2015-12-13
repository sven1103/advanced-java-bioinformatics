package models;

import java.lang.reflect.AnnotatedArrayType;
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


    public static final Map<String, Integer> PYRIMIDINE_MAPPING;
    static
    {
        PYRIMIDINE_MAPPING = new HashMap<>();
        PYRIMIDINE_MAPPING.put("N1", 0);
        PYRIMIDINE_MAPPING.put("C2", 3);
        PYRIMIDINE_MAPPING.put("N3", 6);
        PYRIMIDINE_MAPPING.put("C4", 9);
        PYRIMIDINE_MAPPING.put("C5", 12);
        PYRIMIDINE_MAPPING.put("C6", 15);
    }

    public static final Map<String, Integer> PURINE_MAPPING;
    static
    {
        PURINE_MAPPING = new HashMap<>();
        PURINE_MAPPING.put("N1",0);
        PURINE_MAPPING.put("C2",3);
        PURINE_MAPPING.put("N3",6);
        PURINE_MAPPING.put("C4",9);
        PURINE_MAPPING.put("C5",12);
        PURINE_MAPPING.put("C6",15);
        PURINE_MAPPING.put("N7",18);
        PURINE_MAPPING.put("C8",21);
        PURINE_MAPPING.put("N9",24);
    }

}
