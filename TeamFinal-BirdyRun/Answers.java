import java.io.Serializable;

public class Answers implements Serializable{

	boolean correct;
	String ans;
	int num;
	//answers for the game, whether they are right and question number
	public Answers(boolean correct, String ans, int num) {
		this.correct = correct;
		this.ans = ans;
		this.num = num;
	}
	
	public boolean isCorrect() {
		return this.correct;
	}
	
	public String getAns() {
		return this.ans;
	}
	
	public String toString() {
		return (this.ans);
	}
	
	public int getNum() {
		return (this.num);
	}
	
}
