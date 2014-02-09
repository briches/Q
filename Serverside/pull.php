<?php
$con = mysqli_connect("localhost", "default", "", "QueueDB");
if(mysqli_connect_errno())
{
	echo "There was an error connecting to the SQL database. Sorry about that. Error: " . mysqli_connect_error();
}
else
{
	/*Prepare*/
	if(!($statement = mysqli_prepare($con, "SELECT sn, name, address, lat, lon FROM units WHERE type=?")))
	{
		echo "Statement preparation failed";
		die("Quitting");
	}
	
	/*Execute*/
	mysqli_stmt_bind_param($statement, "s", $_GET[type]);
	mysqli_stmt_execute($statement);
	$statement -> bind_result($sn, $name, $addr, $lat, $lon);

	$serials = array();
	$data = array();
	$i = 0;
	while($statement -> fetch())
	{
		$serials[$i] = $sn;
		$data[$i] = "{\"name\":\"" . $name . "\",\"addr\":\"" . $addr . "\",\"lat\":\"" . $lat . "\",\"lon\":\"" . $lon . "\"}";
		$i++;
	}

	$n = 0;
	
	echo "{\"devices\":[";
	while($n < $i)
	{
		$query = "SELECT * FROM sn$serials[$n] WHERE t = (
								SELECT max(t) FROM sn$serials[$n])";
		$result = mysqli_query($con, $query);
		$entry = mysqli_fetch_array($result);
		echo "\n{\"" . $serials[$n] . "\":{" . "\"loc\":" . $data[$n] . ",\"t\":\"", $entry[t], "\",\"count\":\"" . $entry[count] . "\",\"delta\":\"" . $entry[delta] . "\"}},";
		$n++;
	}
	echo "\n{\"NONE\":\"NONE\"}]}";
	
	mysqli_stmt_close($statement);	
	mysqli_close($con);
}
?>
