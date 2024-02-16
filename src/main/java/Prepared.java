import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import bean.NekoBean;

public class Prepared {
	//クイズ番号ランダムに取得するメソッド
	public ArrayList<Integer> getRandomNum(int columnCnt,int total) {//サーブレットに記載したtotal
		Random random = new Random();

		ArrayList<Integer> list=new ArrayList<Integer>();
		int quizNum=0;

		int i=0;
		boolean same;//クイズ番号が重複していないか？確認するためにブール型のsame変数を宣言
		quizNum = random.nextInt(columnCnt)+1;//クイズテーブルのレコード数を乱数で取得して、quizNum変数に代入。
		list.add(quizNum);
		i++;
		while(i<total) {
			same=false;//same変数の中身をfalseにリセットする。新しく生成されたクイズ番号が重複していないか？確認
			quizNum = random.nextInt(columnCnt)+1;
			for(int j=0;j<list.size();j++) {
				if(list.get(j)==quizNum) {
					same=true;		//ダブったらtrueを入れる
				}
			}
			if(same!=true) {//クイズ番号が重複していなかったら、そのままlistに加える。
				list.add(quizNum);
				i++;
			}
		}
		return list;
	}
	//このメソッドを呼び出すことで、指定された範囲内でランダムな整数値が重複しないように取得され、
	//リストとして返されます。このリストは、例えばクイズの問題番号など、
	//重複しない一意の値を取得する際に使用できます。

	//選択肢をシャッフルするメソッド
	public void Shuffle(NekoBean nb) {
		//照合用の答え
		String mattchAns=nb.getAnswer();

		ArrayList<String> shuf=new ArrayList<String>(); 
		shuf.add(nb.getChoice1());
		shuf.add(nb.getChoice2());
		shuf.add(nb.getChoice3());
		shuf.add(nb.getAnswer());

		Collections.shuffle(shuf);

		//シャッフルしたものを入れなおす
		nb.setChoice1(shuf.get(0));
		nb.setChoice2(shuf.get(1));
		nb.setChoice3(shuf.get(2));
		nb.setAnswer(shuf.get(3));

		//答えの場所を探す
		int ansNum=0;
		for(int i=0;i<shuf.size();i++) {
			if(shuf.get(i).equals(mattchAns)) ansNum=i+1;
		}
		nb.setAnsNum(ansNum);
	}
}