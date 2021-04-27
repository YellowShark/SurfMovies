package ru.yellowshark.favoritemovies.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
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

    companion object {
        private const val DELAY = 400L
        const val MIN_PROGRESS = 0
        const val MIN_LIST_POSITION = 0
    }

    private val disposables = CompositeDisposable()
    private val _movies = MutableLiveData<ViewState<List<Movie>>>()
    val movies: LiveData<ViewState<List<Movie>>>
        get() = _movies
    var currentVisiblePosition = MIN_LIST_POSITION
    var progressStatus = MIN_PROGRESS

    init {
        getMovies()
    }

    override fun onCleared() {
        disposables.clear()
        currentVisiblePosition = MIN_LIST_POSITION
        progressStatus = MIN_PROGRESS
        super.onCleared()
    }

    fun getMovies() {
        disposables.add(repository.getMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onLoading() }
            .delay(DELAY, TimeUnit.MILLISECONDS)
            .subscribe({ onSuccess(it) }, { t -> onError(t) })
        )
    }

    fun searchMovies(query: String) {
        if (query.trim().isNotEmpty())
            disposables.add(
                repository.searchMovies(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { onLoading() }
                    .doOnSuccess { if (it.isEmpty()) throw NoResultsException() }
                    .delay(DELAY, TimeUnit.MILLISECONDS)
                    .subscribe({ onSuccess(it) }, { t -> onError(t) })
            )
    }

    private fun onLoading() {
        _movies.postValue(ViewState.Loading())
        progressStatus = MIN_PROGRESS
    }

    private fun onSuccess(movies: List<Movie>) {
        _movies.postValue(ViewState.Success(movies))
    }

    private fun onError(t: Throwable) {
        _movies.postValue(ViewState.Error(t))
        t.printStackTrace()
    }
}