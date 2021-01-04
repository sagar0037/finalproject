<?php 
require "conn.php";
$email = $_POST["email"];
$password = $_POST["password"];

/*$email = "sagar@gmail.com";
$password = "sagar0012";*/

$isEmailValid = filter_var($email, FILTER_VALIDATE_EMAIL);

if($conn){
    if($isEmailValid===false){
        echo "The email is invalid";
    }
    else{
        $sqlCheckEmail = "SELECT * FROM `user_details` WHERE `email` LIKE '$email'";
        $queryCheckEmail = mysqli_query($conn,$sqlCheckEmail);

        if(mysqli_num_rows($queryCheckEmail) > 0){
            $sqlEmail = "SELECT * FROM `user_details` WHERE `email` LIKE '$email' AND `password` LIKE '$password";
            $queryEmail = mysqli_query($conn,$sqlCheckEmail);

            if(mysqli_num_rows($queryEmail) > 0){
                echo "Login Successful";
            }
            else{
                echo "Incorrect Password";
            }
        }
        else{
            echo "The email is not registered";
        }
    }
}
else{
    echo "Connection Error";
}
?>