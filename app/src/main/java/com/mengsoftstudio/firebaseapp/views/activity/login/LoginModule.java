package com.mengsoftstudio.firebaseapp.views.activity.login;

import com.mengsoftstudio.firebaseapp.contracts.LoginContract;

import dagger.Module;
import dagger.Provides;

@Module
class LoginModule {

    private LoginContract.View view;

    LoginModule(LoginContract.View view) {
        this.view = view;
    }

    @Provides
    LoginContract.View view() { return view; }

}
