<?pip
// 设置返回json格式数据
header('content-type:application/json;charset=utf8');

//连接数据库
// 查询数据到数组中
$result = mysql_query("select top 5 * from Tests order by Score");

$results = array();
while ($row = mysql_fetch_assoc($result)) {
$results[] = $row;
}

// 将数组转成json格式
echo json_encode($results);

// 关闭连接
mysql_free_result($result);

mysql_close($link);
?>