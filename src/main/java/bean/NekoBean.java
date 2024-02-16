package bean;

import java.io.Serializable;
//中身が1週ごとに変わるBeanクラス
public class NekoBean implements Serializable{
	public int quiz_no;
	public String question;
	public String choice1;
	public String choice2;
	public String choice3;
	public String answer;
	public String explanation;
	//クイズごとの正解数と回答数を取得するために追加したカラム
	public int allchallenger;//回答数
	public int correctman;//正解数
	
	public int ansNum;//問題の回答番号
	public String trueAnswer;//問題のシャッフル前に正解の選択肢を格納する。
	
	//daoでこのnbの内容をまとめてresult.jspに表示する。
	public int yourChoice;//自分の選んだ選択肢の番号（１～４）
	boolean    correct;//正解したかどうかの判別。正解の場合trueにする。
	
	public NekoBean() {
		correct=false;//初期値設定。
	}
	
//セッターメソッド（フィールドの値を変更する）
	public void setQuiz_no(int quiz_no){
		this.quiz_no = quiz_no;
	}  

	public void setQuestion(String question){
		this.question = question;
	}  

	public void setChoice1(String choice1){
		this.choice1 = choice1;
	}  

	public void setChoice2(String choice2){
		this.choice2 = choice2;
	}  

	public void setChoice3(String choice3){
		this.choice3 = choice3;
	}  

	public void setAnswer(String answer){
		this.answer = answer;
	}  

	public void setExplanation(String explanation){
		this.explanation = explanation;
	}  
	public void setAnsNum(int ansNum) {
		this.ansNum=ansNum;
	}
	public void setAllchallenger(int allchallenger) {
		this.allchallenger=allchallenger;
	}
	public void setCorrectman(int correctman) {
		this.correctman=correctman;
	}
	public void setTrueAnswer(String trueAnswer) {
		this.trueAnswer=trueAnswer;
	}
	public void setYourChoice(int yourChoice) {
		this.yourChoice=yourChoice;
	}
	public void setCorrect(boolean correct) {
		this.correct=correct;
	}
	
//ゲッターメソッド（フィールドの値を返す）
	public int getQuiz_no(){
		return quiz_no;
	}

	public String getQuestion(){
		return question;
	}

	public String getChoice1(){
		return choice1;
	}

	public String getChoice2(){
		return choice2;
	}

	public String getChoice3(){
		return choice3;
	}

	public String getAnswer(){
		return answer;
	}

	public String getExplanation(){
		return explanation;
	}
	public int getAnsNum() {
		return ansNum;
	}
	public int getAllchallenger() {
		return allchallenger;
	}
	public int getCorrectman() {
		return correctman;
	}
	public String getTrueAnswer() {
		return trueAnswer;
	}
	public int getYourChoice() {
		return yourChoice;
	}
	public boolean getCorrect() {
		return correct;
	}
}
//Beanのお仕事は、データの取り扱いを他のクラスで便利にするために使われている
//Setで各フィールドの値を設定する
//Getで各フィールドの値を取得する

//つまり、setメソッドはオブジェクトの状態を変更するために使用され、
//getメソッドはオブジェクトの情報を取得するために使用されます。
//これにより、NekoBeanクラスのインスタンスは他の部分で簡単に操作でき、
//データを保持・取得するのに便利な方法を提供します。