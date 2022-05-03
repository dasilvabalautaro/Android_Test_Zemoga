package com.globalhiddenodds.androidtestzemoga.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.globalhiddenodds.androidtestzemoga.R
import com.globalhiddenodds.androidtestzemoga.ui.adapters.PostAdapter
import com.globalhiddenodds.androidtestzemoga.ui.fragments.PostPageFragment
import com.globalhiddenodds.androidtestzemoga.ui.utils.Utils
import com.globalhiddenodds.androidtestzemoga.ui.viewmodel.DeleteDatabaseViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var bottomNav: BottomNavigationView
    private val deleteDBViewModel: DeleteDatabaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_activity)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.zemoga_nav_host_fragment) as
                NavHostFragment? ?: return
        navController = host.navController
        appBarConfiguration = AppBarConfiguration(setOf(R.id.posts_dest), null)

        setupActionBar(navController, appBarConfiguration)
        setupBottomNavMenu(navController)

    }

    override fun onResume() {
        super.onResume()
        observerResultDeleteDatabase()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(
            findNavController(R.id.zemoga_nav_host_fragment)
        ) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.zemoga_nav_host_fragment)
            .navigateUp(appBarConfiguration)
    }

    private fun setupActionBar(
        navController: NavController,
        appBarConfiguration: AppBarConfiguration
    ) {
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun setupBottomNavMenu(navController: NavController) {
        bottomNav = findViewById(R.id.bottom_nav_view)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.del_all -> {
                    showDeleteAll(bottomNav)
                    return@setOnItemSelectedListener true
                }
                R.id.del_only -> {
                    showDeleteSingle(bottomNav)
                    return@setOnItemSelectedListener true
                }
                else -> {
                    return@setOnItemSelectedListener false
                }
            }
        }
    }

    private fun deleteCommentsSelected(){
        val list = PostAdapter.listSelect
        if(list.isNotEmpty()){
            list.forEach {
                deleteDBViewModel.deleteComment(it)
                PostAdapter.listSelect.remove(it)
            }
        }
    }

    private fun observerResultDeleteDatabase() {
        deleteDBViewModel.taskResult.observe(this,
            Observer { taskResult ->
                taskResult ?: return@Observer
                showMessageTask(taskResult)
            }
        )
    }

    private fun showMessageTask(msg: String) {
        Utils.notify(this, msg);
    }

    @SuppressLint("ShowToast")
    private fun showDeleteAll(view: View){
        val snackBar: Snackbar = Snackbar.make(view, "¿Are you sure?", Snackbar.LENGTH_LONG)
        snackBar.setAction("Delete", View.OnClickListener {
            if (Utils.databaseFileExists(this)){
                deleteDBViewModel.cleanDatabase()
                PostPageFragment.countRecords = 0
            }
        })
        snackBar.show()
    }

    @SuppressLint("ShowToast")
    private fun showDeleteSingle(view: View){
        val snackBar: Snackbar = Snackbar.make(view, "¿Are you sure?", Snackbar.LENGTH_LONG)
        snackBar.setAction("Delete", View.OnClickListener {
            deleteCommentsSelected()
        })
        snackBar.show()
    }
}