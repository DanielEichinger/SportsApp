package com.example.sportsapp.models

data class SportLocation(var name: String = "",
                         var description: String = "",
                         var kindsOfSports: Set<String> = emptySet())
