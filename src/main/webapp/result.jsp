<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="bean.*"%>
<%@page import="java.util.Random"%><!-- ランダム変数を使うため -->
<jsp:useBean id="nb" scope="session" class="bean.NekoBean" />
<jsp:useBean id ="pb" scope="session" class="bean.ProcessBean" />
<jsp:useBean id ="dto" scope="session" class="bean.NekoDTO" />
<%
int score=pb.getScore()*20;

Random rand = new Random();
int num = rand.nextInt(7) + 1;
%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="result.css">
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
		
	<title>結果画面</title>
	</head>
	<body>
	<div class="yourscore">YourScore is <%=score%></div>
	<%--スコアによって表示する画像と、コメントを表示する --%>
	<div class="photo_range">
	<%if (score == 100) {%>
	<img src="images/result/100/fukidashi.png" alt="Image F" class="fukidasi">
	<img src="images/result/100/r-<%=num%>.png" alt="Image R" class="random_image">
	<%}else if(score<100&&score>=60){%>
	<img src="images/result/60/fukidashi.png" alt="Image F" class="fukidasi">
	<img src="images/result/60/r-<%=num%>.png" alt="Image R" class="random_image">
	<%}else if(score<60&&score>=20){%>
	<img src="images/result/20/fukidashi.png" alt="Image F" class="fukidasi">
	<img src="images/result/20/r-<%=num%>.png" alt="Image R" class="random_image">
	<%}else{ %>
	<img src="images/result/0/fukidashi.png" alt="Image F" class="fukidasi">
	<img src="images/result/0/r-<%=num%>.png" alt="Image R" class="random_image">
	<%} %>
	</div>	
	<%
	for (int i = 0; i < dto.size(); i++) {
		nb = dto.get(i);
	%>
	<details>
  		<summary>
			<div class="monn">
				<span class="mo">
					問題<%=i + 1%>
				</span>
				<span 
					<%if(nb.getCorrect()){%>id="corr"<%
							}else{%>id="unc"<%
							}%>
				>
					<%if(nb.getCorrect()){%>〇<%
							}else{%>×<%
							}%>
				</span>
			</div>
		</summary>
		<p>
			<div class="alert alert-warning fs-4" role="alert" style="white-space: pre;"><%=nb.getQuestion()%></div>
			<br>		
			<%--選択式 --%>
	<div class="suihei"	>
		<div class="flexbox">
		    <button type="botton" <%if(nb.getAnsNum()==1) {%>class="rightchoice"<%}else{%>class="selecter"<%} %> name="choice" value="1" >
		                           <%if(nb.getYourChoice()==1){%><font>1)&nbsp;<%=nb.getChoice1()%></font><%}else{%>1)&nbsp;<%=nb.getChoice1()%><%} %></button><br>
		    <button type="botton" <%if(nb.getAnsNum()==2) {%>class="rightchoice"<%}else{%>class="selecter"<%} %> name="choice" value="2" >
		                           <%if(nb.getYourChoice()==2){%><font>2)&nbsp;<%=nb.getChoice2()%></font><%}else{%>2)&nbsp;<%=nb.getChoice2()%><%} %></button><br>
		    <button type="botton" <%if(nb.getAnsNum()==3) {%>class="rightchoice"<%}else{%>class="selecter"<%} %> name="choice" value="3" >
		                           <%if(nb.getYourChoice()==3){%><font>3)&nbsp;<%=nb.getChoice3()%></font><%}else{%>3)&nbsp;<%=nb.getChoice3()%><%} %></button><br>
		    <button type="botton" <%if(nb.getAnsNum()==4) {%>class="rightchoice"<%}else{%>class="selecter"<%} %> name="choice" value="4" >
                                   <%if(nb.getYourChoice()==4){%><font>4)&nbsp;<%=nb.getAnswer()%></font><%}else{%>4)&nbsp;<%=nb.getAnswer()%><%} %></button><br>
	    </div>
	    	<%--正解の判定と写真を表示 --%>
		<div class="photo_judge">
			<%if(nb.getCorrect()){ %><img src="images/まる.png" >
			<%} else{%><img src="images/ばつ.png" >
			<%} %>
		</div>
	</div>
	    
	    <%-- 
			<br>
			あなたの選択：<%if(nb.getYourChoice()==0){%>未選択
			<%}else{%><%=nb.getYourChoice()%>
			<%} %><br>
		    正解の選択肢:<%=nb.getTrueAnswer() %>
		--%>
		    
		<%--正解の場合と不正解の場合で表示するもの --%>
		<div class="bubble-container">

			<%--解説欄 --%>
        	<div class="cloud-bubble">
        		<%=nb.getExplanation() %>
        	</div>
        </div>
			<br>	
		</p>
	</details>
	<%} %>
	<a href="quiz.html">ホームに戻る</a>
</body>
</html>
<%session.invalidate();%>