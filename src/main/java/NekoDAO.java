import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import bean.NekoBean;
import bean.ProcessBean;

public class NekoDAO {
	private final String URL = "jdbc:postgresql://localhost/academy";
	private final String USER = "academy";
	private final String PASS = "kunren";
	private Connection con = null;

	public void connect() {
		try {
			con = DriverManager.getConnection(URL,USER,PASS);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public NekoBean getQuiz(NekoBean nb,int quizNum) {
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select * "
				+ "from quiz "
				+ "where quiz_no ="
				+ quizNum;
		try {
			connect();
			//ステートメントを生成
			stmt = con.createStatement();
			//SQLを実行
			rs = stmt.executeQuery(sql);
			//検索結果の処理
			//データベースから取得した情報をアプリケーション内で使用するために、データをオブジェクトにマッピングするための手順
			while(rs.next()) {// ResultSet が次の行にデータが存在する場合、このループが繰り返されます。行がなくなるまで、ループ内の処理が行われます。
				nb.setQuiz_no(rs.getInt("quiz_no"));
				nb.setQuestion(rs.getString("question"));
				nb.setChoice1(rs.getString("choice1"));
				nb.setChoice2(rs.getString("choice2"));
				nb.setChoice3(rs.getString("choice3"));
				nb.setAnswer(rs.getString("answer"));
				nb.setExplanation(rs.getString("explanation"));
			//クイズごとの正解数と回答数を取得するために追加したカラム
				nb.setAllchallenger(rs.getInt("allchallenger"));
				nb.setCorrectman(rs.getInt("correctman"));
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			try{
				//⑤リソースを解放
				if(rs != null) rs.close();		//closeメソッドは例外の有無に関わらず実行するためにfinallyに記述する。
				if(stmt != null) stmt.close();	
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		disconnect();
		return nb;
	}
	public int ColumnCount() {
		int count=0;

		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select count(*) cnt "//cntはカウント数の別名
				+ "from quiz";
		try{
			connect();
			//②ステートメントを生成
			stmt = con.createStatement();
			//③SQLを実行
			rs = stmt.executeQuery(sql);
			rs.next();//次の列があるまでカーソルを一つずつ移動する。
			count=rs.getInt(1);//カウント変数は、１列目からカウントする。
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			try{
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		disconnect();
		return count;
	}
	
	//クイズごとの回答数と正解数のためのupdateメソッド
	//不正解の場合のアップデート文
	public int incorrectUpdate(ProcessBean pb) {
		String sql = "UPDATE quiz SET allchallenger = COALESCE(allchallenger, 0) +1"
				+ " where quiz_no = "+ pb.getQuizNum().get(pb.getNth()-2); 
		return executeSql(sql); 
	}
	//正解の場合のアップデート文
	public int correctUpdate(ProcessBean pb) {
		String sql = "UPDATE quiz SET allchallenger = COALESCE(allchallenger, 0) +1,correctman = COALESCE(correctman, 0) +1"
				+ " where quiz_no = "+ pb.getQuizNum().get(pb.getNth()-2); 
		return executeSql(sql); 
	}	
		
		public int executeSql(String sql) {
			Statement stmt = null;
			int result = 0;
			try{
				connect();
				//②ステートメントを生成
				stmt = con.createStatement(); 
				//③SQLを実行
				result = stmt.executeUpdate(sql);
			} catch(Exception e){
				e.printStackTrace();
			} finally {
				try{
					if(stmt != null) stmt.close();
				} catch(Exception e){
					e.printStackTrace();
				}
			}
			disconnect();
			return result;
	}
	
	public void disconnect() {
		try {
			if(con != null)con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

	}
}
