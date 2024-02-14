<?php
$host = "localhost";
$user = "root";
$password = "";
$db = "ecommerce";

$conn = mysqli_connect($host, $user, $password, $db);

if(!$conn) {
    die('Connection Failed');
}