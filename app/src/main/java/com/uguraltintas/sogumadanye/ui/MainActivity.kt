package com.uguraltintas.sogumadanye.ui

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.uguraltintas.sogumadanye.R
import com.uguraltintas.sogumadanye.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(getThemeOnSharedPref())
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

        binding.bottomNavigation.setupWithNavController(navHostFragment.navController)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.foodListFragment,
                R.id.cartFragment,
                R.id.favouriteFragment,
                R.id.orderFragment,
                R.id.profileFragment
            )
        )
        binding.toolbar.setupWithNavController(navHostFragment.navController, appBarConfiguration)

        val badge = binding.bottomNavigation.getOrCreateBadge(R.id.cartFragment)
        badge.maxCharacterCount = 2

        viewModel.cartCount.observe(this) {
            when {
                it <= 0 -> {
                    badge.isVisible = false
                    badge.clearNumber()
                }
                0 < it -> {
                    badge.isVisible = true
                    badge.number = it
                }
            }
        }

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.title = ""
            when (destination.id) {
                R.id.splashFragment, R.id.registerFragment, R.id.loginFragment ->
                    binding.isNavigationVisible = false
                R.id.foodListFragment -> {
                    binding.isNavigationVisible = true
                    viewModel.getCartCount()
                }
                else -> binding.isNavigationVisible = true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.set_theme_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                showColorPickerDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getThemeOnSharedPref(): Int {
        val preferences = getSharedPref()
        return when (preferences.getString("theme", "red")) {
            "green" -> R.style.Theme_Green
            "red" -> R.style.Theme_Red
            "blue" -> R.style.Theme_Blue
            "pink" -> R.style.Theme_Pink
            "purple" -> R.style.Theme_Purple
            "deep_purple" -> R.style.Theme_Deep_Purple
            "indigo" -> R.style.Theme_Indigo
            "light_blue" -> R.style.Theme_Light_Blue
            "cyan" -> R.style.Theme_Cyan
            "teal" -> R.style.Theme_Teal
            "light_green" -> R.style.Theme_Light_Green
            "lime" -> R.style.Theme_Lime
            "yellow" -> R.style.Theme_Yellow
            "amber" -> R.style.Theme_Amber
            "orange" -> R.style.Theme_Orange
            "deep_orange" -> R.style.Theme_Deep_Orange
            "brown" -> R.style.Theme_Brown
            "grey" -> R.style.Theme_Grey
            "blue_grey" -> R.style.Theme_Blue_Grey
            else -> R.style.Theme_Red
        }
    }

    private fun showColorPickerDialog() {
        MaterialColorPickerDialog
            .Builder(this)
            .setTitle(resources.getString(R.string.pick_theme))
            .setColorShape(ColorShape.SQAURE)
            .setDefaultColor(R.color.green)
            .setColorRes(resources.getIntArray(R.array.colors))
            .setColorListener { color, _ ->

                val preferences = getSharedPref()
                val editor = preferences.edit()
                when (color) {
                    getColorID(R.color.green) -> {
                        editor.putString("theme", "green")
                        recreate()
                    }
                    getColorID(R.color.blue) -> {
                        editor.putString("theme", "blue")
                        recreate()
                    }
                    getColorID(R.color.red) -> {
                        editor.putString("theme", "red")
                        recreate()
                    }
                    getColorID(R.color.pink) -> {
                        editor.putString("theme", "pink")
                        recreate()
                    }
                    getColorID(R.color.purple) -> {
                        editor.putString("theme", "purple")
                        recreate()
                    }
                    getColorID(R.color.deep_purple) -> {
                        editor.putString("theme", "deep_purple")
                        recreate()
                    }
                    getColorID(R.color.indigo) -> {
                        editor.putString("theme", "indigo")
                        recreate()
                    }
                    getColorID(R.color.light_blue) -> {
                        editor.putString("theme", "light_blue")
                        recreate()
                    }
                    getColorID(R.color.cyan) -> {
                        editor.putString("theme", "cyan")
                        recreate()
                    }
                    getColorID(R.color.teal) -> {
                        editor.putString("theme", "teal")
                        recreate()
                    }
                    getColorID(R.color.light_green) -> {
                        editor.putString("theme", "light_green")
                        recreate()
                    }
                    getColorID(R.color.lime) -> {
                        editor.putString("theme", "lime")
                        recreate()
                    }
                    getColorID(R.color.yellow) -> {
                        editor.putString("theme", "yellow")
                        recreate()
                    }
                    getColorID(R.color.amber) -> {
                        editor.putString("theme", "amber")
                        recreate()
                    }
                    getColorID(R.color.orange) -> {
                        editor.putString("theme", "orange")
                        recreate()
                    }
                    getColorID(R.color.deep_orange) -> {
                        editor.putString("theme", "deep_orange")
                        recreate()
                    }
                    getColorID(R.color.brown) -> {
                        editor.putString("theme", "brown")
                        recreate()
                    }
                    getColorID(R.color.grey) -> {
                        editor.putString("theme", "grey")
                        recreate()
                    }
                    getColorID(R.color.blue_grey) -> {
                        editor.putString("theme", "blue_grey")
                        recreate()
                    }
                }
                editor.apply()
            }
            .show()
    }

    private fun getColorID(@ColorRes color: Int) =
        ResourcesCompat.getColor(resources, color, null)

    private fun getSharedPref() = getSharedPreferences(
        "com.uguraltintas.sogumadanye",
        Context.MODE_PRIVATE
    )
}