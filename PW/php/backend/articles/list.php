<?php
require __DIR__ . "/../helpers.inc";

$method = $_SERVER['REQUEST_METHOD'];

$link = mysqli_connect('127.0.0.1', 'root', 'pass', 'articles_app');
mysqli_set_charset($link,'utf8');

// create SQL based on HTTP method
switch ($method) {
    case 'GET':
        $sql = "select * from `articles`"; break;
    default:
        http_response_code(405);
        die(mysqli_error());
}

// excecute SQL statement
$result = mysqli_query($link,$sql);

// die if SQL statement failed
if (!$result) {
    http_response_code(404);
    die(mysqli_error());
}

// print results, insert id or affected row count
    echo '{"articles":[';
    for ($i=0;$i<mysqli_num_rows($result);$i++) {
        $article = mysqli_fetch_object($result);
        $article->id = (int) $article->id;
        $article->comments = array();

        $commQuery = mysqli_prepare($link, "select * from `comments` where articleId=?".(getLoggedUser() != null ? "" : " and `moderated`=1"));
        $commQuery->bind_param('d', $article->id);
        $commQuery->execute();
        $commResult = $commQuery->get_result();

        for($j=0; $j < mysqli_num_rows($commResult); $j++) {
            $result_obj = mysqli_fetch_object($commResult);
            $result_obj->moderated = boolval($result_obj->moderated);
            $result_obj->id = intval($result_obj->id);
            $result_obj->articleId = intval($result_obj->articleId);

            array_push($article->comments, $result_obj);
        }
        echo ($i>0?',':'').json_encode($article);
    }
    echo "]}";

// close mysql connection
mysqli_close($link);