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
fun MovieRow(movie: Movie = getMovies()[0]) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        shape = RoundedCornerShape(corner = CornerSize(15.dp)),
        elevation = 5.dp
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(movie.images[1]),
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Add to Favourite",
                        tint = androidx.compose.ui.graphics.Color.White
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Text(movie.title, style = MaterialTheme.typography.h6)
                var isExpanded by remember { mutableStateOf(false) }
                Row(
                    horizontalArrangement = Arrangement.End
                ) {
                    Row(modifier = Modifier.clickable { isExpanded = !isExpanded }
                    ) {
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.KeyboardArrowDown
                            else Icons.Default.KeyboardArrowUp,
                            contentDescription = "Show details",
                        )

                    }
                }
                AnimatedVisibility(visible = isExpanded) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 1.dp, vertical = 50.dp),
                        horizontalArrangement = Arrangement.End)
                    {
                        Column {
                            Row {
                                Text(
                                    text = "Genre : ",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = movie.genre, style = MaterialTheme.typography.subtitle2)
                            }
                            Row {
                                Text(
                                    text = "Released : ",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = movie.year, style = MaterialTheme.typography.subtitle2)
                            }
                            Row {
                                Text(
                                    text = "Director : ",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = movie.director, style = MaterialTheme.typography.subtitle2)
                            }
                            Row {
                                Text(
                                    text = "Actors : ",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = movie.actors, style = MaterialTheme.typography.subtitle2)
                            }

                            Row {
                                Text(
                                    text = "Rating : ",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = movie.rating, style = MaterialTheme.typography.subtitle2)
                            }
                            Divider(
                                thickness = 1.dp
                            )
                            Row {
                                Text(
                                    text = "Plot : ",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = movie.plot, style = MaterialTheme.typography.subtitle2)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MyScreen() {
    var menuExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        actions = {
            Row {
                IconButton(onClick = { menuExpanded = !menuExpanded }) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "Favorites")
                }
                Spacer(modifier = Modifier.width(8.dp))
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
    )
}