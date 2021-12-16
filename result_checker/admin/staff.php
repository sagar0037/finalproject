<?php
session_start();
if(!isset($_SESSION['username'])){
    header('location:login.php');
}
?>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Home Page</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="style.css">
    </head>
    <body>
        <div id="nav-bar">
            <ul>
                <li>Dashboard</li>
                <li><a href="home.php">Institute</a></li>
                <li>Staff</li>
                <li><a href="student.php">Student</a></li>
                <li><a href="logout.php">Logout</a></li>
            </ul>
        </div>
        <div id="container">
            <hr />
            <h1>Staff</h1>
            <div class="user-table">
                <table>
                    <tr>
                        <th>Staff ID</th>
                        <th>Staff Name</th>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Phone Number</th>
                        <th>Institute</th>
                    </tr>
                    <?php
                        require "conn.php";
                        if($conn){
                            $instituteQuery = "SELECT s.*, i.institute_name FROM staff s, institute i WHERE s.institute_id = i.institute_id";
                            $resultQuery = mysqli_query($conn,$instituteQuery);
                            if(mysqli_num_rows($resultQuery)>0){
                                while($row=mysqli_fetch_assoc($resultQuery)){
                                    echo "<tr><td>".$row["staff_id"]."</td><td>".$row["full_name"]."</td><td>".$row["username"]."</td><td>"
                                    .$row["email"]."</td><td>".$row["phone"]."</td><td>".$row["institute_name"]."</td></tr>";
                                }
                            }
                            else{
                                echo "Result 0";
                            }
                        }
                    ?>
                </table>

            </div>
            <hr />
        </div>
    </body>
</html>
