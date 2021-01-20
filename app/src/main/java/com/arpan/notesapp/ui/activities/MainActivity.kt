package com.arpan.notesapp.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.arpan.notesapp.R
import com.arpan.notesapp.ui.viewmodels.NoteViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController : NavController

    private val noteViewModel : NoteViewModel by viewModels()

    private var isLinear = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("fuck", noteViewModel.toString())

        setSupportActionBar(main_toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController


        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.mainFragment) {
                main_toolbar.visibility = View.VISIBLE
            } else {
                main_toolbar.visibility = View.GONE
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        lifecycleScope.launchWhenCreated {
            noteViewModel.isLinear.collect {
                isLinear = it
                if(!isLinear) {
                    menu?.getItem(1)?.setIcon(R.drawable.ic_staggered)
                } else {
                    menu?.getItem(1)?.setIcon(R.drawable.ic_linear)
                }
            }
        }
        return true
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        when(item.itemId) {
            R.id.sign_out -> {
                Firebase.auth.signOut()
                val navOptions = NavOptions.Builder()
                        .setPopUpTo(R.id.mainFragment, true)
                        .build()
                navController.navigate(
                        R.id.action_mainFragment_to_loginFragment,
                        null,
                        navOptions
                )
            }
            R.id.view_type -> {
                noteViewModel.changeLayout()
            }
        }

        return true
    }

}