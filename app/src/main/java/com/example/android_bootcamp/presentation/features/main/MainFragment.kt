package com.example.android_bootcamp.presentation.features.main



import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.android_bootcamp.R
import com.example.android_bootcamp.presentation.common.BaseFragment
import com.example.android_bootcamp.databinding.FragmentMainFragmentBinding
import com.example.android_bootcamp.domain.helper.CardType
import com.example.android_bootcamp.domain.helper.Valute
import com.example.android_bootcamp.presentation.exstension.setTextIfDifferent
import com.example.android_bootcamp.presentation.features.model.AccountPresentation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainFragmentBinding>(FragmentMainFragmentBinding::inflate) {
     private val viewModel: MainViewModel by viewModels()

     override fun setUp() {
          super.setUp()
          setObservers()
          parentFragmentManager.setFragmentResultListener("account_request", this) { _, bundle ->
               val selectedAccount = bundle.getParcelable<AccountPresentation>("selected_account")
               selectedAccount?.let {
                    viewModel.onEvent(MainEvent.AddFromAccount(it))
               }
          }

          parentFragmentManager.setFragmentResultListener("to_account_result", this) { _, bundle ->
               val selectedAccount = bundle.getParcelable<AccountPresentation>("account")
               selectedAccount?.let {
                    viewModel.onEvent(MainEvent.AddToAccount(it))
               }
          }
     }

     override fun setListeners() {
          binding.fromItemId.setOnClickListener {
               val action = MainFragmentDirections.actionPaymentToAccountBottomSheet()
               findNavController().navigate(action)
          }
          binding.toFormId.setOnClickListener {
               val action = MainFragmentDirections.actionPaymentToToAccountBottomSheet()
               findNavController().navigate(action)
          }

          binding.sellFirstId.doAfterTextChanged { text ->
               viewModel.onEvent(MainEvent.UpdateSellFirst(text.toString()))
          }

          binding.sellSecondId.doAfterTextChanged { text ->
               viewModel.onEvent(MainEvent.UpdateSellSecond(text.toString()))
          }
     }


     private fun setObservers() {
          viewLifecycleOwner.lifecycleScope.launch {
               viewModel.state.collectLatest {
                    binding.fromInfoId.rootView.isVisible = it.fromAccount != null
                    if (it.fromAccount != null) {
                         when (it.fromAccount.valuteType) {
                              Valute.GEL -> {
                                   binding.tvFromAmount.text =
                                        getString(R.string.gel, it.fromAccount.balance.toString())
                              }

                              Valute.USD -> {
                                   binding.tvFromAmount.text =
                                        getString(R.string.dolar, it.fromAccount.balance.toString())
                              }

                              Valute.EUR -> {
                                   binding.tvFromAmount.text =
                                        getString(R.string.euro, it.fromAccount.balance.toString())
                              }
                         }

                         binding.tvFromCardNumber.text = getString(
                              R.string.cardwithstars,
                              it.fromAccount.accountNumber.takeLast(4)
                         )

                         binding.ivFromVisaLogo.setImageResource(if (it.fromAccount.cardType == CardType.VISA) R.drawable.visa_icon else R.drawable.mastercard_icon)

                    } else {
                         binding.tvFromAmount.text = getString(R.string.select_card)
                         binding.tvFromCardNumber.text = ""
                         binding.ivFromVisaLogo.setImageResource(0)
                    }

                    if (it.toAccount != null) {
                         when (it.toAccount.valuteType) {
                              Valute.GEL -> {
                                   binding.tvToAmount.text =
                                        getString(R.string.gel, it.toAccount.balance.toString())
                              }

                              Valute.USD -> {
                                   binding.tvToAmount.text =
                                        getString(R.string.dolar, it.toAccount.balance.toString())
                              }

                              Valute.EUR -> {
                                   binding.tvToAmount.text =
                                        getString(R.string.euro, it.toAccount.balance.toString())
                              }
                         }

                         binding.tvToCardNumber.text = getString(
                              R.string.cardwithstars,
                              it.toAccount.accountNumber.takeLast(4)
                         )

                         binding.ivToVisaLogo.setImageResource(if (it.toAccount.cardType == CardType.VISA) R.drawable.visa_icon else R.drawable.mastercard_icon)

                    } else {
                         binding.tvToAmount.text = getString(R.string.select_card)
                         binding.tvToCardNumber.text = ""
                         binding.ivToVisaLogo.setImageResource(0)
                    }
                    if (it.fromAccount != null && it.toAccount != null) {
                         binding.sellSecondId.isVisible =
                              it.fromAccount.valuteType == it.toAccount.valuteType
                    }
                    binding.fromInfoId.rootView.isVisible = it.fromAccount != null
                    binding.sellSecondId.isVisible =
                         it.fromAccount?.valuteType != it.toAccount?.valuteType
                    binding.textInputLayoutSellSecond.isVisible =
                         it.fromAccount?.valuteType != it.toAccount?.valuteType

                    binding.sellFirstId.setTextIfDifferent(it.firstSell?.toString() ?: "")
                    binding.sellSecondId.setTextIfDifferent(it.secondSell?.toString() ?: "")

                    binding.textInputLayoutSellFirst.suffixText =
                         it.fromAccount?.valuteType.toString()
                    binding.textInputLayoutSellSecond.suffixText = when {
                         it.fromAccount != null && it.toAccount != null && it.fromAccount.valuteType != it.toAccount.valuteType ->
                              it.toAccount.valuteType.toString()

                         else -> it.fromAccount?.valuteType.toString()
                    }

               }
          }
     }
}
