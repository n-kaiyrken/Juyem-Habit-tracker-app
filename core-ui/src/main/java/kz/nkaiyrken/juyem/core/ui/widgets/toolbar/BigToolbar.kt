package kz.nkaiyrken.juyem.core.ui.widgets.toolbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.nkaiyrken.juyem.core.ui.R
import kz.nkaiyrken.juyem.core.ui.theme.JuyemTheme
import kz.nkaiyrken.juyem.core.ui.theme.additionalColors

/**
 * Large toolbar with big title text, typically used for main screens.
 *
 * @param modifier Modifier to be applied
 * @param titleText Large title text
 * @param iconResId Optional icon on the right side
 */
@Composable
fun BigToolbar(
    modifier: Modifier,
    titleText: String,
    @DrawableRes iconResId: Int? = null,
) {
    Column(
        modifier = modifier,
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        if (iconResId != null) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.End),
                painter = painterResource(id = iconResId),
                contentDescription = null,
                tint = MaterialTheme.additionalColors.elementsAccent,
            )
        } else {
            Spacer(modifier = Modifier.height(24.dp))
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 12.dp, start = 16.dp),
            text = titleText,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
            ),
        )
    }
}

@Preview
@Composable
private fun BigToolbarPreview() {
    JuyemTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.additionalColors.coreWhite),
        ) {
            BigToolbar(
                modifier = Modifier.fillMaxWidth(),
                titleText = "Home",
                iconResId = R.drawable.ic_warning,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(MaterialTheme.additionalColors.backgroundLight),
            )
        }
    }
}
