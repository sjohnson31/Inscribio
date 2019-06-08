package io.inscrib.inscribio

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.player_list.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        player_list.adapter = SimpleItemRecyclerViewAdapter(PlayerList.ITEMS) { player, value ->
            player.score += value
            player_list.adapter?.notifyItemChanged(player.id - 1)
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
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
