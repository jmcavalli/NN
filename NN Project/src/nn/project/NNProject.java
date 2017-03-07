/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package nn.project;

import java.io.*;

/**
 *
 * @author bible_000
 */
public class NNProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args ) {
        // TODO code application logic here
	if(args.length == 0) {
		System.out.println("You put in no arguments noob");
		return;
	}
	if(args[0].equals("-train")) {
		int[][] picture = new int[128][120];
		File female = new File(System.getProperty("user.dir")+ "\\Female\\Female");
		File male = new File(System.getProperty("user.dir") + "\\Male\\Male");
		File[] femimg = female.listFiles();
		for(int i = 0; 	i < femimg.length; i++) {
			if(femimg[i].getName().equals("b") || femimg[i].getName().equals("a")) 
				continue;
			try {
				String line;
				FileReader fileReader = new FileReader(female + "\\" + femimg[i].getName());
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				while((line = bufferedReader.readLine()) != null) {
					System.out.println(line);
				}

				if(bufferedReader != null)
					bufferedReader.close();
				if(fileReader != null)
					fileReader.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			
		}

	}
	else if(args[0] == "-test") {
	}
	else {
		System.out.println("You put in the wrong arguments noob");
		return;
	}
    }
        
    
}
