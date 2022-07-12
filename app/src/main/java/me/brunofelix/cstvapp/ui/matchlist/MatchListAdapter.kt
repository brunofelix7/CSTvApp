package me.brunofelix.cstvapp.ui.matchlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.brunofelix.cstvapp.R
import me.brunofelix.cstvapp.data.api.response.MatchResponse
import me.brunofelix.cstvapp.databinding.ItemMatchBinding
import me.brunofelix.cstvapp.util.convertDate
import me.brunofelix.cstvapp.util.loadImage
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormatter
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter.ofPattern

class MatchListAdapter constructor(
    private val matches: List<MatchResponse>,
    private val listener: MatchListClickListener
): RecyclerView.Adapter<MatchListAdapter.MatchListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchListAdapter.MatchListViewHolder {
        val root = ItemMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchListViewHolder(root)
    }

    override fun onBindViewHolder(holder: MatchListAdapter.MatchListViewHolder, position: Int) {
        holder.setIsRecyclable(false)
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

        @SuppressLint("SetTextI18n")
        fun bind(match: MatchResponse) {

            // TODO: Binding Status / DateTime
            when (match.status) {
                "running" -> {
                    binding.layoutDateTime.setBackgroundResource(R.drawable.bg_rounded_live)
                    binding.textDateTime.text = "Live"
                }
                "finished" -> {
                    binding.layoutDateTime.setBackgroundResource(R.drawable.bg_rounded)
                    binding.textDateTime.text = "Ended"
                }
                "canceled" -> {
                    binding.layoutDateTime.setBackgroundResource(R.drawable.bg_rounded)
                    binding.textDateTime.text = "Canceled"
                }
                else ->  {
                    binding.layoutDateTime.setBackgroundResource(R.drawable.bg_rounded)
                    binding.textDateTime.text = convertDate(match.scheduledAt)
                }
            }

            // TODO: Binding Opponents
            if (match.opponents != null && match.opponents.isNotEmpty() && match.opponents.size > 1) {
                if (match.opponents[0].opponent?.imageUrl != null) {
                    loadImage(binding.imgTeamOne, match.opponents[0].opponent?.imageUrl!!)
                }
                if (match.opponents[1].opponent?.imageUrl != null) {
                    loadImage(binding.imgTeamTwo, match.opponents[1].opponent?.imageUrl!!)
                }
                binding.textTeamOne.text = match.opponents[0].opponent?.name
                binding.textTeamTwo.text = match.opponents[1].opponent?.name
            }

            // TODO: Binding League + Series
            if (match.league?.imageURL != null) {
                loadImage(binding.imgLeague, match.league.imageURL)
            }
            binding.textLeague.text = "${match.league?.name} - ${match.serie?.name}"
        }
    }
}
