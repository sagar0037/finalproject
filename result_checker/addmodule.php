<?php
require 'conn.php';
$module_code = $_POST["module_code"];
$module_name = $_POST["module_name"];
$staff_id = $_POST["staff_id"];

$checkStaff = "SELECT * FROM staff WHERE staff_id = '$staff_id'";
$staffQuery = mysqli_query($conn,$checkStaff);
$checkModule = "SELECT * from module WHERE module_code='$module_code'";
$checkQuery = mysqli_query($conn,$checkModule);

if(mysqli_num_rows($checkQuery)>0){
    $response['error']="100";
    $response['message']="Module code already used";
}
elseif(mysqli_num_rows($staffQuery)>0){
    $insertQuery = "INSERT INTO module(module_code,module_name,staff_id) VALUES('$module_code','$module_name','$staff_id')";
    $result = mysqli_query($conn,$insertQuery);

    if($result){
        $response['error']="000";
        $response['message']="Module is successfully added";
    }
    else{
        $response['error']="200";
        $response['message']="Module is not added";
    }
}
else{
    $response['error']="300";
    $response['message']="Staff is not registered";
}
echo json_encode($response);
?>