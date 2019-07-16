package com.obasoglu.nbateamviewer.screens.teamslist

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.obasoglu.nbateamviewer.R
import com.obasoglu.nbateamviewer.databinding.FragmentTeamsListBinding
import com.obasoglu.nbateamviewer.utils.hasNetwork
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * [TeamsListFragment] 's main responsibility is to show teams in a Recyclerview.
 * User can sort the teams by alphabetic, wins or losses
 * If items is clicked within Recyclerview, user will be directed to TeamsPageFragment
 */
class TeamsListFragment : Fragment() {

    // Lazily inject
    private val viewModel: TeamsListViewModel by viewModel()

    // Lazily inject
    private val androidContext by inject<Context>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Set binding
        val binding = FragmentTeamsListBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        binding.viewmodel = viewModel

        binding.rvTeams.addItemDecoration(DividerItemDecoration(this.androidContext, LinearLayoutManager.VERTICAL))
        binding.rvTeams.adapter = TeamsListAdapter(TeamsListAdapter.OnClickListener { team ->
            viewModel.navigateTeamDetails(team)
        })

        // Navigate to TeamsPageFragment
        viewModel.navigateToDetailFragment.observe(this, Observer { team ->
            if (team != null) {
                findNavController().navigate(
                    TeamsListFragmentDirections.actionTeamsListFragmentToTeamDetailFragment(
                        team
                    )
                )
                viewModel.navigateTeamDetailsCompleted()
            }
        })

        viewModel.toastMessage.observe(this, Observer { message ->
            if (message != null) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                viewModel.displayToastCompleted()
            }
        })

        setHasOptionsMenu(true)

        retrieveTeams()

        return binding.root

    }

    /**
     * Retrieves teams. Also checks for connection and toasts message
     *
     */
    private fun retrieveTeams() {
        if (!hasNetwork(androidContext)!!) {
            viewModel.displayToast(R.string.noConnection)
        }

        // Data can be exist in cache. So, retrieve
        viewModel.retrieveTeams()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_filter, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.orderTeams(item.itemId)
        return true
    }

}
