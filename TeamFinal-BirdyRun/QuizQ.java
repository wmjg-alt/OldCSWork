import java.io.Serializable;
import java.util.*;
//package birdyRun;

import javax.swing.JTextArea;

public class QuizQ implements Serializable{
	
	String question; // quizzes have questions, answers and wrong choices
	//public char correctLetter;
	private int correctNum;
	Answers answer;
	Answers[] options;
	boolean answeredCorrectly;
	
	public boolean migratoryQ;
	
	// Need arrays of questions and answers for migratory and non migratory
	
	String[] questionsM = new String[] { "Ospery like to eat: ", "Osprey live in what kind of habitat?","Osprey usually travel by: ","Osprey like to build nests out of: ","Osprey are what kind of bird? "}; 
	
	Answers[] am0 = new Answers[] {new Answers(false, "Seeds", 0), new Answers(true, "Fish",1), new Answers(false, "Crabs",2), new Answers(false, "Meat",3)};
	Answers[] am1 = new Answers[] {new Answers(true, "Near shallow bodies of water", 0), new Answers(false, "Near cities",1), new Answers(false, "On mountains",2), new Answers(false, "In deserts",3)};
	Answers[] am2 = new Answers[] {new Answers(false, "Walking", 0), new Answers(false, "Swimming",1), new Answers(true, "Flying",2), new Answers(false, "Jumping",3)};
	Answers[] am3 = new Answers[] {new Answers(true, "Sticks", 0), new Answers(false, "Steel",1), new Answers(false, "Foam",2), new Answers(false, "Soil",3)};
	Answers[] am4 = new Answers[] {new Answers(false, "Nonmigratory", 0), new Answers(true, "Migratory",1), new Answers(false, "Both",2), new Answers(false, "Neither",3)};
	
	String[] questionsNM = new String[] {"Clapper rails like to eat: ","Clapper rails live in what kind of habitat?","Clapper rails usually travel by: ","Clapper rails like to build their nest out of: ","Clapper rails are what kind of bird?"};
	
	Answers[] anm0 = new Answers[] {new Answers(false, "Candy", 0), new Answers(true, "Fish",1), new Answers(false, "Insects",2), new Answers(false, "Fruit",3)};
	Answers[] anm1 = new Answers[] {new Answers(true, "Near shallow bodies of water", 0), new Answers(false, "Near cities",1), new Answers(false, "On mountains",2), new Answers(false, "In deserts",3)};
	Answers[] anm2 = new Answers[] {new Answers(false, "Flying", 0), new Answers(false, "Swimming",1), new Answers(true, "On the ground",2), new Answers(false, "Jumping",3)};
	Answers[] anm3 = new Answers[] {new Answers(true, "Vegetation", 0), new Answers(false, "Steel",1), new Answers(false, "Soil",2), new Answers(false, "Foam",3)};
	Answers[] anm4 = new Answers[] {new Answers(false, "Migratory", 0), new Answers(true, "Nonmigratory",1), new Answers(false, "Both",2), new Answers(false, "Neither",3)};
	
	// Make a 2d array of all answers; each row is for the corresponding question, and each column is answer choices 1-4
	Answers[][] allAnsM = new Answers[][] {am0,am1,am2,am3,am4};
	Answers[][] allAnsNM = new Answers[][] {anm0,anm1,anm2,anm3,anm4};

	
	// this method will choose a random question number and return it           
	public int chooseQ() {
		
		// choosing a random question to display
		Random rand = new Random();
		int nextNum = rand.nextInt(5); // choose a random number from 0-4 to be the question number to be asked
		correctNum = nextNum;
		return nextNum; // returns index of question chosen
	}
	
	// getting the correct answer for the subarray for the particular question
	// Pass in answers array for the particular question, and returns the string of the answer
	public Answers getCorrectAns(Answers[] arr) {
		Answers b = arr[0];
		for (int i = 0; i < 4; i++) { // iterate through answers in the array of options 
			if (arr[i].isCorrect()) {
			b = arr[i];
			break;
		}
			//if (a.isCorrect()) { // if it is the correct answer, set return answer to a
			//	b = a;
			}
		
		System.out.println("After for, correct is " + b.getAns());
		return b; // returns correct answer 
	}
		
	public String toString() {
		return this.question;
	}
	
	public void printChoices() {
		for (int i = 0; i < 4; i++) {
			if (migratoryQ) {
			System.out.println(allAnsM[correctNum][i].getAns());
			} else {
				System.out.println(allAnsNM[correctNum][i].getAns());
			}
		}
	}
	
	public Answers[] getOptions() {
		return this.options;
	}
	public String getQuestion() {
		return (this.question);
	}
	public void setSubmitted(boolean ans) {
		answeredCorrectly = ans;
	}
	public boolean getSubmitted() {
		return this.answeredCorrectly;
	}
	
	public QuizQ(boolean migratory) {
		System.out.println("Entering quiz constructor");
		
		migratoryQ = migratory;
		int questI = chooseQ(); // gets random question and sets questI = index of question
		
		if (migratory) {
			question = questionsM[questI]; // sets question string as the question to be displayed
			options = allAnsM[questI]; // array of answer choices for that question
			answer = getCorrectAns(options);  // sets the answer field as the correct answer
		}
		
		else {
			// same but for non migratory arrays
			question = questionsNM[questI];
			options = allAnsNM[questI];
			answer = getCorrectAns(options);
		}
		System.out.println("End of constructor");
	}
	
	
}
