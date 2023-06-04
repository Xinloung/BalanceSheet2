package com.continentalbakingco.balancesheet20.model

class CountryFlag(countryName : String, flagImage : Int) {
    private var mCountryName : String = countryName
        get() = this.toString()
        set(value){
        field = value
    }
    private var mFlagImage : Int = flagImage
        get() = this.mFlagImage
        set(value){
            if(value > 0)
            field = value
        }

}