<?php
include 'db.php';

$query = "SELECT * FROM products";
$result = mysqli_query($conn, $query);

$products = array();

while($row = mysqli_fetch_assoc($result)) {
    $products[] = $row;
}

echo json_encode([
    'status' => true,
    'products' => $products
]);