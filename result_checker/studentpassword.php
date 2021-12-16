<?php
require "conn.php";
$old = md5($_POST['oldPass']);
$new = md5($_POST['newPass']);
$student_id = $_POST['student_id'];

$checkStudent = "SELECT * from student where student_id='$student_id' and password='$old'";
$result = mysqli_query($conn, $checkStudent);

if(mysqli_num_rows($result)>0){
    $updatePassword = "UPDATE student SET password='$new' where student_id='$student_id'";
    $updateQuery = mysqli_query($conn, $updatePassword);

    if($updateQuery>0){
        $response['error']="000";
        $response['message']="Password update succeed";
    }
    else{
        $response['error']="100";
        $response['message']="Password update failed";
    }
}
else{
    $response['error']="200";
    $response['message']="Incorrect password";
}

echo json_encode($response);

?>