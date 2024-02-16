import java.util.ArrayList;

//初回アクセス時のみ使うクラス
import bean.ProcessBean;
public class FirstReq {
	NekoDAO ndao;//各クラスのインスタンスを扱うための変数を用意する
	Prepared pre;
	ProcessBean pb;
//FirstReqインスタンスが生成されたときに、NekoDAOオブジェクトとPreparedオブジェクトを
//初期化し、また渡されたProcessBeanオブジェクト(pb)の一部の属性を初期化する(コンストラクタ）
	public FirstReq(ProcessBean pb) {
		this.pb=pb;
		ndao=new NekoDAO();
		pre = new Prepared();
		pb.setScore(0);//スコアの初期値０に設定
		pb.setNth(1);//現在の問題番号の数字を１から始める設定		
	}
	//出題するクイズの番号をtotal個(複数のクイズ番号）取得→ArrayListを使用してる。
	public ArrayList<Integer> getAllQuiz(int total) { //getAllQuizで、メソッドを呼び出せるようにしている。（サーブレット)このメソッドは、total(問題の出題数）をもつArrayListを返す。
	int columnCnt = ndao.ColumnCount();//columnCntは、daoにあるメソッドから持ってきてるカラム数のこと。//クイズテーブルのレコード数をcolumn変数に代入。
	//出題数よりデータ数が少なかった場合、出題数をデータ数と同じにする（エラー防止）
	if(total>columnCnt) { 
	total=columnCnt;
	}
	pb.setTotal(total);
	//ArrayList型の変数quizNumにPreparedで作成したQuizNumのメソッドを代入して、
	//後のコードでArrayList型のquizNum変数を扱えるようにしている。
	ArrayList<Integer> quizNum = pre.getRandomNum(columnCnt,total);
	return quizNum;
	}

}

