package com.liquidglass.music.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.kyant.backdrop.Backdrop
import com.kyant.backdrop.drawBackdrop
import com.liquidglass.music.navigation.BottomNavItem
import com.liquidglass.music.navigation.bottomNavItems
import kotlinx.coroutines.launch

/**
 * Liquid Glass Bottom Navigation Bar inspired by Apple Music
 * Uses the Backdrop library to create the glass refraction effect
 */
@Composable
fun LiquidGlassBottomNavBar(
    selectedRoute: String,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    backdrop: Backdrop
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .drawBackdrop(
                backdrop = backdrop,
                shape = { RoundedCornerShape(40.dp) },
                effects = {
                    vibrancy()
                    blur(8f.dp.toPx())
                    lens(20f.dp.toPx(), 40f.dp.toPx())
                },
                onDrawSurface = { drawRect(Color.White.copy(alpha = 0.4f)) }
            )
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomNavItems.forEach { item ->
                LiquidGlassNavItem(
                    item = item,
                    isSelected = selectedRoute == item.screen.route,
                    onClick = { onItemSelected(item.screen.route) },
                    backdrop = backdrop
                )
            }
        }
    }
}

@Composable
private fun LiquidGlassNavItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    backdrop: Backdrop
) {
    val animationScope = rememberCoroutineScope()
    val pressAnimation = remember { Animatable(0f) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .pointerInput(animationScope) {
                val animationSpec = spring<Float>(0.6f, 400f, 0.001f)
                awaitEachGesture {
                    awaitFirstDown()
                    animationScope.launch {
                        pressAnimation.animateTo(1f, animationSpec)
                    }
                    waitForUpOrCancellation()
                    animationScope.launch {
                        pressAnimation.animateTo(0f, animationSpec)
                    }
                }
            }
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        val scale = lerp(1f, 0.85f, pressAnimation.value)

        Box(
            modifier = Modifier
                .size(28.dp)
                .then(
                    if (isSelected) {
                        Modifier.drawBackdrop(
                            backdrop = backdrop,
                            shape = { CircleShape },
                            effects = {
                                vibrancy()
                                blur(2f.dp.toPx())
                                lens(8f.dp.toPx(), 16f.dp.toPx())
                            },
                            layerBlock = {
                                scaleX = scale
                                scaleY = scale
                            },
                            onDrawSurface = { drawRect(Color.White.copy(alpha = 0.3f)) }
                        )
                    } else {
                        Modifier
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                contentDescription = item.label,
                tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.White.copy(alpha = 0.7f),
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            text = item.label,
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.White.copy(alpha = 0.7f),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
