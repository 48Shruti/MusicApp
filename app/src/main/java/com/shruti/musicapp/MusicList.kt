package com.shruti.musicapp

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shruti.musicapp.databinding.FragmentMusicListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MusicList.newInstance] factory method to
 * create an instance of this fragment.
 */
class MusicList : Fragment(), MusicClick {
    lateinit var musicAdapter: MusicAdapter
    lateinit var mainActivity: MainActivity
    lateinit var binding: FragmentMusicListBinding
    lateinit var viewModel: MusicViewModel
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
       binding = FragmentMusicListBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        musicAdapter = MusicAdapter(this)
        binding.musiclist.adapter = musicAdapter
        binding.musiclist.layoutManager = LinearLayoutManager(mainActivity)
        viewModel = ViewModelProvider(mainActivity)[MusicViewModel::class.java]
        viewModel.musicContentList.observe(mainActivity){
            musicAdapter.updateList(it)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MusicList.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MusicList().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun OnSongPlayClick(musicContent: MusicContent) {
        mainActivity.musicContent = musicContent
        if(mainActivity.mediaPlayer.isPlaying){
            mainActivity.mediaPlayer.stop()
            mainActivity.mediaPlayer.reset()
        }else{
            mainActivity.mediaPlayer.setDataSource(mainActivity, Uri.parse(musicContent.storageLocation))
            mainActivity.mediaPlayer.prepare()
            mainActivity.mediaPlayer.start()
        }
    }


}