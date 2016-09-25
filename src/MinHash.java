import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;

public class MinHash {
	public HashMap<String, Integer> unionAll = new HashMap<String, Integer>();
	public HashMap<String, int[]> fileTermsIntegerMapping = new HashMap<String, int[]>();
	public String docarray[] ;
	public int[][] minHash = null;
	public int unionSize;
	public int numPermutations;
	public String folderName;
	public int[] minHashCal;
	int hashFunctions[][];

// constructor of MinHash class which intializes number of permutations and folder name
	public MinHash(String folder, int noOfPermutation)
	{
		this.numPermutations = noOfPermutation;
		this.folderName = folder;
		docarray=this.allDocs();
		this.fetchAllTermsAndCalculateMinHash();
	}

// Returns names of all the files in the given folder
	public String[] allDocs() {
		
		File folderNames = new File(folderName);
        int i=0;
        File[] files = folderNames.listFiles();
        String[] documents = new String[files.length];
        for (File file: files)
        {
        	documents[i] = file.getName();
            i++;
        }
        return documents;	
	}

//Calculating exact Jaccord similarity using Min hash matrix
	public double exactJaccard(String file1, String file2) 
	{
		HashSet<Integer> termsInFile1 = new HashSet<Integer>();
		HashSet<Integer> termsInFile2 = new HashSet<Integer>();
		for (int term : fileTermsIntegerMapping.get(file1))
		{
			termsInFile1.add((Integer) term);
		}
		for (int term : fileTermsIntegerMapping.get(file2)) 
		{
			termsInFile2.add((Integer) term);
		}
		double unionSize = termsInFile1.size() + termsInFile2.size();
		termsInFile1.retainAll(termsInFile2);
		return termsInFile1.size() / (unionSize - termsInFile1.size());
	}

// Calculates and returns min hash signature for a given document
	public int[] minHashSig(String fileName)
	{
		int[] minHashSig = new int[numPermutations];
		int permutationValue;
		int[] terms = fileTermsIntegerMapping.get(fileName);
		for (int i = 0; i < numPermutations; i++) 
		{
			minHashSig[i] = Integer.MAX_VALUE;
			for (int j = 0; j < terms.length; j++) 
			{
				permutationValue = hashFunctions[i][terms[j]];
				if (minHashSig[i] > permutationValue)
					minHashSig[i] = permutationValue;
			}
		}
		return minHashSig;
	}

//Calculating approximate Jaccord similarity using Min hash matrix
	public double approximateJaccard(String file1, String file2) {
		int file1Col=-1,file2Col=-1;
		double count = 0;
		for (int i = 0; i < docarray.length; i++)
		{
			if (docarray[i].equals(file1)) 
				file1Col = i;
			if (docarray[i].equals(file2)) 
				file2Col = i;
		}
		for (int i = 0; i < numPermutations; i++) 
		{
			if (minHash[i][file1Col] == minHash[i][file2Col])
				count = count + 1;
		}
		return count / numPermutations;
	}

// Returns total number of distinct terms in all the documents
	public double numTerms()
	{
		return unionAll.size();
	}

// Returns total number of permutations	
	public double numPermutations()
	{
		return this.numPermutations;
	}

// Returns Min Hash Matrix of all the permutations on all the documents 
	public int[][] minHashMatrix()
	{
		return minHash;
	}

/* 
 * Fetching all the terms from all the documents and assigning unique integer value to each term. For each document we are 
 * storing the integer value of the terms in the document. Then applying Permutation and finding value permutation values 
 * for terms in all the documents and finally calculating Min hash values for all the permutations on all the documents
*/
	
	public void fetchAllTermsAndCalculateMinHash()
	{
		int a,b,prime,termGUI;
		Random random = new Random();
		termGUI = unionAll.size();
		minHash = new int[numPermutations][docarray.length];
		int[] minHashSignatureArray;
		for (int j = 0; j < docarray.length; j++)
		{
			HashSet<String> terms = buildsetofterms(folderName + File.separator + docarray[j]);
			ArrayList<Integer> indexList = new ArrayList<Integer>();
			for (String term : terms) 
			{
				if (unionAll.containsKey(term))
				{
					int indexVal = (int) unionAll.get(term);
					indexList.add(indexVal);

				} 
				else 
				{
					unionAll.put(term, termGUI);
					indexList.add(termGUI);
					termGUI++;
				}
			}
			int[] termMappingArray = new int[indexList.size()];
			for (int i = 0; i < indexList.size(); i++) {
				termMappingArray[i] = indexList.get(i);
			}
			this.fileTermsIntegerMapping.put(docarray[j], termMappingArray);
		}

//Applying Permutation and finding value permutation values for terms in all the documents
		prime = Prime.prime(unionAll.size());
		hashFunctions = new int[numPermutations][unionAll.size()];
		for (int i = 0; i < numPermutations; i++) 
		{
			a = random.nextInt(prime)+0;
			b = random.nextInt(prime)+0;
			for (int j = 0; j < unionAll.size(); j++) 
			{
				hashFunctions[i][j] = ((a * j) + b) % prime;
			}
		}
// Calling MinHashSig method to calculate Min hash values for all the permutations on all the documents 		
		for (int j = 0; j < docarray.length; j++) {
			String fileName = docarray[j];
			minHashSignatureArray = minHashSig(fileName);
			for (int i = 0; i < numPermutations; i++) {
				minHash[i][j] = minHashSignatureArray[i];
			}
		}
	}

// Fetching all the terms for the passed documents by ignoring ".", "," ,":" ,"'" ,"the" ,"words of length less than 2"
	private HashSet<String> buildsetofterms(String filename)
	{
		String readLine, terms[];
        HashSet<String> fileTerms = new HashSet<String>();
        try 
        {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            while ((readLine = reader.readLine()) != null)
            {
                terms = (readLine.toLowerCase().replaceAll("[.,:;'\"]", " ").split(" +"));
                for (String term : terms)
                {
                    if (term.length() > 2 && !term.equals("the"))
                        fileTerms.add(term);
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return fileTerms;
    }

}
