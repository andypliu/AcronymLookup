package com.albertsons.acronyms.exception

class DataException : Exception {
    constructor(message: String) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}