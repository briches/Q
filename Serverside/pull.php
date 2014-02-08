<?php
$con=mysqli_connect("localhost", "root", "", "QueueDB");
if(mysqli_connect_errno())
{
	echo "Could not connect to the database! Error no: " . mysqli_connect_error();
}
else
{
	$query = "SELECT * FROM $_POST[id]";
	$result =  mysqli_query($con, $query);

	echo "{";
	while($row = mysqli_fetch_array($result))
	{
		echo "\"" . $row['id'] . "\":{\"count\":\"" . $row['count'] . "\",\"delta\":\"" . $row['delta'] . "\"},";
	}
	echo "\"NULL\":\"NULL\"}";

	mysqli_close($con);
}
?>
