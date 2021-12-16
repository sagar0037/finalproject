<?php
require 'conn.php';
$institute_id = $_POST["institute_id"];
$institute_name = $_POST["institute_name"];

$checkInstitute = "SELECT * from institute WHERE institute_id='$institute_id'";
$checkQuery = mysqli_query($conn,$checkInstitute);

if((mysqli_num_rows($checkQuery))==0){
    $insertQuery = "INSERT INTO institute(institute_id,institute_name) VALUES('$institute_id','$institute_name')";
    $result = mysqli_query($conn,$insertQuery);

    if($result){
        $response['error']="000";
        $response['message']="Register Succeed";
    }
    else{
        $response['error']="100";
        $response['message']="Register Failed";
    }
}
header('location:home.php');
?>