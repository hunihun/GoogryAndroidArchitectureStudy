package com.tsdev.tsandroid.ui

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.*
import com.tsdev.tsandroid.R
import com.tsdev.tsandroid.base.BaseActivity
import com.tsdev.tsandroid.constant.Const
import com.tsdev.tsandroid.data.Item
import com.tsdev.tsandroid.data.MovieResponse
import com.tsdev.tsandroid.data.repository.NaverReopsitory
import com.tsdev.tsandroid.data.repository.NaverRepositoryImpl
import com.tsdev.tsandroid.databinding.ActivityMainBinding
import com.tsdev.tsandroid.ext.showToast
import com.tsdev.tsandroid.provider.ResourceProviderImpl
import com.tsdev.tsandroid.ui.adapter.MovieRecyclerAdapter
import com.tsdev.tsandroid.ui.observe.ObserverProviderImpl
import com.tsdev.tsandroid.ui.viewmodel.MainViewModel
import com.tsdev.tsandroid.util.BackKeyPressExt
import com.tsdev.tsandroid.util.MapConverter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {


    /***
     * Fragment 는 viewOwnerLifeCycle 적용 해야함 fragment 에는 view 사이클 fragment 사이클 두 가지가 존재.
     ***/

    override val binding: ActivityMainBinding by movieSetDataBinding(R.layout.activity_main, this)
    {
        it.also {
            it.lifecycleOwner = this
            it.activity = this
            it.viewModel = viewModel
        }
    }

    private val movieRecyclerAdapter: MovieRecyclerAdapter by lazy {
        MovieRecyclerAdapter()
    }
    private val movieMapConverter: MapConverter by lazy {
        MapConverter()
    }
    private val naverRepository: NaverReopsitory by lazy {
        NaverRepositoryImpl(movieMapConverter)
    }

    private val backKeyPressExt: BackKeyPressExt by lazy {
        BackKeyPressExt(rxJavaEvent, CompositeDisposable(), ::finish, ::showToast)
    }

    val hideKeyBoard: () -> Unit = {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            binding.searchImg.windowToken,
            0
        )
    }

    override val viewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return  MainViewModel(
                    naverRepository,
                    ResourceProviderImpl(this@MainActivity),
                    ObserverProviderImpl()
                ) as T
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.movieRecycler.run {
            adapter = movieRecyclerAdapter
        }

        /***
         * ?. 부분은 observe 중일 때  ?: 은 observe 를 끊기 때문에 빈 리스트 들어옴.
         ***/
        viewModel.onClearList = {
            movieRecyclerAdapter.run {
                clear()
                notifiedDataChange()
            }
        }
    }

    override fun onBackPressed() {
        backKeyPressExt.onPressedBackKey()
    }
}