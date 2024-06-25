package com.example.crimeguardian.presentation.main.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.example.crimeguardian.R
import com.example.crimeguardian.databinding.FragmentMainBinding
import com.example.crimeguardian.presentation.adapter.DistrictAdapter
import com.example.crimeguardian.presentation.adapter.OffsetDecoration
import com.example.crimeguardian.presentation.model.model.main.District

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val districts = listOf(
        District(R.string.medeu_district, R.drawable.medeu_district, "https://krisha.kz/content/news/2016/nazvany-samye-kriminogennye-uchastki-medeuskogo-rayona"),
        District(R.string.almaly_district, R.drawable.almaly_district, "https://krisha.kz/kz/content/news/2019/opredeleny-samye-kriminal-nye-mesta-almalinskogo-rayona"),
        District(R.string.alatau_district, R.drawable.alatau_district, "https://www.inform.kz/ru/naibolee-opasnye-mesta-v-alatauskom-rayone-almaty-nazvala-policiya_a3556921"),
        District(R.string.auezov_district, R.drawable.auezov_district, "https://www.inform.kz/ru/kolichestvo-prestupleniy-v-samom-gustonaselennom-rayone-almaty-za-proshedshie-11-mesyacev-snizilos-s-35-do-19_a2219024"),
        District(R.string.bostandyk_district, R.drawable.bostandyk_district, "https://mail.kz/ru/news/kz-news/v-bostandykskom-raione-almaty-snizilos-kolichestvo-prestuplenii"),
        District(R.string.turksib_district, R.drawable.turksib_district, "https://tengrinews.kz/kazakhstan_news/nazvanyi-samyie-opasnyie-ulitsyi-turksibskogo-rayona-almatyi-376563/"),
    )

    private lateinit var adapter: DistrictAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDistrictAdapter()
        setupNavigation()
        setupSearchView()
        setupKeyboardListener()
        onItemClickListener()

    }

    private fun onItemClickListener() {
        adapter.itemClick = {
            district -> navigateToUrl(district.url)

        }
    }

    private fun navigateToUrl(url: String) {
        val action = MainFragmentDirections.actionMainFragmentToWebViewFragment(url)
        findNavController().navigate(action)
    }

    private fun setupKeyboardListener() {
        val rootView = binding.root
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.height
            val keypadHeight = screenHeight - rect.bottom

            if (keypadHeight > screenHeight * 0.15) {
                // Keyboard is opened
            } else {
                // Keyboard is closed
            }
        }
    }


    private fun setupDistrictAdapter() {
        adapter = DistrictAdapter(districts.toMutableList())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        val offsetDecoration = OffsetDecoration(start = 6, top = 10, end = 6, bottom = 10)
        binding.recyclerView.addItemDecoration(offsetDecoration)
    }

    private fun setupSearchView() {
        val searchView = binding.searchView // Ensure you are using the correct reference
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredDistricts = if (newText.isNullOrBlank()) {
                    districts
                } else {
                    districts.filter {
                        getString(it.name).contains(newText, ignoreCase = true)
                    }
                }
                adapter.updateDistricts(filteredDistricts)
                return true
            }
        })
    }

    private fun setupNavigation() {
        binding.map.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToIssuesFragment()
            findNavController().navigate(action)
        }

        binding.news.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToNewsFragment()
            findNavController().navigate(action)
        }

        binding.article.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToArticleFragment()
            findNavController().navigate(action)
        }

        binding.sos.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToProfileFragment()
            findNavController().navigate(action)
        }

        binding.instruction.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToInstructionFragment()
            findNavController().navigate(action)
        }

        binding.ai.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToAIFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
