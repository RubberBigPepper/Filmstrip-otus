package com.akmanaev.filmstrip.io

import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaPlayer.OnPreparedListener
import android.util.Log




object Mp3Player {

    private var player: MediaPlayer? = null

    fun stopPlay() {
        player?.let {
            try {
                it.stop()
            } catch (_: Exception) {
            }
            try {
                it.reset()
            } catch (_: Exception) {
            }
            try {
                it.release()
            } catch (_: Exception) {
            }
            player = null
        }
    }

    private fun isPlayingMP3(): Boolean {
        try {
            return player?.isPlaying ?:false
        } catch (_: Exception) {
        }
        return false
    }

    fun playMP3(strURL: String) {
        stopPlay()
        if (strURL.isNotEmpty()) {
            try {
                player = MediaPlayer()
                player?.setOnPreparedListener{
                    try {
                        player?.start()
                    }catch (_: java.lang.Exception) {
                    }
                }
                player?.setDataSource(strURL)
                player?.prepareAsync()
                player?.setOnCompletionListener{
                    stopPlay()
                }
            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
            }
        }
    }

}