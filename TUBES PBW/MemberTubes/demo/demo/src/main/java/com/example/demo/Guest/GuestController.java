package com.example.demo.Guest;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Artis.Artis;
import com.example.demo.Artis.ArtisRepository;
import com.example.demo.Setlist.*;
import com.example.demo.Show.Show;
import com.example.demo.Show.ShowRepository;

@Controller
public class GuestController {

    @Autowired
    private SetListRepository setListRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ArtisRepository artistRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/guest")
    public String showGuestPage(Model model) {
        return "Guest";
    }

    @PostMapping("/search")
    public String search(@RequestParam String query, Model model, RedirectAttributes redirectAttributes) {
        // Cek apakah input query cocok dengan artis atau show
        List<Artis> artistResults = artistRepository.findByNameContainingIgnoreCase(query);
        List<Show> showResults = showRepository.findByNameContainingIgnoreCase(query);

        if (!artistResults.isEmpty()) {
            // Jika query cocok dengan artis
            Artis selectedArtist = artistResults.get(0); // Ambil artis pertama yang cocok
            List<Show> showHadir = showRepository.findShowByArtisId(selectedArtist.getId());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
            List<Map<String, String>> formattedShows = showHadir.stream()
                    .map(show -> Map.of(
                            "id", String.valueOf(show.getId()),
                            "namaShow", show.getNamaShow(),
                            "lokasiShow", show.getLokasiShow(),
                            "tanggalShow", show.getTanggalShow().format(formatter) // Format tanggal
                    ))
                    .toList();
            model.addAttribute("artist", selectedArtist.getNamaArtis());
            model.addAttribute("shows", formattedShows);
            return "Search"; // Pindahkan ke halaman hasil pencarian
        } else if (!showResults.isEmpty()) {
            // Jika query cocok dengan show
            Show selectedShow = showResults.get(0); // Ambil show pertama yang cocok
            List<Artis> artisHadir = artistRepository.findArtistsByShow(selectedShow.getNamaShow());
            model.addAttribute("show", selectedShow.getNamaShow());
            model.addAttribute("artis", artisHadir);
            return "Search"; // Pindahkan ke halaman hasil pencarian
        }
        // Jika tidak ada hasil
        redirectAttributes.addFlashAttribute("error", "Hasil tidak ditemukan untuk pencarian");
        return "redirect:/guest";
    }

    @GetMapping("/setListArtist")
    public String getSetlistget(@RequestParam String type, @RequestParam String name, Model model) {
        if ("artist".equalsIgnoreCase(type)) {
            // Jika type adalah artis
            Artis selectedArtist = artistRepository.findByNameContainingIgnoreCase(name).get(0);
            List<Setlist> setlists = setListRepository.findByArtistName(selectedArtist.getNamaArtis());
            model.addAttribute("type", "artist");
            model.addAttribute("artis", selectedArtist.getNamaArtis());
            model.addAttribute("setlist", setlists);
        } else if ("show".equalsIgnoreCase(type)) {
            // Jika type adalah show
            Show selectedShow = showRepository.findByNameContainingIgnoreCase(name).get(0);
            List<Setlist> setlists = setListRepository.findSetlistsByShowId(selectedShow.getId());
            List<Artis> artists = artistRepository.findArtistsByShow(selectedShow.getNamaShow());
            model.addAttribute("type", "show");
            model.addAttribute("show", selectedShow.getNamaShow());
            model.addAttribute("setlist", setlists);
            model.addAttribute("artists", artists);
        }
        return "SetListArtist";
    }

    @PostMapping("/setListArtist")
    public String getSetlistpost(@RequestParam String type, @RequestParam String name, Model model) {
        if ("artist".equalsIgnoreCase(type)) {
            // Dapatkan artis berdasarkan nama
            Artis selectedArtist = artistRepository.findByNameContainingIgnoreCase(name).get(0);
            // Dapatkan setlist berdasarkan artis
            List<Setlist> setlists = setListRepository.findByArtistName(selectedArtist.getNamaArtis());
            // Dapatkan show yang dihadiri artis
            List<Show> shows = showRepository.findShowByArtisId(selectedArtist.getId());

            model.addAttribute("type", "artist");
            model.addAttribute("artis", selectedArtist.getNamaArtis());
            model.addAttribute("setlist", setlists);
            model.addAttribute("shows", shows);
        } else if ("show".equalsIgnoreCase(type)) {
            // Jika type adalah show
            Show selectedShow = showRepository.findByNameContainingIgnoreCase(name).get(0);
            List<Setlist> setlists = setListRepository.findSetlistsByShowId(selectedShow.getId());
            List<Artis> artists = artistRepository.findArtistsByShow(selectedShow.getNamaShow());
            model.addAttribute("type", "show");
            model.addAttribute("show", selectedShow.getNamaShow());
            model.addAttribute("setlist", setlists);
            model.addAttribute("artists", artists);
        }
        return "SetListArtist";
    }

}