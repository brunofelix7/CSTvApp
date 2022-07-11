package me.brunofelix.cstvapp.ui.matchdetails

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.brunofelix.cstvapp.data.api.response.PlayerResponse
import me.brunofelix.cstvapp.databinding.ItemPlayersOneBinding
import me.brunofelix.cstvapp.util.loadImage

class MatchDetailsPlayersOneAdapter constructor(
    private val players: List<PlayerResponse>
): RecyclerView.Adapter<MatchDetailsPlayersOneAdapter.MatchDetailsPlayersOneViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchDetailsPlayersOneViewHolder {
        val root = ItemPlayersOneBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchDetailsPlayersOneViewHolder(root)
    }

    override fun onBindViewHolder(holder: MatchDetailsPlayersOneViewHolder, position: Int) {
        holder.bind(players[position])
    }

    override fun getItemCount(): Int = players.size

    /**
     * ViewHolder
     */
    inner class MatchDetailsPlayersOneViewHolder(
        val binding: ItemPlayersOneBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(player: PlayerResponse) {
            binding.textNickname.text = player.nickname
            binding.textFullname.text = player.firstName + player.lastName
            if (player.imageUrl != null) {
                loadImage(binding.imgPlayer, player.imageUrl)
            }
        }
    }
}
