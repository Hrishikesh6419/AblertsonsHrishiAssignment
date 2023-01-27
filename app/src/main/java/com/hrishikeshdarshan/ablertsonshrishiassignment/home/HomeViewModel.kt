package com.hrishikeshdarshan.ablertsonshrishiassignment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrishikeshdarshan.ablertsonshrishiassignment.data.AcromineRepository
import com.hrishikeshdarshan.ablertsonshrishiassignment.data.models.WordDetail
import com.hrishikeshdarshan.ablertsonshrishiassignment.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AcromineRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _acromineEvent = MutableLiveData<AcromineEvent>()
    val acromineEvent: LiveData<AcromineEvent>
        get() = _acromineEvent

    fun fetchLongForms(shortForm: String) {

        viewModelScope.launch(dispatcher) {
            _acromineEvent.postValue(AcromineEvent.Loading)

            when (val response = repository.getLongForms(shortForm)) {
                is Resource.Error -> _acromineEvent.postValue(AcromineEvent.Failure(response.message!!))
                is Resource.Success -> _acromineEvent.postValue(AcromineEvent.Success(response.data!!))
            }
        }
    }

    sealed class AcromineEvent {
        class Success(val response: List<WordDetail>) : AcromineEvent()
        class Failure(val errorMessage: String) : AcromineEvent()
        object Loading : AcromineEvent()
    }
}