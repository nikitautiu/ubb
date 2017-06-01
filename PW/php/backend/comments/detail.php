<?php
require __DIR__ . "/../helpers.inc";

header("Content-type: application/json"); // My assumption of your model..


$method = $_SERVER['REQUEST_METHOD'];
$request = explode('/', trim($_SERVER['REQUEST_URI'],'/'));
$input = json_decode(file_get_contents('php://input'),true);

$link = mysqli_connect('127.0.0.1', 'root', 'pass', 'articles_app');
mysqli_set_charset($link,'utf8');

// sanitize the input
$input = sanitizeJson($link, $input);
$commentId = intval($request[1]);
$input["id"] = $commentId;

// build the SET part of the SQL command
$set = getSetString(array("moderated"=>$input["moderated"]));

// create SQL based on HTTP method
switch ($method) {
    case 'PUT':
        $sql = "update `comments` set `moderated`=1 where `id`=".$commentId; break;
    default:
        http_response_code(405);
        die();
        break;
}

// excecute SQL statement
$result = mysqli_query($link, $sql);

// die if SQL statement failed
if (!$result) {
    http_response_code(404);
    die(mysqli_error($link));
}

// print results, insert id or affected row count
else if ($method == 'PUT') {
    echo json_encode($input);
}

// close mysql connection
mysqli_close($link);