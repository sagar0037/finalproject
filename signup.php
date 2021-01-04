<?php
require "conn.php";
/*$fullname = $_POST["fullname"];
$email = $_POST["email"];
$username = $_POST["username"];
$password = $_POST["password"];*/

$fullname = "Sagar Parajuli";
$email = "sagarparajuli02@gmail.com";
$username = "sagar02";
$password = "sagar02";

$isEmailValid = filter_var($email, FILTER_VALIDATE_EMAIL);

if($conn){
    if(strlen($password)<6){
        echo "The password must be more than 6 letters";
    }
    elseif(strlen($password)>40){
        echo "The password must be less than 40 letters";
    }
    elseif($isEmailValid===false){
        echo "The email is invalid";
    }
    else{
        $sqlCheckUsername = "SELECT * FROM `user_details` WHERE `username` LIKE '$username'";
        $queryUsername = mysqli_query($conn,$sqlCheckUsername);
        $sqlCheckEmail = "SELECT * FROM `user_details` WHERE `email` LIKE '$email'";
        $queryEmail = mysqli_query($conn,$sqlCheckEmail);

        if(mysqli_num_rows($queryUsername) > 0){
            echo "The username is already used, type another";
        }
        elseif(mysqli_num_rows($queryEmail) > 0){
            echo "The email is already used, type another";
        }
        else{
            $sqlSignUp = "INSERT INTO `user_details`(`fullname`, `email`, `username`, `password`) VALUES ('$fullname', '$email', '$username', '$password')";

            if(mysqli_query($conn, $sqlSignUp)){
                echo "Sign Up Successful";
            }
            else{
                echo "Sign Up Failed";
            }
        }
    }
}
else{
    echo"Connection Error";
}

?>