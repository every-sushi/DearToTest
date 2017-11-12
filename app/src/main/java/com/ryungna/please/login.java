package com.ryungna.please;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.ryungna.please.model.UserModel;

public class login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    Button join;
    Button login;
    EditText email;
    EditText passwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        join = (Button)findViewById(R.id.join);
        login = (Button)findViewById(R.id.login);
        email = (EditText) findViewById(R.id.email);
        passwd = (EditText) findViewById(R.id.passwd);
        mAuth = FirebaseAuth.getInstance(); //firebaseAuh 개체의 공유 인스턴스 가져오기

        //로그인되어있는지 아닌지 확인하는거임
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Intent intent = new Intent(login.this, navigation.class);
                    finish(); //로그인이 되면 로그인액티비티가 사라지는거임
                    startActivity(intent);
                    Toast.makeText(login.this,"hello", Toast.LENGTH_SHORT);

                } else {
                    // User is signed out
                    Toast.makeText(login.this,"failed login", Toast.LENGTH_SHORT);

                }
                // ...
            }
        };


        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), passwd.getText().toString())
                        .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                UserModel userModel = new UserModel();
                                userModel.userName = email.getText().toString();

                                String uid = task.getResult().getUser().getUid();
                                FirebaseDatabase.getInstance().getReference().child("user").child(uid).setValue(userModel);

                                if (!task.isSuccessful()) {
                                    Toast.makeText(login.this, "회원가입 실패",
                                            Toast.LENGTH_SHORT).show();
                                }
                                else{//성공적으로 가입이 되면
                                    Toast.makeText(login.this, "회원가입 성공",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });




            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(email.getText().toString(),passwd.getText().toString());

            }
        });



    }
    private void loginUser(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) { //로그인실패
                            Toast.makeText(login.this, "로그인 실패",
                                    Toast.LENGTH_SHORT).show();


                            //        updateUI(null);
                        }
                        else {

                            //FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(login.this, "로그인 성공",
                                    Toast.LENGTH_SHORT).show();
                            //     updateUI(user);
                        }

                        // ...
                    }
                });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }










}
