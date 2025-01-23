import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.android_bootcamp.R
import com.example.android_bootcamp.base.BaseFragment
import com.example.android_bootcamp.databinding.FragmentLoginBinding
import com.example.android_bootcamp.view_models.LoginViewModel

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
        }//when we get values from register fragment we put it in the edittexts

        val sharedPreferences =
            requireContext().getSharedPreferences("SessionPrefs", Context.MODE_PRIVATE)
        val savedToken = sharedPreferences.getString("token", null)
        val savedEmail = sharedPreferences.getString("email", null)
        //initializing sharedPreferences to save session

        if (savedToken != null && savedEmail != null) {
            navigateToHome(savedEmail)
        }//if its not null we go to home page and take email with us

        loginViewModel.loginResponse.observe(viewLifecycleOwner) { response ->
            val email = binding.emailId.text.toString()
            if (binding.checkbox.isChecked) {
                saveSession(response.token, email)
            }//we save session only if checkbox is checked

            navigateToHome(email)
        }

        loginViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }

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
        val sharedPreferences =
            requireContext().getSharedPreferences("SessionPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putString("token", token)
            .putString("email", email)
            .apply()
    }

    private fun navigateToHome(email: String) {
        val action = LoginDirections.actionLoginToMainFragment(email)
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.login, true)
            .build()
        findNavController().navigate(action, navOptions)
    }

    private fun String.isValidEmail(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}