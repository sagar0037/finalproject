<?php
require "conn.php";
$module_code = $_POST['module_code'];
$students = "SELECT imd.*, st.username from in_module imd, student st where imd.student_id=st.student_id and module_code='$module_code'";
$resultQuery = mysqli_query($conn,$students);

if(mysqli_num_rows($resultQuery)>0){
    while($row=mysqli_fetch_assoc($resultQuery)){
        $response['students'][]=$row;
    }
}

echo json_encode($response);
?>