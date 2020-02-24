package com.github.zsoltk.compose.savedinstancestate

import android.os.Bundle
import androidx.compose.ProvidableAmbient
import androidx.compose.ambientOf

val ActiveSavedInstanceState: ProvidableAmbient<Bundle> =
    ambientOf { Bundle() }

