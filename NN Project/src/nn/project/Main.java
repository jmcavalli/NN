/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nn.project;

import java.io.*;

/**
 *
 * @author bible_000
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args ) {
        // TODO code application logic here
	if(args[1] == "-train") {
		String directory = System.getProperty("user.dir") + "\\Female";
		System.out.println("hi");
	}
	else if(args[1] == "-test") {
	}
	else {
		System.out.println("You put in no arguments noob");
		return;
	}
        String filename = System.getProperty("user.dir") + "\\Female\\5_1_1.txt";
        String line;
        
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(filename);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                filename + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + filename + "'");                  
        }
    }
        
    
}
