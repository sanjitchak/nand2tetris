//package Vmtranslator;
import java.util.*;
import java.io.*;
public class Vmtranslator {
	static  Scanner scan;
	static String file;
	 
	 
	static FileWriter fw;
	static BufferedWriter bw;
	static int count = -1;  //for different variables of eq,lt and gt

	public Vmtranslator (String filename){
		
		try {
			scan = new Scanner(new File(filename));	
		} 
		catch(Exception e)
		{
			System.out.println("Doesn't exist");
		}
		 

	}
	public void readWrite(String file)
	{    try   {

		 
		  fw = new FileWriter(file+".asm");
		bw = new BufferedWriter(fw);
		 
 

	} catch (IOException e) {

		e.printStackTrace();

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
    	bw.write("@"+file+"."+number+"\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
		
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
    	bw.write("@SP\nM=M-1\nA=M\nD=M\n@"+file+"."+number+"\nM=D\n");
		
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
				  bw.write("D;JGT\n");
				 
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
			Scanner scan2 = new Scanner(function_name);
			scan2.useDelimiter(".");
			String function_name_part1=scan2.next();
		
	        scan2.close();
			//Vmtranslator hack2 = new Vmtranslator(function_name_part1+".vm"); //execute for new file
			 
			//hack2.searchfunction();
			//hack2.writeToFile();
			//function_name2=null; //so that writeTofile function wont skip function
	  
		
		
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

		Vmtranslator hack = new Vmtranslator(args[0]);
		Scanner scan2 = new Scanner(args[0]); //for having same filename
		scan2.useDelimiter("[.]");
		 file = scan2.next(); //to get filename for asm
		 System.out.println(file);
		 String ext=null;
		 
		 if(scan2.hasNext())
		 ext = scan2.next();
		
		 scan2.close();
		 System.out.println(ext);
		//hack.readWrite(file);
		int i =0;
		if(ext==null) //if argument is a directory not a vm file
		{
		   File directory = new File(args[0]);
   
	        //get all the files from a directory

	        File[] fList = directory.listFiles();
  int n =file.length();
  String filename_array [] = new String [n];
  
	        for (File file : fList){
 
	           if(file.getName().contains(".vm"))
	           {
	        	   filename_array [i] = file.getName();
	           i++;
	           }
}
	    	for(String arr : filename_array )
	    	{
	    		System.out.println(arr);
	    	}
		}
	
		//hack.writeToFile();
	/*try { bw.close();
fw.close();}
	catch  (IOException e)
	{
	}*/

	}
}
