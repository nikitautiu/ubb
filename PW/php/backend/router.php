<?php
// router.php
header("Access-Control-Allow-Origin: *");  // for cors
if (isset($_SERVER['HTTP_ORIGIN'])) {
    // Decide if the origin in $_SERVER['HTTP_ORIGIN'] is one
    // you want to allow, and if so:
    header("Access-Control-Allow-Origin: {$_SERVER['HTTP_ORIGIN']}");
    header('Access-Control-Allow-Credentials: true');
    header('Access-Control-Max-Age: 86400');    // cache for 1 day
}

// Access-Control headers are received during OPTIONS requests
if ($_SERVER['REQUEST_METHOD'] == 'OPTIONS') {

    if (isset($_SERVER['HTTP_ACCESS_CONTROL_REQUEST_METHOD']))
        // may also be using PUT, PATCH, HEAD etc
        header("Access-Control-Allow-Methods: GET, POST, PUT, OPTIONS");

    if (isset($_SERVER['HTTP_ACCESS_CONTROL_REQUEST_HEADERS']))
        header("Access-Control-Allow-Headers: {$_SERVER['HTTP_ACCESS_CONTROL_REQUEST_HEADERS']}");

    exit(0);
}

if (preg_match('/articles\/?$/', $_SERVER["REQUEST_URI"])) {
    require 'articles/list.php';    // articles list endpoint
} else if (preg_match("/articles\/[0-9]+\/comments\/?$/", $_SERVER["REQUEST_URI"])) {
    require 'comments/list.php';    // comments list endpoint
} else if (preg_match("/comments\/[0-9=]+\/?$/", $_SERVER["REQUEST_URI"])) {
    require 'comments/detail.php';
} else if (preg_match("/users\/me\/?$/", $_SERVER["REQUEST_URI"])) {
    require 'users/me.php';
} else {
    echo "<p>Welcome to PHP</p>";
}
?>