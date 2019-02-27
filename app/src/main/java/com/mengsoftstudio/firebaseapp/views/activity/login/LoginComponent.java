package com.mengsoftstudio.firebaseapp.views.activity.login;

import com.mengsoftstudio.firebaseapp.dis.component.FirebaseComponent;
import com.mengsoftstudio.firebaseapp.utils.CustomScope;

import dagger.Component;

@CustomScope
@Component(modules = {LoginModule.class}, dependencies = {FirebaseComponent.class})
public interface LoginComponent {
    void inject(LoginActivity activity);
}
