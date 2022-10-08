package com.jaax.login.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.jaax.login.R
import com.jaax.login.data.UserAdapter
import com.jaax.login.data.model.User
import com.jaax.login.data.model.UserInfo
import com.jaax.login.data.showusers.ShowUsersMVP
import com.jaax.login.databinding.ActivityShowUsersBinding
import com.jaax.login.util.Utils.Companion.TAG
import com.jaax.login.util.Utils.Companion.TAG_ERROR_MESSAGE
import com.jaax.login.util.Utils.Companion.TAG_INVALID_MESSAGE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ShowUsersActivity : AppCompatActivity(), ShowUsersMVP.View,
    NavigationView.OnNavigationItemSelectedListener,
    SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityShowUsersBinding
    private lateinit var adapter: UserAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private val arrayListUser = ArrayList<User>(0)

    @Inject
    lateinit var presenter: ShowUsersMVP.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recyclerView)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            toolbar,
            R.string.email,
            R.string.email
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.swipeLayout.searchview.setOnQueryTextListener(this)
        binding.navigationView.setNavigationItemSelectedListener(this)

        adapter = UserAdapter(
            listUsers = arrayListUser,
            onUserClickListener = {
                position -> lifecycleScope.launch(Dispatchers.IO){
                    presenter.userSelected(position)
                }
        })

        layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch(Dispatchers.IO) {
            presenter.requestUsers()
        }
    }

    override fun onResume() {
        super.onResume()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalChildCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()

                if (dy > 0) {
                    if (presenter.getLoadable() && totalItemCount <= (lastVisibleItem + totalChildCount)) {
                        presenter.setLoadable(false)

                        lifecycleScope.launch(Dispatchers.IO) {
                            presenter.requestUsers()
                        }
                    }
                }
            }
        })
    }

    override fun showUsers(list: List<User>) {
        adapter.addAllUsers(list)
    }

    override fun searchViewVisible() {
        binding.swipeLayout.searchview.visibility = View.VISIBLE
    }

    override fun setTitleItemEmail(email: String) {
        binding.navigationView.menu.findItem(R.id.item_email).title = email
    }

    override fun exit() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        this.finish()
    }

    override fun showUnsuccessfulMessage() {
        UnsuccessfulMessage().show(supportFragmentManager, TAG_INVALID_MESSAGE)
    }

    override fun showErrorMessage() {
        ErrorMessage().show(supportFragmentManager, TAG_ERROR_MESSAGE)
    }

    override fun updateInfo(user: UserInfo) {
        ErrorMessage().show(supportFragmentManager, "invalid_message")
        Log.i(TAG, "updateInfo: ${user.data}")
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_email -> {
                Toast.makeText(this, item.title.toString(), Toast.LENGTH_SHORT).show()
            }
            R.id.item_logout -> {
                lifecycleScope.launch(Dispatchers.IO) {
                    presenter.logOut()
                }
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        lifecycleScope.launch(Dispatchers.IO) { presenter.setItemEmail() }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText!!.isNotEmpty()) fileterdList(newText) else adapter.addAllUsers(arrayListUser)
        return false
    }

    private fun fileterdList(newText: String?) {
        val filteredList = ArrayList<User>(0)

        for(user in arrayListUser) {
            if(user.id.toString().contains(newText!!.lowercase())
                || user.email.lowercase().contains(newText.lowercase())
                || user.first_name.lowercase().contains(newText.lowercase())
                || user.last_name.lowercase().contains(newText.lowercase()))
                filteredList.add(user)
        }
        if(filteredList.isNotEmpty()) {
            adapter.filteredList(filteredList)
        }
    }
}