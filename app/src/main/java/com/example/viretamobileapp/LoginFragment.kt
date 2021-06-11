package com.example.viretamobileapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.net.URL

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button: Button = view.findViewById(R.id.button_login) as Button
        button.setOnClickListener { viewButton: View ->
            var loginTextEdit = view.findViewById(R.id.text_login) as EditText
            var passwordTextEdit = view.findViewById(R.id.text_password) as EditText
            val login: String = loginTextEdit.text.toString()
            val password: String = passwordTextEdit.text.toString()
            if(password.toString().trim().equals("") or login.toString().trim().equals("")) {
                Toast.makeText(activity, "Fields are not valid", Toast.LENGTH_LONG).show()
            } else {
                val url: String = "http://10.0.2.2:3000/account?username=$login&password=$password"
                doAsync {
                    val apiResponse = URL(url).readText()
                    if(!JSONObject(apiResponse).getBoolean("isLoggedIn")) {
                        uiThread {
                            Toast.makeText(activity, "Unauthorized", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        uiThread {
                            val bundle = Bundle()
                            bundle.putString("userInfo", apiResponse)
                            view.findNavController().navigate(R.id.action_loginFragment_to_mainFragment, bundle)
                        }
                    }
                }

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
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}