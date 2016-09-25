import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class LSH {
	
	public  ArrayList<HashMap<Integer, ArrayList<String>>> bandTables;
	public  int bands;
	public int[][]minHashMatrix;
	public String[] docNames;
	
// For every document dividing rows into b bands and mapping the rows into the hash table

	public  LSH(int[][] minHashMatrix, String[] docNames, int bands)
	{
		this.bands=bands;
		this.minHashMatrix=minHashMatrix;
		this.docNames = docNames;
		bandTables= new ArrayList<HashMap<Integer, ArrayList<String>>>(bands);
		int index=0;
		int a;
		int b;
		Random random = new Random();
		//bandTables.addAll(null);
		int totalnNumberOfPermutations= minHashMatrix.length;
		//number of rows in each band
		int numberOfRowsInEachBand = totalnNumberOfPermutations/bands;
		if (!(numberOfRowsInEachBand*bands == totalnNumberOfPermutations) )
			numberOfRowsInEachBand = numberOfRowsInEachBand+1;
		int sizeOfHashTable=Prime.prime(docNames.length);
		a = random.nextInt(sizeOfHashTable)+0;
		b = random.nextInt(sizeOfHashTable)+0;;
		for(int i=0;i< bands;i++)
		{
			bandTables.add(new HashMap<Integer,ArrayList<String>>());
		}
		for (int i=0;i<docNames.length;i++)
		{
			for (int bandNumber=0;bandNumber<bands;bandNumber++)
			{
				index=0;
				HashMap<Integer, ArrayList<String>> hashTable = new HashMap<Integer, ArrayList<String>>(sizeOfHashTable);
				hashTable = bandTables.get(bandNumber);
			for (int k =bandNumber*numberOfRowsInEachBand;k<((bandNumber+1)*numberOfRowsInEachBand)&&(k<totalnNumberOfPermutations-1);k++)
			{
                index = (index + (a*minHashMatrix[k][i]+b))%sizeOfHashTable;
				//sumOfValuesInEachBand = sumOfValuesInEachBand + minHashMatrix[k][i];
			}
			index = index%sizeOfHashTable;

			ArrayList<String> docNamesInTable= new ArrayList<String>();
			if(!(hashTable.get(index)== null))
			{
			docNamesInTable = hashTable.get(index);
			if (!hashTable.get(index).contains(docNames[i]) )
			docNamesInTable.add(docNames[i]);
			}
			docNamesInTable.add(docNames[i]);
			hashTable.put(index, docNamesInTable);
			//bandTables.get(bandNumber).get(index).
			bandTables.get(bandNumber).replace(index, docNamesInTable);
				
			}
		}
		
	}
	/* For the passed document we are seeing in each table of each band if this document is present. If present then
	* we are appending documents in that list to an array of strings
	*/
	String[] nearDuplicatesOf(String docName)
	{
		ArrayList<String> nearDuplicateDocuments = new ArrayList<String>();
		String[] nearDuplicateDocumentsArray;
		
		for (int i=0;i<bandTables.size();i++)
		{
			for (int j: bandTables.get(i).keySet() )
			{
			  if(bandTables.get(i).get(j) != null)	
			  {
				if (bandTables.get(i).get(j).contains(docName))
				{
					for (String str: bandTables.get(i).get(j) )
					{
						if(!nearDuplicateDocuments.contains(str))
					nearDuplicateDocuments.add(str);
					}
				}
				
				}
			}
		}
		nearDuplicateDocumentsArray = new String[nearDuplicateDocuments.size()];
		
		return nearDuplicateDocuments.toArray(nearDuplicateDocumentsArray);
	}
	
/*	public static void main(String args[]) 
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
		System.out.println("Enter folder Path: ");
		String folderName = reader.readLine();
		String[] nearDuplicates ; 
		System.out.println("Enter number of permutations");
		int numberOfPermutations = Integer.parseInt(reader.readLine());
		MinHash temp = new MinHash(folderName,numberOfPermutations);
		temp.allDocs();
		LSH l = new LSH(temp.minHashMatrix(),temp.docarray,15);
		nearDuplicates = l.nearDuplicatesOf("space-0.txt");
		System.out.println(Arrays.deepToString(nearDuplicates));
		System.out.println("Length" + nearDuplicates.length);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} */
	}
	


