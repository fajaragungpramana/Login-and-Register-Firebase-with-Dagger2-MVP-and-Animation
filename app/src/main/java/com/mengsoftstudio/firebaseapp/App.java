package com.mengsoftstudio.firebaseapp;

import android.app.Application;

import com.mengsoftstudio.firebaseapp.dis.component.DaggerFirebaseComponent;
import com.mengsoftstudio.firebaseapp.dis.component.FirebaseComponent;
import com.mengsoftstudio.firebaseapp.dis.module.AppModule;
import com.mengsoftstudio.firebaseapp.dis.module.FirebaseModule;

public class App extends Application {

    private FirebaseComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerFirebaseComponent.builder()
                .appModule(new AppModule(this))
                .firebaseModule(new FirebaseModule())
                .build();

    }

    public FirebaseComponent getComponent() {
        return component;
    }

}
