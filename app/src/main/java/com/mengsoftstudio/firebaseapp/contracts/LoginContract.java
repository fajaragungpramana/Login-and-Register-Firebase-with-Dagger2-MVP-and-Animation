package com.mengsoftstudio.firebaseapp.contracts;

public interface LoginContract {

    interface View {

        void onShowLoading();
        void onLoginSuccess();
        void onLoginFailed(String message);
        void onHideLoading();

        void invalidEmail();
        void invalidPassword();

    }

    interface Presenter {

        void login(String email, String password);

    }

}
