<?php
$stringt = stripslashes(file_get_contents('php://input'));
$stringt = rtrim($stringt, "\"");
$stringt = substr($stringt, 1);
$data = json_decode($stringt,true);

header('Content-Type: application/json');

$food = $data['food'];
$drink = $data['drink'];
$seat = $data['seat'];







$servername = "localhost";
$username = "root";
$password = "password";
$dbname = "jfk";

// Create connection
$conn = new mysqli($servername, $username, $password,$dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = 'INSERT INTO food (food,drink,seat_number) VALUES ("' . $food . '","' . $drink . "\",\"" . $seat . "\");";

if ($conn->query($sql) === TRUE) {
    echo json_encode("Good");
} else {
 //   echo "Error: " . $sql . "<br>" . $conn->error;
    echo json_encode("Bad");
}

?>