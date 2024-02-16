<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="bean.*"%>
<%@ page import="math.NumberFormatter"%><!-- 正解率の％表示に必要 -->
<jsp:useBean id="nb" scope="session" class="bean.NekoBean" />
<jsp:useBean id="pb" scope="session" class="bean.ProcessBean" />

<%--選ばれた選択肢の問題の順番（choice）をansNum（正解の番号）と照合して正解or不正解を判定する --%>
 <%
 int ansNum = nb.getAnsNum();
 int choice=0;
 if(request.getParameter("choice")!=null)
   choice = Integer.parseInt(request.getParameter("choice"));
 boolean correct=false;
 if(ansNum==choice) {
	 correct=true;
 }
 %>
 
 <%-- Javaの式を使用して正解率を計算し表示 --%>
<% int correctCount = nb.getCorrectman(); // 正解数を取得 
   int totalQuestions = nb.getAllchallenger(); // 総回答数を取得 
   double accuracy = ((double)correctCount) / totalQuestions; //正解率＝（double型）正解数/回答数*100
   if(totalQuestions==0)accuracy=0;//NaN対策
   
   NumberFormatter formatter = new NumberFormatter();// フォーマットパターンを設定 
   String formattedAccuracy = formatter.formatNumber(accuracy);
   String rightper = formattedAccuracy;
   
   String rightperT = rightper.substring(0, rightper.length()-1); //%を取ってif文の条件式が有効化する
   double flrightper = Double.parseDouble(rightperT); //string型をdouble型に変換
   
 %> 

<!DOCTYPE html>
<html>
<head>
<title>クイズの解答・解説</title>
<!-- bootstrapを使うときに必ずこのリンクが必要。 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous" />
<link href="ans.css" rel="stylesheet" type="text/css">
</head>
<body>
  <div class="bg-secondary p-1 text-white bg-opacity-10">
   <div class="container-sm my-box">
    <div class="pb-3 mb-1 bg-light text-dark">		    
		<%--現在の問題数--%>   
		<div class="nthnum"><%=pb.getNth()%>問目</div>
	    <%--正解率--%><%--正解率の数値によって色分けする。0%はデフォルト色にする --%>
		<% if(flrightper >= 80) { %>
			<div class="rightper_blue">正解率：<%=rightper%></div>
		<% } else if(flrightper > 0 && flrightper <= 20) { %>
			<div class="rightper_red">正解率：<%=rightper%></div>
		<% } else if(totalQuestions == 0) { %>	
			<div class="rightper">正解率：<%=rightper%></div>
		<% } else{ %>
			<div class="rightper">正解率：<%=rightper%></div>
		<% } %>	
		<br>
		<%--問題文--%>
	<div class="manaka">
		<div class="quest"><%=nb.getQuestion()%></div>
	</div>
        <%--選択式 --%>
	  <div class="flexbox">
		    <button type="botton" <%if(ansNum==1) {%>class="rightchoice"<%}else{%>class="selecter"<%} %> name="choice" value="1" >
		                           <%if(choice==1){%><font><%=nb.getChoice1()%></font><%}else{%><%=nb.getChoice1()%><%} %></button><br>
		    <button type="botton" <%if(ansNum==2) {%>class="rightchoice"<%}else{%>class="selecter"<%} %> name="choice" value="2" >
		                           <%if(choice==2){%><font><%=nb.getChoice2()%></font><%}else{%><%=nb.getChoice2()%><%} %></button><br>
	  </div>
	  <div class="flexbox">
		    <button type="botton" <%if(ansNum==3) {%>class="rightchoice"<%}else{%>class="selecter"<%} %> name="choice" value="3" >
		                           <%if(choice==3){%><font><%=nb.getChoice3()%></font><%}else{%><%=nb.getChoice3()%><%} %></button><br>
		    <button type="botton" <%if(ansNum==4) {%>class="rightchoice"<%}else{%>class="selecter"<%} %> name="choice" value="4" >
                                   <%if(choice==4){%><font><%=nb.getAnswer()%></font><%}else{%><%=nb.getAnswer()%><%} %></button><br>
	  </div>
		<%--正解の場合と不正解の場合で表示するもの --%>
		<div class="bubble-container">
			<%--正解の判定と写真を表示 --%>
			<div class="photo_judge">
			<%if(correct){ %><img src="images/まる.png" width="20%" height="20%">
			<%} else{%><img src="images/ばつ.png" width="20%" height="20%">
			<%} %>
			</div>
			<%--解説欄 --%>
        	<div class="cloud-bubble">
        		<%=nb.getExplanation() %>
        	</div>
        </div>
		<br>
		 
		 <!--  <h4>「正解の選択肢」→<%=nb.getTrueAnswer() %></h4>-->
	<form action="/NekoMy/NekoServlet">
	    <%--現在の問題番号と、自分の選んだ選択肢を送る --%>
		<input type="hidden" name="nth" value="<%=pb.getNth()%>">
		<input type="hidden" name="yourChoice"value="<%=choice%>">
		<%--〇×によってscoreを送る --%>
		<%if(correct){ %>
		<input type="hidden" name="score" value="correct">
		<%} else{%>
		<input type="hidden" name="score" value="incorrect">
		<%} %>
		<%--現在の問題番号は５問までにする→６問目から結果ボタンが出るようにする--%>
		<%if(pb.getNth()<pb.getTotal()) {%>
		<div class="next"><input type="submit" class="btn_next" name="btn" value="次の問題へ" /></div>
		<%}else {%>
	    <div class="fin"><input type="submit" class="btn_fin" name="btn" value="結果" /></div>
		<%} %>
	</form>
   </div>
  </div>
 </div> 
 
 <!-- bootstrapを使うときに必ずこのリンクが必要。 -->
  </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous">
    </script>
</body>
</html>