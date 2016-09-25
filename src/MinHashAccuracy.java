import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class MinHashAccuracy 
{
	public static void main(String args[]) 
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			
			System.out.println("Enter folder Path: ");
			String folderName = reader.readLine();
			
			System.out.println("Enter number of permutations");
			int numberOfPermutations = Integer.parseInt(reader.readLine());
			
			System.out.println("Enter Error Parameter: ");
			double errorParameter = Float.parseFloat(reader.readLine());
			
			MinHash temp = new MinHash(folderName,numberOfPermutations);
			temp.allDocs();
			
			int count=0;
// calculating exact similarity and jaccard similiarity for a document in the folder with every other document in the folder
			for (int i = 0; i < temp.docarray.length; i++)
			{
				for (int j = i + 1; j < temp.docarray.length; j++)
				{	//exact Jaccard similarity				
					double exactJaccard=temp.exactJaccard(temp.docarray[i], temp.docarray[j]);
					// Approximate Jaccard similarity
					double approximateJaccard=temp.approximateJaccard(temp.docarray[i], temp.docarray[j]);
					double simDiff=Math.abs(exactJaccard-approximateJaccard);
					if(simDiff>errorParameter)
					{						 
						count++;
					}
				}
			}
			System.out.println("No of Dissimilar Pairs: " + count);
			//System.out.println(Arrays.deepToString(temp.minHashMatrix()));
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
