package net.soda.archiectureexample.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_add.*
import net.soda.archiectureexample.R


class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar)

        numberPickerPriority.minValue = 1
        numberPickerPriority.maxValue = 10

        if(intent.hasExtra(IntentAdd.EXTRA_ID.name)){
            setTitle("Edit Note")
            editTextTitle.setText(intent.getStringExtra(IntentAdd.EXTRA_TITLE.name))
            editTextDescription.setText(intent.getStringExtra(IntentAdd.EXTRA_DESCRIPTION.name))
            numberPickerPriority.value = intent.getIntExtra(IntentAdd.EXTRA_PRIORITY.name, 1)

        } else{
            setTitle("Add Note")
        }

        toolbar.setNavigationIcon(R.drawable.ic_close)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.save_note -> {
                saveNote()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun saveNote() {
        val title: String = editTextTitle.text.toString()
        val description: String = editTextDescription.text.toString()
        val priority: Int = numberPickerPriority.value

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert title and description", Toast.LENGTH_SHORT).show()
            return
        }

        val id:Int = intent.getIntExtra(IntentAdd.EXTRA_ID.name, -1)

        val intent: Intent = Intent()
        intent.putExtra(IntentAdd.EXTRA_TITLE.name, title)
        intent.putExtra(IntentAdd.EXTRA_DESCRIPTION.name, description)
        intent.putExtra(IntentAdd.EXTRA_PRIORITY.name, priority)

        if(id != -1){
            intent.putExtra(IntentAdd.EXTRA_ID.name,id)
        }

        Log.d("id_add", id.toString())
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}
