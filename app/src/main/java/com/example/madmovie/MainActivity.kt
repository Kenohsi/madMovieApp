package com.example.madmovie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.madmovie.ui.theme.MADMovieTheme
import com.example.testapp.models.Movie
import com.example.testapp.models.getMovies
import coil.compose.rememberAsyncImagePainter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MADMovieTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        MyScreen()
                        MovieList()
                    }
                }

            }
        }
    }
}


@Preview
@Composable
fun MovieList(movies: List<Movie> = getMovies()){
    LazyColumn{
        items(movies) { movie ->
            MovieRow(movie)
        }
    }
}

@Composable
fun MovieRow(movie: Movie) {
    Card(
        shape = RoundedCornerShape(corner = CornerSize(15.dp)),
        elevation = 5.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = rememberAsyncImagePainter(movie.images[1]),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier.padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(movie.title, style = MaterialTheme.typography.h6)

                var isExpanded by remember { mutableStateOf(false) }

                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        if (isExpanded) Icons.Default.KeyboardArrowUp
                        else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Show details"
                    )
                }

                AnimatedVisibility(visible = isExpanded) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Genre: ${movie.genre}", fontWeight = FontWeight.Bold)
                        Text("Released: ${movie.year}", fontWeight = FontWeight.Bold)
                        Text("Director: ${movie.director}", fontWeight = FontWeight.Bold)
                        Text("Actors: ${movie.actors}", fontWeight = FontWeight.Bold)
                        Text("Rating: ${movie.rating}", fontWeight = FontWeight.Bold)
                        Divider(thickness = 1.dp)
                        Text("Plot: ${movie.plot}")
                    }
                }
            }
        }
    }
}


@Composable
fun MyScreen() {
    var menuExpanded by remember { mutableStateOf(false) }

    Column {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name)) },
            actions = {
                Row {
                    IconButton(onClick = { menuExpanded = !menuExpanded }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "Favorites")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        )

        DropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false },
        ) {
            DropdownMenuItem(onClick = { }) {
                Icon(Icons.Filled.Favorite,contentDescription = null)
                Spacer(modifier = Modifier.width(15.dp))
                Text(text = "Favourites")
            }
        }
    }
}