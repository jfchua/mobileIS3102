<?php

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
$query = "SELECT * FROM requests"; //You don't need a ; like you do in SQL
$result = $conn->query($query);

echo "<table>"; // start a table tag in the HTML
echo "<tr><th>Request</th><th>Seat Number</th></tr>";
while($row = $result->fetch_assoc()){   //Creates a loop to loop through results
echo "<tr><td>" . $row['request'] . "</td><td>" . $row['seat_number'] . "</td></tr>";  //$row['index'] the index here is a field name
}

echo "</table>"; //Close the table in HTML


?>

<style>
 table, th, td {
    border: 1px solid black;
}
</style>