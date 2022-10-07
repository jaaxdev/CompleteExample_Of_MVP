package com.jaax.login.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaax.login.data.UserAdapter
import com.jaax.login.data.model.User
import com.jaax.login.data.network.UserService
import com.jaax.login.data.showusers.ShowUsersMVP
import com.jaax.login.data.showusers.ShowUsersPresenter
import com.jaax.login.databinding.ActivityShowUsersBinding
import com.jaax.login.util.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ShowUsersActivity: AppCompatActivity(), ShowUsersMVP.View {

    private lateinit var binding: ActivityShowUsersBinding
    private lateinit var adapter: UserAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var presenter: ShowUsersMVP.Presenter

    @Inject
    lateinit var service: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = ShowUsersPresenter(this)

        adapter = UserAdapter(onUserClickListener = { position -> presenter.userSelected(position) })

        layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = layoutManager

        binding.recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch(Dispatchers.IO) {
            presenter.requestUsers()
        }
    }

    override fun onResume() {
        super.onResume()

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                    val totalChildCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()

                if(dy > 0){
                    
                    if (!presenter.getLoadable() && totalItemCount <= (lastVisibleItem+totalChildCount)) {
                        presenter.setLoadable(true)

                        lifecycleScope.launch(Dispatchers.IO) {
                            presenter.requestUsers()
                        }
                    }
                }
            }
        })
    }

    override fun showUsers(list: List<User>) {
        adapter.addAllPokemon(list)
    }

    override fun searchViewVisible() {
        binding.searchview.visibility = View.VISIBLE
    }
}