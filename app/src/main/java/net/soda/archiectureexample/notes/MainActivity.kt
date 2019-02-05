package net.soda.archiectureexample.notes

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import net.soda.archiectureexample.NoteViewModel
import net.soda.archiectureexample.R
import net.soda.archiectureexample.add.AddActivity
import net.soda.archiectureexample.add.IntentAdd
import net.soda.archiectureexample.data.Note

class MainActivity : AppCompatActivity(), NoteAdapter.OnItemClickListener {


    lateinit var noteViewModel: NoteViewModel

    val ADD_NOTE_REQUEST: Int = 1
    val EDIT_NOTE_REQUEST: Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_add_note.setOnClickListener {
            val intent: Intent = Intent(this, AddActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST)

        }

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        val adapter: NoteAdapter = NoteAdapter()
        recycler_view.adapter = adapter


        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        noteViewModel.getAllNotes().observe(this, Observer<List<Note>>{ notes ->
            adapter.submitList(notes!!)
        })

        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.adapterPosition))
                Toast.makeText(this@MainActivity, "Note deleted", Toast.LENGTH_SHORT).show()
            }

            override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
                return false
            }

        }).attachToRecyclerView(recycler_view)

        adapter.setOnItemClickListener(this@MainActivity)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val title: String? = data?.getStringExtra(IntentAdd.EXTRA_TITLE.name)
            val description: String? = data?.getStringExtra(IntentAdd.EXTRA_DESCRIPTION.name)
            val priority: Int? = data?.getIntExtra(IntentAdd.EXTRA_PRIORITY.name, 1)

            val note: Note =
                Note(title!!, description!!, priority!!)
            noteViewModel.insert(note)

            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()

        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == Activity.RESULT_OK){
            val id: Int? = data?.getIntExtra(IntentAdd.EXTRA_ID.name, -1)

            if(id == -1){
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show()
                return
            }

            val title: String? = data?.getStringExtra(IntentAdd.EXTRA_TITLE.name)
            val description: String? = data?.getStringExtra(IntentAdd.EXTRA_DESCRIPTION.name)
            val priority: Int? = data?.getIntExtra(IntentAdd.EXTRA_PRIORITY.name, 1)
            val note: Note =
                Note(title!!, description!!, priority!!)
            note.id = id!!

            noteViewModel.update(note)

            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()

        } else{
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = MenuInflater(this@MainActivity)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.delete_all_notes -> {
                noteViewModel.deleteAllNotes()
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show()
                return true
            }

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onItemClick(note: Note) {
        Log.d("id_main", note.id.toString())
        val intent: Intent = Intent(this@MainActivity, AddActivity::class.java)
        intent.putExtra(IntentAdd.EXTRA_ID.name, note.id)
        intent.putExtra(IntentAdd.EXTRA_TITLE.name, note.title)
        intent.putExtra(IntentAdd.EXTRA_DESCRIPTION.name, note.description)
        intent.putExtra(IntentAdd.EXTRA_PRIORITY.name, note.priority)
        startActivityForResult(intent, EDIT_NOTE_REQUEST)

    }

}
