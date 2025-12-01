package com.example.login001v.data.repository

import com.example.login001v.data.model.QrResult

class QrRepository {
    fun processQrContent(content: String): QrResult {
        return QrResult(content)
    }
}