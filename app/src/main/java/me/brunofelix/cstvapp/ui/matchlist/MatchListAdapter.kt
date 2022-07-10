package me.brunofelix.cstvapp.ui.matchlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.brunofelix.cstvapp.data.api.response.MatchResponse
import me.brunofelix.cstvapp.databinding.ItemMatchBinding
import me.brunofelix.cstvapp.util.loadImage

class MatchListAdapter constructor(
    private val matches: List<MatchResponse>,
    private val listener: MatchListClickListener
): RecyclerView.Adapter<MatchListAdapter.MatchListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchListAdapter.MatchListViewHolder {
        val root = ItemMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchListViewHolder(root)
    }

    override fun onBindViewHolder(holder: MatchListAdapter.MatchListViewHolder, position: Int) {
        holder.bind(matches[position])
        holder.binding.cardItem.setOnClickListener {
            listener.onItemClick(matches[position])
        }
    }

    override fun getItemCount(): Int = matches.size

    /**
     * ViewHolder
     */
    inner class MatchListViewHolder(
        val binding: ItemMatchBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(match: MatchResponse) {
            binding.textDateTime.text = match.scheduledAt

            if (match.opponents != null && match.opponents.isNotEmpty() && match.opponents.size > 1) {
                if (match.opponents[0].opponent?.imageUrl != null) {
                    loadImage(binding.imgTeamOne, match.opponents[0].opponent?.imageUrl!!)
                }
                if (match.opponents[1].opponent?.imageUrl != null) {
                    loadImage(binding.imgTeamTwo, match.opponents[1].opponent?.imageUrl!!)
                }
            }
        }
    }
}
