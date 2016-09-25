import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MinHashSpeed 
{
	public static void main(String args[]) 
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			
			System.out.println("Enter folder Path: ");
			String folderName = reader.readLine();
			
			System.out.println("Enter number of permutations");
			int numberOfPermutations = Integer.parseInt(reader.readLine());
			
			MinHash temp = new MinHash(folderName,numberOfPermutations);
			temp.allDocs();
			
			double startInstance, endInstance;
			System.out.println("Started computing exact Jaccard Similarity");
// Time Exact Jaccard Similarity			
			startInstance = System.nanoTime();
			for (int i = 0; i < temp.docarray.length; i++)
			{
				for (int j = i + 1; j < temp.docarray.length; j++)
				{					
					double exactJaccard=temp.exactJaccard(temp.docarray[i], temp.docarray[j]);
				}
			}
			endInstance = System.nanoTime();
			System.out.println("Time taken to calculate exact Jaccard Similarity: "+ (endInstance-startInstance)/1E9 + " seconds");

//Time for Approximate Jaccard Similarity	
			System.out.println("Started computing approximate Jaccard Similarity");
			startInstance = System.nanoTime();
			for (int i = 0; i < temp.docarray.length; i++)
			{
				for (int j = i + 1; j < temp.docarray.length; j++)
				{					
					double approximateJaccard=temp.approximateJaccard(temp.docarray[i], temp.docarray[j]);
				}
			}
			endInstance = System.nanoTime();
			System.out.println("Time taken to calculate exact Jaccard Similarity: "+ (endInstance-startInstance)/1E9 + " seconds");

		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
