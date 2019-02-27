package com.mengsoftstudio.firebaseapp.presenters;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Patterns;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mengsoftstudio.firebaseapp.contracts.LoginContract;
import com.mengsoftstudio.firebaseapp.models.Account;
import com.mengsoftstudio.firebaseapp.models.Constant;

import java.util.Objects;

import javax.inject.Inject;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;
    private FirebaseAuth auth;

    @Inject
    public LoginPresenter(LoginContract.View view, FirebaseAuth auth) {
        this.view = view;
        this.auth = auth;
    }


    @Override
    public void login(String email, String password) {

        final Account account = new Account("", email, password);

        if (validateInput(account)) {

            try {

                view.onShowLoading();
                auth.signInWithEmailAndPassword(account.getEmail(), account.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {
                            Log.i(Constant.LOGIN_PRESENTER_LOG, "Login account successfully");

                            view.onLoginSuccess();

                        } else {
                            Log.i(Constant.LOGIN_PRESENTER_LOG, "Login account failed");

                            view.onLoginFailed(Objects.requireNonNull(task.getException()).getMessage());

                        }

                        view.onHideLoading();

                    }

                });

            } catch (Exception e) {
                Log.e(Constant.LOGIN_PRESENTER_LOG, "Exception: " + e.getMessage());
            }

        }

    }

    private boolean validateInput(Account account) {

        boolean valid = false;

        if(Patterns.EMAIL_ADDRESS.matcher(account.getEmail()).matches()) {
            valid = true;
            if(!account.getPassword().isEmpty()) {
                valid = true;
            } else {
                valid = false;
                view.invalidPassword();
            }
        } else {
            valid = false;
            view.invalidEmail();
        }

        return valid;
    }


}
