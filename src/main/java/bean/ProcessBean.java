package bean;
import java.io.Serializable;
import java.util.ArrayList;
//中身が固定のBeanクラス
public class ProcessBean implements Serializable{
	public int total;//問題数
	public int score;//点数
	public int nth;//現在の問題番号
	public ArrayList<Integer> quizNum=new ArrayList<Integer>();//一番最初にランダムで取得した問題番号
	
	public ProcessBean() {
	}
	
	public void setTotal(int total) {
		this.total=total;
	}
	public void setScore(int score) {
		this.score=score;
	}
	public void setNth(int nth) {
		this.nth=nth;
	}
	public void setQuizNum(ArrayList<Integer> quizNum) {
		this.quizNum=quizNum;
	}
	
	
	public int getTotal() {
		return total;
	}
	
	public int getScore(){
		return score;
	}
	
	public int getNth(){
		return nth;
	}
	
	public ArrayList<Integer> getQuizNum(){
		return quizNum;
	}

}
//初回時のスコア、現在の問題番号、ランダムで取得した問題番号を
//プロセスビーンで設定している。
//二回目以降は、サーブレットに変数の中身が変わるように処理しているので
//サーブレットの処理を見に行く。
//一回目は、FirstReqとPreparedをみる。
//二回目以降は、score,nth,quizNumの変数の値が変わる処理をサーブレットでしている。

