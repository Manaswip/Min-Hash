import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class NearDuplicates {
	
	public static void main(String args[]) 
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			
			System.out.println("Enter folder Path: ");
			String folderName = reader.readLine();
			
			System.out.println("Enter number of permutations");
			int numberOfPermutations = Integer.parseInt(reader.readLine());
			
			System.out.println("Enter number of Bands: ");
			int bands = Integer.parseInt(reader.readLine());
			
			System.out.println("Enter Similarity threshold: ");
			float simThreshold = Float.parseFloat(reader.readLine());
			
			System.out.println("Enter Document Name: ");
			String docName = reader.readLine();
			
			ArrayList<String> removeFalsePositivies = new ArrayList<String>();
			String[] removeFalsePositiviesArray;
			String[] nearDuplicates ;
			int index=0;
			
			// Calling MinHash class in order to calculate MinHash matrix for the documents in the folder
			MinHash temp = new MinHash(folderName,numberOfPermutations);
			temp.allDocs();
            // Calling LSH class to find the near Duplicates
			LSH lshInstance = new LSH(temp.minHashMatrix(),temp.docarray,bands);
			nearDuplicates=lshInstance.nearDuplicatesOf(docName);
			System.out.println("near duplicates before eliminating false positives are");
			for(int i=0;i<nearDuplicates.length;i++)
			{
				System.out.println(nearDuplicates[i]);
			}
			
		    ArrayList<String> nearDuplicateList = new ArrayList<String>(Arrays.asList(nearDuplicates));
			for (int i = 0; i < nearDuplicates.length; i++)
			{		
				//Calculating approximate Jaccard Similarity to eliminate false positives
					double approximateJaccard=temp.approximateJaccard(docName, nearDuplicates[i]);
					if(approximateJaccard< simThreshold)
					{						 
						removeFalsePositivies.add(nearDuplicates[i]);
						index=index+1;
					}
			}
			removeFalsePositiviesArray = new String[removeFalsePositivies.size()];
			removeFalsePositivies.toArray(removeFalsePositiviesArray);
			nearDuplicateList.removeAll(removeFalsePositivies);
			String[] nearDuplicate = new String[nearDuplicateList.size()];
			nearDuplicateList.toArray(nearDuplicate);
			
			System.out.println("Near Duplicates after eliminating false positives are  " );
			for (int i=0;i<nearDuplicate.length;i++)
			{
				System.out.println(nearDuplicate[i]);
			}
			
			System.out.println("Number of false positives: " + removeFalsePositiviesArray.length );
			//System.out.println("false positive rate: " +(double) removeFalsePositiviesArray.length/ (double) nearDuplicates.length );
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
