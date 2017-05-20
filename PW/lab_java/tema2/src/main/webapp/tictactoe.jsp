<%@ page import="Tictactoe.Game" %><%--
  Created by IntelliJ IDEA.
  User: vitiv
  Date: 5/16/17
  Time: 1:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Game game = ((Game)request.getAttribute("game"));
    Integer playerId = game.getCurrentPlayer();
    String playerName = (playerId.equals(1) ? game.getPlayer1() : game.getPlayer2());
    boolean myTurn = playerName.equals(session.getAttribute("user"));
%>
<html>
<head>
    <title>Tictactoe</title>
    <link rel="stylesheet" href="css/tictactoe.css" />
</head>
<body>
<div id="page-container">
<div id="game-stats">
    ${game.getPlayer1()} <% if(game.getWinner() != null && game.getWinner().equals(1)) { %> <span class="winner">[Winner]</span> <% } %>
    vs
    ${game.getPlayer2() != null ? game.getPlayer2() : "[Waiting for opponent]"} <% if(game.getWinner() != null && game.getWinner().equals(2)) { %> <span class="winner">[Winner]</span> <% } %>
</div>

<%
    if(game.getWinner() == null) {
    if(myTurn) {
%>
<div id="turn">E tura ta</div>
<% } else { %>
<div id="turn">E tura adversarului</div>
<%}}%>


<table id="game-board">
    <% for(int i = 0; i < 3; ++i) { %>
        <tr>
            <% for(int j = 0; j < 3; ++j) { %>
                <td>
                <%if(game.getCell(i+1, j+1) != null && game.getCell(i+1, j+1) == 1){ %>
                    X
                <% } else if(game.getCell(i+1, j+1) != null && game.getCell(i+1, j+1) == 2) { %>
                    0
                <% } else if(game.getPlayer2() != null  && myTurn) { %>
                    <% if(game.getWinner() == null) { %>
                    <form method="post">
                        <input type="hidden" name="row" value="<%= i+1 %>" />
                        <input type="hidden" name="col" value="<%= j+1 %>" />
                        <input type="submit" />
                    </form>
                <% }} %>
                </td>
            <% } %>
        </tr>
    <% } %>
</table>
</div>
</body>
</html>
