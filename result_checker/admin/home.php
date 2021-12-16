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
                <li>Institute</li>
                <li><a href="staff.php">Staff</a></li>
                <li><a href="student.php">Student</a></li>
                <li><a href="logout.php">Logout</a></li>
            </ul>
        </div>
        <div id="container">
            <hr />
            <h1>Institute</h1>
            <div class="institute-table">
                <table>
                    <tr>
                        <th>Institute ID</th>
                        <th>Institute Name</th>
                    </tr>
                    <?php
                        require "conn.php";
                        if($conn){
                            $instituteQuery = "SELECT * from institute";
                            $resultQuery = mysqli_query($conn,$instituteQuery);
                            if(mysqli_num_rows($resultQuery)>0){
                                while($row=mysqli_fetch_assoc($resultQuery)){
                                    echo "<tr><td>".$row["institute_id"]."</td><td>".$row["institute_name"]."</td></tr>";
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
            <div class="register-form">
                <h2>Institute Register</h2>
                <form action="instituteregister.php" method="POST">
                    <div class="fields">
                        <input type="number" name="institute_id" required>
                        <label>Institute ID</label>
                    </div>
                    <div class="fields">
                        <input type="text" name="institute_name" required>
                        <label>Institute Name</label>
                    </div>
                    <input type="submit" value="Register">
                </form>
            </div>
            <hr />
        </div>
    </body>
</html>
