<?php
chdir('/home/utkarsh/superbatcat/R_thingsv2');
echo "started";
$output = shell_exec('sudo bash load.sh Email-Enron.txt eigenvector.r 2>&1');
echo "<pre>$output</pre>";
echo "done";
?>
