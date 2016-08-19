
import java.io.*;
import java.util.*;

public class Handle
{
	public static void readFileByLines(String srcFileName , String targetPathName)throws Exception
	{
	    File srcFile = new File(srcFileName);
	    BufferedReader bufferedReader = null;
	    try
	    {
	    	bufferedReader = new BufferedReader(new FileReader(srcFile));
	        System.out.println("\n" + srcFile.getAbsolutePath() + "\tsucceed������");
	        targetPathName = targetPathName + "\\"+ srcFile.getName();
			File targetpath = new File(targetPathName);
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(targetpath));
	        String tempString = "";
	        boolean bool = false;
	        while((tempString = bufferedReader.readLine()) != null)
	        {
	        	boolean boob = false;
	        	String tempstring = tempString;
	        	String str = "";
	        	if(tempString.trim().isEmpty())
	        		continue;
	        	else
	        	{
	        		tempstring += '$';
	       			char [] string = tempstring.toCharArray();
	       			for(int i = 0;i < string.length - 1 && string[i] != '$';i ++)
	       			{
	      				String tempstr = "";
	       				tempstr += string[i];
	       				if(tempstr.equals("\""))
	       				{
	       					if(!boob && !bool)
	       					{
	       						boob = true;
	       						str += string[i];
	       						continue;
	       					}
	       					else if(boob && !bool)
	       					{
	       						boob = false;
	       						str += string[i];
	       						continue;
	       					}
	       					else if(bool)
	       						break;
	       				}
       					tempstr += string[i + 1];
       					if(!bool  && !boob && tempstr.equals("//"))
	      					break;
	       				else if(tempstr.equals("/*"))
	   					{
	       					if(!boob && !bool)
	       					{
	       						bool = true;
	       						i ++;
	       						continue;
	       					}
	       					else if(boob && !bool)
	       					{
	       						str += string[i];
	       					}
	       					else if(bool)
	       						break;
	      				}
	       				else if(tempstr.equals("*/"))
	       				{
	       					if(!boob && bool)
	       					{
	       						bool = false;
	       						i ++;
	       						continue;
	       					}
	       					else if(boob && !bool)
	       						str += string[i];
	       					else if(boob && bool)
	       					{
	       						bool = false;
	       						i ++;
	       						continue;
	       					}
	     				}
	       				else if(!bool)
	       					str += string[i];
       				}
	        	}

	        	if(!str.trim().isEmpty())
	        		bufferedWriter.write(str + "\r\n");
	        	bufferedWriter.flush();
	        }
	        bufferedWriter.close();
	    }
	    catch(IOException e)
	    {
	    	System.out.println("\n�ļ���ȡ�쳣������");
	    	System.exit(0);
	    }
	    finally
	    {
	    	bufferedReader.close();
	    }
	}
	
	public static List <File> getFileList(String srcPath , List <File> filelist , int flag)throws Exception
	{
		File [] files = new File(srcPath).listFiles();
		if(files != null)
		{
			for(int i = 0; i < files.length; i++)
			{
				String fileName = files[i].getName();
				if(files[i].isDirectory() && flag == 1)
					getFileList(files[i].getAbsolutePath() , filelist , flag);
				else if(fileName.endsWith(".c") || fileName.endsWith(".cpp") ||
						fileName.endsWith(".java") || fileName.endsWith(".js") ||
						fileName.endsWith(".cs"))
					filelist.add(files[i]);
				else
					continue;
			}
		}
		return filelist;
	}
	
	public static void main(String[] args)throws Exception
	{
		String srcPath = "";
		String targetPath = "";
		String targetpath = "";
		String string = "";
		int flag = 0;
		if(args.length != 0)
			string = args[0];
		if(string.isEmpty() || string.equalsIgnoreCase("-h") || string.equalsIgnoreCase("-help") || string.equalsIgnoreCase("help") )
		{
			System.out.println("\n\n\t\t\tRemove Blank Lines And Comments\n");
			System.out.println("ʵ�ֶ�.c .cpp .java .js .cs��ʽ�ļ��Ŀ��������Լ�����ע�ͷ�" + " // ��" + "/*...*/ ��" + "/**...*/" + " �� ///�����.\n\nѡ�");
			System.out.println("\t-i\t" + "�������ļ�������Ŀ¼��");
			System.out.println("\t-o\t" + "������ļ����Ŀ¼��");
			System.out.println("\t-r\t" + "�Ƿ�������Ŀ¼��");
			System.out.println("\t-h\t" + "������");
			System.out.println("\n�ο������ʽ��" + "-i C:\\testIn -o C:\\testOut -r");
			System.exit(0);
		}
		else
		{
			if(args.length > 5 || args.length < 4)
			{
				System.out.println("\n�����������ȷ������");
				System.exit(0);
			}
			for(int i = 0;i < args.length;i ++)
			{
				if(args[i].equals("-i"))
				{
					srcPath = args[i + 1];
					i ++;
				}
				else if(args[i].equals("-o"))
				{
					targetPath = args[i + 1];
					targetpath = targetPath;
					i ++;
				}
				else if(args[i].equals("-r"))
				{
					flag = 1;
				}
				else
				{
					System.out.println("\n�����ʽ����ȷ������");
					System.exit(0);
				}
			}
		}
		File fileIn = new File(srcPath);
		File fileOut = new File(targetPath);
		if((!fileIn.isDirectory() && !fileIn.isFile()) || !fileIn.exists())
		{
			System.out.println("\n����Ŀ¼�����ڻ��߸�ʽ����ȷ������");
			System.exit(0);
		}
		if((fileOut.isFile()) || targetPath.contains(srcPath) || targetPath.equals(srcPath))
		{
			System.out.println("\n���Ŀ¼����ȷ������");
			System.exit(0);
		}
		if(!fileOut.getParentFile().exists())
		{
			fileOut.getParentFile().mkdirs();
			System.out.println(fileOut.getParentFile().getName());
		}
		try
		{
			fileOut.createNewFile();
		}
		catch(Exception e)
		{
			System.out.println("\n����ļ������쳣������");
			System.exit(0);
		}
		System.out.println("\n\n");
		List <File> fileList = new ArrayList <File>();
		fileList = getFileList(srcPath , fileList , flag);
		for(File item : fileList)
	    {
			try
			{
				if(item.isFile())
				{
					String itemString = item.getParentFile().toString();
					targetPath = targetpath;
					targetPath += (itemString.substring(srcPath.length() , itemString.length()));
					File file4 = new File(targetPath + "\\" + item.getName());
					if(!file4.getParentFile().exists())
					{
						file4.getParentFile().mkdirs();
					}
					item.mkdirs();
					readFileByLines(item.toString() , targetPath);
				}
			}
			catch(Exception e)
			{
				System.out.println("\n�ļ����Ŀ¼����ȷ������");
				System.exit(0);
			}
	    }
		System.out.println("\n\n**********************************************\n" +
				"\t\t�ļ���������ɣ�����\n\t\t��л����ʹ�ã�����\n**********************************************\n");
	}
}
