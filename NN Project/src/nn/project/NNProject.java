/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nn.project;

import java.io.*;
import java.util.*;

/**
 *
 * @author bible_000
 */

class Network{
    hiddenLayer hidden = new hiddenLayer();
    OutputNode output = new OutputNode();
    
    public void setUp(){
        hidden.setUp();
        output.setUp(9);
    }
}

class hiddenLayer{
    HiddenNode[] hiddenNodes;
    
    public void setUp(){
        hiddenNodes = new HiddenNode[9];
        for(int i = 0; i < 9; i++)
            hiddenNodes[i] = new HiddenNode();
        
        hiddenNodes[0].setUp(0, 0, 43, 40);
        hiddenNodes[1].setUp(43, 0, 86, 40);
        hiddenNodes[2].setUp(86, 0, 127, 40);
        hiddenNodes[3].setUp(0, 40, 43, 80);
        hiddenNodes[4].setUp(43, 40, 86, 80);
        hiddenNodes[5].setUp(86, 40, 127, 80);
        hiddenNodes[6].setUp(0, 80, 43, 119);
        hiddenNodes[7].setUp(43, 80, 86, 119);
        hiddenNodes[8].setUp(86, 80, 127, 119);
    }
}

class OutputNode{
    double[] weights;
    
    public void setUp(int hiddenNum){
        Random r = new Random();
        weights = new double[hiddenNum];
        for(int i = 0; i < hiddenNum; i++)
            weights[i] = r.nextDouble();
    }
}

class HiddenNode{
    int topleftx;
    int toplefty;
    int bottomrightx;
    int bottomrighty;
    double[][] weights;
    
    public void setUp(int tlx, int tly, int brx, int bry){
        topleftx = tlx;
        toplefty = tly;
        bottomrightx = brx;
        bottomrighty = bry;
        weights = new double[brx - tlx][bry - tly];
        Random r = new Random();
        
        //prime weights with random small numbers
        for(int i  = 0; i < brx - tlx; i++)
            for(int j = 0; j < bry - tly; j++){
                weights[i][j] = -1 + 2 * r.nextDouble();
            }
    }
}

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
        Network brain = new Network();
        
	if(args[0].equals("-train")) {
            brain.setUp();
            readDirectory("Female", false, -1);
            readDirectory("Male", false, 1);
	}
	else if(args[0].equals("-test")) {
	}
	else {
		System.out.println("You put in the wrong arguments noob");
	}
    }
    
    public static void readDirectory(String dirname, boolean test, int answer){
		File path = new File(System.getProperty("user.dir")+ "\\" + dirname + "\\" + dirname );
		File[] img = path.listFiles();
		for(int i = 0; 	i < img.length; i++) { //for all pictures in file
			if(img[i].getName().equals("b") || img[i].getName().equals("a")) //ignore b and a
				continue;
                        String filename = path +  "\\" + img[i].getName();
			int[][] picture = readPicture(filename);
                        if(test){
                            testNN(picture);
                        }else{
                            trainNN(picture, answer);
                        }
		}
    }
    
    
    public static int[][] readPicture(String filename){
        int[][] picture = new int[128][120];
        try {
				String line;
                                int x = 0, y = 0;
				FileReader fileReader = new FileReader(filename);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				while((line = bufferedReader.readLine()) != null) {
                                    String[] tokens = line.split(" ");
                                    for(int j = 0; j < tokens.length; j++){
                                        picture[x][y] = Integer.parseInt(tokens[j]);
                                        x++;
                                    }
                                    if(x >= 127){
                                        y++;
                                        x = 0;
                                    }
				}

				if(bufferedReader != null)
					bufferedReader.close();
				if(fileReader != null)
					fileReader.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
        return picture;
    }
    
    public static double g(double x){
        return 1/(1 + Math.pow(Math.E, -x));
    }
    
    public static double gPrime(double x){
        return g(x) * (1 - g(x));
    }
    
    public static void trainNN(int[][] img, int answer){
        
    }
    
    public static void testNN(int[][] img){
        
    }
        
    
}
