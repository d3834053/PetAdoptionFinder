package uk.ac.tees.mad.d3834053.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uk.ac.tees.mad.d3834053.database.DatabaseRepository
import uk.ac.tees.mad.d3834053.database.DatabaseRepositoryImpl
import uk.ac.tees.mad.d3834053.database.ItemDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ItemDatabase::class.java, "pet_database").build()

    @Singleton
    @Provides
    fun databaseRepository(database: ItemDatabase): DatabaseRepository =
        DatabaseRepositoryImpl(database.getDao())
}