package com.example.proyecto.views


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.proyecto.R
import com.example.proyecto.databinding.ActivityMainBinding
import com.example.proyecto.views.viewmodel.TareaViewModel
import com.example.proyecto.views.viewmodel.TareasViewModelFactory
import com.example.proyecto.views.viewmodel.UserPreferences


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private var modoNocturno: Boolean = false
    private var menu2: Menu? = null

    private val viewModel: TareaViewModel by viewModels {
        TareasViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       setSupportActionBar(binding.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)




        binding.bottomNavigation.setOnItemSelectedListener {
            bottomBarOnClickItem(it)
        }



        viewModel.preferencias.observe(this) {
            actualizarEstadoModoNocturno(it)
        }


    }

    private fun actualizarEstadoModoNocturno(it: UserPreferences) {
        modoNocturno = it.modoNocturno
        if (it.modoNocturno) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        invalidateOptionsMenu()


    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_arriba, menu)
        menu2 = menu
        return true
    }


    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val itemNightMode = menu?.findItem(R.id.modoNocturnoItem)

        if (modoNocturno) {
            itemNightMode?.setIcon(R.drawable.sunny)

            itemNightMode?.icon?.setTint(Color.WHITE)

        } else {
            itemNightMode?.setIcon(R.drawable.moon)
            itemNightMode?.icon?.setTint(Color.BLACK)
        }
        return true
    }


    fun bottomBarOnClickItem(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.modoNocturnoItem) {
            viewModel.cambiarModoNocturno()
            return true
        } else {
            val navController = findNavController(R.id.navHostFragment)
            return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
        }
    }




}
