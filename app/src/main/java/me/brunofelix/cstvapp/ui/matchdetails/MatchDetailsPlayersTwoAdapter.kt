package me.brunofelix.cstvapp.ui.matchdetails

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.brunofelix.cstvapp.data.api.response.PlayerResponse
import me.brunofelix.cstvapp.databinding.ItemPlayersOneBinding
import me.brunofelix.cstvapp.databinding.ItemPlayersTwoBinding
import me.brunofelix.cstvapp.util.loadImage

class MatchDetailsPlayersTwoAdapter constructor(
    private val players: List<PlayerResponse>
): RecyclerView.Adapter<MatchDetailsPlayersTwoAdapter.MatchDetailsPlayersTwoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchDetailsPlayersTwoViewHolder {
        val root = ItemPlayersTwoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchDetailsPlayersTwoViewHolder(root)
    }

    override fun onBindViewHolder(holder: MatchDetailsPlayersTwoViewHolder, position: Int) {
        holder.bind(players[position])
    }

    override fun getItemCount(): Int = players.size

    /**
     * ViewHolder
     */
    inner class MatchDetailsPlayersTwoViewHolder(
        val binding: ItemPlayersTwoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(player: PlayerResponse) {
            binding.textNickname.text = player.nickname
            binding.textFullname.text = "${player.firstName} ${player.lastName}"
            if (player.imageUrl != null) {
                loadImage(binding.imgPlayer, player.imageUrl)
            }
        }
    }
}
