<?php
chdir('/home/utkarsh/superbatcat/R_thingsv2');
echo "started";
$data = $_GET['data'];
$algo = $_GET['algo'];
$output = shell_exec('sudo bash load1.sh '.$data.' '.$algo.' 2>&1');
echo "<pre>$output</pre>";
echo "done";
?>
