package com.example.a26a_10208_l02_03.model

import com.example.a26a_10208_l02_03.R


class DataManager {
    companion object {
        private val names = arrayOf(
            "Mauritius",
            "Austria",
            "Oman",
            "Ethiopia",
            "Tanzania",
            "Nicaragu",
            "Estonia",
            "Uganda",
            "Slovenia",
            "Italy",
            "Lebanon ",
            "Iraq",
            "Syria",
            "Honduras",
            "Comoros ",
            "China",
            "Bhutan",
            "Dominica",
            "Mauritan",
            "Scotland",
            "Kenya",
            "Slovakia",
            "Pakistan",
            "Hungary",
            "New Zealand",
            "Sri Lanka",
            "Indonesia",
            "Turkey",
            "Niger",
            "Australia",
            "Thailand",
            "Mexico",
        )
        private val canEnters = arrayOf(
            true,
            false,
            false,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            false,
            false,
            false,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            true,
            false,
            true,
            true,
            true,
            false,
            true,
            false,
            true,
            true,
            true,
        )
        private val flagImages = arrayOf(
            R.drawable._001_mauritius,
            R.drawable._003_austria,
            R.drawable._004_oman,
            R.drawable._005_ethiopia,
            R.drawable._006_tanzania,
            R.drawable._007_nicaragua,
            R.drawable._008_estonia,
            R.drawable._009_uganda,
            R.drawable._010_slovenia,
            R.drawable._013_italy,
            R.drawable._018_lebanon,
            R.drawable._020_iraq,
            R.drawable._022_syria,
            R.drawable._024_honduras,
            R.drawable._029_comoros,
            R.drawable._034_china,
            R.drawable._040_bhutan,
            R.drawable._047_dominican_republic,
            R.drawable._050_mauritania,
            R.drawable._055_scotland,
            R.drawable._067_kenya,
            R.drawable._091_slovakia,
            R.drawable._100_pakistan,
            R.drawable._115_hungary,
            R.drawable._121_new_zealand,
            R.drawable._127_sri_lanka,
            R.drawable._209_indonesia,
            R.drawable._218_turkey,
            R.drawable._222_niger,
            R.drawable._234_australia,
            R.drawable._238_thailand,
            R.drawable._252_mexico,
        )

        fun getAllCountries(): List<Country> {

            val allCountries = mutableListOf<Country>()

            for (i in names.indices)
                allCountries.add(
                    Country(
                        flagImage = flagImages[i],
                        name = names[i],
                        canEnter = canEnters[i]
                    )
                )

            allCountries.shuffle()

            return allCountries

        }
    }
}