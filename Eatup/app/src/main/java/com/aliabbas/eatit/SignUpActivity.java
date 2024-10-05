package com.aliabbas.eatit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aliabbas.eatit.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUpActivity extends AppCompatActivity {

    MaterialEditText edtPhone, edtName, edtPass;
    Button btn_signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setBackgroundDrawableResource(R.drawable.background);

        edtPhone = (MaterialEditText)findViewById(R.id.edtPhone);
        edtName = (MaterialEditText)findViewById(R.id.edtName);
        edtPass = (MaterialEditText)findViewById(R.id.edtPass);
        btn_signUp = (Button) findViewById(R.id.btn_signUp);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference table_user = database.getReference("User");

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog mDialog = new ProgressDialog(SignUpActivity.this);
                mDialog.setMessage("Please wait......");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // check if already exist
                        if (!snapshot.child(edtPhone.getText().toString().trim()).exists())
                        {
                            Log.e("ifcondition:","Followed");
                            mDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Signup Successfully",Toast.LENGTH_LONG).show();
                            User user = new User (edtName.getText().toString(),edtPass.getText().toString());
                            table_user.child(edtPhone.getText().toString().trim()).setValue(user);
                            finish();
                        }
                        else if(snapshot.child(edtPhone.getText().toString().trim()).exists()){
                            Log.e("ifcondition:","UnFollow");
                            mDialog.dismiss();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }
}