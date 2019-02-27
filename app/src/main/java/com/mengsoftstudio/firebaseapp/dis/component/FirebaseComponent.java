package com.mengsoftstudio.firebaseapp.dis.component;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mengsoftstudio.firebaseapp.dis.module.AppModule;
import com.mengsoftstudio.firebaseapp.dis.module.FirebaseModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, FirebaseModule.class})
public interface FirebaseComponent {

    FirebaseAuth firebaseAuth();
    FirebaseFirestore firebaseFirestore();

}
