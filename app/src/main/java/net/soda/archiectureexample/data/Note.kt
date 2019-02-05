package net.soda.archiectureexample.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    var title: String,
    var description: String,
    val priority: Int) {

    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}

