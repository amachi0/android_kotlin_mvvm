package net.soda.archiectureexample.data.source

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.os.AsyncTask
import net.soda.archiectureexample.data.Note

@Database(entities = arrayOf(Note::class), version = 3)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        private var INSTANCE: NoteDatabase? = null

        private var roomCallback = object: RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(INSTANCE!!)
                    .execute()

            }
        }

        fun getInstance(context: Context): NoteDatabase? {
            if (INSTANCE == null) {
                synchronized(NoteDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NoteDatabase::class.java, "NoteDatabase.db")
                            .fallbackToDestructiveMigration().addCallback(roomCallback)
                            .build()
                }
            }
            return INSTANCE
        }
    }


    class PopulateDbAsyncTask(db: NoteDatabase): AsyncTask<Void, Void, Void>() {
        val noteDao: NoteDao = db.noteDao()

        override fun doInBackground(vararg params: Void?): Void? {
            noteDao.insert(Note("Title1", "Description1", 1))
            noteDao.insert(Note("Title2", "Description2", 2))
            noteDao.insert(Note("Title3", "Description3", 3))
            return null
        }

    }



}