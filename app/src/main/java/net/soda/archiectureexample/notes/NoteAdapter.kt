package net.soda.archiectureexample.notes

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.soda.archiectureexample.R
import net.soda.archiectureexample.data.Note

class NoteAdapter: ListAdapter<Note, NoteAdapter.NoteHolder>(DiffCallback()){
    var mListener: OnItemClickListener? = null

    class DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.title.equals(newItem.title) &&
                    oldItem.description.equals(newItem.description) &&
                    oldItem.priority.equals(newItem.priority)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return NoteHolder(itemView)
    }


    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val currentNote: Note = getItem(position)
        holder.textViewTitle.setText(currentNote.title)
        holder.textViewDescription.setText(currentNote.description)
        holder.textViewPriority.setText(currentNote.priority.toString())

        holder.mImageView.setOnClickListener {
            if (mListener != null && position != RecyclerView.NO_POSITION){
                mListener?.onItemClick(currentNote)
            }
        }
    }

    fun getNoteAt(position: Int): Note {
        return getItem(position)
    }

    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val mImageView = itemView
        val textViewTitle: TextView = itemView.findViewById(R.id.text_view_title)
        val textViewDescription: TextView = itemView.findViewById(R.id.text_view_description)
        val textViewPriority: TextView = itemView.findViewById(R.id.text_view_priority)
    }

    interface OnItemClickListener{
        fun onItemClick(note: Note)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }
}