<?php
require "conn.php";
$email = $_POST['email'];
$password = md5($_POST['password']);

$checkEmail = "SELECT * FROM student WHERE email = '$email'";
$emailQuery = mysqli_query($conn,$checkEmail);

if(mysqli_num_rows($emailQuery)>0){
    $checkQuery = "SELECT s.*, i.institute_name FROM student s, institute i WHERE s.institute_id = i.institute_id and email = '$email' and password = '$password'";
    $resultQuery = mysqli_query($conn,$checkQuery);
    if(mysqli_num_rows($resultQuery)>0){

        while($row=$resultQuery->fetch_assoc()){
            $response['student_id']=$row["student_id"];
            $response['full_name']=$row["full_name"];
            $response['username']=$row["username"];
            $response['email']=$row["email"];
            $response['phone']=$row["phone"];
            $response['institute_id']=$row["institute_id"];
            $response['institute_name']=$row["institute_name"];
        }
        $response['error']="000";
        $response['message']="Login Succeed";
    }
    else{
        $response['error']="100";
        $response['message']="Incorrect Password";
    }
}
else{
    $response['error']="200";
    $response['message']="Email is not registered";
}

echo json_encode($response);
?>