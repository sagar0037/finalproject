<?php
require "conn.php";
$old = md5($_POST['oldPass']);
$new = md5($_POST['newPass']);
$staff_id = $_POST['staff_id'];

$checkStaff = "SELECT * from staff where staff_id='$staff_id' and password='$old'";
$result = mysqli_query($conn, $checkStaff);

if(mysqli_num_rows($result)>0){
    $updatePassword = "UPDATE staff SET password='$new' where staff_id='$staff_id'";
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