<?php
require 'conn.php';
$full_name = $_POST['full_name'];
$username = $_POST['username'];
$email = $_POST['email'];
$password = md5($_POST['password']);
$phone = $_POST['phone'];
$institute_id = $_POST['institute_id'];

$isEmailValid = filter_var($email, FILTER_VALIDATE_EMAIL);
$checkUser = "SELECT * FROM student WHERE username = '$username'";
$checkEmail = "SELECT * FROM student WHERE email = '$email'";
$checkInstitute = "SELECT * from institute WHERE institute_id='$institute_id'";

$userQuery = mysqli_query($conn,$checkUser);
$emailQuery = mysqli_query($conn,$checkEmail);
$instituteQuery = mysqli_query($conn,$checkInstitute);

if($isEmailValid===false){
    $response['error']="100";
    $response['message']="Invalid Email";
}
elseif(mysqli_num_rows($userQuery)>0){
    $response['error']="200";
    $response['message']="Username already registered";
}
elseif(mysqli_num_rows($emailQuery)>0){
    $response['error']="300";
    $response['message']="Email already registered";
}
elseif(mysqli_num_rows($instituteQuery)>0){
    $insertQuery = "INSERT INTO student(full_name,username,email,password,phone,institute_id) VALUES('$full_name','$username','$email','$password','$phone','$institute_id')";
    $result = mysqli_query($conn,$insertQuery);
    if($result){
        $response['error']="000";
        $response['message']="Register Succeed";
    }
    else{
        $response['error']="400";
        $response['message']="Register Failed";
    }
}
else{
    $response['error']="500";
    $response['message']="Institute is not registered";
}

echo json_encode($response);

?>