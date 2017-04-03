<?php
error_reporting(0);
require "init.php";

$name = $_POST["username"];
$password = $_POST["password"];
$fname = $_POST["firstName"];
$lname = $_POST["lastName"];

// $db_name = "innodb";
// $mysql_user = "fiveguys";
// $mysql_pass = "fiveguys";
// $server_name = "worksheetlogin.com065mqvmij.us-east-1.rds.amazonaws.com";


// $con = mysqli_connect($server_name, $mysql_user, $mysql_pass, $db_name);


// $name = "sdf";
// $password = "sdf";
// $fname = "s";
// $lname = "d";
// $email = "sdf@r54";

$sql = "INSERT INTO Users (UserID, Username, Password, Firstname, Lastname) VALUES (NULL, '".$name."', '".$password."', '".$fname."', '".$lname."')";

if(!mysqli_query($con, $sql)){
	echo '{"message":"Unable to save the data to the database."}';
}
?>