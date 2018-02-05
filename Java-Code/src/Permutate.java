import java.util.*;

public class Permutate {
    public static void main(String args[]) {
        String[] letters = {"A","B","C","D","E","F","G","H"};
        permutate(letters);
    }
    
    public static ArrayList<ArrayList<String>> permutate(String[] array) {
    	ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
    	permutate(array, 0, result);
    	return result;
    }
 
    private static void permutate(String[] array, int counter, ArrayList<ArrayList<String>> result) {
    	if (counter >= array.length ) {
    		ArrayList<String> newArr = new ArrayList<String>();
			for (int i = 0; i < array.length; i++) {
				newArr.add(array[i]);
			}
			
			/* I recommend using "java Permutate > result.txt" on cmd after compiling
			Check current directory for result.txt after*/
			
			System.out.println(Arrays.toString(newArr.toArray()));
    		result.add(newArr);
    	}
    	for (int j = counter; j <= array.length-1; j++) {
    		if (hasDuplicate(array, counter, j)) {
    			swap(array, counter, j);
    			permutate(array, counter + 1, result);
    			swap(array, counter, j);
    		}
    	}
    }
     
	private static void swap(String[] array, int i, int j) {
    	String temp = array[i];
    	array[i] = array[j];
    	array[j] = temp;
    }
	
    private static boolean hasDuplicate(String[] array, int counter, int j) {
    	for (int i = counter; i <= j-1; i++) {
    		if (array[i] == array[j])
    			return false;
    	}
    	return true;
    }

}

