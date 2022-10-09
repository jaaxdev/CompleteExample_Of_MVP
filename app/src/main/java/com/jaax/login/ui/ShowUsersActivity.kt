package com.jaax.login.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
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
import kotlin.math.abs

@AndroidEntryPoint
class ShowUsersActivity : AppCompatActivity(), ShowUsersMVP.View,
    NavigationView.OnNavigationItemSelectedListener,
    SearchView.OnQueryTextListener,
    AppBarLayout.OnOffsetChangedListener {

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

        initDrawerLayout()
        initAdapter()
        initRecyclerView()
        lifecycleScope.launch(Dispatchers.IO) {
            presenter.requestUsers()
        }
        recyclerScrollListener()
    }

    private fun initDrawerLayout() {
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
        binding.swipeLayout.appbar.addOnOffsetChangedListener(this)
        binding.swipeLayout.searchview.setOnQueryTextListener(this)
        binding.navigationView.setNavigationItemSelectedListener(this)
    }

    private fun initAdapter() {
        adapter = UserAdapter(
            loadListUsers = arrayListUser,
            onUserClickListener = {
                    position -> lifecycleScope.launch(Dispatchers.IO){
                presenter.userSelected(position)
            }
            })
    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = adapter
    }

    private fun recyclerScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(dy > 0){
                    val visibleItemCount = layoutManager.childCount
                    val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                    val totalItems = adapter.itemCount
                    val currentPage = presenter.getCurrentPage()

                    if(!presenter.getLoading() && currentPage < presenter.getTotalPages()) {
                        Log.i(TAG, "onScrolled: LOADABLE")
                        if(visibleItemCount + pastVisibleItem >= totalItems) {
                            Log.i(TAG, "onScrolled: LOADING DATA")
                            presenter.setLoading(false)

                            lifecycleScope.launch(Dispatchers.IO) {
                                presenter.requestUsers()
                            }
                            presenter.setCurrentPage(currentPage+1)
                        }
                    }
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun showUsers(list: List<User>) {
        arrayListUser.addAll(list)
        adapter.loadListUsers = arrayListUser
        adapter.notifyDataSetChanged()
        Log.i(TAG, "showUsers: ${adapter.loadListUsers}")
    }

    override fun visibleProgressbar() {
        binding.swipeLayout.progressbar.visibility = View.GONE
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

    override fun showNoDataFoundMessage() {
        Toast.makeText(this, getString(R.string.no_more_data), Toast.LENGTH_SHORT).show()
    }

    override fun updateInfo(user: UserInfo) {
        binding.swipeLayout.tvNameToolbar.text = "#"
                .plus(user.data.id.toString())
                .plus(" ")
                .plus(user.data.first_name)
                .plus(" ")
                .plus(user.data.last_name)
        binding.swipeLayout.tvEmailToolbar.text = user.data.email
        Glide
            .with(this)
            .load(user.data.avatar)
            .circleCrop()
            .into(binding.swipeLayout.ivAvatarToolbar)
        binding.swipeLayout.appbar.setExpanded(true)
        binding.swipeLayout.collapseToolbar.title = ""
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
        hideKeyboard()
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter.filter(newText)
        return false
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        //contraido
        if(abs(verticalOffset) - appBarLayout!!.totalScrollRange == 0) {
            binding.swipeLayout.ivAvatarToolbar.visibility = View.INVISIBLE
            binding.swipeLayout.collapseToolbar.title = getString(R.string.pick_user)
        } else {
            binding.swipeLayout.ivAvatarToolbar.visibility = View.VISIBLE
            if(binding.swipeLayout.tvNameToolbar.text.toString().isNotEmpty())
                binding.swipeLayout.collapseToolbar.title = ""
        }
    }
    private fun hideKeyboard() {
        val input = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        input.hideSoftInputFromWindow(binding.layoutShowusers.windowToken, 0)
    }
}