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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText Email;
    private EditText Password;
    private EditText Username;
    private EditText Phone;
    private CheckBox Male, Female;
    private Button Signup_btn;
    private TextView Login_link;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Int();
        initListener();


    }

    private void Int() {
        Email = (EditText) findViewById(R.id.email_signup);
        Password = (EditText) findViewById(R.id.password_signup);
        Username = (EditText) findViewById(R.id.username_signup);
        Phone = (EditText) findViewById(R.id.phone_signup);
        Male = (CheckBox) findViewById(R.id.male_checkbox);
        Female = (CheckBox) findViewById(R.id.female_checkbox);
        Signup_btn = (Button) findViewById(R.id.button_signup);
        Login_link = (TextView) findViewById(R.id.login_link);
        progressDialog = new ProgressDialog(this);
    }

    private void initListener() {
        Signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickSignup();
            }
        });
    }

    private void AddInformationDatabase(){
        DatabaseReference reference;
        FirebaseDatabase database;
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("user");

        String email = Email.getText().toString();
        String password = Password.getText().toString();
        String username = Username.getText().toString();
        String phone = Phone.getText().toString();

        User user = new User(email, username, password, phone, "");
        reference.push().setValue(user);



    }

    private void onclickSignup() {
        //Lấy dữ liệu
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        //Kiem tra du lieu
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }


        FirebaseAuth mDataUser_Signup = FirebaseAuth.getInstance();
        progressDialog.show();
        mDataUser_Signup.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                            startActivity(intent);
                            AddInformationDatabase();
                            finishAffinity();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }

                });
    }
}
