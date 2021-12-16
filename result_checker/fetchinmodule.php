<?php
require "conn.php";
$student_id = $_POST["student_id"];
$modules = "SELECT imd.*, md.module_name from in_module imd, module md where imd.module_code=md.module_code and student_id='$student_id'";
$resultQuery = mysqli_query($conn,$modules);

if(mysqli_num_rows($resultQuery)>0){
    while($row=mysqli_fetch_assoc($resultQuery)){
        $response['inmodule'][]=$row;
    }
}
else{
    $response['inmodule']= [object] ;
}
echo json_encode($response);
?>