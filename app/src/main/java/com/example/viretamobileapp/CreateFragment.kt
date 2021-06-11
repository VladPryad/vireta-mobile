package com.example.viretamobileapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateFragment : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId: String = requireArguments().getString("userId").toString()

        val button_create: Button = view.findViewById(R.id.button_newpot_create) as Button
        button_create.setOnClickListener { createButton: View ->
            var nameTextEdit = view.findViewById(R.id.text_newpot_name) as EditText
            var descTextEdit = view.findViewById(R.id.text_newpot_description) as EditText
            var schemeTextEdit = view.findViewById(R.id.text_newpot_scheme) as EditText
            val name: String = nameTextEdit.text.toString()
            val desc: String = descTextEdit.text.toString()
            val scheme: String = schemeTextEdit.text.toString()
            if(name.toString().trim().equals("") or desc.toString().trim().equals("") or scheme.toString().trim().equals("")) {
                Toast.makeText(activity, "All fields must be filled", Toast.LENGTH_LONG).show()
            } else {
                //TODO:Add functionality
                Toast.makeText(activity, "Pot created", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreateFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}