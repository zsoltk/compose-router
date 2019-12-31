package com.github.zsoltk.compose.savedinstancestate

import android.os.Bundle
import androidx.compose.Ambient

val savedInstanceState: Ambient<Bundle> =
    Ambient.of { Bundle() }

