package com.obasoglu.nbateamviewer.screens.teampage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.obasoglu.nbateamviewer.databinding.FragmentPlayersListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TeamsPageFragment : Fragment() {

    // Lazily inject
    private val viewModel: TeamsPageViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentPlayersListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        binding.rvTeams.adapter = PlayersListAdapter()

        val team = TeamsPageFragmentArgs.fromBundle(arguments!!).team
        viewModel.setTeam(team)

        return binding.root

    }

}
