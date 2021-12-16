<?php
session_start();

$user = $_POST['user'];
$pass = $_POST['pass'];

if($user=="admin" && $pass=="admin"){
    $_SESSION['username'] = $user;
    header('location:home.php');
}
else{
    header('location:login.php');
}
?>