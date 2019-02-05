package net.soda.archiectureexample.data.source

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import net.soda.archiectureexample.data.Note

class NoteRepository(application: Application) {
    val database: NoteDatabase = NoteDatabase.getInstance(application)!!
    val noteDao = database.noteDao()
    val allNotesInDatabase = noteDao.getAllNotes()

    fun insert(note: Note){
        InsertNoteAsyncTask(noteDao).execute(note)
    }

    fun update(note: Note){
        UpdateNoteAsyncTask(noteDao).execute(note)
    }

    fun delete(note: Note){
        DeleteNoteAsyncTask(noteDao).execute(note)
    }

    fun deleteAllNotes(){
        DeleteAllNotesAsyncTask(noteDao).execute()
    }

    fun getAllNotes(): LiveData<List<Note>>{
        return allNotesInDatabase
    }

    class InsertNoteAsyncTask(noteDao: NoteDao): AsyncTask<Note, Void, Void>() {
       val noteDao: NoteDao = noteDao

        override fun doInBackground(vararg note: Note): Void? {
           noteDao.insert(note[0])
            return null
        }
    }

    class UpdateNoteAsyncTask(noteDao: NoteDao): AsyncTask<Note, Void, Void>() {
        val noteDao: NoteDao = noteDao

        override fun doInBackground(vararg note: Note): Void? {
            noteDao.update(note[0])
            return null
        }
    }

    class DeleteNoteAsyncTask(noteDao: NoteDao): AsyncTask<Note, Void, Void>() {
        val noteDao: NoteDao = noteDao

        override fun doInBackground(vararg note: Note): Void? {
            noteDao.delete(note[0])
            return null
        }
    }

    class DeleteAllNotesAsyncTask(noteDao: NoteDao): AsyncTask<Note, Void, Void>() {
        val noteDao: NoteDao = noteDao

        override fun doInBackground(vararg note: Note): Void? {
            noteDao.deleteAllNotes()
            return null
        }
    }
}