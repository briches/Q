<?php
$con=mysqli_connect("localhost", "default", "", "QueueDB");
if(mysqli_connect_errno())
{
	echo "There was an error connecting to the SQL database. Sorry about that. Error: " . mysqli_connect_error();
}
else
{
	$serialno = "sn" . mysqli_real_escape_string($con, $_POST[sn]);
	$count = mysqli_real_escape_string($con, $_POST[count]);
	$delta = mysqli_real_escape_string($con, $_POST[delta]);
	mysqli_query($con, "INSERT INTO $serialno(count, delta) VALUES ($count, $delta)");

	if(mysqli_affected_rows($con))
	{
		echo "Successfully created an entry for " . $serialno . ".";
	}
	else
	{
		echo "Commit failed :C";
	}

	mysqli_close($con);
}
?>
