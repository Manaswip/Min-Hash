
import java.io.*;

//Used to calculate the Prime number greater than the passed value.
public class Prime {
	
	public static int prime(int maxValueOfX)
	{
		
		while(true)
		{
			boolean isPrime=true;
			maxValueOfX = maxValueOfX+1;
			int sqt = (int)Math.sqrt(maxValueOfX);
			for(int i=2;i<=sqt;i++)
			{
				if(maxValueOfX%i==0)
				{
					isPrime=false;
					break;
				}
			}
			
			if(isPrime)
			{
				break;
			}
			
	}
		return maxValueOfX;
	}
	
    public static void main(String[] args) throws Exception {
    	BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
    	System.out.println("Enter bits per element");
		int num=Integer.parseInt(r.readLine());
		
		
		}

 	 
    	
    }

