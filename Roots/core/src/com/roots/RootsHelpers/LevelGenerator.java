package com.roots.RootsHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelGenerator {
	private Random intGen;
    private List<Integer> startList;
    private int targetNumber,recentSquaredIndex = -1,recentRootIndex = -1,rank,depth;
    
    public LevelGenerator()
    {
        this.intGen = new Random();
        startList = new ArrayList<Integer>();
    }
    public int getStartNumAt(int index){
    	return startList.get(index);
    }
    public void setRank(int rank){
    	this.rank = rank;
    }
    public void setDepth(int depth){
    	this.depth = depth;
    }
    public int getTargetValue(){
    	return startList.get(startList.size()-1);
    }
    
    
    //might want to change this while loop to something more specific?
    //also might want to change obfuscation depth
    public void getLevel()
    {
        startList.clear();
        List<Integer> toModify = getInts();
        System.out.println(toModify);
        for (int i = 0; i < depth; i++) {
            doOperation(toModify);
            
        }
        chooseTarget(toModify);
        int i = 0;
        while(startList.contains(targetNumber)&& i<20)
        {
        	doOperation(toModify);
            chooseTarget(toModify);
            //System.out.println("called level loop");
            //System.out.println(toModify);
            i ++;
        }
        startList.add(targetNumber);
        recentSquaredIndex = -1;
        recentRootIndex = -1;
    }
    
    
   
    
    //generates a starting list of some random perfect squares
    private List<Integer> getInts()
    {
        List<Integer> outInts = new ArrayList<Integer>();
        int bottom = 0;
        if(rank <=1){
        	bottom = 1; //prevent rank 1 from having 1s as a goal
        }
        for (int i = 0; i < rank; i++) {
            int num = bottom + intGen.nextInt(9)+1;
            int square = num*num;
            outInts.add(square);
            startList.add(square);
            
        }
        return outInts;
        
    }
    
    
    //adds, roots, or squares a number in the list
    private void doOperation(List<Integer> inList)
    {
    	int opKey = 0;
    	if(rank > 1){
    		 opKey = intGen.nextInt(3);
    		}
    	else{
    		 opKey = intGen.nextInt(2);
    		}
        switch (opKey)
        {
            case(0):
                squareOne(inList);
               // System.out.println("Squared");
                break;
            case(1):
                rootOne(inList);
               // System.out.println("rooted");
                break;
            case(2):
                combineTwo(inList);
               // System.out.println("summed");
                break;
            default:
                break;
        }
    }
    
    //chooses the sum of the numbers a quarts of the time, otherwise chooses a number in the column
    private void chooseTarget(List<Integer> inList)
    {
    	if(inList.size() <= 1){
    		targetNumber = inList.get(0);
    		return;
    	}
        int targKey = intGen.nextInt(10);
        switch(targKey)
        {
            case(0):
                targetNumber = sumOf(inList);
                //System.out.println("chose sum target");
                break;
            case(1):case(2):case(3):
                targetNumber = inList.get(intGen.nextInt(inList.size()));
                //System.out.println("Chose random Target");
                break;
            default:
            	targetNumber = inList.get(intGen.nextInt(inList.size()));
                break;
        }
    }
    
    
    
    private int sumOf(List<Integer> inList)
    {
        int output = 0;
        for (int i = 0; i < inList.size(); i++) {
            output += inList.get(i);
            
        }
        return output;
    }
    
    
    
    //roots a random perfect square in the list, does nothing if none
    private void rootOne(List<Integer> inList)
    {
        //get random list index
       int index = intGen.nextInt(inList.size());
       if(rank > 1 && inList.size()> 1){
    	   int trys = 0;
    	   while (index == recentRootIndex&&trys < 10){
    		   index = intGen.nextInt(inList.size());
    		  // System.out.println(index);
    		 //  System.out.println(inList);
    		   trys ++;
    	   }
       }
       int num = inList.get(index); 
       if(isPerfectSquare(num))
       {
           inList.set(index, (int)Math.sqrt(num));
           recentRootIndex = index;
       }
       
    }
    
    
    private boolean isPerfectSquare(Integer n)
    {
        if( n < 0)
        {
            return false;
        }
        switch(n%10)
        {
            //all possibilties for perfect square last digits
            case(0): case(1): case(4): case(5): case(6): case(9):
                int test = (int)Math.sqrt(n);
                return test*test == n; //check to see if the test squared is the original number
            default:
                return false;
        }
    }
    
    
    
    
    
    
    //pretty self explanatory
    private void squareOne(List<Integer> inList)
    {
        int index = intGen.nextInt(inList.size());
        if(rank > 1 && inList.size()> 1){
        int trys = 0;
     	   while (index == recentSquaredIndex&& trys < 10){      //I will eat my shoes if do something this hacky again
     		   index = intGen.nextInt(inList.size());
     		  // System.out.println(inList);
     		  //System.out.println(index);
     		  trys ++;
     	   }
        }
        int num = inList.get(index);
        if(num > 256)//cheap code to not to get giant numbers
        {
            return;
        }
        int squaredNum = num*num;
        inList.set(index, squaredNum);
        recentSquaredIndex = index;
    }
    
    
    
    private void combineTwo(List<Integer> inList)
    {
        if(inList.size()<2)
        {
            return;
        }
        //pick two random numbers in the list, that aren't at the same spot
        int firstIndex = intGen.nextInt(inList.size());
        int secondIndex = getRandomExcluding(firstIndex, inList.size());
        int first = inList.get(firstIndex);
        int second = inList.get(secondIndex);
        //add them together
        int newNum = first + second;
        //sloppily reform the list to contain their sum
        inList.remove(firstIndex);
        inList.remove(inList.indexOf(second));
        inList.add(newNum);
        
        
    }
    
    //just a quick method to make sure we don't get duplicates
    protected Integer getRandomExcluding(int n, int range)
    {
        int number = intGen.nextInt(range);
        if(number != n)
        {
            return number;
        }
        return getRandomExcluding(n,range);
    }
}

