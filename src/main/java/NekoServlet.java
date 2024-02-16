import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.NekoBean;
import bean.NekoDTO;
import bean.ProcessBean;


@WebServlet("/NekoServlet")
public class NekoServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//文字化け防止・エンコードの設定
		request.setCharacterEncoding("utf-8");

		//すでにあるセッションを返し、セッションがない場合はnullを返す。
		HttpSession session=request.getSession(false);
		//sessionはnullが入る。

		//プロセスビーンを後続のコードで扱えるようにpb宣言する(後のコードでpbに具体的なオブジェクトを代入することで扱える）
		ProcessBean pb;		
		NekoDAO ndao = new NekoDAO();
		Prepared pre = new Prepared();
		
	   	//htmlへのブラウザバック、ブラウザを消した時の対策
		//ブラウザにセッションが残った状態で、実行すると、配列の要素数エラーが起きていたので、ボタンが押された時点で、セッションを着るようにした。
	    if(session!=null) {
	    	if(request.getParameter("start")!=null) {
	    		session.invalidate();
	    		session=null;
	    	}
	    }
		//初回接続時に、セッションと各インスタンスを初期化する。
		if(session==null) {
			session=request.getSession(true);//新しいセッションを作成。
			Integer total=5;//answer.jspでtotalを使うよ。//出題数
			pb = new ProcessBean();
			FirstReq first=new FirstReq(pb);//FirstReqオブジェクトがProcessBeanオブジェクトを使用できる。
											//FirstReqはコンストラクタで、newされた時点で、FirstReqのメソッドが入ってる。処理
			//ランダム生成した数字をクイズ番号として格納（ダブりなし）
			pb.setQuizNum(first.getAllQuiz(total));//ランダムでとってきた問題数をプロセスビーンのQuizNumに入れて使えるようにしている。
			//追加したコード
			NekoDTO dto=new NekoDTO();//dtoをインスタンス化
			session.setAttribute("dto", dto);//同じセッション内のdtoを使うため、受け取るdtoを同じセッション内のdtoに格納。
			System.out.println("初回接続確認");
		}else {
			pb=(ProcessBean)session.getAttribute("pb");//セッションにあるプロセスビーンのデータを扱うから
			System.out.println("2回目以降の接続確認");
			//オブジェクト型から型を返れないからキャストの記述が必要でこの書き方になってる。
		}
		//answer.jspから受け取った際の処理内容を記述
		//リクエスト初回は、nthに値が入っていないので、この処理は通らない。本命は二回目以降の場合。
		if(request.getParameter("nth")==null) {//"nth"はpbで取得する現在の問題番号の値。
			System.out.println("wwwwwwwwwwwwwwww");
			//2回目以降、問題番号、スコアを格納
		}else if(Integer.parseInt(request.getParameter("nth"))<pb.getTotal()+1) {
			System.out.println(request.getParameter("nth"));
			//ユーザーが今、何番目の問題に回答しているのか？分かるように管理
			pb.setNth(Integer.parseInt(request.getParameter("nth"))+1);//カウントアップのための+1して現在の問題番号にこの時点で+1されてる。
			NekoBean nb=(NekoBean)session.getAttribute("nb");
			if(request.getParameter("score").equals("correct")) {//ユーザーが正しい回答をしたらこの分岐に入る。
				//スコアが更新される度にプロセスビーンに新たなスコアを設定する。
				int score=0;
				score=pb.getScore()+1;
				pb.setScore(score);
    			nb.setCorrect(true);//問題文など＋正誤判定をdtoに格納
				//問題を解いて、正解した場合にカウント＋１されるようにする。正解しなかったら別のSQLメソッドが実行される。
				System.out.println("正解  "+pb.getNth());
				
				ndao.correctUpdate(pb);//この時点で引数指定したpbのnthはこの時点でカウントアップ＋１されてるので、DAOのメソッドで扱うnthの中身の数字を統一する必要が出てる。
				System.out.println("正解カウントアップ");
			}else if(request.getParameter("score").equals("incorrect")){
				System.out.println("不正解\"  "+pb.getNth());
				ndao.incorrectUpdate(pb);//この時点で引数指定したpbのnthはこの時点でカウントアップ＋１されてるので、DAOのメソッドで扱うnthの中身の数字を統一する必要が出てる。
				System.out.println("不正解カウントアップ");
			}
			int yourChoice=Integer.parseInt(request.getParameter("yourChoice"));//answer.jspから受け取り
			System.out.println("yourChoiceの値を格納");
			nb.setYourChoice(yourChoice);
			NekoDTO dto=(NekoDTO)session.getAttribute("dto");//同じDTOに格納したいので、セッションを作る。
			dto.add(nb);//addメソッドでnbの要素を１つ１つ同じセッションのdtoに格納する。			
			System.out.println(dto.get(pb.getNth()-2));
		}
		//System.out.println("totalの数"+pb.getTotal());

		//total回終了時、リザルト画面へ
		if(request.getParameter("btn")!=null) {
			if(request.getParameter("btn").equals("結果")) {
				RequestDispatcher rd = request.getRequestDispatcher("/result.jsp");
				rd.forward(request, response);
				System.out.println("結果確認用表示");
			}
		}

		//quizNumに新しいクイズ番号がはいるのはここ。
		int quizNum=pb.getQuizNum().get(pb.getNth()-1);//ArrayListは０から開始なので-1
		System.out.println("rrrrrrrrrrrrrrrr");

		NekoBean nb = new NekoBean();
		
		//データからクイズを獲得
		nb=ndao.getQuiz(nb,quizNum);
		//シャッフル前に、getAnswerの中身をtrueAnswerにセットする。(answer.jspに、不正解の時→正解の選択肢を表示する）
		nb.setTrueAnswer(nb.getAnswer());
		//選択肢をシャッフル
		pre.Shuffle(nb);

		//リクエストスコープにpbとnbの内容を格納する。
		session.setAttribute("pb", pb);
		session.setAttribute("nb", nb);

		//jspにフォワードする
		RequestDispatcher rd = request.getRequestDispatcher("/question.jsp");
		rd.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
}





