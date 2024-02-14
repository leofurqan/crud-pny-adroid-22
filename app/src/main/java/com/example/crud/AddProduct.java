package com.example.crud;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.crud.data.AppData;
import com.example.crud.databinding.ActivityAddProductBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AddProduct extends AppCompatActivity {

    ActivityAddProductBinding binding;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_product);

        binding.btnImage.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
        });

        binding.btnSubmit.setOnClickListener(v -> {
            StringRequest request = new StringRequest(Request.Method.POST, AppData.IP + "add.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("r_add", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("e_add", error.toString());
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("name", binding.etName.getText().toString());
                    params.put("brand_name", binding.etBrand.getText().toString());
                    params.put("price", binding.etPrice.getText().toString());
                    params.put("quantity", binding.etQuantity.getText().toString());
                    params.put("image", image);

                    return params;
                }
            };

            Volley.newRequestQueue(this).add(request);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Uri selectedImageUri = data.getData();
            if (null != selectedImageUri) {
                // update the preview image in the layout
                binding.img.setImageURI(selectedImageUri);
                final InputStream imageStream;
                try {
                    imageStream = getContentResolver().openInputStream(selectedImageUri);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                image = encodeImage(selectedImage);
            }
        }
    }

    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }
}