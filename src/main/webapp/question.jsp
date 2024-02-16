<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="bean.*"%>
<%@ page import="math.NumberFormatter"%><!-- 正解率の％表示に必要 -->
<jsp:useBean id="nb" scope="session" class="bean.NekoBean" />
<jsp:useBean id="pb" scope="session" class="bean.ProcessBean" />

<%-- Javaの式を使用して正解率を計算し表示 --%>
<%
int correctCount = nb.getCorrectman(); // 正解数を取得 
int totalQuestions = nb.getAllchallenger(); // 総回答数を取得 
double accuracy = ((double) correctCount) / totalQuestions; //正解率＝（double型）正解数/回答数*100
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
<title>クイズの問題</title>
<!-- bootstrapを使うときに必ずこのリンクが必要。 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"rel="stylesheet"integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"crossorigin="anonymous" />
<link href="que.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="bg-secondary p-1 text-white bg-opacity-10">
		<div class="container-sm my-box">
			<div class="p-1 mb-1 bg-light text-dark">

				<form action="answer.jsp">
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
					<%-- ここのchiceの選択肢のどれかの１～４番目のクイズ番号を文字列でanswer.jspに送る。それをint型にして照合に使う。--%>
					<div class="flexbox">
						<button type="submit" class="selecter" name="choice" value="1"/><%=nb.getChoice1()%>
						<button type="submit" class="selecter" name="choice" value="2"/><%=nb.getChoice2()%><br>
					</div>
					<div class="flexbox">
						<button type="submit" class="selecter" name="choice" value="3"/><%=nb.getChoice3()%>
						<button type="submit" class="selecter" name="choice" value="4"/><%=nb.getAnswer()%>
					</div>
					<!--<input type="submit" class="btn btn-primary" name="btn" value="回答する" /> -->
				</form>
			</div>
		</div>
	</div>
	<!-- bootstrapを使うときに必ずこのリンクが必要。 -->
	</script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous">
		
	</script>
</body>
</html>