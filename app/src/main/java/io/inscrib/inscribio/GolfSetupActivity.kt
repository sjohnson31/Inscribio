package io.inscrib.inscribio

import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_golf_setup.*
import kotlinx.android.synthetic.main.content_golf_setup.*


class GolfSetupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_golf_setup)
        setSupportActionBar(toolbar)
        Int.MAX_VALUE

        val enableSubmitButtonIfFilledOut = { _: Editable ->
            submit_button.isEnabled = number_of_players.text.isNotEmpty() && score_cap.text.isNotEmpty()
        }
        number_of_players.afterTextChange(enableSubmitButtonIfFilledOut)
        score_cap.afterTextChange(enableSubmitButtonIfFilledOut)

        submit_button.setOnClickListener {
            startPlayerListActivity(
                this@GolfSetupActivity,
                numPlayers = number_of_players.text.toString().toInt(),
                scoreCap = score_cap.text.toString().toInt()
            )
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Email your golf game setup to a friend!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

}
