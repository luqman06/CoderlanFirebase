package coderland.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;

    private EditText edEmail, edPassword;
    private Button btnLogin, btnRegister;
    private LinearLayout layoutLoading;
    private TextView tvForgotPassword;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        // layoutLoading = findViewById(R.id.layoutLoading1);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }

    }

    private void doLogin() {
//        layoutLoading.setVisibility(View.VISIBLE);

        auth.signInWithEmailAndPassword(edEmail.getText().toString(), edPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            Toast.makeText(MainActivity.this, "Berhasil Login", Toast.LENGTH_LONG).show();
                            //  layoutLoading.setVisibility(View.GONE);

                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            finish();

                        } else {
                            try {
                                throw task.getException();

                            } catch (FirebaseAuthWeakPasswordException e) {
                                Toast.makeText(MainActivity.this, "password terlalu pendek", Toast.LENGTH_LONG).show();
                                edPassword.setError("Password pendek");
//                                 layoutLoading.setVisibility(View.GONE);
                            } catch (FirebaseAuthUserCollisionException e) {
                                Toast.makeText(MainActivity.this, "Email belum siap coba lain-nya", Toast.LENGTH_LONG).show();
                                // layoutLoading.setVisibility(View.GONE);
                            } catch (Exception e) {
                            }


                        }
                    }
                });

    }

    private void doRegister() {
        //  layoutLoading.setVisibility(View.VISIBLE);

        auth.createUserWithEmailAndPassword(edEmail.getText().toString(), edPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            Toast.makeText(MainActivity.this, "Regristasi berhasil", Toast.LENGTH_LONG).show();
                            //  layoutLoading.setVisibility(View.GONE);

                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            finish();

                        } else {
                            try {
                                throw task.getException();

                            } catch (FirebaseAuthWeakPasswordException e) {
                                Toast.makeText(MainActivity.this, "password terlalu pendek", Toast.LENGTH_LONG).show();
                                edPassword.setError("Password pendek");
                                // layoutLoading.setVisibility(View.GONE);
                            } catch (FirebaseAuthUserCollisionException e) {
                                Toast.makeText(MainActivity.this, "Email belum siap coba lain-nya", Toast.LENGTH_LONG).show();
                                // layoutLoading.setVisibility(View.GONE);
                            } catch (Exception e) {

                            }


                        }



                        }

                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:

                if(edEmail.getText().toString().length()==0||edPassword.getText().toString().length()==0){
                    //jika form Email belum di isi / masih kosong
                    Toast.makeText(MainActivity.this, "Mohon di isi semua", Toast.LENGTH_LONG).show();

                }else {

                    doLogin();

                }
                break;

            case R.id.btnRegister:
                if(edEmail.getText().toString().length()==0||edPassword.getText().toString().length()==0){
                    //jika form Email belum di isi / masih kosong
                    Toast.makeText(MainActivity.this, "Mohon di isi semua", Toast.LENGTH_LONG).show();

                }else {

                    doRegister();

                }
                break;
            case R.id.tvForgotPassword:
                startActivity(new Intent(MainActivity.this, ForgotPasswordActivity.class));
                break;
        }

    }
}
