package ru.yellowshark.favoritemovies.ui.home

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import ru.yellowshark.favoritemovies.domain.Repository
import ru.yellowshark.favoritemovies.domain.exception.NoResultsException
import ru.yellowshark.favoritemovies.domain.model.Movie
import ru.yellowshark.favoritemovies.ui.base.ViewState
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val _movies = MutableLiveData<ViewState<List<Movie>>>()
    val movies: LiveData<ViewState<List<Movie>>>
        get() = _movies

    init {
        getMovies()
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    fun getMovies() {
        disposables.add(repository.getMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _movies.postValue(ViewState.Loading()) }
            .delay(300, TimeUnit.MILLISECONDS)
            .subscribe({ onSuccess(it) }, { t -> onError(t) })
        )
    }

    fun searchMovies(query: String) {
        if (query.trim().isNotEmpty())
            disposables.add(
                repository.searchMovies(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { _movies.postValue(ViewState.Loading()) }
                    .delay(300, TimeUnit.MILLISECONDS)
                    .subscribe({ onSuccess(it) }, { t -> onError(t) })
            )
    }

    private fun onSuccess(movies: List<Movie>) {
        if (movies.isNotEmpty())
            _movies.postValue(ViewState.Success(movies))
        else
            throw NoResultsException()
    }

    private fun onError(t: Throwable) {
        _movies.postValue(ViewState.Error(t))
        t.printStackTrace()
    }
}