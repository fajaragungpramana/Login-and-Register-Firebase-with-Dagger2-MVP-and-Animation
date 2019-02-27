package com.mengsoftstudio.firebaseapp.views.activity.register;

import com.mengsoftstudio.firebaseapp.dis.component.FirebaseComponent;
import com.mengsoftstudio.firebaseapp.utils.CustomScope;

import dagger.Component;

@CustomScope
@Component(modules = {RegisterModule.class}, dependencies = {FirebaseComponent.class})
public interface RegisterComponent {
    void inject(RegisterActivity activity);
}
