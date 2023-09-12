package com.customgit.presentation.user.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.customgit.R
import com.customgit.data.auth.app_auth.TokenStorage
import com.customgit.databinding.FragmentSearchBinding
import com.customgit.presentation.user.user_repositories_info.FragmentUserRepos

class FragmentSearch : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    override fun onResume() {
        super.onResume()
        checkUserName()
    }

    private fun setupViews() = with(binding) {

        Glide.with(requireContext())
            .load("https://www.dropbox.com/scl/fi/rbks2ickxtqyntdny16dm/gitCat.webp?rlkey=y5jthzid5n69iv9a6ubds0n6e&raw=1")
            .optionalCenterCrop()
            .into(gitLogo)

        //при нажатии на задний фон или крестик - фрагмент закроется
        clickInterceptor.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun checkUserName() = with(binding) {

        val usernameFromUser = gitUsername
        val searchButton = search

        // обработчик для кнопки Search
        searchButton.setOnClickListener {

            val enteredUsername = usernameFromUser.text.toString()

            when (enteredUsername.isEmpty()) {
                true -> Toast.makeText(requireContext(), "Enter name", Toast.LENGTH_SHORT).show()
                else -> {
                    progressBar.visibility - View.VISIBLE
                    searchButton.visibility = View.INVISIBLE

                    TokenStorage.username = enteredUsername

                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame, FragmentUserRepos())
                        .addToBackStack(null).commit()
                }
            }
        }
    }

    companion object {
        fun newInstance3() = FragmentSearch()
    }
}