
// Duplicate file and Empty File Cleaner Project in java

import java.lang.*;
//for collection framework
import java.util.*;
//for  directory traversal
import java.io.*;
//for file reading
import java.io.FileInputStream;
//for checksome
import java.security.MessageDigest;

class Demo
{
	public static void main(String args[]) throws Exception
	{
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	System.out.println("Please Enter the Directory name");
	String dir = br.readLine();

	Cleaner cobj = new Cleaner(dir);

	cobj.CleanDirectoryEmptyFile();
	cobj.CleanDirectoryDuplicateFile();
	}
};

class Cleaner
{
	public File fdir=null;
	public Cleaner(String name)
	{
		fdir=new File(name);
		if(!fdir.exists())
		{
			System.out.println("Invalid Directory Name");
			System.exit(0);
		}
	}
	public void CleanDirectoryEmptyFile() 
	{
		File filelist[]= fdir.listFiles();
		int EmptyFile=0;
		for(File file : filelist)
		{

			if(file.length()==0)
			{
				System.out.println("Empty File name : "+file.getName());
				if(!file.delete())
				{
					System.out.println("Unable to Delete");
				}
				else
				{
					EmptyFile++;
				}
			}
		}
		System.out.println("Total empty files deleted "+EmptyFile);
	}

	public void CleanDirectoryDuplicateFile() throws Exception
   {
			File filelist[]= fdir.listFiles();
			int DupFile=0;
			//bucket to read the data
			byte bytearr[]=new byte[1024];

			// create linked list of strings
			LinkedList<String>lobj=new LinkedList<String>();
		
			int Rcount=0;
		try
		{
			MessageDigest digest = MessageDigest.getInstance("MD5");
			if(digest==null)
			{
			System.out.println("Unable to get the MD5");
			System.exit(0);

			}
			for(File file : filelist)
			{
				//object to read the data from the file
				FileInputStream fis=new FileInputStream(file);

				//if fle is not empty
			if(file.length()!=0)
			{
				while((Rcount =fis.read(bytearr))!=-1)
				{
					digest.update(bytearr,0,Rcount);
				}
			}
			//to get the hash bytes of checksome
			byte bytes[] =digest.digest();
			
			//stringbuilder to create editable string
			StringBuilder sb = new StringBuilder();
			
			//to convert hexadeciaml format
			for(int i=0;i<bytes.length;i++)
			{
				//add each byte from decimal to hexadecemial in the stringbuffer
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100,16).substring(1));
			}

			System.out.println("file Name : " +file.getName()+ " Checksum : "+sb);
			//add the checksome in linkedlist

			if(lobj.contains(sb.toString()))   //checksomes is already there
			{
				if(!file.delete())
				{
					System.out.println("Unable to Delete");
				}
				else
				{
					DupFile++;
				}

			}
			else   //there is no checksome
			{
				lobj.add(sb.toString());
			}
			fis.close();
		}
	}
	catch(Exception  obj)
	{
		System.out.println("Exception occured : " + obj);
	}
	finally
	{

	}
    System.out.println("Total Duplicate  files deleted "+DupFile);
   }
}




