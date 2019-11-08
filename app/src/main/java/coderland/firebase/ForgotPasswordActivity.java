 package coderland.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;

 public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

     private FirebaseAuth auth;

     private EditText edEmail;
     private Button btnRest;
     private Button tampilbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        auth = FirebaseAuth.getInstance();

        edEmail = findViewById(R.id.edEmail);
        btnRest = findViewById(R.id.btnReset);

        btnRest.setOnClickListener(this);
    }

    private void resetPassword(){
        auth.sendPasswordResetEmail(edEmail.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPasswordActivity.this, "Bukak email anda untuk megganti password", Toast.LENGTH_LONG).show();

                        }else {Toast.makeText(ForgotPasswordActivity.this, "Maaf ada kesalahn. \n COBA LAGI", Toast.LENGTH_LONG).show();

                        }
                    }
                });

    }
    @Override
     public void onClick(View v) {
        if(edEmail.getText().toString().length()==0){
            //jika form Email belum di isi / masih kosong
            Toast.makeText(ForgotPasswordActivity.this, "Mohon di isi semua", Toast.LENGTH_LONG).show();

        }else {
        if (v.getId() == R.id.btnReset) {
            resetPassword();
        }
        }
    }
}
