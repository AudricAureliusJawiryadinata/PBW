package com.example.demo.Guest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class SetlistController {

    private final Map<String, Setlist> setlists = Map.of(
            "Coldplay", new Setlist(
                    "Wembley Stadium, London, England",
                    "June 20, 2024",
                    List.of(
                            new Song("Yellow", ""),
                            new Song("Fix You", "Encore"),
                            new Song("Viva La Vida", ""),
                            new Song("Paradise", ""),
                            new Song("A Sky Full of Stars", "Acoustic"),
                            new Song("The Scientist", ""),
                            new Song("Clocks", ""),
                            new Song("Adventure of a Lifetime", ""),
                            new Song("Something Just Like This", ""),
                            new Song("Magic", ""))),
            "Taylor Swift", new Setlist(
                    "Madison Square Garden, New York, USA",
                    "May 15, 2024",
                    List.of(
                            new Song("Love Story", "Acoustic"),
                            new Song("You Belong with Me", ""),
                            new Song("All Too Well (10 Minute Version)", ""),
                            new Song("Shake It Off", ""),
                            new Song("Anti-Hero", ""),
                            new Song("Fearless", ""),
                            new Song("Blank Space", ""),
                            new Song("Enchanted", ""),
                            new Song("Wildest Dreams", ""),
                            new Song("The Archer", ""))),
            "Ed Sheeran", new Setlist(
                    "Wembley Stadium, London, England",
                    "July 10, 2024",
                    List.of(
                            new Song("Shape of You", ""),
                            new Song("Perfect", "Encore"),
                            new Song("Thinking Out Loud", ""),
                            new Song("Castle on the Hill", ""),
                            new Song("Photograph", ""),
                            new Song("Happier", ""),
                            new Song("Galway Girl", ""),
                            new Song("I See Fire", ""),
                            new Song("Bad Habits", ""),
                            new Song("Shivers", ""))),
            "Bruno Mars", new Setlist(
                    "Staples Center, Los Angeles, USA",
                    "August 5, 2024",
                    List.of(
                            new Song("Just the Way You Are", ""),
                            new Song("Uptown Funk", "Encore"),
                            new Song("Grenade", ""),
                            new Song("24K Magic", ""),
                            new Song("When I Was Your Man", ""),
                            new Song("Locked Out of Heaven", ""),
                            new Song("Treasure", ""),
                            new Song("Versace on the Floor", ""),
                            new Song("Finesse", ""),
                            new Song("Runaway Baby", ""))));

    private final List<Album> albums = List.of(
            new Album("Parachutes", 10),
            new Album("A Rush of Blood to the Head", 11),
            new Album("X&Y", 12),
            new Album("Viva la Vida or Death and All His Friends", 10),
            new Album("Mylo Xyloto", 14),
            new Album("Ghost Stories", 9),
            new Album("A Head Full of Dreams", 11),
            new Album("Everyday Life", 16),
            new Album("Music of the Spheres", 12));

    private final List<Concert> concertTimeline = List.of(
            new Concert("Wembley Stadium", "London, England", "June 20, 2024", "8:00 PM"),
            new Concert("Madison Square Garden", "New York, USA", "May 15, 2024", "7:30 PM"),
            new Concert("Staples Center", "Los Angeles, USA", "August 5, 2024", "8:30 PM"),
            new Concert("Wembley Stadium", "London, England", "July 10, 2024", "8:00 PM"));

    @GetMapping("/setListArtist")
    public String getSetlist(@RequestParam(defaultValue = "Coldplay") String artist, Model model) {
        Setlist setlist = setlists.getOrDefault(artist, setlists.get("Coldplay"));
        model.addAttribute("artist", artist);
        model.addAttribute("setlist", setlist);
        model.addAttribute("albums", albums);
        model.addAttribute("concertTimeline", concertTimeline);
        return "setListArtist";
    }
}