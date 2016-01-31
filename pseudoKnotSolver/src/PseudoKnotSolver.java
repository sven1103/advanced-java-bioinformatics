import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sven on 1/31/16.
 */
public class PseudoKnotSolver {


    public static List<Pair<Integer, Integer>> removePseudoknots(List<Pair<Integer, Integer>> basePairs){
        if(basePairs.isEmpty()){
            return basePairs;
        }

        for(Pair bond : basePairs){
            for(Pair otherBond : basePairs){
                if(isPseudoKnot(bond,otherBond)){
                    List copyBasePairs = new ArrayList<>(basePairs);
                    copyBasePairs.remove(otherBond);
                    basePairs.remove(bond);
                    List list1 = removePseudoknots(basePairs);
                    List list2 = removePseudoknots(copyBasePairs);
                    if(list1.size()> list2.size()){
                        return list1;
                    } else {
                        return list2;
                    }
                }
            }
        }
        return basePairs;
    }


    private static boolean isPseudoKnot(Pair<Integer, Integer> pair,
                                        Pair<Integer, Integer> otherPair){

        if(pair.equals(otherPair)){
            return false;
        }

        int i,j,iPrime, jPrime;

        if(pair.getKey() > otherPair.getKey()){
            iPrime = pair.getKey();
            jPrime = pair.getValue();
            i = otherPair.getKey();
            j = otherPair.getValue();
        } else{
            i = pair.getKey();
            j = pair.getValue();
            iPrime = otherPair.getKey();
            jPrime = otherPair.getValue();
        }



        // i<j<i'<j'
        if(((i<j) && (iPrime<jPrime)) && (j<iPrime)){
            return false;
        }
        // i<i'<j'<j
        if(((i<iPrime) && (jPrime < j)) && (iPrime<jPrime)){
            return false;
        } else{
            // Pseudoknot found
            return true;
        }
    }



    public static void main(String[] args){
        List<Pair<Integer, Integer>> testList = new ArrayList<>();

        testList.add(new Pair<>(1,10));
        testList.add(new Pair<>(2,9));
        testList.add(new Pair<>(3,8));
        testList.add(new Pair<>(4,7));
        testList.add(new Pair<>(5,11));
        testList.add(new Pair<>(6,12));



        List newList = removePseudoknots(testList);

        newList.forEach(value -> System.out.println(value.toString()));

    }



}
