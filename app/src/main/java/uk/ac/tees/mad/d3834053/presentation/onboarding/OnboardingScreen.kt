package uk.ac.tees.mad.d3834053.presentation.onboarding

import android.content.Context
import androidx.compose.runtime.Composable
import uk.ac.tees.mad.d3834053.NavigationDestination
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.d3834053.R
import uk.ac.tees.mad.d3834053.presentation.onboarding.Tags.TAG_ONBOARD_SCREEN_NAV_BUTTON
import uk.ac.tees.mad.d3834053.presentation.onboarding.Tags.TAG_ONBOARD_SCREEN_IMAGE_VIEW
import uk.ac.tees.mad.d3834053.presentation.onboarding.Tags.TAG_ONBOARD_TAG_ROW



object OnboardingDestination : NavigationDestination {
    override val route = "onBoardingDestination"
}



val onboardPagesList = listOf(
    OnboardPage(
        imageRes = R.drawable.onboarding1,
        title = "Find petcare around your location",
        description = "Just turn on your location and you will find the nearest pet care you wish."
    ), OnboardPage(
        imageRes = R.drawable.onboarding2,
        title = "Let us give the best treatment",
        description = "Get the best treatment for your animal with us"
    ), OnboardPage(
        imageRes = R.drawable.onboarding3,
        title = "Book appointment with us!",
        description = "What do you think? book our veterinarians now."
    )
)

object Tags {
    const val TAG_ONBOARD_SCREEN = "onboard_screen"
    const val TAG_ONBOARD_SCREEN_IMAGE_VIEW = "onboard_screen_image"
    const val TAG_ONBOARD_SCREEN_NAV_BUTTON = "nav_button"
    const val TAG_ONBOARD_TAG_ROW = "tag_row"
}


@Composable
fun OnboardScreen(
    onNavigateToLoginScreen:() -> Unit
) {

    val onboardPages = onboardPagesList

    val currentPage = remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag(Tags.TAG_ONBOARD_SCREEN)
            .background(Color.White)
    ) {

        OnBoardImageView(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            currentPage = onboardPages[currentPage.value]
        )

        OnBoardDetails(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            currentPage = onboardPages[currentPage.value]
        )

        OnBoardNavButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp),
            currentPage = currentPage.value,
            noOfPages = onboardPages.size,
             onNextClicked = { currentPage.value++
             },
            onNavigation = onNavigateToLoginScreen
        )

        TabSelector(
            onboardPages = onboardPages,
            currentPage = currentPage.value
        ) { index ->
            currentPage.value = index
        }
    }
}

@Composable
fun OnBoardDetails(
    modifier: Modifier = Modifier, currentPage: OnboardPage
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = currentPage.title,
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = currentPage.description,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

//Function for ending onboarding screen
private fun onBoardingFinish(context: Context): Boolean{
    val sharedPreferences= context.getSharedPreferences("onboarding",Context.MODE_PRIVATE)
    val editor= sharedPreferences.edit()
    return editor.putBoolean("isFinished",true).commit()
}


@Composable
fun OnBoardNavButton(
    modifier: Modifier = Modifier, currentPage: Int, noOfPages: Int, onNextClicked: () -> Unit, onNavigation : () -> Unit
) {
    val context = LocalContext.current
    Button(
        onClick = {
            if (currentPage < noOfPages - 1) {
                onNextClicked()
            }
            else{
                onBoardingFinish(context)
                onNavigation()
            }
        }, modifier = modifier.testTag(TAG_ONBOARD_SCREEN_NAV_BUTTON)
    ) {
        Text(text = if (currentPage < noOfPages - 1) "Next" else "Get Started")
    }
}


@Composable
fun OnBoardImageView(modifier: Modifier = Modifier, currentPage: OnboardPage) {
    val imageRes = currentPage.imageRes
    Box(
        modifier = modifier
            .testTag(TAG_ONBOARD_SCREEN_IMAGE_VIEW + currentPage.title)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )
        Box(modifier = Modifier
            .fillMaxSize()
            .align(Alignment.BottomCenter)
            .graphicsLayer {
                // Apply alpha to create the fading effect
                alpha = 0.6f
            }
            .background(
                Brush.verticalGradient(
                    colorStops = arrayOf(
                        Pair(0.8f, Color.Transparent), Pair(1f, Color.White)
                    )
                )
            ))
    }
}

@Composable
fun TabSelector(onboardPages: List<OnboardPage>, currentPage: Int, onTabSelected: (Int) -> Unit) {
    TabRow(
        selectedTabIndex = currentPage,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .testTag(TAG_ONBOARD_TAG_ROW)

    ) {
        onboardPages.forEachIndexed { index, _ ->
            Tab(selected = index == currentPage, onClick = {
                onTabSelected(index)
            }, modifier = Modifier.padding(16.dp), content = {
                Box(
                    modifier = Modifier
                        .testTag("$TAG_ONBOARD_TAG_ROW$index")
                        .size(8.dp)
                        .background(
                            color = if (index == currentPage) MaterialTheme.colorScheme.onPrimary
                            else Color.LightGray, shape = RoundedCornerShape(4.dp)
                        )
                )
            })
        }
    }
}

data class OnboardPage(
    val imageRes: Int,
    val title: String,
    val description: String
)

@Preview
@Composable
private fun View() {
    OnboardScreen({

    })
}