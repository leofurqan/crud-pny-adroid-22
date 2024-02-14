<?php
include 'db.php';

if(!empty($_GET['product_id']) && !empty($_POST['name']) && !empty($_POST["price"]) && !empty($_POST["quantity"]) && !empty($_POST["brand"]) && !empty($_POST["image"])) {
    $product_id = $_GET["product_id"];
    $name = $_POST["name"];
    $price = $_POST["price"];
    $quantity = $_POST["quantity"];
    $brand = $_POST["brand"];
    $image = $_POST["image"];
    $date = date('Y-m-d H:i:s');

    $query = "UPDATE products SET name = '$name', price = '$price', quantity = '$quantity', brand_name = '$brand', image = '$image' WHERE id = '$product_id'";
    $result = mysqli_query($conn, $query);

    if($result) {
        echo json_encode([
           'status' => true,
           'message' => 'Product updated succesfully...'
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