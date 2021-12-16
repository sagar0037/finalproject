<?php
require "conn.php";
$individual_id = $_POST["individual_id"];

$checkQuestion = "SELECT q.*, a.answer_id, a.answer_text, r.result_text FROM questions q, answers a, result r WHERE q.question_id = a.question_id AND a.answer_id = r.answer_id AND q.individual_id='$individual_id'";
$questionQuery = mysqli_query($conn,$checkQuestion);

if(mysqli_num_rows($questionQuery)>0){
    while($row[] = mysqli_fetch_assoc($questionQuery)){
        $response['result']=$row;
    }
    $response['error']="000";
    $response['message']="Questions sucessfully showed";
}else{
    $response['error']="100";
    $response['message']="Failed to find the questions"; 
}

echo json_encode($response);
?>