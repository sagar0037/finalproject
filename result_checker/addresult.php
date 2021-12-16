<?php
require 'conn.php';
$result_text = $_POST["result_text"];
$answer_id = $_POST["answer_id"];

$checkAvailability = "SELECT * FROM answers WHERE answer_id='$answer_id'";
$queryAvailability = mysqli_query($conn,$checkAvailability);

$checkAnswer = "SELECT * FROM result WHERE answer_id='$answer_id'";
$answerQuery = mysqli_query($conn,$checkAnswer);

if(mysqli_num_rows($queryAvailability)==0){
    $response['error']="100";
    $response['message']="Answer is not found";
}
elseif(mysqli_num_rows($answerQuery)>0){
    $response['error']="200";
    $response['message']="Result is already given";
}
else{
    $insertQuery = "INSERT INTO result(result_text,answer_id) VALUES('$result_text','$answer_id')";
    $result = mysqli_query($conn,$insertQuery);

    if($result){
        $response['error']="000";
        $response['message']="Result is successfully added";
    }
    else{
        $response['error']="300";
        $response['message']="Result is not added";
    }
}
echo json_encode($response);
?>