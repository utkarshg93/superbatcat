<?php
chdir('/home/utkarsh/R_things');
echo "started";
$output = shell_exec('sudo bash eigenvector.sh enron_undir_edgelist 2>&1');
echo "<pre>$output</pre>";
echo "done";
?>
