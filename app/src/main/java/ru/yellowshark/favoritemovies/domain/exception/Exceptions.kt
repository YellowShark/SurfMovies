package ru.yellowshark.favoritemovies.domain.exception

import java.lang.Exception

class NoConnectivityException : Exception("Check your network connection")

class NoResultsException : Exception("Nothing was found")