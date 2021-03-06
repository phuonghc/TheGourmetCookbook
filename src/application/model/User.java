/**
 * User - model for the user 
 * Application Programming Spring 2019 
 * The Gourmet Cookbook 
 * @author Marco Zamora - bld783
 */
package application.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {
	/**
	 * loggedIn - boolean value that tells the 
	 * application whether a user in logged in.
	 */
	public static boolean loggedIn;
	/**
	 * viewSaved - boolean value that tells the application
	 * whether the application )
	 */
	public static boolean viewSaved;

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
	public static ArrayList<String> userRecipes = new ArrayList<String>();

	/**
	 * temp - string that holds the recipe information for a user that is not logged 
	 * ing. 
	 */
	public static String temp = ""; 

	public static List<String> userIntolerances = new ArrayList<String>();

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
				if(token[0].equals(username)) {
					if(token[1].equals(password)) {
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

	/**
	 * creatNewAccount - this method creates and saves a new user account.
	 * It also makes file to store recipes that a user wants to store. 
	 * @param username - String that contains the user name that a user chooses
	 * @param password - String that contains the users preferred password
	 * @param confirmPassword - String that contains a copy of the user password
	 * @return Int - returns int to be used as flag of the result of this method
	 */
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

		//Write the new user in the login.csv
		try {
			String output = username + "," + password;
			BufferedWriter writer = new BufferedWriter(new FileWriter("./users/login.csv",true));
			writer.write(output+"\n");
			writer.close();
			//Set the current user
			User.setUsername(username);
			User.setPassword(password);
			User.loggedIn = true; 

			//Create the file to save users recipes
			File file = new File("./userSavedRecipes/"+username+".csv");
			try {
				if (file.createNewFile())
				{
					System.out.println("File is created!");
				} else {
					System.out.println("File already exists.");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 1;
		} catch(IOException f) {
			f.printStackTrace();
		}


		return 0;
	}

	/**
	 * saveRecipes - this method saves the recipes that user chooses to 
	 * the users saved recipes file. 
	 */
	public static void saveRecipes() {
		try { 
			BufferedWriter writer = new BufferedWriter(new FileWriter("./userSavedRecipes/"+ User.getUsername() +".csv"));
			for(String r : userRecipes) {
				writer.write(r+"\n");
			}
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * loadUserSavedRecipes - this method will load the saved recipes that exist on the user
	 * saved recipe files to the array list of saved recipes
	 */
	public static void loadUserSavedRecipes() {
		try {
			Scanner scan = new Scanner(new File("./userSavedRecipes/" + User.getUsername()+".csv"));
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				User.getUserRecipes().add(line);
			}
			scan.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean checkIfRecipeExist(String recipe) {
		if(User.getUserRecipes().contains(recipe)) {
			return true;
		}
		return false;
	}

	/**
	 * logout - this method will clear all user data in the User 
	 * model, so another user can login. 
	 */
	public static void logout() {
		User.setUsername("");
		User.setPassword("");
		User.getUserRecipes().clear();
		User.loggedIn = false; 
		userIntolerances.clear();
	}
	
	/**
	 * This method is used to load intolerances for logged-in user.
	 * 
	 * @throws IOException
	 */
	public static void loadUserIntolerances() throws IOException {
		File file = new File("data/userIntolerances/" + username + ".csv");
		if (!file.exists()) {
			return;
		}
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			userIntolerances.add(line);
		}
		scanner.close();
	}
	
	/**
	 * This method is used to save intolerances for logged-in user.
	 * 
	 * @throws IOException
	 */
	public static void saveUserIntolerances() throws IOException {
		FileWriter fileWriter = new FileWriter("data/userIntolerances/" + username + ".csv");
		int i = 0;
		for (String intolerance : userIntolerances) {
			if (i == 0) {
				fileWriter.write(intolerance);
			} else {
				fileWriter.write(System.lineSeparator() + intolerance);
			}
			i++;
		}
		fileWriter.close();
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

	/**
	 * getTemp - gets the temp string 
	 * @return temp - string
	 */
	public static String getTemp() {
		return temp;
	}

	/**
	 * setTemp - sets the temp string
	 * @param temp - string of a recipe obj
	 */
	public static void setTemp(String temp) {
		User.temp = temp;
	}

	/**
	 * @return the userIntolerances
	 */
	public static List<String> getUserIntolerances() {
		return userIntolerances;
	}

	/**
	 * @param userIntolerances the userIntolerances to set
	 */
	public static void setUserIntolerances(List<String> userIntolerances) {
		User.userIntolerances = userIntolerances;
	}

}
