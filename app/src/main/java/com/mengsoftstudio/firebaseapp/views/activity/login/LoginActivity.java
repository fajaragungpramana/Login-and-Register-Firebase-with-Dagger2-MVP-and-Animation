package com.mengsoftstudio.firebaseapp.views.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mengsoftstudio.firebaseapp.App;
import com.mengsoftstudio.firebaseapp.R;
import com.mengsoftstudio.firebaseapp.contracts.LoginContract;
import com.mengsoftstudio.firebaseapp.presenters.LoginPresenter;
import com.mengsoftstudio.firebaseapp.views.activity.main.MainActivity;
import com.mengsoftstudio.firebaseapp.views.activity.register.RegisterActivity;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements LoginContract.View, View.OnClickListener {

    private EditText fieldEmail, fieldPassword;
    private TextView createAccount, forgotPassword, tv1, tv2;
    private Button loginBtn;
    private ProgressBar progressBar;

    private boolean exit = false;

    @Inject
    LoginPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initOnClick();
        setAnimate();

        DaggerLoginComponent.builder()
                .loginModule(new LoginModule(this))
                .firebaseComponent(((App) getApplicationContext()).getComponent())
                .build()
                .inject(this);

    }

    private void initView() {

        fieldEmail = findViewById(R.id.et_email);
        fieldPassword = findViewById(R.id.et_password);
        forgotPassword = findViewById(R.id.tv_forgot_password);
        createAccount = findViewById(R.id.tv_create_account);
        loginBtn = findViewById(R.id.btn_login);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        progressBar = findViewById(R.id.progressBar);

    }

    private void initOnClick() {

        createAccount.setOnClickListener(this);
        loginBtn.setOnClickListener(this);

    }

    private void setAnimate() {

        fieldEmail.setAlpha(1f);
        fieldEmail.setTranslationY(0f);

        fieldPassword.setAlpha(1f);
        fieldPassword.setTranslationY(0f);

        forgotPassword.setAlpha(1f);
        forgotPassword.setTranslationY(0f);

        loginBtn.setAlpha(1f);
        loginBtn.setTranslationY(0f);

        createAccount.setAlpha(1f);
        createAccount.setTranslationY(0f);

        tv1.setAlpha(0f);
        tv1.setTranslationY(500f);

        tv2.setAlpha(0f);
        tv2.setTranslationY(500f);

    }

    @Override
    public void onBackPressed() {

        if(exit) {
            finish();
        } else {
            exit = true;
            Toast.makeText(LoginActivity.this, "Press back button again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 1000L);

        }

    }

    @Override
    public void onShowLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoginSuccess() {

        fieldEmail.animate().alpha(0f).translationY(500f).setDuration(600).start();
        fieldPassword.animate().alpha(0f).translationY(500f).setDuration(600).setStartDelay(300).start();

        forgotPassword.animate().alpha(0f).translationY(500f).setDuration(600).setStartDelay(600).start();
        loginBtn.animate().alpha(0f).translationY(500f).setDuration(600).setStartDelay(900).start();
        createAccount.animate().alpha(0f).translationY(500f).setDuration(600).setStartDelay(1200).start();

        tv1.animate().alpha(1f).translationY(0f).setDuration(600).setStartDelay(1500).start();
        tv2.animate().alpha(1f).translationY(0f).setDuration(600).setStartDelay(1800).start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(
                        new Intent(LoginActivity.this, MainActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                );
                finish();

            }
        }, 3000L);

    }

    @Override
    public void onLoginFailed(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void invalidEmail() {
        fieldEmail.setError(getString(R.string.invalid_email));
    }

    @Override
    public void invalidPassword() {
        fieldPassword.setError(getString(R.string.invalid_password2));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_create_account : {

                startActivity(
                        new Intent(LoginActivity.this, RegisterActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                );

                break;
            }

            case R.id.btn_login : {

                presenter.login(fieldEmail.getText().toString(), fieldPassword.getText().toString());

                break;
            }

        }

    }

}
