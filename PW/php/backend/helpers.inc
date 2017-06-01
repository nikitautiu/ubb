<?php
/**
 * Created by PhpStorm.
 * User: vitiv
 * Date: 6/1/17
 * Time: 6:01 PM
 */
function sanitizeJson($link, $input)
{
    array_walk($input, function (&$value, $key) use ($link) {
        if ($value === null) $value = null;
        else $value = mysqli_real_escape_string($link, (string)$value);
    }, $input);
    return $input;
}

function getSetString($input)
{
    $set = '';
    for ($i = 0, $keys = array_keys($input); $i < count($keys); $i++) {
        $set .= ($i > 0 ? ',' : '') . '`' . $keys[$i] . '`=';
        $set .= ($input[$keys[$i]] === null ? 'NULL' : '"' . $input[$keys[$i]] . '"');
    }
    return $set;
}

function getLoggedUser() {
    if (session_status() == PHP_SESSION_NONE) session_start();
    if(isset($_SESSION['logged_user'])) return $_SESSION['logged_user'];
    return null;
}

