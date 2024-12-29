package com.example.android_bootcamp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_bootcamp.databinding.FragmentMyOrdersBinding


class MyOrdersFragment : Fragment() {
    private var _binding: FragmentMyOrdersBinding? = null
    private val binding get() = _binding!!//binding
    private val statusAdapter by lazy {
        StatusAdapter(statusList) { status ->
            currentStatus = status
            filter(status)
        }
    }//status adapter where we also update current status
    private val orderAdapter by lazy {
        OrderListAdapter() { order ->
            details(order)
        }
    }
    private var currentStatus = StatusModel("Pending")//at first status is Pending
    private var statusList =
        listOf(StatusModel("Pending"), StatusModel("Cancelled"), StatusModel("Delivered"))
    private var orderList = listOf(
        OrderModel(1, "TN12345", 2, 49.99, StatusModel("Pending"), 1672444800000),
        OrderModel(2, "TN12346", 1, 19.99, StatusModel("Pending"), 1672531200000),
        OrderModel(3, "TN12347", 3, 74.97, StatusModel("Pending"), 1672617600000),
        OrderModel(4, "TN12348", 1, 29.99, StatusModel("Pending"), 1672704000000),
        OrderModel(5, "TN12349", 5, 124.95, StatusModel("Pending"), 1672790400000),
        OrderModel(6, "TN12350", 2, 39.98, StatusModel("Pending"), 1672876800000),
        OrderModel(7, "TN12351", 4, 99.96, StatusModel("Pending"), 1672963200000),
        OrderModel(8, "TN12352", 1, 9.99, StatusModel("Pending"), 1673049600000),
        OrderModel(9, "TN12353", 6, 179.94, StatusModel("Pending"), 1673136000000)
    )//random orders they are not from the figma and also they are all with Pending status


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyOrdersBinding.inflate(inflater, container, false)

        adapter()//calling adapters

        parentFragmentManager.setFragmentResultListener(
            "dt_order_status_key",
            viewLifecycleOwner
        ) { _, bundle ->
            val newStatus = bundle.getString("dt_order_status")
            val orderId = bundle.getInt("dt_order_id")
            updateOrderStatus(orderId, newStatus)
        }
        //we get the info from the parent fragment to properly update list
        return binding.root
    }


    private fun updateOrderStatus(orderId: Int, newStatus: String?) {
        val order = orderList.find { it.orderId == orderId }
        if (order != null && newStatus != null) {
            order.status =
                StatusModel(newStatus)//we update order with the id we got from the details fragment
        }
        filter(currentStatus) // Reapply the active filter
    }

    private fun adapter() {
        val recyclerViewStatus = binding.filterRecyclerView
        recyclerViewStatus.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerViewStatus.adapter = statusAdapter
        val recyclerViewOrder = binding.recyclerView
        recyclerViewOrder.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewOrder.adapter = orderAdapter
        filter(currentStatus)
        //here we initialized recycler views and also filtered list with currentStatus
    }

    private fun filter(status: StatusModel) {
        val filteredList =
            orderList.filter { it.status.status == status.status }//we filter the orders with the status that is chosen in the status recycler view
        orderAdapter.submitList(filteredList)
        //
    }


    private fun details(order: OrderModel) {
        val detailsFragment = Details.newInstance(
            orderId = order.orderId,
            trackingNumber = order.trackingNumber,
            quantity = order.qunatity,
            price = order.price,
            status = order.status.status,
            date = order.date
        )//we use fragments companion object to get details in the second fragment
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_id, detailsFragment)
            .addToBackStack(null)
            .commit()
        //when the user clicks on the details button we send him in the details fragment where the info is deisplayed


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}