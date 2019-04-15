package application.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class User {
	
public static boolean loggedIn;

/**
 * username - String that contains the 
 * username of the user logged in. 
 */
public static String username; 

/**
 * password - String that contains the 
 * password of the user logged in.  
 */
public static String password;
	
/**
 * userRecipes - ArrayList<String> that contains
 * the query strings of all the favorite recipes 
 * the user wants to save for later reference. 
 */
public static ArrayList<String> userRecipes;

/**
 * validateUser - this method takes in a String username and 
 * a String password that are used to verify the identiy of the
 * user. 
 * @param username - String that contains the user name of the 
 * user. 
 * @param password - String that contains the password of the 
 * user. 
 * @return boolean - returns a boolean value of true if the user and
 * password match. False otherwise. 
 */
public static boolean validateUser(String username, String password) {
	try { 
		Scanner scan = new Scanner(new File("./users/login.csv"));
		
		while(scan.hasNextLine()) {
			String line = scan.nextLine();
			String token[] = line.split(",");
			if(token[0].equalsIgnoreCase(username)) {
				if(token[1].equalsIgnoreCase(password)) {
					scan.close();
					return true; 
				}
			}
					
		}scan.close();
	} catch(IOException e) {
		e.printStackTrace();
	}
	return false;
}

public static int createNewAccount(String username, String password, String confirmPassword) {
	if (!password.equals(confirmPassword)) {
		return 3;
	}else {
		try {
			Scanner scan = new Scanner(new File("./users/login.csv"));
			
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				String token[] = line.split(",");
				if(token[0].equalsIgnoreCase(username)) {
					scan.close();
					return 2;
				}
			}scan.close();		
		} catch(IOException f) {
			f.printStackTrace();
		}
	}
	
	try {
	String output = username + "," + password;
	BufferedWriter writer = new BufferedWriter(new FileWriter("./users/login.csv",true));
	writer.write(output+"\n");
	writer.close();
	return 1;
	} catch(IOException f) {
		f.printStackTrace();
	}
	return 0;
}


/**
 * getUsername - gets the String that contains
 * the username of the user. 
 * @return username - String that contains the 
 * username of the user. 
 */
public static String getUsername() {
	return username;
}

/**
 * setUsername - sets the String that contains 
 * the username of the user. 
 * @param username - String that contains the 
 * username of the user.
 */
public static void setUsername(String username) {
	User.username = username;
}

/**
 * getPassword - gets the String that contains 
 * the password of the user. 
 * @return password - String that contains the 
 * password of the user. 
 */
public static String getPassword() {
	return password;
}

/**
 * setPassword - sets the String that contains
 * the password of the user. 
 * @param password - String that contains the 
 * password of the user. 
 */
public static void setPassword(String password) {
	User.password = password;
}

/**
 * getUserRecipes - gets the ArrayList<String> 
 * that contain the query Strings of the user
 * saved. 
 * @return userRecipes - ArrayList<String> of 
 * recipes Strings. 
 */
public static ArrayList<String> getUserRecipes() {
	return userRecipes;
}

/**
 * setUserRecipes - gets the ArrayList<String> 
 * that contain the query Strings of the user
 * saved.
 * @param userRecipes - ArrayList<String> of 
 * recipes Strings.
 */
public static void setUserRecipes(ArrayList<String> userRecipes) {
	User.userRecipes = userRecipes;
} 
/**
 * isLoggedIn - returns if user is logged in 
 * @return boolean 
 */
public static boolean isLoggedIn() {
	return loggedIn;
}
/**
 * setLoggedIn - sets if the user is logged in 
 * @param loggedIn - boolean that has status 
 * off login of user.
 */
public static void setLoggedIn(boolean loggedIn) {
	User.loggedIn = loggedIn;
}
}
