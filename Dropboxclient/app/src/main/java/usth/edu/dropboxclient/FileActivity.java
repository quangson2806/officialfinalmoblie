package usth.edu.dropboxclient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import usth.edu.dropboxclient.databinding.MyRecycleViewBinding;

public class FileActivity extends AppCompatActivity {

    Button upload,fetch;
    EditText editText;


    FirebaseStorage storage;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    ArrayList<String> urls = new ArrayList<>();
    DatabaseReference databaseReference;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("Files");

        editText = findViewById(R.id.editfilename);

        fetch = findViewById(R.id.fetchFile);
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FileActivity.this,MyRecycleViewActivity.class ));
            }
        });

        upload = findViewById(R.id.uploadFile);




        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPdf();

            }
        });
    }





    private void selectPdf() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF file"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1 && resultCode == RESULT_OK && data.getData() != null){
            uploadFile(data.getData());

        }else {
            Toast.makeText(this, "Please select a file", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadFile(Uri data) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading file..");
        progressDialog.setProgress(0);
        progressDialog.show();

        StorageReference reference = storageReference.child("Uploads/" +System.currentTimeMillis() + ".pdf");


        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri url = uriTask.getResult();

                        //String url = taskSnapshot.getStorage().getDownloadUrl().getResult().toString();
                        filemodel filemodel = new filemodel(editText.getText().toString(), url.toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(filemodel);
                        Toast.makeText(FileActivity.this, "File  uploaded", Toast.LENGTH_SHORT).show();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FileActivity.this, "File failed uploaded", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        int currentProgress =(int) (100*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                        progressDialog.setProgress(currentProgress);

                    }
                });
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }
}
