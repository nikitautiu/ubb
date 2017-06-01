<?php
require __DIR__ . "/../helpers.inc";

$method = $_SERVER['REQUEST_METHOD'];

// create SQL based on HTTP method
switch ($method) {
    case 'GET':
        $user = getLoggedUser();
        echo json_encode(array("username"=>$user));
        break;
    case 'PUT':
        // this is either a login or a logout
        $input = json_decode(file_get_contents('php://input'),true);

        $link = mysqli_connect('127.0.0.1', 'root', 'pass', 'articles_app');
        mysqli_set_charset($link,'utf8');

        // sanitize the input
        $input = sanitizeJson($link, $input);

        if (session_status() == PHP_SESSION_NONE) session_start(); // needed for logging in
        if($input["username"] == null) {
            $_SESSION['logged_user'] = null; // log the user out
            echo json_encode(array("username"=>null));
        }
        else {
            // log him in
            $query = mysqli_prepare($link, "select * from `users` where username=?");
            $query->bind_param('s', $input["username"]);
            $query->execute();
            $queryRes = $query->get_result();

            $user = mysqli_fetch_assoc($queryRes);

            $loggedIn = false;
            if($user != null) {
                $loggedIn = ($user["password"] == $input["password"]);
            }

            if($loggedIn) {
                $_SESSION['logged_user'] = $user["username"]; // log the user in
                echo json_encode(array("username"=>$user["username"]));
            }
            else {
                echo json_encode(array("username"=>null));
            }
            break;
        }
        break;
    default:
        http_response_code(405);
}
