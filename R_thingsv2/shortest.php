<?php
chdir('/home/utkarsh/superbatcat/R_thingsv2');
echo "started";

$src = $_GET['src'];
$target = $_GET['target'];
$flag = $_GET['flag'];
$topen = $_GET['topen'];
echo $src;
$output = shell_exec('sudo bash shortest.sh '.$src.' '.$target.' '.$flag.' '.$topen.' 2>&1');
echo "<pre>$output</pre>";
echo "done";
?>
