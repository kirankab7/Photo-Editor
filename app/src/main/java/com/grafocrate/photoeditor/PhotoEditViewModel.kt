package com.grafocrate.photoeditor

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PhotoEditViewModel : ViewModel() {
    private val _imageBitmap = MutableLiveData<Bitmap?>()
    val imageBitmap: MutableLiveData<Bitmap?> get() = _imageBitmap

    private val _currentFilter = MutableLiveData<String>()
    val currentFilter: LiveData<String> get() = _currentFilter

    fun setImageBitmap(bitmap: Bitmap) {
        _imageBitmap.value = bitmap
    }

    fun applyFilter(filterName: String, progress: Int) {
        _currentFilter.value = filterName
        // Apply filter logic here, possibly using coroutines
        viewModelScope.launch {
            val filteredBitmap = applyFilterToBitmap(_imageBitmap.value, filterName, progress)
            _imageBitmap.postValue(filteredBitmap)
        }
    }

    private suspend fun applyFilterToBitmap(bitmap: Bitmap?, filterName: String, progress: Int): Bitmap? {
        return withContext(Dispatchers.Default) {
            // Apply filter to bitmap using the filterName and progress and return the new bitmap
            bitmap // Replace this with actual filter application logic
        }
    }

    fun undo() {
        // Undo logic here
    }

    fun redo() {
        // Redo logic here
    }

    fun delete() {
        // Delete logic here
    }

    fun saveImage() {
        // Save image logic here
    }

    fun shareImage() {
        // Share image logic here
    }

    private suspend fun applyFilterToBitmap(bitmap: Bitmap?, filterName: String): Bitmap? {
        return withContext(Dispatchers.Default) {
            // Apply filter to bitmap and return the new bitmap
            bitmap // Replace this with actual filter application logic
        }
    }
}

