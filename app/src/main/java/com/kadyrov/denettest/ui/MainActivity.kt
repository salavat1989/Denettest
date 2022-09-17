package com.kadyrov.denettest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kadyrov.denettest.R

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		savedInstanceState ?: setDefaultFragment()
	}

	private fun setDefaultFragment() {
		val fragment = NodeFragment.newInstance()
		supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer, fragment)
			.commit()
	}
}