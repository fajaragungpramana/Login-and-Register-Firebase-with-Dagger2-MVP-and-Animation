package com.mengsoftstudio.firebaseapp.views.activity.register;

import com.mengsoftstudio.firebaseapp.contracts.RegisterContract;

import dagger.Module;
import dagger.Provides;

@Module
class RegisterModule {

    private RegisterContract.View view;

    RegisterModule(RegisterContract.View view) {
        this.view = view;
    }

    @Provides
    RegisterContract.View view() {
        return view;
    }

}
