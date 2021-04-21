package ru.yellowshark.favoritemovies.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import ru.yellowshark.favoritemovies.domain.Repository
import ru.yellowshark.favoritemovies.domain.model.Movie
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    init {
        getMovies()
    }

    fun getMovies() {
        disposables.add(repository.getMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _movies.postValue(it)
                    Log.d("TAG", "getMovies: $it")
                },
                { t ->
                    t.printStackTrace()
                }
            )
        )
    }
}