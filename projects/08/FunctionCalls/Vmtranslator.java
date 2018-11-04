//package Vmtranslator;
import java.util.*;
import java.io.*;
public class Vmtranslator {
	static  Scanner scan;
	static String file;
	 static int call_count =1; //for return address
	static String ext=null;
	static FileWriter fw;
	static String function_file_static; //for storing different static value in different static function according to vm file name
	static BufferedWriter bw;
	static int count = -1;  //for different variables of eq,lt and gt

	public Vmtranslator (String filename){
		 try   {
  
				 if(ext==null)
			  fw = new FileWriter(filename+"/"+filename+".asm");
				 else
					 fw = new FileWriter(filename+".asm");
					 
			bw = new BufferedWriter(fw);
			 
	 

		} catch (IOException e) {

			e.printStackTrace();

		}
	}
	public void readfile(String file)
	{  
		try {
			scan = new Scanner(new File(file));	
		} 
		catch(Exception e)
		{
			System.out.println("Doesn't exist");
		}
	}
	//push_pop
	public static void memory_op()
	{
	 String first = scan.next();
	 String second = scan.next();
	 int number = scan.nextInt();
	 
	 //for push
	 if(first.contains("push"))
	 { 
		  switch(second)
		  {
		  case "static":
    try { bw.write("//push static\n");
    	bw.write("@"+function_file_static+"."+number+"\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
		
		}
		catch (IOException e)
		{
			
		}
    break;
		  case "constant":
			  try {
				  bw.write("//push const\n");
				  bw.write("@"+number+"\nD=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
			  }
			  catch (IOException e)
			  {
				  
			  }
			  break;
		  case "local":
			  try {
				  bw.write("//push local\n");
				  bw.write("@LCL\nD=M\n");
				  for(int i=1;i<=number;i++)
					  bw.write("D=D+1\n");
				  
				  bw.write("A=D\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");  
			  }
			  catch (IOException e) {
				// TODO: handle exception
			}
			  break;
		  case "argument":	
			  try {
				  bw.write("//push arg\n");
				  bw.write("@ARG\nD=M\n");
				  for(int i=1;i<=number;i++)
					  bw.write("D=D+1\n");
				  
				  bw.write("A=D\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");  
			  }
			  catch (IOException e) {
				// TODO: handle exception
			}
			  break;
		  case "temp":
			  try {
				  bw.write("//push temp\n");
				  bw.write("@"+(5+number)+"\nD=M\n");
				  
		bw.write("@SP\nA=M\nM=D\n@SP\nM=M+1\n");  
			  }
			  catch (IOException e) {
				// TODO: handle exception
			} 
			  break;
		  case "pointer":
			  if(number==0)
			  {
				  try{bw.write("//push pointer 0\n");
					  bw.write("@THIS"+"\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
				  }
				  catch (IOException e) {
					// TODO: handle exception
				}
				  }
			  else 
			  {
				  try{
					  bw.write("//push pointer 1\n");
					  bw.write("@THAT"+"\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
				  }
				  catch (IOException e) {
					// TODO: handle exception
				}
			  }
			  break;
		  case "this":
			  try {
				  bw.write("//push this\n");
				  bw.write("@THIS\nD=M\n");
				  for(int i=1;i<=number;i++)
					  bw.write("D=D+1\n");
				  
				  bw.write("A=D\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");  
			  }
			  catch (IOException e) {
				// TODO: handle exception
			}
			  break;
		  case "that":
			  try {
				  bw.write("//push that\n");
				  bw.write("@THAT\nD=M\n");
				  for(int i=1;i<=number;i++)
					  bw.write("D=D+1\n");
				  
				  bw.write("A=D\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");  
			  }
			  catch (IOException e) {
				// TODO: handle exception
			}
			  break;
		  }
	 }
	 //for pop
	 else 
	 {
		 
		  switch(second)
		  {
		  case "static":
    try { bw.write("//pop static\n");
    	bw.write("@SP\nM=M-1\nA=M\nD=M\n@"+function_file_static+"."+number+"\nM=D\n");
		
		}
		catch (IOException e)
		{
			
		}
    break;
		  case "local":
			  try {
				  bw.write("//pop local\n");
				  bw.write("@SP\nM=M-1\nA=M\nD=M\n");
				  bw.write("@LCL\nA=M\n");
				  for(int i=1;i<=number;i++)
					  bw.write("A=A+1\n");
				  
				  bw.write("M=D\n");  
			  }
			  catch (IOException e) {
				// TODO: handle exception
			}
			  break;
		  case "argument":	
			  try {
				  bw.write("//pop arg\n");
				  bw.write("@SP\nM=M-1\nA=M\nD=M\n");
				  bw.write("@ARG\nA=M\n");
				  for(int i=1;i<=number;i++)
					  bw.write("A=A+1\n");
				  
				  bw.write("M=D\n");  
			  }
			  catch (IOException e) {
				// TODO: handle exception
			}
			  break;
		  case "temp":
			  try {
				  
				  bw.write("//pop temp\n");
				  bw.write("@SP\nM=M-1\nA=M\nD=M\n");
				  bw.write("@"+(5+number)+"\nM=D\n");
				  
		 
			  }
			  catch (IOException e) {
				// TODO: handle exception
			} 
			  break;
		  case "pointer":
			
			  if(number==0)
			  {  
				  try{
					  bw.write("//pop pointer 0\n");
					  bw.write("@SP\nM=M-1\nA=M\nD=M\n");
					  bw.write("@THIS"+"\nM=D\n");
				  }
				  catch (IOException e) {
					// TODO: handle exception
				}
				  }
			  else 
			  {
				  
				  try{ 
					  bw.write("//pop pointer 1\n");
					  bw.write("@SP\nM=M-1\nA=M\nD=M\n");
				  bw.write("@THAT"+"\nM=D\n");
				  }
				  catch (IOException e) {
					// TODO: handle exception
				}
			  }
			  break;
		  case "this":
			  try {
				  bw.write("//pop this\n");
				  bw.write("@SP\nM=M-1\nA=M\nD=M\n");
				  bw.write("@THIS\nA=M\n");
				  for(int i=1;i<=number;i++)
					  bw.write("A=A+1\n");
				  
				  bw.write("M=D\n");  
			  }
			  catch (IOException e) {
				// TODO: handle exception
			}
			  break;
		  case "that":
			  try {
				  bw.write("//pop that\n");
				  bw.write("@SP\nM=M-1\nA=M\nD=M\n");
				  bw.write("@THAT\nA=M\n");
				  for(int i=1;i<=number;i++)
					  bw.write("A=A+1\n");
				  
				  bw.write("M=D\n");   
			  }
			  catch (IOException e) {
				// TODO: handle exception
			}
			  break;
		  }
	 }
	return;
	}
	public static void looper()
	{
		String argument = scan.next();
		String label = scan.next();
		switch(argument)
		{
		case "label":
			  try {
				  bw.write("//label \n");
				  bw.write("("+label+")\n");
				  
			  }
			  catch (IOException e) {
				// TODO: handle exception
			}
			  break;
		case "if-goto":
			try {
				  bw.write("//label jump \n");
				  bw.write("@SP\n");
				  bw.write("A=M\n");
				  bw.write("A=A-1\n");
				  bw.write("D=M\n");
				  bw.write("@SP\n");
				  bw.write("M=M-1\n");
				  bw.write("@"+label+"\n");
				  bw.write("D;JNE\n");
				 
			  }
			  catch (IOException e) {
				// TODO: handle exception
			}
			  break;
		case "goto":
			try {
				  bw.write("//label jump compulsory \n");
			 
				  bw.write("@"+label+"\n");
				  bw.write("0;JMP\n");
				 
			  }
			  catch (IOException e) {
				// TODO: handle exception
			}
			  break;
		}
	}
	//arithmetic op
	public static void arithmetic_op()
	{
		
		String operation = scan.next();
		
		if(operation.contains("eq")||operation.contains("gt")||operation.contains("lt"))
			count++;
			
		switch(operation)
		{
		case "add":
			 try {
				  bw.write("//add\n");
				  bw.write("@SP\nM=M-1\nA=M\nD=M\n");
				  
				  bw.write("A=A-1\nM=D+M\n");
				   
			  }
			  catch (IOException e) {
				// TODO: handle exception
			}
			  break;
		case "sub":
			 try {
				  bw.write("//sub\n");
				  bw.write("@SP\nM=M-1\nA=M\n");
				  
				  bw.write("A=A-1\nD=M\nA=A+1\nM=D-M\nD=M\nA=A-1\nM=D\n");
				  
				   
			  }
			  catch (IOException e) {
				// TODO: handle exception
			}
			  break;
		case "neg": 
			  try {
				  bw.write("//neg\n");
				  bw.write("@SP\nA=M\nA=A-1\n");
				  
				  bw.write("M=!M\nM=M+1\n");
				  
				   
			  }
			  catch (IOException e) {
				// TODO: handle exception
			}
			  break;
		case "eq":
			 try {
				  bw.write("//eq\n");
				  bw.write("@SP\nM=M-1\nA=M\n");
				  
				  bw.write("D=M\nA=A-1\nM=D-M\nD=M\n@EQ"+count+"\nD;JEQ\n@SP\nA=M\nA=A-1\nM=0\n@EXIT"+count+"\n0;JMP\n(EQ"+count+")\n@SP\nA=M\nA=A-1\nM=-1\n(EXIT"+count+")\n");
				  
				   
			  }
			  catch (IOException e) {
				// TODO: handle exception
			}
			  break;
		case "gt":
			 try {
				  bw.write("//gt\n");
				  bw.write("@SP\nM=M-1\nA=M\n");
				  
				  bw.write("A=A-1\nD=M\nA=A+1\nM=D-M\nD=M\n@GT"+count+"\nD;JGT\n@SP\nA=M\nA=A-1\nM=0\n@EXIT"+count+"\n0;JMP\n(GT"+count+")\n@SP\nA=M\nA=A-1\nM=-1\n(EXIT"+count+")\n");
				  
				   
			  }
			  catch (IOException e) {
				// TODO: handle exception
			}
			  break;
		case "lt":
			 try {
				  bw.write("//lt\n");
				  bw.write("@SP\nM=M-1\nA=M\n");
				  bw.write("A=A-1\nD=M\nA=A+1\nM=D-M\nD=M\n@LT"+count+"\nD;JLT\n@SP\nA=M\nA=A-1\nM=0\n@EXIT"+count+"\n0;JMP\n(LT"+count+")\n@SP\nA=M\nA=A-1\nM=-1\n(EXIT"+count+")\n");
				  
				 // bw.write("A=A-1\nD=M\nA=A+1\nM=D-M\nD=M\nA=A-1\n@LT\nD;JLT\nM=-1\n@EXIT\n0;JMP\n(LT)\nM=0\n(EXIT)\n");
				  
				   
			  }
			  catch (IOException e) {
				// TODO: handle exception
			}
			  break;
		case "and":
			 try {
				  bw.write("//and\n");
				  bw.write("@SP\nM=M-1\nA=M\n");
				  
				  bw.write("A=A-1\nD=M\nA=A+1\nM=D&M\nD=M\nA=A-1\nM=D\n");
				  
				   
			  }
			  catch (IOException e) {
				// TODO: handle exception
			}
			  break; 
		case "or":
			 try {
				  bw.write("//or\n");
				  bw.write("@SP\nM=M-1\nA=M\n");
				  
				  bw.write("A=A-1\nD=M\nA=A+1\nM=D|M\nD=M\nA=A-1\nM=D\n");
				  
				   
			  }
			  catch (IOException e) {
				// TODO: handle exception
			}
			  break; 
		case "not":
			 try {
				  bw.write("//not\n");
				  bw.write("@SP\nA=M\n");
				  
				  bw.write("A=A-1\nM=!M\n");
				  
				   
			  }
			  catch (IOException e) {
				// TODO: handle exception
			}
			  break; 
		}
	}
	public static void return_writer()
	{
		try {
			  bw.write("//return type\n");
			  bw.write("@LCL\n");
			  bw.write("D=M\n");
			  bw.write("@frame\n");
			  bw.write("M=D\n");
			  bw.write("A=M\n");
			  bw.write("A=A-1\n");
			  bw.write("A=A-1\n");
			  bw.write("A=A-1\n");
			  bw.write("A=A-1\n");
			  bw.write("A=A-1\n");
			  bw.write("D=M\n");
			  bw.write("@retaddr\n");
			  bw.write("M=D\n");
			  
			  bw.write("@SP\n");
			  bw.write("A=M\n");
			  bw.write("A=A-1\n");
			  bw.write("D=M\n");
			  bw.write("@ARG\n");
			  bw.write("A=M\n");
			  bw.write("M=D\n");
			  
			  bw.write("A=A+1\n");
			  bw.write("D=A\n");
			  bw.write("@SP\n");
			  bw.write("M=D\n");
			  
			  bw.write("@frame\n"); 
			  bw.write("A=M\n");
			  
			  bw.write("A=A-1\n");
			  bw.write("D=M\n");
			  bw.write("@THAT\n");
			  bw.write("M=D\n");
			  
			  bw.write("@frame\n"); 
			  bw.write("A=M\n");
			  
			  bw.write("A=A-1\n");
			  bw.write("A=A-1\n");
			  bw.write("D=M\n");
			  bw.write("@THIS\n");
			  bw.write("M=D\n");
			  
			  bw.write("@frame\n"); 
			  bw.write("A=M\n");
			  
			  bw.write("A=A-1\n");
			  bw.write("A=A-1\n");
			  bw.write("A=A-1\n");
			  bw.write("D=M\n");
			  bw.write("@ARG\n");
			  bw.write("M=D\n");
			  
			  bw.write("@frame\n"); 
			  bw.write("A=M\n");
			  
			  bw.write("A=A-1\n");
			  bw.write("A=A-1\n");
			  bw.write("A=A-1\n");
			  bw.write("A=A-1\n");
			  bw.write("D=M\n");
			  bw.write("@LCL\n");
			  bw.write("M=D\n");
			  
			  bw.write("@retaddr\n");
			  bw.write("A=M\n");
			  bw.write("0;JMP\n");
			  
			  
			   
		  }
		  catch (IOException e) {
			// TODO: handle exception
		}
		  return;
	}
	public static void function_writer()
	{
	String type=scan.next();
	String function_name= scan.next();
 
	
	int number = scan.nextInt();
	switch(type)
	{
	case "function":
		try {
			  bw.write("//function type\n");
			  bw.write("("+function_name+")\n");
			  for(int i=0;i<number;i++)
				  bw.write("@"+0+"\nD=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
	   
		  }
		  catch (IOException e) {
			// TODO: handle exception
		}
		  break; 
	case "call":
		try {
			bw.write("//call type\n");
		 bw.write("@return"+call_count+"\nD=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
		 bw.write("@LCL"+"\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
		 bw.write("@ARG"+"\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
		 bw.write("@THIS"+"\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
		 bw.write("@THAT"+"\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
		 bw.write("@SP"+"\nA=M\n");
		 for(int i=0;i<number;i++)
			 bw.write("A=A-1\n");
		 for(int i=0;i<5;i++)
			 bw.write("A=A-1\n");
		  
			 bw.write("D=A\n@ARG\nM=D\n");
			 
				 bw.write("@SP\nD=M\n@LCL\nM=D\n");
				 bw.write("@"+function_name+"\n");
				 bw.write("0;JMP\n");
				 bw.write("("+"return"+call_count+")\n");
				 
			 
		 call_count++;
		  }
		  catch (IOException e) {
			// TODO: handle exception
		}
		  break; 
	}
	return;
	}
	
 
	//main writing
	public  void writeToFile()
	{  
	while(scan.hasNextLine()) //first pass
	{
		
		String a =null;
		if(scan.hasNext("//"))  //for ignoring comments  
		{
			scan.nextLine();
			continue;
		}

		
		
		if (scan.hasNext("pop") || scan.hasNext("push"))
		 
			memory_op();
		 
		else if(scan.hasNext("label")||scan.hasNext("if-goto") || scan.hasNext("goto")) 
			looper();
		else if(scan.hasNext("function") ) 
			{
			 
				function_writer();
			}
		else if(scan.hasNext("call"))
			function_writer();
		else if(scan.hasNext("return"))
			{return_writer();
			
			}
		else
			arithmetic_op();

		a = scan.nextLine(); //for skipping to nextLine
		
 

	}
	return;


	}
	
	public static void main(String args[])
	{

		
		Scanner scan2 = new Scanner(args[0]); //for having same filename
		
		if(args[0].contains("/")) 
			scan2.useDelimiter("[/]");
		else 
			scan2.useDelimiter("[.]");
		 
		file = scan2.next(); //to get filename for asm
		
		
		
		 
		 
		 ext=null; //for extension
		 
		 if(scan2.hasNext())
		 ext = scan2.next();
		 Vmtranslator hack = new Vmtranslator(file);
		 scan2.close();
		 
		//hack.readWrite(file);
		int i =0;
		if(ext==null) //if argument is a directory not a vm file
		{
		   File directory = new File(args[0]);
   
	        //get all the files from a directory

	        File[] fList = directory.listFiles();
  int n =fList.length;
  String filename_array [] = new String [n];
  
	        for (File file : fList){
 
	           if(file.getName().contains(".vm")&& !file.getName().contains("Sys"))
	           {
	        	   filename_array [i] = file.getName();
	           i++;
	           }
}
	        System.out.println("opening "+"SyS.... ");
	        function_file_static = "Sys"; //for static push pop
	    	hack.readfile(file+"/"+"Sys.vm");
	    	
	    	hack.writeToFile();
	        for(String filename : filename_array)
	        {
	        	if(filename==null)
	        		break;
	        	System.out.println("opening "+ filename+".... ");
	        	
	        	function_file_static = filename; //for static push pop
	        	
	        	hack.readfile(file+"/"+filename);
	        	hack.writeToFile();
	        }
	        
		}
		else
		{
			//System.out.println(args[0]);
			function_file_static = file; //for static push pop
			hack.readfile(args[0]);
			hack.writeToFile();
			
		}
	
	 
		try { bw.close();
		fw.close();}
			catch  (IOException e)
			{
			}
	}
}
