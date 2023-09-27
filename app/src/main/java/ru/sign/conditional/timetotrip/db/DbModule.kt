package ru.sign.conditional.timetotrip.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.sign.conditional.timetotrip.R
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DbModule {
    @Singleton
    @Provides
    fun provideDb(
        @ApplicationContext
        context: Context
    ) : AppDb = Room.databaseBuilder(
        context = context,
        klass = AppDb::class.java,
        name = context.getString(R.string.db_name)
    ).build()
}