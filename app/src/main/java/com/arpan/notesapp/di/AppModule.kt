package com.arpan.notesapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import com.arpan.notesapp.firebase.FirebaseUtils
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideOnBoardPreference(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.createDataStore(
            name = "ViewType@Notes"
        )
    }

    @Provides
    @Singleton
    fun provideNotesCollection() = Firebase.firestore.collection("notes")

    @Provides
    @Singleton
    fun providesFirebaseUtils(collection: CollectionReference) = FirebaseUtils(collection)

}