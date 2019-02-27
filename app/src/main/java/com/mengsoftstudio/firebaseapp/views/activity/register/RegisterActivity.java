package com.mengsoftstudio.firebaseapp.views.activity.register;

import android.content.Intent;
import android.os.Bundle;
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
import com.mengsoftstudio.firebaseapp.contracts.RegisterContract;
import com.mengsoftstudio.firebaseapp.presenters.RegisterPresenter;
import com.mengsoftstudio.firebaseapp.views.activity.login.LoginActivity;

import javax.inject.Inject;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View,View.OnClickListener {

    private EditText fieldUsername, fieldEmail, fieldPassword;
    private TextView haveAccount, tv1, termsOfUse, tv2, tv3;
    private Button registerBtn, loginNowBtn;
    private ProgressBar progressBar;

    @Inject
    RegisterPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        initOnClick();
        setAnimate();

        DaggerRegisterComponent.builder()
                .registerModule(new RegisterModule(this))
                .firebaseComponent(((App) getApplicationContext()).getComponent())
                .build()
                .inject(this);

    }

    private void initView() {

        fieldUsername = findViewById(R.id.et_username);
        fieldEmail = findViewById(R.id.et_email);
        fieldPassword = findViewById(R.id.et_password);
        haveAccount = findViewById(R.id.tv_login);
        registerBtn = findViewById(R.id.btn_register);

        tv1 = findViewById(R.id.tv1);
        termsOfUse = findViewById(R.id.tv_terms_of_use);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        loginNowBtn = findViewById(R.id.btn_login);

        progressBar = findViewById(R.id.progressBar);

    }

    private void initOnClick() {

        haveAccount.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        loginNowBtn.setOnClickListener(this);

    }

    private void setAnimate() {

        fieldUsername.setAlpha(1f);
        fieldUsername.setTranslationY(0f);

        fieldEmail.setAlpha(1f);
        fieldEmail.setTranslationY(0f);

        fieldPassword.setAlpha(1f);
        fieldPassword.setTranslationY(0f);

        tv1.setAlpha(1f);
        tv1.setTranslationY(0f);

        termsOfUse.setAlpha(1f);
        termsOfUse.setTranslationY(0f);

        registerBtn.setAlpha(1f);
        registerBtn.setTranslationY(0f);

        haveAccount.setAlpha(1f);
        haveAccount.setTranslationY(0f);

        tv2.setAlpha(0f);
        tv2.setTranslationY(500f);

        tv3.setAlpha(0f);
        tv3.setTranslationY(500f);

        loginNowBtn.setAlpha(0f);
        loginNowBtn.setTranslationY(500f);

    }

    @Override
    public void onShowLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRegisterSuccess() {

        fieldUsername.animate().alpha(0f).translationY(500f).setDuration(600).start();
        fieldEmail.animate().alpha(0f).translationY(500f).setDuration(600).setStartDelay(300).start();
        fieldPassword.animate().alpha(0f).translationY(500f).setDuration(600).setStartDelay(600).start();

        tv1.animate().alpha(0f).translationY(500f).setDuration(600).setStartDelay(800).start();
        termsOfUse.animate().alpha(0f).translationY(500f).setDuration(600).setStartDelay(1200).start();

        registerBtn.animate().alpha(0f).translationY(500f).setDuration(600).setStartDelay(1500).start();
        haveAccount.animate().alpha(0f).translationY(500f).setDuration(600).setStartDelay(1800).start();

        tv2.animate().alpha(1f).translationY(0f).setDuration(600).setStartDelay(2100).start();
        tv3.animate().alpha(1f).translationY(0f).setDuration(600).setStartDelay(2400).start();
        loginNowBtn.animate().alpha(1f).translationY(0f).setDuration(600).setStartDelay(2700).start();

    }

    @Override
    public void onRegisterFailed(String message) {
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void invalidUsername() {
        fieldUsername.setError(getString(R.string.invalid_username));
    }

    @Override
    public void invalidEmail() {
        fieldEmail.setError(getString(R.string.invalid_email));
    }

    @Override
    public void invalidPassword() {
        fieldPassword.setError(getString(R.string.invalid_password));
    }

    private void toActivityLogin() {

        startActivity(
                new Intent(RegisterActivity.this, LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        );

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_login : {

                toActivityLogin();

                break;
            }

            case R.id.btn_register : {

                presenter.register(
                        fieldUsername.getText().toString(), fieldEmail.getText().toString(), fieldPassword.getText().toString());

                break;
            }

            case R.id.btn_login : {

                toActivityLogin();

                break;
            }

        }

    }

}
