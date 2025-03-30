package com.example.android_bootcamp.presentation.features.accounts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_bootcamp.databinding.AccountItemBinding
import com.example.android_bootcamp.presentation.features.model.AccountPresentation

class AccountsAdapter(
    private val onItemClick: (AccountPresentation) -> Unit
) : ListAdapter<AccountPresentation, AccountsAdapter.AccountViewHolder>(AccountDiffCallback()) {

    inner class AccountViewHolder(private val binding: AccountItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(account: AccountPresentation) {
            binding.tvAccountName.text = account.accountName
            binding.tvAccountNumber.text = account.accountNumber
            binding.tvBalance.text = "Balance: ${account.balance} ${account.valuteType}"
            binding.tvCardType.text = "Card: ${account.cardType}"

            binding.root.setOnClickListener {
                onItemClick(account)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val binding = AccountItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AccountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class AccountDiffCallback : DiffUtil.ItemCallback<AccountPresentation>() {
    override fun areItemsTheSame(oldItem: AccountPresentation, newItem: AccountPresentation): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AccountPresentation, newItem: AccountPresentation): Boolean {
        return oldItem == newItem
    }
}
