<?php
require 'conn.php';
$question_text = $_POST["question_text"];
$individual_id = $_POST["individual_id"];

$checkQuestions = "SELECT * from in_module WHERE individual_id='$individual_id'";
$checkQuery = mysqli_query($conn,$checkQuestions);

if(mysqli_num_rows($checkQuery)>0){
    $insertQuery = "INSERT INTO questions(question_text,individual_id) VALUES('$question_text','$individual_id')";
    $result = mysqli_query($conn,$insertQuery);

    if($result){
        $response['error']="000";
        $response['message']="Questions are successfully added";
    }
    else{
        $response['error']="100";
        $response['message']="Questions are not added";
    }
}
else{
    $response['error']="200";
    $response['message']="Module not found";
}
echo json_encode($response);
?>