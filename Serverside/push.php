<?php
$con=mysqli_connect("localhost", "default", "", "QueueDB");
if(mysqli_connect_errno())
{
	echo "There was an error connecting to the SQL database. Sorry about that. Error: " . mysqli_connect_error();
}
else
{
	if($statement = mysqli_prepare($con, "INSERT INTO sn?(count, delta) VALUES (?, ?)")))
	{
		mysqli_stmt_bind_param($statement, "sid", $_POST[sn], $_POST[count], $_POST[delta]);

		mysqli_stmt_execute($statement);

		mysqli_stmt_close($statement);
		mysqli_close($con);
	}
	else
	{
		echo "Could not process the statement :C";
	}

	mysqli_stmt_close($statement);
	mysqli_close($con);
}
?>
