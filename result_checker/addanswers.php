<?php
require 'conn.php';
$answer_text = $_POST["answer_text"];
$question_id = $_POST["question_id"];

$checkAvailability = "SELECT * FROM questions WHERE question_id='$question_id'";
$queryAvailability = mysqli_query($conn,$checkAvailability);

$checkQuestion = "SELECT * FROM answers WHERE question_id='$question_id'";
$questionQuery = mysqli_query($conn,$checkQuestion);

if(mysqli_num_rows($queryAvailability)==0){
    $response['error']="100";
    $response['message']="Question is not found";
}
elseif(mysqli_num_rows($questionQuery)>0){
    $response['error']="200";
    $response['message']="Answer is already given";
}
else{
    $insertQuery = "INSERT INTO answers(answer_text,question_id) VALUES('$answer_text','$question_id')";
    $result = mysqli_query($conn,$insertQuery);

    if($result){
        $response['error']="000";
        $response['message']="Answer is successfully added";
    }
    else{
        $response['error']="300";
        $response['message']="Answer is not added";
    }
}
echo json_encode($response);
?>