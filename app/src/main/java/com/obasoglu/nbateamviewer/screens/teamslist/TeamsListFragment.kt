package com.obasoglu.nbateamviewer.screens.teamslist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.obasoglu.nbateamviewer.R
import com.obasoglu.nbateamviewer.databinding.FragmentTeamsListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * [TeamsListFragment] 's main responsibility is to show teams in a Recyclerview.
 * User can sort the teams by alphabetic, wins or losses
 * If items is clicked within Recyclerview, user will be directed to [TeamDetailsFragment]
 */
class TeamsListFragment : Fragment() {

    // Lazily inject
    private val viewModel: TeamsListViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentTeamsListBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        binding.viewmodel = viewModel

        binding.rvTeams.adapter = TeamsListAdapter(TeamsListAdapter.OnClickListener { team ->
            viewModel.displayTeamDetails(team)
        })

        viewModel.navigateToDetailFragment.observe(this, Observer { team ->
            if (team != null) {
                findNavController().navigate(
                    TeamsListFragmentDirections.actionTeamsListFragmentToTeamDetailFragment(
                        team
                    )
                )
                viewModel.displayTeamDetailsCompleted()
            }
        })

        setHasOptionsMenu(true)

        viewModel.retrieveTeams()

        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_filter, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.sortTeams(item.itemId)
        return true
    }

}
