package com.example.viretamobileapp

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.net.URL


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var listView = requireView().findViewById(com.example.viretamobileapp.R.id.potList) as ListView


        val props: String = arguments?.getString("userInfo")?: "{}"
        val userId: String = JSONObject(props).getString("id")

        val button_add: Button = view.findViewById(com.example.viretamobileapp.R.id.button_addpot) as Button
        button_add.setOnClickListener { viewButton: View ->
            val bundle = Bundle()
            bundle.putString("userId", userId)
            view.findNavController().navigate(com.example.viretamobileapp.R.id.action_mainFragment_to_createFragment, bundle)
        }

        val url: String = "http://10.0.2.2:3001/pot/user/${userId}"
        doAsync {
            val apiResponse = URL(url).readText()
            val potList = JSONObject(apiResponse).getJSONArray("pots")
            val potArray = ArrayList<String>()
            if (potList != null) {
                for (i in 0 until potList.length()) {
                    potArray.add(potList.getString(i))
                }
            }
            uiThread {
                val adapter: ArrayAdapter<String> = ArrayAdapter(
                    requireActivity(),
                    R.layout.simple_list_item_1, potArray.map{ el -> "${JSONObject(el).getString("name")} (id: ${JSONObject(el).getString("id")})" }
                )
                listView.adapter = adapter

                listView.setOnItemClickListener(OnItemClickListener { parent, v, position, id ->
                    val selectedPotId: String = JSONObject(potArray[position]).getString("id");
                    val bundle = Bundle()
                    bundle.putString("potId", selectedPotId)
                    view.findNavController().navigate(com.example.viretamobileapp.R.id.action_mainFragment_to_potFragment, bundle)
                })
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.example.viretamobileapp.R.layout.fragment_main, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}