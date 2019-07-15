package com.obasoglu.nbateamviewer.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Nba Team data class, Parcelable
 * @param[id] id
 * @param[fullName] Full name of the team
 * @param[wins] total wins
 * @param[losses] total losses
 * @param[players] List of all players
 *
 */
@Parcelize
data class Team(
    val id: Int,
    @SerializedName(  "full_name")
    val fullName: String,
    val wins: Int,
    val losses: Int,
    val players: List<Player>
) : Parcelable

/**
 * Nba player, Parcelable
 * @param[id] id
 * @param[firstName] First name of the player
 * @param[lastName] Last name of the player
 * @param[position] Position of the player
 * @param[number] Number of the player
 *
 */
@Parcelize
data class Player(
    val id: Int,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName(  "last_name")
    val lastName: String,
    val position: String,
    val number: Int
) : Parcelable

