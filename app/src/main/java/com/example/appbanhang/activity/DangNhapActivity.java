package com.example.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.retrofit.ApiBanHang;
import com.example.appbanhang.retrofit.Retrofitclient;
import com.example.appbanhang.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangNhapActivity extends AppCompatActivity {
    TextView txtdangki,txtresetpass;
    EditText email,pass;
    AppCompatButton btndangnhap;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    boolean isLogin= false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        initView();
        initControl();
    }

    private void initControl() {
        txtdangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),DangKiActivity.class);
                startActivity(intent);
            }
        });
        txtresetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),ResetPassActivity.class);
                startActivity(intent);
            }
        });
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // kiem tra va add du lieu vao
                String str_email = email.getText().toString().trim();
                String str_pass = pass.getText().toString().trim();
                if(TextUtils.isEmpty(str_email)){
                    Toast.makeText(getApplicationContext(), "B???n ch??a nh???p Email", Toast.LENGTH_SHORT).show();
                }else  if(TextUtils.isEmpty(str_pass)){
                    Toast.makeText(getApplicationContext(), "B???n ch??a nh???p Password", Toast.LENGTH_SHORT).show();
                }else {
                    // svae dang nhap nguoi dung khi nguoi dung vao lai trang
                    Paper.book().write("email", str_email);
                    Paper.book().write("pass", str_pass);
                    dangNhap(str_email, str_pass);
                }
            }
        });
    }


    private void dangNhap(String email,String pass) {
        compositeDisposable.add(apiBanHang.dangnhap(email,pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if(userModel.isSuccess()){
                                isLogin=true;
                                Paper.book().write("islogin", isLogin);
                                Utils.user_current =userModel.getResult().get(0);//phan tu dau tien cua list
                                // luu lai thong tin nguoi dung
                                Paper.book().write("user", userModel.getResult().get(0));
                                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        },throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }
    private void initView() {
        Paper.init(this);
        apiBanHang = Retrofitclient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        txtdangki= findViewById(R.id.txtdangki);
        email= findViewById(R.id.email);
        pass= findViewById(R.id.pass);
        txtresetpass= findViewById(R.id.txtresetpass);
        btndangnhap= findViewById(R.id.btndangnhap);


        // read data paper
        if(Paper.book().read("email")!=null && Paper.book().read("pass")!=null){
            email.setText(Paper.book().read("email"));
            pass.setText(Paper.book().read("pass"));
           /* if(Paper.book().read("islogin") != null){
                boolean flag=Paper.book().read("islogin");
                if(flag){

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                           // dangNhap(Paper.book().read("email"),Paper.book().read("pass"));
                        }
                    },1000);
                }
            }*/
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.user_current.getEmail()!=null && Utils.user_current.getPass() !=null){
            email.setText(Utils.user_current.getEmail());
            pass.setText(Utils.user_current.getPass());
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}