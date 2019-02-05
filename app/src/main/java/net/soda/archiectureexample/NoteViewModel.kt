package net.soda.archiectureexample

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import net.soda.archiectureexample.data.Note
import net.soda.archiectureexample.data.source.NoteRepository

class NoteViewModel(application: Application): AndroidViewModel(application){
    val repository: NoteRepository =
        NoteRepository(application)
    val allNotesInData: LiveData<List<Note>> = repository.getAllNotes()

    fun insert(note: Note){
        repository.insert(note)
    }

    fun update(note: Note){
        repository.update(note)
    }

    fun delete(note: Note){
        repository.delete(note)
    }

    fun deleteAllNotes(){
        repository.deleteAllNotes()
    }

    fun getAllNotes(): LiveData<List<Note>>{
        return allNotesInData
    }

}