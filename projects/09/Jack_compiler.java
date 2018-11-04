//package jack_compiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Jack_compiler {

	static String file;
	static FileWriter fw;
	static BufferedWriter bw;
	static String className="";
	static Scanner scan;
	static String ext = null;
	static int fieldcount = 0; //for constructor
	static int labelNo = 0; //for if and while label
	static int staticIndex = -1; //for compiling static type of class
	static int fieldIndex = -1;  // for compiling field type of class
	static HashMap<String, ArrayList<String>> classVariable = new HashMap<String, ArrayList<String>>();
	static HashMap<String, ArrayList<String>> subroutineVariable = new HashMap<String, ArrayList<String>>();

	public static void symbolTableDefine(String name, String type, String kind, int index, int scope)
	{

		ArrayList<String> typeKind = new ArrayList<String>();
		typeKind.add(type );
		typeKind.add(kind );
		typeKind.add(Integer.toString(index));

		if(scope == 0)
		{
			classVariable.put(name, typeKind);

		}
		else
		{ 
			subroutineVariable.put(name, typeKind);
			/*System.out.println(name);
			System.out.println(subroutineVariable.values());*/
		}

	}
	public static int varCount(String kind, int scope)
	{
		int varCount=0;
		if(scope == 0)
		{
			for(ArrayList<String> name :classVariable.values())
			{
				if(name.get(1).contains(kind))
				{
					varCount++;
				}
			}
		}
		else
		{
			for(ArrayList<String> name : subroutineVariable.values())
			{
				if(name.get(1).contains(kind))
				{
					varCount++;
				}
			}
		}
		return varCount;
	}
	public static String KindOf(String name)
	{
		if(subroutineVariable.containsKey(name))
		{
			return subroutineVariable.get(name).get(1);

		}

		if(classVariable.containsKey(name))
		{		 


			return classVariable.get(name).get(1);

		}

		return "NONE";
	}
	public static String TypeOf(String name )
	{


		if(  subroutineVariable.containsKey(name))
		{
			return subroutineVariable.get(name).get(0);

		}
		if(  classVariable.containsKey(name))
		{

			return classVariable.get(name).get(0);

		}
		return "NONE";

	}
	public static int indexOf(String name )
	{
		int index = -1;
		if( subroutineVariable.containsKey(name))
		{
			index = Integer.parseInt(subroutineVariable.get(name).get(2));

			/*kind = subroutineVariable.get(name).get(1);

		   //System.out.println(kind);

			for(String indexCheck :subroutineVariable.keySet())
			{
				 if(subroutineVariable.get(indexCheck).get(1).contains("kind"))
				     nameIndex++;

				if(indexCheck.contains(name.toUpperCase()))
					break;
			} 
			for(ArrayList<String> indexCheck :subroutineVariable.values())
			{
				nameIndex -- ;
				if(indexCheck.get(1).contains(kind))
					index++;

				if(nameIndex == -1)
					break;
			}
			//System.out.println(index);
			//System.out.println(" ");
			 */			return index;

		}
		if(classVariable.containsKey(name))
		{   
			index = Integer.parseInt(classVariable.get(name).get(2));
			/*kind = classVariable.get(name).get(1);

			for(String indexCheck :classVariable.keySet())
			{
				nameIndex++;
				if(indexCheck.contains(name.toUpperCase()))
					break;
			} 
			for(ArrayList<String> indexCheck :classVariable.values())
			{
				nameIndex -- ;
				if(indexCheck.get(1).contains(kind))
					index++;

				if(nameIndex == -1)
					break;
			}*/
			return index;

		}


		return -1;

	}
	class Tokenizer //nested class 
	{


		public Tokenizer() //declaration
		{

		}
		public void makefile(String filename)
		{
			try {

				if (ext == null)
					fw = new FileWriter(file + "/" + filename + "1.xml");


				else
					fw = new FileWriter(filename + "1.xml");


				bw = new BufferedWriter(fw);

			} catch (IOException e) {

				e.printStackTrace();

			}
		}

		public void readfile(String file) {
			try {
				scan = new Scanner(new File(file));
			} catch (Exception e) {
				System.out.println("Doesn't exist");
			}
		}

		public void writeToFileTokenizer() {
			try {
				bw.write("<tokens>\n"); // writing token at the beginning of file

			} catch (IOException e) {

			}
			while (scan.hasNext()) {

				String a = null;

				if (scan.hasNext(Pattern.compile("/{2,}.*"))) // for ignoring comments
				{

					scan.nextLine();


					continue;
				}


				a = scan.next();

				if (a.contains("/**")) // for ignoring comments
				{
					scan.useDelimiter("[*][/]");
					scan.next();
					scan.reset();
					scan.nextLine();
					continue;
				}

				if (a.contains("\""))
				{ int quoteNo=0;
				char[] tokenArray = a.toCharArray();
				for(char b : tokenArray)
				{
					if(b == '"')
					{
						quoteNo++;
					}
				}

				if(quoteNo != 2)
				{
					scan.useDelimiter("\"");
					a = a.concat(scan.next());
					scan.reset();
					a = a.concat(scan.next()); 
				}

				/*	String quote = "";
					while(!quote.contains("\""))
						quote = quote + scan.next();

					a = a+quote;
					a = a.trim();*/


				//new commands
				/* try {
						bw.write("<stringConstant> " +a+ "</stringConstant>\n");

					} catch (IOException e) {

					}*/
				/* continue;*/

				}
				else
					a = a.replaceAll("\\s", "");


				//for checking
				/*		try {
 				bw.write(a+"\n"); // writing token at the end of file

 			} catch (IOException e) {

 			}*/


				char[] tokenArray = a.toCharArray();

				token_checker(tokenArray);
			}

			try {
				bw.write("</tokens>"); // writing token at the end of file

			} catch (IOException e) {

			}
			scan.close();
			return;

		}

		// ..........................................................................................
		private void token_checker(char[] tokenArray) {
			String token = "";

			int i = 0;

			while (i < tokenArray.length) {
				char b = tokenArray[i];

				if (b == '"') // for string constant
				{

					if (!token.isEmpty())
						if (keyword_checker(token)) {
							try {
								bw.write("<keyword> " + token + " </keyword>\n");

							} catch (IOException e) {

							}
							token = "";
						} else {
							try {
								bw.write("<identifier> " + token + " </identifier>\n");

							} catch (IOException e) {

							}
							token = "";
						}

					i = string_extractor(tokenArray, i + 1); // to skip string's position
					i++;
					continue;

				}
				if (b == '.') // for dot symbol
				{

					if (!token.isEmpty())
						if (keyword_checker(token)) {
							try {
								bw.write("<keyword> " + token + " </keyword>\n");

							} catch (IOException e) {

							}
							token = "";
						} else {
							try {
								bw.write("<identifier> " + token + " </identifier>\n");

							} catch (IOException e) {

							}
							token = "";
						}
					try {
						bw.write("<symbol> " + Character.toString(b) + " </symbol>\n");

					} catch (IOException e) {

					}
					i++;

					continue;
				}
				if (b == '{') // for curly symbol
				{
					if (!token.isEmpty())
						if (keyword_checker(token)) {
							try {
								bw.write("<keyword> " + token + " </keyword>\n");

							} catch (IOException e) {

							}
							token = "";
						} else {
							try {
								bw.write("<identifier> " + token + " </identifier>\n");

							} catch (IOException e) {

							}
							token = "";
						}
					try {
						bw.write("<symbol> " + Character.toString(b) + " </symbol>\n");

					} catch (IOException e) {

					}
					i++;
					continue;
				}
				if (b == '}') // for curly symbol
				{
					if (!token.isEmpty())
						if (keyword_checker(token)) {
							try {
								bw.write("<keyword> " + token + " </keyword>\n");

							} catch (IOException e) {

							}
							token = "";
						} else {
							try {
								bw.write("<identifier> " + token + " </identifier>\n");

							} catch (IOException e) {

							}
							token = "";
						}
					try {
						bw.write("<symbol> " + Character.toString(b) + " </symbol>\n");

					} catch (IOException e) {

					}
					i++;
					continue;
				}
				if (b == '(') // for bracket symbol
				{
					if (!token.isEmpty())
						if (keyword_checker(token)) {
							try {
								bw.write("<keyword> " + token + " </keyword>\n");

							} catch (IOException e) {

							}
							token = "";
						} else {
							try {
								bw.write("<identifier> " + token + " </identifier>\n");

							} catch (IOException e) {

							}
							token = "";
						}
					try {
						bw.write("<symbol> " + Character.toString(b) + " </symbol>\n");

					} catch (IOException e) {

					}
					i++;
					continue;
				}
				if (b == ')') // for bracket symbol
				{
					if (!token.isEmpty())
						if (keyword_checker(token)) {
							try {
								bw.write("<keyword> " + token + " </keyword>\n");

							} catch (IOException e) {

							}
							token = "";
						} else {
							try {
								bw.write("<identifier> " + token + " </identifier>\n");

							} catch (IOException e) {

							}
							token = "";
						}
					try {
						bw.write("<symbol> " + Character.toString(b) + " </symbol>\n");

					} catch (IOException e) {

					}
					i++;
					continue;
				}
				if (b == '[') // for square bracket symbol
				{
					if (!token.isEmpty())
						if (keyword_checker(token)) {
							try {
								bw.write("<keyword> " + token + " </keyword>\n");

							} catch (IOException e) {

							}
							token = "";
						} else {
							try {
								bw.write("<identifier> " + token + " </identifier>\n");

							} catch (IOException e) {

							}
							token = "";
						}
					try {
						bw.write("<symbol> " + Character.toString(b) + " </symbol>\n");

					} catch (IOException e) {

					}
					i++;
					continue;
				}
				if (b == ']') // for square bracket symbol
				{
					if (!token.isEmpty())
						if (keyword_checker(token)) {
							try {
								bw.write("<keyword> " + token + " </keyword>\n");

							} catch (IOException e) {

							}
							token = "";
						} else {
							try {
								bw.write("<identifier> " + token + " </identifier>\n");

							} catch (IOException e) {

							}
							token = "";
						}
					try {
						bw.write("<symbol> " + Character.toString(b) + " </symbol>\n");

					} catch (IOException e) {

					}
					i++;
					continue;
				}
				if (b == ',') // for comma symbol
				{
					if (!token.isEmpty())
						if (keyword_checker(token)) {
							try {
								bw.write("<keyword> " + token + " </keyword>\n");

							} catch (IOException e) {

							}
							token = "";
						} else {
							try {
								bw.write("<identifier> " + token + " </identifier>\n");

							} catch (IOException e) {

							}
							token = "";
						}
					try {
						bw.write("<symbol> " + Character.toString(b) + " </symbol>\n");

					} catch (IOException e) {

					}
					i++;
					continue;
				}
				if (b == ';') // for semicolon symbol
				{
					if (!token.isEmpty())
						if (keyword_checker(token)) {
							try {
								bw.write("<keyword> " + token + " </keyword>\n");

							} catch (IOException e) {

							}
							token = "";
						} else {
							try {
								bw.write("<identifier> " + token + " </identifier>\n");

							} catch (IOException e) {

							}
							token = "";
						}
					try {
						bw.write("<symbol> " + Character.toString(b) + " </symbol>\n");

					} catch (IOException e) {

					}
					i++;
					continue;
				}
				if (b == '+') // for + symbol
				{
					if (!token.isEmpty())
						if (keyword_checker(token)) {
							try {
								bw.write("<keyword> " + token + " </keyword>\n");

							} catch (IOException e) {

							}
							token = "";
						} else {
							try {
								bw.write("<identifier> " + token + " </identifier>\n");

							} catch (IOException e) {

							}
							token = "";
						}
					try {
						bw.write("<symbol> " + Character.toString(b) + " </symbol>\n");

					} catch (IOException e) {

					}
					i++;
					continue;
				}
				if (b == '-') // for - symbol
				{
					if (!token.isEmpty())
						if (keyword_checker(token)) {
							try {
								bw.write("<keyword> " + token + " </keyword>\n");

							} catch (IOException e) {

							}
							token = "";
						} else {
							try {
								bw.write("<identifier> " + token + " </identifier>\n");

							} catch (IOException e) {

							}
							token = "";
						}
					try {
						bw.write("<symbol> " + Character.toString(b) + " </symbol>\n");

					} catch (IOException e) {

					}
					i++;
					continue;
				}
				if (b == '*') // for * symbol
				{
					if (!token.isEmpty())
						if (keyword_checker(token)) {
							try {
								bw.write("<keyword> " + token + " </keyword>\n");

							} catch (IOException e) {

							}
							token = "";
						} else {
							try {
								bw.write("<identifier> " + token + " </identifier>\n");

							} catch (IOException e) {

							}
							token = "";
						}
					try {
						bw.write("<symbol> " + Character.toString(b) + " </symbol>\n");

					} catch (IOException e) {

					}
					i++;
					continue;
				}
				if (b == '/') // for slash symbol
				{
					if (!token.isEmpty())
						if (keyword_checker(token)) {
							try {
								bw.write("<keyword> " + token + " </keyword>\n");

							} catch (IOException e) {

							}
							token = "";
						} else {
							try {
								bw.write("<identifier> " + token + " </identifier>\n");

							} catch (IOException e) {

							}
							token = "";
						}
					try {
						bw.write("<symbol> " + Character.toString(b) + " </symbol>\n");

					} catch (IOException e) {

					}
					i++;
					continue;
				}
				if (b == '&') // for & symbol
				{
					if (!token.isEmpty())
						if (keyword_checker(token)) {
							try {
								bw.write("<keyword> " + token + " </keyword>\n");

							} catch (IOException e) {

							}
							token = "";
						} else {
							try {
								bw.write("<identifier> " + token + " </identifier>\n");

							} catch (IOException e) {

							}
							token = "";
						}
					try {
						bw.write("<symbol> " + "&amp;" + " </symbol>\n");

					} catch (IOException e) {

					}
					i++;
					continue;
				}
				if (b == '|') // for stick symbol
				{
					if (!token.isEmpty())
						if (keyword_checker(token)) {
							try {
								bw.write("<keyword> " + token + " </keyword>\n");

							} catch (IOException e) {

							}
							token = "";
						} else {
							try {
								bw.write("<identifier> " + token + " </identifier>\n");

							} catch (IOException e) {

							}
							token = "";
						}
					try {
						bw.write("<symbol> " + Character.toString(b) + " </symbol>\n");

					} catch (IOException e) {

					}
					i++;
					continue;
				}
				if (b == '<') // for lesser symbol
				{
					if (!token.isEmpty())
						if (keyword_checker(token)) {
							try {
								bw.write("<keyword> " + token + " </keyword>\n");

							} catch (IOException e) {

							}
							token = "";
						} else {
							try {
								bw.write("<identifier> " + token + " </identifier>\n");

							} catch (IOException e) {

							}
							token = "";
						}
					try {
						bw.write("<symbol> " + "&lt;" + " </symbol>\n");

					} catch (IOException e) {

					}
					i++;
					continue;
				}
				if (b == '>') // for greater symbol
				{
					if (!token.isEmpty())
						if (keyword_checker(token)) {
							try {
								bw.write("<keyword> " + token + " </keyword>\n");

							} catch (IOException e) {

							}
							token = "";
						} else {
							try {
								bw.write("<identifier> " + token + " </identifier>\n");

							} catch (IOException e) {

							}
							token = "";
						}
					try {
						bw.write("<symbol> " + "&gt;" + " </symbol>\n");

					} catch (IOException e) {

					}
					i++;
					continue;
				}
				if (b == '=') // for = symbol
				{
					if (!token.isEmpty())
						if (keyword_checker(token)) {
							try {
								bw.write("<keyword> " + token + " </keyword>\n");

							} catch (IOException e) {

							}
							token = "";
						} else {
							try {
								bw.write("<identifier> " + token + " </identifier>\n");

							} catch (IOException e) {

							}
							token = "";
						}
					try {
						bw.write("<symbol> " + Character.toString(b) + " </symbol>\n");

					} catch (IOException e) {

					}
					i++;
					continue;
				}
				if (b == '~') // for ~ symbol
				{
					if (!token.isEmpty())
						if (keyword_checker(token)) {
							try {
								bw.write("<keyword> " + token + " </keyword>\n");

							} catch (IOException e) {

							}
							token = "";
						} else {
							try {
								bw.write("<identifier> " + token + " </identifier>\n");

							} catch (IOException e) {

							}
							token = "";
						}
					try {
						bw.write("<symbol> " + Character.toString(b) + " </symbol>\n");

					} catch (IOException e) {

					}
					i++;
					continue;
				}

				if (Character.isDigit(b)) {
					if (token.isEmpty())
					{	i = digit_extractor(tokenArray, i); 
					continue;
					}
				}
				token = token.concat(Character.toString(b));
				i++;
			}

			if (!token.isEmpty())
				if (keyword_checker(token)) {
					try {
						bw.write("<keyword> " + token + " </keyword>\n");

					} catch (IOException e) {

					}
				} else {
					try {
						bw.write("<identifier> " + token + " </identifier>\n");

					} catch (IOException e) {

					}
				}

		}

		private int digit_extractor(char[] tokenArray, int j) {
			String token = "";

			for (int i = j; i < tokenArray.length; i++) {



				if (Character.isDigit(tokenArray[i])) {
					token = token.concat(Character.toString(tokenArray[i]));

					if(i == tokenArray.length-1) //if we are at last character then write in file before continue
					{


						try {
							bw.write("<integerConstant> " + token + " </integerConstant>\n");

						} catch (IOException e) {

						}
					}


					continue;

				}
				try {
					bw.write("<integerConstant> " + token + " </integerConstant>\n");

				} catch (IOException e) {

				}
				return i;

			}
			return tokenArray.length;

		}

		private int string_extractor(char[] tokenArray, int j) {
			String token = "";
			for (int i = j; i < tokenArray.length; i++) {

				if (tokenArray[i] != '"') {
					token =token.concat(Character.toString(tokenArray[i]));
					continue;
				}

				try {
					bw.write("<stringConstant> " + token + "</stringConstant>\n");

				} catch (IOException e) {

				}
				return i;
			}

			return tokenArray.length;

		}

		private boolean keyword_checker(String token) {

			if (token.equals("class"))
				return true;
			if (token.equals("constructor"))
				return true;
			if (token.equals("function"))
				return true;
			if (token.equals("method"))
				return true;
			if (token.equals("field"))
				return true;
			if (token.equals("static"))
				return true;
			if (token.equals("var"))
				return true;
			if (token.equals("int"))
				return true;
			if (token.equals("char"))
				return true;
			if (token.equals("boolean"))
				return true;
			if (token.equals("void"))
				return true;
			if (token.equals("true"))
				return true;
			if (token.equals("false"))
				return true;
			if (token.equals("null"))
				return true;
			if (token.equals("this"))
				return true;
			if (token.equals("let"))
				return true;
			if (token.equals("do"))
				return true;
			if (token.equals("if"))
				return true;
			if (token.equals("else"))
				return true;
			if (token.equals("while"))
				return true;
			if (token.equals("return"))
				return true;

			return false;
		}
	} //tokenizer nested class end

	class Parser //nested class 
	{
		String token_type = null;
		String token = null;
		String token_type_slash = null;
		public Parser() //declaration
		{

		}
		public void makefile(String filename)
		{
			try {

				if (ext == null)
					fw = new FileWriter(file + "/" + filename + "2.xml");


				else
					fw = new FileWriter(filename + "2.xml");


				bw = new BufferedWriter(fw);

			} catch (IOException e) {

				e.printStackTrace();

			}
		}

		public void readfile(String file) {
			try {
				scan = new Scanner(new File(file));
			} catch (Exception e) {
				System.out.println("Doesn't exist");
			}
		}
		public void writeToFileParser() {

			scan.next();
			while (!scan.hasNext("</tokens>"))//writer start 
			{


				token_type = scan.next();
				token = scan.next();
				token_type_slash= scan.next();



				classCompile(  );


			}
			// writing class  at the end of file

			scan.close();
			return;

		}

		private void statementCompile( ) {

			switch(token) {

			case "do": doCompile( );
			break;
			case "let" : letCompile( );
			break;
			case "return" : returnCompile( );
			break;
			case "if" : ifCompile( );
			break;
			case "while" : whileCompile( );
			break;


			}

		} 

		private void classCompile( ) {
			staticIndex = -1;
			fieldIndex = -1;
			try {
				bw.write("<class>\n"); // writing class at the beginning of file

			} catch (IOException e) {

			}


			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}

			token_type = scan.next();
			token = scan.next();
			token_type_slash=scan.next();
			try {
				bw.write(" "+"<kind>"+" " + "class"+ " "+"</kind>"+"\n");  

			} catch (IOException e) {

			}
			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}
			className = token;

			while(!scan.hasNext("</tokens>"))
			{
				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();

				if(token.contains("{")) 
					break;

				try {
					bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

				} catch (IOException e) {

				}
			}
			//opening bracket
			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}
			while(!scan.hasNext("</tokens>"))
			{
				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();

				if(token.contains("}"))
					break;

				if(token.contains("field") || token.contains("static"))
					classVarDec( );
				else 
					subroutine( );
			}

			//closing bracket
			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}
			try {
				bw.write("</class>\n"); // writing class at the end of file

			} catch (IOException e) {

			}
		}
		private void whileCompile( ) {
			try {
				bw.write("<whileStatement>\n");  

			} catch (IOException e) {

			}			
			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}
			token_type = scan.next();
			token = scan.next();
			token_type_slash=scan.next();
			//printing (
			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}


			expressionCompile(  );

			token_type = scan.next();
			token = scan.next();
			token_type_slash=scan.next();
			//printing )
			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}
			//opening { 
			token_type = scan.next();
			token = scan.next();
			token_type_slash=scan.next();


			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}
			try {
				bw.write("<statements>\n");  

			} catch (IOException e) {

			}
			while(!scan.hasNext("</tokens>"))
			{
				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();

				if(token.contains("}"))
					break;


				statementCompile( );


			}
			try {
				bw.write("</statements>\n");  

			} catch (IOException e) {

			}
			//printing }
			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}

			try {
				bw.write("</whileStatement>\n");  

			} catch (IOException e) {

			}

		}
		private void expressionCompile( ) {
			try {
				bw.write("<expression>\n");  

			} catch (IOException e) {

			}	

			scan.useDelimiter("</");
			while(!scan.hasNext("\n<symbol> ; ") && !scan.hasNext("<symbol> ; ") && !scan.hasNext("\n<symbol> [)] ") && !scan.hasNext("<symbol> [)] ") &&  !scan.hasNext("<symbol> ] ") && !scan.hasNext("\n<symbol> ] ")&&!scan.hasNext("\n<symbol> , ") && !scan.hasNext("<symbol> , ") )
			{	
				scan.reset();

				String previous_token = token;

				if(scan.hasNext("<stringConstant>")) 
				{
					scan.next();
					scan.useDelimiter("</");
					try {
						bw.write("<term>\n");  

					} catch (IOException e) {

					}

					try {
						bw.write(" "+"<stringConstant> "+ scan.next()+"</stringConstant>"+"\n");  

					} catch (IOException e) {

					}

					try {
						bw.write("</term>\n");  

					} catch (IOException e) {

					}
					scan.reset();
					scan.next();//skip </stringConstant>

					scan.useDelimiter("</");
					continue;

				}

				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();



				if(token.contains("+") ||  (token.contains("-") && !previous_token.contains("(") &&  !previous_token.contains(",")) || token.contains("*") ||token.contains("/") ||token.contains("&") ||token.contains("|") ||token.contains("<") ||token.contains(">") ||token.contains("=")  )
				{


					try {
						bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

					} catch (IOException e) {

					}


					scan.useDelimiter("</");
					continue;
				}

				termCompile();

				scan.useDelimiter("</");
			}
			scan.reset();
			try {
				bw.write("</expression>\n");  

			} catch (IOException e) {

			}

		}
		private void termCompile() {
			try {
				bw.write("<term>\n");  

			} catch (IOException e) {

			}

			scan.useDelimiter("</");
			if(scan.hasNext("\n<symbol> \\. ") || scan.hasNext("<symbol> \\. "))
			{

				//if it's a subroutine with . 
				//classname is not written yet. We are writting its attributes first
				if(token.matches("([A-Z][a-zA-Z]*\\s*)*")) //if start with capital then it's a classname of this or another program
				{
					try {
						bw.write(" "+"<kind>"+" " + "class"+ " "+"</kind>"+"\n");  

					} catch (IOException e) {
					}
				}
				else
				{

					try {
						bw.write(" "+"<kind>"+" " + KindOf(token).toLowerCase()+ " "+"</kind>"+"\n");  

					} catch (IOException e) {

					}
					try {
						bw.write(" "+"<index>"+" " + indexOf(token )+ " "+"</index>"+"\n");  

					} catch (IOException e) {

					}
					try {
						bw.write(" "+"<type>"+" " + TypeOf(token)+ " "+"</type>"+"\n");  //to know class of the object  

					} catch (IOException e) {

					}
					try {
						bw.write(" "+"<status>"+" " + "using"+ " "+"</status>"+"\n");  

					} catch (IOException e) {

					}

				}
			}
			else //if not a subroutine with . 
			{
				if(token_type.contains("<identifier>"))
				{
					if(KindOf(token).contains("NONE"))
					{
						if(!token.contains("this"))
						{
							try {
								bw.write(" "+"<kind>"+" " + "subroutine"+ " "+"</kind>"+"\n");  

							} catch (IOException e) {

							}
						}
					}
					else
					{ 

						try {
							bw.write(" "+"<index>"+" " + indexOf(token )+ " "+"</index>"+"\n");  

						} catch (IOException e) {

						}

						try {
							bw.write(" "+"<kind>"+" " + KindOf(token).toLowerCase() + " "+"</kind>"+"\n");  

						} catch (IOException e) {

						}
						try {
							bw.write(" "+"<status>"+" " + "using"+ " "+"</status>"+"\n");  

						} catch (IOException e) {

						}
					}
				}
			}
			scan.reset();
			//always start with variable
			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}

			if(token_type.contains("<identifier>"))
			{ 

				//for all cases of identifier
				int choice=0;
				scan.useDelimiter("</");
				if(scan.hasNext("\n<symbol> [(] ") || scan.hasNext("<symbol> [(] "))
					choice = 1;
				if(scan.hasNext("\n<symbol> \\[ ") || scan.hasNext("<symbol> \\[ "))
				{choice = 2;

				}
				if(scan.hasNext("\n<symbol> \\. ") || scan.hasNext("<symbol> \\. "))
				{choice = 3;



				}

				scan.reset();
				switch(choice)
				{

				case 1 :

					token_type = scan.next();
					token = scan.next();
					token_type_slash=scan.next();

					//printing (
					try {
						bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

					} catch (IOException e) {

					}

					expressionList();

					token_type = scan.next();
					token = scan.next();
					token_type_slash=scan.next();

					//printing )
					try {
						bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

					} catch (IOException e) {

					}

					break;
				case 2 :


					token_type = scan.next();
					token = scan.next();
					token_type_slash=scan.next();

					//printing [
					try {
						bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

					} catch (IOException e) {

					}

					expressionCompile();

					token_type = scan.next();
					token = scan.next();
					token_type_slash=scan.next();

					//printing ]
					try {
						bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

					} catch (IOException e) {

					}

					break;
				case 3 :

					token_type = scan.next();
					token = scan.next();
					token_type_slash=scan.next();

					//printing .
					try {
						bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

					} catch (IOException e) {

					}
					token_type = scan.next();
					token = scan.next();
					token_type_slash=scan.next();

					//printing subroutineName
					try {
						bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

					} catch (IOException e) {

					}

					token_type = scan.next();
					token = scan.next();
					token_type_slash=scan.next();

					//printing (
					try {
						bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

					} catch (IOException e) {

					}


					expressionList();



					token_type = scan.next();
					token = scan.next();
					token_type_slash=scan.next();

					//printing )
					try {
						bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

					} catch (IOException e) {

					}

					break;	
				}

				scan.reset();
			}
			else if( token.contains("-") || token.contains("~"))
			{  

				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();


				termCompile();


			}
			else if( token.contains("("))
			{  

				expressionCompile();
				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();

				//printing )
				try {
					bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

				} catch (IOException e) {

				}
			}

			try {
				bw.write("</term>\n");  

			} catch (IOException e) {

			}

		}
		private void ifCompile( ) {
			try {
				bw.write("<ifStatement>\n");  

			} catch (IOException e) {

			}	
			//if print
			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}
			token_type = scan.next();
			token = scan.next();
			token_type_slash=scan.next();
			//printing (
			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}


			expressionCompile( );

			token_type = scan.next();
			token = scan.next();
			token_type_slash=scan.next();
			//printing )
			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}
			//opening { 
			token_type = scan.next();
			token = scan.next();
			token_type_slash=scan.next();


			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}

			try {
				bw.write("<statements>\n");  

			} catch (IOException e) {

			}
			while(!scan.hasNext("</tokens>"))
			{
				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();

				if(token.contains("}"))
					break;

				statementCompile( );

			}
			try {
				bw.write("</statements>\n");  

			} catch (IOException e) {

			}
			//printing }
			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}
			//to check next is else keyword
			scan.useDelimiter("</");
			if(scan.hasNext("\n<keyword> else "))
			{
				scan.reset();
				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();
				//printing else
				try {
					bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

				} catch (IOException e) {

				}
				//opening { 
				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();


				try {
					bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

				} catch (IOException e) {

				}
				try {
					bw.write("<statements>\n");  

				} catch (IOException e) {

				}
				while(!scan.hasNext("</tokens>"))
				{
					token_type = scan.next();
					token = scan.next();
					token_type_slash=scan.next();

					if(token.contains("}"))
						break;


					statementCompile( );

				}
				try {
					bw.write("</statements>\n");  

				} catch (IOException e) {

				}
				//printing }
				try {
					bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

				} catch (IOException e) {

				}
			}

			try {
				bw.write("</ifStatement>\n");  

			} catch (IOException e) {

			}
			scan.reset();
		}
		private void letCompile( ) {
			try {
				bw.write("<letStatement>\n");  

			} catch (IOException e) {

			}	
			//let print
			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}
			token_type = scan.next();
			token = scan.next();
			token_type_slash=scan.next();
			try {
				bw.write(" "+"<index>"+" " + indexOf(token )+ " "+"</index>"+"\n");  

			} catch (IOException e) {

			}
			//System.out.println(classVariable.containsKey(token.toUpperCase()));
			try {
				bw.write(" "+"<kind>"+" " + KindOf(token).toLowerCase() + " "+"</kind>"+"\n");  

			} catch (IOException e) {

			}
			try {
				bw.write(" "+"<status>"+" " + "using"+ " "+"</status>"+"\n");  

			} catch (IOException e) {

			}
			//printing varName
			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}


			token_type = scan.next();
			token = scan.next();
			token_type_slash=scan.next();
			//printing [ or =
			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}
			if(token.contains("["))
			{


				expressionCompile( );
				//printing ]	
				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();
				try {
					bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

				} catch (IOException e) {

				}
			}
			if(!token.contains("="))
			{
				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();
				//printing )
				try {
					bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

				} catch (IOException e) {

				}
			}


			expressionCompile( );
			//print ';
			token_type = scan.next();
			token = scan.next();
			token_type_slash=scan.next();
			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}


			try {
				bw.write("</letStatement>\n");  

			} catch (IOException e) {

			}

		}
		private void returnCompile( ) {
			try {
				bw.write("<returnStatement>\n");  

			} catch (IOException e) {

			}	
			//return print
			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}
			scan.useDelimiter("</");
			if(!scan.hasNext("\n<symbol> ; ") && !scan.hasNext("<symbol> ; "))
			{
				scan.reset();

				expressionCompile( );

				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();

			}
			else {

				scan.reset();
				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();

			}

			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}

			try {
				bw.write("</returnStatement>\n");  

			} catch (IOException e) {

			}	

		}
		private void doCompile( ) {
			try {
				bw.write("<doStatement>\n");  

			} catch (IOException e) {

			}	
			//do print
			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}

			token_type = scan.next();
			token = scan.next();
			token_type_slash=scan.next();

			scan.useDelimiter("</");
			if(scan.hasNext("\n<symbol> \\. ") || scan.hasNext("<symbol> \\. "))
			{

				if(token.matches("([A-Z][a-zA-Z]*\\s*)*"))  //if start with capital
				{
					try {
						bw.write(" "+"<kind>"+" " + "class"+ " "+"</kind>"+"\n");  

					} catch (IOException e) {
					}
				}
				else
				{

					//System.out.println(token);
					try {
						bw.write(" "+"<kind>"+" " + KindOf(token).toLowerCase()+ " "+"</kind>"+"\n");  

					} catch (IOException e) {

					}

					try {
						bw.write(" "+"<index>"+" " + indexOf(token )+ " "+"</index>"+"\n");  

					} catch (IOException e) {

					}
					try {
						bw.write(" "+"<type>"+" " + TypeOf(token) + " "+"</type>"+"\n");  //to know class of the object  

					} catch (IOException e) {

					}
					try {
						bw.write(" "+"<status>"+" " + "using"+ " "+"</status>"+"\n");  

					} catch (IOException e) {

					}

				}
			}
			else {
				try {
					bw.write(" "+"<kind>"+" " + "subroutine"+ " "+"</kind>"+"\n");  

				} catch (IOException e) {

				}

			}
			scan.reset();
			//subroutine or class 
			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}

			token_type = scan.next();
			token = scan.next();
			token_type_slash=scan.next();


			if(token.contains("."))
			{

				///print .
				try {
					bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

				} catch (IOException e) {

				}
				try {
					bw.write(" "+"<kind>"+" " + "subroutine"+ " "+"</kind>"+"\n");  

				} catch (IOException e) {

				}
				//print subName
				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();
				try {
					bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

				} catch (IOException e) {

				}
				//print (
				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();
				try {
					bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

				} catch (IOException e) {

				}
				expressionList();

				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();

				//print )
				try {
					bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

				} catch (IOException e) {

				}

			}
			else {
				//print (

				try {
					bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

				} catch (IOException e) {

				}
				try {
					bw.write("<expressionList>\n");  

				} catch (IOException e) {

				}
				while(!scan.hasNext("</tokens>"))
				{
					scan.useDelimiter("</");

					if(scan.hasNext("\n<symbol> [)] ") || scan.hasNext("<symbol> [)] "))
						break;

					scan.reset();

					expressionCompile( );

					scan.useDelimiter("</");
					if(scan.hasNext("\n<symbol> [)] ") || scan.hasNext("<symbol> [)] "))
						break;

					scan.reset();
					//print ,
					token_type = scan.next();
					token = scan.next();
					token_type_slash=scan.next();

					try {
						bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

					} catch (IOException e) {

					}
				}

				scan.reset();

				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();
				try {
					bw.write("</expressionList>\n");  

				} catch (IOException e) {

				}
				//print )
				try {
					bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

				} catch (IOException e) {

				}

			}
			//.........................................................
			//print ;
			token_type = scan.next();
			token = scan.next();
			token_type_slash=scan.next();
			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}

			try {
				bw.write("</doStatement>\n");  

			} catch (IOException e) {

			}	
		}

		private void expressionList() {
			try {
				bw.write("<expressionList>\n");  

			} catch (IOException e) {

			}
			while(!scan.hasNext("</tokens>"))
			{
				scan.useDelimiter("</");

				if(scan.hasNext("\n<symbol> [)] ") || scan.hasNext("<symbol> [)] "))
					break;

				scan.reset();

				expressionCompile( );

				scan.useDelimiter("</");
				if(scan.hasNext("\n<symbol> [)] ") || scan.hasNext("<symbol> [)] "))
					break;

				scan.reset();
				//print ,
				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();

				try {
					bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

				} catch (IOException e) {

				}
			}
			scan.reset();



			try {
				bw.write("</expressionList>\n");  

			} catch (IOException e) {

			}

		}
		private void subroutine( ) {

			int index =-1;
			try {
				bw.write("<subroutineDec>\n");  

			} catch (IOException e) {

			}

			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}
			String typeOfKeyword = token ; //constructor or method or function

			while(!scan.hasNext("</tokens>"))
			{
				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();





				if(token.contains("("))
					break;


				if(token_type.contains("<identifier>"))
				{  
					try {
						bw.write(" "+"<kind>"+" " + "subroutine"+ " "+"</kind>"+"\n");  

					} catch (IOException e) {

					}

					if(typeOfKeyword.contains("constructor"))
					{
						fieldcount = varCount("field", 0);
						try {
							bw.write(" "+"<fieldcount>"+" " + Integer.toString(fieldcount)+ " "+"</fieldcount>"+"\n");  

						} catch (IOException e) {

						}
					}


					try {
						bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

					} catch (IOException e) {

					}
				}
				else {
					try {
						bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

					} catch (IOException e) {

					}
				}
			}

			//printing (
			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}

			parameterCompile( typeOfKeyword);

			//printing )
			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}
			//subbody
			try {
				bw.write("<subroutineBody>\n");  

			} catch (IOException e) {

			}
			//opening { 
			token_type = scan.next();
			token = scan.next();
			token_type_slash=scan.next();

			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}

			while(!scan.hasNext("</tokens>"))
			{
				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();

				if(token.contains("}"))
					break;
				if(!token.contains("var"))
					break;

				index = varDec( index );



			}
			if(!token.contains("}"))
			{
				try {
					bw.write("<statements>\n");  

				} catch (IOException e) {

				}
				while(!scan.hasNext("</tokens>"))
				{
					statementCompile( );

					token_type = scan.next();
					token = scan.next();
					token_type_slash=scan.next();

					if(token.contains("}"))
						break;


				}

				try {
					bw.write("</statements>\n");  

				} catch (IOException e) {

				}
			}
			//printing }
			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}
			try {
				bw.write("</subroutineBody>\n");  

			} catch (IOException e) {

			}
			try {
				bw.write("</subroutineDec>\n");  

			} catch (IOException e) {

			}


		}

		private int varDec(int index ) {

			try {
				bw.write("<varDec>\n");  

			} catch (IOException e) {

			}

			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}

			token_type = scan.next();
			token = scan.next();
			token_type_slash=scan.next();

			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}
			String type = token;

			while(!scan.hasNext("</tokens>"))
			{
				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();

				if(token.contains(";"))
					break;

				if(token_type.contains("<identifier>") )
				{  index++;
				symbolTableDefine(token, type, "var", index ,1);

				//System.out.println( token );
				try {
					bw.write(" "+"<index>"+" " + indexOf(token )+ " "+"</index>"+"\n");  

				} catch (IOException e) {

				}

				try {
					bw.write(" "+"<kind>"+" " + "var"+ " "+"</kind>"+"\n");  

				} catch (IOException e) {

				}
				try {
					bw.write(" "+"<status>"+" " + "defined"+ " "+"</status>"+"\n");  

				} catch (IOException e) {

				}


				try {
					bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

				} catch (IOException e) {

				}
				}
				else
				{				try {
					bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

				} catch (IOException e) {

				}
				}
			}
			//print ;
			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}
			try {
				bw.write("</varDec>\n");  

			} catch (IOException e) {

			}
			return index;
		}
		private void parameterCompile( String typeOfKeyword) {
			int index = -1;
			try {
				bw.write("<parameterList>\n");  

			} catch (IOException e) {

			}
			//
			subroutineVariable.clear();  
			String type="";
			if(typeOfKeyword.contains("method")  )
			{ index++;
			symbolTableDefine("this", className, "argument",index, 1);
			}


			while(!token.contains(")"))
			{  


				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();



				if(!token.contains(",")  && !token.contains(")") )
				{type = token;
				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();
				index ++;
				symbolTableDefine(token, type, "argument",index, 1);

				try {
					bw.write(" "+"<index>"+" " + indexOf(token )+ " "+"</index>"+"\n");  

				} catch (IOException e) {

				}

				try {
					bw.write(" "+"<kind>"+" " + "argument"+ " "+"</kind>"+"\n");  

				} catch (IOException e) {

				}
				try {
					bw.write(" "+"<status>"+" " + "defined"+ " "+"</status>"+"\n");  

				} catch (IOException e) {

				}


				try {
					bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

				} catch (IOException e) {

				}
				}
				if(token.contains(",")  )
				{
					try {
						bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

					} catch (IOException e) {

					}
				}

			}




			try {
				bw.write("</parameterList>\n");  

			} catch (IOException e) {

			}

		}
		private void classVarDec( ) {

			try {
				bw.write("<classVarDec>\n");  

			} catch (IOException e) {

			}
			String kind = token;
			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}


			token_type = scan.next();
			token = scan.next();
			token_type_slash=scan.next();

			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}
			String type = token;

			while(!scan.hasNext("</tokens>"))
			{
				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();

				if(token.contains(";"))
					break;

				if(token_type.contains("<identifier>") )
				{ 

					if(kind.contains("static"))
					{		staticIndex++;
					symbolTableDefine(token, type, kind,staticIndex, 0);
					}
					else
					{	fieldIndex++;
					symbolTableDefine(token, type, kind,fieldIndex, 0);
					}


					try {
						bw.write(" "+"<index>"+" " + indexOf(token)+ " "+"</index>"+"\n");  

					} catch (IOException e) {

					}

					try {
						bw.write(" "+"<kind>"+" " + kind+ " "+"</kind>"+"\n");  

					} catch (IOException e) {

					}
					try {
						bw.write(" "+"<status>"+" " + "defined"+ " "+"</status>"+"\n");  

					} catch (IOException e) {

					}


					try {
						bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

					} catch (IOException e) {

					}
				}
				else 
				{
					try {
						bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

					} catch (IOException e) {

					}
				}
			}

			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}
			try {
				bw.write("</classVarDec>\n");  

			} catch (IOException e) {

			}
		}
		/*private String token_checker(String a) {


		}*/	
	}//Parser nested class end
	//..........................................................................
	class VMWriter //nested class 
	{
		String token_type = null;
		String token = null;
		String token_type_slash = null;
		public VMWriter() //declaration
		{

		}
		public void makefile(String filename)
		{
			try {

				if (ext == null)
					fw = new FileWriter(file + "/" + filename + ".vm");


				else
					fw = new FileWriter(filename + ".vm");


				bw = new BufferedWriter(fw);

			} catch (IOException e) {

				e.printStackTrace();

			}
		}

		public void readfile(String file) {
			try {
				scan = new Scanner(new File(file));
			} catch (Exception e) {
				System.out.println("Doesn't exist");
			}
		}
		public void  writePush(String segment, int index) {

			try {
				bw.write("push "+ segment+" " +index+"\n");  

			} catch (IOException e) {

			}

		}
		public void writePop(String segment, int index) {
			try {
				bw.write("pop "+ segment+" " +index+"\n");  

			} catch (IOException e) {

			}
		}
		public void writeArithmetic(String command) {

			switch(command)
			{
			case "+" :
				try {
					bw.write("add \n");  

				} catch (IOException e) {

				}
				break;
			case "-" :
				try {
					bw.write("sub \n");  

				} catch (IOException e) {

				}
				break;
			case "*" :
				writeCall("Math.multiply", 2);
				break;

			case "/" :
				writeCall("Math.divide", 2);
				break;

			case "=" :
				try {
					bw.write("eq \n");  

				} catch (IOException e) {

				}
				break;
			case "&gt;" :
				try {
					bw.write("gt \n");  

				} catch (IOException e) {

				}
				break;
			case "&lt;" :
				try {
					bw.write("lt \n");  

				} catch (IOException e) {

				}
				break;

			case "&amp;" :
				try {
					bw.write("and \n");  

				} catch (IOException e) {

				}
				break;
			case "|" :
				try {
					bw.write("or \n");  

				} catch (IOException e) {

				}
				break;
			}



		}
		public void writeLabel(String label) {
			try {
				bw.write("label " + label+" \n");  

			} catch (IOException e) {

			}
		}
		public void writeGoto(String label) {
			try {
				bw.write("goto "+label+" \n");  

			} catch (IOException e) {

			}
		}
		public void writeIf(String label)
		{
			try {
				bw.write("if-goto "+label+" \n");  

			} catch (IOException e) {

			}
		}
		public void writeCall(String name, int nArgs)
		{
			try {
				bw.write("call "+ name+" " +nArgs+"\n");  

			} catch (IOException e) {

			}
		}

		public void writeFunction(String name, int nLocals) {
			try {
				bw.write("function "+ name+" " +nLocals+"\n");  

			} catch (IOException e) {

			}
		}
		public void writeReturn()
		{
			try {
				bw.write("return \n");  

			} catch (IOException e) {

			}
		}

		public void writeToFileParser() {

			while(!scan.hasNext("<identifier>"))
			{
				scan.next();
			}
			scan.next();

			className = scan.next();

			while (!scan.hasNext("</class>"))//writer start 
			{
				token = scan.next();

				/*	if(token.contains("<classVarDec>"))
					classVarDec( );*/

				if(token.contains("<subroutineDec>"))
					subroutine( );

			}


			scan.close();
			return;

		}
		/*	private void classVarDec() {

		}*/
		private void subroutine() {
			int fieldcount=0;
			String subroutineName = "";
			String subroutineType = "";
			int localCount = 0;

			//method func cons
			token_type = scan.next();
			token = scan.next();
			token_type_slash= scan.next();

			subroutineType = token;

			if(subroutineType.contains("method"))
			{
				localCount++;
			}
			if(subroutineType.contains("constructor"))
			{
				while(!scan.hasNext("<fieldcount>"))
				{
					scan.next();
				}
				scan.next();

				fieldcount = scan.nextInt();

			}
			while(!scan.hasNext("<identifier>"))
			{
				scan.next();
			}
			scan.next(); //skip <identifier>
			token=scan.next();

			if(token.matches("([A-Z][a-zA-Z]*\\s*)*"))
			{
				while(!scan.hasNext("<identifier>"))
				{
					scan.next();
				}
				scan.next(); //skip <identifier>
				token=scan.next();
			}
			subroutineName=token;

			while(!scan.hasNext("<statements>") && !scan.hasNext("<returnStatement>") )
			{ int flag =0; //for skipping till an identifier comes
			token= scan.next();
			if(token.contains("<varDec>"))
			{ 
				while(flag == 0)
				{
					token_type = scan.next();
					token = scan.next();
					token_type_slash= scan.next();
					if(token_type.contains("<identifier>"))
					{
						localCount++;
						token_type = scan.next();
						token = scan.next();
						token_type_slash= scan.next();
						while(!token.contains(";") )
						{
							//for variable_name,variable_name
							if(token.contains(","))
								localCount++;

							token_type = scan.next();
							token = scan.next();
							token_type_slash= scan.next();


						}
						flag =1;
					}
				}

			}

			}
			writeFunction(className+"."+subroutineName, localCount);

			if(subroutineType.contains("constructor"))
			{  
				writePush("constant", fieldcount);
				writeCall("Memory.alloc", 1);
				writePop("pointer", 0);
			}
			else if(subroutineType.contains("method"))
			{
				writePush("argument", 0);
				writePop("pointer", 0);
			}



			statementCompile();

		}
		private void statementCompile() { 

			while(!scan.hasNext("</statements>"))
			{
				token= scan.next();
				switch(token) {

				case "<doStatement>": doCompile( );
				break;
				case "<letStatement>" : letCompile( );
				break;
				case "<returnStatement>" : returnCompile( );
				break;
				case "<ifStatement>" : ifCompile( );
				break;
				case "<whileStatement>" : whileCompile( );
				break;


				}

			}

		}
		private void whileCompile() {
			int label1=0;
			int label2=0;
			label1= ++labelNo;
			label2 = ++labelNo;

			writeLabel("L"+label1);
			while(!scan.hasNext("<expression>"))
			{
				scan.next();
			}
			scan.next();
			expressionCompile();

			try {
				bw.write("not \n");  

			} catch (IOException e) {

			} 
			writeIf("L"+label2);

			while(!scan.hasNext("<statements>"))
			{
				scan.next();
			}
			scan.next();
			statementCompile();

			writeGoto("L"+label1);
			writeLabel("L"+label2);

			while(!scan.hasNext("</whileStatement>"))
			{
				scan.next();
			}

		}
		private void ifCompile() {
			int label1=0;
			int label2=0;
			int elseflag = 0;

			while(!scan.hasNext("<expression>"))
			{
				scan.next();
			}
			scan.next();
			expressionCompile();

			try {
				bw.write("not \n");  

			} catch (IOException e) {

			}


			label1= ++labelNo;

			writeIf("L"+label1);

			while(!scan.hasNext("<statements>"))
			{
				scan.next();
			}
			scan.next();

			statementCompile();



			while(!scan.hasNext("</ifStatement>"))
			{
				token= scan.next();
				if(token.contains("else"))
				{
					elseflag =1;
					label2 = ++labelNo;
					writeGoto("L"+label2);
					writeLabel("L"+label1);

					while(!scan.hasNext("<statements>"))
					{
						scan.next();
					}
					scan.next();

					statementCompile();
				}
			}
			if(elseflag == 0)
			{
				writeLabel("L"+label1);
			}
			else {
				writeLabel("L"+label2);
			}

		}
		private void returnCompile() {
			int expression = 0; //if expression compiles


			while(!scan.hasNext("</returnStatement>"))
			{
				token = scan.next();
				if(token.contains("<expression>"))
				{
					expressionCompile();
					expression = 1;
				}

			}

			if(expression == 0)
			{
				writePush("constant", 0);
			}

			writeReturn();
		}
		private void letCompile() {
			int index =0;
			String segment = "";
			int arrayType=0; //see whether array is compiled

			while(!scan.hasNext("</letStatement>"))
			{
				token = scan.next();
				if(token.contains("<index>"))
				{
					index = scan.nextInt();
				}
				if(token.contains("<kind>"))
				{
					segment = scan.next();
					if(segment.contains("field"))
					{
						segment = "this";
					}
					else if(segment.contains("var"))
					{
						segment = "local";

					}


				}
				if(token.contains("<symbol>"))
				{
					token = scan.next();
					if(token.contains("["))
					{ //push a
						writePush(segment, index);
						while(!scan.hasNext("<expression>"))
						{
							scan.next();
						}
						scan.next();
						expressionCompile();
						writeArithmetic("+");
						segment = "that";
						index = 0;
						arrayType=1;
					}
					if(token.contains("="))
					{
						break;
					}
				}
			}
			while(!scan.hasNext("<expression>"))
			{
				scan.next();
			}
			scan.next();
			expressionCompile();
			if(arrayType==1)
			{
				writePop("temp", 0); //popping any value to temp0
				writePop("pointer", 1);
				writePush("temp", 0);
			}
			writePop(segment, index);
		}
		private void doCompile() {
			String subroutineClass = "";
			String subroutineName = "";
			String subroutineType = "";
			int params_no = 0;
			while(!scan.hasNext("<kind>"))
			{
				scan.next();
			}
			scan.next(); //skip <kind>


			token = scan.next();

			if(token.contains("class"))
			{
				while(true)
				{
					token = scan.next();
					if(token.contains("<identifier>"))
						break;
				}

				subroutineClass = scan.next();
				while(true)
				{
					token = scan.next();
					if(token.contains("<identifier>"))
						break;
				}
				subroutineName = scan.next();
				subroutineType = "function";
			}
			else if(token.contains("subroutine"))
			{

				//as it's always classname.variable
				subroutineClass = className; 
				while(true)
				{
					token = scan.next();
					if(token.contains("<identifier>"))
						break;
				}
				subroutineName = scan.next();
				subroutineType = "method";
				writePush("pointer", 0);
				params_no++;
			}
			else
			{           //as it's always classname.variable in vm

				String kind = token;

				while(!scan.hasNext("<index>"))
				{
					scan.next();
				}
				scan.next();
				int index = scan.nextInt();
				if(kind.contains("field"))
				{
					writePush("this", index);
				}
				else if(kind.contains("var"))
				{
					writePush("local", index);
				}
				else {
					writePush(kind, index);
				}

				while(true)
				{
					token = scan.next();
					if(token.contains("<type>")) 
						break;
				}
				subroutineClass = scan.next();
				while(true)
				{
					token = scan.next();
					if(token.contains("<symbol>"))
						break;
				}
				while(true)
				{
					token = scan.next();
					if(token.contains("<identifier>"))
						break;
				}
				subroutineName = scan.next();
				subroutineType = "method";

				params_no++;
			}

			while(!scan.hasNext("<expressionList>"))
			{
				scan.next();
			}

			scan.next(); //skip <expressionList> string

			params_no = params_no + expressionListCompile( subroutineType);

			writeCall(subroutineClass+"."+subroutineName, params_no);

			writePop("temp", 0);
		}

		private int expressionListCompile( String typeOfKeyword) {
			int params_no = 0;

			if (scan.hasNext("</expressionList>"))
			{
				return 0;
			}
			else {
				params_no++;
			}
			while(!scan.hasNext("</expressionList>"))
			{
				token = scan.next();
				if(token.contains("<expression>"))
					expressionCompile();
				if(token.contains(","))
					params_no++;

			}  

			return  params_no;
		}
		private void expressionCompile() {
			String op="";
			while(!scan.hasNext("</expression>"))
			{ 
				token=scan.next();

				if(token.contains("<term>"))
				{
					int i = termCompile();
					if (i == 1)
					{
						//idea from nand2tetris array handle
						writePop("pointer", 1);

						writePush("that", 0); 


					}
				}

				if(token.contains("<symbol>"))
				{

					op = scan.next();

					//scan.next(); //</symbol>
					int i = termCompile();
					if (i == 1)
					{
						//idea from nand2tetris array handle
						writePop("pointer", 1);


						writePush("that", 0);
						/*writePop("temp", 0);
						//for first array
						writePop("pointer", 1);

						writePush("that", 0);
						//pushing second array
						writePush("temp", 0);*/
						writeArithmetic(op);

					}
					else {
						writeArithmetic(op);
					}
				}

			}
		}
		private int termCompile() {

			while(!scan.hasNext("</term>"))
			{ 
				token=scan.next();

				if(token.contains("<integerConstant>"))
				{
					token = scan.next();
					writePush("constant", Integer.parseInt(token));
				}
				else if(token.contains("<index>"))
				{
					int index = scan.nextInt();
					scan.next(); //skipping </index>
					scan.next(); //skipping <kind>
					String kind = scan.next();
					if(kind.contains("field"))
					{
						writePush("this", index);
					}
					else if(kind.contains("var"))
					{
						writePush("local", index);
					}
					else {
						writePush(kind, index);
					}

				}
				else if(token.contains("<expression>"))
				{
					expressionCompile();
				}
				if(token.contains("<stringConstant>"))
				{

					scan.useDelimiter("</");
					token = scan.next();



					scan.reset();
					char[] tokenArray = token.toCharArray();
					writePush("constant", token.length());
					writeCall("String.new", 1);
					for(int k = 0; k<token.length();k++)
					{
						int asciiCode = tokenArray[k];
						writePush("constant", asciiCode);
						writeCall("String.appendChar", 2);

					}
				}
				else if(token.contains("<symbol>"))
				{
					token = scan.next();
					if(token.contains("-"))
					{
						termCompile();
						try {
							bw.write("neg \n");  

						} catch (IOException e) {

						}

					}
					if(token.contains("~"))
					{
						termCompile();
						try {
							bw.write("not \n");  

						} catch (IOException e) {

						}

					}
					if(token.contains("[")) //array compile
					{
						expressionCompile();
						writeArithmetic("+");
						while(!scan.hasNext("</term>"))
						{
							scan.next();
						}
						return 1;
					}
					if(token.contains("[(]"))  
					{
						expressionCompile();

					}
				}
				else if(token.contains("<keyword>"))
				{
					token = scan.next();
					if(token.contains("true"))
					{
						writePush("constant", 0);
						try {
							bw.write("not \n");  

						} catch (IOException e) {

						}
					}

					if(token.contains("false"))
					{
						writePush("constant", 0);
					}

					if(token.contains("null"))
					{
						writePush("constant", 0);
					}
					if(token.contains("this"))
					{
						writePush("pointer", 0);
					}

				}
				else if(token.contains("<kind>"))
				{
					String subroutineClass = "";
					String subroutineName = "";
					String subroutineType = "";
					int params_no = 0;



					token = scan.next();

					if(token.contains("class"))
					{
						while(true)
						{
							token = scan.next();
							if(token.contains("<identifier>"))
								break;
						}

						subroutineClass = scan.next();
						while(true)
						{
							token = scan.next();
							if(token.contains("<identifier>"))
								break;
						}
						subroutineName = scan.next();
						subroutineType = "function";
					}
					else if(token.contains("subroutine"))
					{

						//as it's always classname.variable
						subroutineClass = className; 
						while(true)
						{
							token = scan.next();
							if(token.contains("<identifier>"))
								break;
						}
						subroutineName = scan.next();
						subroutineType = "method";
						writePush("pointer", 0);
						params_no++;
					}
					else
					{           //as it's always classname.variable in vm

						String kind = token;

						while(!scan.hasNext("<index>"))
						{
							scan.next();
						}
						scan.next();
						int index = scan.nextInt();
						if(kind.contains("field"))
						{
							writePush("this", index);
						}
						else if(kind.contains("var"))
						{
							writePush("local", index);
						}
						else {
							writePush(kind, index);
						}

						while(true)
						{
							token = scan.next();
							if(token.contains("<type>")) 
								break;
						}
						subroutineClass = scan.next();
						while(true)
						{
							token = scan.next();
							if(token.contains("<symbol>"))
								break;
						}
						while(true)
						{
							token = scan.next();
							if(token.contains("<identifier>"))
								break;
						}
						subroutineName = scan.next();
						subroutineType = "method";

						params_no++;
					}

					while(!scan.hasNext("<expressionList>"))
					{
						scan.next();
					}

					scan.next(); //skip <expressionList> string

					params_no = params_no + expressionListCompile( subroutineType);

					writeCall(subroutineClass+"."+subroutineName, params_no);

				}

			}
			return 0;
		}

	}
	//VMWRITER nested class end
	public static void main(String args[]) {
		Scanner scan2 = new Scanner(args[0]); // for having same filename

		if (args[0].contains("/"))
			scan2.useDelimiter("[/]");
		else
			scan2.useDelimiter("[.]");

		file = scan2.next(); // to get filename or directory

		ext = null; // for extension

		Jack_compiler st = new Jack_compiler();

		if (scan2.hasNext())
			ext = scan2.next();


		scan2.close();
		//.......................................................................................................Tokenizer Starts

		Jack_compiler.Tokenizer tokenizer = st.new Tokenizer();
		int i = 0;//for filename array

		if (ext == null) // if argument is a directory not a jack file
		{
			File directory = new File(args[0]);

			// get all the files from a directory

			File[] fList = directory.listFiles();
			int n = fList.length;
			String filename_array[] = new String[n];

			for (File file : fList) {

				if (file.getName().contains(".jack")) {
					filename_array[i] = file.getName();
					i++;
				}
			}

			for (String filename : filename_array) {
				if (filename == null)
					break;

				System.out.println("opening " + filename + ".... ");
				tokenizer.makefile(filename.substring(0,filename.indexOf("."))); //to extract string till dot

				tokenizer.readfile(file + "/" + filename);

				tokenizer.writeToFileTokenizer();
				try {
					bw.close();
					fw.close();
				} catch (IOException e) {
				}
			}

		} else {

			tokenizer.makefile(file);
			tokenizer.readfile(args[0]);

			tokenizer.writeToFileTokenizer();
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
			}
		}


		//.......................................................................................................Parser Starts
		Jack_compiler.Parser parser = st.new Parser();
		i = 0;//for filename array
		System.out.println("Parsing");
		if (ext == null) // if argument is a directory not a jack file
		{
			File directory = new File(args[0]);

			// get all the files from a directory

			File[] fList = directory.listFiles();
			int n = fList.length;
			String filename_array[] = new String[n];

			for (File file : fList) {

				if (file.getName().contains("1.xml")) {
					filename_array[i] = file.getName();
					i++;
				}
			}

			for (String filename : filename_array) {
				if (filename == null)
					break;

				System.out.println("opening " + filename + ".... ");

				parser.makefile(filename.substring(0,filename.indexOf("1"))); //to extract string till dot
				System.out.println(filename);

				parser.readfile(file + "/" + filename);

				parser.writeToFileParser();
				try {
					bw.close();
					fw.close();
				} catch (IOException e) {
				}

				classVariable.clear(); //to reset class level symbol table
			}

		} else {
			parser.makefile(file);
			parser.readfile(file+"1.xml");

			parser.writeToFileParser();

			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
			}

		}
		i = 0; //for filename array
	 
		if (ext == null) // if argument is a directory not a jack file
		{


			File directory = new File(args[0]);

			// get all the files from a directory

			File[] fList = directory.listFiles();
			int n = fList.length;
			String filename_array[] = new String[n];

			for (File file : fList) {

				if (file.getName().contains("1.xml")) {
					filename_array[i] = file.getName();
					i++;
				}
			}

			for (String filename : filename_array) {
				if (filename == null)
					break;

				System.out.println("Deleting " + filename + ".... ");

				try
				{
					Files.deleteIfExists(Paths.get(file + "/" + filename));
				}
				catch(NoSuchFileException e)
				{
					System.out.println("No such file/directory exists");
				}
				catch(DirectoryNotEmptyException e)
				{
					System.out.println("Directory is not empty.");
				}
				catch(IOException e)
				{
					System.out.println("Invalid permissions.");
				}

			}

		} else {



			try
			{
				Files.deleteIfExists(Paths.get(args[0].substring(0, args[0].indexOf("."))+"1.xml"));
			}
			catch(NoSuchFileException e)
			{
				System.out.println("No such file/directory exists");
			}
			catch(DirectoryNotEmptyException e)
			{
				System.out.println("Directory is not empty.");
			}
			catch(IOException e)
			{
				System.out.println("Invalid permissions.");
			}


		}  
		//.......................................................................................................VmWriter Starts
		Jack_compiler.VMWriter vmwriter = st.new VMWriter();
		i = 0;   //for filename array
		System.out.println("Compiling");
		if (ext == null) // if argument is a directory not a jack file
		{
			File directory = new File(args[0]);

			// get all the files from a directory

			File[] fList = directory.listFiles();
			int n = fList.length;
			String filename_array[] = new String[n];

			for (File file : fList) {

				if (file.getName().contains("2.xml")) {
					filename_array[i] = file.getName();
					i++;
				}
			}

			for (String filename : filename_array) {
				if (filename == null)
					break;
				System.out.println("opening " + filename + ".... ");

				vmwriter.makefile(filename.substring(0,filename.indexOf("2"))); //to extract string till dot

				vmwriter.readfile(file + "/" + filename);

				vmwriter.writeToFileParser();
				try {
					bw.close();
					fw.close();
				} catch (IOException e) {
				}


			}

		} else {
			vmwriter.makefile(file);
			vmwriter.readfile(file+"2.xml");

			vmwriter.writeToFileParser();

			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
			}

		}
		i = 0; //for filename array for deleting files

		  if (ext == null) // if argument is a directory not a jack file
		{


			File directory = new File(args[0]);

			// get all the files from a directory

			File[] fList = directory.listFiles();
			int n = fList.length;
			String filename_array[] = new String[n];

			for (File file : fList) {

				if (file.getName().contains("2.xml")) {
					filename_array[i] = file.getName();
					i++;
				}
			}

			for (String filename : filename_array) {
				if (filename == null)
					break;

				System.out.println("Deleting " + filename + ".... ");

				try
				{
					Files.deleteIfExists(Paths.get(file + "/" + filename));
				}
				catch(NoSuchFileException e)
				{
					System.out.println("No such file/directory exists");
				}
				catch(DirectoryNotEmptyException e)
				{
					System.out.println("Directory is not empty.");
				}
				catch(IOException e)
				{
					System.out.println("Invalid permissions.");
				}

			}

		} else {

			try
			{
				Files.deleteIfExists(Paths.get(args[0].substring(0, args[0].indexOf("."))+"2.xml"));
			}
			catch(NoSuchFileException e)
			{
				System.out.println("No such file/directory exists");
			}
			catch(DirectoryNotEmptyException e)
			{
				System.out.println("Directory is not empty.");
			}
			catch(IOException e)
			{
				System.out.println("Invalid permissions.");
			}


		}   


	} 
}
