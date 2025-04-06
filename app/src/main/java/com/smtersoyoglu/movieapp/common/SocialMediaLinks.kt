package com.smtersoyoglu.movieapp.common

object SocialMediaLinks {
    fun getInstagramUrl(instagramId: String) = "https://www.instagram.com/$instagramId"
    fun getTwitterUrl(twitterId: String) = "https://www.twitter.com/$twitterId"
    fun getImdbUrl(imdbId: String) = "https://www.imdb.com/name/$imdbId"
}