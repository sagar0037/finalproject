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
                <li><a href="staff.php">Staff</a></li>
                <li>Student</li>
                <li><a href="logout.php">Logout</a></li>
            </ul>
        </div>
        <div id="container">
            <hr />
            <h1>Student</h1>
            <div class="user-table">
                <table>
                    <tr>
                        <th>Student ID</th>
                        <th>Student Name</th>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Phone Number</th>
                        <th>Institute</th>
                    </tr>
                    <?php
                        require "conn.php";
                        if($conn){
                            $instituteQuery = "SELECT s.*, i.institute_name FROM student s, institute i WHERE s.institute_id = i.institute_id";
                            $resultQuery = mysqli_query($conn,$instituteQuery);
                            if(mysqli_num_rows($resultQuery)>0){
                                while($row=mysqli_fetch_assoc($resultQuery)){
                                    ?>
                                    <tr>
                                        <td> <?php echo $row['student_id']; ?> </td>
                                        <td> <?php echo $row['full_name']; ?> </td>
                                        <td> <?php echo $row['username']; ?> </td>
                                        <td> <?php echo $row['email']; ?> </td>
                                        <td> <?php echo $row['phone']; ?> </td>
                                        <td> <?php echo $row['institute_name']; ?> </td>

                                    </tr>
                                    <?php
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
