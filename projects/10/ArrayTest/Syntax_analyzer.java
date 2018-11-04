//package syntax_analyzer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Syntax_analyzer {

	static String file;
	static FileWriter fw;
	static BufferedWriter bw;
	static Scanner scan;
	static String ext = null;

	public Syntax_analyzer(String filename) {
		try {

			if (ext == null)
				fw = new FileWriter(filename + "/" + filename + "1.xml");
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

	public static void main(String args[]) {
		Scanner scan2 = new Scanner(args[0]); // for having same filename

		if (args[0].contains("/"))
			scan2.useDelimiter("[/]");
		else
			scan2.useDelimiter("[.]");

		file = scan2.next(); // to get filename or directory

		ext = null; // for extension

		if (scan2.hasNext())
			ext = scan2.next();
		Syntax_analyzer tokenizer = new Syntax_analyzer(file);
		scan2.close();

		int i = 0;

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

				tokenizer.readfile(file + "/" + filename);
				tokenizer.writeToFileTokenizer();
			}

		} else {

			tokenizer.readfile(args[0]);
			tokenizer.writeToFileTokenizer();

		}

		try {
			bw.close();
			fw.close();
		} catch (IOException e) {
		}

	}
}
