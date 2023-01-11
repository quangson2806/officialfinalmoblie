package usth.edu.dropboxclient;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class SignInActivity extends AppCompatActivity {

    private TextView layoutSignup;
    private EditText Email;
    private EditText Password;
    private Button Login_btn;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Int();
        initListener();

    }


    private void Int(){
        layoutSignup = (TextView) findViewById(R.id.signup_link);
        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password_login);
        Login_btn = (Button) findViewById(R.id.button_login);
        progressDialog = new ProgressDialog(this);

    }

    private void initListener(){
        layoutSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        Login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OnClickSignin();
            }
        });
    }


    private void OnClickSignin() {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        String username = Email.getText().toString();
        String password = Password.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter your email and password !!", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.show();
        auth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignInActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignInActivity.this, Homepage.class);
                            intent.putExtra("email", username);
                            startActivity(intent);
                            //UpdateUI();
                            //SendImage();
                            //finishAffinity();
                            progressDialog.dismiss();

                        } else {
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void UpdateUI() {
        String username = Email.getText().toString();
        Intent profileIntent = new Intent(this, Profile_Activity.class);
        profileIntent.putExtra("email", username);
        startActivity(profileIntent);

    }

    private void SendImage() {
        String username = Email.getText().toString();
        Intent profileIntent = new Intent(this, Homepage.class);
        //profileIntent.putExtra("email", username);
        startActivity(profileIntent);

    }
}