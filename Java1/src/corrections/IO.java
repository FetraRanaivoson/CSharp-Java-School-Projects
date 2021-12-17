package corrections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IO
{
	// Input
	
	public static String readLine() throws IOException
	{
		return new BufferedReader(new InputStreamReader(System.in)).readLine();
	}

	public static int parseInt() throws IOException, NumberFormatException
	{
		return Integer.parseInt(readLine());
	}

	public static double parseDouble() throws IOException, NumberFormatException
	{
		return Double.parseDouble(readLine());
	}

	public static String getLine()
	{
		try
		{
			return readLine();	
		}
		catch (IOException e)
		{
			println("Input/output exception occurred. Returning empty string.");
		}
		return "";
	}

	public static int getInt()
	{
		try
		{
			while (true)
			{
				try
				{
					return parseInt();
				}
				catch (NumberFormatException e)
				{
					println("Invalid integer format. Try again:");
				}
			}			
		}
		catch (IOException e)
		{
			println("Input/output exception occurred. Returning 0.");
		}
		return 0;
	}

	public static double getDouble()
	{
		try
		{
			while (true)
			{
				try
				{
					return parseDouble();
				}
				catch (NumberFormatException e)
				{
					println("Invalid double floating-point format. Try again:");
				}
			}			
		}
		catch (IOException e)
		{
			println("Input/output exception occurred. Returning 0.");
		}
		return 0;
	}

	// Output

	public static void print(String s)
	{
		System.out.print(s);
	}

	public static void print(int i)
	{
		print(Integer.toString(i));
	}

	public static void print(double d)
	{
		print(Double.toString(d));
	}


	public static void println(String s)
	{
		System.out.println(s);
	}

	public static void println(int i)
	{
		println(Integer.toString(i));
	}

	public static void println(double d)
	{
		println(Double.toString(d));
	}
}
