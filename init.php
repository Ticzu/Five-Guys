<?php

error_reporting(0);
$db_name = "innodb";
$mysql_user = "fiveguys";
$mysql_pass = "fiveguys";
$server_name = "worksheetlogin.com065mqvmij.us-east-1.rds.amazonaws.com";


$con = mysqli_connect($server_name, $mysql_user, $mysql_pass, $db_name);

if (!$con) {

	echo("Message: Unable to connect to the database");
} else{
	echo "string";
}
?>