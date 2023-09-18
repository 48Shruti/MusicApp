package com.shruti.musicapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.NonDisposableHandle
import kotlinx.coroutines.NonDisposableHandle.parent
interface MusicClick{
    fun OnSongPlayClick(musicContent: MusicContent)

}
class MusicAdapter(var musicInterface: MusicClick) : RecyclerView.Adapter<MusicAdapter.ViewHolder>() {
    var item : ArrayList<MusicContent> = arrayListOf()
    class ViewHolder(var view : View) :RecyclerView.ViewHolder(view){
        var songname = view.findViewById<TextView>(R.id.tvmusicview)
        var titlename = view.findViewById<TextView>(R.id.songname)
        var  musicClick = view.findViewById<ImageButton>(R.id.btnplaymusic)
        var musicPlay =view.findViewById<ImageButton>(R.id.musicPlay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.music_list_view,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
      return item.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.songname.setText(item[position].title)
        holder.musicClick.setOnClickListener{
            musicInterface.OnSongPlayClick(item[position])
        }
    }
    fun updateList( musicContent: ArrayList<MusicContent>){
        this.item.clear()
        this.item.addAll(musicContent)
        notifyDataSetChanged()
    }
}


