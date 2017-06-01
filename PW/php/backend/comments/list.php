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
$articleId = intval($request[1]);
$input["articleId"] = $articleId;

// build the SET part of the SQL command
$set = getSetString($input);

// create SQL based on HTTP method
switch ($method) {
    case 'GET':
        $sql = "select * from  `comments` where `articleId`=".$articleId.(getLoggedUser() != null ? "" : " and `moderated`=1");
        break;
    case 'POST':
        $sql = "insert into `comments` set ".$set; break;
    default:
        http_response_code(405);
        die(mysqli_error());
}

// excecute SQL statement
$result = mysqli_query($link, $sql);

// die if SQL statement failed
if (!$result) {
    http_response_code(404);
    die(mysqli_error());
}


// print results, insert id or affected row count
if ($method == 'GET') {
    $results = array();
    for ($i=0;$i<mysqli_num_rows($result);$i++) {
        $result_obj = mysqli_fetch_object($result);

        // convert them manually because mysqli
        $result_obj->moderated = boolval($result_obj->moderated);
        $result_obj->id = intval($result_obj->id);
        $result_obj->articlesId = intval($result_obj->articlesId);

        $results[] = ($result_obj); // fucking append
    }
    echo json_encode($results);
}
// print results, insert id or affected row count
else  if ($method == 'POST') {
    $input['id'] = mysqli_insert_id($link);
    $input['articleId'] = $articleId;

    echo json_encode($input);
}

// close mysql connection
mysqli_close($link);