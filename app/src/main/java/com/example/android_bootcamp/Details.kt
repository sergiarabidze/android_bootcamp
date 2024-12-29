package com.example.android_bootcamp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android_bootcamp.databinding.FragmentDetailsBinding

//argument keys
private const val ARG_ORDER_ID = "dt_order_id"
private const val ARG_TRACKING_NUMBER = "dt_tracking_number"
private const val ARG_QUANTITY = "dt_quantity"
private const val ARG_PRICE = "dt_price"
private const val ARG_STATUS = "dt_status"
private const val ARG_DATE = "dt_date"

class Details : Fragment() {

    private var orderId: Int? = null
    private var trackingNumber: String? = null
    private var quantity: Int? = null
    private var price: Double? = null
    private var status: String? = null
    private var date: Long? = null

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            orderId = it.getInt(ARG_ORDER_ID)
            trackingNumber = it.getString(ARG_TRACKING_NUMBER)
            quantity = it.getInt(ARG_QUANTITY)
            price = it.getDouble(ARG_PRICE)
            status = it.getString(ARG_STATUS)
            date = it.getLong(ARG_DATE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            dtOrderIdText.text = "Order ID: $orderId"
            dtTrackingNumberText.text = "Tracking Number: $trackingNumber"
            dtQuantityText.text = "Quantity: $quantity"
            dtPriceText.text = "Price: $$price"
            dtStatusText.text = "Status: $status"
            dtDateText.text =
                "Date: ${date?.formatTimestamp()}"//we show details in the details fragment
            //if the order on which details was clicked is Pending we should send back info about whether it was canceled or delivered
            if (status == "Pending") {
                dtCancelButton.setOnClickListener {
                    sendResult("Cancelled")
                }
                dtDeliveredButton.setOnClickListener {
                    sendResult("Delivered")
                }
            } else {//also we dont show buttons cause user cant change status of the canceled or delivered order
                dtCancelButton.visibility = View.GONE
                dtDeliveredButton.visibility = View.GONE
            }
        }

    }


    private fun sendResult(newStatus: String) {
        parentFragmentManager.setFragmentResult(
            "dt_order_status_key",
            Bundle().apply {
                putString("dt_order_status", newStatus)
                putInt("dt_order_id", orderId ?: 0)
            }
        )//we send back info to myorders fragment about new status and id we changed status of
        parentFragmentManager.popBackStack()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(
            orderId: Int,
            trackingNumber: String,
            quantity: Int,
            price: Double,
            status: String,
            date: Long
        ) = Details().apply {
            arguments = Bundle().apply {
                putInt(ARG_ORDER_ID, orderId)
                putString(ARG_TRACKING_NUMBER, trackingNumber)
                putInt(ARG_QUANTITY, quantity)
                putDouble(ARG_PRICE, price)
                putString(ARG_STATUS, status)
                putLong(ARG_DATE, date)
            }
        }
    }
}
