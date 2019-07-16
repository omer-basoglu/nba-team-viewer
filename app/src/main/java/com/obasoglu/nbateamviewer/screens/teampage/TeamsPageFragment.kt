package com.obasoglu.nbateamviewer.screens.teampage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.obasoglu.nbateamviewer.databinding.FragmentPlayersListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * TeamsPageFragment, shows team details and all it's players with their details
 */
class TeamsPageFragment : Fragment() {

    // Lazily inject
    private val viewModel: TeamsPageViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentPlayersListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        binding.rvTeams.addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
        binding.rvTeams.adapter = PlayersListAdapter()

        //Retrieve team from SageArgs and set via databinding - Binding Adapter
        val team = TeamsPageFragmentArgs.fromBundle(arguments!!).team
        viewModel.setTeam(team)

        return binding.root

    }

}
