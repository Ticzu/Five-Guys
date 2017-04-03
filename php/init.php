<?php

error_reporting(0);
$db_name = "worksheetlogin";
$mysql_user = "fiveguys"
$mysql_pass = "fiveguys"
$server_name = "worksheetlogin.com065mqvmij.us-east-1.rds.amazonaws.com"

$conn = mysqli_connect($server_name, $mysql_user, $mysql_pass, $db_name);

if (!$conn) {
	echo("Message: Unable to connect to the database");
}
?>