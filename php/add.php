<?php
include 'db.php';

if(!empty($_POST['name']) && !empty($_POST["price"]) && !empty($_POST["quantity"]) && !empty($_POST["brand_name"]) && !empty($_POST["image"])) {
    $name = $_POST["name"];
    $price = $_POST["price"];
    $quantity = $_POST["quantity"];
    $brand = $_POST["brand_name"];
    $image = $_POST["image"];
    $date = date('Y-m-d H:i:s');

    $query = "INSERT INTO products(name, price, quantity, brand_name, image, created_date) VALUE('$name', '$price', '$quantity', '$brand', '$image', '$date')";

    $result = mysqli_query($conn, $query);
    if($result) {
        echo json_encode([
            'status' => true,
            'message' => 'Product added succesfully...'
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

function base64_to_jpeg($base64_string, $output_file) {
    // open the output file for writing
    $ifp = fopen( $output_file, 'wb' ); 

    // split the string on commas
    // $data[ 0 ] == "data:image/png;base64"
    // $data[ 1 ] == <actual base64 string>
    $data = explode( ',', $base64_string );

    // we could add validation here with ensuring count( $data ) > 1
    fwrite( $ifp, base64_decode( $data[ 1 ] ) );

    // clean up the file resource
    fclose( $ifp ); 

    return $output_file; 
}