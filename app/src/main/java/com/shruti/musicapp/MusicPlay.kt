package com.shruti.musicapp

import android.icu.text.CaseMap.Title
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.util.TimeUtils.formatDuration
import com.shruti.musicapp.databinding.FragmentMusicPlayBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MusicPlay.newInstance] factory method to
 * create an instance of this fragment.
 */
class MusicPlay : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var mainActivity: MainActivity
    lateinit var binding : FragmentMusicPlayBinding
    var songCount = 0
    lateinit var runnable: Runnable
    var songList : ArrayList<MusicContent> ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentMusicPlayBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(mainActivity.mediaPlayer.isPlaying){
            binding.songname.setText(mainActivity.musicContent?.title)
            mainActivity.mediaPlayer.pause()
        }else{
            mainActivity.mediaPlayer.start()
        }

        binding.tvtime.text = formatDuration(mainActivity.mediaPlayer.currentPosition.toLong())
        binding.tvend.text = formatDuration(mainActivity.mediaPlayer.duration.toLong())
        binding.seekbar.progress = 0
        seekSetup()
        binding.seekbar.max = mainActivity.mediaPlayer.duration
        binding.tvplay.setOnClickListener {
            if (mainActivity.mediaPlayer.isPlaying) {
                binding.songname.setText(mainActivity.musicContent?.title)
                mainActivity.mediaPlayer.pause()
            }
            else {
//                mainActivity.mediaPlayer.setDataSource(mainActivity, Uri.parse(mainActivity.musicContent?.storageLocation))
                mainActivity.mediaPlayer.start()
            }
        }
        binding.imgforw.setOnClickListener {
            binding.tvtime.text = formatDuration(mainActivity.mediaPlayer.currentPosition.toLong())
            binding.tvend.text = formatDuration(mainActivity.mediaPlayer.duration.toLong())
            binding.seekbar.progress = 0
            if (songCount  == mainActivity.musicList.size - 1){
                songCount = 0
                mainActivity.mediaPlayer.stop()
                mainActivity.mediaPlayer.reset()
                mainActivity.musicContent = mainActivity.musicList[songCount]
                mainActivity.mediaPlayer.setDataSource(mainActivity.musicList[songCount].storageLocation)
                mainActivity.mediaPlayer.prepare()
                mainActivity.mediaPlayer.start()
                binding.songname.setText(mainActivity.musicContent?.title)
                binding.tvtime.text = formatDuration(mainActivity.mediaPlayer.currentPosition.toLong())
                binding.tvend.text = formatDuration(mainActivity.mediaPlayer.duration.toLong())
                binding.seekbar.progress = 0
            }
            else {
                songCount++
                mainActivity.mediaPlayer.stop()
                mainActivity.mediaPlayer.reset()
                mainActivity.musicContent = mainActivity.musicList[songCount]
                mainActivity.mediaPlayer.setDataSource(mainActivity.musicList[songCount].storageLocation)
                mainActivity.mediaPlayer.prepare()
                mainActivity.mediaPlayer.start()
                binding.songname.setText(mainActivity.musicContent?.title)
                binding.tvtime.text = formatDuration(mainActivity.mediaPlayer.currentPosition.toLong())
                binding.tvend.text = formatDuration(mainActivity.mediaPlayer.duration.toLong())
                binding.seekbar.progress = 0
            }
        }
        binding.imgback.setOnClickListener {
            if (songCount == 0) {
                songCount = mainActivity.musicList.size - 1
                mainActivity.musicContent = mainActivity.musicList[songCount]
                mainActivity.mediaPlayer.stop()
                mainActivity.mediaPlayer.reset()
                mainActivity.mediaPlayer.setDataSource(mainActivity.musicList[songCount].storageLocation)
                mainActivity.mediaPlayer.prepare()
                mainActivity.mediaPlayer.start()
                binding.songname.setText(mainActivity.musicContent?.title)
                binding.tvtime.text = formatDuration(mainActivity.mediaPlayer.currentPosition.toLong())
                binding.tvend.text = formatDuration(mainActivity.mediaPlayer.duration.toLong())
                binding.seekbar.progress = 0
            }
            else{
                songCount--
                mainActivity.musicContent = mainActivity.musicList[songCount]
                mainActivity.mediaPlayer.stop()
                mainActivity.mediaPlayer.reset()
                mainActivity.mediaPlayer.setDataSource(mainActivity.musicList[songCount].storageLocation)
                mainActivity.mediaPlayer.prepare()
                mainActivity.mediaPlayer.start()
                binding.songname.setText(mainActivity.musicContent?.title)
                binding.tvtime.text = formatDuration(mainActivity.mediaPlayer.currentPosition.toLong())
                binding.tvend.text = formatDuration(mainActivity.mediaPlayer.duration.toLong())
                binding.seekbar.progress = 0
            }
        }
        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p2) mainActivity.mediaPlayer.seekTo(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) = Unit
            override fun onStopTrackingTouch(p0: SeekBar?) = Unit
        })
    }
        fun seekSetup(){
            runnable = Runnable {
                binding.tvtime.text = formatDuration(mainActivity.mediaPlayer.currentPosition.toLong())
                binding.seekbar.progress = mainActivity.mediaPlayer.currentPosition
                Handler(Looper.getMainLooper()).postDelayed(runnable,200)
            }
            Handler(Looper.getMainLooper()).postDelayed(runnable,0)
        }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MusicPlay.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MusicPlay().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}