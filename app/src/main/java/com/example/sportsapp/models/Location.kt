package com.example.sportsapp.models

data class Location(var name: String = "",
                    var description: String = "",
                    var sports: Set<String> = emptySet())
