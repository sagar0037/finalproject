<?php
require 'conn.php';
$module_code = $_POST['module_code'];
$student_id = $_POST['student_id'];
$institute_id = $_POST['institute_id'];

$checkModule = "SELECT * FROM module WHERE module_code = '$module_code'";
$checkStudent = "SELECT * FROM student WHERE student_id = '$student_id'";
$checkInstitute = "SELECT * from institute WHERE institute_id='$institute_id'";
$checkInstituteConn = "SELECT * FROM student WHERE student_id = '$student_id' and institute_id='$institute_id'";
$checkIndividual = "SELECT * FROM in_module WHERE module_code = '$module_code' and student_id = '$student_id'";
$checkAvailable = "SELECT * from module INNER JOIN staff ON staff.staff_id = module.staff_id WHERE module.module_code='$module_code' and staff.institute_id='$institute_id'";

$moduleQuery = mysqli_query($conn,$checkModule);
$studentQuery = mysqli_query($conn,$checkStudent);
$instituteQuery = mysqli_query($conn,$checkInstitute);
$instituteConnQuery = mysqli_query($conn,$checkInstituteConn);
$individualQuery = mysqli_query($conn,$checkIndividual);
$availableQuery = mysqli_query($conn,$checkAvailable);

if(mysqli_num_rows($moduleQuery)==0){
    $response['error']="100";
    $response['message']="Unidentified module";
}
elseif(mysqli_num_rows($studentQuery)==0){
    $response['error']="200";
    $response['message']="Student is not registered";
}
elseif(mysqli_num_rows($instituteQuery)==0){
    $response['error']="300";
    $response['message']="Institute is not registered";
}
elseif(mysqli_num_rows($instituteConnQuery)==0){
    $response['error']="400";
    $response['message']="Institute is not available";
}
elseif(mysqli_num_rows($individualQuery)>0){
    $response['error']="500";
    $response['message']="Module Already Added";
}
elseif(mysqli_num_rows($availableQuery)>0){
    $insertQuery = "INSERT INTO in_module(module_code,student_id,institute_id) VALUES('$module_code','$student_id','$institute_id')";
    $result = mysqli_query($conn,$insertQuery);
    if($result){
        $response['error']="000";
        $response['message']="Module is successfully added";
    }
    else{
        $response['error']="600";
        $response['message']="Module is not added";
    }
}
else{
    $response['error']="700";
    $response['message']="Module is not available";  
}

echo json_encode($response);

?>