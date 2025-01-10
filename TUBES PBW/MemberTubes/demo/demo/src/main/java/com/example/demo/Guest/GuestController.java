package com.example.demo.Guest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Artis.Artis;
import com.example.demo.Artis.ArtisRepository;
import com.example.demo.Setlist.*;
import com.example.demo.Show.Show;
import com.example.demo.Show.ShowRepository;

@Controller
public class guestController {

    @Autowired
    private SetListRepository setListRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ArtisRepository artistRepository;
    
    @GetMapping("/guest")
    public String showGuestPage(Model model) {
        return "Guest";
    }

    @PostMapping("/search")
public String search(@RequestParam String query, Model model) {
    // Cek apakah input query cocok dengan artis atau show
    List<Artis> artistResults = artistRepository.findByNameContainingIgnoreCase(query);
    List<Show> showResults = showRepository.findByNameContainingIgnoreCase(query);

    if (!artistResults.isEmpty()) {
        // Jika query cocok dengan artis
        Artis selectedArtist = artistResults.get(0); // Ambil artis pertama yang cocok
        List<Setlist> setlists = setListRepository.findSetlistsByArtisId(selectedArtist.getId());
        model.addAttribute("artist", selectedArtist.getNamaArtis());
        model.addAttribute("setlist", setlists);
    } else if (!showResults.isEmpty()) {
        // Jika query cocok dengan show
        Show selectedShow = showResults.get(0); // Ambil show pertama yang cocok
        List<Setlist> setlists = setListRepository.findSetlistsByShowId(selectedShow.getId());
        model.addAttribute("show", selectedShow.getNamaShow());
        model.addAttribute("setlist", setlists);
    } else {
        // Jika tidak ada hasil
        model.addAttribute("error", "Hasil tidak ditemukan untuk pencarian: " + query);
    }

    return "Search"; // Pindahkan ke halaman hasil pencarian
}

    // @GetMapping("/setListArtist")
    // public String getSetlist(@RequestParam(defaultValue = "ColdPlay") String artist, Model model) {
    //     Setlist setlist = setlists.getOrDefault(artist, setlists.get("Coldplay"));
    //     model.addAttribute("artist", artist);
    //     model.addAttribute("setlist", setlist);
    //     model.addAttribute("concertTimeline", concertTimeline);
    //     return "SetListArtist";
    // }
}