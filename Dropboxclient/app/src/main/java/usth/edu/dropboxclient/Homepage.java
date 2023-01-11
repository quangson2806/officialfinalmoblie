package usth.edu.dropboxclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


    }
    public void btnFile(View view){
        Intent btnFile =new Intent(this,FileActivity.class);
        startActivity(btnFile);

    }
    public void btnImage(View view){
        Intent btnImage =new Intent(this,Image_Screen.class);
        startActivity(btnImage);
    }
    public void btnVideo(View view){
        Intent btnVideo =new Intent(this,dashboard.class);
        startActivity(btnVideo);
    }
    public void btnProfile(View view){
        String email = getIntent().getStringExtra("email");
        Intent btnProfile =new Intent(this,Profile_Activity.class);
        btnProfile.putExtra("email", email);
        startActivity(btnProfile);
    }

}