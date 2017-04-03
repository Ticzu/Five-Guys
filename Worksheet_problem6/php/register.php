<?php
error_reporting(0);
require "init.php";

$name = $_POST["name"];
$password = $_POST["password"];
$fname = $_POST["FirstName"];
$lname = $_POST["LastName"];


$sql = "INSERT INTO 'Users' ('UserID', 'Username', 'Password', 'FirstName', 'LastName') VALUES (NULL, '".$name."', '".$password."', '".$fname."', '".$lname."');";

if (!$conn) {
	echo("Message: Unable to save to the database");
}

?>