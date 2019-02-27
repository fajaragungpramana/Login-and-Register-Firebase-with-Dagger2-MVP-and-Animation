package com.mengsoftstudio.firebaseapp.contracts;

public interface RegisterContract {

    interface View {

        void onShowLoading();
        void onRegisterSuccess();
        void onRegisterFailed(String message);
        void onHideLoading();

        void invalidUsername();
        void invalidEmail();
        void invalidPassword();

    }

    interface Presenter {

        void register(String username, String email, String password);

    }

}
