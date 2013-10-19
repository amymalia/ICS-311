package utils;

import main.DynamicSet;

/**Includes static methods*/
public class Utils<Type> {
	
	//used to emulate a switch on the inputs
	public enum Command{
		runtest, insert, search, delete, pred, succ, min, max, quit;
	}
	
	//used for the compareValue method
	public static final int GREATER = 1;
	public static final int LESSER = 2;
	public static final int EQUAL = 3;
	
	//These are used to determine the operations for runtest
	public static final int INSERT = 4;
	public static final int SUCCESSOR = 5;
	public static final int PREDECESSOR = 6;
	public static final int SEARCH = 7;
	public static final int MINIMUM = 8;
	public static final int MAXIMUM = 9;
	
	/* Helper method that compares strings*/
	public static int compareValue(String value, String nodeValue) {
		int compare = (value.compareToIgnoreCase(nodeValue));
		if (compare > 0) {
			return GREATER;
		}
		else if (compare == 0) {
			return EQUAL;
		}
		return LESSER;
	}
	public static double[] testSet(DynamicSet<String> set, String[] input, int operation) {
		double [] outputArray = new double [3];
		double sum = 0;
		double min = 1000000000000000000000000000.0;
		double max = 0;
		
		for (String word: input) {
			double start = System.nanoTime();
			//operation is based on input
			switch (operation) {
				case INSERT: set.insert(word,null); break; 
				case SEARCH: set.search(word); break;
				case SUCCESSOR: set.successor(word); break;
				case PREDECESSOR: set.predecessor(word); break;
			}
			
			double end = System.nanoTime();
			double time = end - start;
			if (time < min) {
				min = time;
			}
			if (time > max) {
				max = time;
			}
			sum = sum + time;
		}
		System.out.println("min:  " + min);
		System.out.println("max:  " + max);
		System.out.println("average:  " + sum/input.length);
		System.out.println("");
		outputArray[0] = min;
		outputArray[1] = max;
		outputArray[2] = sum/input.length;
		return outputArray;
	}
	
	public static double testMinMax(DynamicSet<String> set, int operation) {
		double start = System.nanoTime();
		if (operation == MINIMUM) {
			set.minimum();
		}
		if (operation == MAXIMUM) {
			set.maximum();
		}
		double end = System.nanoTime();
		System.out.println("time:  " + (end - start));
		return end - start;
	}
	/**Checks to see if command is valid*/
	public static boolean isEnum(String test) {

	    for (Command c : Command.values()) {
	        if (c.name().equals(test)) {
	            return true;
	        }
	    }
	    return false;
	}
}