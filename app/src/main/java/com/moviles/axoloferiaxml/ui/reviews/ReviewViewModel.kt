package com.moviles.axoloferiaxml.ui.reviews

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviles.axoloferiaxml.core.KeystoreHelper
import com.moviles.axoloferiaxml.data.database.StallFavorite
import com.moviles.axoloferiaxml.data.model.GenericResponse
import com.moviles.axoloferiaxml.data.model.PublishReview
import com.moviles.axoloferiaxml.data.model.Review
import com.moviles.axoloferiaxml.domain.DeleteReviewUseCase
import com.moviles.axoloferiaxml.domain.GetReviewsUseCase
import com.moviles.axoloferiaxml.domain.SendReviewUseCase
import com.moviles.axoloferiaxml.ui.home_user.StallResult
import com.moviles.axoloferiaxml.ui.home_user.StallView
import com.moviles.axoloferiaxml.ui.stall_management.create_stall.GenericResponseResult
import kotlinx.coroutines.launch

class ReviewViewModel : ViewModel() {



    private val _reviewResult = MutableLiveData<ReviewResult>()
    val reviewResult: LiveData<ReviewResult> = _reviewResult

    private val _genericResponseResult = MutableLiveData<GenericResponseResult>()
    val genericResponseResult: LiveData<GenericResponseResult> = _genericResponseResult

    private val getReviewsUseCase = GetReviewsUseCase()
    private val sendReviewUseCase = SendReviewUseCase()
    private val deleteReviewUseCase = DeleteReviewUseCase()

    fun getReviewList(context: Context, stallId: String) {
        viewModelScope.launch {
            try {
                val keystoreHelper = KeystoreHelper(context)
                val result = getReviewsUseCase(keystoreHelper, stallId.toInt())
                if (result != null) {
                    _reviewResult.value =
                        ReviewResult(success = result.reviewData)
//                    _reviewResult.value?.success?.stall?.forEach {
//                        Log.d("stall", "$it")
//                    }
                } else {
                    _reviewResult.value =
                        ReviewResult(error = 0)
                    Toast.makeText(context, "EXCEP$result", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "EXCEP ", Toast.LENGTH_LONG).show()
                Log.e("error", e.message.toString())
                e.printStackTrace()
            }
        }
    }


    fun sendReview(review: PublishReview, context: Context) {
        viewModelScope.launch {
            try {
                val keystoreHelper = KeystoreHelper(context)
                val result = sendReviewUseCase(review, keystoreHelper)
                if (result != null) {
                    _genericResponseResult.value =
                        GenericResponseResult(success = result)
                } else {
                    _genericResponseResult.value =
                        GenericResponseResult(error = 0)
                    Toast.makeText(context, "EXCEP$result", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "EXCEP", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }
    }

    fun deleteReview(context: Context, reviewId: Int) {
        viewModelScope.launch {
            try {
                val keystoreHelper = KeystoreHelper(context)
                val result = deleteReviewUseCase(keystoreHelper, reviewId)
                if (result != null) {
                    _genericResponseResult.value =
                        GenericResponseResult(success = result)
                } else {
                    _genericResponseResult.value =
                        GenericResponseResult(error = 0)
                    Toast.makeText(context, "EXCEP $result", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "EXCEP ", Toast.LENGTH_LONG).show()
                Log.e("error", e.message.toString())
                e.printStackTrace()
            }
        }
    }
}