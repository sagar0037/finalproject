<?php
session_start();
if(isset($_SESSION['username'])){
    header('location:home.php');
}

?>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Admin Login</title>
        <meta charset="utf-8">
        <link rel="stylesheet" href="style.css">
        <style>
            h1{
                color: blue;
            }
        </style>
    </head>
    <body>
        <div class="login-form">
            <h1>Login</h1>
            <form action="validate.php" method="POST">
                <div class="fields">
                    <input type="text" name="user" required>
                    <label>Username</label>
                </div>
                <div class="fields">
                    <input type="password" name="pass" required>
                    <label>Password</label>
                </div>
                <input type="submit" value="Login">
            </form>
        </div>
    </body>
</html>
