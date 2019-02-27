package com.mengsoftstudio.firebaseapp.presenters;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Patterns;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mengsoftstudio.firebaseapp.contracts.RegisterContract;
import com.mengsoftstudio.firebaseapp.models.Account;
import com.mengsoftstudio.firebaseapp.models.Constant;

import java.util.HashMap;
import java.util.Objects;

import javax.inject.Inject;

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View view;
    private FirebaseAuth auth;
    private FirebaseFirestore dbRef;

    @Inject
    public RegisterPresenter(RegisterContract.View view, FirebaseAuth auth, FirebaseFirestore dbRef) {
        this.view = view;
        this.auth = auth;
        this.dbRef = dbRef;
    }

    @Override
    public void register(String username, String email, String password) {

        final Account account = new Account(username, email, password);

        if(validateInput(account)) {

            try {

                view.onShowLoading();
                auth.createUserWithEmailAndPassword(account.getEmail(), account.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {
                            Log.i(Constant.REGISTER_PRESENTER_LOG, "Register account successfully");

                            final HashMap<String, String> map = new HashMap<>();
                            map.put("username", account.getUsername());

                            String uid = Objects.requireNonNull(auth.getCurrentUser()).getUid();

                            dbRef.collection("users").document(uid).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()) {
                                        Log.d(Constant.REGISTER_PRESENTER_LOG, "Put username to FirebaseFirestore successfully");
                                    } else {
                                        Log.d(Constant.REGISTER_PRESENTER_LOG, "Put username to FirebaseFirestore failed");
                                    }

                                }
                            });

                            view.onRegisterSuccess();

                        } else {
                            Log.i(Constant.REGISTER_PRESENTER_LOG, "Register account Failed");

                            view.onRegisterFailed(Objects.requireNonNull(task.getException()).getMessage());

                        }

                        view.onHideLoading();

                    }
                });

            } catch (Exception e) {
                Log.e(Constant.REGISTER_PRESENTER_LOG, "Exception: " + e.getMessage());
            }

        }

    }

    private boolean validateInput(Account account) {

        boolean valid = false;

        if(!account.getUsername().isEmpty() && account.getUsername().length() > 4) {
            valid = true;
            if(Patterns.EMAIL_ADDRESS.matcher(account.getEmail()).matches()) {
                valid = true;
                if(!account.getPassword().isEmpty() && account.getPassword().length() > 6) {
                    valid = true;
                } else {
                    valid = false;
                    view.invalidPassword();
                }
            } else {
                valid = false;
                view.invalidEmail();
            }
        } else {
            valid = false;
            view.invalidUsername();
        }

        return valid;
    }

}
