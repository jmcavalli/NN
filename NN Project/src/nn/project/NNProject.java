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
    double learnRate = 0.05;
    
    public void setUp(int num){
        hidden.setUp(num);
        output.setUp(num);
    }
    public double encode(int[][] img){
        double[] hiddenOutput;
        hiddenOutput = hidden.encode(img);
        return output.encode(hiddenOutput);
        
    }
    
    public void correct(double answer, double result){
        double[] weights = output.correct(learnRate, answer, result);
        hidden.correct(learnRate, weights, output.delta);
    }
    
}

class hiddenLayer{
    HiddenNode[] hiddenNodes;
    
    public void setUp(int num){
        hiddenNodes = new HiddenNode[num];
        for(int i = 0; i < hiddenNodes.length; i++)
            hiddenNodes[i] = new HiddenNode();
        
        for(int i = 0; i < num; i++){
//        hiddenNodes[i + 0].setUp(29, 14, 98, 119);
//        hiddenNodes[i + 0].setUp(47, 39, 61, 56);
//        hiddenNodes[i + 1].setUp(67, 39, 80, 56);
//        hiddenNodes[i + 0].setUp(58, 45, 71, 63);
//        hiddenNodes[i + 0].setUp(37, 14, 87, 84);
//        hiddenNodes[i + 1].setUp(45, 61, 84, 71);
//        hiddenNodes[i + 1].setUp(47, 69, 84, 83);
//        hiddenNodes[i + 0].setUp(54, 79, 73, 104);
        hiddenNodes[i + 0].setUp(0, 0, 128, 120);
//        hiddenNodes[i + 2].setUp(0, 0, 128, 120);
//        hiddenNodes[i + 3].setUp(0, 0, 128, 120);
//        hiddenNodes[i + 4].setUp(0, 0, 128, 120);
//        hiddenNodes[i + 5].setUp(0, 0, 128, 120);
//        hiddenNodes[i + 6].setUp(0, 0, 128, 120);
//        hiddenNodes[i + 7].setUp(0, 0, 128, 120);
        }
        //hiddenNodes[8].setUp(86, 80, 127, 119);
    }
    public double[] encode(int img[][]){
        double[] answer = new double[hiddenNodes.length];
        for(int i = 0; i < answer.length; i++){
            answer[i] = hiddenNodes[i].encode(img);
        }
        return answer;
    }
    
    public void correct(double eta, double weights[], double delta){
        for(int i = 0; i < hiddenNodes.length; i++){
            hiddenNodes[i].delta = gPrime(hiddenNodes[i].in) * weights[i] * delta;
            hiddenNodes[i].correct(eta);
        }
    }
    public static double g(double x){
        return 1/(1 + Math.pow(Math.E, -x));
    }
    
    public static double gPrime(double x){
        return g(x) * (1 - g(x));
    }
}

class OutputNode{
    double[] weights;
    double[] memory;
    double in;
    double a;
    double delta;
    
    public void setUp(int hiddenNum){
        Random r = new Random();
        weights = new double[hiddenNum];
        memory = new double[hiddenNum];
        for(int i = 0; i < hiddenNum; i++)
            weights[i] = -0.01 + 0.02 * r.nextDouble();
    }
    public double encode(double[] inputs){
        double sum = 0;
        for(int i = 0; i < inputs.length; i++){
            sum += inputs[i] * weights[i];
            memory[i] = inputs[i];
        }
        in = sum;
        a = g(in);
        return a;
    }
    
    public double[] correct(double eta, double answer, double result){
        delta = gPrime(in) * (answer - result);
        double[] oldWeights = new double[weights.length];
        for(int i = 0; i < weights.length; i++){
            oldWeights[i] = weights[i];
            weights[i] += eta * memory[i] * delta;
        }
        return oldWeights;
    }
    
    public static double g(double x){
        return 1/(1 + Math.pow(Math.E, -x));
    }
    public static double gPrime(double x){
        return g(x) * (1 - g(x));
    }
}

class HiddenNode{
    int topleftx;
    int toplefty;
    int bottomrightx;
    int bottomrighty;
    double[][] weights;
    double[][] memory;
    double in;
    double a;
    double delta;
    
    public void setUp(int tlx, int tly, int brx, int bry){
        topleftx = tlx;
        toplefty = tly;
        bottomrightx = brx;
        bottomrighty = bry;
        weights = new double[brx - tlx][bry - tly];
        memory = new double[brx - tlx][bry - tly];
        Random r = new Random();
        
        //prime weights with random small numbers
        for(int i  = 0; i < brx - tlx; i++)
            for(int j = 0; j < bry - tly; j++){
                weights[i][j] = -0.01 + 0.02 * r.nextDouble();
            }
    }
    public double encode(int[][] img){
        int sum = 0;
        for(int x = topleftx; x < bottomrightx; x++){
            for(int y = toplefty; y < bottomrighty; y++){
                memory[x - topleftx][y - toplefty] = img[x][y];
                sum += weights[x - topleftx][y - toplefty] * img[x][y];
            }
        }
        in = sum;
        a = g(in);
        return a;
    }
    
    public void correct(double eta){
        for(int x = 0; x < bottomrightx - topleftx; x++){
            for(int y = 0; y < bottomrighty - toplefty; y++)
                weights[x][y] += eta * memory[x][y] * delta;
        }
    }
    
    public static double g(double x){
        return 1/(1 + Math.pow(Math.E, -x));
    }
    public static double gPrime(double x){
        return g(x) * (1 - g(x));
    }
}

public class NNProject {

    /**
     * @param args the command line arguments
     */
    
    static Network brain;
    
    public static void main(String[] args ) {
        // TODO code application logic here
	if(args.length == 0) {
		System.out.println("You put in no arguments noob");
		return;
	}
        brain = new Network();
        int num = 80;
        
	if(args[0].equals("-train")) {
            brain.setUp(num);
            readDirectory("Female", false, 0, true);
            readDirectory("Male", false, 1, true);
	}
	else if(args[0].equals("-test")) {
            brain.setUp(num);
            //readTurns(false, 1, false);
            for(int i = 0; i < 30; i++){
                readTurns(false, "Man1", "Woman1", 1, false);
            //    readDirectory("Female", false, 0, false);
            //    readDirectory("Male", false, 1, false);
            }
            readDirectory("Test1", true, 0, true);
            visualize();
	}
	else {
		System.out.println("You put in the wrong arguments noob");
	}
    }
    
    public static void readDirectory(String dirname, boolean test, double answer, boolean write){
		File path = new File(System.getProperty("user.dir")+ "\\" + dirname );
		File[] img = path.listFiles();
		for(int i = 0; 	i < img.length; i++) { //for all pictures in file
			if(img[i].getName().equals("b") || img[i].getName().equals("a")) //ignore b and a
				continue;
                        String filename = path +  "\\" + img[i].getName();
			int[][] picture = readPicture(filename);
                        if(test){
                            System.out.print(img[i].getName() + " ");
                            testNN(picture);
                        }else{
                            trainNN(picture, answer, write);
                        }
		}
    }
    
    public static void readRandom(int times, boolean test, double answer, boolean write){
                Random r = new Random();
                Random r1 = new Random();
		File pathM = new File(System.getProperty("user.dir")+ "\\" + "Male" );
		File[] imgM = pathM.listFiles();
                File pathF = new File(System.getProperty("user.dir")+ "\\" + "Female" );
		File[] imgF = pathF.listFiles();
		for(int i = 0, j = 0, k = times; k >= 0; k--) { //for all pictures in file
                    i = (int)(Math.floor(imgM.length * r1.nextDouble()));
                    j = (int)(Math.floor(imgF.length * r1.nextDouble()));
			if(imgF[j].getName().equals("b") || imgM[i].getName().equals("a")) //ignore b and a
				continue;
                        String filenameM = pathM +  "\\" + imgM[i].getName();
                        String filenameF = pathF +  "\\" + imgF[j].getName();
                        int[][] picture;
                        if(r.nextDouble() < 0.5){
                            picture = readPicture(filenameF);
                            if(test){
                                testNN(picture);
                            }else{
                                trainNN(picture, 0, write);
                            }
                            //j++;
                        }else{
                            picture = readPicture(filenameM);
                            if(test){
                                testNN(picture);
                            }else{
                                trainNN(picture, 1, write);
                            }
                            
                            //i++;
                        }
                        
                        
		}
    }
    
    public static void readTurns(boolean test, String male, String female, double answer, boolean write){
		File pathM = new File(System.getProperty("user.dir")+ "\\" + male );
		File[] imgM = pathM.listFiles();
                File pathF = new File(System.getProperty("user.dir")+ "\\" + female );
		File[] imgF = pathF.listFiles();
		for(int i = 0; 	i < imgM.length && i < imgF.length; i++) { //for all pictures in file
			if(imgF[i].getName().equals("b") || imgM[i].getName().equals("a")) //ignore b and a
				continue;
                        String filenameM = pathM +  "\\" + imgM[i].getName();
                        String filenameF = pathF +  "\\" + imgF[i].getName();
                        int[][] picture;
                        
                            picture = readPicture(filenameF);
                            if(test){
                                testNN(picture);
                            }else{
                                trainNN(picture, 0, write);
                            }
                        
                            picture = readPicture(filenameM);
                            if(test){
                                testNN(picture);
                            }else{
                                trainNN(picture, 1, write);
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
    
    
    
    public static void trainNN(int[][] img, double answer, boolean write){
        double result = brain.encode(img);
        brain.correct(answer, result);
        if(write){
            if(result < 0.5){
                System.out.println("Woman " + (1 - result));
            }else{
                System.out.println("Man " + result);
            }
        }
    }
    
    public static void testNN(int[][] img){
        double result = brain.encode(img);
        if(result < 0.5){
            System.out.println("Woman " + (1 - result));
        }else{
            System.out.println("Man " + result);
        }
    }
    
    public static void visualize(){
        try{
            PrintWriter newfile = new PrintWriter(System.getProperty("user.dir") + "\\" + "visualization.txt");
            HiddenNode temp = brain.hidden.hiddenNodes[0];
            for(int y = 0; y < temp.bottomrighty - temp.toplefty; y++){
                for(int x = 0; x < temp.bottomrightx - temp.topleftx; x++){
                    newfile.print(Math.round(100 * temp.weights[x][y]) + " ");
                }
                newfile.println();
            }
            
        }catch(IOException e){
        }
    }
        
    
}
