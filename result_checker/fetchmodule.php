<?php
require "conn.php";
$staff_id = $_POST["staff_id"];
$modules = "SELECT * from module where staff_id='$staff_id'";
$resultQuery = mysqli_query($conn,$modules);
$response = array();

if(mysqli_num_rows($resultQuery)>0){
    while($row[] = mysqli_fetch_assoc($resultQuery)){
        $response['module']=$row;
    }
}
else{
    $response['module']=(object)[];
}

echo json_encode($response);
?>