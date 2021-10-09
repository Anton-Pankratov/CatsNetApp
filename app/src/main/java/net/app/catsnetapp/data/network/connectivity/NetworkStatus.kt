package net.app.catsnetapp.data.network.connectivity

sealed class NetworkStatus

object Available : NetworkStatus()
object Unavailable : NetworkStatus()
