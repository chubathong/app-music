package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.Service.DBHelper;

public class RegisterActivity extends AppCompatActivity {
    Button btnsignin,btnsignup;
    EditText txtusername,txtpassword,txtrepassword;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtusername=(EditText) findViewById(R.id.username);
        txtpassword=(EditText) findViewById(R.id.password);
        txtrepassword=(EditText) findViewById(R.id.repassword);
        btnsignin=(Button) findViewById(R.id.btnsignin);
        btnsignup=(Button) findViewById(R.id.btnsignup);
        DB = new DBHelper(this);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=txtusername.getText().toString();
                String pass=txtpassword.getText().toString();
                String repass=txtrepassword.getText().toString();

                if(user.equals("") || pass.equals("") || repass.equals("")){
                    Toast.makeText(RegisterActivity.this,"Vui lòng điền đầy đủ thông tin",Toast.LENGTH_SHORT).show();

                }else{
                    if(pass.equals(repass)){
                        Boolean checkuser=DB.checkUsername(user);
                        if(checkuser==false){
                            Boolean insert=DB.insertData(user,pass);
                            if(insert==true){
                                Toast.makeText(RegisterActivity.this,"Đăng ký thành công",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);

                            }else{
                                Toast.makeText(RegisterActivity.this,"Đăng ký thất bại",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(RegisterActivity.this,"Username này đã tồn tại! Vui lòng chọn 1 username khác",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this,"Password không khớp",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}