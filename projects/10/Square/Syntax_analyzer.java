//package syntax_analyzer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Syntax_analyzer {

	static String file;
	static FileWriter fw;
	static BufferedWriter bw;
	static Scanner scan;
	static String ext = null;
 
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

				if (scan.hasNext(Pattern.compile("/{2,}"))) // for ignoring comments
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
				{

					String quote = "";
					while(!quote.contains("\""))
						quote = quote + scan.next();

					a = a+quote;
					a = a.trim();

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
					{	i = digit_extractor(tokenArray, i); // to skip string's position
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
					bw.write("<stringConstant> " + token + " </stringConstant>\n");

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
			// TODO Auto-generated method stub


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
			try {
				bw.write("<class>\n"); // writing class at the beginning of file

			} catch (IOException e) {

			}


			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}
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

				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();
				
				 
				if(token.contains("+") ||  (token.contains("-") && !previous_token.contains("(") ) || token.contains("*") ||token.contains("/") ||token.contains("&") ||token.contains("|") ||token.contains("<") ||token.contains(">") ||token.contains("=")  )
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
			try {
				bw.write("<subroutineDec>\n");  

			} catch (IOException e) {

			}

			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}
			while(!scan.hasNext("</tokens>"))
			{
				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();



				if(token.contains("("))
					break;

				try {
					bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

				} catch (IOException e) {

				}
			}

			//printing (
			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}

			parameterCompile( );

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

				varDec( );



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

		private void varDec( ) {
			try {
				bw.write("<varDec>\n");  

			} catch (IOException e) {

			}

			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}
			while(!scan.hasNext("</tokens>"))
			{
				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();

				if(token.contains(";"))
					break;

				try {
					bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

				} catch (IOException e) {

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

		}
		private void parameterCompile( ) {
			try {
				bw.write("<parameterList>\n");  

			} catch (IOException e) {

			}

			while(!scan.hasNext("</tokens>"))
			{
				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();

				if(token.contains(")"))
					break;

				try {
					bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

				} catch (IOException e) {

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

			try {
				bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

			} catch (IOException e) {

			}
			while(!scan.hasNext("</tokens>"))
			{
				token_type = scan.next();
				token = scan.next();
				token_type_slash=scan.next();

				if(token.contains(";"))
					break;

				try {
					bw.write(" "+token_type+" " + token+ " "+token_type_slash+"\n");  

				} catch (IOException e) {

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
			// TODO Auto-generated method stub

		}*/	
	}//Parser nested class end

	public static void main(String args[]) {
		Scanner scan2 = new Scanner(args[0]); // for having same filename

		if (args[0].contains("/"))
			scan2.useDelimiter("[/]");
		else
			scan2.useDelimiter("[.]");

		file = scan2.next(); // to get filename or directory

		ext = null; // for extension

		Syntax_analyzer st = new Syntax_analyzer();

		if (scan2.hasNext())
			ext = scan2.next();


		scan2.close();
		//.......................................................................................................Tokenizer Starts

		Syntax_analyzer.Tokenizer tokenizer = st.new Tokenizer();
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
		Syntax_analyzer.Parser parser = st.new Parser();
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



	}
}
