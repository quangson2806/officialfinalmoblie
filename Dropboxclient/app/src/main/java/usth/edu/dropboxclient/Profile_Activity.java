package usth.edu.dropboxclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile_Activity extends AppCompatActivity {

    private TextView userAvatar;
    private TextView emailUser,userNameUser,phoneUser;
    private Button editUser, signout;
    private ImageView avatarUser;

    private ImageView imgAvatar;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private static final String USER = "user";
    private String UserUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Init();
        SetUserInformation();
        initListener();
    }

    private void SetUserInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }

        String email = getIntent().getStringExtra("email");

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference(USER);


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    if (ds.child("email").getValue().equals(email)) {

                        emailUser.setText(ds.child("email").getValue(String.class));
                        userNameUser.setText(ds.child("username").getValue(String.class));
                        phoneUser.setText(ds.child("phone").getValue(String.class));
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void initListener() {
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Profile_Activity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });




    }



    private void Init() {
        signout = (Button) findViewById(R.id.Signout);
        editUser = (Button) findViewById(R.id.Editprofile);
        emailUser  = (TextView) findViewById(R.id.email_profile);
        userNameUser = (TextView) findViewById(R.id.username_profile);
        phoneUser = (TextView) findViewById(R.id.phone_avatar);
        imgAvatar = (ImageView) findViewById(R.id.imageProfile);
    }

}