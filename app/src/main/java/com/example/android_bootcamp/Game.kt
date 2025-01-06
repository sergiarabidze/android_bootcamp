package com.example.android_bootcamp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.android_bootcamp.databinding.FragmentGameBinding

private const val ARG_PARAM1 = "param1"

class Game : Fragment() {
    private var param1: String? = null
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy{ GameAdapter{ buttonModel -> clickListener(buttonModel) }}
    private val gameManager: MatrixGameManager by lazy{ MatrixGameManager(param1?.toInt() ?: 3)}
    private var isPlayer1Turn = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter()

    }
    private fun clickListener(buttonModel: ButtonModel) {
        val type = if (isPlayer1Turn) Type.FIRST else Type.SECOND
        val isWin = gameManager.updateCell(buttonModel.id, type)
        adapter.submitList(gameManager.getMatrix().flatten().mapIndexed { index, type -> ButtonModel(index, type) })
        if (isWin) {
            Toast.makeText(context, "Player $type wins!", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }
        if(gameManager.checkDraw()){
            Toast.makeText(context, "Draw!", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }
        isPlayer1Turn = !isPlayer1Turn

    }

    private fun adapter(){
        binding.recyclerId.adapter = adapter
        val itemRecyclerView = binding.recyclerId

        itemRecyclerView.layoutManager = GridLayoutManager(
            requireContext(),
            param1!!.toInt()
        )
        adapter.submitList(gameManager.getMatrix().flatten().mapIndexed { index, type -> ButtonModel(index, type) })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            Game().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}