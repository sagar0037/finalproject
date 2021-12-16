<?php
require "conn.php";
$individual_id = $_POST["individual_id"];

$checkQuestion = "SELECT * FROM questions WHERE question_id NOT IN (SELECT question_id FROM answers) AND individual_id='$individual_id'";
$questionQuery = mysqli_query($conn,$checkQuestion);

if(mysqli_num_rows($questionQuery)>0){
    while($row[] = mysqli_fetch_assoc($questionQuery)){
        $response['questions']=$row;
    }
    $response['error']="000";
    $response['message']="Questions sucessfully showed";
}else{
    $response['error']="100";
    $response['message']="Failed to find the questions"; 
}

echo json_encode($response);
?>