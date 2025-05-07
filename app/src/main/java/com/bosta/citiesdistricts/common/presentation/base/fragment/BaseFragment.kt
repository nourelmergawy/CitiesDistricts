package com.bosta.citiesdistricts.common.presentation.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.bosta.citiesdistricts.R
import com.bosta.citiesdistricts.common.data.models.exception.BostaException
import com.bosta.citiesdistricts.common.presentation.base.delegates.exception_delegate.BostaExceptionDelegate
import com.bosta.citiesdistricts.common.presentation.base.delegates.internet_connection_delegate.IInternetConnectionDelegate
import com.bosta.citiesdistricts.common.presentation.base.delegates.internet_connection_delegate.InternetConnectionDelegate
import com.bosta.citiesdistricts.common.presentation.base.delegates.loading_delegate.ILoadingDialogDelegate
import com.bosta.citiesdistricts.common.presentation.base.delegates.loading_delegate.LoadingDialogDelegate
import com.bosta.citiesdistricts.common.presentation.base.delegates.message_delegate.IMessageDelegate
import com.bosta.citiesdistricts.common.presentation.base.delegates.message_delegate.MessageDelegate
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<VB : ViewBinding>(private val inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> VB) :
    Fragment(),
    IInternetConnectionDelegate by InternetConnectionDelegate(),
    IMessageDelegate by MessageDelegate(),
    BostaExceptionDelegate {
    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!

    lateinit var loadingDialogDelegate: ILoadingDialogDelegate
    override fun onAttach(context: Context) {
        super.onAttach(context)
        loadingDialogDelegate = LoadingDialogDelegate(context)
    }

    open fun setupUI() {}
    open fun onFragmentViewCreated(savedInstanceState: Bundle?) {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateMethod.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        onFragmentViewCreated(savedInstanceState)
    }

    open fun handleValidationErrors(fieldErrors: Map<String, Int>) {}

    override fun handleBostaException(
        exception: BostaException,
        onRetry: () -> Unit
    ) {
        when (exception) {
            is BostaException.Network.Timeout -> {
                if (exception.isRetryable()) {
                    showSnackbar(
                        binding.root,
                        getString(exception.messageRes),
                        actionText = getString(R.string.retry),
                        action = { retryAfterDelay(onRetry, exception.getRetryDelay()) }
                    )
                }
            }

            is BostaException.Network.UnknownHost -> {
                if (exception.isRetryable()) {
                    showSnackbar(
                        binding.root,
                        getString(exception.messageRes),
                        actionText = getString(R.string.retry),
                        action = { retryAfterDelay(onRetry, exception.getRetryDelay()) }
                    )
                }
            }

            is BostaException.Client.NotFound -> {
                showDialog(requireContext(), message = getString(exception.messageRes))
                showSnackbar(
                    binding.root,
                    getString(exception.messageRes)
                )
            }

            is BostaException.Client.ResponseValidation -> {
                val message = buildList {
                    exception.fieldErrors.takeIf { it.isNotEmpty() }?.let { errors ->
                        add(errors.entries.joinToString("\n") { it.value })
                    }
                }.joinToString(" ").takeIf { it.isNotBlank() }
                showSnackbar(requireView(), message ?: "Response Validation Error")
            }

            is BostaException.Client.UnauthorizedAccess -> {
                showSnackbar(
                    binding.root,
                    getString(exception.messageRes),
                    duration = Snackbar.LENGTH_INDEFINITE,
                    actionText = getString(R.string.login),
                    action = {
                        //show un-authorized bottom sheet fragment
                    }
                )
            }

            is BostaException.Local.IOProcess -> {
                val message = exception.messageRes?.let { getString(it) } ?: exception.errorMessage
                ?: "IO Error"
                showToast(requireContext(), message)
            }

            is BostaException.Local.RequestValidation -> {
                handleValidationErrors(exception.fieldErrors)
            }

            is BostaException.Server.NonRetryableServerException -> {
                showDialog(requireContext(), message = getString(exception.messageRes))
            }

            is BostaException.Server.RetryableServerException -> {
                if (exception.isRetryable()) {
                    showSnackbar(
                        binding.root,
                        getString(exception.messageRes),
                        actionText = getString(R.string.retry),
                        action = { retryAfterDelay(onRetry, exception.getRetryDelay()) }
                    )
                }
            }

            is BostaException.UnexpectedHttpException -> {
                showDialog(
                    requireContext(),
                    message = exception.errorMessage ?: "Unexpected HTTP Error"
                )
            }

            is BostaException.UnknownException -> {
            }
        }
    }

    private fun retryAfterDelay(action: () -> Unit, delay: Long) {
        binding.root.postDelayed({
            if (isNetworkAvailable(requireContext())) {
                action()
            } else {
                showSnackbar(
                    binding.root,
                    getString(R.string.no_internet_connection),
                    duration = Snackbar.LENGTH_LONG,
                    actionText = getString(R.string.retry),
                    action = { retryAfterDelay(action, delay) }
                )
            }
        }, delay)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}