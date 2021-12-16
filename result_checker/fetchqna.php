<?php
require "conn.php";
$individual_id = $_POST["individual_id"];

$checkQuestion = "SELECT q.*, a.answer_id, a.answer_text FROM questions q, answers a WHERE q.question_id = a.question_id AND a.answer_id NOT IN (SELECT answer_id FROM result) AND q.individual_id='$individual_id'";
$questionQuery = mysqli_query($conn,$checkQuestion);

if(mysqli_num_rows($questionQuery)>0){
    while($row[] = mysqli_fetch_assoc($questionQuery)){
        $response['answers']=$row;
    }
    $response['error']="000";
    $response['message']="Questions sucessfully showed";
}else{
    $response['error']="100";
    $response['message']="Failed to find the questions"; 
}

echo json_encode($response);
?>