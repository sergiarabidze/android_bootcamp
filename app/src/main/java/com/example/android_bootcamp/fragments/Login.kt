import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.datastore.preferences.core.edit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.android_bootcamp.R
import com.example.android_bootcamp.base.BaseFragment
import com.example.android_bootcamp.databinding.FragmentLoginBinding
import com.example.android_bootcamp.datastore.PreferenceKeys
import com.example.android_bootcamp.datastore.dataStore
import com.example.android_bootcamp.resource.Resource
import com.example.android_bootcamp.view_models.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class Login : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val loginViewModel: LoginViewModel by viewModels()
    override fun setUp() {
        super.setUp()
        parentFragmentManager.setFragmentResultListener(
            "REGISTER_RESULT",
            viewLifecycleOwner
        ) { _, bundle ->
            val email = bundle.getString("email")
            val password = bundle.getString("password")
            binding.emailId.setText(email)
            binding.passwordId.setText(password)
        }//when we get values from register fragment we put it in the edit texts


        viewLifecycleOwner.lifecycleScope.launch {
            loginViewModel.loginState.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.login_successful, resource.data.token),
                            Toast.LENGTH_SHORT
                        ).show()
                        if (binding.checkbox.isChecked) {
                            saveSession(resource.data.token, binding.emailId.text.toString())
                        }
                        navigateToHome(binding.emailId.text.toString())
                    }

                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    Resource.Idle -> {
                        //default
                    }
                }
            }
        }
        readSession()
    }

    override fun setListeners() {
        super.setListeners()
        with(binding) {
            registerId.setOnClickListener {
                findNavController().navigate(LoginDirections.actionLoginToRegister())
            }

            //validate fields and enable "Log In" button
            val textWatcher = object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val email = emailId.text.toString()
                    val password = passwordId.text.toString()
                    loginId.isEnabled = email.isValidEmail() && password.isNotEmpty()
                }

                override fun afterTextChanged(s: Editable?) {}
            }
            emailId.addTextChangedListener(textWatcher)
            passwordId.addTextChangedListener(textWatcher)
            loginId.setOnClickListener {
                val email = emailId.text.toString()
                val password = passwordId.text.toString()

                loginViewModel.loginUser(email, password)
            }
        }

    }

    private fun saveSession(token: String?, email: String?) {
        lifecycleScope.launch(Dispatchers.IO) {
            requireContext().dataStore.edit { preferences ->
                preferences[PreferenceKeys.TOKEN] = token ?: ""
                preferences[PreferenceKeys.EMAIL] = email ?: ""
            }
        }
    }

    private fun navigateToHome(email: String) {
        val action = LoginDirections.actionLoginToMainFragment(email)
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.login, true)
            .build()
        findNavController().navigate(action, navOptions)
    }

    private fun readSession() {
        lifecycleScope.launch {
            requireContext().dataStore.data
                .map { preferences ->
                    val token = preferences[PreferenceKeys.TOKEN]
                    val email = preferences[PreferenceKeys.EMAIL]
                    Pair(token, email) // Return the token and email as a pair
                }
                .collect { userData ->
                    val (savedToken, savedEmail) = userData
                    // If token and email are not null, navigate to Home
                    if (savedToken != null && savedEmail != null) {
                        navigateToHome(savedEmail)
                    }
                }
        }
    }

    private fun String.isValidEmail(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}