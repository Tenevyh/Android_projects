package com.tenevyh.android.scales4money.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tenevyh.android.scales4money.Balance
import com.tenevyh.android.scales4money.viewmodel.BalanceViewModel
import com.tenevyh.android.scales4money.R
import com.tenevyh.android.scales4money.adapter.BalanceHistoryAdapter
import kotlinx.android.synthetic.main.balance_fragment.*
import java.text.SimpleDateFormat

class BalanceFragment: Fragment(R.layout.balance_fragment) {

    private val dateFormat = SimpleDateFormat("d MMM yyyy, HH:mm")
    private var adapter: BalanceHistoryAdapter? = BalanceHistoryAdapter(emptyList())
    private val balanceViewModel: BalanceViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        historyRV.layoutManager = LinearLayoutManager(context)
        balanceViewModel.balanceSheetsLD.observe(
            viewLifecycleOwner,
            Observer { history ->
                history?.let {
                    if(history.isNotEmpty()) itemBalance.text = history.last().number+" руб."
                    updateUI(history)
                }
            }
        )
    }

    override fun onStart() {
        super.onStart()
        buttonAdd.setOnClickListener {
            SelectionFragment().show(parentFragmentManager, "Selection fragment")
        }
    }

    private fun updateUI(balanceSheets: List<Balance>){
        adapter = BalanceHistoryAdapter(balanceSheets)
        historyRV.adapter = adapter
        if(historyRV.adapter?.itemCount!! > 0) {
            historyRV.smoothScrollToPosition(historyRV.adapter?.itemCount!! - 1)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = BalanceFragment()
    }
}