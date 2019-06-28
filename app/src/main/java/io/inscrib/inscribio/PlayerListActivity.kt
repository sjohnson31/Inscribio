package io.inscrib.inscribio

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.player_list.*

fun startPlayerListActivity(context: Activity, numPlayers: Int, scoreCap: Int) {
    val intent = Intent(context, PlayerListActivity::class.java)
    intent.putExtra("NUM_PLAYERS", numPlayers)
    intent.putExtra("SCORE_CAP", scoreCap)
    context.startActivity(intent)
}

class PlayerListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            println(savedInstanceState)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val numPlayers = intent.getIntExtra("NUM_PLAYERS", 1)
        val scoreCap = intent.getIntExtra("SCORE_CAP", 1)

        PlayerList.ITEMS.clear()
        repeat(numPlayers) {
            PlayerList.ITEMS.add(PlayerList.Player(it, 0))
        }

        (player_list.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        player_list.adapter = SimpleItemRecyclerViewAdapter(PlayerList.ITEMS) { player, value ->
            player.score += value
            if (player.score >= scoreCap) {
                Snackbar.make(toolbar, "Somebody won! Who knows who did", Snackbar.LENGTH_LONG).show()
            }
            player_list.adapter?.notifyItemChanged(player.id)
        }

        fab.setOnClickListener {
            val intent = this.intent
            finish()
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}

class SimpleItemRecyclerViewAdapter(
    private val players: List<PlayerList.Player>,
    private val onScoreChangeClicked: (player: PlayerList.Player, amount: Int) -> Unit
) :
    RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.player_list_content, parent, false)
        return ViewHolder(view) { button, amount -> onScoreChangeClicked(button.tag as PlayerList.Player, amount) }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val player = players[position]
        holder.scoreText.text = player.score.toString()

        with(holder.itemView) {
            tag = player
        }
    }

    override fun getItemCount() = players.size

    inner class ViewHolder(view: View, val onScoreChangeClicked: (view: View, amount: Int) -> Unit) :
        RecyclerView.ViewHolder(view) {
        val scoreText: TextView = view.findViewById(R.id.player_score)

        init {
            view.findViewById<Button>(R.id.add_one_button).setOnClickListener { onScoreChangeClicked(view, 1) }
            view.findViewById<Button>(R.id.add_ten_button).setOnClickListener { onScoreChangeClicked(view, 10) }
            view.findViewById<Button>(R.id.subtract_one_button).setOnClickListener { onScoreChangeClicked(view, -1) }
            view.findViewById<Button>(R.id.subtract_ten_button).setOnClickListener { onScoreChangeClicked(view, -10) }
        }
    }
}
