package com.cruzadasapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText mNombre,mEmail,mPassword, mPhone;
    private Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mNombre      = findViewById(R.id.txtname);
        mEmail       = findViewById(R.id.txtemail);
        mPassword    = findViewById(R.id.txtpassword);
        mPhone       = findViewById(R.id.txtphone);
        mRegisterBtn = findViewById(R.id.LoginBtn);
        mLoginBtn    = findViewById(R.id.reglogin);
        fAuth        = FirebaseAuth.getInstance();
        progressBar  = findViewById(R.id.progressBar);
        fStore       = FirebaseFirestore.getInstance();


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String nombre = mNombre.getText().toString();
                String phone = mPhone.getText().toString();

                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Ingrese el correo");
                    return;}
                if (TextUtils.isEmpty(nombre)){
                    mNombre.setError("Ingrese su nombre");
                    return;}
                if (TextUtils.isEmpty(phone)){
                    mPhone.setError("Ingrese un número de teléfono");
                    return;}
                if (TextUtils.isEmpty(password)){
                    mPassword.setError("Ingrese la contraseña");
                    return;}
                if (password.length() < 6){
                    mPassword.setError("La contraseña debe tener al menos 6 caracteres");}

                progressBar.setVisibility(View.VISIBLE);

                //Registro del usuario en FireBase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Register.this, "Usuario creado " + nombre, Toast.LENGTH_SHORT).show();
                            DocumentReference documentReference = fStore.collection("Usuarios").document(email);
                            Map<String, Object> user = new HashMap<>();
                            user.put("Correo",email);
                            user.put("Nombre",nombre);
                            user.put("Teléfono",phone);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: Usuario creado para " + nombre);

                                }
                            });
                            startActivity(new Intent(getApplicationContext(),Login.class));

                        }else {
                            Toast.makeText(Register.this, "Error en el registro", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}