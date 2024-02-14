<?php
include 'db.php';

if(!empty($_GET["product_id"])) {
    $product_id = $_GET["product_id"];

    $query = "DELETE FROM products WHERE id = '$product_id'";
    $result = mysqli_query($conn, $query);

    if($result) {
        echo json_encode([
          'status' => true,
          'message' => 'Product deleted succesfully...'
        ]);
    } else {
        echo json_encode([
          'status' => false,
          'message' => 'Something went wrong...'
        ]);
    }
} else {
    echo json_encode([
        "status" => false,
        "message" => "All fields are required"
    ]);
}