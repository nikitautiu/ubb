<%--
  Created by IntelliJ IDEA.
  User: vitiv
  Date: 5/12/17
  Time: 10:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Hangman</title>
  <link rel="stylesheet" href="css/hangman.css" />
  <script type="text/javascript" src="js/jquery-3.2.1.js"></script>
  <script type="text/javascript" src="js/hangman.js"></script>
</head>
<body>
  <div id="page-container">
    <canvas id="stickman">This Text will show if the Browser does NOT support HTML5 Canvas tag</canvas>
    <ul id="word" class="guess"></ul>
    <div id="mistakes">Greseli ramase: <span id="lives-left">0</span></div>
    <ul id="bad-guesses" class="guess bad-guess"></ul>
  </div>
<script type="text/javascript">word = "${word}";</script>
</body>
</html>
